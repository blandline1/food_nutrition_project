import java.lang.reflect.Array;
import java.util.ArrayList;

public class Analysis {

    private static Analysis instance = new Analysis();

    private Analysis() {

    }

    public static Analysis getInstance() {
        return instance;
    }

    public double func_Q(int xi, int xa) {
        return ((double) (xi - Math.abs(xa - xi)) /xi) * 100;
    }

    public double food_biased_weight(int cal, int fats, int prot, int carbs, int hyd, int adher) {
        return 0.3*cal + 0.1*fats + 0.15*prot + 0.15*carbs + 0.2*hyd + 0.1*adher;
    }

    public double workout_biased_weight(int sets, int reps, int weights) {
        return 0.3*sets + 0.3*reps + 0.4*weights;
    }

    public double adherence_calculator(ArrayList<String> expected, ArrayList<String> actual) {
        int times_expected = 0;
        for (String s : actual) {
            if (expected.contains(s)) {
                times_expected++;
            }
        }
        return ((double) times_expected /expected.size()) * 100;
    }

    public double hyd_score(int x, int cap) {
        if(x>=cap) {
            return 100;

        }
        else {
            return (double) x/cap * 100;
        }
    }

    public void conductFoodAnalysis(Member mb) {

    }





}
