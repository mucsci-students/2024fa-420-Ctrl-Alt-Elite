import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Represents a UML class with a name and a list of methods.
 */
public class UmlClass {
    private String name;
    private LinkedHashSet<String> fields;
    private ArrayList<Method> methods;

    /**
     * Constructs a new UmlClass with the specified name.
     * 
     * @param name The name of the UML class.
     */
    public UmlClass(String name) {
        this.name = name;
        this.methods = new ArrayList<>();
        this.fields = new LinkedHashSet<>();
    }

    /**
     * Gets the name of the UML class.
     * 
     * @return The name of the class.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the UML class.
     * 
     * @param name The new name to be set for the class.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a field to the class.
     * 
     * @param fieldName The name of the field to add.
     * @return {@code true} if the field was added, {@code false} if the field
     *         already exists.
     */
    public boolean addField(String fieldName) {
        return fields.add(fieldName); // Returns false if the field is already present
    }

    /**
     * Deletes a field from the class.
     * 
     * @param fieldName The name of the field to delete.
     * @return {@code true} if the field was removed, {@code false} if the field was
     *         not found.
     */
    public boolean deleteField(String fieldName) {
        return fields.remove(fieldName);
    }

    /**
     * Renames a field in the class.
     */
    public boolean renameField(String oldName, String newName) {
        if (fields.contains(oldName) && !fields.contains(newName)) {
            fields.remove(oldName);
            fields.add(newName);
            return true;
        }
        return false;
    }

    /**
     * Represents a method with a list of parameters.
     */
    public class Method {
        /** The name of the method. */
        private String name;
        /** A list of parameters. */
        private LinkedHashSet<String> parameters;

        /**
         * Creates a new method with a list of parameters.
         * 
         * @param name The name of the method as provided by the user.
         */
        public Method(String name, LinkedHashSet<String> parameters) {
            this.name = name;
            this.parameters = new LinkedHashSet<>(parameters);
        }

        /**
         * Returns the name of the method.
         * 
         * @return The name of the method.
         */
        public String getName() {
            return name;
        }

        /**
         * Changes the name of the method.
         * 
         * @param newName The new name for the method.
         */
        public void setName(String newName) {
            this.name = newName;
        }

        /**
         * Returns the list of parameters.
         * 
         * @return The list of parameters.
         */
        public LinkedHashSet<String> getParameters() {
            return parameters;
        }

        /**
         * Changes the list of parameters to a completely new list.
         * 
         * @param parameters The new list of parameters.
         */
        public void setParameters(LinkedHashSet<String> parameters) {
            this.parameters = parameters;
        }

        /**
         * Removes a parameter from the list of parameters.
         * 
         * @param paraName The name of the parameter to remove.
         */
        public boolean removeParameter(String paraName) {
            return parameters.remove(paraName);
        }

        /**
         * Compares this Method with another object for equality.
         * Two Methods are considered equal if they have the same name
         * and parameter list.
         * 
         * @param obj The object to be compared.
         * @return {@code true} if the objects are equal, {@code false} otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) { // If both references point to the same object, they are equal.
                return true;
            }
            if (obj == null) { // If the other object is null, they are not equal.
                return false;
            }
            if (getClass() != obj.getClass()) { // If the classes are different, they are not equal.
                return false;
            }

            // Cast the object to UmlRelationship for comparison.
            Method other = (Method) obj;

            // Compare the source fields for equality.
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }

            // Compare the destination fields for equality.
            if (parameters == null) {
                if (other.parameters != null) {
                    return false;
                }
            } else if (!parameters.equals(other.parameters)) {
                return false;
            }

            // If both source and destination are equal, the objects are equal.
            return true;
        }

        /**
         * Generates a hash code for the Method object.
         * The hash code is computed based on the name and parameter list.
         * 
         * @return An integer representing the hash code of the object.
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
            return result;
        }

        /**
         * Generates a string representation of the representation object.
         */
        @Override
        public String toString() {
            String string = "\tMethod: " + name;
            string = string.concat(" (");
            if (!parameters.isEmpty()) {
                Iterator<String> iter = parameters.iterator();
                string = string.concat(iter.next());
                for (int i = 1; i < parameters.size(); i++) {
                    string = string.concat(", ");
                    string = string.concat(iter.next());
                }
            }
            string = string.concat(")\n");
            return string;
        }
    }

