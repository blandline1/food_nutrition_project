import java.util.*;

public class ApprovedPlan extends AllPlans {

    private ArrayList<Food> idealMealPlan = null;
    private ArrayList<Workout> idealWorkoutPlan = null;
    private Member associatedMember = null;
    private Trainer associatedTrainer = null;

    public void appendPlan(ArrayList<Food> fd, ArrayList<Workout> wk, User usr){

        idealMealPlan = fd;
        idealWorkoutPlan = wk;
        //associatedMember = member;
        append(usr, this);
    }

    public ArrayList<Food> getFoodPlan(){
        return (ArrayList<Food>) idealMealPlan.clone();
    }


    public ArrayList<Workout> getWorkoutPlan(){
        return (ArrayList<Workout>) idealWorkoutPlan.clone();
    }

    public void assignTrainer(Trainer trn) {
        associatedTrainer = trn;

    }







}
