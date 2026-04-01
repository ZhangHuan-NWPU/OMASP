package hospital_v2.domain;

import ec.util.RandomChoice;
import java.util.ArrayList;

/*
 * Department.java
 *
 * Created: 2024/4/21
 * By: Zhang Huan
 */

public class Department {
    // 科室的名称
    public final String name;
    // 科室的检查包列表
    public ArrayList<ExamItem> exam_items;
    // 科室的机器数量
    public final int m_size;
    // 科室的机器列表
    public ArrayList<Machine> machines;

    // 构造函数
    public Department(String name, int m_size){
        this.name = name;
        this.exam_items = new ArrayList<ExamItem>();
        this.m_size = m_size;
        this.machines = new ArrayList<Machine>();
    }

    // 添加检查项目
    public void addExamItem(ExamItem e){
        exam_items.add(e);
    }

    // 添加机器
    public void addMachine(Machine m){
        if (machines.size() < m_size){
            machines.add(m);
        }
        else{
            System.out.println("Err: addMachine oversize");
        }
    }

    // 根据患者的检查数量，随机选择一个检查项目
    public ExamItem getRandomExamItem(int exam_num, float random_float) {
        // 获得占比列表
        float[] rates = new float[exam_items.size()];
        for (int i = 0; i < rates.length; i++) {
            float[] demand_ratio = exam_items.get(i).demand_ratio;
            // 确保检查数量在最大检查数量范围内
            if (exam_num >= demand_ratio.length) exam_num = demand_ratio.length - 1;
            rates[i] = exam_items.get(i).demand_ratio[exam_num];
        }
        // 随机选择
        RandomChoice.organizeDistribution(rates);
        int index = RandomChoice.pickFromDistribution(rates, random_float);
        return exam_items.get(index);
    }
}
