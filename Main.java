import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static UmlEditor umlEditor = new UmlEditor();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.print("Enter a command (Type 'Help' for a list of commands): ");
            String command = scanner.nextLine().trim();

            switch (command.toLowerCase()) {
                case "addclass":
                    System.out.println("Enter the class name: ");
                    String className = scanner.nextLine().trim();
                    if (umlEditor.addClass(className)) {
                        System.out.println("Class '" + className + "' has been added.");
                    } else {
                        System.out.println("Failed to add class. Name may be invalid or duplicated.");
                    }
                    break;

                case "deleteclass":
                    System.out.println("Enter the class name to delete: ");
                    String classToDelete = scanner.nextLine().trim();
                    if (umlEditor.deleteClass(classToDelete)) {
                        System.out.println("Class '" + classToDelete + "' has been deleted.");
                    } else {
                        System.out.println("Failed to delete class. Name may be invalid or does not exist.");
                    }
                    break;

                case "renameclass":
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

                case "addattribute":
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

                case "deleteattribute":
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

                case "renameattribute":
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

                case "addrelationship":
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

                case "deleterelationship":
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

                case "listclasses":
                    umlEditor.listClasses();
                    break;

                case "listclass":
                    System.out.println("Enter the class name to list: ");
                    String listClassName = scanner.nextLine().trim();
                    umlEditor.listClass(listClassName);
                    break;

                case "listrelationships":
                    umlEditor.listRelationships();
                    break;

                case "save":
                    System.out.println("Enter filename to save to: ");
                    String saveFilename = scanner.nextLine().trim();
                    try {
                        //JsonUtils.save(umlEditor, saveFilename);
                        System.out.println("Data saved to '" + saveFilename + "'.");
                    } catch (IOException e) {
                        System.out.println("Failed to save data: " + e.getMessage());
                    }
                    break;

                case "load":
                    System.out.println("Enter filename to load from: ");
                    String loadFilename = scanner.nextLine().trim();
                    try {
                        umlEditor = JsonUtils.load(loadFilename);
                        System.out.println("Data loaded from '" + loadFilename + "'.");
                    } catch (IOException e) {
                        System.out.println("Failed to load data: " + e.getMessage());
                    }
                    break;

                case "help":
                    displayHelp();
                    break;

                case "exit":
                    System.out.println("Exiting the application.");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid command. Type 'Help' for a list of valid commands.");
            }
        }

        scanner.close();
    }

    private static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("addclass - Add a class");
        System.out.println("deleteclass - Delete a class");
        System.out.println("renameclass - Rename a class");
        System.out.println("addattribute - Add an attribute to a class");
        System.out.println("deleteattribute - Delete an attribute from a class");
        System.out.println("renameattribute - Rename an attribute in a class");
        System.out.println("addrelationship - Add a relationship between classes");
        System.out.println("deleterelationship - Delete a relationship between classes");
        System.out.println("listclasses - List all classes and their attributes");
        System.out.println("listclass - List the contents of a specified class");
        System.out.println("listrelationships - List all relationships");
        System.out.println("save - Save data to a JSON file");
        System.out.println("load - Load data from a JSON file");
        System.out.println("help - Display this help menu");
        System.out.println("exit - Exit the application");
    }
}
