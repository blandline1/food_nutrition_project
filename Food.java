import java.util.List;

public class Food {
    private int calories;
    private int carbs; // in grams
    private int proteins; // in grams
    private int fats; // in grams
    private List<String> foodList; // List of food items consumed
    private double waterIntake; // in liters

    public Food(int calories, int carbs, int proteins, int fats, List<String> foodList, double waterIntake) {
        this.calories = calories;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
        this.foodList = foodList;
        this.waterIntake = waterIntake;
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
