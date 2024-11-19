public class Workout {
    private String name;
    private int sets;
    private int reps;
    private int caloriesBurned;

    public Workout(String name, int sets, int reps, int caloriesBurned) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.caloriesBurned = caloriesBurned;
    }

    @Override
    public String toString() {
        return "Workout [name=" + name + ", sets=" + sets + ", reps=" + reps + ", caloriesBurned=" + caloriesBurned + "]";
    }
}
