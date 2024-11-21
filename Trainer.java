import java.util.List;

public class Trainer extends User {

    public Trainer(String name, int id, String password) {
        super(name, id, password);
    }

    @Override
    public String toString() {
        return "Trainer [name=" + name + ", id=" + id + "]";
    }


    @Override
    public void getTrainer(List<Trainer> trainers) {
        trainers.add(this);
    }
}
