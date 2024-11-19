import java.util.*;

public class ApprovedPlan extends AllPlans {

    private ArrayList<Food> idealMealPlan = null;
    private ArrayList<Workout> idealWorkoutPlan = null;

    public void appendPlan(ArrayList<Food> fd, ArrayList<Workout> wk, Member member){

        idealMealPlan = fd;
        idealWorkoutPlan = wk;
        append(member, this);
    }

    public ArrayList<Food> getFoodPlan(){
        return (ArrayList<Food>) idealMealPlan.clone();
    }


    public ArrayList<Workout> getWorkoutPlan(){
        return (ArrayList<Workout>) idealWorkoutPlan.clone();
    }



}
