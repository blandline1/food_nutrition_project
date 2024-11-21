public class Member extends User {

    private boolean premium = false;

    public Member(String name, int id, String password) {
        super(name, id, password);
    }

    @Override
    public String toString() {
        return "Member [name=" + name + ", id=" + id + "]";
    }

    public void setPreimum() {
        premium = true;
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
