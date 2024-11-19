import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Member member = new Member("John Doe", 101, "12345"); // Example member
        Logger logger = Logger.getInstance();

        boolean exit = false;

        while (!exit) {
            User loggedUser = null;
            while (loggedUser == null) {

                System.out.println("\nWelcome to the Food Nutrition App Authenticator!");
                System.out.println("1. Member sign up");
                System.out.println("2. Trainer sign up");
                System.out.println("3. Log in");
                System.out.println("4. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 4) {
                    exit = true;
                    break;
                }
                Authenticator authenticator = Authenticator.getInstance();
                String name;
                String password;
                System.out.print("Please enter your username: ");
                name = scanner.nextLine();
                System.out.print("Please enter your password: ");
                password = scanner.nextLine();

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
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            }
            member = (Member) loggedUser;
            if (!exit) {
                System.out.println("\nWelcome to the Food Nutrition App!");
                System.out.println("1. Go to Logger");
                System.out.println("2. Go to Planner");
                System.out.println("3. Go to Subscriber");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        LoggerMenu.showLoggerMenu(logger, member, scanner);
                        break;
                    case 2:
                        exit = true;
                        System.out.println("Thank you for using the app. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        scanner.close();
    }
}
