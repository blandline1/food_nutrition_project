import java.util.*;

public class Planner {
    private static final Planner instance = new Planner();

    private final HashMap<User, ArrayList<AllPlans>> allPlans;

    private Planner(){
        allPlans = new HashMap<>();
    }

    public static Planner getInstance(){
        return instance;
    }

    public void memberCreation(Member member){
        allPlans.put(member, new ArrayList<>());
    }

    public void makePlan(Scanner s) {

        Member mb = (Member) Authenticator.getInstance().getLoggedUser();

        // Making food plan
        ArrayList<Food> ret_fd_obj = new ArrayList<>();
        for (int i = 0; i < 7; i++) {

            System.out.printf("Day %d:\n", i+1);
            int calories, carbs, proteins, fats, num_foods;
            List<String> foodList = new ArrayList<>();
            double waterIntake;

            // Making food object
            System.out.println("Please enter target calories: ");
            calories = s.nextInt();
            System.out.println("Please enter target carbs: ");
            carbs = s.nextInt();
            System.out.println("Please enter target proteins: ");
            proteins = s.nextInt();
            System.out.println("Please enter target fats: ");
            fats = s.nextInt();
            System.out.println("Please enter desired number of foods in food list: ");
            num_foods = s.nextInt();
            s.nextLine(); // consume new line
            for (int j = 0; j < num_foods; j++) {
                System.out.println("Please enter food: ");
                foodList.add(s.nextLine());
            }
            System.out.println("Please enter target water intake: ");
            waterIntake = s.nextDouble();
            Food fd = new Food(calories, carbs, proteins, fats, foodList, waterIntake);
        }
        
        // Making workout plan
        System.out.println("Please enter number of workouts in each day: ");
        int num_works = s.nextInt();
        ArrayList<ArrayList<Workout>> ret_wk_obj = new ArrayList<>();
        int reps, sets, minutes, calBurnt = 0;
        String wk_name = "";
        
        for (int i = 0; i < 7; i++) {
            System.out.printf("Day %d:\n", i+1);
            ArrayList<Workout> wk_list = new ArrayList<>();
            for (int j = 0; j < num_works; j++) {
                System.out.println("Please enter workout: ");
                s.nextLine();
                wk_name = s.nextLine();
                System.out.println("Please enter number of sets: ");
                sets = s.nextInt();
                System.out.println("Please enter number of reps: ");
                reps = s.nextInt();
                System.out.println("Please enter minutes: ");
                minutes = s.nextInt();
                wk_list.add(new Workout(wk_name, sets, reps, minutes));
            }
            ret_wk_obj.add(wk_list);
        }

            AllPlans nap = new AllPlans();
            nap.updatePlan(ret_fd_obj, ret_wk_obj, mb);
            ArrayList<AllPlans> pln = allPlans.get(mb);
            pln.add(nap);
            allPlans.put(mb, pln);

    }

    public void makePlanTrainer(Trainer tr, Scanner s) {


        // Making food plan

        ArrayList<Food> ret_fd_obj = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int calories, carbs, proteins, fats, num_foods = 0;
            List<String> foodList = new ArrayList<>();
            double waterIntake = 0.0;

            // Making food object

            System.out.println("Please enter target calories: ");
            calories = s.nextInt();
            System.out.println("Please enter target carbs: ");
            carbs = s.nextInt();
            System.out.println("Please enter target proteins: ");
            proteins = s.nextInt();
            System.out.println("Please enter target fats: ");
            fats = s.nextInt();
            System.out.println("Please enter desired number of foods in food list: ");
            num_foods = s.nextInt();
            for (int j = 0; j < num_foods; j++) {
                System.out.println("Please enter food: ");
                foodList.add(s.next());
            }
            System.out.println("Please enter target water intake: ");
            waterIntake = s.nextDouble();
            Food fd = new Food(calories, carbs, proteins, fats, foodList, waterIntake);
        }

        // Making workout plan

        System.out.println("Please enter number of workouts in each day: ");
        int num_works = s.nextInt();
        ArrayList<ArrayList<Workout>> ret_wk_obj = new ArrayList<>();
        int reps, sets, minutes, calBurnt = 0;
        String wk_name = "";

        for (int i = 0; i < 7; i++) {
            ArrayList<Workout> wk_list = new ArrayList<>();
            for (int j = 0; j < num_works; j++) {
                System.out.println("Please enter workout: ");
                wk_name = s.nextLine();
                System.out.println("Please enter number of sets: ");
                sets = s.nextInt();
                System.out.println("Please enter number of reps: ");
                reps = s.nextInt();
                System.out.println("Please enter minutes: ");
                minutes = s.nextInt();
                wk_list.add(new Workout(wk_name, sets, reps, minutes));
            }
            ret_wk_obj.add(wk_list);
        }



        AllPlans ap = new AllPlans();
        ap.updatePlan(ret_fd_obj, ret_wk_obj, tr);
        ArrayList<AllPlans> pln = allPlans.get(tr);
        pln.add(ap);
        allPlans.put(tr, pln);

    }

    public AllPlans getPlan(User usr) {
        if (allPlans.containsKey(usr)) {
            ArrayList<AllPlans> pln = allPlans.get(usr);
            if(!pln.isEmpty()) {
                return allPlans.get(usr).getLast();
            }
        }
        return null;

    }

    public void displayPlan(User usr) {
        AllPlans pln = getPlan(usr);
        if (pln != null) {
            ArrayList<Food> fd = pln.getFoodPlan();
            ArrayList<ArrayList<Workout>> wk = pln.getWorkoutPlan();
            int counter = 0;
            for (Food fd_el : fd) {
                System.out.printf("Day %d: %s\n", counter, fd_el);
                counter++;
            }
            counter = 0;
            for (ArrayList<Workout> wk_el : wk) {
                System.out.printf("Day %d:\n", counter);
                for (Workout wk_el_el : wk_el) {
                    System.out.println(wk_el_el);
                }
                counter++;
            }
        }
    }

    public void showPlan(Member member, Scanner scanner) {
        AllPlans pln = getPlan(member);
        if (pln != null) {
            pln.printPlan();
            pln.approve((Trainer) Authenticator.getInstance().getLoggedUser(), scanner);
        }else{
            System.out.println("User does not have any plan yet!!");
        }
    }
}
