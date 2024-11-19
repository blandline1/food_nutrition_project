import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String name;
    protected int id;
    protected List<Food> dailyFoodLogs;
    protected List<Workout> dailyWorkoutLogs;


    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.dailyFoodLogs = new ArrayList<>();
        this.dailyWorkoutLogs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


}
