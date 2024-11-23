import java.util.*;

public class NonApprovedPlan extends AllPlans {

    private ArrayList<Food> idealMealPlan = null;
    private ArrayList<ArrayList<Workout>> idealWorkoutPlan = null;


    public void updatePlan(ArrayList<Food> fd, ArrayList<ArrayList<Workout>> wk, User usr) {

        idealMealPlan = fd;
        idealWorkoutPlan = wk;
        //associatedMember = member;
    }

    public ArrayList<Food> getFoodPlan() {
        return (ArrayList<Food>) idealMealPlan.clone();
    }


    public ArrayList<ArrayList<Workout>> getWorkoutPlan() {
        return (ArrayList<ArrayList<Workout>>) idealWorkoutPlan.clone();
    }

}


