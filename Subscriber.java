import java.util.*;

public class Subscriber {
    private static final Subscriber instance = new Subscriber();
    private final HashMap<Trainer, List<Member>> subscribeTrainers;


    private Subscriber(){
        subscribeTrainers = new HashMap<>();
    }

    public static Subscriber getInstance(){
        return instance;
    }

    public void showSubscriberMenu(){
        while(true) {
            Member loggedMember = (Member) Authenticator.getInstance().getLoggedUser();
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nPlease choose your wish");
            if (loggedMember.checkPremium()) {
                System.out.println("1. Unsubscribe trainer");
            } else {
                System.out.println("1. Subscribe trainer");
            }
            System.out.println("2. Show trainer stats");
            System.out.println("-1. Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (loggedMember.checkPremium()) {
                        Unsubscribed(loggedMember);
                    } else {
                        subscribeToTrainers(loggedMember, scanner);
                    }
                    break;
                case 2:
                    showSubscribedStats();
                    break;
                case -1:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void Unsubscribed(Member loggedMember) {
        for (Trainer trainer : subscribeTrainers.keySet()) {
            subscribeTrainers.get(trainer).removeIf(member -> member.equals(loggedMember));
        }
    }

    public void subscribeToTrainers(Member loggedMember, Scanner scanner){
        Authenticator authenticator = Authenticator.getInstance();
        List<Trainer> allTrainers = authenticator.getAllTrainers();
        showTrainerList(allTrainers);

        System.out.print("Enter the number (-1 to back): ");
        int trainerID = scanner.nextInt();
        if (allTrainers.isEmpty()) {
            System.out.println("No trainers available");
        }
        else {
            if(trainerID > 0 && trainerID <= allTrainers.size()) {
                Trainer trainer = allTrainers.get(trainerID-1);
                subscribeTrainers.get(trainer).add(loggedMember);
                loggedMember.setSubscribed();
                System.out.println(loggedMember + " subscribed to " + trainer + " Successfully!");
            }
        }

    }

    private void showTrainerList(List<Trainer> allTrainers) {
        for(int i = 1; i <= allTrainers.size(); i++){
            System.out.println(i + ": " +allTrainers.get(i-1).getName());
        }
    }

    public void showSubscribedStats() {
        for(Trainer trainer : subscribeTrainers.keySet()){
            System.out.println("1: " + trainer.getName() + ": " + subscribeTrainers.get(trainer).size());
        }
    }


    public void addTrainer(Trainer trainer) {
        subscribeTrainers.put(trainer, new ArrayList<>());
    }

    public AllPlans choseTrainerPlan() {
        try{
            Member mb = (Member) Authenticator.getInstance().getLoggedUser();
            List<Trainer> trainerlist = (Authenticator.getInstance()).getAllTrainers();
            Trainer trainer = null;
            for (Trainer trn : trainerlist) {
                List<Member> memberList = subscribeTrainers.get(trn);
                if (memberList.contains(mb)) {
                    trainer = trn;
                    break;
                }
            }
            Planner planner = Planner.getInstance();
            AllPlans plan = planner.getPlan(trainer);
            if (plan == null) {
                throw new ExNoTrainerPlan();
            }
            return plan;
        }
        catch (ExNoTrainerPlan e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}


