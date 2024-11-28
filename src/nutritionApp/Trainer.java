package nutritionApp;

import java.util.List;
import java.util.Scanner;

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

    @Override
    public void showOptions() {
        System.out.println("\n1. Check Subscriptions");
        System.out.println("2. Approve Plan");
        System.out.println("3. Create and Assign Plan");
        System.out.println("4. Check Member Analysis");
        System.out.println("-1. Log out");
        System.out.print("Enter your choice: ");
    }
    
    
    @Override
    public void runOpt1(Scanner scanner) {
       Command cmd = Command.getInstance();
       cmd.trainerrunOpt1(scanner);
    }

    @Override
    public void runOpt2(Scanner scanner) {
    	Command cmd = Command.getInstance();
    	cmd.trainerrunOpt2(scanner);
    }

    @Override
    public void runOpt3(Scanner scanner) {
    	Command cmd = Command.getInstance();
    	cmd.trainerrunOpt3(scanner);
    }

    @Override
    public void runOpt4(Scanner scanner) {
        Command cmd = Command.getInstance();
        cmd.trainerrunOpt4(scanner);
    }


}
