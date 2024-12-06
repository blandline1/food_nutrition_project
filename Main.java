package nutritionApp;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Uncomment to read from a file instead of standard input
        // Scanner scanner = new Scanner(new File("1.txt"));
        // runningProgram(scanner);
        // scanner.close();
        
        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);
        runningProgram(scanner);
    }

    private static void runningProgram(Scanner scanner) {
        boolean exit = false; // Flag to control the program loop

        try (scanner) { // Try-with-resources to ensure scanner is closed
            while (!exit) {
                // Get the singleton instance of the Authenticator
                Authenticator authenticator = Authenticator.getInstance();
                // Show the login page and check if the user logged in successfully
                exit = authenticator.LoginPage(scanner);
                User loggedUser = authenticator.getLoggedUser(); // Retrieve logged-in user

                // Continue until the user logs out or exits
                while (!exit && loggedUser != null) {
                    // Display options available to the logged-in user
                    loggedUser.showOptions();

                    // Read the user's choice
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    // Handle user's choice with a switch statement
                    switch (choice) {
                        case -1: // If the user chooses to log out
                            loggedUser = null; // Clear the logged user
                            System.out.println(authenticator.Logout()); // Log out and print message
                            break;
                        case 1: // Option 1
                            loggedUser.runOpt1(scanner);
                            break;
                        case 2: // Option 2
                            loggedUser.runOpt2(scanner);
                            break;
                        case 3: // Option 3
                            loggedUser.runOpt3(scanner);
                            break;
                        case 4: // Option 4
                            loggedUser.runOpt4(scanner);
                            break;
                        default: // Handle invalid choice
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        } catch (ExNotSubscribed e) { // Handle specific exceptions
            System.out.println(e.getMessage());
        } catch (ExNoTrainerPlan e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close(); // Ensure the scanner is closed in the end
        }
    }
}
