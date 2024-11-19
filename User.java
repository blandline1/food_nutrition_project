import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String name;
    protected int id;
    

    public User(String name, int id) {
        this.name = name;
        this.id = id;
       
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

   
}
