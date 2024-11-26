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

    public double hyd_score(double x, double cap) {
        if(x>=cap) {
            return 100;

        }
        else {
            return (double) x/cap * 100;
        }
    }

    public void conductFoodAnalysis(ArrayList<Food> expected, ArrayList<Food> actual) {

        double total_cal = 0.0;
        double total_fats = 0.0;
        double total_prot = 0.0;
        double total_carbs = 0.0;
        double total_hyd = 0.0;
        double adher = 0.0;
        double total_biased_weight = 0.0;
        for (int i=0; i<7; i++) {
            int cal, fats, prot, carbs;
            double hyd;
            cal = actual.get(i).getCalories();
            fats = actual.get(i).getFats();
            prot = actual.get(i).getProteins();
            carbs = actual.get(i).getCarbs();
            hyd = actual.get(i).getWaterIntake();
            total_cal += func_Q(expected.get(i).getCalories(), actual.get(i).getCalories());
            total_fats += func_Q(expected.get(i).getFats(), actual.get(i).getFats());
            total_prot += func_Q(expected.get(i).getProteins(), actual.get(i).getProteins());
            total_carbs += func_Q(expected.get(i).getCarbs(), actual.get(i).getCarbs());
            adher = adherence_calculator(expected.get(i).getFoodList(), actual.get(i).getFoodList());
            total_hyd += hyd_score(actual.get(i).getWaterIntake(), expected.get(i).getWaterIntake());
            total_biased_weight += food_biased_weight(cal, fats, prot, carbs, (int)hyd, (int)adher);

        }
        total_cal /= 7;
        total_fats /= 7;
        total_prot /= 7;
        total_carbs /= 7;

        total_hyd /= 7;
        total_biased_weight /= 7;

        System.out.println("Individual scores");
        System.out.printf("Calories: %.2f, Fats: %.2f, Proteins: %.2f, Carbs: %.2f, Water Intake: %.2f\n", total_cal, total_fats, total_prot, total_carbs, total_hyd);
        System.out.printf("Overall score: %.2f", total_biased_weight);




    }





}
