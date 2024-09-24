package app;

import java.io.IOException;
import java.util.Scanner;

/**
 * The Main class provides a command-line interface for interacting with the UML editor.
 * Users can perform various operations such as adding, deleting, renaming classes, and attributes,
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

                case "AddAttribute":
                    // Adds an attribute to a class
                    System.out.println("Enter the class name to add the attribute to: ");
                    String classToAddAttribute = scanner.nextLine().trim();
                    System.out.println("Enter the attribute name: ");
                    String attributeName = scanner.nextLine().trim();
                    if (umlEditor.addAttribute(classToAddAttribute, attributeName)) {
                        System.out.println("Attribute '" + attributeName + "' added to class '" + classToAddAttribute + "'.");
                    } else {
                        System.out.println("Failed to add attribute. Name may be invalid or duplicated, or class does not exist.");
                    }
                    break;

                case "DeleteAttribute":
                    // Deletes an attribute from a class
                    System.out.println("Enter the class name to delete the attribute from: ");
                    String classToDeleteAttribute = scanner.nextLine().trim();
                    System.out.println("Enter the attribute name to delete: ");
                    String attributeToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteAttribute(classToDeleteAttribute, attributeToDelete)) {
                        System.out.println("Attribute '" + attributeToDelete + "' has been deleted from class '" + classToDeleteAttribute + "'.");
                    } else {
                        System.out.println("Failed to delete attribute. Name may be invalid or class does not exist.");
                    }
                    break;

                case "RenameAttribute":
                    // Renames an attribute in a class
                    System.out.println("Enter the class name with the attribute to rename: ");
                    String classToRenameAttribute = scanner.nextLine().trim();
                    System.out.println("Enter the current attribute name: ");
                    String oldAttributeName = scanner.nextLine().trim();
                    System.out.println("Enter the new attribute name: ");
                    String newAttributeName = scanner.nextLine().trim();
                    if (umlEditor.renameAttribute(classToRenameAttribute, oldAttributeName, newAttributeName)) {
                        System.out.println("Attribute '" + oldAttributeName + "' has been renamed to '" + newAttributeName + "' in class '" + classToRenameAttribute + "'.");
                    } else {
                        System.out.println("Failed to rename attribute. Name may be invalid or duplicated, or class does not exist.");
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

                case "ListClasses":
                    // Lists all classes and their attributes
                    umlEditor.listClasses();
                    break;

                case "ListClass":
                    // Lists the attributes of a specified class
                    System.out.println("Enter the class name to list: ");
                    String listClassName = scanner.nextLine().trim();
                    umlEditor.listClass(listClassName);
                    break;

                case "ListRlationships":
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
        System.out.println("AddAttribute - Add an attribute to a class");
        System.out.println("DeleteAttribute - Delete an attribute from a class");
        System.out.println("RenameAttribute - Rename an attribute in a class");
        System.out.println("AddRelationship - Add a relationship between classes");
        System.out.println("DeleteRelationship - Delete a relationship between classes");
        System.out.println("ListClasses - List all classes and their attributes");
        System.out.println("ListClass - List the contents of a specified class");
        System.out.println("ListRelationships - List all relationships");
        System.out.println("Save - Save data to a JSON file");
        System.out.println("Load - Load data from a JSON file");
        System.out.println("Help - Display this help menu");
        System.out.println("Exit - Exit the application");
    }
}