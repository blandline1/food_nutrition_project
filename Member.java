
import java.util.ArrayList;
import java.util.List;
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



    public void addFoodLog(Food food) {
        Scanner s = new Scanner(System.in);
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
    }

    public void addWorkoutLog(Workout workout) {
        Scanner s = new Scanner(System.in);

        if(dailyWorkoutLogs.size()==7) {

            char choice;
            System.out.print("Workout log limit reached, logging now would clear previous logs. Are you sure you want to proceed? (Y/N): ");
            choice = s.next().charAt(0);
            if (choice == 'Y') {
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
    public void runOpt4(Scanner scanner) throws ExNotSubscribed {
        Member member = (Member) Authenticator.getInstance().getLoggedUser();
        if (member.checkPremium()) {
            member.conductAnalysis();
        } else {
            throw new ExNotSubscribed();
        }
    }

    @Override
    public void runOpt2(Scanner scanner) throws ExNotSubscribed {
        Member member = (Member) Authenticator.getInstance().getLoggedUser();
        Planner planner = Planner.getInstance();
        Subscriber sb = Subscriber.getInstance();
        System.out.println("1. Make a plan.");
        System.out.println("2. Pick trainer plan.");
        int plan_opt = scanner.nextInt();
        if (plan_opt == 1) {
            planner.makePlan(scanner);
        } else if (plan_opt == 2) {
            if (member.checkPremium()) {
                AllPlans plan = sb.choseTrainerPlan();
                planner.displayPlan(member);
            }
            throw new ExNotSubscribed();
        }
    }

    @Override
    public void runOpt1(Scanner scanner) {
        LoggerMenu.getInstance().showLoggerMenu(scanner);
    }

    @Override
    public void runOpt3(Scanner scanner) {
        Subscriber subscriber = Subscriber.getInstance();
        subscriber.showSubscriberMenu();
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
