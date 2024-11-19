package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;

import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlClass.Method;
import Model.UmlEditorModel;
import Model.UmlRelationship;
import View.CLI;

/**
 * The UmlCliController class handles the command-line interface (CLI)
 * interactions
 * for managing a UML diagram. It communicates with the UmlEditorModel to
 * manipulate the data and uses the CLI view to display messages to the user.
 */
public class UmlCliController {

    // Fields
    private UmlEditorModel model;
    private UmlEditor umlEditor;
    private final CLI view;
    private final Scanner scanner;
    private final LineReader reader; // Moved here to be a class field

    private static final Set<String> COMMANDS = new HashSet<>(Arrays.asList(
            "add-class", "delete-class", "rename-class",
            "add-field", "delete-field", "rename-field",
            "add-method", "delete-method", "rename-method",
            "add-parameter", "delete-parameter", "change-parameter",
            "list-classes", "list-class", "list-relationship",
            "undo", "redo", "help", "exit"));

    /**
     * Constructs a new UmlCliController.
     *
     * @param model     The UML editor model used to manage UML data.
     * @param umlEditor The UmlEditor object that provides operations to modify UML
     *                  components.
     * @param view      The CLI view for displaying messages and prompting the user.
     */
    public UmlCliController(UmlEditorModel model, UmlEditor umlEditor, CLI view) {
        this.model = model;
        this.umlEditor = umlEditor;
        this.view = view;
        this.scanner = new Scanner(System.in);
        this.reader = LineReaderBuilder.builder()
                .completer(new StringsCompleter(COMMANDS))
                .build();
    }

