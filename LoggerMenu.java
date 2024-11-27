import java.util.Arrays;
import java.util.Scanner;

public class LoggerMenu {

    public static LoggerMenu instance = new LoggerMenu();

    private LoggerMenu() {}

    public static LoggerMenu getInstance() {
        return instance;
    }

    public void showLoggerMenu(Scanner scanner) {
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
                    Food food = logFood(scanner);
                    logger.logFood(member, food);
                    break;
                case 2:
                    Workout workout = logWorkout(scanner);
                    logger.logWorkout(member, workout);
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

    private Food logFood(Scanner scanner) {
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

        return new Food(calories, carbs, proteins, fats, Arrays.asList(foodItems.split(",")), waterIntake);
    }

    private Workout logWorkout(Scanner scanner) {
        System.out.println("\nLog Workout:");
        System.out.print("Enter workout name: ");
        String name = scanner.nextLine();
        System.out.print("Enter sets: ");
        int sets = scanner.nextInt();
        System.out.print("Enter reps: ");
        int reps = scanner.nextInt();
        System.out.print("Enter minutes of workout: ");
        int minutes = scanner.nextInt();

        return new Workout(name, sets, reps, minutes);

    }
}
