import java.util.ArrayList;

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
        this.foodList = foodList;
        this.waterIntake = waterIntake;
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
        return foodList;
    }
    public double getWaterIntake() {
        return waterIntake;
    }

    @Override
    public String toString() {
        return "Food [calories=" + calories + ", carbs=" + carbs + "g, proteins=" + proteins + "g, fats=" + fats
                + "g, foodList=" + foodList + ", waterIntake=" + waterIntake + "L]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(!(obj instanceof Food)) return false;
        final Food other = (Food) obj;
        return this.calories == other.calories &&
                this.carbs == other.carbs &&
                this.proteins == other.proteins &&
                this.fats == other.fats &&
                this.foodList.equals(other.foodList) &&
                this.waterIntake == other.waterIntake;
    }
}
