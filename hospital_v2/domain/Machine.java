package hospital_v2.domain;
import hospital_v2.domain.*;

import java.util.*;

/*
 * Machine.java
 *
 * Created: 2024/4/21
 * By: Zhang Huan
 */

public class Machine {
    // 机器的属性：名称、科室、检查包
    public final String name;
    public final Department department;

    // 机器的检查项目列表
    public ArrayList<ExamItem> exam_items;

    // 机器的计划
    public ArrayList<Map<String, Integer>> weekday_plan;
    public ArrayList<Map<String, Integer>> weekend_plan;
    // 检查资源，一个以日期（date）和时间段（slot）为下标的二维列表
    public ArrayList<ArrayList<Resource>> resource_table;
    public int[] total_daily_resource;
    public int[] remaining_daily_resource;

    // 终端
    public int overstocked_patient_num = 0;
    public int overstocked_patient_workload = 0;
    public float average_workload;

    // 构造函数
    public Machine(String name, Department department, float awl){
        this.name = name;
        this.department = department;
        this.exam_items = department.exam_items;
        this.average_workload = awl;
        // 初始化资源表
        this.resource_table = new ArrayList<ArrayList<Resource>>();
    }

    // 设置计划中的时间段
    public Map<String, Integer> getSlotConf(int id, int st, int et, int size){
        Map<String, Integer> slot = new HashMap<>();
        slot.put("ID", id);
        // 注意时间段的开始时间与结束时间
        if (et >= st){
            slot.put("START_TIME", st);
            slot.put("END_TIME", et);
        }
        else{
            slot.put("START_TIME", st);
            slot.put("END_TIME", st);
            System.out.println("Err: getSlotConf st > et");
        }
        slot.put("LENGTH", slot.get("END_TIME") - slot.get("START_TIME"));
        slot.put("SIZE", size);
        return slot;
    }

    /**
     * 计划是一天之内时间段和资源安排的模板。
     * 时间段（Slot）的信息包括编号、开始时间、结束时间和容量。本函数通过一系列数组描述机器的计划。
     * 数组的长度必须相同，而且提前决定。时间的单位为分钟。
     * **/
    public void setWeekdayPlan(int size, int[] ids, int[] sts, int[] ets, int[] sizes){
        ArrayList<Map<String, Integer>> plan = new ArrayList<Map<String, Integer>>();
        for (int i = 0; i < size; i++){
            if (i >= ids.length){
                System.out.println("Err: setWeekdayPlan oversize ids");
            }
            if (i >= sts.length){
                System.out.println("Err: setWeekdayPlan oversize sts");
            }
            if (i >= ets.length){
                System.out.println("Err: setWeekdayPlan oversize ets");
            }
            if (i >= sizes.length){
                System.out.println("Err: setWeekdayPlan oversize sizes");
            }
            plan.add(getSlotConf(ids[i], sts[i], ets[i], sizes[i]));
        }
        weekday_plan = plan;
    }

    public void setWeekendPlan(int size, int[] ids, int[] sts, int[] ets, int[] sizes){
        ArrayList<Map<String, Integer>> plan = new ArrayList<Map<String, Integer>>();
        for (int i = 0; i < size; i++){
            if (i >= ids.length){
                System.out.println("Err: setWeekdayPlan oversize ids");
            }
            if (i >= sts.length){
                System.out.println("Err: setWeekdayPlan oversize sts");
            }
            if (i >= ets.length){
                System.out.println("Err: setWeekdayPlan oversize ets");
            }
            if (i >= sizes.length){
                System.out.println("Err: setWeekdayPlan oversize sizes");
            }
            plan.add(getSlotConf(ids[i], sts[i], ets[i], sizes[i]));
        }
        weekend_plan = plan;
    }

    // 根据计划生成真实资源列表
    public void getNewResourceTable(int d){
        total_daily_resource = new int[d];
        ArrayList<ArrayList<Resource>> resource_table = new ArrayList<>();
        for (int i = 0; i < d; i++){
            // 根据日期确定每日的资源
            ArrayList<Resource> daily_resource = new ArrayList<>();
            if (i % 7 <= 4){
                for (Map<String, Integer> slot: weekday_plan){
                    Resource r = new Resource(i, slot.get("ID"), this, slot.get("SIZE"));
                    daily_resource.add(r);
                    total_daily_resource[i] += r.capacity;
                }
                resource_table.add(daily_resource);
            }
            else{
                for (Map<String, Integer> slot: weekend_plan){
                    Resource r = new Resource(i, slot.get("ID"), this, slot.get("SIZE"));
                    daily_resource.add(r);
                    total_daily_resource[i] += r.capacity;
                }
                resource_table.add(daily_resource);
            }
        }
        this.resource_table = resource_table;
        this.remaining_daily_resource = total_daily_resource.clone();
    }

    // 垃圾清理
    public void cleanUp(){
        for(ArrayList<Resource> dr: resource_table) {
            for(Resource res: dr) {
                res.cleanUp();
                res = null;
            }
        }
    }

    // 计算剩余资源
    public int getRemainingResource(int warm_up_period, int sim_period) {
        int res = 0;
        for (ArrayList<Resource> daily_resource: resource_table) {
            for (Resource r: daily_resource) {
                if (warm_up_period <= r.date && r.date < sim_period) res += r.remaining_capacity;
            }
        }
        return res;
    }

    // 计算剩余资源
    public int getTotalResource(int warm_up_period, int sim_period) {
        int res = 0;
        for (int d = warm_up_period; d < sim_period; d++) res = res + total_daily_resource[d];
        return res;
    }

    public void printResource(int warm_up_period, int sim_period) {
        System.out.println("Machine: "+name);
        for (ArrayList<Resource> daily_resource: resource_table) {
            for (Resource r: daily_resource) {
                if (warm_up_period <= r.date && r.date < sim_period) {
                    System.out.println("D"+r.date+"S"+r.slot+": "+r.occupied_capacity+"/"+r.capacity);
                }
            }
        }
    }
}
