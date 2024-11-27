import java.util.Objects;
import java.util.Scanner;

public class Workout {
    private final String name;
    private final int sets;
    private final int reps;
    private final int minutes;

    public Workout(String name, int sets, int reps, int minutes) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.minutes = minutes;
    }

    public static Workout createWorkoutFromInput(Scanner scanner) {
        System.out.print("Enter workout name: ");
        String name = scanner.nextLine();
        System.out.print("Enter sets: ");
        int sets = scanner.nextInt();
        System.out.print("Enter reps: ");
        int reps = scanner.nextInt();
        System.out.print("Enter minutes of workout: ");
        int minutes = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return new Workout(name, sets, reps, minutes);
    }

    @Override
    public String toString() {
        return String.format("Workout [name=%s, sets=%d, reps=%d, minutes=%d]", name, sets, reps, minutes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Workout other = (Workout) obj;
        return sets == other.sets &&
                reps == other.reps &&
                minutes == other.minutes &&
                Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sets, reps, minutes);
    }
}
