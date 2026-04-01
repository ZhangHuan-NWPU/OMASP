package hospital_v2;

import java.util.*;
import ec.EvolutionState;
import ec.app.MultiAppScheduling.*;
import ec.gp.GPIndividual;
import ec.util.RandomChoice;
import hospital_v2.domain.*;
import sim.util.distribution.Exponential;

/*
 * Replication.java
 *
 * Created: 2024/1/17
 * By: Zhang Huan
 */

public class Replication {
    // 场景
    public Scenario scene;

    // 仿真参数：仿真种子、日期记录
    public int date = 0;
    // 仿真时长：预热期、统计期和收尾期
    public final int warm_up_period;
    public final int service_period;
    public final int filling_period;
    public final int sim_period;
    public final int total_period;

    // 门诊参数：科室列表、检查约束
    public ArrayList<Department> dept_list;
    public ExamConstraints exam_constr;
    public HashMap<Integer, OrderStructure> order_structure_list;

    // 评估使用
    public GPIndividual ind;
    public _Problem problem;

    // 目标函数：间接等待时间，跑路次数
    public float indirect_waiting_time = 0.0F;
    public float travel_times = 0.0F;

    // 统计信息：患者总数，剩余资源。资源总量
    public int patient_num = 0;
    public int total_patient_num = 0;
    public int[] remaining_resource;
    public int[] total_resource;

    // 构造函数
    public Replication(Scenario scene,  _Problem problem){
        this.scene = scene;
        scene.setUp();

        warm_up_period = scene.getWarm_up_period();
        service_period = scene.getService_period();
        filling_period = scene.getFilling_period();
        sim_period = warm_up_period + service_period;
        total_period = sim_period + filling_period;
        dept_list = scene.getDept_list();
        exam_constr = scene.getExam_constr();
        order_structure_list = scene.getOrder_structure_list();

        this.problem = problem;
    }

    // 启动仿真
    public void run(EvolutionState state, int thread, GPIndividual ind, Scenario scene, _FloatData input){
        // 得到个体
        this.ind = ind;

        // 更新资源列表
        for (Department dept: dept_list){
            for (Machine mach: dept.machines){
                mach.getNewResourceTable(total_period);
                // System.out.println(mach.name + mach.resource_table.toString());
            }
        }

        // System.out.println(order_structure_list.get(1).order_list.get(0)[0].machines.get(0).resource_table);

        // 更新属性
        date = 0;
        indirect_waiting_time = 0.0F;
        travel_times = 0.0F;
        patient_num = 0;
        remaining_resource = new int[dept_list.size()];
        total_resource = new int[dept_list.size()];

        // 运行仿真
        for (; date < sim_period; date++){
            runAShift(state, thread, scene, input);
            // System.out.println("Date" + date + ' ' + indirect_waiting_time / patient_num);
        }

        for (int i = 0; i < dept_list.size(); i++){
            for (Machine mach: dept_list.get(i).machines){
                remaining_resource[i] += mach.getRemainingResource(warm_up_period, sim_period);
                total_resource[i] += mach.getTotalResource(warm_up_period, sim_period);
            }
        }

        // 计算目标函数
        indirect_waiting_time = indirect_waiting_time / patient_num;
        travel_times = travel_times / patient_num;
        /*
        // 打印资源利用情况
        for (Department dept: dept_list) {
            System.out.println(dept.name);
            for (Machine mach: dept.machines) {
                mach.printResource(warm_up_period, sim_period);
            }
        }
        */
        // 清理资源列表
        for (Department dept: dept_list) {
            for (Machine mach: dept.machines) {
                mach.cleanUp();
            }
        }
    }

