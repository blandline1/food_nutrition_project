package nutritionApp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class User {
    protected String name;
    protected String password;
    protected int id;
  
    public User(String name, int id, String password) {
        this.name = name;
        this.password = password;
        this.id = id;
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
        return name.equals(((User)user).name);
    }

    public abstract void getTrainer(List<Trainer> trainers);

    public abstract void showOptions();

    public abstract void runOpt4(Scanner scanner) throws ExNotSubscribed;

    public abstract void runOpt2(Scanner scanner) throws ExNotSubscribed, ExNoTrainerPlan;

    public abstract void runOpt1(Scanner scanner);

    public abstract void runOpt3(Scanner scanner);
}
