package Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlEditorModel;
import Model.UmlRelationship;
import View.CLI;

/**
 * The UmlCliController class handles the command-line interface (CLI) interactions
 * for managing a UML diagram. It communicates with the UmlEditorModel to
 * manipulate the data and uses the CLI view to display messages to the user.
 */
public class UmlCliController {

    //Fields
    private UmlEditorModel model;
    private final UmlEditor umlEditor;
    private final CLI view;
    private final Scanner scanner;
    
    /**
     * Constructs a new UmlCliController.
     *
     * @param model The UML editor model used to manage UML data.
     * @param umlEditor The UmlEditor object that provides operations to modify UML components.
     * @param view The CLI view for displaying messages and prompting the user.
     */
    public UmlCliController(UmlEditorModel model, UmlEditor umlEditor, CLI view) {
        this.model = model;
        this.umlEditor = umlEditor;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Starts the command loop that waits for and processes user input commands.
     */
    public void start() {
        boolean exit = false;

        // Loop until the user chooses to exit
        while (!exit) {
            view.displayMessage("Enter a command (Type 'help' for a list of commands): ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "add-class":
                    handleAddClass();
                    break;
                case "delete-class":
                    handleDeleteClass();
                    break;
                case "rename-class":
                    handleRenameClass();
                    break;
                case "add-field":
                    handleAddField();
                    break;
                case "delete-field":
                    handleDeleteField();
                    break;
                case "rename-field":
                    handleRenameField();
                    break;
                case "add-method":
                    handleAddMethod();
                    break;
                case "delete-method":
                    handleDeleteMethod();
                    break;
                case "rename-method":
                    handleRenameMethod();
                    break;
                case "remove-parameter":
                    handleRemoveParameter();
                    break;
                case "change-parameter":
                    handleChangeParameter();
                    break;
                case "add-relationship":
                    handleAddRelationship();
                    break;
                case "delete-relationship":
                    handleDeleteRelationship();
                    break;
                case "change-relationship-type":
                    handleChangeRelationshipType();
                    break;
                case "save":
                    handleSave();
                    break;
                case "load":
                    handleLoad();
                    break;
                case "list-classes":
                    handleListClasses();
                    break;
                case "list-class":
                    handleListClass();
                    break;
                case "list-relationships":
                    handleListRelationships();
                    break;
                case "help":
                    // Displays a list of available commands
                    view.displayHelp();
                    break;
                case "exit":
                    exit = true;
                    view.displayMessage("Exiting the program.");
                    break;
                default:
                    view.displayMessage("Invalid command. Type 'help' for options.");
                    break;
            }
        }
        scanner.close(); // Close the scanner when done
    }

    /**
     * Handles the 'add-class' command by prompting the user for a class name and adding it to the UML editor.
     */
    public void handleAddClass() {
        view.displayMessage("Enter the class name: ");
        String className = scanner.nextLine().trim();
        if (umlEditor.addClass(className)) {
            view.displayMessage("Class '" + className + "' has been added.");
        } else {
            view.displayMessage("Failed to add class. Name may be invalid or duplicated.");
        }
    }
    
    /**
     * Handles the 'delete-class' command by prompting the user for a class name and deleting it from the UML editor.
     */
    public void handleDeleteClass() {
        // Delete a class from the UML editor
        view.displayMessage("Enter the name of the class to delete: ");
        String classToDelete = scanner.nextLine().trim();
        if (umlEditor.deleteClass(classToDelete)) {
            view.displayMessage("Class '" + classToDelete + "' has been deleted.");
        } else {
            view.displayMessage("Failed to delete class. Name may be invalid or does not exist.");
        }
    }

    /**
     * Handles the 'rename-class' command by prompting the user for the old class name and new class name and renaming it in the UML editor.
     */
    public void handleRenameClass() {
        // Rename an existing class
        view.displayMessage("Enter the class name to rename: ");
        String oldClassName = scanner.nextLine().trim();
        System.out.println("Enter the new class name: ");
        String newClassName = scanner.nextLine().trim();
        if (umlEditor.renameClass(oldClassName, newClassName)) {
            view.displayMessage("Class '" + oldClassName + "' has been renamed to '" + newClassName + "'.");
        } else {
            view.displayMessage("Failed to rename class. Name may be invalid or duplicated.");
        }
    }

    /**
     * Handles the 'add-field' command by prompting the user for a class and field name and adding the field to the class.
     */
    public void handleAddField() {
        view.displayMessage("Enter the name of the class to add the field to: ");
        String classToAddField = scanner.nextLine().trim();
        view.displayMessage("Enter the field name: ");
        String fieldName = scanner.nextLine().trim();
        if (umlEditor.addField(classToAddField, fieldName)) {
            view.displayMessage("Field '" + fieldName + "' added to class '" + classToAddField + "'.");
        } else {
            view.displayMessage("Failed to add field. Class may not exist or field is invalid.");
        }
    }

    /**
     * Handles the 'delete-field' command by prompting the user for a class and field name and removing the field from the class.
     */    
    public void handleDeleteField() {
        view.displayMessage("Enter the name of the class to delete the field from: ");
        String classToDeleteField = scanner.nextLine().trim();
        view.displayMessage("Enter the field name to delete: ");
        String fieldToDelete = scanner.nextLine().trim();
        if (umlEditor.deleteField(classToDeleteField, fieldToDelete)) {
            view.displayMessage("Field '" + fieldToDelete + "' deleted from class '" + classToDeleteField + "'.");
        } else {
            view.displayMessage("Failed to delete field. Class or field may not exist.");
        }
    }

    /**
     * Handles the 'rename-field' command by prompting the user for a class and old and new field names and renaming the field.
     */
    public void handleRenameField() {
        view.displayMessage("Enter the name of the class to rename the field in: ");
        String classToRenameField = scanner.nextLine().trim();
        view.displayMessage("Enter the old field name: ");
        String oldFieldName = scanner.nextLine().trim();
        view.displayMessage("Enter the new field name: ");
        String newFieldName = scanner.nextLine().trim();
        if (umlEditor.renameField(classToRenameField, oldFieldName, newFieldName)) {
            view.displayMessage("Field '" + oldFieldName + "' renamed to '" + newFieldName + "' in class '"
                    + classToRenameField + "'.");
        } else {
            view.displayMessage("Failed to rename field. Class or field may not exist.");
        }
    }

    
    /**
    * Handles adding a method to a class by prompting the user for class name,
    * method name, and parameters, and then adding the method to the UML editor.
    */    
    public void handleAddMethod() {
        view.displayMessage("Enter the name of the class to add the method to: ");
        String classToAddMethod = scanner.nextLine().trim();
        view.displayMessage("Enter the method name: ");
        String methodName = scanner.nextLine().trim();
        view.displayMessage("Enter the parameters for the method (p1, p2, p3, etc.): ");
        String parameters = scanner.nextLine().trim();
        
        LinkedHashSet<String> paraList = new LinkedHashSet<>();
        if (!parameters.trim().isEmpty()) {
            Arrays.stream(parameters.split(","))
                    .map(String::trim)
                    .forEach(paraList::add);
        }

        if (umlEditor.addMethod(classToAddMethod, methodName, paraList)) {
            view.displayMessage("Method '" + methodName + "' added to class '" + classToAddMethod + "'.");
        } else {
            view.displayMessage(
                    "Failed to add method. Name or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles deleting a method from a class by prompting the user for class name,
     * method name, and parameters, and then deleting the method from the UML editor.
     */
    public void handleDeleteMethod() {
        view.displayMessage("Enter the name of the class to delete the method from: ");
        String classToDeleteMethod = scanner.nextLine().trim();
        view.displayMessage("Enter the name of the method to delete: ");
        String methodToDelete = scanner.nextLine().trim();

        int paraListNum;
        view.displayMessage("Enter the number(0, 1, 2, etc.) of parameters that belong to the method: ");
        try {
            paraListNum = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Parameter number entered improperly. Please enter a numeral for the parameter count (0, 1, 2, etc.).");
            scanner.nextLine(); // Clear the buffer
            return;
        }
        scanner.nextLine(); // Clear the buffer

        LinkedHashSet<String> parameterList = new LinkedHashSet<>();
        for (int i = 1; i <= paraListNum; i++) {
            view.displayMessage("Enter the name of parameter " + i + ": ");
            String paraName = scanner.nextLine().trim();
            parameterList.add(paraName);
        }

        if (umlEditor.deleteMethod(classToDeleteMethod, methodToDelete, parameterList)) {
            view.displayMessage("Method '" + methodToDelete + "' has been deleted from class '"
                    + classToDeleteMethod + "'.");
        } else {
            view.displayMessage("Failed to delete method. Name may be invalid or class does not exist.");
        }
    }

    /**
     * Handles renaming a method in a class by prompting the user for class name,
     * current method name, new method name, and parameters, and then renaming the method.
     */
    public void handleRenameMethod() {
        // Renames a method in a class
        view.displayMessage("Enter the name of the class with the method to rename: ");
        String classToRenameMethod = scanner.nextLine().trim();
        view.displayMessage("Enter the current method name: ");
        String oldMethodName = scanner.nextLine().trim();

        view.displayMessage("Enter the number (0, 1, 2, etc.) of parameters that belong to the method: ");
        int paraListNumber;
        try {
            paraListNumber = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Parameter number entered improperly. Please enter a numeral for the parameter count (0, 1, 2, etc.).");
            scanner.nextLine(); // Clear the scanner buffer
            return; // Exit the method
        }
        scanner.nextLine(); // Clear the buffer after reading the integer

        LinkedHashSet<String> parameters = new LinkedHashSet<>();
        for (int i = 1; i <= paraListNumber; i++) {
            view.displayMessage("Enter the name of parameter " + i + ": ");
            String paraName = scanner.nextLine().trim();
            parameters.add(paraName);
        }

        view.displayMessage("Enter the new method name: ");
        String newMethodName = scanner.nextLine().trim();

        if (umlEditor.renameMethod(classToRenameMethod, oldMethodName, parameters, newMethodName)) {
            view.displayMessage("Method '" + oldMethodName + "' has been renamed to '" + newMethodName + "' in class '"
                    + classToRenameMethod + "'.");
        } else {
            view.displayMessage("Failed to rename method. Name may be invalid or duplicated, or class does not exist.");
        }
    }
    
    /**
     * Handles removing a parameter from a method by prompting the user for class name,
     * method name, and parameter name, and then removing the parameter from the method.
     */
    public void handleRemoveParameter() {
        // Removes a parameter from a method
        view.displayMessage("Enter the name of the class of the method with the parameter to remove: ");
        String classToRemoveParameter = scanner.nextLine().trim();
        view.displayMessage("Enter the name of the method with the parameter to remove: ");
        String methodOfParameter = scanner.nextLine().trim();
        view.displayMessage("Enter the name of the parameter to remove: ");
        String paraName = scanner.nextLine().trim();

        if (umlEditor.removeParameter(classToRemoveParameter, methodOfParameter, paraName)) {
            view.displayMessage("Parameter '" + paraName + "' was removed from '" + methodOfParameter + "'.");
        } else {
            view.displayMessage("Failed to remove parameter. Name may be invalid, or class does not exist.");
        }
    }

    /**
     * Handles changing parameters of a method by prompting the user for class name,
     * method name, and new parameters, and then changing the parameters of the method.
     */
    public void handleChangeParameter() {
        // Replaces a list of parameters with a new list.
        view.displayMessage("Enter the name of the class of the method with the parameters to change: ");
        String classToChangeParameter = scanner.nextLine().trim();
        view.displayMessage("Enter the name of the method with the parameters to change: ");
        String methodToChangeParameters = scanner.nextLine().trim();
        view.displayMessage("Enter the new parameters for the method (p1, p2, p3, etc.): ");
        String parameters = scanner.nextLine().trim();
        
        LinkedHashSet<String> newParaList = new LinkedHashSet<>();
        if (!parameters.trim().isEmpty()) {
            Arrays.stream(parameters.split(","))
                    .map(String::trim)
                    .forEach(newParaList::add);
        }

        if (umlEditor.changeParameters(classToChangeParameter, methodToChangeParameters, newParaList)) {
            view.displayMessage("Method '" + methodToChangeParameters + "' had its parameters changed.");
        } else {
            view.displayMessage(
                    "Failed to change parameters. Name or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles adding a relationship between two classes by prompting the user for source class,
     * destination class, and relationship type, and then adding the relationship to the UML editor.
     */
    public void handleAddRelationship() {
        // Adds a relationship between two classes
        view.displayMessage("Enter the source class: ");
        String source = scanner.nextLine().trim();
        view.displayMessage("Enter the destination class: ");
        String destination = scanner.nextLine().trim();

        RelationshipType type = null;
        while (type == null) {
            view.displayMessage(
                    "Enter the type of relationship (Aggregation, Composition, Inheritance, or Realization): ");
            String inputType = scanner.nextLine().trim().toUpperCase();

            try {
                type = RelationshipType.valueOf(inputType);
            } catch (IllegalArgumentException e) {
                view.displayMessage(
                        "Invalid relationship type. Please enter one of the following: Aggregation, Composition, Inheritance, or Realization.");
            }
        }

        if (umlEditor.addRelationship(source, destination, type)) {
            view.displayMessage("Relationship from '" + source + "' to '" + destination + "' has been added.");
        } else {
            view.displayMessage("Failed to add relationship. Source or destination may not exist.");
        }
    }

    /**
     * Handles deleting a relationship between two classes by prompting the user
     * for source class and destination class, and then deleting the relationship.
     */
    public void handleDeleteRelationship() {
        // Deletes a relationship between two classes
        view.displayMessage("Enter the source class: ");
        String deleteSource = scanner.nextLine().trim();
        view.displayMessage("Enter the destination class: ");
        String deleteDestination = scanner.nextLine().trim();

        RelationshipType type1 = null;
        while (type1 == null) {
            view.displayMessage(
                    "Enter the type of relationship (Aggregation, Composition, Inheritance, or Realization): ");
            String inputTyp = scanner.nextLine().trim().toUpperCase();

            try {
                type1 = RelationshipType.valueOf(inputTyp);
            } catch (IllegalArgumentException e) {
                view.displayMessage(
                        "Invalid relationship type. Please enter one of the following: Aggregation, Composition, Inheritance, or Realization.");
            }
        }

        if (umlEditor.deleteRelationship(deleteSource, deleteDestination, type1)) {
            view.displayMessage(
                    "Relationship from '" + deleteSource + "' to '" + deleteDestination + "' has been deleted.");
        } else {
            view.displayMessage("Failed to delete relationship.");
        }
    }

    /**
     * Handles changing the relationship type between two classes.
     * Prompts the user for the source class, destination class, 
     * the current relationship type, and the new relationship type.
     */
    public void handleChangeRelationshipType() {
        // Get the source class
        view.displayMessage("Enter the source class: ");
        String sourceToChange = scanner.nextLine().trim();

        // Get the destination class
        view.displayMessage("Enter the destination class: ");
        String destinationToChange = scanner.nextLine().trim();

        // Ask for the current relationship type
        RelationshipType currentType = null;
        while (currentType == null) {
            view.displayMessage(
                    "Enter the current relationship type (Aggregation, Composition, Inheritance, or Realization): ");
            String currentTypeInput = scanner.nextLine().trim().toUpperCase();

            try {
                currentType = RelationshipType.valueOf(currentTypeInput);
            } catch (IllegalArgumentException e) {
                view.displayMessage("Invalid relationship type. Please try again.");
            }
        }

        // Ask for the new relationship type
        RelationshipType newType = null;
        while (newType == null) {
            view.displayMessage(
                    "Enter the new relationship type (Aggregation, Composition, Inheritance, or Realization): ");
            String newTypeInput = scanner.nextLine().trim().toUpperCase();

            try {
                newType = RelationshipType.valueOf(newTypeInput);
            } catch (IllegalArgumentException e) {
                view.displayMessage("Invalid relationship type. Please try again.");
            }
        }

        // Call the method to change the relationship type
        if (umlEditor.changeRelationshipType(sourceToChange, destinationToChange, currentType, newType)) {
            view.displayMessage("Relationship between '" + sourceToChange + "' and '" + destinationToChange
                    + "' has been changed from '" + currentType + "' to '" + newType + "'.");
        } else {
            view.displayMessage("Failed to change relationship type. It may not exist or already exists.");
        }
    }

    /**
     * Handles saving the UML model to a JSON file.
     * Prompts the user to enter a filename and saves the current UML data.
     */
    public void handleSave() {
        // Saves the current UML data to a JSON file
        System.out.println("Enter a filename to save to: ");
        String saveFilename = scanner.nextLine().trim();

        try {
            JsonUtils.save(model, saveFilename);
            System.out.println("Data saved to '" + saveFilename + "'.");
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    /**
     * Handles loading a UML model from a JSON file.
     * Prompts the user to enter a filename and loads the UML data.
     */
    public void handleLoad() {
        // Loads UML data from a JSON file
        System.out.println("Enter a filename to load from: ");
        String loadFilename = scanner.nextLine().trim();

        try {
            model = JsonUtils.load(loadFilename);
            System.out.println("Data loaded from '" + loadFilename + "'.");
        } catch (IOException e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
    }

    /**
     * Handles listing all classes in the UML model.
     * Displays each class to the user.
     */
    public void handleListClasses() {
        // For every class that the model has, tell the view 
        //  to display it to the user.
        for (UmlClass umlClass : model.getClassesValues()) {
            view.displayMessage(umlClass.toString());
        }
    }

    /**
     * Handles listing the details of a specified class.
     * Prompts the user to enter the class name and displays its methods.
     */
    public void handleListClass() {
        // Lists the methods of a specified class
        System.out.println("Enter the class name to list: ");
        String listClassName = scanner.nextLine().trim();
        
        UmlClass umlClass = model.getUmlClass(listClassName);
        if (umlClass != null) {
            view.displayMessage(umlClass.toString());
        } else {
            view.displayMessage("Class '" + listClassName + "' does not exist.");
        }
    }

    /**
     * Handles listing all relationships between classes in the UML model.
     * Displays each relationship to the user.
     */
    public void handleListRelationships() {
        // Lists all relationships between classes
       for (UmlRelationship relationship : model.getRelationships()) {
            view.displayMessage(relationship.toString());
        }
    }

}
