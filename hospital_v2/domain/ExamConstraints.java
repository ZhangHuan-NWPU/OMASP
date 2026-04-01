package hospital_v2.domain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * TestScene.java
 *
 * Created: 2024/1/24
 * By: Zhang Huan
 */

/**
 * 检查包之间具有多种业务约束，包括：顺序、排斥和紧邻。
 * 顺序：患者的两项检查之间必须具有先后顺序。
 * 排斥：患者的两项检查之间必须存在一定的间隔。
 * 紧邻：患者的两项检查之间的间隔必须低于固定的天数。
 * **/

public class ExamConstraints {
    // 业务约束列表
    public ArrayList<Map<String, Object>> constraints = new ArrayList<>();

    // 添加业务约束
    public void addConstr(ExamPack p1, ExamPack p2, int type, int param){
        Map<String, Object> c = new HashMap<String, Object>();
        c.put("MAIN_PACK", p1);
        c.put("SUB_PACK", p2);
        if (type == 1){
            c.put("CONSTR", "SEQ");
        }
        else if (type == 2){
            c.put("CONSTR", "REPEL");
        }
        else if (type == 3){
            c.put("CONSTR", "NEAR");
        }
        else{
            c.put("CONSTR", "REPEL");
            System.out.println("Err: addConstr type");
        }
        c.put("PARAM", param);
    }
}
