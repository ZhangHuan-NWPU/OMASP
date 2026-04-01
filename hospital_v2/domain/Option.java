package hospital_v2.domain;

/*
 * Option.java
 *
 * Created: 2024/2/s6
 * By: Zhang Huan
 */

/**
 * 选项（Option）记录了一个检查资源对应的属性。
 * 通过调度规则进行评估。
 * **/

public class Option implements Comparable<Option>{
    // 资源
    public Resource resource;
    // 属性
    public int id;
    public float value = 0.0F;
    // 终端
    public int WL;            // 该检查项目的工作量
    public int EAD;           // 该检查项目的最早可行日期
    public int TWL;           // 该患者的检查的总工作量
    public int nE;            // 该患者的检查数量/权重
    public int MaxEAD;        // 该患者所有检查的最早可行日期中的最大值
    public int MinEAD;        // 该患者所有检查的最早可行日期中的最小值
    public int nM;            // 该科室的机器数量
    public int D;             // 日期
    public int S;             // 时间段
    public int TC;            // 该时间段的总容量
    public int RC;            // 该时间段的剩余容量
    public int MRC;           // 该机器在当天的剩余容量
    public int nOP;           // 该机器的积压患者数
    public int OWL;           // 该机器的积压患者的总工作量
    public float AWL;         // 该机器的检查项目的平均工作量

    public Option(Resource r,
                  int WL,            // 该检查项目的工作量
                  int EAD,           // 该检查项目的最早可行日期
                  int TWL,           // 该患者的检查的总工作量
                  int nE,            // 该患者的检查数量/权重
                  int MaxEAD,        // 该患者所有检查的最早可行日期中的最大值
                  int MinEAD,        // 该患者所有检查的最早可行日期中的最小值
                  int nM,            // 该科室的机器数量
                  int D,             // 日期
                  int S,             // 时间段
                  int TC,            // 该时间段的总容量
                  int RC,            // 该时间段的剩余容量
                  int MRC,           // 该机器在当天的剩余容量
                  int nOP,           // 该机器的积压患者数
                  int OWL,           // 该机器的积压患者的总工作量
                  float AWL) {       // 该机器的检查项目的平均工作量
        this.resource = r;
        this.WL = WL;
        this.EAD = EAD;
        this.TWL = TWL;
        this.nE = nE;
        this.MaxEAD = MaxEAD;
        this.MinEAD = MinEAD;
        this.nM = nM;
        this.D = D;
        this.S = S;
        this.TC = TC;
        this.RC = RC;
        this.MRC = MRC;
        this.nOP = nOP;
        this.OWL = OWL;
        this.AWL = AWL;
    }

    // 获取变量的名字
    public String getVarName(){
        return resource.machine.name + '-' + D + '-' + S;
    }

    @Override
    public int compareTo(Option o) {
        return Float.compare(this.value, o.value);
    }

    // 获取属性向量
    public String printOption() {
        return "(" + WL + ","
                   + EAD + ","
                   + TWL + ","
                   + nE + ","
                   + MaxEAD + ","
                   + MinEAD + ","
                   + nM + ","
                   + D + ","
                   + S + ","
                   + TC + ","
                   + RC + ","
                   + MRC + ","
                   + nOP + ","
                   + OWL + ","
                   + AWL + ","
                   + value + ")\n";
    }
}
