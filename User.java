import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String name;
    protected String password;
    protected int id;
    protected ArrayList<Food> dailyFoodLogs;
    protected ArrayList<ArrayList<Workout>> dailyWorkoutLogs;
  
    public User(String name, int id, String password) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.dailyFoodLogs = new ArrayList<>();
        this.dailyWorkoutLogs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public boolean checkPass(String password) {
        StringBuilder passHash = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            int newCharValue = currentChar + 5;
            char newChar = (char) newCharValue;
            passHash.append(newChar);
        }
        return this.password.contentEquals(passHash);
    }

    @Override
    public boolean equals(Object user){
        if(user instanceof User) {
            return name.equals(((User)user).name);
        }
        return false;
    }

    public abstract void getTrainer(List<Trainer> trainers);

}
