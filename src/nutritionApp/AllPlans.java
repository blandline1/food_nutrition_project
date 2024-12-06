package nutritionApp;

import java.util.*;

public class AllPlans implements Cloneable{

    private ArrayList<Food> idealMealPlan = new ArrayList<>();
    private ArrayList<ArrayList<Workout>> idealWorkoutPlan = new ArrayList<>();
    private PlanType planType;
    private Trainer associatedTrainer;

    public void updatePlan(ArrayList<Food> fd, ArrayList<ArrayList<Workout>> wk){
        idealMealPlan = fd;
        idealWorkoutPlan = wk;
        planType = NonApprovedPlan.getInstance();
        associatedTrainer = null;
    }

    public ArrayList<Food> getFoodPlan() {
        return (ArrayList<Food>) idealMealPlan.clone();
    }

    public ArrayList<ArrayList<Workout>> getWorkoutPlan() {
        return (ArrayList<ArrayList<Workout>>) idealWorkoutPlan.clone();
    }

    public void printPlan(){
        planType.printPlanType();
        for (Food fd : idealMealPlan) {
            System.out.println(fd);
        }
        for (ArrayList<Workout> wk : idealWorkoutPlan) {
            for (Workout wk1 : wk) {
                System.out.println(wk1);
            }
        }
    }

    public void approve(Trainer loggedUser, Scanner scanner) {
        associatedTrainer = loggedUser;
        System.out.println("press Y to Approved: ");
        String choice = scanner.nextLine();
        if (choice.equals("Y")) {
            this.planType = ApprovedPlan.getInstance();
        }
    }
} 
