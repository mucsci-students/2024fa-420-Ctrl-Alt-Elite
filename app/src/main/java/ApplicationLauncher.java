import java.util.Scanner;

import View.CLI;
import View.GUI;

public class ApplicationLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the application mode:");
        System.out.println("1: Command Line Interface (CLI)");
        System.out.println("2: Graphical User Interface (GUI)");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                CLI.main(args); // Launch CLI
                break;
            case "2":
                GUI.main(args); // Launch GUI
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
                break;
        }
        scanner.close();
    }
}