package hospital_v2;
import com.gurobi.gurobi.*;
import hospital_v2.domain.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DecisionMaker {
    // 所需参数
    public Patient patient;
    public ArrayList<DecisionSituation> DS_list;
    public Scenario scene;

    // gurobi环境
    public GRBEnv env;

    // 构造函数
    public DecisionMaker(Patient p, ArrayList<DecisionSituation> dsl, Scenario s, GRBEnv en){
        patient = p;
        DS_list = dsl;
        scene = s;
        env = en;
    }

    // 优化
    public void optimize(){
        try {
            // step0 初始化
            GRBModel model = new GRBModel(env);
            model.set(GRB.IntParam.OutputFlag, 0);

            // step1 变量输入
            HashMap<Option, GRBVar>[] Vars = new HashMap [patient.exam_num];
            for (int i = 0; i < patient.exam_num; i++) {
                Vars[i] = new HashMap<Option, GRBVar>();
                for (Option opt: DS_list.get(i).opt_list) {
                    Vars[i].put(opt, model.addVar(0.0, 1.0, 0.0, GRB.BINARY,
                                                  patient.exam_list[i].name + '-' + opt.getVarName()));
                }
            }

            // step2 约束输入
            // 约束（1）每一项检查只能分配一个资源。
            for (int i = 0; i < patient.exam_num; i++) {
                GRBLinExpr constr = new GRBLinExpr();
                for (Option opt: DS_list.get(i).opt_list) {
                    constr.addTerm(1.0, Vars[i].get(opt));
                }
                model.addConstr(constr, GRB.EQUAL, 1.0, patient.exam_list[i].name);
            }

            // 约束（2）每一个日期和时间段只能分配一项检查。
            GRBLinExpr[][] constr_set = new GRBLinExpr[scene.getTotal_period()][scene.getMax_slot()];
            for (int i = 0; i < patient.exam_num; i++) {
                for (Option opt: DS_list.get(i).opt_list) {
                    if (constr_set[opt.D][opt.S - 1] == null) {
                        constr_set[opt.D][opt.S - 1] = new GRBLinExpr();
                    }
                    else {
                        constr_set[opt.D][opt.S - 1].addTerm(1.0, Vars[i].get(opt));
                    }
                }
            }

            // step3 设置优化目标
            GRBLinExpr objective = new GRBLinExpr();
            for (int i = 0; i < patient.exam_num; i++) {
                for (Option opt: DS_list.get(i).opt_list) {
                    objective.addTerm(opt.value, Vars[i].get(opt));
                }
            }
            model.setObjective(objective, GRB.MAXIMIZE);

            // step4 优化和判断结果
            model.optimize();

            if (model.get(GRB.IntAttr.Status) == GRB.Status.OPTIMAL) {
                for (int i = 0; i < patient.exam_num; i++) {
                    for (Option opt : DS_list.get(i).opt_list) {
                        if (Vars[i].get(opt).get(GRB.DoubleAttr.X) == 1) {
                            DS_list.get(i).chosen_opt = opt;
                            break;
                        }
                    }
                }
            }

        }catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". " +
                    e.getMessage());
        }
    }
}
