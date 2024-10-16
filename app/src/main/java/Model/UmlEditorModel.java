package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that holds the classes and relationships data structures for a UML Editor
 */
public class UmlEditorModel {
    /** A map to store UML classes by their name */
    private Map<String, UmlClass> classes;

    /** List to store all the relationships created */
    private List<UmlRelationship> relationships;  

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * A model that holds the classes and relationships for the UML Editor.
     */
    public UmlEditorModel () {
        this.classes = new HashMap<>();
        this.relationships = new ArrayList<>();
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
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
    public List<UmlRelationship> getRelationships() {
        return relationships;
    }

    /**
     * Set all of the relationships.
     * 
     * @param relationships The new set of relationship.
     */
    public void setRelationships(List<UmlRelationship> relationships) {
        this.relationships = relationships;
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Returns a UML class from the Map of classes given a name.
     * 
     * @param name The name of the UML class to return.
     * @return The UML class if found, {@code null} otherwise.
     */
    public UmlClass getUmlClass(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        return classes.get(name);
    }

    /**
     * Return weather a class of a certain name exists in the Map of classes.
     * 
     * @param className The name of the class.
     * @return {@code true} if the class name exists in the Map of classes, {@code false} otherwise.
     */
    public boolean classExist(String className) {
        if (className == null || className.isEmpty()) {
            return false;
        }
        
        return classes.containsKey(className);
    }
    
    /**
     * Returns the values from the classes Map.
     * 
     * @return The values from the map of classes
     */
    public Collection<UmlClass> getClassesValues() {
        return classes.values();
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Adds a new class to the Map if the class dose not already exist
     *  and the name of the class is not null or empty.
     * 
     * @param name The name of the new class to add.
     * @return {@code true} if the class was added, {@code false} otherwise.
     */
    public boolean addClass(String name) {
        // Return false if name is null, empty, class already exists, or the name has white space
        if (classes.containsKey(name) || name == null || name.isEmpty() || name.contains(" ")) {
            return false;
        }
        
        classes.put(name, new UmlClass(name));
        return true;
    }

    /**
     * Deletes a class from the Map and all relationships involving that class.
     * 
     * @param name The name of the class to be deleted.
     * @return {@code true} if the class was deleted, {@code false} otherwise.
     */
    public boolean deleteClass(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        
        if (classes.containsKey(name)) {
            classes.remove(name);
            // Remove relationships involving the class
            relationships.removeIf(rel -> rel.getSource().equals(name) || rel.getDestination().equals(name));
            return true;
        }
        return false;
    }

    /**
     * Rename a class in the Map to a new name if it doesn't already exist
     *  and the name is not null or empty.
     * 
     * @param oldName The class's old name.
     * @param newName The class's new name.
     * @return {@code true} if the class was renamed, {@code false} otherwise.
     */
    public boolean renameClass(String oldName, String newName) {
        // Check for null or empty newName, if oldName exists, if the new name already exists
        //  and if the new name has white space
        if (newName == null || newName.isEmpty()
            || !classes.containsKey(oldName) 
            || classes.containsKey(newName) 
            || newName.contains(" ")) {
            return false; // Invalid conditions
        }

        UmlClass umlClass = classes.remove(oldName);
        umlClass.setName(newName);
        classes.put(newName, umlClass);

        // Update relationships with the new class name
        List<UmlRelationship> updatedRelationships = new ArrayList<>();
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

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Adds a new relationship to the list of relationships.
     * 
     * @param source The source entity.
     * @param destination The destination entity.
     * @param type The type of the relationship.
     * @return {@code true} if the relationship was added, {@code false} if the relationship was not added.
     */
    public boolean addRelationship(String source, String destination, RelationshipType type) {
        // If any of the parameters are empty, return false.
        if (source.isEmpty() || destination.isEmpty() || type == null) {
            return false;
        }
        
        // Check that both classes exist.
        if (!classExist(source) || !classExist(destination)) {
            return false;
        }

        // Create a new relationship and add it to the list.
        UmlRelationship newRelationship = new UmlRelationship(source, destination, type);

        // Checks if there is a relationship already equal to the new relationship.
        for (UmlRelationship relationship : relationships) {
            if (relationship.equals(newRelationship)) {
                return false;
            }
        }

        return relationships.add(newRelationship);
    }

    /**
     * Deletes an existing relationship from the list.
     * It looks for a relationship matching the given source, destination, and type.
     * 
     * @param source The source entity.
     * @param destination The destination entity.
     * @param type The type of the relationship.
     * @return {@code true} if the relationship could be deleted, 
     *          {@code false} if the relationship could not be deleted. 
     */
    public boolean deleteRelationship(String source, String destination, RelationshipType type) {
        // If any of the parameters are empty, return false.
        if (source.isEmpty() || destination.isEmpty() || type == null) {
            return false;
        }
        
        // Finds the relationship and deletes it.
        UmlRelationship relationship = findRelationship(source, destination, type);
        if (relationship != null) {
            return relationships.remove(relationship); // Remove the relationship if found.
        }

        // If no matching relationship is found.
        return false;
    }

    /**
     * Changes the type of an existing relationship in the list of relationships.
     * 
     * @param source The source entity.
     * @param destination The destination entity.
     * @param newType The new type to change the relationship to.
     * @return {@code true} if the relationship was changed, {@code false} if the relationship could not be changed.
     */
    public boolean changeRelationshipType(String source, String destination, RelationshipType currentType, RelationshipType newType) {
        // If any of the parameters are empty, return false.
        if (source.isEmpty() || destination.isEmpty() || currentType == null || newType == null) {
            return false;
        }
        
        // Finds the relationship and changes its type to the new type.
        UmlRelationship relationship = findRelationship(source, destination, currentType);
        if (relationship != null) {
            relationship.setType(newType);
            return true;
        }

        // If no matching relationship is found.
        return false;
    }

    /**
     * New helper method that checks source, destination, and type
     * 
     * @param source The source entity.
     * @param destination The destination entity.
     * @param type The type of the relationship.
     * @return The UmlRelationship object that was being searched for, {@code null} otherwise.
     */
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
}

/*----------------------------------------------------------------------------------------------------------------*/
