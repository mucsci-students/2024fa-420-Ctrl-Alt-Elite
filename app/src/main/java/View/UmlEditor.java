package View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlRelationship;


public class UmlEditor {
    /** A map to store UML classes by their name */
    private Map<String, UmlClass> classes;

    /** A set to store relationships between UML classes */
    private Set<UmlRelationship> relationships;

    /**
     * Constructor initializes the collections for classes and relationships.
     */
    public UmlEditor() {
        this.classes = new HashMap<>();
        this.relationships = new HashSet<>();
    }

    /**
     * Retrieves a UML class by name.
     * 
     * @param name The name of the UML class to retrieve.
     * @return The UML class if found, {@code null} otherwise.
     */
    public UmlClass getClass(String name) {
        return classes.get(name);
    }

    /**
     * Adds a new class if it doesn't already exist and the name is not null or
     * empty.
     * 
     * @param name The name of the new class.
     * @return {@code true} if the class was added, {@code false} otherwise.
     */
    /**
     * Adds a new class if it doesn't already exist and the name is not null or
     * empty.
     * 
     * @param name The name of the new class.
     * @return {@code true} if the class was added, {@code false} otherwise.
     */
    public boolean addClass(String name) {
        if (name == null || name.isEmpty() || classes.containsKey(name)) {
            return false; // Return false if name is null, empty, or class already exists
        }
        classes.put(name, new UmlClass(name));
        return true;
    }

    /**
     * Deletes a class and all relationships involving that class.
     * 
     * @param name The name of the class to be deleted.
     * @return {@code true} if the class was deleted, {@code false} otherwise.
     */
    public boolean deleteClass(String name) {
        if (classes.containsKey(name)) {
            classes.remove(name);
            // Remove relationships involving the class
            relationships.removeIf(rel -> rel.getSource().equals(name) || rel.getDestination().equals(name));
            return true;
        }
        return false;
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
        // Check for null or empty newName and if oldName exists
        if (oldName == null || newName == null || newName.isEmpty() || !classes.containsKey(oldName)) {
            return false; // Invalid conditions
        }

        // Check if the new name already exists
        if (classes.containsKey(newName)) {
            return false; // New name already exists
        }

        UmlClass umlClass = classes.remove(oldName);
        umlClass.setName(newName);
        classes.put(newName, umlClass);

        // Update relationships with the new class name
        Set<UmlRelationship> updatedRelationships = new HashSet<>();
        for (UmlRelationship rel : relationships) {
            if (rel.getSource().equals(oldName)) {
                updatedRelationships.add(new UmlRelationship(newName, rel.getDestination(), rel.getType()));
            } else if (rel.getDestination().equals(oldName)) {
                updatedRelationships.add(new UmlRelationship(rel.getSource(), newName, rel.getType()));
            } else {
                updatedRelationships.add(rel);
            }
        }
        relationships = updatedRelationships;
        return true;
    }

