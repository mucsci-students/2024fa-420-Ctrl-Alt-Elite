package Controller;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;

import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlEditorModel;
import Model.UmlRelationship;
import View.CLI;

public class UmlCliController {

    private final UmlEditor umlEditor;
    private final CLI view;
    private final Scanner scanner;
    private UmlEditorModel model;

    public UmlCliController(UmlEditor umlEditor, CLI view) {
        this.umlEditor = umlEditor;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;

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

    // Add a class to the UML editor
    public void handleAddClass() {
        view.displayMessage("Enter the class name: ");
        String className = scanner.nextLine().trim();
        if (umlEditor.addClass(className)) {
            view.displayMessage("Class '" + className + "' has been added.");
        } else {
            view.displayMessage("Failed to add class. Name may be invalid or duplicated.");
        }
    }

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

    // Handle adding a field to a class
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

    // Handle deleting a field from a class
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

    // Handle renaming a field in a class
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

    // Handle adding a method to a class
    public void handleAddMethod() {
        view.displayMessage("Enter the name of the class to add the method to: ");
        String classToAddMethod = scanner.nextLine().trim();
        view.displayMessage("Enter the method name: ");
        String methodName = scanner.nextLine().trim();

        int paraNum;
        view.displayMessage("Enter the number(0, 1, 2, etc.) of parameters for the method: ");
        try {
            paraNum = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Parameter number entered improperly. Please enter a numeral for the parameter count (0, 1, 2, etc.).");
            scanner.nextLine(); // Clear the buffer
            return;
        }
        scanner.nextLine(); // Clear the buffer

        LinkedHashSet<String> paraList = new LinkedHashSet<>();
        for (int i = 1; i <= paraNum; i++) {
            view.displayMessage("Enter the name of parameter " + i + ": ");
            String paraName = scanner.nextLine().trim();
            if (!paraList.add(paraName)) {
                view.displayMessage("Parameter name invalid, please try again.");
                i--; // Decrement i to repeat this iteration
            }
        }

        if (umlEditor.addMethod(classToAddMethod, methodName, paraList)) {
            view.displayMessage("Method '" + methodName + "' added to class '" + classToAddMethod + "'.");
        } else {
            view.displayMessage(
                    "Failed to add method. Name or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

    // Handle deleting a method from a class
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

    public void handleRemoveParameter() {
        // Removes a parameter, or many parameters, from a method
        view.displayMessage("Enter the name of the class of the method with the parameters to remove: ");
        String classToRemoveParameter = scanner.nextLine().trim();
        view.displayMessage("Enter the name of the method with the parameters to remove: ");
        String methodOfParameters = scanner.nextLine().trim();

        view.displayMessage("How many parameters would you like to remove (1, 2, 3, etc.): ");
        int numToRemove;
        try {
            numToRemove = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Parameter number to remove entered improperly. Please enter a numeral for the parameter count (0, 1, 2, etc.).");
            scanner.nextLine(); // Clear the scanner buffer
            return; // Exit the method
        }
        scanner.nextLine(); // Clear the buffer after reading the integer

        for (int i = 0; i < numToRemove; i++) {
            view.displayMessage("Enter the name of the parameter to remove: ");
            String paraName = scanner.nextLine().trim();

            if (umlEditor.removeParameter(classToRemoveParameter, methodOfParameters, paraName)) {
                view.displayMessage("Parameter '" + paraName + "' was removed from '" + methodOfParameters + "'.");
            } else {
                view.displayMessage("Failed to remove parameter. Name may be invalid, or class does not exist.");
                break; // Exit the loop on failure
            }
        }
    }

    public void handleChangeParameter() {
        // Replaces a list of parameters with a new list.
        view.displayMessage("Enter the name of the class of the method with the parameters to change: ");
        String classToChangeParameter = scanner.nextLine().trim();
        view.displayMessage("Enter the name of the method with the parameters to change: ");
        String methodToChangeParameters = scanner.nextLine().trim();

        view.displayMessage(
                "Enter the new number (0, 1, 2, etc.) of parameters for '" + methodToChangeParameters + "': ");
        int newParaNum;
        try {
            newParaNum = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Parameter number entered improperly. Please enter a numeral for the parameter count (0, 1, 2, etc.).");
            scanner.nextLine(); // Clear the scanner buffer
            return; // Exit the method
        }
        scanner.nextLine(); // Clear the buffer after reading the integer

        LinkedHashSet<String> newParaList = new LinkedHashSet<>();
        for (int i = 1; i <= newParaNum; i++) {
            view.displayMessage("Enter the name of parameter " + i + ": ");
            String paraName = scanner.nextLine().trim();
            newParaList.add(paraName);
        }

        if (umlEditor.changeParameters(classToChangeParameter, methodToChangeParameters, newParaList)) {
            view.displayMessage("Method '" + methodToChangeParameters + "' had its parameters changed.");
        } else {
            view.displayMessage(
                    "Failed to change parameters. Name or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

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

    public void handleListClasses() {
        // For every class that the model has, tell the view 
        //  to display it to the user.
        for (UmlClass umlClass : model.getClassesValues()) {
            view.displayMessage(umlClass.toString());
        }
    }

    public void handleListClass() {
        // Lists the methods of a specified class
        System.out.println("Enter the class name to list: ");
        String listClassName = scanner.nextLine().trim();
        
        UmlClass umlClass = model.getClass(listClassName);
        if (umlClass != null) {
            view.displayMessage(umlClass.toString());
        } else {
            view.displayMessage("Class '" + listClassName + "' does not exist.");
        }
    }

    public void handleListRelationships() {
        // Lists all relationships between classes
       for (UmlRelationship relationship : model.getRelationships()) {
            view.displayMessage(relationship.toString());
        }
    }

}
