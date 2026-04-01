package hospital_v2.domain;
import java.util.ArrayList;

/*
 * Patient.java
 *
 * Created: 2024/1/17
 * By: Zhang Huan
 */

/**
 * 患者（Patient）是仿真模型的基础类之一，表示一名提交预约请求的患者。
 * 预约请求是一个清单，包含了该患者需要接受的一系列检查。
 * 患者的预约日期、时间段和机器都保存在本类中，以备后续使用。
 * **/

public class Patient {
    // 患者的参数包括：提交日期、检查数量（权重）、检查清单
    // 这些属性用于描述患者的预约请求，以及具体的检查需要
    public final int submit_date;
    public final int exam_num;
    public Exam[] exam_list;
    public int last = 0;

    // 终端（总工作量、最早可行日期中的最大值和最小值）
    public int total_workload = 0;
    public int max_earliest_available_date;
    public int min_earliest_available_date;

    // 预约信息：是否得到服务
    public boolean received = false;

    // 构造函数
    public Patient(int date, int exam_num){
        this.submit_date = date;
        this.exam_num = exam_num;
        this.exam_list = new Exam[exam_num];
    }

    // 添加新的检查
    public void addExam(Exam e){
        if (last < exam_num) {
            exam_list[last] = e;
            last++;
            total_workload += e.workload;
        }
        else {
            System.out.println("No space in exam list.");
        }
    }

    // 设置最早可行日期中的最大值和最小值
    public void setMaxAndMinEAD(){
        int max = 0;
        int min = 100000;
        for (Exam e: exam_list){
            if (e.earliest_available_date > max){
                max = e.earliest_available_date;
            }
            if (e.earliest_available_date < min){
                min = e.earliest_available_date;
            }
        }
        max_earliest_available_date = max;
        min_earliest_available_date = min;
    }
}
