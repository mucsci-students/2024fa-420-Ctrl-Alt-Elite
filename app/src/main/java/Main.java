import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Main class provides a command-line interface for interacting with the UML editor.
 * Users can perform various operations such as adding, deleting, renaming classes, and methods,
 * as well as managing relationships between classes. Commands can also save and load data in JSON format.
 */
public class Main {

    /** The UML editor instance to manage classes and relationships.*/
    private static UmlEditor umlEditor = new UmlEditor();

    /**
     * The main method starts the command-line interface for interacting with the UML editor.
     * It prompts the user for commands and performs the corresponding actions.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Main loop for processing commands from the user
        while (!exit) {
            System.out.print("Enter a command (Type 'Help' for a list of commands): ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "AddClass":
                    // Adds a class to the UML editor
                    System.out.println("Enter the class name: ");
                    String className = scanner.nextLine().trim();
                    if (umlEditor.addClass(className)) {
                        System.out.println("Class '" + className + "' has been added.");
                    } else {
                        System.out.println("Failed to add class. Name may be invalid or duplicated.");
                    }
                    break;

                case "DeleteClass":
                    // Deletes a class from the UML editor
                    System.out.println("Enter the class name to delete: ");
                    String classToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteClass(classToDelete)) {
                        System.out.println("Class '" + classToDelete + "' has been deleted.");
                    } else {
                        System.out.println("Failed to delete class. Name may be invalid or does not exist.");
                    }
                    break;

                case "RenameClass":
                    // Renames an existing class
                    System.out.println("Enter the class name to rename: ");
                    String oldClassName = scanner.nextLine().trim();
                    System.out.println("Enter the new class name: ");
                    String newClassName = scanner.nextLine().trim();
                    if (umlEditor.renameClass(oldClassName, newClassName)) {
                        System.out.println("Class '" + oldClassName + "' has been renamed to '" + newClassName + "'.");
                    } else {
                        System.out.println("Failed to rename class. Name may be invalid or duplicated.");
                    }
                    break;

                    //TODO
                case "AddMethod":
                    // Adds an method to a class
                    System.out.println("Enter the class name to add the method to: ");
                    String classToAddMethod = scanner.nextLine().trim();
                    System.out.println("Enter the method name: ");
                    String methodName = scanner.nextLine().trim();
                    
                    //TODO the command prompts again, says its invaild, and prompts again when the exceiton gets thrown
                    System.out.println("Enter the number(0, 1, 2, etc.) of parameters for the method: ");
                    int paraNum = 0;
                    try {
                        paraNum = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Parameter number entered improperly. Please enter a numeral for the parameter count(0, 1, 2, etc.).");
                        break;
                    }
                    scanner.nextLine();
                    
                    ArrayList<String> paraList = new ArrayList<>();
                    for (int i = 1; i <= paraNum; i++) {
                        System.out.println("Enter the name of parameter " + i + ": ");
                        String paraName = scanner.nextLine().trim();
                        paraList.add(paraName);
                    }
                    
                    if (umlEditor.addMethod(classToAddMethod, methodName, paraList)) {
                        System.out.println("Method '" + methodName + "' added to class '" + classToAddMethod + "'.");
                    } else {
                        System.out.println("Failed to add method. Name may be invalid or duplicated, or class does not exist.");
                    }
                    break;

                    //TODO
                case "DeleteMethod":
                    // Deletes an method from a class
                    System.out.println("Enter the class name to delete the method from: ");
                    String classToDeleteMethod = scanner.nextLine().trim();
                    System.out.println("Enter the method name to delete: ");
                    String methodToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteMethod(classToDeleteMethod, methodToDelete)) {
                        System.out.println("Method '" + methodToDelete + "' has been deleted from class '" + classToDeleteMethod + "'.");
                    } else {
                        System.out.println("Failed to delete method. Name may be invalid or class does not exist.");
                    }
                    break;

                    //TODO
                case "RenameMethod":
                    // Renames a method in a class
                    System.out.println("Enter the class name with the method to rename: ");
                    String classToRenameMethod = scanner.nextLine().trim();
                    System.out.println("Enter the current method name: ");
                    String oldMethodName = scanner.nextLine().trim();
                    System.out.println("Enter the new method name: ");
                    String newMethodName = scanner.nextLine().trim();
                    if (umlEditor.renameMethod(classToRenameMethod, oldMethodName, newMethodName)) {
                        System.out.println("Method '" + oldMethodName + "' has been renamed to '" 
                            + newMethodName + "' in class '" + classToRenameMethod + "'.");
                    } else {
                        System.out.println("Failed to rename method. Name may be invalid or duplicated, or class does not exist.");
                    }
                    break;

                case "AddRelationship":
                    // Adds a relationship between two classes
                    System.out.println("Enter the source class: ");
                    String source = scanner.nextLine().trim();
                    System.out.println("Enter the destination class: ");
                    String destination = scanner.nextLine().trim();
                    if (umlEditor.addRelationship(source, destination)) {
                        System.out.println("Relationship from '" + source + "' to '" + destination + "' has been added.");
                    } else {
                        System.out.println("Failed to add relationship. Source or destination may not exist.");
                    }
                    break;

                case "DeleteRelationship":
                    // Deletes a relationship between two classes
                    System.out.println("Enter the source class: ");
                    String deleteSource = scanner.nextLine().trim();
                    System.out.println("Enter the destination class: ");
                    String deleteDestination = scanner.nextLine().trim();
                    if (umlEditor.deleteRelationship(deleteSource, deleteDestination)) {
                        System.out.println("Relationship from '" + deleteSource + "' to '" + deleteDestination + "' has been deleted.");
                    } else {
                        System.out.println("Failed to delete relationship.");
                    }
                    break;

                    //TODO
                case "ListClasses":
                    // Lists all classes and their methods
                    umlEditor.listClasses();
                    break;

                    //TODO
                case "ListClass":
                    // Lists the methods of a specified class
                    System.out.println("Enter the class name to list: ");
                    String listClassName = scanner.nextLine().trim();
                    umlEditor.listClass(listClassName);
                    break;

                case "ListRelationships":
                    // Lists all relationships between classes
                    umlEditor.listRelationships();
                    break;

                case "Save":
                    // Saves the current UML data to a JSON file
                    System.out.println("Enter a filename to save to: ");
                    String saveFilename = scanner.nextLine().trim();
                    try {
                        JsonUtils.save(umlEditor, saveFilename);
                        System.out.println("Data saved to '" + saveFilename + "'.");
                    } catch (IOException e) {
                        System.out.println("Failed to save data: " + e.getMessage());
                    }
                    break;

                case "Load":
                    // Loads UML data from a JSON file
                    System.out.println("Enter a filename to load from: ");
                    String loadFilename = scanner.nextLine().trim();
                    try {
                        umlEditor = JsonUtils.load(loadFilename);
                        System.out.println("Data loaded from '" + loadFilename + "'.");
                    } catch (IOException e) {
                        System.out.println("Failed to load data: " + e.getMessage());
                    }
                    break;

                    //TODO
                case "Help":
                    // Displays a list of available commands
                    displayHelp();
                    break;

                case "Exit":
                    // Exits the application
                    System.out.println("Exiting the application.");
                    exit = true;
                    break;

                default:
                    // Handles invalid commands
                    System.out.println("Invalid command. Type 'Help' for a list of valid commands.");
            }
        }

        scanner.close();
    }

    /**
     * Displays the available commands and their descriptions to the user.
     */
    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("AddClass - Add a class");
        System.out.println("DeleteClass - Delete a class");
        System.out.println("RenameClass - Rename a class");
        System.out.println("AddMethod - Add a method to a class");
        System.out.println("DeleteMethod - Delete a method from a class");
        System.out.println("RenameMethod - Rename a method in a class");
        System.out.println("AddRelationship - Add a relationship between classes");
        System.out.println("DeleteRelationship - Delete a relationship between classes");
        System.out.println("ListClasses - List all classes and their methods");
        System.out.println("ListClass - List the contents of a specified class");
        System.out.println("ListRelationships - List all relationships");
        System.out.println("Save - Save data to a JSON file");
        System.out.println("Load - Load data from a JSON file");
        System.out.println("Help - Display this help menu");
        System.out.println("Exit - Exit the application");
    }
}