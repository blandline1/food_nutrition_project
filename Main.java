import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("1.txt"));
        Logger logger = Logger.getInstance();
        Planner planner = Planner.getInstance();
        Subscriber sb = Subscriber.getInstance();

        boolean exit = false;


        while (!exit) {
            User loggedUser = null;
            while (loggedUser == null && !exit) {

                System.out.println("\nWelcome to the Food Nutrition App Authenticator!");
                System.out.println("1. Member sign up");
                System.out.println("2. Trainer sign up");
                System.out.println("3. Log in");
                System.out.println("4. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();
                Authenticator authenticator = Authenticator.getInstance();
                String name = "";
                String password = "";


                if(choice != 4) {

                    System.out.print("Please enter your username: ");
                    name = scanner.nextLine();
                    System.out.print("Please enter your password: ");
                    password = scanner.nextLine();
                }


                switch (choice) {
                    case 1:
                        System.out.print(authenticator.MemberSignUp(name, password));
                        break;
                    case 2:
                        System.out.print(authenticator.TrainerSignUp(name, password));
                        break;
                    case 3:
                        loggedUser = authenticator.Login(name, password);
                        if (loggedUser == null) {
                            System.out.print("Invalid credentials");
                        }
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Fuck off!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            }
            Member member = (Member) loggedUser;
            if (!exit) {
                System.out.println("\nWelcome to the Food Nutrition App!");
            }
            try{

                while (!exit) {

                    System.out.println("\n1. Go to Logger");
                    System.out.println("2. Go to Planner");
                    System.out.println("3. Go to Subscriber");
                    System.out.println("4. Go to Analysis");
                    System.out.println("-1. Exit");
                    System.out.print("Enter your choice: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case -1:
                            exit = true;
                            System.out.println("Thank you for using Food Nutrition App!");
                            break;
                        case 1:
                            LoggerMenu.showLoggerMenu(logger, member, scanner);
                            break;
                        case 2:
                            exit = true;
                            System.out.println("1. Make a plan.");
                            System.out.println("2. Pick trainer plan.");
                            int plan_opt = scanner.nextInt();
                            if (plan_opt == 1) {
                                planner.makePlan(member, scanner);
                            }
                            else if (plan_opt == 2) {
                                if(!sb.isSubscribed(member)) {
                                    throw new ExNotSubscribed();
                                }
                                AllPlans plan = sb.choseTrainerPlan(member);
                                planner.displayPlan(member);
                            }
                            break;
                        case 3:
                            Subscriber subscriber = Subscriber.getInstance();
                            subscriber.showSubscriberMenu(member);
                            break;
                        case 4:
                            if(!member.checkPremium()) {
                                throw new ExNotSubscribed();
                            }
                            else {
                                member.conductAnalysis();
                            }
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }

            }
            catch (ExNotSubscribed e) {
                System.out.println(e.getMessage());

            }

            finally {
                scanner.close();
            }
        }


    }
}
