package Controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Memento;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlEditorModel;

public class UmlEditor {
    /** The model that holds the classes and relationships for this Uml Editor */
    private UmlEditorModel model;

    /*----------------------------------------------------------------------------------------------------------------*/
    private Memento memento = new Memento();

    public UmlEditor(UmlEditorModel initialModel) {
        this.model = initialModel; // Use a deep copy here
        memento.saveState(this.model); // Save initial state
    }

    // Undo the last action
    public void undo() {
        UmlEditorModel previousState = memento.undoState();
        if (previousState != null) {
            this.model = previousState;
            System.out.println("Undo performed.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    // Redo the last undone action
    public void redo() {
        UmlEditorModel nextState = memento.redoState();
        if (nextState != null) {
            this.model = nextState;
            System.out.println("Redo performed.");
        } else {
            System.out.println("Nothing to redo.");
        }

    }
    /*----------------------------------------------------------------------------------------------------------------*/
    /* CLASS MANAGEMENT METHODS */
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Adds a new class if it doesn't already exist and the name is not null or
     * empty.
     * 
     * @param name     The name of the new class.
     * @param position The initial position of the new class.
     * @return {@code true} if the class was added, {@code false} otherwise.
     */
    public boolean addClass(String name, Point position) {
        if (name == null || name.isEmpty() || name.contains(" ")) {
            return false;
        }
        return model.addClass(name, position); // Pass the position to the model
    }

    /**
     * Adds a new class if it doesn't already exist and the name is not null or
     * empty.
     * 
     * @param name The name of the new class.
     * @return {@code true} if the class was added, {@code false} otherwise.
     */
    // Example method to add a class
    // Method to add a class
    public boolean addClass(String name) {
        if (name == null || name.isEmpty() || name.contains(" ")) {
            return false;
        }
        return model.addClass(name);
    }

    /**
     * Deletes a class and all relationships involving that class.
     * 
     * @param name The name of the class to be deleted.
     * @return {@code true} if the class was deleted, {@code false} otherwise.
     */
    public boolean deleteClass(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return model.deleteClass(name);
    }

    /**
     * Rename a class to a new name if it doesn't already exist
     * and the name is not null or empty.
     * 
     * @param oldName The class's old name.
     * @param newName The class's new name.
     * @return {@code true} if the class was renamed, {@code false} otherwise.
     */
    public boolean renameClass(String oldName, String newName) {
        if (oldName == null || oldName.isEmpty() || newName == null || newName.isEmpty() || newName.contains(" ")) {
            return false;
        }

        return model.renameClass(oldName, newName);
    }

    /**
     * Retrieves a UML class given a name.
     * 
     * @param name The name of the UML class to retrieve.
     * @return The UML class if found, {@code null} otherwise.
     */
    public UmlClass getClass(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return model.getUmlClass(name);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /* FIELD MANAGEMENT METHODS */
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Retrieves the list of field names for a given class.
     *
     * @param className The name of the class whose fields are to be retrieved.
     * @return A list of field names for the specified class. If the class does not
     *         exist,
     *         an empty list is returned.
     */
    public List<String> getFields(String className) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return new ArrayList<>(umlClass.getFields().keySet()); // Return only field names
        }
        return Collections.emptyList(); // Return empty list if class doesn't exist
    }

    /**
     * Adds a new field to a specified class.
     *
     * @param className The name of the class to which the field will be added.
     * @param fieldType The type of the field to be added (e.g., int, String).
     * @param fieldName The name of the field to be added.
     * @return {@code true} if the field was successfully added,
     *         {@code false} if the field already exists or the class was not found.
     */
    public boolean addField(String className, String fieldType, String fieldName) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            System.out.println(
                    "Attempting to add field: " + fieldName + " of type: " + fieldType + " to class: " + className);
            boolean result = umlClass.addField(fieldType, fieldName);
            if (!result) {
                System.out.println("Field '" + fieldName + "' already exists in class '" + className + "'.");
            }
            return result;
        }
        System.out.println("Class '" + className + "' not found.");
        return false;
    }

