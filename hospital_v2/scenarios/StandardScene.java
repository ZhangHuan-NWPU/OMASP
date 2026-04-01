package hospital_v2.scenarios;

/*
 * StandardScene.java
 *
 * Created: 2024/4/21
 * By: Zhang Huan
 */

import hospital_v2.Scenario;
import hospital_v2.domain.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 场景（Scenario）存储仿真模型需要的信息。
 * 一个场景类的实例包含了各种分布的参数和仿真模型的配置。
 * 一个场景表示一个不同的门诊场景。
 * **/

public class StandardScene extends Scenario {
    // 时间参数
    // 仿真时长，包括：预热期、统计期和收尾期
    public int warm_up_period = 5;
    public int service_period = 10;
    public int filling_period = 20;

    // 仿真参数
    // 单日患者到达数量
    // 354 443 531
    // 234 293 352
    public int patient_arr_lambda;
    // 检查需求信息，下标就是患者的检查数量，因此第一个数一定是0。例如：exam_num_rate[x]表示患者检查数量为x的概率。
    // {0, 0.80F, 0.15F, 0.05F, 0.00F}
    // {0, 0.50F, 0.25F, 0.15F, 0.10F}
    public float[] exam_num_ratio;
    // 检查单结构信息，储存每一个检查数量下的检查单。
    public HashMap<Integer, OrderStructure> order_structure_list;

    // 优化参数
    // 总天数与单日最大时间段数量
    public int max_slot;
    public int total_period = 35;

    // 资源参数
    // 科室列表与检查约束（通过setUp函数）
    public ArrayList<Department> dept_list;
    public ExamConstraints exam_constr;

    public StandardScene(int pal, float[] enr) {
        patient_arr_lambda = pal;
        exam_num_ratio = enr;
    }


