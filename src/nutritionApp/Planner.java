package nutritionApp;

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

    public void makePlanForMember(Scanner s) {

        Member mb = (Member) Authenticator.getInstance().getLoggedUser();

        // Making food plan
        ArrayList<Food> ret_fd_obj = getFoodPlan(s);
        
        // Making workout plan
        ArrayList<ArrayList<Workout>> ret_wk_obj = getWorkoutPlan(s);

        AllPlans nap = new AllPlans();
        nap.updatePlan(ret_fd_obj, ret_wk_obj);
        allPlans.computeIfAbsent(mb, k -> new ArrayList<>()).add(nap);

    }

	private ArrayList<ArrayList<Workout>> getWorkoutPlan(Scanner s) {
        ArrayList<ArrayList<Workout>> ret_wk_obj = new ArrayList<>();
        int reps, sets, minutes, calBurnt = 0;
        String wk_name = "";
        
        LoggerMenu loggerMenu = LoggerMenu.getInstance();
        
        for (int i = 0; i < 7; i++) {
            System.out.printf("Day %d:\n", i+1);
            ret_wk_obj.add(loggerMenu.getDailyWorkout(s));
        }
		return ret_wk_obj;
	}

	private ArrayList<Food> getFoodPlan(Scanner s) {
		ArrayList<Food> ret_fd_obj = new ArrayList<>();
		LoggerMenu loggerMenu = LoggerMenu.getInstance();
        for (int i = 0; i < 7; i++) {
            ret_fd_obj.add(loggerMenu.logFood(s));
        }
		return ret_fd_obj;
	}

    public void makePlanTrainer(Member mb, Scanner s) {


        // Making food plan
        ArrayList<Food> ret_fd_obj = getFoodPlan(s);

        // Making work out plan
        ArrayList<ArrayList<Workout>> ret_wk_obj = getWorkoutPlan(s);

        Trainer tr = (Trainer) Authenticator.getInstance().getLoggedUser();
        AllPlans ap = new AllPlans();
        ap.updatePlan(ret_fd_obj, ret_wk_obj);
        ap.approve(tr, s);
        allPlans.computeIfAbsent(mb, k -> new ArrayList<>()).add(ap);

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

    public void displayPlan(User usr) throws ExNoTrainerPlan {
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
        throw new ExNoTrainerPlan();
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

	public void showMemberPlannerMenu(Scanner scanner) throws ExNotSubscribed, ExNoTrainerPlan {
		Member member = (Member) Authenticator.getInstance().getLoggedUser();
        Planner planner = Planner.getInstance();
        Subscriber sb = Subscriber.getInstance();
        System.out.println("1. Make a plan.");
        System.out.println("2. Pick trainer plan.");
        int plan_opt = scanner.nextInt();
        if (plan_opt == 1) {
            planner.makePlanForMember(scanner);
        } else if (plan_opt == 2) {
            if (member.checkPremium()) {
                Trainer trainer = sb.choseTrainerPlan();
                planner.displayPlan(trainer);
            }
            throw new ExNotSubscribed();
        }
	}
}
