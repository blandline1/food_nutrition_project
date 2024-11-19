public class Trainer extends User {

    public Trainer(String name, int id) {
        super(name, id);
    }

    @Override
    public String toString() {
        return "Trainer [name=" + name + ", id=" + id + "]";
    }
}
