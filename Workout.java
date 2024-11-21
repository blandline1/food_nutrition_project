public class Workout {
    private String name;
    private int sets;
    private int reps;
    private int minutes;

    public Workout(String name, int sets, int reps, int caloriesBurned) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "Workout [name=" + name + ", sets=" + sets + ", reps=" + reps + ", minutes=" + minutes + "]";
    }
}
