package app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UmlEditor {
    // A map to store UML classes by their name
    private Map<String, UmlClass> classes;
    
    // A set to store relationships between UML classes
    private Set<UmlRelationship> relationships;

    // Constructor initializes the collections for classes and relationships
    public UmlEditor() {
        this.classes = new HashMap<>();
        this.relationships = new HashSet<>();
    }

    // Adds a new class if it doesn't already exist and the name is not null or empty
    public boolean addClass(String name) {
        if (name == null || name.isEmpty() || classes.containsKey(name)) {
            return false;  // Return false if name is null, empty, or class already exists
        }
        classes.put(name, new UmlClass(name));
        return true;
    }

    // Deletes a class and all relationships involving that class
    public boolean deleteClass(String name) {
        if (classes.containsKey(name)) {
            classes.remove(name);
            // Remove relationships involving the class
            relationships.removeIf(rel -> rel.getSource().equals(name) || rel.getDestination().equals(name));
            return true;
        }
        return false;
    }

    public boolean renameClass(String oldName, String newName) {
        // Check for null or empty newName and if oldName exists
        if (oldName == null || newName == null || newName.isEmpty() || !classes.containsKey(oldName)) {
            return false;  // Invalid conditions
        }
        
        // Check if the new name already exists
        if (classes.containsKey(newName)) {
            return false;  // New name already exists
        }
        
        UmlClass umlClass = classes.remove(oldName);
        umlClass.setName(newName);
        classes.put(newName, umlClass);
        
        // Update relationships with the new class name
        Set<UmlRelationship> updatedRelationships = new HashSet<>();
        for (UmlRelationship rel : relationships) {
            if (rel.getSource().equals(oldName)) {
                updatedRelationships.add(new UmlRelationship(newName, rel.getDestination()));
            } else if (rel.getDestination().equals(oldName)) {
                updatedRelationships.add(new UmlRelationship(rel.getSource(), newName));
            } else {
                updatedRelationships.add(rel);
            }
        }
        relationships = updatedRelationships;
        return true;
    }


    // Adds an attribute to a specified class
    public boolean addAttribute(String className, String attribute) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.addAttribute(attribute);
        }
        return false;
    }

    // Deletes an attribute from a specified class
    public boolean deleteAttribute(String className, String attribute) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.deleteAttribute(attribute);
        }
        return false;
    }

    // Renames an attribute in a specified class
    public boolean renameAttribute(String className, String oldName, String newName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.renameAttribute(oldName, newName);
        }
        return false;
    }

    // Adds a relationship between two classes if both exist and are not the same
    public boolean addRelationship(String source, String destination) {
        if (source.equals(destination)) {
            return false;  // Can't relate a class to itself
        }
        if (classes.containsKey(source) && classes.containsKey(destination)) {
            return relationships.add(new UmlRelationship(source, destination));
        }
        return false;  // One or both classes do not exist
    }


    // Deletes a relationship between two classes
    public boolean deleteRelationship(String source, String destination) {
        return relationships.remove(new UmlRelationship(source, destination));
    }

    // Lists all UML classes
    public void listClasses() {
        for (UmlClass umlClass : classes.values()) {
            System.out.println(umlClass);
        }
    }

    // Lists a specific UML class by name
    public void listClass(String name) {
        UmlClass umlClass = classes.get(name);
        if (umlClass != null) {
            System.out.println(umlClass);
        } else {
            System.out.println("Class '" + name + "' does not exist.");
        }
    }

    // Lists all UML relationships
    public void listRelationships() {
        for (UmlRelationship relationship : relationships) {
            System.out.println(relationship);
        }
    }

    // Getters and setters for classes and relationships
    public Map<String, UmlClass> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, UmlClass> classes) {
        this.classes = classes;
    }

    public Set<UmlRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(Set<UmlRelationship> relationships) {
        this.relationships = relationships;
    }
}
