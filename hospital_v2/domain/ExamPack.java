package hospital_v2.domain;

/*
 * Pack.java
 *
 * Created: 2024/1/22
 * By: Zhang Huan
 */

import java.util.ArrayList;

/**
 * 检查包（ExamPack）是仿真模型的基础类之一，表示一类具有共同特征的检查项目（ExamItem）。
 * 由于检查项目数量众多，因此通过检查包的形式组织检查项目。
 * 每一个检查包只有由一个科室执行。
 * **/

public class ExamPack {
    // 检查包的属性：名称、科室和出现频率（根据患者的检查数量）
    public final String name;
    public final Department department;
    public float[] demand_ratio;
    // 检查项目列表
    public ArrayList<ExamItem> items;

    //构造函数
    public ExamPack(String name, Department department, float[] ratio){
        this.name = name;
        this.department = department;
        this.demand_ratio = ratio;
        this.items = new ArrayList<ExamItem>();
    }

    // 添加检查项目
    public void addExamItem(ExamItem e){
        items.add(e);
    }
}
