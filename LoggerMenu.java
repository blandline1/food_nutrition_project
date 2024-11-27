import java.util.Scanner;

public class LoggerMenu {

    public static void showLoggerMenu(Scanner scanner) {
        boolean exitLogger = false;
        Logger logger = Logger.getInstance();
        Member member = (Member) Authenticator.getInstance().getLoggedUser();

        while (!exitLogger) {
            displayMenu();
            int choice = getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    logFood(logger, member, scanner);
                    break;
                case 2:
                    logWorkout(logger, member, scanner);
                    break;
                case 3:
                    member.showLogs();
                    break;
                case 4:
                    exitLogger = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nLogger Menu:");
        System.out.println("1. Log Food");
        System.out.println("2. Log Workout");
        System.out.println("3. Show Logs");
        System.out.println("4. Go Back to Main Menu");
    }

    private static void logFood(Logger logger, Member member, Scanner scanner) {
        System.out.println("\nLog Food:");
        Food food = Food.createFoodFromInput(scanner);
        logger.logFood(member, food);
    }

    private static void logWorkout(Logger logger, Member member, Scanner scanner) {
        System.out.println("\nLog Workout:");
        Workout workout = Workout.createWorkoutFromInput(scanner);
        logger.logWorkout(member, workout);
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        return scanner.nextInt();
    }

}
