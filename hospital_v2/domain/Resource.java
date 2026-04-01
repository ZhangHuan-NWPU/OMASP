package hospital_v2.domain;
import java.util.ArrayList;

/*
 * Resource.java
 *
 * Created: 2024/4/21
 * By: Zhang Huan
 */

public class Resource {
    // 标识检查资源的主要信息为日期（date）和时间段（slot）
    public int date;
    public int slot;
    public Machine machine;
    // 检查资源的主要属性包括容量、占用容量、剩余容量和患者预约列表
    public int capacity;
    public int occupied_capacity = 0;
    public int remaining_capacity;
    public ArrayList<Patient> patient_list;

    // 构造方法
    public Resource(int date, int slot, Machine machine, int capacity){
        this.date = date;
        this.slot = slot;
        this.machine = machine;
        this.capacity = capacity;
        // 初始化剩余容量为容量
        this.remaining_capacity = this.capacity;
        // 初始化患者列表
        this.patient_list = new ArrayList<>();
    }

    // 添加患者
    public void addPatient(Patient p, Exam e){
        patient_list.add(p);
        occupied_capacity += e.workload;
        remaining_capacity -= e.workload;
    }

    // 垃圾清理
    public void cleanUp(){
        for(Patient p: patient_list) {
            p = null;
        }
    }
}
