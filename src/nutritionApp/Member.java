package nutritionApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Member extends User {

    private ArrayList<Food> dailyFoodLogs;
    private ArrayList<ArrayList<Workout>> dailyWorkoutLogs;
    private boolean premium = false;
	private static final Command cmd = Command.getInstance();


    public Member(String name, int id, String password) {
        super(name, id, password);
        this.dailyFoodLogs = new ArrayList<>();
        this.dailyWorkoutLogs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Member [name=" + name + ", id=" + id + "]";
    }



    public void addFoodLog(Food food) {
        Scanner s = new Scanner(System.in);
        try {
	        if (dailyFoodLogs.size()==7) {
	            char choice;
	            System.out.print("Food log limit reached, logging now would clear previous logs. Are you sure you want to proceed? (Y/N): ");
	            choice = s.next().charAt(0);
	            if (choice == 'Y') {
	                dailyFoodLogs.clear();
	                dailyFoodLogs.add(food);
	                return;
	            }
	            return;
	        }
	        dailyFoodLogs.add(food);
        }finally {
        	s.close();
        }
    }

    public void addWorkoutLog(ArrayList<Workout> workouts) {
        Scanner s = new Scanner(System.in);
    	try {
	        if(dailyWorkoutLogs.size()==7) {
	            char choice;
	            System.out.print("Workout log limit reached, logging now would clear previous logs. Are you sure you want to proceed? (Y/N): ");
	            choice = s.next().charAt(0);
	            if (choice == 'Y') {
	                dailyWorkoutLogs.clear();
	                dailyWorkoutLogs.add(workouts);
	                return;
	            }
	            return;
	        }
	        dailyWorkoutLogs.add(workouts);
    	}finally {
    		s.close();
    	}
    }

    public void showLogs() {
        int counter = 0;
        for (Food fd_el : dailyFoodLogs) {
            System.out.printf("Day %d: %s\n", counter, fd_el);
            counter++;
        }
        counter = 0;
        for (ArrayList<Workout> wk_el : dailyWorkoutLogs) {
            System.out.printf("Day %d:\n", counter);
            for (Workout wk_el_el : wk_el) {
                System.out.println(wk_el_el);
            }
            counter++;
        }
    }

    @Override
    public void getTrainer(List<Trainer> trainers) {}

    @Override
    public void showOptions() {
        System.out.println("\n1. Go to Logger");
        System.out.println("2. Go to Planner");
        System.out.println("3. Go to Subscriber");
        System.out.println("4. Go to Analysis");
        System.out.println("-1. Log out");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void runOpt2(Scanner scanner) throws ExNotSubscribed, ExNoTrainerPlan {
        cmd.MemberrunOpt2(scanner);
    }

    @Override
    public void runOpt1(Scanner scanner) {
        cmd.MemberrunOpt1(scanner);
    }

    @Override
    public void runOpt3(Scanner scanner) {
        cmd.MemberrunOpt3(scanner);
    }
    
    @Override
    public void runOpt4(Scanner scanner) throws ExNotSubscribed {
        cmd.MemberrunOpt4(scanner);
    }

    public void setSubscribed() {
        premium = true;
    }

    public boolean checkPremium() {
        return premium;
    }

   public void conductAnalysis() {
        Analysis analysis = Analysis.getInstance();
        Planner pln = Planner.getInstance();
        AllPlans ap = pln.getPlan(this);
        ArrayList<Food> expected = ap.getFoodPlan();
        ArrayList<ArrayList<Workout>> expected_wk = ap.getWorkoutPlan();
        analysis.conductFoodAnalysis(expected, dailyFoodLogs);
        analysis.conductWorkoutAnalysis(expected_wk, dailyWorkoutLogs);
    }


}