package hospital_v2.scenarios;
import hospital_v2.Scenario;
import hospital_v2.domain.*;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * TestScene.java
 *
 * Created: 2024/1/21
 * By: Zhang Huan
 */

/**
 * 场景（Scenario）存储仿真模型需要的信息。
 * 一个场景类的实例包含了各种分布的参数和仿真模型的配置。
 * 一个场景表示一个不同的门诊场景。
 * **/

public class TestScene extends Scenario {
    // 时间参数
    // 仿真时长，包括：预热期、统计期和收尾期
    public int warm_up_period = 3;
    public int service_period = 7;
    public int filling_period = 20;

    // 仿真参数
    // 患者到达分布，以小时为下标，以每小时到达的平均患者数量（lambda）为元素
    public int patient_arr_lambda = 100;
    // 检查需求信息，下标就是患者的检查数量，因此第一个数一定是0。例如：exam_num_rate[x]表示患者检查数量为x的概率。
    public float[] exam_num_ratio = {0, 0.8F, 0.2F};
    // 检查单结构信息，储存每一个检查数量下的检查单。
    public HashMap<Integer, OrderStructure> order_structure_list;

    // 优化参数
    // 总天数与单日最大时间段数量
    public int max_slot;
    public int total_period = 30;

    // 资源参数
    // 科室列表与检查约束（通过setUp函数）
    public ArrayList<Department> dept_list;
    public ExamConstraints exam_constr;


    // 设置科室
    public void setUp() {
        // 新建科室列表
        ArrayList<Department> new_dept_list = new ArrayList<>();
        // 科室1：1个检查包，3个机器
        Department d1 = new Department("D1", 3);
        // 检查项目
        d1.addExamItem(new ExamItem("D1E1", d1, 1, new float[] {0, 0.5F, 0.5F}));
        d1.addExamItem(new ExamItem("D1E2", d1, 2, new float[] {0, 0.5F, 0.5F}));

        // 机器
        Machine d1m1 = new Machine("D1M1", d1, 1.5F);
        Machine d1m2 = new Machine("D1M2", d1, 1.5F);
        Machine d1m3 = new Machine("D1M3", d1, 1.5F);

        // 计划
        int[] ids1 = {1, 2, 3, 4, 5, 6};
        int[] sts1 = {420, 510, 600, 810, 900, 990};
        int[] ets1 = {510, 600, 690, 900, 990, 1080};
        int[] sizes1 = {20, 20, 20, 20, 20, 20};
        int[] ids2 = {1, 2, 3};
        int[] sts2 = {480, 570, 660};
        int[] ets2 = {570, 660, 750};
        int[] sizes2  = {20, 20, 20};
        max_slot = 6;

        d1m1.setWeekdayPlan(6, ids1, sts1, ets1, sizes1);
        d1m1.setWeekendPlan(3, ids2, sts2, ets2, sizes2);
        d1m2.setWeekdayPlan(6, ids1, sts1, ets1, sizes1);
        d1m2.setWeekendPlan(3, ids2, sts2, ets2, sizes2);
        d1m3.setWeekdayPlan(6, ids1, sts1, ets1, sizes1);
        d1m3.setWeekendPlan(3, ids2, sts2, ets2, sizes2);
        // 添加机器
        d1.addMachine(d1m1);
        d1.addMachine(d1m2);
        d1.addMachine(d1m3);
        // 添加科室
        new_dept_list.add(d1);

        // 科室2：2个检查包，2个机器
        Department d2 = new Department("D2", 2);
        // 检查项目
        d2.addExamItem(new ExamItem("P2E1", d2, 1, new float[] {0, 0.5F, 0.5F}));
        d2.addExamItem(new ExamItem("P2E2", d2, 2, new float[] {0, 0.5F, 0.5F}));
        // 机器
        Machine d2m1 = new Machine("D2M1", d2, 1.5F);
        Machine d2m2 = new Machine("D2M2", d2, 1.5F);
        d2m1.setWeekdayPlan(6, ids1, sts1, ets1, sizes1);
        d2m1.setWeekendPlan(3, ids2, sts2, ets2, sizes2);
        d2m2.setWeekdayPlan(6, ids1, sts1, ets1, sizes1);
        d2m2.setWeekendPlan(3, ids2, sts2, ets2, sizes2);
        // 添加机器
        d2.addMachine(d2m1);
        d2.addMachine(d2m2);
        // 添加科室
        new_dept_list.add(d2);

        // 加入属性
        this.dept_list = new_dept_list;

        // 建立检查单结构
        order_structure_list = new HashMap<>();
        for (int i = 1; i <= 2; i++) {
            order_structure_list.put(i, new OrderStructure(i));
        }

        // 取出检查数量为1的检查单结构
        order_structure_list.get(1).addNewOrder(new Department[] {d1}, 0.5F);
        order_structure_list.get(1).addNewOrder(new Department[] {d2}, 0.5F);
        // 取出检查数量为2的检查单结构
        order_structure_list.get(2).addNewOrder(new Department[] {d1, d2}, 1.0F);
    }

    public int getWarm_up_period() {return warm_up_period;}

    public int getService_period() {return service_period;}

    public int getFilling_period() {return filling_period;}

    public int getTotal_period() {return total_period;}

    public int getPatient_arr_lambda() {return patient_arr_lambda;}

    public float[] getExam_num_ratio() {return exam_num_ratio;}

    public HashMap<Integer, OrderStructure> getOrder_structure_list() {return order_structure_list;}

    public ArrayList<Department> getDept_list(){
        return dept_list;
    };

    public ExamConstraints getExam_constr() {return exam_constr;}

    public int getMax_slot() {return max_slot;}

    public static void main(String[] args) throws InterruptedException {
        new TestScene();
    }
}
