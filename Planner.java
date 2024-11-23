import java.util.*;

public class Planner {
    private static Planner instance = new Planner();
    public static Planner getInstance(){
        return instance;
    }

    public void makePlan(Member mb) {

        Scanner s = new Scanner(System.in);
        
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
                wk_name = s.next();
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
        
        
        if (mb.getPremium()) {
            ApprovedPlan ap = new ApprovedPlan();
            ap.appendPlan(ret_fd_obj, ret_wk_obj, mb);
        }
        else {
            NonApprovedPlan nap = new NonApprovedPlan();
            nap.appendPlan(ret_fd_obj, ret_wk_obj, mb);
        }

    }

    public void makePlan(Trainer tr) {
        Scanner s = new Scanner(System.in);

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
                wk_name = s.next();
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



        ApprovedPlan ap = new ApprovedPlan();
        ap.appendPlan(ret_fd_obj, ret_wk_obj, tr);

    }

}
