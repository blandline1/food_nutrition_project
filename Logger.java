public class Logger {
    private static Logger instance;

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logFood(Member member, Food food) {
        member.addFoodLog(food);
        System.out.println("Food log added for member: " + member.getName());
    }

    public void logWorkout(Member member, Workout workout) {
        member.addWorkoutLog(workout);
        System.out.println("Workout log added for member: " + member.getName());
    }
}
