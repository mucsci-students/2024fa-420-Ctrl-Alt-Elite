import java.util.HashSet;
import java.util.Set;

/**
 * Represents a UML class with a name and a set of attributes.
 */
public class UmlClass {
    // The name of the UML class
    private String name;

    // A set to store the attributes of the UML class. Attributes are unique.
    private Set<String> attributes;

    /**
     * Constructs a new UmlClass with the specified name.
     * Initializes the attributes set as an empty set.
     *
     * @param name the name of the UML class
     */
    public UmlClass(String name) {
        this.name = name;
        this.attributes = new HashSet<>();
    }

    /**
     * Returns the name of the UML class.
     *
     * @return the name of the UML class
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the UML class to a new value.
     *
     * @param name the new name of the UML class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a new attribute to the UML class.
     * 
     * @param attribute the name of the attribute to add
     * @return {@code true} if the attribute was added (didn't exist before), 
     *         {@code false} if the attribute already exists in the class
     */
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    /**
     * Deletes an attribute from the UML class.
     *
     * @param attribute the name of the attribute to delete
     * @return {@code true} if the attribute was successfully removed, 
     *         {@code false} if the attribute did not exist
     */
    public boolean deleteAttribute(String attribute) {
        return attributes.remove(attribute);
    }

    /**
     * Renames an existing attribute in the UML class.
     *
     * @param oldName the current name of the attribute
     * @param newName the new name for the attribute
     * @return {@code true} if the attribute was renamed successfully (i.e., the old attribute existed and 
     *         the new name does not already exist), {@code false} otherwise
     */
    public boolean renameAttribute(String oldName, String newName) {
        // Ensure that the old attribute exists and the new name is not already taken
        if (attributes.contains(oldName) && !attributes.contains(newName)) {
            attributes.remove(oldName);
            attributes.add(newName);
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the UML class, 
     * including its name and attributes.
     *
     * @return a formatted string representing the UML class and its attributes
     */
    @Override
    public String toString() {
        return "Class: " + name + "\nAttributes: " + attributes;
    }
}