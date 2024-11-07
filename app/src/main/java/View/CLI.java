package View;

import Controller.UmlCliController;
import Controller.UmlEditor;
import Model.UmlEditorModel;

public class CLI {

    /**
     * Displays a generic message to the user.
     * 
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a generic message to the user, but with no new line
     *  at the end of the print statement.
     * 
     * @param message The message to display.
     */
    public void displayMessageNoLn(String message) {
        System.out.print(message);
    }

    /**
     * Displays the available commands and their descriptions to the user.
     */
    public void displayHelp() {
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
        System.out.println("remove-parameter - Remove a parameter from a method");
        System.out.println("change-parameters - Replace all parameters for a method");
        System.out.println("add-relationship - Add a relationship between classes");
        System.out.println("delete-relationship - Delete a relationship between classes");
        System.out.println("change-relationship-type - Change the type of relationship between classes");
        System.out.println("list-classes - List all classes and their methods");
        System.out.println("list-class - List the contents of a specified class");
        System.out.println("list-relationships - List all relationships");
        System.out.println("save - Save data to a JSON file");
        System.out.println("load - Load data from a JSON file");
        System.out.println("help - Display this help menu");
        System.out.println("exit - Exit the application");
        System.out.println("undo - undos the last command.");
		System.out.println("redo - goes back to state before previous undo.");
    }

    /**
     * The main entry point for the UML Editor application.
     *
     * Initializes the UML editor, command-line interface, and controller,
     * then starts the application.
     * 
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        UmlEditorModel model = new UmlEditorModel(); // Instantiate the model
        UmlEditor umlEditor = new UmlEditor(model); // Instantiate your UML editor
        CLI cliView = new CLI(); // Instantiate the CLI view
        UmlCliController controller = new UmlCliController(model, umlEditor, cliView); // Create the controller
        controller.start(); // Start the application
    }
}