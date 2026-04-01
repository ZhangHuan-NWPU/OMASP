package hospital_v2.domain;

/*
 * Exam.java
 *
 * Created: 2024/4/21
 * By: Zhang Huan
 */

/**
 * 检查（Exam）是仿真模型的基础类之一，表示某个患者的一个检查需求。
 * 该类包含了检查项目的基本信息。
 * 患者的预约日期、时间段和机器都保存在本类中，以备后续使用。
 * **/

public class Exam {
    // 检查的属性：名称、科室、工作量
    // 检查包是一项检查所属的类型，而工作量则是一项检查占用机器能力的程度
    public final String name;
    public final Department department;
    public final int workload;

    // 终端
    public int earliest_available_date;

    // 预约信息
    // 检查是否得到预约
    public boolean received = false;
    // 检查的预约机器、日期、时间段
    public Machine machine;
    public int appointment_date;
    public int appointment_slot;

    // 构造函数
    public Exam(String name, Department department, int workload){
        this.name = name;
        this.department = department;
        this.workload = workload;
    }
}
