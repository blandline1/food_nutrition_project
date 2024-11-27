import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Food {
    private int calories;
    private int carbs; // in grams
    private int proteins; // in grams
    private int fats; // in grams
    private ArrayList<String> foodList; // List of food items consumed
    private double waterIntake; // in liters

    public Food(int calories, int carbs, int proteins, int fats, ArrayList<String> foodList, double waterIntake) {
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
        this.foodList = new ArrayList<>(foodList);
        this.waterIntake = waterIntake;
    }

    public static Food createFoodFromInput(Scanner scanner) {
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
        scanner.nextLine(); // Consume newline
        ArrayList<String> foodList = new ArrayList<>(Arrays.asList(foodItems.split(",")));
        return new Food(calories, carbs, proteins, fats, foodList, waterIntake);
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getProteins() {
        return proteins;
    }

    public int getFats() {
        return fats;
    }

    public ArrayList<String> getFoodList() {
        return new ArrayList<>(foodList);
    }

    public double getWaterIntake() {
        return waterIntake;
    }

    @Override
    public String toString() {
        return String.format("Food [calories=%d, carbs=%dg, proteins=%dg, fats=%dg, foodList=%s, waterIntake=%.2fL]",
                calories, carbs, proteins, fats, foodList, waterIntake);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Food other = (Food) obj;
        return calories == other.calories &&
                carbs == other.carbs &&
                proteins == other.proteins &&
                fats == other.fats &&
                Double.compare(other.waterIntake, waterIntake) == 0 &&
                Objects.equals(foodList, other.foodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, carbs, proteins, fats, foodList, waterIntake);
    }
}