    // 设置科室
    public void setUp() {
        // 新建科室列表
        ArrayList<Department> new_dept_list = new ArrayList<>();
        // 科室1：CT，包括15台机器与1种检查项目
        Department CT = new Department("CT", 15);
        // CT的检查项目
        CT.addExamItem(new ExamItem("E_CT01", CT, 1, new float[] {0, 1.0F, 1.0F, 1.0F, 1.0F}));

        // CT的机器
        Machine M_CT01 = new Machine("M_CT01", CT, 1.0F);
        Machine M_CT02 = new Machine("M_CT02", CT, 1.0F);
        Machine M_CT03 = new Machine("M_CT03", CT, 1.0F);
        Machine M_CT04 = new Machine("M_CT04", CT, 1.0F);
        Machine M_CT05 = new Machine("M_CT05", CT, 1.0F);
        Machine M_CT06 = new Machine("M_CT06", CT, 1.0F);
        Machine M_CT07 = new Machine("M_CT07", CT, 1.0F);
        Machine M_CT08 = new Machine("M_CT08", CT, 1.0F);
        Machine M_CT09 = new Machine("M_CT09", CT, 1.0F);
        Machine M_CT10 = new Machine("M_CT10", CT, 1.0F);
        Machine M_CT11 = new Machine("M_CT11", CT, 1.0F);
        Machine M_CT12 = new Machine("M_CT12", CT, 1.0F);
        Machine M_CT13 = new Machine("M_CT13", CT, 1.0F);
        Machine M_CT14 = new Machine("M_CT14", CT, 1.0F);
        Machine M_CT15 = new Machine("M_CT15", CT, 1.0F);

        // 计划
        int[] ids1 = {2, 3, 5};
        int[] sts1 = {480, 600, 780};
        int[] ets1 = {600, 720, 900};
        int[] sizes1 = {5, 5, 3};
        int[] ids2 = {2, 3};
        int[] sts2 = {480, 600};
        int[] ets2 = {600, 720};
        int[] sizes2  = {5, 5};
        max_slot = 5;

        M_CT01.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT01.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT02.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT02.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT03.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT03.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT04.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT04.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT05.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT05.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT06.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT06.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT07.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT07.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT08.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT08.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT09.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT09.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT10.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT10.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT11.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT11.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT12.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT12.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT13.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT13.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT14.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT14.setWeekendPlan(2, ids2, sts2, ets2, sizes2);
        M_CT15.setWeekdayPlan(3, ids1, sts1, ets1, sizes1);
        M_CT15.setWeekendPlan(2, ids2, sts2, ets2, sizes2);

        // 添加机器
        CT.addMachine(M_CT01);
        CT.addMachine(M_CT02);
        CT.addMachine(M_CT03);
        CT.addMachine(M_CT04);
        CT.addMachine(M_CT05);
        CT.addMachine(M_CT06);
        CT.addMachine(M_CT07);
        CT.addMachine(M_CT08);
        CT.addMachine(M_CT09);
        CT.addMachine(M_CT10);
        CT.addMachine(M_CT11);
        CT.addMachine(M_CT12);
        CT.addMachine(M_CT13);
        CT.addMachine(M_CT14);
        CT.addMachine(M_CT15);
        // 添加科室
        new_dept_list.add(CT);

        // 科室2：DR，包括5台机器与4种检查项目
        Department DR = new Department("DR", 10);
        // 检查包
        DR.addExamItem(new ExamItem("E_DR01", DR, 1, new float[] {0, 0.80F, 0.65F, 0.65F, 0.65F}));
        DR.addExamItem(new ExamItem("E_DR02", DR, 2, new float[] {0, 0.15F, 0.25F, 0.25F, 0.25F}));
        DR.addExamItem(new ExamItem("E_DR03", DR, 3, new float[] {0, 0.05F, 0.05F, 0.05F, 0.05F}));
        DR.addExamItem(new ExamItem("E_DR04", DR, 4, new float[] {0, 0.00F, 0.05F, 0.05F, 0.05F}));
        // 机器
        Machine M_DR01 = new Machine("M_DR01", DR, 1.315F);
        Machine M_DR02 = new Machine("M_DR02", DR, 1.315F);
        Machine M_DR03 = new Machine("M_DR03", DR, 1.315F);
        Machine M_DR04 = new Machine("M_DR04", DR, 1.315F);
        Machine M_DR05 = new Machine("M_DR05", DR, 1.315F);
        Machine M_DR06 = new Machine("M_DR06", DR, 1.315F);
        Machine M_DR07 = new Machine("M_DR07", DR, 1.315F);
        Machine M_DR08 = new Machine("M_DR08", DR, 1.315F);
        Machine M_DR09 = new Machine("M_DR09", DR, 1.315F);
        Machine M_DR10 = new Machine("M_DR10", DR, 1.315F);
        // 计划
        int[] ids3 = {2, 3, 5};
        int[] sts3 = {480, 600, 780};
        int[] ets3 = {600, 720, 900};
        int[] sizes3 = {4, 4, 2};
        int[] ids4 = {2, 3};
        int[] sts4 = {480, 600};
        int[] ets4 = {600, 720};
        int[] sizes4  = {4, 4};
        // 设置计划
        M_DR01.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR01.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR02.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR02.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR03.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR03.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR04.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR04.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR05.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR05.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR06.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR06.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR07.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR07.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR08.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR08.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR09.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR09.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        M_DR10.setWeekdayPlan(3, ids3, sts3, ets3, sizes3);
        M_DR10.setWeekendPlan(2, ids4, sts4, ets4, sizes4);
        // 添加机器
        DR.addMachine(M_DR01);
        DR.addMachine(M_DR02);
        DR.addMachine(M_DR03);
        DR.addMachine(M_DR04);
        DR.addMachine(M_DR05);
        DR.addMachine(M_DR06);
        DR.addMachine(M_DR07);
        DR.addMachine(M_DR08);
        DR.addMachine(M_DR09);
        DR.addMachine(M_DR10);
        // 添加科室
        new_dept_list.add(DR);

        // 科室3：MR，包括10台机器与3种检查项目
        Department MR = new Department("MR", 10);
        // 检查包
        MR.addExamItem(new ExamItem("E_MR01", MR, 2, new float[] {0, 0.70F, 0.85F, 0.85F, 0.85F}));
        MR.addExamItem(new ExamItem("E_MR02", MR, 3, new float[] {0, 0.25F, 0.10F, 0.10F, 0.10F}));
        MR.addExamItem(new ExamItem("E_MR03", MR, 4, new float[] {0, 0.05F, 0.05F, 0.05F, 0.05F}));
        // 机器
        Machine M_MR01 = new Machine("M_MR01", MR, 2.311F);
        Machine M_MR02 = new Machine("M_MR02", MR, 2.311F);
        Machine M_MR03 = new Machine("M_MR03", MR, 2.311F);
        Machine M_MR04 = new Machine("M_MR04", MR, 2.311F);
        Machine M_MR05 = new Machine("M_MR05", MR, 2.311F);
        Machine M_MR06 = new Machine("M_MR06", MR, 2.311F);
        Machine M_MR07 = new Machine("M_MR07", MR, 2.311F);
        Machine M_MR08 = new Machine("M_MR08", MR, 2.311F);
        Machine M_MR09 = new Machine("M_MR09", MR, 2.311F);
        Machine M_MR10 = new Machine("M_MR10", MR, 2.311F);
        // 计划
        int[] ids5 = {2, 3, 5};
        int[] sts5 = {480, 600, 780};
        int[] ets5 = {600, 720, 900};
        int[] sizes5 = {5, 5, 5};
        int[] ids6 = {2, 3};
        int[] sts6 = {480, 600};
        int[] ets6 = {600, 720};
        int[] sizes6  = {5, 5};
        // 设置计划
        M_MR01.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR01.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR02.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR02.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR03.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR03.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR04.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR04.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR05.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR05.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR06.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR06.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR07.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR07.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR08.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR08.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR09.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR09.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        M_MR10.setWeekdayPlan(3, ids5, sts5, ets5, sizes5);
        M_MR10.setWeekendPlan(2, ids6, sts6, ets6, sizes6);
        // 添加机器
        MR.addMachine(M_MR01);
        MR.addMachine(M_MR02);
        MR.addMachine(M_MR03);
        MR.addMachine(M_MR04);
        MR.addMachine(M_MR05);
        MR.addMachine(M_MR06);
        MR.addMachine(M_MR07);
        MR.addMachine(M_MR08);
        MR.addMachine(M_MR09);
        MR.addMachine(M_MR10);
        // 添加科室
        new_dept_list.add(MR);

        // 科室4：US，包括20台机器与4种检查项目
        Department US = new Department("US", 15);
        // 检查包
        US.addExamItem(new ExamItem("E_US01", US, 1, new float[] {0, 0.70F, 0.40F, 0.40F, 0.40F}));
        US.addExamItem(new ExamItem("E_US02", US, 2, new float[] {0, 0.15F, 0.20F, 0.20F, 0.20F}));
        US.addExamItem(new ExamItem("E_US03", US, 3, new float[] {0, 0.10F, 0.20F, 0.20F, 0.20F}));
        US.addExamItem(new ExamItem("E_US03", US, 4, new float[] {0, 0.05F, 0.20F, 0.20F, 0.20F}));
        // 机器
        Machine M_US01 = new Machine("M_US01", US, 1.618F);
        Machine M_US02 = new Machine("M_US02", US, 1.618F);
        Machine M_US03 = new Machine("M_US03", US, 1.618F);
        Machine M_US04 = new Machine("M_US04", US, 1.618F);
        Machine M_US05 = new Machine("M_US05", US, 1.618F);
        Machine M_US06 = new Machine("M_US06", US, 1.618F);
        Machine M_US07 = new Machine("M_US07", US, 1.618F);
        Machine M_US08 = new Machine("M_US08", US, 1.618F);
        Machine M_US09 = new Machine("M_US09", US, 1.618F);
        Machine M_US10 = new Machine("M_US10", US, 1.618F);
        Machine M_US11 = new Machine("M_US11", US, 1.618F);
        Machine M_US12 = new Machine("M_US12", US, 1.618F);
        Machine M_US13 = new Machine("M_US13", US, 1.618F);
        Machine M_US14 = new Machine("M_US14", US, 1.618F);
        Machine M_US15 = new Machine("M_US15", US, 1.618F);
        // 计划
        int[] ids7 = {2, 3, 5, 6, 7};
        int[] sts7 = {480, 600, 780, 900, 1140};
        int[] ets7 = {600, 720, 900, 1080, 1260};
        int[] sizes7 = {4, 4, 4, 4, 2};
        int[] ids8 = {2, 5};
        int[] sts8 = {480, 780};
        int[] ets8 = {600, 900};
        int[] sizes8  = {4, 4};
        // 设置计划
        M_US01.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US01.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US02.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US02.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US03.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US03.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US04.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US04.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US05.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US05.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US06.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US06.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US07.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US07.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US08.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US08.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US09.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US09.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US10.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US10.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US11.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US11.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US12.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US12.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US13.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US13.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US14.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US14.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        M_US15.setWeekdayPlan(5, ids7, sts7, ets7, sizes7);
        M_US15.setWeekendPlan(2, ids8, sts8, ets8, sizes8);
        // 添加机器
        US.addMachine(M_US01);
        US.addMachine(M_US02);
        US.addMachine(M_US03);
        US.addMachine(M_US04);
        US.addMachine(M_US05);
        US.addMachine(M_US06);
        US.addMachine(M_US07);
        US.addMachine(M_US08);
        US.addMachine(M_US09);
        US.addMachine(M_US10);
        US.addMachine(M_US11);
        US.addMachine(M_US12);
        US.addMachine(M_US13);
        US.addMachine(M_US14);
        US.addMachine(M_US15);
        // 添加科室
        new_dept_list.add(US);

        // 科室5：UC，包括5台机器与1种检查项目
        Department UC = new Department("UC", 5);
        // 检查包
        UC.addExamItem(new ExamItem("E_UC01", UC, 1, new float[] {0, 1.0F, 1.0F, 1.0F, 1.0F}));
        // 机器
        Machine M_UC01 = new Machine("M_UC01", UC, 1.0F);
        Machine M_UC02 = new Machine("M_UC02", UC, 1.0F);
        Machine M_UC03 = new Machine("M_UC03", UC, 1.0F);
        Machine M_UC04 = new Machine("M_UC04", UC, 1.0F);
        Machine M_UC05 = new Machine("M_UC05", UC, 1.0F);
        // 计划
        int[] ids9 = {2, 5, 6};
        int[] sts9 = {480, 780, 900};
        int[] ets9 = {600, 900, 1020};
        int[] sizes9 = {6, 6, 3};
        int[] ids10 = {2, 5};
        int[] sts10 = {480, 780};
        int[] ets10 = {600, 900};
        int[] sizes10  = {6, 6};
        // 设置计划
        M_UC01.setWeekdayPlan(3, ids9, sts9, ets9, sizes9);
        M_UC01.setWeekendPlan(2, ids10, sts10, ets10, sizes10);
        M_UC02.setWeekdayPlan(3, ids9, sts9, ets9, sizes9);
        M_UC02.setWeekendPlan(2, ids10, sts10, ets10, sizes10);
        M_UC03.setWeekdayPlan(3, ids9, sts9, ets9, sizes9);
        M_UC03.setWeekendPlan(2, ids10, sts10, ets10, sizes10);
        M_UC04.setWeekdayPlan(3, ids9, sts9, ets9, sizes9);
        M_UC04.setWeekendPlan(2, ids10, sts10, ets10, sizes10);
        M_UC05.setWeekdayPlan(3, ids9, sts9, ets9, sizes9);
        M_UC05.setWeekendPlan(2, ids10, sts10, ets10, sizes10);
        // 添加机器
        UC.addMachine(M_UC01);
        UC.addMachine(M_UC02);
        UC.addMachine(M_UC03);
        UC.addMachine(M_UC04);
        UC.addMachine(M_UC05);
        // 添加科室
        new_dept_list.add(UC);

        // 科室6：ES，包括5台机器与3种检查项目
        Department ES = new Department("ES", 5);
        // 检查包
        ES.addExamItem(new ExamItem("E_ES01", ES, 1, new float[] {0, 0.60F, 0.75F, 0.75F, 0.75F}));
        ES.addExamItem(new ExamItem("E_ES02", ES, 2, new float[] {0, 0.35F, 0.25F, 0.25F, 0.25F}));
        ES.addExamItem(new ExamItem("E_ES03", ES, 3, new float[] {0, 0.05F, 0.00F, 0.00F, 0.00F}));
        // 机器
        Machine M_ES01 = new Machine("M_ES01", ES, 1.398F);
        Machine M_ES02 = new Machine("M_ES02", ES, 1.398F);
        Machine M_ES03 = new Machine("M_ES03", ES, 1.398F);
        Machine M_ES04 = new Machine("M_ES04", ES, 1.398F);
        Machine M_ES05 = new Machine("M_ES05", ES, 1.398F);
        // 计划
        int[] ids11 = {1, 2, 3, 4};
        int[] sts11 = {450, 480, 600, 720};
        int[] ets11 = {480, 600, 720, 780};
        int[] sizes11 = {2, 5, 5, 3};
        int[] ids12 = {2};
        int[] sts12 = {480};
        int[] ets12 = {600};
        int[] sizes12  = {5};
        // 设置计划
        M_ES01.setWeekdayPlan(4, ids11, sts11, ets11, sizes11);
        M_ES01.setWeekendPlan(1, ids12, sts12, ets12, sizes12);
        M_ES02.setWeekdayPlan(4, ids11, sts11, ets11, sizes11);
        M_ES02.setWeekendPlan(1, ids12, sts12, ets12, sizes12);
        M_ES03.setWeekdayPlan(4, ids11, sts11, ets11, sizes11);
        M_ES03.setWeekendPlan(1, ids12, sts12, ets12, sizes12);
        M_ES04.setWeekdayPlan(4, ids11, sts11, ets11, sizes11);
        M_ES04.setWeekendPlan(1, ids12, sts12, ets12, sizes12);
        M_ES05.setWeekdayPlan(4, ids11, sts11, ets11, sizes11);
        M_ES05.setWeekendPlan(1, ids12, sts12, ets12, sizes12);
        // 添加机器
        ES.addMachine(M_ES01);
        ES.addMachine(M_ES02);
        ES.addMachine(M_ES03);
        ES.addMachine(M_ES04);
        ES.addMachine(M_ES05);
        // 添加科室
        new_dept_list.add(ES);

        // 加入属性
        this.dept_list = new_dept_list;

        // 建立检查单结构
        order_structure_list = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            order_structure_list.put(i, new OrderStructure(i));
        }

