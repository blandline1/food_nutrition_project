import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoggerMenu {

    public static void showLoggerMenu(Scanner scanner) {
        boolean exitLogger = false;
        Logger logger = Logger.getInstance();
        Member member = (Member) Authenticator.getInstance().getLoggedUser();

        while (!exitLogger) {
            System.out.println("\nLogger Menu:");
            System.out.println("1. Log Food");
            System.out.println("2. Log Workout");
            System.out.println("3. Show Logs");
            System.out.println("4. Go Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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

    private static void logFood(Logger logger, Member member, Scanner scanner) {
        System.out.println("\nLog Food:");
        System.out.print("Enter calories: ");
        int calories = scanner.nextInt();
        System.out.print("Enter carbs (in grams): ");
        int carbs = scanner.nextInt();
        System.out.print("Enter proteins (in grams): ");
        int proteins = scanner.nextInt();
        System.out.print("Enter fats (in grams): ");
        int fats = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter food items (comma-separated): ");
        String foodItems = scanner.nextLine();
        System.out.print("Enter water intake (in liters): ");
        double waterIntake = scanner.nextDouble();
        List<String> list_food =  Arrays.asList(foodItems.split(","));
        ArrayList<String> food_list = new ArrayList<>(list_food);
        Food food = new Food(calories, carbs, proteins, fats, food_list, waterIntake);
        logger.logFood(member, food);
    }

    private static void logWorkout(Logger logger, Member member, Scanner scanner) {
        System.out.println("\nLog Workout:");
        System.out.print("Enter workout name: ");
        String name = scanner.nextLine();
        System.out.print("Enter sets: ");
        int sets = scanner.nextInt();
        System.out.print("Enter reps: ");
        int reps = scanner.nextInt();
        System.out.print("Enter minutes of workout: ");
        int minutes = scanner.nextInt();

        Workout workout = new Workout(name, sets, reps, minutes);
        logger.logWorkout(member, workout);
    }
}
