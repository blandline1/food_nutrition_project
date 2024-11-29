package nutritionApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoggerMenu {

    private static final LoggerMenu instance = new LoggerMenu();

    private LoggerMenu (){};
    public static LoggerMenu getInstance(){
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
                    ArrayList<Workout> workouts = logWorkout(scanner);
                    logger.logWorkout(member, workouts);
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

    public Food logFood(Scanner scanner) {
        System.out.println("\nLog Food!");
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
        return new Food(calories, carbs, proteins, fats, food_list, waterIntake);
    }

    private ArrayList<Workout> logWorkout(Scanner s) {
    	System.out.println("\nLog Workout!");
    	System.out.print("Enter number of workouts: ");
    	return getDailyWorkout(s);
        
    }
	public ArrayList<Workout> getDailyWorkout(Scanner s) {
        System.out.print("Enter number of workouts: ");
		int num_workouts = s.nextInt();
		s.nextLine(); //consume newLine
         ArrayList<Workout> wk = new ArrayList<>();
         for (int i = 0; i < num_workouts; i++) {

             System.out.print("Enter workout name: ");
             String name = s.nextLine();
             System.out.print("Enter number of sets: ");
             int sets = s.nextInt();
             System.out.print("Enter number of reps: ");
             int reps = s.nextInt();
             System.out.print("Enter number of calories burned: ");
             int calBurnt = s.nextInt();
             System.out.print("Enter number of minute: ");
             int minutes = s.nextInt();
             System.out.println();
             s.nextLine();
             Workout wk_el = new Workout(name, sets, reps, calBurnt, minutes);
             wk.add(wk_el);
         }
		return wk;
	}
}
