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

    public double food_biased_weight(int cal, int fats, int prot, int carbs, double hyd, double adher) {
        return 0.3*cal + 0.1*fats + 0.15*prot + 0.15*carbs + 0.2*hyd + 0.1*adher;
    }

    public double workout_biased_weight(int sets, int reps, int minutes) {
        return 0.3*sets + 0.3*reps + 0.4*minutes;
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

        double ind_cal = 0.0;
        double ind_fats = 0.0;
        double ind_prot = 0.0;
        double ind_carbs = 0.0;
        double ind_hyd = 0.0;

        int total_cal = 0;
        int total_fats = 0;
        int total_prot = 0;
        int total_carbs = 0;
        double total_hyd = 0;
        double adher = 0.0;

        double total_biased_weight = 0.0;
        for (int i=0; i<7; i++) {

            double hyd;
            total_cal += actual.get(i).getCalories();
            total_fats += actual.get(i).getFats();
            total_prot += actual.get(i).getProteins();
            total_carbs += actual.get(i).getCarbs();
            total_hyd += actual.get(i).getWaterIntake();
            ind_cal += func_Q(expected.get(i).getCalories(), actual.get(i).getCalories());
            ind_fats += func_Q(expected.get(i).getFats(), actual.get(i).getFats());
            ind_prot += func_Q(expected.get(i).getProteins(), actual.get(i).getProteins());
            ind_carbs += func_Q(expected.get(i).getCarbs(), actual.get(i).getCarbs());
            adher += adherence_calculator(expected.get(i).getFoodList(), actual.get(i).getFoodList());
            ind_hyd += hyd_score(actual.get(i).getWaterIntake(), expected.get(i).getWaterIntake());


        }
        total_cal /= 7;
        total_fats /= 7;
        total_prot /= 7;
        total_carbs /= 7;
        adher /= 7;
        ind_cal /= 7;
        ind_fats /= 7;
        ind_prot /= 7;
        ind_carbs /= 7;
        ind_hyd /= 7;

        total_hyd /= 7;
        total_biased_weight = food_biased_weight(total_cal, total_fats, total_prot, total_carbs, total_hyd, adher);

        System.out.println("Individual scores");
        System.out.printf("Calories: %.2f, Fats: %.2f, Proteins: %.2f, Carbs: %.2f, Water Intake: %.2f\n", ind_cal, ind_fats, ind_prot, ind_carbs, ind_hyd);
        System.out.printf("Overall score: %.2f", total_biased_weight);




    }

    public void conductWorkoutAnalysis(ArrayList<ArrayList<Workout>> expected, ArrayList<ArrayList<Workout>> actual) {

        int total_sets =0;
        int total_reps =0;
        int total_minutes =0;

        int final_sets = 0;
        int final_reps = 0;
        int final_minutes = 0;


        double ind_sets = 0.0;
        double ind_reps = 0.0;
        double ind_minutes = 0.0;
        double adherence = 0.0;

        for (int i=0; i<7; i++) {

            total_sets =0;
            total_reps =0;
            total_minutes =0;

            ArrayList<String> expected_wk = new ArrayList<>();
            ArrayList<String> actual_wk = new ArrayList<>();
            int size = Math.max(expected.get(i).size(), actual.get(i).size());
            for (int j=0; j<size; j++) {
                expected_wk.add(expected.get(i).get(j).getName());
                actual_wk.add(actual.get(i).get(j).getName());

                ind_sets += func_Q(expected.get(i).get(j).getSets(), actual.get(i).get(j).getSets());
                ind_reps += func_Q(expected.get(i).get(j).getReps(), actual.get(i).get(j).getReps());
                ind_minutes += func_Q(expected.get(i).get(j).getMinutes(), actual.get(i).get(j).getMinutes());

                total_sets += expected.get(i).get(j).getSets();
                total_reps += actual.get(i).get(j).getReps();
                total_minutes += expected.get(i).get(j).getMinutes();
            }
            ind_sets /= size;
            ind_reps /= size;
            ind_minutes /= size;

            total_sets /= size;
            total_reps /= size;
            total_minutes /= size;

            final_sets += total_sets;
            final_reps += total_reps;
            final_minutes += total_minutes;

            adherence = adherence_calculator(expected_wk, actual_wk);

            System.out.println("Day 1 individual scores: ");
            System.out.printf("Sets: %.2f, Reps: %.2f, Minutes: %.2f, Adherence: %.2f\n", ind_sets, ind_reps, ind_minutes, adherence );

        }

        double total_biased_weight = workout_biased_weight(final_sets, final_reps, final_minutes);
        System.out.printf("Overall biased score: %.2f", total_biased_weight);

    }
