import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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


    public boolean addField(String className, String fieldName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.addField(fieldName);
        }
        return false;
    }

    // Delete a field from a class
    public boolean deleteField(String className, String fieldName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.deleteField(fieldName);
        }
        return false;
    }

    // Rename a field in a class
    public boolean renameField(String className, String oldFieldName, String newFieldName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.renameField(oldFieldName, newFieldName);
        }
        return false;
    }

    // Adds a method to a specified class
    public boolean addMethod(String className, String method, LinkedHashSet<String> paraList) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.addMethod(method, paraList);
        }
        return false;
    }

    // Deletes a method from a specified class
    public boolean deleteMethod(String className, String method, LinkedHashSet<String> paraList) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.deleteMethod(method, paraList);
        }
        return false;
    }

    // Renames a method in a specified class
    public boolean renameMethod(String className, String oldName, LinkedHashSet<String> paraList, String newName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.renameMethod(oldName, paraList, newName);
        }
        return false;
    }

    // Remove a parameter, or multiple, from a method.
    public boolean removeParameter(String className, String methodName, String paraName) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.removeParameter(methodName, paraName);
        }
        return false; 
    }

    // Replace the list of parameters of a certain method with a new one.
    public boolean changeParameters(String className, String methodName, LinkedHashSet<String> parameters) {
        UmlClass umlClass = classes.get(className);
        if (umlClass != null) {
            return umlClass.changeParameters(methodName, parameters);
        }
        return false;
    }

    // Adds a relationship between two classes if both exist and are not the same
    public boolean addRelationship(String source, String destination, RelationshipType type) {
        if (classes.containsKey(source) && classes.containsKey(destination)) {
            return relationships.add(new UmlRelationship(source, destination, type));
        }
        return false;  // One or both classes do not exist
    }

    // Deletes a relationship between two classes
    public boolean deleteRelationship(String source, String destination, RelationshipType type) {
        return relationships.remove(new UmlRelationship(source, destination, type));
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
