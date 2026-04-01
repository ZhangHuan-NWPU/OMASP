package hospital_v2.domain;

public class VarKey {
    // 存储属性
    public Exam exam;
    public Resource res;

    // 构造函数
    public VarKey(Exam e, Resource r) {
        exam = e;
        res = r;
    }

    // 获取变量的名字
    public String getVarName(){
        return exam.name + '-' + res.machine.name + '-' + res.date + '-' + res.slot;
    }
}
