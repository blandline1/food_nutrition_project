import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Member member = new Member("John Doe", 101); // Example member
        Logger logger = Logger.getInstance();
        Planner planner = Planner.getInstance();

        boolean exit = false;

        while (!exit) {
            System.out.println("\nWelcome to the Food Nutrition App!");
            System.out.println("1. Go to Logger");
            System.out.println("2. Go to Planner");
            System.out.println("3. Go to Subscriber");
            System.out.println("-1. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    LoggerMenu.showLoggerMenu(logger, member, scanner);
                    break;

                case 2:
                    System.out.println("1. Make own plan");
                    System.out.println("2. Get trainer's plan");
                    int ch = scanner.nextInt();
                    scanner.nextLine(); // Consume newline


                case -1:
                    exit = true;
                    System.out.println("Thank you for using the app. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }



}
