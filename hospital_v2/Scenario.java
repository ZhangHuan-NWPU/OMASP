package hospital_v2;
import hospital_v2.domain.Department;
import hospital_v2.domain.ExamConstraints;
import hospital_v2.domain.OrderStructure;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Scenario.java
 *
 * Created: 2024/1/17
 * By: Zhang Huan
 */

public abstract class Scenario {
    public int warm_up_period;
    public int service_period;
    public int filling_period;
    public int total_period;

    public int patient_arr_lambda;
    public float[] exam_num_ratio;
    // 检查单结构信息，储存每一个检查数量下的检查单。
    public HashMap<Integer, OrderStructure> order_structure_list;

    public ArrayList<Department> dept_list;
    public ExamConstraints exam_constr;

    public int max_slot;

    public abstract void setUp();

    public abstract int getWarm_up_period();

    public abstract int getService_period();

    public abstract int getFilling_period();

    public abstract int getTotal_period();

    public abstract int getPatient_arr_lambda();

    public abstract float[] getExam_num_ratio();

    public abstract HashMap<Integer, OrderStructure> getOrder_structure_list();

    public abstract ArrayList<Department> getDept_list();

    public abstract ExamConstraints getExam_constr();

    public abstract int getMax_slot();
}
