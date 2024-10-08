import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * The Main class provides a command-line interface for interacting with the UML
 * editor.
 * Users can perform various operations such as adding, deleting, renaming
 * classes, and methods,
 * as well as managing relationships between classes. Commands can also save and
 * load data in JSON format.
 */
public class Main {

    /** The UML editor instance to manage classes and relationships. */
    private static UmlEditor umlEditor = new UmlEditor();

    /**
     * The main method starts the command-line interface for interacting with the
     * UML editor.
     * It prompts the user for commands and performs the corresponding actions.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        //TODO make it so input cannot have white space, trailing or otherwise
        // Main loop for processing commands from the user
        while (!exit) {
            System.out.print("Enter a command (Type 'help' for a list of commands): ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add-class":
                    // Add a class to the UML editor
                    System.out.println("Enter the class name: ");
                    String className = scanner.nextLine().trim();
                    if (umlEditor.addClass(className)) {
                        System.out.println("Class '" + className + "' has been added.");
                    } else {
                        System.out.println("Failed to add class. Name may be invalid or duplicated.");
                    }
                    break;

                case "delete-class":
                    // Delete a class from the UML editor
                    System.out.println("Enter the name of the class to delete: ");
                    String classToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteClass(classToDelete)) {
                        System.out.println("Class '" + classToDelete + "' has been deleted.");
                    } else {
                        System.out.println("Failed to delete class. Name may be invalid or does not exist.");
                    }
                    break;

                case "rename-class":
                    // Rename an existing class
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

                case "add-field":
                    System.out.println("Enter the name of the class to add the field to: ");
                    String classToAddField = scanner.nextLine().trim();
                    System.out.println("Enter the field name: ");
                    String fieldName = scanner.nextLine().trim();
                    if (umlEditor.addField(classToAddField, fieldName)) {
                        System.out.println("Field '" + fieldName + "' added to class '" + classToAddField + "'.");
                    } else {
                        System.out.println("Failed to add field. Class may not exist or field is invalid.");
                    }
                    break;

                case "delete-field":
                    System.out.println("Enter the name of the class to delete the field from: ");
                    String classToDeleteField = scanner.nextLine().trim();
                    System.out.println("Enter the field name to delete: ");
                    String fieldToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteField(classToDeleteField, fieldToDelete)) {
                        System.out.println(
                                "Field '" + fieldToDelete + "' deleted from class '" + classToDeleteField + "'.");
                    } else {
                        System.out.println("Failed to delete field. Class or field may not exist.");
                    }
                    break;

                case "rename-field":
                    System.out.println("Enter the name of the class to rename the field in: ");
                    String classToRenameField = scanner.nextLine().trim();
                    System.out.println("Enter the old field name: ");
                    String oldFieldName = scanner.nextLine().trim();
                    System.out.println("Enter the new field name: ");
                    String newFieldName = scanner.nextLine().trim();
                    if (umlEditor.renameField(classToRenameField, oldFieldName, newFieldName)) {
                        System.out.println("Field '" + oldFieldName + "' renamed to '" + newFieldName + "' in class '"
                                + classToRenameField + "'.");
                    } else {
                        System.out.println("Failed to rename field. Class or field may not exist.");
                    }
                    break;

                case "add-method":
                    // Add a method to a class
                    System.out.println("Enter the name of the class to add the method to: ");
                    String classToAddMethod = scanner.nextLine().trim();
                    System.out.println("Enter the method name: ");
                    String methodName = scanner.nextLine().trim();

                    // TODO the command prompts again, says its invalid, and prompts again when the
                    // exception gets thrown
                    System.out.println("Enter the number(0, 1, 2, etc.) of parameters for the method: ");
                    int paraNum = 0;
                    try {
                        paraNum = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println(
                                "Parameter number entered improperly. Please enter a numeral for the parameter count(0, 1, 2, etc.).");
                        break;
                    }
                    scanner.nextLine();

                    LinkedHashSet<String> paraList = new LinkedHashSet<>();
                    for (int i = 1; i <= paraNum; i++) {
                        System.out.println("Enter the name of parameter " + i + ": ");
                        String paraName = scanner.nextLine().trim();
                        paraList.addLast(paraName);
                    }

                    if (umlEditor.addMethod(classToAddMethod, methodName, paraList)) {
                        System.out.println("Method '" + methodName + "' added to class '" + classToAddMethod + "'.");
                    } else {
                        System.out.println(
                                "Failed to add method. Name may be invalid or duplicated, or class does not exist.");
                    }
                    break;

                case "delete-method":
                    // Deletes an method from a class
                    System.out.println("Enter the name of the class to delete the method from: ");
                    String classToDeleteMethod = scanner.nextLine().trim();
                    System.out.println("Enter the name of the method to delete: ");
                    String methodToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteMethod(classToDeleteMethod, methodToDelete)) {
                        System.out.println("Method '" + methodToDelete + "' has been deleted from class '"
                                + classToDeleteMethod + "'.");
                    } else {
                        System.out.println("Failed to delete method. Name may be invalid or class does not exist.");
                    }
                    break;

                case "rename-method":
                    // Renames a method in a class
                    System.out.println("Enter the name of the class with the method to rename: ");
                    String classToRenameMethod = scanner.nextLine().trim();
                    System.out.println("Enter the current method name: ");
                    String oldMethodName = scanner.nextLine().trim();
                    System.out.println("Enter the new method name: ");
                    String newMethodName = scanner.nextLine().trim();
                    if (umlEditor.renameMethod(classToRenameMethod, oldMethodName, newMethodName)) {
                        System.out.println("Method '" + oldMethodName + "' has been renamed to '"
                                + newMethodName + "' in class '" + classToRenameMethod + "'.");
                    } else {
                        System.out.println(
                                "Failed to rename method. Name may be invalid or duplicated, or class does not exist.");
                    }
                    break;

                case "remove-parameter":
                    // Removes a parameter, or many parameters, from a method
                    System.out.println("Enter the name of the class of the method with the parameters to remove: ");
                    String classToRemoveParameter = scanner.nextLine().trim();
                    System.out.println("Enter the name of the method with the parameters to remove: ");
                    String methodOfParameters = scanner.nextLine().trim();
                    System.out.println("How many parameters would you like to remove(1, 2, 3, etc.): ");

                    // TODO the command prompts again, says its invalid, and prompts again when the
                    // exception gets thrown
                    int numToRemove = 0;
                    try {
                        numToRemove = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println(
                                "Parameter number to remove entered improperly. Please enter a numeral for the parameter count(0, 1, 2, etc.).");
                        break;
                    }
                    scanner.nextLine();
                    for (int i = 0; i < numToRemove; i++) {
                        System.out.println("Enter the name of the parameter to remove: ");
                        String paraName = scanner.nextLine().trim();

                        if (umlEditor.removeParameter(classToRemoveParameter, methodOfParameters, paraName)) {
                            System.out.println(
                                    "Parameter '" + paraName + "' was removed from '" + methodOfParameters + "'.");
                        } else {
                            System.out.println(
                                    "Failed to remove parameter. Name may be invalid, or class does not exist.");
                            break;
                        }
                    }
                    break;

                case "change-parameter":
                    // Replaces a list of parameters with a new list.
                    System.out.println("Enter the name of the class of the method with the parameters to change: ");
                    String classToChangeParameter = scanner.nextLine().trim();
                    System.out.println("Enter the name of the method with the parameters to change: ");
                    String methodToChangeParameters = scanner.nextLine().trim();

                    // TODO the command prompts again, says its invalid, and prompts again when the
                    // exception gets thrown
                    System.out.println("Enter the new number(0, 1, 2, etc.) of parameters for '"
                            + methodToChangeParameters + "': ");
                    int newParaNum = 0;
                    try {
                        newParaNum = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println(
                                "Parameter number entered improperly. Please enter a numeral for the parameter count(0, 1, 2, etc.).");
                        break;
                    }
                    scanner.nextLine();

                    LinkedHashSet<String> newParaList = new LinkedHashSet<>();
                    for (int i = 1; i <= newParaNum; i++) {
                        System.out.println("Enter the name of parameter " + i + ": ");
                        String paraName = scanner.nextLine().trim();
                        newParaList.addLast(paraName);
                    }

                    if (umlEditor.changeParameters(classToChangeParameter, methodToChangeParameters, newParaList)) {
                        System.out.println("Method '" + methodToChangeParameters + "' had its parameters changed.");
                    } else {
                        System.out.println(
                                "Failed to add method. Name may be invalid or duplicated, or class does not exist.");
                    }
                    break;

                    case "add-relationship":
                    // Adds a relationship between two classes
                    System.out.println("Enter the source class: ");
                    String source = scanner.nextLine().trim();
                    System.out.println("Enter the destination class: ");
                    String destination = scanner.nextLine().trim();
                    
                    RelationshipType type = null;
                    while (type == null) {
                        System.out.println("Enter the type of relationship (Aggregation, Composition, Inheritance, or Realization): ");
                        String inputType = scanner.nextLine().trim().toUpperCase();
                        
                        try {
                            type = RelationshipType.valueOf(inputType);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid relationship type. Please enter one of the following: Aggregation, Composition, Inheritance, or Realization.");
                        }
                    }
                    
                    if (umlEditor.addRelationship(source, destination, type)) {
                        System.out.println("Relationship from '" + source + "' to '" + destination + "' has been added.");
                    } else {
                        System.out.println("Failed to add relationship. Source or destination may not exist.");
                    }
                    break;
                
                case "delete-relationship":
                    // Deletes a relationship between two classes
                    System.out.println("Enter the source class: ");
                    String deleteSource = scanner.nextLine().trim();
                    System.out.println("Enter the destination class: ");
                    String deleteDestination = scanner.nextLine().trim();
                    
                    RelationshipType type1 = null;
                    while (type1 == null) {
                        System.out.println("Enter the type of relationship (Aggregation, Composition, Inheritance, or Realization): ");
                        String inputTyp = scanner.nextLine().trim().toUpperCase();
                        
                        try {
                            type1 = RelationshipType.valueOf(inputTyp);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid relationship type. Please enter one of the following: Aggregation, Composition, Inheritance, or Realization.");
                        }
                    }
                    
                    if (umlEditor.deleteRelationship(deleteSource, deleteDestination, type1)) {
                        System.out.println("Relationship from '" + deleteSource + "' to '" + deleteDestination + "' has been deleted.");
                    } else {
                        System.out.println("Failed to delete relationship.");
                    }
                    break;
                
                case "list-classes":
                    // Lists all classes and their methods
                    umlEditor.listClasses();
                    break;

                case "list-class":
                    // Lists the methods of a specified class
                    System.out.println("Enter the class name to list: ");
                    String listClassName = scanner.nextLine().trim();
                    umlEditor.listClass(listClassName);
                    break;

                case "list-relationships":
                    // Lists all relationships between classes
                    umlEditor.listRelationships();
                    break;

                case "save":
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

                case "load":
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

                case "help":
                    // Displays a list of available commands
                    displayHelp();
                    break;

                case "exit":
                    // Exits the application
                    System.out.println("Exiting the application...");
                    exit = true;
                    break;

                default:
                    // Handles invalid commands
                    System.out.println("Invalid command. Type 'help' for a list of valid commands.");
            }
        }

        scanner.close();
    }

    /**
     * Displays the available commands and their descriptions to the user.
     */
    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("add-class - Add a class");
        System.out.println("delete-class - Delete a class");
        System.out.println("rename-class - Rename a class");
        System.out.println("add-field - Add a field to a class");
        System.out.println("delete-field - Delete a field from a class");
        System.out.println("rename-field - Rename a field in a class");
        System.out.println("add-method - Add a method to a class");
        System.out.println("delete-method - Delete a method from a class");
        System.out.println("rename-method - Rename a method in a class");
        System.out.println("remove-parameter - Remove one or multiple parameters from a method");
        System.out.println("change-parameter - Replace all parameters for a method");
        System.out.println("add-relationship - Add a relationship between classes");
        System.out.println("delete-relationship - Delete a relationship between classes");
        System.out.println("list-classes - List all classes and their methods");
        System.out.println("list-class - List the contents of a specified class");
        System.out.println("list-relationships - List all relationships");
        System.out.println("save - Save data to a JSON file");
        System.out.println("load - Load data from a JSON file");
        System.out.println("help - Display this help menu");
        System.out.println("exit - Exit the application");
    }
}