        // 取出检查数量为1的检查单结构
        order_structure_list.get(1).addNewOrder(new Department[] {US}, 0.4019F);
        order_structure_list.get(1).addNewOrder(new Department[] {CT}, 0.3236F);
        order_structure_list.get(1).addNewOrder(new Department[] {MR}, 0.0734F);
        order_structure_list.get(1).addNewOrder(new Department[] {UC}, 0.0803F);
        order_structure_list.get(1).addNewOrder(new Department[] {DR}, 0.0648F);
        order_structure_list.get(1).addNewOrder(new Department[] {ES}, 0.0558F);
        // 取出检查数量为2的检查单结构
        order_structure_list.get(2).addNewOrder(new Department[] {CT, US}, 0.2453F);
        order_structure_list.get(2).addNewOrder(new Department[] {UC, US}, 0.1870F);
        order_structure_list.get(2).addNewOrder(new Department[] {CT, MR}, 0.1598F);
        order_structure_list.get(2).addNewOrder(new Department[] {CT, UC}, 0.0926F);
        order_structure_list.get(2).addNewOrder(new Department[] {CT, DR}, 0.0704F);
        order_structure_list.get(2).addNewOrder(new Department[] {MR, US}, 0.0637F);
        order_structure_list.get(2).addNewOrder(new Department[] {ES, US}, 0.0524F);
        order_structure_list.get(2).addNewOrder(new Department[] {CT, ES}, 0.0472F);
        order_structure_list.get(2).addNewOrder(new Department[] {DR, US}, 0.0453F);
        order_structure_list.get(2).addNewOrder(new Department[] {DR, MR}, 0.0362F);
        // 取出检查数量为3的检查单结构
        order_structure_list.get(3).addNewOrder(new Department[] {CT, UC, US}, 0.3686F);
        order_structure_list.get(3).addNewOrder(new Department[] {UC, MR, US}, 0.2777F);
        order_structure_list.get(3).addNewOrder(new Department[] {MR, UC, US}, 0.0860F);
        order_structure_list.get(3).addNewOrder(new Department[] {CT, ES, US}, 0.0718F);
        order_structure_list.get(3).addNewOrder(new Department[] {CT, MR, UC}, 0.0539F);
        order_structure_list.get(3).addNewOrder(new Department[] {ES, UC, US}, 0.0449F);
        order_structure_list.get(3).addNewOrder(new Department[] {CT, DR, MR}, 0.0395F);
        order_structure_list.get(3).addNewOrder(new Department[] {DR, UC, US}, 0.0239F);
        order_structure_list.get(3).addNewOrder(new Department[] {CT, DR, US}, 0.0215F);
        order_structure_list.get(3).addNewOrder(new Department[] {CT, MR, ES}, 0.0121F);
        // 取出检查数量为4的检查单结构
        order_structure_list.get(4).addNewOrder(new Department[] {CT, MR, UC, US}, 0.5744F);
        order_structure_list.get(4).addNewOrder(new Department[] {CT, ES, UC, US}, 0.2891F);
        order_structure_list.get(4).addNewOrder(new Department[] {CT, MR, ES, US}, 0.0670F);
        order_structure_list.get(4).addNewOrder(new Department[] {CT, DR, UC, US}, 0.0422F);
        order_structure_list.get(4).addNewOrder(new Department[] {CT, DR, MR, US}, 0.0273F);
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
