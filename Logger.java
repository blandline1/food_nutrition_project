public class Logger {
    private static final Logger instance = new Logger();

    private Logger() {}

    public static Logger getInstance() {
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
