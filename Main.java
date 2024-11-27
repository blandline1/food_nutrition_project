import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("1.txt"));
        //runningProgram(scanner);
        scanner.close();
        scanner = new Scanner(System.in);
        runningProgram(scanner);

    }

    private static void runningProgram(Scanner scanner) {
        boolean exit = false;

        try (scanner) {
            while (!exit) {
                User loggedUser = null;
                while (loggedUser == null && !exit) {

                    System.out.println("\nWelcome to the Food Nutrition App Authenticator!");
                    System.out.println("1. Member sign up");
                    System.out.println("2. Trainer sign up");
                    System.out.println("3. Log in");
                    System.out.println("-1. Exit");

                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    Authenticator authenticator = Authenticator.getInstance();
                    String name = "";
                    String password = "";


                    if (choice != -1) {

                        System.out.print("Please enter your username: ");
                        name = scanner.nextLine();
                        System.out.print("Please enter your password: ");
                        password = scanner.nextLine();
                    }


                    switch (choice) {
                        case 1:
                            System.out.print(authenticator.MemberSignUp(name, password));
                            break;
                        case 2:
                            System.out.print(authenticator.TrainerSignUp(name, password));
                            break;
                        case 3:
                            loggedUser = authenticator.Login(name, password);
                            if (loggedUser == null) {
                                System.out.print("Invalid credentials");
                            }
                            break;
                        case -1:
                            exit = true;
                            System.out.println("Fuck off!");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                }

                if (!exit) {
                    System.out.println("\nWelcome to the Food Nutrition App!");
                }

                while (!exit && loggedUser != null) {

                    loggedUser.showOptions();

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case -1:
                            loggedUser = null;
                            System.out.println("Thank you for using Food Nutrition App!");
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
                            loggedUser.runOpt4();
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

    private static void initData() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("InitData.txt"));

    }
}
