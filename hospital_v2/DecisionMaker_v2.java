package hospital_v2;

import hospital_v2.domain.Option;
import hospital_v2.domain.Patient;
import java.util.ArrayList;
import java.util.Collections;

public class DecisionMaker_v2 {
    public void optimize(Patient p, ArrayList<DecisionSituation> dsl, Scenario s) {
        ArrayList<Solution> solu_set = getSolutions(p.exam_num);
        for (Solution solu: solu_set) {
            for (int i: solu.array) {
                DecisionSituation ds = dsl.get(i);
                for (Option opt: ds.opt_list) {
                    if (solu.notInSolu(opt)) {
                        solu.chosen[i] = opt;
                        solu.obj += opt.value;
                        break;
                    }
                }
            }
        }
        Collections.sort(solu_set, Collections.reverseOrder());
        Solution best_solu = solu_set.getFirst();
        for (int i: best_solu.array) {
            dsl.get(i).chosen_opt = best_solu.chosen[i];
        }
    }

    public ArrayList<Solution> getSolutions(int num) {
        ArrayList<Solution> solu_set = new ArrayList<>();
        switch (num) {
            case 2: {
                int[] a_1 = {0, 1};
                solu_set.add(new Solution(a_1));
                int[] a_2 = {1, 0};
                solu_set.add(new Solution(a_2));
                break;
            }
            case 3: {
                int[] a_1 = {0, 1, 2};
                solu_set.add(new Solution(a_1));
                int[] a_2 = {0, 2, 1};
                solu_set.add(new Solution(a_2));
                int[] a_3 = {1, 0, 2};
                solu_set.add(new Solution(a_3));
                int[] a_4 = {1, 2, 0};
                solu_set.add(new Solution(a_4));
                int[] a_5 = {2, 0, 1};
                solu_set.add(new Solution(a_5));
                int[] a_6 = {2, 1, 0};
                solu_set.add(new Solution(a_6));
                break;
            }
            case 4: {
                int[] a_1 = {0, 1, 2, 3};
                solu_set.add(new Solution(a_1));
                int[] a_2 = {0, 1, 3, 2};
                solu_set.add(new Solution(a_2));
                int[] a_3 = {0, 2, 1, 3};
                solu_set.add(new Solution(a_3));
                int[] a_4 = {0, 2, 3, 1};
                solu_set.add(new Solution(a_4));
                int[] a_5 = {0, 3, 1, 2};
                solu_set.add(new Solution(a_5));
                int[] a_6 = {0, 3, 2, 1};
                solu_set.add(new Solution(a_6));
                int[] a_7 = {1, 0, 2, 3};
                solu_set.add(new Solution(a_7));
                int[] a_8 = {1, 0, 3, 2};
                solu_set.add(new Solution(a_8));
                int[] a_9 = {1, 2, 0, 3};
                solu_set.add(new Solution(a_9));
                int[] a_10 = {1, 2, 3, 0};
                solu_set.add(new Solution(a_10));
                int[] a_11 = {1, 3, 0, 2};
                solu_set.add(new Solution(a_11));
                int[] a_12 = {1, 3, 2, 0};
                solu_set.add(new Solution(a_12));
                int[] a_13 = {2, 0, 1, 3};
                solu_set.add(new Solution(a_13));
                int[] a_14 = {2, 0, 3, 1};
                solu_set.add(new Solution(a_14));
                int[] a_15 = {2, 1, 0, 3};
                solu_set.add(new Solution(a_15));
                int[] a_16 = {2, 1, 3, 0};
                solu_set.add(new Solution(a_16));
                int[] a_17 = {2, 3, 0, 1};
                solu_set.add(new Solution(a_17));
                int[] a_18 = {2, 3, 1, 0};
                solu_set.add(new Solution(a_18));
                int[] a_19 = {3, 0, 1, 2};
                solu_set.add(new Solution(a_19));
                int[] a_20 = {3, 0, 2, 1};
                solu_set.add(new Solution(a_20));
                int[] a_21 = {3, 1, 0, 2};
                solu_set.add(new Solution(a_21));
                int[] a_22 = {3, 1, 2, 0};
                solu_set.add(new Solution(a_22));
                int[] a_23 = {3, 2, 0, 1};
                solu_set.add(new Solution(a_23));
                int[] a_24 = {3, 2, 1, 0};
                solu_set.add(new Solution(a_24));
                break;
            }
            default: {
                System.out.println("Invalid exam num!");
            }
        }
        return solu_set;
    }

    private class Solution implements Comparable<Solution>{
        public int[] array;
        public Option[] chosen;
        public float obj = 0.0F;

        public Solution(int[] arr) {
            array = arr;
            chosen = new Option[arr.length];
        }

        public boolean notInSolu(Option new_opt){
            boolean flag = true;
            for (Option opt: chosen) {
                if (opt != null && new_opt.D == opt.D && new_opt.S == opt.S) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        @Override
        public int compareTo(Solution s) {
            return Float.compare(this.obj, s.obj);
        }
    }
}

