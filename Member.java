import java.util.ArrayList;
import java.util.List;

public class Member extends User {
    protected List<Food> dailyFoodLogs;
    protected List<Workout> dailyWorkoutLogs;

    public Member(String name, int id) {
        super(name, id);
        this.dailyFoodLogs = new ArrayList<>();
        this.dailyWorkoutLogs = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Member [name=" + name + ", id=" + id + "]";
    }

    public void addFoodLog(Food food) {
        dailyFoodLogs.add(food);
    }

    public void addWorkoutLog(Workout workout) {
        dailyWorkoutLogs.add(workout);
    }

    public void showLogs() {
        System.out.println("\nFood Logs:");
        for (Food food : dailyFoodLogs) {
            System.out.println(food);
        }
        System.out.println("\nWorkout Logs:");
        for (Workout workout : dailyWorkoutLogs) {
            System.out.println(workout);
        }
    }
}
