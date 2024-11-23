import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Member extends User {

    private boolean premium = false;

    public Member(String name, int id, String password) {
        super(name, id, password);
    }

    @Override
    public String toString() {
        return "Member [name=" + name + ", id=" + id + "]";
    }

    public void setPreimum() {
        premium = true;
    }
    public boolean getPremium() {
        return premium;
    }

    public void addFoodLog(Food food) {
        if (dailyFoodLogs.size()==7) {
            if(checkPremium()) {
                Analysis analysis = Analysis.getInstance();
                analysis.conductFoodAnalysis();
                System.out.println("Analysis conducted and saved, clearing previous logs");
            }
            else {
                System.out.println("Not a premium member, clearing previous weekly log.");
            }
            dailyFoodLogs.clear();
            dailyFoodLogs.add(food);
        }
    }

    public void addWorkoutLog(Workout workout) {
        Scanner s = new Scanner(System.in);

        if(dailyWorkoutLogs.size()==7) {
            if(checkPremium()) {
                Analysis analysis = Analysis.getInstance();
                analysis.conductWorkoutAnalysis();
                System.out.println("Analysis conducted and saved, clearing previous logs");
            }
            else {
                System.out.println("Not a premium member, clearing previous weekly log.");
            }
            dailyWorkoutLogs.clear();
            System.out.print("Enter number of workouts: ");
            int num_workouts = s.nextInt();
            System.out.println();
            ArrayList<Workout> wk = new ArrayList<>();
            for (int i = 0; i < num_workouts; i++) {

                System.out.print("Enter workout name: ");
                String name = s.nextLine();
                System.out.print("Enter number of sets: ");
                int sets = s.nextInt();
                System.out.println();
                System.out.print("Enter number of reps: ");
                int reps = s.nextInt();
                System.out.println();
                System.out.print("Enter number of calories burned: ");
                int calBurnt = s.nextInt();
                System.out.println();
                Workout wk_el = new Workout(name, sets, reps, calBurnt);
                wk.add(wk_el);
            }
            dailyWorkoutLogs.add(wk);
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


    public void setSubscribed() {
        premium = true;
    }

    public boolean checkPremium() {
        return premium;
    }
}
