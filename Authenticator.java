import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Authenticator {

    private static final Authenticator instance = new Authenticator();
    private User loggedUser;
    private final List<User> users;
    private int idCount;

    public enum UserType {
        MEMBER, TRAINER
    }

    private Authenticator() {
        users = new ArrayList<>();
        idCount = 0;
        loggedUser = null;
    }

    public User getLoggedUser() {return loggedUser;}

    public static Authenticator getInstance() {
        return instance;
    }

    public void AuthenticatorPrompt() {
        System.out.println("\nWelcome to the Food Nutrition App Authenticator!");
        System.out.println("1. Member sign up");
        System.out.println("2. Trainer sign up");
        System.out.println("3. Log in");
        System.out.println("-1. Exit");
    }

    public Boolean LoginPage(Scanner scanner) {
        this.loggedUser = null;
        while (this.loggedUser == null) {

            AuthenticatorPrompt();

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == -1) {
                return true;
            }

            System.out.print("Please enter your username: ");
            String name = scanner.nextLine();

            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(SignUp(UserType.MEMBER, name, password));
                    break;
                case 2:
                    System.out.println(SignUp(UserType.TRAINER, name, password));
                    break;
                case 3:
                    if ((this.loggedUser = Login(name, password)) != null) {
                        System.out.println("\nWelcome to the Food Nutrition App!");
                        return false;
                    } else {
                        System.out.println("Invalid Credentials.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
        return true;

    }

    private String HashPassword(String password) {
        StringBuilder passHash = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            int newCharValue = currentChar + 5;
            char newChar = (char) newCharValue;
            passHash.append(newChar);
        }
        return passHash.toString();
    }

    private boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty() && input.length() <= 50;
    }

    public String SignUp(UserType userType, String name, String password) {
        if (!isValidInput(name) || !isValidInput(password)) {
            return "Invalid username or password.";
        }
        for (User user : users) {
            if (user.getName().equals(name)) {
                return "Username already in use";
            }
        }
        String passHash = HashPassword(password);
        
        if (userType == UserType.MEMBER) {
            return MemberSignUp(name, passHash);
        }
        else if (userType == UserType.TRAINER) {
            return TrainerSignUp(name, passHash);
        }
        else {
            return "Invalid Type";
        }
    }

    public String MemberSignUp(String name, String password) {
        Member member = new Member(name, idCount, password);
        users.add(member);
        Planner planner = Planner.getInstance();
        planner.memberCreation(member);
        idCount++;
        return "Successfully registered with name " + name + " as member";
    }

    public String TrainerSignUp(String name, String password) {
        Trainer trainer = new Trainer(name, idCount, password);
        users.add(trainer);
        Subscriber subscriber = Subscriber.getInstance();
        subscriber.addTrainer(trainer);
        idCount++;
        return "Successfully registered with name " + name + " as trainer";
    }

    public User Login(String username, String password) {
        User loggedUser = null;
        for (User user : users) {
            if (user.getName().equals(username)) {
                loggedUser = user;
            }
        }
        if (loggedUser != null && loggedUser.checkPass(password)) {
            this.loggedUser = loggedUser;
            return loggedUser;
        }
        return null;
    }
    
    public String Logout() {
        loggedUser = null;
        return "\nThank you for using Food Nutrition App!";
    } 

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        for(User user : users) {
            user.getTrainer(trainers);
        }
        return trainers;
    }
}
