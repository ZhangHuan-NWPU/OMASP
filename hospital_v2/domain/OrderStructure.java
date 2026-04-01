package hospital_v2.domain;


/*
 * OrderStructure.java
 *
 * Created: 2024/4/22
 * By: Zhang Huan
 */

import ec.util.RandomChoice;
import java.util.ArrayList;


/**
 * 检查单结构（OrderStructure）保存若干个预设的检查单结构。
 * 一个检查单结构类的实例包含了一个检查数量，以及若干个检查单结构。
 * 每一个检查单结构包括一份科室列表与一个占比。
 * **/

public class OrderStructure {
    // 检查数量
    public final int exam_num;
    // 预设的检查单列表（每一份检查单是一个科室列表）与相应的概率列表
    public ArrayList<Department[]> order_list;
    public ArrayList<Float> rate_list;

    // 构造函数
    public OrderStructure(int exam_num) {
        this.exam_num = exam_num;
        order_list = new ArrayList<>();
        rate_list = new ArrayList<>();
    }

    // 添加一份检查单
    public void addNewOrder(Department[] order, float rate) {
        // 假如检查单的检查数量不符合预设的数量，直接返回
        if (order.length != exam_num) return;
        // 添加
        order_list.add(order);
        rate_list.add(rate);
    }

    // 根据概率随机选择一份检查单
    public Department[] getRandomOrder(float random_float) {
        // 获得占比列表
        float[] rates = new float[rate_list.size()];
        for (int i = 0; i < rates.length; i++) {
            rates[i] = rate_list.get(i);
        }
        // 随机选择
        RandomChoice.organizeDistribution(rates);
        int index = RandomChoice.pickFromDistribution(rates, random_float);
        return order_list.get(index);
    }
}