    public void runAShift(EvolutionState state, int thread, Scenario scene, _FloatData input){
        // step1 设置仿真时钟
        double sim_clock = 0.0;

        // step2 设置指数分布
        Exponential intervalGenerator = new Exponential(scene.getPatient_arr_lambda() / 24.0, state.random[thread]);

        // 更新积压患者数量和工作量
        for (Department department : dept_list) {
            for (Machine machine : department.machines) {
                for (Resource resource : machine.resource_table.get(date)) {
                    resource.machine.overstocked_patient_num -= resource.patient_list.size();
                    resource.machine.overstocked_patient_workload -= resource.occupied_capacity;
                }
            }
        }

        // step3 直到终止条件
        while (sim_clock < 24) {
            sim_clock += intervalGenerator.nextDouble();
            Patient patient = getNewPatient(state, thread, scene);

            // step4 生成一系列决策情景
            ArrayList<DecisionSituation> DS_list = getDecisionSituation(patient);
            //for (DecisionSituation ds: DS_list) {
            //    System.out.println(ds.opt_list.size());
            //}

            // step5 评估
            for (DecisionSituation ds: DS_list) {
                ds.evaluate(state, thread, ind, input, problem);
            }
            if (patient.exam_num > 1) {
                DecisionMaker_v2 dm = new DecisionMaker_v2();
                dm.optimize(patient, DS_list, scene);
            }

            // step6 更新患者，资源和目标函数
            // 患者得到预约
            patient.received = true;


            // 更新资源的工作量，机器的积压工作量（顺便更新目标函数）
            int[] runs = new int[patient.exam_num];
            int iwt = 0;
            for (int i = 0; i < patient.exam_num; i++) {
                // System.out.println(DS_list.get(i).chosen_opt);
                if (DS_list.get(i).chosen_opt == null) {
                    continue;
                }
                Resource res = DS_list.get(i).chosen_opt.resource;
                res.patient_list.add(patient);
                // System.out.println(res.occupied_capacity+" "+patient.exam_list[i].workload);
                if (res.occupied_capacity + patient.exam_list[i].workload <= res.capacity) {
                    res.occupied_capacity += patient.exam_list[i].workload;
                    res.remaining_capacity -= patient.exam_list[i].workload;

                    res.machine.remaining_daily_resource[date] -= patient.exam_list[i].workload;
                }
                // System.out.println(res.occupied_capacity+" "+patient.exam_list[i].workload);
                res.machine.overstocked_patient_num += 1;
                res.machine.overstocked_patient_workload += patient.exam_list[i].workload;

                runs[i] = res.date;
                iwt += (res.date - date);

                /*
                if (warm_up_period <= date && date < sim_period) {
                    Option copt = DS_list.get(i).chosen_opt;
                    // System.out.println("(" + copt.EAD + "," + copt.MaxEAD + "," + copt.D + "," + copt.MRC+ "," + copt.S + ")");
                    // System.out.println("(" + copt.EAD + "," + copt.D + "," + copt.TWL + "," + copt.S + ")");
                    // System.out.println("(" + copt.nE + "," + copt.EAD + "," + copt.D + "," + copt.MaxEAD + "," + copt.S + "," + copt.RC + "," + copt.WL + ")");
                    System.out.println(copt.OWL);
                    // System.out.println(Arrays.toString(DS_list.get(i).get_decision()));
                }
                */
            }

            // 更新目标函数（间接等待时间和跑路次数）
            total_patient_num++;
            if (warm_up_period <= date && date < sim_period) {
                patient_num++;
                indirect_waiting_time += iwt;
                HashSet<Integer> set = new HashSet<>();
                for (int i = 0; i < runs.length; i++) {
                    set.add(runs[i]);
                }
                travel_times += set.size();
            }
        }
    }

    // 生成新患者
    public Patient getNewPatient(EvolutionState state, int thread, Scenario scene) {
        // 获得检查的数量
        float[] exam_num_probabilities = scene.getExam_num_ratio().clone();
        RandomChoice.organizeDistribution(exam_num_probabilities);
        int exam_num = RandomChoice.pickFromDistribution(exam_num_probabilities, state.random[thread].nextFloat());

        // 生成一个新患者
        Patient new_patient = new Patient(date, exam_num);

        // 确定患者的检查列表
        Department[] patient_dept_list = order_structure_list.get(exam_num).getRandomOrder(state.random[thread].nextFloat());

        // 确定患者的检查项目
        for (Department dept: patient_dept_list) {
            new_patient.addExam(dept.getRandomExamItem(exam_num, state.random[thread].nextFloat()).getNewExam());
        }

        return new_patient;
    }

