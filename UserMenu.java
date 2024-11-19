import java.util.Scanner;

public class UserMenu {

    public void showMenu() {
        Logger logger = Logger.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Log Workout");
            System.out.println("2. Log Nutrition");
            System.out.println("3. Show Logs");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter workout details (e.g., '30 mins running'): ");
                    String workout = scanner.nextLine();
                    logger.logWorkout(workout);
                    break;
                case 2:
                    System.out.print("Enter nutrition details (e.g., '500 calories, lunch'): ");
                    String nutrition = scanner.nextLine();
                    logger.logNutrition(nutrition);
                    break;
                case 3:
                    logger.showLogs();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
