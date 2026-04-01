package hospital_v2.domain;

/*
 * ExamItem.java
 *
 * Created: 2024/4/21
 * By: Zhang Huan
 */

/**
 * 检查项目（ExamItem）是仿真模型的基础类之一，表示一个检查项目。
 * 该类包含了检查项目的基本信息。
 * 该类可以生成一个检查（Exam），以加入患者的检查清单。
 * **/

public class ExamItem {
    // 检查的属性：名称、检查包、工作量、出现频率（根据患者的检查数量）
    // 检查包是一项检查所属的类型，而工作量则是一项检查占用机器能力的程度
    public final String name;
    public final Department department;
    public final int workload;
    public final float[] demand_ratio;

    // 构造函数
    public ExamItem(String name, Department department, int workload, float[] ratio){
        this.name = name;
        this.department = department;
        this.workload = workload;
        this.demand_ratio = ratio;
    }

    // 获得一个检查
    public Exam getNewExam(){
        return new Exam(name, department, workload);
    }
}
