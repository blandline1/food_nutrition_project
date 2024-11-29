package nutritionApp;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner s = new Scanner(new File("C:\\Users\\ahmad\\eclipse-workspace\\NutritionApp\\src\\nutritionApp\\ApprovePlan.txt"));
//        runningProgram(s);
//        s.close();
        Scanner scanner = new Scanner(System.in);
        runningProgram(scanner);
        scanner.close();

    }

    private static void runningProgram(Scanner scanner) {
        boolean exit = false;

        try  {
            while (!exit) {
                Authenticator authenticator = Authenticator.getInstance();
                exit = authenticator.LoginPage(scanner);
                User loggedUser = authenticator.getLoggedUser();
                
                while (!exit && loggedUser != null) {

                    loggedUser.showOptions();

                    int choice = scanner.nextInt();
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
        } catch (ExNoTrainerPlan e) {
        	System.out.println(e.getMessage());
		}
    }
}