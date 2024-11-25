import java.util.ArrayList;

public class Analysis {

    private static Analysis instance = new Analysis();

    private Analysis() {

    }

    public static Analysis getInstance() {
        return instance;
    }

    public void conductWorkoutAnalysis(Member mem, ArrayList<ArrayList<Workout>> wk) {
        Planner planner = Planner.getInstance();
        AllPlans ap = planner.getPlan(mem);
        ArrayList<ArrayList<Workout>> idealPlan = ap.getWorkoutPlan();
        for (int i = 0; i < idealPlan.size(); i++) {
            int times_hit = 0;
            for (int j =0; j< idealPlan.get(i).size(); j++) {

                if(idealPlan.get(i).get(j).equals(wk.get(i).get(j))) {
                    times_hit ++;
                }
            }
            System.out.printf("On Day %d, workout times hit: %d\n", i, times_hit);

        }
    }
    public void conductFoodAnalysis(Member mem, ArrayList<Food> fd) {
        Planner planner = Planner.getInstance();
        AllPlans ap = planner.getPlan(mem);
        ArrayList<Food> idealPlan = ap.getFoodPlan();
        int times_hit = 0;
        for (int i = 0; i < idealPlan.size(); i++) {

            if (idealPlan.get(i).equals(fd.get(i))) {
                times_hit ++;
            }

        }
        System.out.printf("Total weekly food hit: %d", times_hit);

    }


}
