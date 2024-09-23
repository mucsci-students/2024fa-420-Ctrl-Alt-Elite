import java.util.HashSet;
import java.util.Set;

/**
 * Represents a UML class with a name and a set of attributes.
 */
public class UmlClass {
    private String name;
    private Set<String> attributes;

    /**
     * Constructs a new UmlClass with the specified name.
     * 
     * @param name the name of the UML class
     */
    public UmlClass(String name) {
        this.name = name;
        this.attributes = new HashSet<>();
    }

    /**
     * Gets the name of the UML class.
     * 
     * @return the name of the class
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the UML class.
     * 
     * @param name the new name to be set for the class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a new attribute to the UML class.
     * 
     * @param attribute the name of the attribute to add
     * @return {@code true} if the attribute was added, {@code false} if the attribute already exists
     */
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    /**
     * Deletes an attribute from the UML class.
     * 
     * @param attribute the name of the attribute to delete
     * @return {@code true} if the attribute was removed, {@code false} if the attribute was not found
     */
    public boolean deleteAttribute(String attribute) {
        return attributes.remove(attribute);
    }

    /**
     * Renames an existing attribute in the UML class.
     * 
     * @param oldName the current name of the attribute to rename
     * @param newName the new name of the attribute
     * @return {@code true} if the attribute was successfully renamed, {@code false} if the old name was not found 
     *         or if the new name already exists
     */
    public boolean renameAttribute(String oldName, String newName) {
        if (attributes.contains(oldName) && !attributes.contains(newName)) {
            attributes.remove(oldName);
            attributes.add(newName);
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the UML class, including its name and attributes.
     * 
     * @return a string containing the class name and its attributes
     */
    @Override
    public String toString() {
        return "Class: " + name + "\nAttributes: " + attributes;
    }
}