    // TODO methods can share names, but the parameters must be different
    /**
     * Adds a new method to the UML class.
     * 
     * @param methodName The name of the method to add.
     * @param parameters The list of parameters for the method.
     * @return {@code true} if the method was added, {@code false} if the method
     *         already exists.
     */
    public boolean addMethod(String methodName, LinkedHashSet<String> parameters) {
        // Loop through the methods to see if a method
        // with methodName already exists.
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return false;
            }
        }

        // Add the new method.
        Method newMethod = new Method(methodName, parameters);
        return methods.add(newMethod);
    }

    // TODO methods can share names, but the parameters must be different
    /**
     * Deletes a method from the UML class.
     * 
     * @param methodName The name of the method to delete.
     * @return {@code true} if the method was removed, {@code false} if the method
     *         was not found.
     */
    public boolean deleteMethod(String methodName) {
        // Loop through the methods to find the method
        // that's named "methodName" and remove it.
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return methods.remove(method);
            }
        }

        return false;
    }

    // TODO methods can share names, but the parameters must be different
    /**
     * Renames an existing method in the UML class.
     * 
     * @param oldName The current name of the method to rename.
     * @param newName The new name of the method.
     * @return {@code true} if the method was successfully renamed, {@code false} if
     *         the old name was not found
     *         or if the new name already exists.
     */
    public boolean renameMethod(String oldName, String newName) {
        // If there are no methods to rename, return false
        if (oldName == null || newName == null || methods.isEmpty()) {
            return false;
        }

        // Loop through the methods and check if a method
        // with the new name is already present.
        for (Method method : methods) {
            if (method.getName().equals(newName)) {
                return false;
            }
        }

        // Loop through the methods and find the method with
        // the old name and replace it with the new name.
        for (Method method : methods) {
            if (method.getName().equals(oldName)) {
                method.setName(newName);
            }
        }

        return true;
    }

    // TODO add tests for parameters
    // TODO parameters cannot share names
    /**
     * Remove a parameter from a method.
     * 
     * @return {@code true} if the parameter was able to be removed, {@code false}
     *         if it could not be removed.
     */
    public boolean removeParameter(String methodName, String paraName) {
        // If any of the parameters are invalid, return false.
        if (methodName.isEmpty() || paraName.isEmpty()) {
            return false;
        }

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method.removeParameter(paraName);
            }
        }

        return false;
    }

    // TODO add tests for parameters
    // TODO parameters cannot share names
    /**
     * Replace the entire list of parameters with a new
     * list provided by the user.
     * 
     * @param methodName The name of the method that the new parameters are for.
     * @param parameters The new list of parameters for the method.
     * @return {@code true} if the parameters were changed, {@code false} if the
     *         parameters were not changed.
     */
    public boolean changeParameters(String methodName, LinkedHashSet<String> parameters) {
        // If the method name is invalid, return false.
        // In this case, 'parameters' is allowed to be empty as that means
        // that the user wants the new list to have 0 parameters,
        // which is a valid option.
        if (methodName.isEmpty()) {
            return false;
        }

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                method.setParameters(parameters);
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a string representation of the UML class, including its name,
     * indented fields, and methods along with their parameters.
     * 
     * @return a string containing the class name, fields, and methods
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Class: ").append(name).append("\n");

        // Add Fields section
        stringBuilder.append("\tFields:\n");
        for (String field : fields) {
            stringBuilder.append("\t\t").append(field).append("\n");
        }

        // Add Methods section
        stringBuilder.append("\tMethods:\n");
        for (Method method : methods) {
            stringBuilder.append("\t\t").append(method.getName()).append(" (");

            // Append parameters if they exist
            Iterator<String> iter = method.getParameters().iterator();
            if (iter.hasNext()) {
                stringBuilder.append(iter.next());
            }
            while (iter.hasNext()) {
                stringBuilder.append(", ").append(iter.next());
            }

            stringBuilder.append(")\n");
        }

        return stringBuilder.toString();
    }
}