    // 对一个患者生成一系列决策情景
    public ArrayList<DecisionSituation> getDecisionSituation(Patient patient){
        //System.out.println(dept_list.get(0).machines.get(0));
        //System.out.println(patient.exam_list[0].department.machines.get(0));
        // step1 建立决策情景数组
        ArrayList<DecisionSituation> DS_list = new ArrayList<>();

        // step2 计算每一个检查的最早可开始日期
        int[] eads = new int[patient.exam_num];
        to: for (int i = 0; i < patient.exam_num; i++) {

            Exam e = patient.exam_list[i];
            // 计算检查的最早可开始日期
            for (int d = (date+1); d < total_period; d++) {
                for (Machine machine : e.department.machines) {
                    ArrayList<Resource>daily_resource = machine.resource_table.get(d);
                    for (Resource resource : daily_resource) {
                        if (resource.remaining_capacity >= e.workload) {
                            // 设置最早可开始日期
                            e.earliest_available_date = resource.date;
                            // 暂时储存日期
                            eads[i] = resource.date;
                            continue to;
                        }
                    }
                }
            }
        }

        int min_ead = date + 1;
        OptionalInt temp = Arrays.stream(eads).min();
        if (temp.isPresent()) min_ead = temp.getAsInt();

        int max_ead = total_period - 1;
        temp = Arrays.stream(eads).max();
        if (temp.isPresent()) max_ead = temp.getAsInt();

        // step2 对于每一项检查，建立一个决策情景
        for (Exam e: patient.exam_list){
            DecisionSituation newDS = new DecisionSituation();

            // step3 筛选选项
            // boolean flag = false; // flag用来确定检查的最早可开始日期
            for (Machine machine: e.department.machines){
                // 对于机器上的每一个资源
                for (int d = e.earliest_available_date; d <= max_ead; d++){
                    // 稳态规则测试
                    // if (d > date + 7) continue;
                    // System.out.println(machine.name + machine.resource_table.toString());
                    for (Resource resource: machine.resource_table.get(d)){
                        // 筛选过程
                        if (resource.remaining_capacity < e.workload) {
                            continue;
                        }
                        // 添加到决策情景
                        Option newOpt = new Option(resource,
                                e.workload,                                        // 该检查项目的工作量
                                e.earliest_available_date,                         // 该检查项目的最早可行日期
                                patient.total_workload,                            // 该患者的检查的总工作量
                                patient.exam_num,                                  // 该患者的检查数量/权重
                                max_ead,                                           // 该患者所有检查的最早可行日期中的最大值
                                min_ead,                                           // 该患者所有检查的最早可行日期中的最小值
                                e.department.m_size,                                 // 该科室的机器数量
                                resource.date,                                     // 日期
                                resource.slot,                                     // 时间段
                                resource.capacity,                                 // 该时间段的总容量
                                resource.remaining_capacity,                       // 该时间段的剩余容量
                                machine.remaining_daily_resource[resource.date],   // 该机器在当天的剩余容量
                                machine.overstocked_patient_num,                   // 该机器的积压患者数
                                machine.overstocked_patient_workload,              // 该机器的积压患者的总工作量
                                machine.average_workload);                         // 该机器的检查项目的平均工作量)
                        newDS.addNewOption(newOpt);
                    }
                }
            }
            DS_list.add(newDS);
        }

        /*
        // 更新所有检查的最早可行日期中的最大值和最小值
        if (patient.exam_num > 1) {
            int MaxEAD = 0;
            int MinEAD = 9999;
            for (Exam e: patient.exam_list){
                MinEAD = Math.min(MinEAD, e.earliest_available_date);
                MaxEAD = Math.max(MaxEAD, e.earliest_available_date);
            }
            for (DecisionSituation ds: DS_list){
                ds.updateMaxEADAndMinEAD(MaxEAD, MinEAD);
            }
        }
         */
        return DS_list;
    }
}
