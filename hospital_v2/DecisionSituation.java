package hospital_v2;
import ec.EvolutionState;
import ec.app.MultiAppScheduling._FloatData;
import ec.app.MultiAppScheduling._Problem;
import ec.gp.GPIndividual;
import hospital_v2.domain.Option;

import java.util.ArrayList;
import java.util.Collections;

/*
 * DecisionSituation.java
 *
 * Created: 2024/1/17
 * By: Zhang Huan
 */

/**
 * 决策情景（DecisionSituation）是某患者的一项检查可以使用的检查资源及其参数的集合。
 * 决策情景通过调用遗传规划个体进行评估，从而作出选择。
 * **/

public class DecisionSituation {
    // 选项清单
    public ArrayList<Option> opt_list = new ArrayList<>();

    // 选中的选项
    public Option chosen_opt = null;

    // 添加一个新的选项
    public void addNewOption(Option opt){
        opt_list.add(opt);
    }

    // 更新所有检查的最早可行日期中的最大值和最小值
    public void updateMaxEADAndMinEAD(int max, int min){
        for (Option opt: opt_list){
            opt.MinEAD = min;
            opt.MaxEAD = max;
        }
    }

    // 评估
    public void evaluate(final EvolutionState state,
                         final int threadnum,
                         final GPIndividual ind,
                         _FloatData input,
                         _Problem problem){

        for (Option option: opt_list){
            problem.current_option = option;
            ind.trees[0].child.eval(state, threadnum, input, problem.stack, ind, problem);
            option.value = input.x;
        }
        Collections.sort(opt_list, Collections.reverseOrder());
        if (!opt_list.isEmpty()) chosen_opt = opt_list.getFirst();
    }

    // 导出决策行为
    public double[] get_decision() {
        double d_min = 999;
        double d_max = 0;
        double s_min = 999;
        double s_max = 0;
        for (Option option: opt_list) {
            if (option.D < d_min) d_min = option.D;
            if (option.D > d_max) d_max = option.D;
            if (option.S < s_min) s_min = option.S;
            if (option.S > s_max) s_max = option.S;
        }
        if (chosen_opt != null) {
            double res1 = 0.0;
            double res2 = 0.0;
            // if (d_max == d_min) res1 = 0.0;
            // if ((chosen_opt.D - chosen_opt.MaxEAD) < ((chosen_opt.S - chosen_opt.MRC) / Math.max(1, chosen_opt.OWL))) res1 = 1.0;
            if ((chosen_opt.S - 2) <= (-chosen_opt.TWL)) res2 = 1.0;
            if (d_max > d_min) res1 = (chosen_opt.D - d_min) / (d_max - d_min);
            if (s_max > s_min) res2 = (chosen_opt.S - s_min) / (s_max - s_min);
            return new double[] {chosen_opt.nE, res1, res2};
        }
        else {
            return new double[] {0.0, 0.0, 0.0};
        }
    }
}