    /**
     * Add a field to a class.
     * 
     * @param className The name of the class in which the field will be added.
     * @param fieldName The name of the field.
     * @return {@code true} if the field was added, {@code false} otherwise.
     */
    public boolean addField(String className, String fieldName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.addField(fieldName);
        }
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
        UmlClass umlClass = classes.get(className);
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
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.renameField(oldFieldName, newFieldName);
        }
        return false;
    }

    /**
     * Adds a method to a specified class.
     * 
     * @param className  The name of the class in which the method will be added.
     * @param methodName The name of the method.
     * @param paraList   The list of parameters that belong to the method.
     * @return {@code true} if the method was added, {@code false} otherwise.
     */
    public boolean addMethod(String className, String methodName, LinkedHashSet<String> paraList) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.addMethod(methodName, paraList);
        }
        return false;
    }

    /**
     * Deletes a method from a specified class.
     * 
     * @param className  The name of the class in which the method will be deleted.
     * @param methodName The name of the method.
     * @param paraList   The list of parameters that belong to the method.
     * @return {@code true} if the method was deleted, {@code false} otherwise.
     */
    public boolean deleteMethod(String className, String methodName, LinkedHashSet<String> paraList) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.deleteMethod(methodName, paraList);
        }
        return false;
    }

    /**
     * Renames a method in a specified class.
     * 
     * @param className The name of the class in which the method will be renamed.
     * @param oldName   The old name of the method.
     * @param paraList  The list of parameters that belong to the method.
     * @param newName   The new name of the method.
     * @return {@code true} if the method was renamed, {@code false} otherwise.
     */
    public boolean renameMethod(String className, String oldName, LinkedHashSet<String> paraList, String newName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.renameMethod(oldName, paraList, newName);
        }
        return false;
    }

    /**
     * Remove a parameter, or multiple, from a method.
     * 
     * @param className  The name of the class in which the method belongs.
     * @param methodName The name of the method in which the parameters belong.
     * @param paraName   The name of the parameter to remove.
     * @return {@code true} if the parameter was removed, {@code false} otherwise.
     */
    public boolean removeParameter(String className, String methodName, String paraName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.removeParameter(methodName, paraName);
        }
        return false;
    }

    /**
     * Replace the list of parameters of a certain method with a new one.
     * 
     * @param className  The name of the class in which the parameters belong.
     * @param methodName The name of the method in which the parameters belong.
     * @param parameters The new list of parameters for the method.
     * @return {@code true} if the parameters were changed, {@code false} otherwise.
     */
    public boolean changeParameters(String className, String methodName, LinkedHashSet<String> parameters) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.changeParameters(methodName, parameters);
        }
        return false;
    }

    /**
     * Adds a relationship between two classes, if both exist and are not the same.
     * 
     * @param source      The source of the relationship.
     * @param destination The destination of the relationship.
     * @param type        The type of relationship.
     * @return {@code true} if the relationship was added, {@code false} otherwise.
     */
    public boolean addRelationship(String source, String destination, RelationshipType type) {
        if (classes.containsKey(source) && classes.containsKey(destination)) {
            return relationships.add(new UmlRelationship(source, destination, type));
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
        return relationships.remove(new UmlRelationship(source, destination, type));
    }

    public boolean changeRelationshipType(String source, String destination, RelationshipType currentType,
            RelationshipType newType) {
        // Find the relationship with the current type
        UmlRelationship relationship = findRelationship(source, destination, currentType);

        // Check if the relationship exists
        if (relationship == null) {
            System.out.println("Relationship not found between '" + source + "' and '" + destination + "'.");
            return false; // Relationship not found
        }

        // Check if the new type already exists for the same relationship
        if (findRelationship(source, destination, newType) != null) {
            System.out.println("A relationship of type '" + newType + "' already exists between '" + source + "' and '"
                    + destination + "'.");
            return false; // Prevent changing to an existing type
        }

        // Change the relationship type
        relationship.setType(newType); // Update the relationship type
        System.out.println("Relationship between '" + source + "' and '" + destination + "' has been changed from '"
                + currentType + "' to '" + newType + "'.");
        return true; // Change successful
    }

    // Existing method that checks only source and destination
    public UmlRelationship findRelationship(String source, String destination) {
        for (UmlRelationship relationship : relationships) {
            if (relationship.getSource().equals(source) &&
                    relationship.getDestination().equals(destination)) {
                return relationship; // Return the found relationship
            }
        }
        return null; // Return null if no relationship is found
    }

    // New overloaded method that checks source, destination, and type
    public UmlRelationship findRelationship(String source, String destination, RelationshipType type) {
        for (UmlRelationship relationship : relationships) {
            if (relationship.getSource().equals(source) &&
                    relationship.getDestination().equals(destination) &&
                    relationship.getType() == type) { // Check for relationship type
                return relationship; // Return the found relationship
            }
        }
        return null; // Return null if no relationship is found
    }

    /**
     * Lists all UML classes.
     */
    public void listClasses() {
        for (UmlClass umlClass : classes.values()) {
            System.out.println(umlClass);
        }
    }

    /**
     * Lists a specific UML class by name.
     * 
     * @param name The name of the UML class.
     */
    public void listClass(String name) {
        UmlClass umlClass = classes.get(name);
        if (umlClass != null) {
            System.out.println(umlClass);
        } else {
            System.out.println("Class '" + name + "' does not exist.");
        }
    }

    /**
     * Lists all UML relationships.
     */
    public void listRelationships() {
        for (UmlRelationship relationship : relationships) {
            System.out.println(relationship);
        }
    }

    /**
     * Get all of the UML classes.
     * 
     * @return The map of UML classes.
     */
    public Map<String, UmlClass> getClasses() {
        return classes;
    }

    /**
     * Set all of the UML classes.
     * 
     * @param classes The new map of classes.
     */
    public void setClasses(Map<String, UmlClass> classes) {
        this.classes = classes;
    }

    /**
     * Get all of the relationships.
     * 
     * @return The set of relationships.
     */
    public Set<UmlRelationship> getRelationships() {
        return relationships;
    }

    /**
     * Set all of the relationships.
     * 
     * @param relationships The new set of relationship.
     */
    public void setRelationships(Set<UmlRelationship> relationships) {
        this.relationships = relationships;
    }

    // Retrieves the fields of a specified UML class by name
    public Set<String> getFields(String className) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.getFields(); // Assuming getFields() returns a Set<String> of field names
        }
        return null; // Return null if the class does not exist
    }
}