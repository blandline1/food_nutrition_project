package nutritionApp;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner scanner = new Scanner(new File("1.txt"));
//        //runningProgram(scanner);
//        scanner.close();
        Scanner scanner = new Scanner(System.in);
        runningProgram(scanner);

    }

    private static void runningProgram(Scanner scanner) {
        boolean exit = false;

        try (scanner) {
            while (!exit) {
                Authenticator authenticator = Authenticator.getInstance();
                exit = authenticator.LoginPage(scanner);
                User loggedUser = authenticator.getLoggedUser();

                while (!exit && loggedUser != null) {

                    loggedUser.showOptions();

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case -1:
                            loggedUser = null;
                            System.out.println(authenticator.Logout());
                            break;
                        case 1:
                            loggedUser.runOpt1(scanner);
                            break;
                        case 2:
                            loggedUser.runOpt2(scanner);
                            break;
                        case 3:
                            loggedUser.runOpt3(scanner);
                            break;
                        case 4:
                            loggedUser.runOpt4(scanner);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        } catch (ExNotSubscribed e) {
            System.out.println(e.getMessage());
        }finally {
            scanner.close();
        }
    }
}