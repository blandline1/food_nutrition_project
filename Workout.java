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

    public String getName() {
        return name;
    }
    public int getSets() {
        return sets;
    }
    public int getReps() {
        return reps;
    }
    public int getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        return "Workout [name=" + name + ", sets=" + sets + ", reps=" + reps + ", minutes=" + minutes + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Workout other = (Workout) obj;
        return this.name.equals(other.name) &&
                this.sets == other.sets &&
                this.reps == other.reps &&
                this.minutes == other.minutes;
    }
}
