package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;

import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlClass.Method;
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
    private UmlEditor umlEditor;
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
                case "change-parameters":
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
        String action = "delete"; // The action that this function will take
        String classToDelete = chooseClass(action); // Call helper to find the class's name
        if (classToDelete == null) { return; } // Stop if chooseClass found an error.

        if (umlEditor.deleteClass(classToDelete)) {
            view.displayMessage("Class '" + classToDelete + "' has been deleted."); // Success
        } else {
            view.displayMessage("Failed to delete class. Name may be invalid or does not exist."); // Fail
        }
    }

    /**
     * Handles the 'rename-class' command by prompting the user for the old class name and new class name and renaming it in the UML editor.
     */
    public void handleRenameClass() {
        // Rename an existing class
        String action = "rename"; // The action that this function will take
        String oldClassName = chooseClass(action); // Call helper to find the class's name
        if (oldClassName == null) { return; } // Stop if chooseClass found an error.

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
        // Add a field to a class
        String action = "add the field to"; // The action that this function will take
        String classToAddField = chooseClass(action); // Call helper to find the class's name
        if (classToAddField == null) { return; } // Stop if chooseClass found an error.

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
        // Delete a field from a class.
        String classAction = "delete the field from"; // The action that this function will take
        String classToDeleteField = chooseClass(classAction); // Call helper to find the class's name
        if (classToDeleteField == null) { return; } // Stop if chooseClass found an error.

        String fieldAction = "delete"; // The action that this function will take
        String fieldToDelete = chooseField(classToDeleteField, fieldAction); // Call helper to find the class's name
        if (fieldToDelete == null) { return; } // Stop if chooseField found an error.

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
        // Rename a field
        String classAction = "rename the field from"; // The action that this function will take
        String classToRenameField = chooseClass(classAction); // Call helper to find the class's name
        if (classToRenameField == null) { return; } // Stop if chooseClass found an error.

        String fieldAction = "rename"; // The action that this function will take
        String oldFieldName = chooseField(classToRenameField, fieldAction); // Call helper to find the class's name
        if (oldFieldName == null) { return; } // Stop if chooseField found an error.

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
        // Add a method to an existing class
        String action = "add the method to"; // The action that this function will take
        String classToAddMethod = chooseClass(action); // Call helper to find the class's name
        if (classToAddMethod == null) { return; } // Stop if chooseClass found an error.
        
        view.displayMessage("Enter the method name: ");
        String methodName = scanner.nextLine().trim();
        view.displayMessage("Enter the parameters for the method (p1, p2, etc.): ");
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
        // Delete a method from a class
        String action = "delete the method from"; // The action that this function will take
        String classToDeleteMethod = chooseClass(action); // Call helper to find the class's name
        if (classToDeleteMethod == null) { return; } // Stop if chooseClass found an error.

        String methodAction = "delete"; // The action that this function will take
        Method methodToDelete = chooseMethod(classToDeleteMethod, methodAction); // Call helper to find the class's name
        if (methodToDelete == null) { return; } // Stop if chooseMethod found an error.

        if (umlEditor.deleteMethod(classToDeleteMethod, methodToDelete.getName(), methodToDelete.getParameters())) {
            view.displayMessage("Method '" + methodToDelete.getName() + "' has been deleted from class '"
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
        String action = "rename the method from"; // The action that this function will take
        String classToRenameMethod = chooseClass(action); // Call helper to find the class's name
        if (classToRenameMethod == null) { return; } // Stop if chooseClass found an error.

        String methodAction = "delete"; // The action that this function will take
        Method oldMethodName = chooseMethod(classToRenameMethod, methodAction); // Call helper to find the class's name
        if (oldMethodName == null) { return; } // Stop if chooseMethod found an error.

        view.displayMessage("Enter the new method name: ");
        String newMethodName = scanner.nextLine().trim();

        if (umlEditor.renameMethod(classToRenameMethod, oldMethodName.getName(), oldMethodName.getParameters(), newMethodName)) {
            view.displayMessage("Method '" + oldMethodName.getName() + "' has been renamed to '" + newMethodName + "' in class '"
                    + classToRenameMethod + "'.");
        } else {
            view.displayMessage("Failed to rename method. Name may be invalid or duplicated, or class does not exist.");
        }
    }
    
    /**
     * Handles removing a parameter from a method by prompting the user for class name,
     * method name, and parameter name, and then removing the parameter from the method.
     */
    //TODO list classes, list methods, list parameters
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
        String action = "change the parameters of a method"; // The action that this function will take
        String classToChangeParameter = chooseClass(action); // Call helper to find the class's name
        if (classToChangeParameter == null) { return; } // Stop if chooseClass found an error.
        
        String methodAction = "change"; // The action that this function will take
        Method methodToChangeParameters = chooseMethod(classToChangeParameter, methodAction); // Call helper to find the class's name
        if (methodToChangeParameters == null) { return; } // Stop if chooseMethod found an error.

        view.displayMessage("Enter the new parameters for the method (p1, p2, p3, etc.): ");
        String parameters = scanner.nextLine().trim();
        
        LinkedHashSet<String> newParaList = new LinkedHashSet<>();
        if (!parameters.trim().isEmpty()) {
            Arrays.stream(parameters.split(","))
                    .map(String::trim)
                    .forEach(newParaList::add);
        }

        if (umlEditor.changeParameters(classToChangeParameter, methodToChangeParameters.getName(), newParaList)) {
            view.displayMessage("Method '" + methodToChangeParameters.getName() + "' had its parameters changed.");
        } else {
            view.displayMessage(
                    "Failed to change parameters. Name or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles adding a relationship between two classes by prompting the user for source class,
     * destination class, and relationship type, and then adding the relationship to the UML editor.
     */
    //TODO list realationship types
    public void handleAddRelationship() {
        // Adds a relationship between two classes
        String sourceAction = "be the source class"; // The action that this function will take
        String source = chooseClass(sourceAction); // Call helper to find the class's name
        if (source == null) { return; } // Stop if chooseClass found an error.

        String destinationAction = "be the destination class"; // The action that this function will take
        String destination = chooseClass(destinationAction); // Call helper to find the class's name
        if (destination == null) { return; } // Stop if chooseClass found an error.

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
    //TODO list relaitonships, list realationship types
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
    //TODO list relaitonships, list realationship types
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
            umlEditor = new UmlEditor(model);
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
        String action = "list"; // The action that this function will take
        String listClassName = chooseClass(action); // Call helper to find the class's name
        if (listClassName == null) { return; } // Stop if chooseClass found an error.
        
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

    /*************************************************************************************************/
    //Helper Functions
    private String chooseClass(String action) {
        Map<String, UmlClass> map = model.getClasses();
        if (map.isEmpty()) {
            view.displayMessage("There are no classes to choose from.");
            return null;
        }

        view.displayMessage("Select the number of the class to " + action + ": ");
        String[] keys = new String[map.size()];
        int index = 0;
        int displayIndex = 1;
        for (Map.Entry<String, UmlClass> mapEntry : map.entrySet()) {
            keys[index] = mapEntry.getKey();
            view.displayMessage("\t" + displayIndex + ". " + keys[index]);
            index++;
            displayIndex++;
        }
        
        int classIndex;
        try {
            classIndex = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                "Class number entered improperly. Please enter a number that corresponds to a class (1, 2, 3, etc.).");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
        scanner.nextLine(); // Clear the buffer

        return keys[(classIndex - 1)];
    }

    private String chooseField(String className, String action) {
        UmlClass fieldClass = model.getClass(className);
        if (fieldClass == null) {
            view.displayMessage("There is no class to choose from.");
            return null;
        }

        LinkedHashSet<String> fieldSet = fieldClass.getFields();
        if (fieldSet.isEmpty()) {
            view.displayMessage("There are no fields to choose from.");
            return null;
        }

        view.displayMessage("Select the number of the field to " + action + ": ");
        String[] fields = new String[fieldSet.size()];
        int index = 0;
        int displayIndex = 1;
        for ( String field : fieldSet) {
            fields[index] = field;
            view.displayMessage("\t" + displayIndex + ". " + fields[index]);
            index++;
            displayIndex++;
        }
        
        int fieldIndex;
        try {
            fieldIndex = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                "Field number entered improperly. Please enter a number that corresponds to a field (1, 2, 3, etc.).");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
        scanner.nextLine(); // Clear the buffer

        return fields[(fieldIndex - 1)];
    }
    
    private Method chooseMethod(String className, String action) {
        UmlClass methodClass = model.getClass(className);
        if (methodClass == null) {
            view.displayMessage("There is no class to choose from.");
            return null;
        }

        ArrayList<Method> methodList = methodClass.getMethodsList();
        if (methodList.isEmpty()) {
            view.displayMessage("There are no methods to choose from.");
            return null;
        }

        view.displayMessage("Select the number of the method to " + action + ": ");
        int displayIndex = 1;
        for (Method method : methodList) {
            view.displayMessage("\t" + displayIndex + ". " + method.singleMethodString());
            displayIndex++;
        }

        int methodIndex;
        try {
            methodIndex = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                "Method number entered improperly. Please enter a number that corresponds to a method (1, 2, 3, etc.).");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
        scanner.nextLine(); // Clear the buffer

        return methodList.get(methodIndex - 1);
    }

    private String chooseParameter(Method method) {
        if (method == null) {
            view.displayMessage("There are no methods to choose from.");
            return null;
        }

        LinkedHashSet<String> parameters = method.getParameters();
        if (parameters.isEmpty()) {
            view.displayMessage("There are no fields to choose from.");
            return null;
        }


        return 0;
    }

    private int chooseRelationship() {
        
        return 0;
    }

    private int chooseRelationshipType() {
        
        return 0;
    }


}
