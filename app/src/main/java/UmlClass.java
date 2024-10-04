import java.util.HashSet;
import java.util.Set;

/**
 * Represents a UML class with a name and a set of methods.
 */
public class UmlClass {
    private String name;
    private Set<String> methods;

    /**
     * Constructs a new UmlClass with the specified name.
     * 
     * @param name the name of the UML class
     */
    public UmlClass(String name) {
        this.name = name;
        this.methods = new HashSet<>();
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

    
    //TODO
    /*
     * Adds a new method to the UML class.
     * 
     * @param methodName the name of the method to add
     * @return {@code true} if the method was added, {@code false} if the method already exists
     */
    public boolean addMethod(String methodName) {
        return methods.add(methodName);
    }
    
    //TODO
    /**
     * Deletes a method from the UML class.
     * 
     * @param methodName the name of the method to delete
     * @return {@code true} if the method was removed, {@code false} if the method was not found
     */
    public boolean deleteMethod(String methodName) {
        return methods.remove(methodName);
    }

    //TODO
    /**
     * Renames an existing method in the UML class.
     * 
     * @param oldName the current name of the method to rename
     * @param newName the new name of the method
     * @return {@code true} if the method was successfully renamed, {@code false} if the old name was not found 
     *         or if the new name already exists
     */
    public boolean renameMethod(String oldName, String newName) {
        if (methods.contains(oldName) && !methods.contains(newName)) {
            methods.remove(oldName);
            methods.add(newName);
            return true;
        }
        return false;
    }

   //TODO
    /**
     * Returns a string representation of the UML class, including its name and methods, 
     *  along with their parameters.
     * 
     * @return a string containing the class name and its methods
     */
    @Override
    public String toString() {
        return "Class: " + name + "\nMethods: " + methods;
    }
}