    /**
     * Delete a field from a class.
     * 
     * @param className The name of the class in which the field will be deleted.
     * @param fieldName The name of the field.
     * @return {@code true} if the field was deleted, {@code false} otherwise.
     */
    public boolean deleteField(String className, String fieldName) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.deleteField(fieldName);
        }
        return false;
    }

    /**
     * Rename a field in a class.
     * 
     * @param className    The name of the class in which the field will be renamed.
     * @param oldFieldName The old name of the field.
     * @param newFieldName The new name of the field.
     * @return {@code true} if the field was renamed, {@code false} otherwise.
     */
    public boolean renameField(String className, String oldFieldName, String newFieldName) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.renameField(oldFieldName, newFieldName);
        }
        return false;
    }

    /**
     * Updates the type of an existing field in a specified class.
     *
     * @param className The name of the class containing the field to be updated.
     * @param fieldName The name of the field to be updated.
     * @param newFieldType The new type for the field.
     * @return {@code true} if the field type was successfully updated,
     *         {@code false} if the field was not found or the new type is invalid.
     */
    public boolean updateFieldType(String className, String fieldName, String newFieldType) {
        // Validate input
        if (newFieldType.isEmpty()) {
            System.out.println("Field type cannot be empty.");
            return false;
        }
        
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            System.out.println("Attempting to update field: " + fieldName + " to type: " + newFieldType + " in class: " + className);
            boolean result = umlClass.updateFieldType(fieldName, newFieldType);
            if (!result) {
                System.out.println("Field '" + fieldName + "' not found in class '" + className + "' or failed to update type.");
            }
            return result;
        }
        System.out.println("Class '" + className + "' not found.");
        return false;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /* METHOD MANAGEMENT METHODS */
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Adds a method to a specified class.
     * 
     * @param className  The name of the class in which the method will be added.
     * @param methodName The name of the method.
     * @param paraList   The list of parameters that belong to the method.
     * @param returnType The return type of the method.
     * @return {@code true} if the method was added, {@code false} otherwise.
     */
    public boolean addMethod(String className, String methodName, List<String[]> paraList, String returnType) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.addMethod(methodName, paraList, returnType);
        }
        return false;
    }

    /**
     * Deletes a method from a specified class.
     * 
     * @param className  The name of the class in which the method will be deleted.
     * @param methodName The name of the method.
     * @param paraList   The list of parameters that belong to the method.
     * @param returnType The return type of the method.
     * @return {@code true} if the method was deleted, {@code false} otherwise.
     */
    public boolean deleteMethod(String className, String methodName, List<String[]> paraList, String returnType) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.deleteMethod(methodName, paraList, returnType);
        }
        return false;
    }

    /**
     * Renames a method in a specified class.
     * 
     * @param className  The name of the class in which the method will be renamed.
     * @param oldName    The old name of the method.
     * @param paraList   The list of parameters that belong to the method.
     * @param returnType The return type of the method.
     * @param newName    The new name of the method.
     * @return {@code true} if the method was renamed, {@code false} otherwise.
     */
    public boolean renameMethod(String className, String oldName, List<String[]> paraList, String returnType,
            String newName) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.renameMethod(oldName, paraList, returnType, newName);
        }
        return false;
    }

    /**
     * Change the return type of a method.
     * 
     * @param className The name of the class in which the method belongs.
     * @param methodName The name of the method
     * @param paraList The list of parameters that belong to the method
     * @param oldType The old return type
     * @param newType The new return type
     * @return {@code true} if the method's return type was changed, {@code false} otherwise.
     */
    public boolean changeReturnType(String className, String methodName, List<String[]> paraList, String oldType, 
                                        String newType) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.changeReturnType(methodName, paraList, oldType, newType);
        }
        return false;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /* PARAMETER MANAGEMENT METHODS */
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Remove a parameter from a method.
     * 
     * @param className  The name of the class in which the method belongs.
     * @param methodName The name of the method in which the parameter belongs.
     * @param parameters The parameters of the method.
     * @param returnType The return type of the method.
     * @param paraName   The name of the parameter to remove.
     * @return {@code true} if the parameter was removed, {@code false} otherwise.
     */
    public boolean removeParameter(String className, String methodName, List<String[]> parameters,
            String returnType, String[] parameterPair) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.removeParameter(methodName, parameters, returnType, parameterPair);
        }
        return false;
    }

    /**
     * Replace the list of parameters of a certain method with a new one.
     * 
     * @param className     The name of the class in which the parameters belong.
     * @param methodName    The name of the method in which the parameters belong.
     * @param oldParameters The old list of parameters.
     * @param returnType    The return type of the method.
     * @param newParameters The new list of parameters for the method.
     * @return {@code true} if the parameters were changed, {@code false} otherwise.
     */
    public boolean changeParameters(String className, String methodName, List<String[]> oldParameters,
            String returnType, List<String[]> newParameters) {
        UmlClass umlClass = model.getUmlClass(className);
        if (umlClass != null) {
            return umlClass.changeParameters(methodName, oldParameters, returnType, newParameters);
        }
        return false;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /* RELATIONSHIP MANAGEMENT METHODS */
    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Adds a relationship between two classes, if both exist and are not the same.
     * 
     * @param source      The source of the relationship.
     * @param destination The destination of the relationship.
     * @param type        The type of relationship.
     * @return {@code true} if the relationship was added, {@code false} otherwise.
     */
    public boolean addRelationship(String source, String destination, RelationshipType type) {
        if (model.classExist(source) && model.classExist(destination)) {
            return model.addRelationship(source, destination, type);
        }
        return false; // One or both classes do not exist
    }

    /**
     * Deletes a relationship between two classes.
     * 
     * @param source      The source of the relationship.
     * @param destination The destination of the relationship.
     * @param type        The type of relationship.
     * @return {@code true} if the relationship was deleted, {@code false}
     *         otherwise.
     */
    public boolean deleteRelationship(String source, String destination, RelationshipType type) {
        if (model.classExist(source) && model.classExist(destination)) {
            return model.deleteRelationship(source, destination, type);
        }
        return false;
    }

    /**
     * Changes the type of an existing relationship.
     * 
     * @param source      The source entity.
     * @param destination The destination entity.
     * @param newType     The new type to change the relationship to.
     * @return {@code true} if the relationship was changed, {@code false} if the
     *         relationship could not be changed.
     */
    public boolean changeRelationshipType(String source, String destination, RelationshipType currentType,
            RelationshipType newType) {
        if (model.classExist(source) && model.classExist(destination)) {
            return model.changeRelationshipType(source, destination, currentType, newType);
        }
        return false;
    }
}

/*----------------------------------------------------------------------------------------------------------------*/