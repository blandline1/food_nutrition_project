import java.util.ArrayList;
import java.util.List;

public class Authenticator {

    private static Authenticator instance = new Authenticator();
    private List<User>  users;
    private int idCount;

    private Authenticator() {
        users = new ArrayList<>();
        idCount = 0;
    }

    public static Authenticator getInstance() {
        return instance;
    }

    public String MemberSignUp(String name, String password) {
        for(User user : users) {
            if(user.getName().equals(name)){
                return "Username already in use";
            }
        }
        StringBuilder passHash = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            int newCharValue = currentChar + 5;
            char newChar = (char) newCharValue;
            passHash.append(newChar);
        }
        users.add(new Member(name, idCount, passHash.toString()));
        idCount++;
        return "Successfully registered with name " + name + " as member";
    }

    public String TrainerSignUp(String name, String password) {
        for(User user : users) {
            if(user.getName().equals(name)){
                return "Username already in use";
            }
        }
        StringBuilder passHash = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            int newCharValue = currentChar + 5;
            char newChar = (char) newCharValue;
            passHash.append(newChar);
        }
        users.add(new Trainer(name, idCount, passHash.toString()));
        idCount++;
        return "Successfully registered with name " + name + " as trainer";
    }

    public User Login(String username, String password) {
        User loogedUser = null;
        for(User user : users) {
            if(user.getName().equals(username)){
                loogedUser = user;
            }
        }
        if(loogedUser != null && loogedUser.checkPass(password)){
            return loogedUser;
        }
        return null;
    }

}