    /**
     * Starts the command loop that waits for and processes user input commands.
     */
    public void start() {
        boolean exit = false;

        // Loop until the user chooses to exit
        while (!exit) {
            view.displayMessage("Enter a command (Type 'help' for a list of commands): ");
            String command = reader.readLine().trim();

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
                case "change-field-type":
                    handleChangeFieldType();
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
                case "change-return-type":
                    handleChangeReturnType();
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
                    view.displayMessage("Exiting the program...");
                    break;

                case "undo":
                    handleUndo();
                    break;
                case "redo":
                    handleRedo();
                    break;

                default:
                    view.displayMessage("Invalid command. Type 'help' for options.");
                    break;
            }
        }
        scanner.close(); // Close the scanner when done
    }

    /**
     * Handles the 'add-class' command by prompting the user for a class name and
     * adding it to the UML editor.
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
     * Handles the 'delete-class' command by prompting the user for a class name and
     * deleting it from the UML editor.
     */
    public void handleDeleteClass() {
        // Delete a class from the UML editor
        String action = "delete"; // The action that this function will take
        String classToDelete = chooseClass(action); // Call helper to find the class's name
        if (classToDelete == null) {
            return;
        } // Stop if chooseClass found an error.

        if (umlEditor.deleteClass(classToDelete)) {
            view.displayMessage("Class '" + classToDelete + "' has been deleted."); // Success
        } else {
            view.displayMessage("Failed to delete class. Name may be invalid or does not exist."); // Fail
        }
    }

    /**
     * Handles the 'rename-class' command by prompting the user for the old class
     * name and new class name and renaming it in the UML editor.
     */
    public void handleRenameClass() {
        // Rename an existing class
        String action = "rename"; // The action that this function will take
        String oldClassName = chooseClass(action); // Call helper to find the class's name
        if (oldClassName == null) {
            return;
        } // Stop if chooseClass found an error.

        System.out.println("Enter the new class name: ");
        String newClassName = scanner.nextLine().trim();

        if (umlEditor.renameClass(oldClassName, newClassName)) {
            view.displayMessage("Class '" + oldClassName + "' has been renamed to '" + newClassName + "'.");
        } else {
            view.displayMessage("Failed to rename class. Name may be invalid or duplicated.");
        }
    }

    /**
     * Handles the 'add-field' command by prompting the user for a class, field
     * type, and field name, then adding the field to the class.
     */
    public void handleAddField() {
        // Find the class to add the field to
        String classToAddField = chooseClass("add the field to");
        if (classToAddField == null) {
            return;
        } // Stop if chooseClass found an error.

        // Prompt for field type
        view.displayMessage("Enter the field type: ");
        String fieldType = scanner.nextLine().trim();

        // Validate field type
        if (fieldType.isEmpty()) {
            view.displayMessage("Field type cannot be empty.");
            return;
        }

        // Prompt for field name
        view.displayMessage("Enter the field name: ");
        String fieldName = scanner.nextLine().trim();

        // Validate field name
        if (fieldName.isEmpty() || fieldName.contains(" ")) {
            view.displayMessage("Field name cannot be empty or contain spaces.");
            return;
        }

        // Attempt to add the field to the UML class
        if (umlEditor.addField(classToAddField, fieldType, fieldName)) {
            view.displayMessage(
                    "Field '" + fieldType + " " + fieldName + "' added to class '" + classToAddField + "'.");
        } else {
            view.displayMessage("Failed to add field. Class may not exist or field name already exists.");
        }
    }

    /**
     * Handles the 'delete-field' command by prompting the user for a class and
     * field name and removing the field from the class.
     */
    public void handleDeleteField() {
        // Delete a field from a class.
        String classAction = "delete the field from"; // The action that this function will take
        String classToDeleteField = chooseClass(classAction); // Call helper to find the class's name
        if (classToDeleteField == null) {
            return;
        } // Stop if chooseClass found an error.

        String fieldAction = "delete"; // The action that this function will take
        String fieldToDelete = chooseField(classToDeleteField, fieldAction); // Call helper to find the class's name
        if (fieldToDelete == null) {
            return;
        } // Stop if chooseField found an error.

        if (umlEditor.deleteField(classToDeleteField, fieldToDelete)) {
            view.displayMessage("Field '" + fieldToDelete + "' deleted from class '" + classToDeleteField + "'.");
        } else {
            view.displayMessage("Failed to delete field. Class or field may not exist.");
        }
    }

    /**
     * Handles the 'rename-field' command by prompting the user for a class and old
     * and new field names and renaming the field.
     */
    public void handleRenameField() {
        // Rename a field
        String classAction = "rename the field from"; // The action that this function will take
        String classToRenameField = chooseClass(classAction); // Call helper to find the class's name
        if (classToRenameField == null) {
            return;
        } // Stop if chooseClass found an error.

        String fieldAction = "rename"; // The action that this function will take
        String oldFieldName = chooseField(classToRenameField, fieldAction); // Call helper to find the class's name
        if (oldFieldName == null) {
            return;
        } // Stop if chooseField found an error.

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
     * Handles the 'change-field-type' command by prompting the user for a class,
     * field name, and new field type, then changing the field's type.
     */
    public void handleChangeFieldType() {
        // Find the class to modify the field in
        String classAction = "change the field type of"; // The action that this function will take
        String classToChangeFieldType = chooseClass(classAction); // Call helper to find the class's name
        if (classToChangeFieldType == null) {
            return; // Stop if chooseClass found an error
        }

        // Find the field whose type needs to be changed
        String fieldAction = "change type of"; // The action that this function will take
        String fieldToChangeType = chooseField(classToChangeFieldType, fieldAction); // Call helper to find the field's
                                                                                     // name
        if (fieldToChangeType == null) {
            return; // Stop if chooseField found an error
        }

        // Prompt for the new field type
        view.displayMessage("Enter the new field type: ");
        String newFieldType = scanner.nextLine().trim();

        // Validate the new field type
        if (newFieldType.isEmpty()) {
            view.displayMessage("Field type cannot be empty.");
            return;
        }

        // Attempt to change the field type
        if (umlEditor.updateFieldType(classToChangeFieldType, fieldToChangeType, newFieldType)) {
            view.displayMessage("Field '" + fieldToChangeType + "' in class '" + classToChangeFieldType +
                    "' changed to type '" + newFieldType + "'.");
        } else {
            view.displayMessage("Failed to change field type. Class or field may not exist.");
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
        if (classToAddMethod == null) {
            return;
        } // Stop if chooseClass found an error.

        view.displayMessage("Enter the method name: ");
        String methodName = scanner.nextLine().trim();
        view.displayMessage("Enter the parameters for the method (String p1, int p2, etc.): ");
        String parameters = scanner.nextLine().trim();

        List<String[]> paraList = new ArrayList<>();
        if (!parameters.trim().isEmpty()) {
            String[] splitParameters = parameters.split(",");
            for (String parameter : splitParameters) {
                parameter = parameter.trim();
                String[] splitSpace = parameter.split(" ");
                paraList.add(splitSpace);
            }
        }

        view.displayMessage("Enter the method return type (void, int, etc.): ");
        String returnType = scanner.nextLine().trim();

        if (umlEditor.addMethod(classToAddMethod, methodName, paraList, returnType)) {
            view.displayMessage("Method '" + methodName + "' added to class '" + classToAddMethod + "'.");
        } else {
            view.displayMessage(
                    "Failed to add method. Name, return type, or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles deleting a method from a class by prompting the user for class name,
     * method name, and parameters, and then deleting the method from the UML
     * editor.
     */
    public void handleDeleteMethod() {
        // Delete a method from a class
        String action = "delete the method from"; // The action that this function will take
        String classToDeleteMethod = chooseClass(action); // Call helper to find the class's name
        if (classToDeleteMethod == null) {
            return;
        } // Stop if chooseClass found an error.

        String methodAction = "delete"; // The action that this function will take
        Method methodToDelete = chooseMethod(classToDeleteMethod, methodAction); // Call helper to find the class's name
        if (methodToDelete == null) {
            return;
        } // Stop if chooseMethod found an error.

        if (umlEditor.deleteMethod(classToDeleteMethod, methodToDelete.getName(), methodToDelete.getParameters(),
                methodToDelete.getReturnType())) {
            view.displayMessage("Method '" + methodToDelete.getName() + "' has been deleted from class '"
                    + classToDeleteMethod + "'.");
        } else {
            view.displayMessage("Failed to delete method. Name may be invalid or class does not exist.");
        }
    }

    /**
     * Handles renaming a method in a class by prompting the user for class name,
     * current method name, new method name, and parameters, and then renaming the
     * method.
     */
    public void handleRenameMethod() {
        // Renames a method in a class
        String action = "rename the method from"; // The action that this function will take
        String classToRenameMethod = chooseClass(action); // Call helper to find the class's name
        if (classToRenameMethod == null) {
            return;
        } // Stop if chooseClass found an error.

        String methodAction = "rename"; // The action that this function will take
        Method oldMethod = chooseMethod(classToRenameMethod, methodAction); // Call helper to find the class's name
        if (oldMethod == null) {
            return;
        } // Stop if chooseMethod found an error.

        view.displayMessage("Enter the new method name: ");
        String newMethodName = scanner.nextLine().trim();

        if (umlEditor.renameMethod(classToRenameMethod, oldMethod.getName(), oldMethod.getParameters(),
                oldMethod.getReturnType(), newMethodName)) {
            view.displayMessage(
                    "Method '" + oldMethod.getName() + "' has been renamed to '" + newMethodName + "' in class '"
                            + classToRenameMethod + "'.");
        } else {
            view.displayMessage("Failed to rename method. Name may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles the changing of a method's return type by prompting 
     * the user to choose the class and method, then asking them
     * for the new return type.
     */
    public void handleChangeReturnType() {
        // Change the return type of a method in a class
        String action = "change the return type of"; // The action that this function will take
        String classOfMethod = chooseClass(action); // Call helper to find the class's name
        if (classOfMethod == null) {
            return;
        } // Stop if chooseClass found an error.

        String methodAction = "change the return type"; // The action that this function will take
        Method method = chooseMethod(classOfMethod, methodAction); // Call helper to find the method's name
        if (method == null) {
            return;
        } // Stop if chooseMethod found an error.

        view.displayMessage("Enter the new return type (String, int, etc.): ");
        String newReturnType = scanner.nextLine().trim();

        if (umlEditor.changeReturnType(classOfMethod, method.getName(), method.getParameters(),
            method.getReturnType(), newReturnType)) {
            view.displayMessage(
                    "The return type of method '" + method.getName() + "' has been changed to '" + newReturnType + "' in class '"
                            + classOfMethod + "'.");
        } else {
            view.displayMessage("Failed to change the return type. Name may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles removing a parameter from a method by prompting the user for class
     * name,
     * method name, and parameter name, and then removing the parameter from the
     * method.
     */
    public void handleRemoveParameter() {
        // Removes a parameter from a method
        String action = "remove the parameter from"; // The action that this function will take
        String classToRemoveParameter = chooseClass(action); // Call helper to find the class's name
        if (classToRemoveParameter == null) {
            return;
        } // Stop if chooseClass found an error.

        String methodAction = "remove the parameter from"; // The action that this function will take
        Method methodOfParameter = chooseMethod(classToRemoveParameter, methodAction); // Call helper to find the
                                                                                       // class's name
        if (methodOfParameter == null) {
            return;
        } // Stop if chooseMethod found an error.

        String parameterAction = "remove"; // The action that this function will take
        String[] parameterPair = chooseParameter(methodOfParameter, parameterAction); // Call helper to find the class's
                                                                                      // name
        if (parameterPair == null || parameterPair.length != 2) {
            return;
        } // Stop if chooseParameter found an error.

        if (umlEditor.removeParameter(classToRemoveParameter, methodOfParameter.getName(),
                methodOfParameter.getParameters(),
                methodOfParameter.getReturnType(), parameterPair)) {
            view.displayMessage(
                    "Parameter '" + parameterPair[1] + "' was removed from '" + methodOfParameter.getName() + "'.");
        } else {
            view.displayMessage("Failed to remove parameter. Name may be invalid, or class does not exist.");
        }
    }

    /**
     * Handles changing parameters of a method by prompting the user for class name,
     * method name, and new parameters, and then changing the parameters of the
     * method.
     */
    public void handleChangeParameter() {
        // Replaces a list of parameters with a new list.
        String action = "change the parameters of a method"; // The action that this function will take
        String classToChangeParameter = chooseClass(action); // Call helper to find the class's name
        if (classToChangeParameter == null) {
            return;
        } // Stop if chooseClass found an error.

        String methodAction = "change"; // The action that this function will take
        Method methodToChangeParameters = chooseMethod(classToChangeParameter, methodAction); // Call helper to find the
                                                                                              // class's name
        if (methodToChangeParameters == null) {
            return;
        } // Stop if chooseMethod found an error.

        view.displayMessage("Enter the new parameters for the method (String p1, int p2, etc.): ");
        String parameters = scanner.nextLine().trim();

        List<String[]> newParaList = new ArrayList<>();
        if (!parameters.trim().isEmpty()) {
            String[] splitParameters = parameters.split(",");
            for (String parameter : splitParameters) {
                parameter = parameter.trim();
                String[] splitSpace = parameter.split(" ");
                newParaList.add(splitSpace);
            }
        }

        if (umlEditor.changeParameters(classToChangeParameter, methodToChangeParameters.getName(),
                methodToChangeParameters.getParameters(),
                methodToChangeParameters.getReturnType(), newParaList)) {
            view.displayMessage("Method '" + methodToChangeParameters.getName() + "' had its parameters changed.");
        } else {
            view.displayMessage(
                    "Failed to change parameters. Name or parameters may be invalid or duplicated, or class does not exist.");
        }
    }

    /**
     * Handles adding a relationship between two classes by prompting the user for
     * source class,
     * destination class, and relationship type, and then adding the relationship to
     * the UML editor.
     */
    public void handleAddRelationship() {
        // Adds a relationship between two classes
        String sourceAction = "be the source class"; // The action that this function will take
        String source = chooseClass(sourceAction); // Call helper to find the class's name
        if (source == null) {
            return;
        } // Stop if chooseClass found an error.

        String destinationAction = "be the destination class"; // The action that this function will take
        String destination = chooseClass(destinationAction); // Call helper to find the class's name
        if (destination == null) {
            return;
        } // Stop if chooseClass found an error.

        String typeAction = "add"; // The action that this function will take
        RelationshipType type = chooseRelationshipType(typeAction); // Call helper to find the class's name
        if (type == null) {
            return;
        } // Stop if chooseRelationshipType found an error.

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
        String relationshipAction = "delete"; // The action that this function will take
        UmlRelationship relationship = chooseRelationship(relationshipAction); // Call helper to find the class's name
        if (relationship == null) {
            return;
        } // Stop if chooseRelationship found an error.

        if (umlEditor.deleteRelationship(relationship.getSource(), relationship.getDestination(),
                relationship.getType())) {
            view.displayMessage(
                    "Relationship from '" + relationship.getSource() + "' to '" + relationship.getDestination()
                            + "' of type '" + relationship.getType() + "' has been deleted.");
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
        // Get the relationship
        String relationshipAction = "change the type of"; // The action that this function will take
        UmlRelationship relationship = chooseRelationship(relationshipAction); // Call helper to find the class's name
        if (relationship == null) {
            return;
        } // Stop if chooseRelationship found an error.

        RelationshipType currentType = relationship.getType();

        // Ask for the new relationship type
        String newTypeAction = "change to"; // The action that this function will take
        RelationshipType newType = chooseRelationshipType(newTypeAction); // Call helper to find the class's name
        if (newType == null) {
            return;
        } // Stop if chooseRelationshipType found an error.

        // Call the method to change the relationship type
        if (umlEditor.changeRelationshipType(relationship.getSource(), relationship.getDestination(),
                relationship.getType(), newType)) {
            view.displayMessage(
                    "Relationship between '" + relationship.getSource() + "' and '" + relationship.getDestination()
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
        // to display it to the user.
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
        if (listClassName == null) {
            return;
        } // Stop if chooseClass found an error.

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

    public void handleUndo() {
        umlEditor.undo();
        System.out.println("Undo performed.");
    }

    public void handleRedo() {
        umlEditor.redo();
        System.out.println("Redo performed.");
    }

    /*************************************************************************************************/
    // Helper Functions
    /**
     * Go through all created classes and retrieve the class
     * the user selects.
     * 
     * @param action The action that the calling function is performing.
     * @return The name of the class
     */
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
        for (Map.Entry<String, UmlClass> element : map.entrySet()) {
            keys[index] = element.getKey();
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

        if (classIndex > displayIndex || classIndex < 0) {
            view.displayMessage(
                    "Class number does not exist.");
            return null;
        }

        return keys[(classIndex - 1)];
    }

    /**
     * Go through all created fields and retrieve the field
     * the user selects.
     * 
     * @param className The name of the class that the fields are in
     * @param action    The action that the calling function is performing.
     * @return The name of the field
     */
    private String chooseField(String className, String action) {
        UmlClass fieldClass = model.getUmlClass(className); // Ensure this retrieves the class correctly
        if (fieldClass == null) {
            view.displayMessage("There is no class to choose from.");
            return null;
        }

        // Retrieve fields from the UmlClass as a LinkedHashMap
        LinkedHashMap<String, String> fieldMap = fieldClass.getFields();
        if (fieldMap.isEmpty()) {
            view.displayMessage("There are no fields to choose from.");
            return null;
        }

        view.displayMessage("Select the number of the field to " + action + ": ");
        String[] fields = new String[fieldMap.size()]; // Prepare an array for field names
        int index = 0;
        int displayIndex = 1;

        // Populate the fields array and display them
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            fields[index] = entry.getKey(); // Get the field name (key)
            view.displayMessage("\t" + displayIndex + ". " + fields[index] + " (" + entry.getValue() + ")"); // Show
                                                                                                             // field
                                                                                                             // name and
                                                                                                             // type
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

        if (fieldIndex > displayIndex || fieldIndex < 1) {
            view.displayMessage("Field number does not exist.");
            return null;
        }

        return fields[fieldIndex - 1]; // Return the selected field name
    }

    /**
     * Go through all created methods and retrieve the method
     * the user selects.
     * 
     * @param className The name of the class that the methods are in
     * @param action    The action that the calling function is performing
     * @return The method object the user selected
     */
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

        if (methodIndex > displayIndex || methodIndex < 0) {
            view.displayMessage(
                    "Method number does not exist.");
            return null;
        }

        return methodList.get(methodIndex - 1);
    }

    /**
     * Go through all created parameters and retrieve the parameter
     * the user selects.
     * 
     * @param method The method that the parameter belongs to
     * @param action The action that the calling function is performing
     * @return The string array with the parameter type and name
     */
    private String[] chooseParameter(Method method, String action) {
        if (method == null) {
            view.displayMessage("There are no methods to choose from.");
            return null;
        }

        // The name of the parameter is the key, the type is the value
        List<String[]> parameters = method.getParameters();

        if (parameters.isEmpty()) {
            view.displayMessage("There are no parameters to choose from.");
            return null;
        }

        view.displayMessage("Select the number of the parameter to " + action + ": ");
        int displayIndex = 1;
        for (String[] element : parameters) {
            view.displayMessage("\t" + displayIndex + ". " + element[0] + " " + element[1]);

            displayIndex++;
        }

        int parameterIndex;
        try {
            parameterIndex = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Parameter number entered improperly. Please enter a number that corresponds to a parameter (1, 2, 3, etc.).");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
        scanner.nextLine(); // Clear the buffer

        if (parameterIndex > displayIndex || parameterIndex < 0) {
            view.displayMessage(
                    "Parameter number does not exist.");
            return null;
        }

        return parameters.get(parameterIndex - 1);
    }

    /**
     * Go through all created relationships and retrieve the relationship
     * the user selects.
     * 
     * @param action The action that the calling function is performing
     * @return The UmlRelationship object
     */
    private UmlRelationship chooseRelationship(String action) {
        List<UmlRelationship> relationships = model.getRelationships();
        if (relationships.isEmpty()) {
            view.displayMessage("There are no relationships to choose from.");
            return null;
        }

        view.displayMessage("Select the number of the relationship to " + action + ": ");
        int displayIndex = 1;
        for (UmlRelationship rel : relationships) {
            view.displayMessage("\t" + displayIndex + ". " + rel.toString());
            displayIndex++;
        }

        int relationshipIndex;
        try {
            relationshipIndex = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Relationship number entered improperly. Please enter a number that corresponds to a relationship (1, 2, 3, etc.).");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
        scanner.nextLine(); // Clear the buffer

        if (relationshipIndex > displayIndex || relationshipIndex < 0) {
            view.displayMessage(
                    "Relationship number does not exist.");
            return null;
        }

        return relationships.get(relationshipIndex - 1);
    }

    /**
     * Go through all relationships types and retrieve the type
     * the user selects.
     * 
     * @param action The action that the calling function is performing
     * @return The RelationshipType object
     */
    private RelationshipType chooseRelationshipType(String action) {
        RelationshipType types[] = RelationshipType.values();

        view.displayMessage("Select the number of the type to " + action + ": ");
        int displayIndex = 1;
        for (RelationshipType type : types) {
            view.displayMessage("\t" + displayIndex + ". " + type);
            displayIndex++;
        }

        int typeIndex;
        try {
            typeIndex = scanner.nextInt();
        } catch (Exception e) {
            view.displayMessage(
                    "Number entered improperly. Please enter a number that corresponds to a type (1, 2, 3, etc.).");
            scanner.nextLine(); // Clear the buffer
            return null;
        }
        scanner.nextLine(); // Clear the buffer

        if (typeIndex > displayIndex || typeIndex < 0) {
            view.displayMessage(
                    "Parameter number does not exist.");
            return null;
        }

        return types[(typeIndex - 1)];
    }
}