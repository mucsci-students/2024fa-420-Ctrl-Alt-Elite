package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents a UML class with a name and a list of methods.
 */
public class UmlClass {
    private String name;
    private LinkedHashMap<String, String> fields = new LinkedHashMap<>(); // Corrected type to LinkedHashMap
    private ArrayList<Method> methods;
    private Point position; // Position as a Point object

    /**
     * Constructs a new UmlClass with the specified name and position.
     * 
     * @param name     The name of the UML class.
     * @param position The x-y position of the UML class.
     */
    public UmlClass(String name, Point position) {
        this.name = name;
        this.methods = new ArrayList<>();
        this.position = position; // Initialize position
    }

    /**
     * Constructs a new UmlClass with the specified name.
     * 
     * @param name The name of the UML class.
     */
    public UmlClass(String name) {
        this.name = name;
        this.methods = new ArrayList<>();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
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
     * Returns a copy of the fields to prevent modification of the internal fields
     * map.
     * 
     * @return A LinkedHashMap containing the fields.
     */
    public LinkedHashMap<String, String> getFields() {
        return new LinkedHashMap<>(fields); // Return a copy to prevent modification
    }

    /**
     * Gets the list of methods of the UML class in a readable format.
     * 
     * @return A list of strings representing the methods and their parameters.
     */
    public ArrayList<String> getMethods() {
        ArrayList<String> methodsList = new ArrayList<>();

        for (Method method : methods) {
            StringBuilder methodString = new StringBuilder();
            methodString.append(method.getReturnType()).append(" ");
            methodString.append(method.getName()).append("(");

            // TODO test
            // Add method parameters
            Iterator<Map.Entry<String, String>> paramIterator = method.getParameters().entrySet().iterator();
            if (paramIterator.hasNext()) {
                Entry<String, String> element = paramIterator.next();
                methodString.append(element.getValue()).append(" ");
                methodString.append(element.getKey());
            }
            while (paramIterator.hasNext()) {
                methodString.append(", ");

                Entry<String, String> element = paramIterator.next();
                methodString.append(element.getValue()).append(" ");
                methodString.append(element.getKey());
            }

            methodString.append(")");
            methodsList.add(methodString.toString());
        }

        return methodsList;
    }

    /**
     * Gets the list of method objects.
     * 
     * @return The list of method objects for this class
     */
    public ArrayList<Method> getMethodsList() {
        return methods;
    }

    public boolean addField(String fieldType, String fieldName) {
        // Check for invalid input
        if (fieldType.isEmpty() || fieldName.isEmpty() || fieldName.contains(" ") || fields.containsKey(fieldName)) {
            return false;
        }
        // Add field to the map
        fields.put(fieldName, fieldType);
        return true;
    }

    public boolean deleteField(String fieldName) {
        // Check if the field exists
        if (fields.containsKey(fieldName)) {
            // Remove the field from the map
            fields.remove(fieldName);
            return true; // Indicate successful removal
        } else {
            return false; // Indicate that the field was not found
        }
    }

    // Renames an existing field. Returns true if the field was found and renamed,
    // false if the field didn't exist or if the new name is invalid.
    public boolean renameField(String oldName, String newName) {
        // Check for invalid conditions
        if (!fields.containsKey(oldName)) {
            return false; // Field to rename does not exist
        }
        if (fields.containsKey(newName)) {
            return false; // New name already exists
        }
        if (newName.isEmpty() || newName.contains(" ")) {
            return false; // New name is invalid
        }

        // Rename the field
        String fieldType = fields.remove(oldName); // Remove the old entry
        fields.put(newName, fieldType); // Add the new entry with the renamed field
        return true; // Renaming successful
    }

    /**
     * Represents a method with a list of parameters.
     */
    public class Method {
        /** The name of the method. */
        private String name;
        /** A map of parameters, with the name as the key and the type as the value. */
        private Map<String, String> parameters;
        /** The return type of the method */
        private String returnType;

        /**
         * Creates a new method with a list of parameters.
         * 
         * @param name       The name of the method as provided by the user.
         * @param parameters The parameters that belong to the method.
         * @param returnType The return type of the method.
         */
        public Method(String name, Map<String, String> parameters, String returnType) {
            this.name = name;
            this.parameters = new LinkedHashMap<>(parameters);
            this.returnType = returnType;
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
        public Map<String, String> getParameters() {
            return parameters;
        }

        /**
         * Changes the list of parameters to a completely new list.
         * 
         * @param parameters The new list of parameters.
         */
        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        /**
         * Removes a parameter from the list of parameters.
         * 
         * @param paraName The name of the parameter to remove.
         * @return {@code true} if the parameter was removed, {@code false} if the
         *         parameter could not be removed.
         */
        public boolean removeParameter(String[] parameterPair) {
            return parameters.remove(parameterPair[0], parameterPair[1]);
        }

        /**
         * Gets the return type of the method.
         * 
         * @return The return type of the method.
         */
        public String getReturnType() {
            return returnType;
        }

        /**
         * Set a new return type for the method.
         * 
         * @param newReturnType The new return type for the method.
         */
        public void setReturnType(String newReturnType) {
            this.returnType = newReturnType;
        }

        /**
         * Returns a string representation of a single method, with its return type and
         * parameters.
         * 
         * @return The method, its return type, and parameters as a string.
         */
        // TODO refactor this so that it is just the method class toString.
        public String singleMethodString() {
            StringBuilder methodString = new StringBuilder();
            methodString.append(this.getReturnType()).append(" ");
            methodString.append(this.getName()).append("(");

            // Add method parameters
            // TODO test
            Iterator<Map.Entry<String, String>> paramIterator = this.getParameters().entrySet().iterator();
            if (paramIterator.hasNext()) {
                Entry<String, String> element = paramIterator.next();
                methodString.append(element.getValue()).append(" ");
                methodString.append(element.getKey());
            }
            while (paramIterator.hasNext()) {
                methodString.append(", ");

                Entry<String, String> element = paramIterator.next();
                methodString.append(element.getValue()).append(" ");
                methodString.append(element.getKey());
            }

            methodString.append(")");
            return methodString.toString();
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

            // Cast the object to Method for comparison.
            Method other = (Method) obj;

            // Compare the name field for equality.
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }

            // Compare the parameters field for equality.
            if (parameters == null) {
                if (other.parameters != null) {
                    return false;
                }
            } else if (!parameters.keySet().equals(other.parameters.keySet())) {
                return false;
            }

            // If both name and parameter types are equal, the objects are equal.
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
            result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
            return result;
        }

        /**
         * Generates a string representation of the representation object.
         * 
         * @return A string representation of the Method class.
         */
        // TODO test
        @Override
        public String toString() {
            String string = "\tMethod: " + returnType + " " + name;
            string = string.concat(" (");
            if (!parameters.isEmpty()) {
                Iterator<Map.Entry<String, String>> iter = this.getParameters().entrySet().iterator();

                Entry<String, String> entry = iter.next();
                string = string.concat(entry.getValue());
                string = string.concat(" ");
                string = string.concat(entry.getKey());
                for (int i = 1; i < parameters.size(); i++) {
                    string = string.concat(", ");

                    Entry<String, String> loopEntry = iter.next();
                    string = string.concat(loopEntry.getValue());
                    string = string.concat(" ");
                    string = string.concat(loopEntry.getKey());
                }
            }
            string = string.concat(")\n");
            return string;
        }
    }

    /**
     * Adds a new method to the UML class.
     * 
     * @param methodName The name of the method to add.
     * @param parameters The list of parameters for the method.
     * @param returnType The return type of the method to add.
     * @return {@code true} if the method was added, {@code false} if the method
     *         already exists.
     */
    public boolean addMethod(String methodName, Map<String, String> parameters, String returnType) {
        // The method must have a name and valid return type
        if (methodName.isEmpty() || methodName.contains(" ") || returnType.isEmpty() || returnType.contains(" ")) {
            return false;
        }

        if (!parameters.isEmpty()) {
            // Check that all of the parameter names are valid
            for (Map.Entry<String, String> element : parameters.entrySet()) {
                if (element.getKey().isEmpty() || element.getValue().isEmpty()
                        || element.getKey().contains(" ") || element.getValue().contains(" ")) {
                    return false;
                }
            }
        }

        Method newMethod = new Method(methodName, parameters, returnType);
        // Loop through the methods to see if a method that equals
        // the method we are trying to create already exists.
        for (Method method : methods) {
            if (method.equals(newMethod)) {
                return false;
            }
        }

        // Add the new method.
        return methods.add(newMethod);
    }

    /**
     * Deletes a method from the UML class.
     * 
     * @param methodName The name of the method to delete.
     * @param parameters The parameters belong to the method.
     * @param returnType The return type of the method to delete.
     * @return {@code true} if the method was removed, {@code false} if the method
     *         was not found.
     */
    public boolean deleteMethod(String methodName, Map<String, String> parameters, String returnType) {
        // Loop through the methods to find and remove the given method.
        Method testMethod = new Method(methodName, parameters, returnType);

        for (Method method : methods) {
            if (method.equals(testMethod)) {
                return methods.remove(method);
            }
        }

        return false;
    }

    /**
     * Renames an existing method in the UML class.
     * 
     * @param oldName    The current name of the method to rename.
     * @param parameters The parameters belonging to the method.
     * @param returnType The return type of the method to rename.
     * @param newName    The new name of the method.
     * @return {@code true} if the method was successfully renamed, {@code false} if
     *         the new name already exists or if the 'oldname' method was not found
     */
    public boolean renameMethod(String oldName, Map<String, String> parameters, String returnType, String newName) {
        // If the names are empty, if there are no methods, or if there is white space
        // in the new name, return false.
        if (oldName.isEmpty() || newName.isEmpty() || methods.isEmpty() || newName.contains(" ")) {
            return false;
        }

        Method testMethod1 = new Method(newName, parameters, returnType);
        // Loop through the methods to see if a method that equals
        // the method we are trying to create already exists.
        for (Method method : methods) {
            if (method.equals(testMethod1)) {
                return false;
            }
        }

        Method testMethod2 = new Method(oldName, parameters, returnType);
        // Loop through the methods and find the method with
        // the old name and replace it with the new name.
        for (Method method : methods) {
            if (method.equals(testMethod2)) {
                method.setName(newName);
                return true;
            }
        }

        return false;
    }

    /**
     * Remove a parameter from a method.
     * 
     * @param methodName The name of the method of the parameter to remove.
     * @param paraName   The name of the parameter to remove.
     * @return {@code true} if the parameter was able to be removed, {@code false}
     *         if it could not be removed.
     */
    public boolean removeParameter(String methodName, Map<String, String> parameters, String returnType,
            String[] parameterPair) {
        // If any of the function parameters are invalid, return false.
        if (methodName.isEmpty() || parameterPair[1].isEmpty()) {
            return false;
        }

        Method testMethod = new Method(methodName, parameters, returnType);
        // Loop through the methods, find the one we need,
        // and remove the parameter
        for (Method method : methods) {
            if (method.equals(testMethod)) {
                return method.removeParameter(parameterPair);
            }
        }

        return false;
    }

    /**
     * Replace the entire list of parameters with a new
     * list provided by the user.
     * 
     * @param methodName The name of the method that the new parameters are for.
     * @param parameters The new list of parameters for the method.
     * @return {@code true} if the parameters were changed, {@code false} if the
     *         parameters were not changed.
     */
    public boolean changeParameters(String methodName, Map<String, String> oldParameters, String returnType,
            Map<String, String> newParameters) {
        // If the method name is invalid, return false.
        if (methodName.isEmpty()) {
            return false;
        }

        if (!newParameters.isEmpty()) {
            // Check that all of the parameter names are valid
            for (Map.Entry<String, String> element : newParameters.entrySet()) {
                if (element.getKey().isEmpty() || element.getValue().isEmpty()
                        || element.getKey().contains(" ") || element.getValue().contains(" ")) {
                    return false;
                }
            }
        }

        Method testMethod = new Method(methodName, oldParameters, returnType);
        for (Method method : methods) {
            if (method.equals(testMethod)) {
                method.setParameters(newParameters);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Class: ").append(name).append("\n");

        // Add Fields section with types
        stringBuilder.append("\tFields:\n");
        for (Map.Entry<String, String> field : fields.entrySet()) { // Use entrySet() to iterate
            stringBuilder.append("\t\t").append(field.getValue()) // Field type
                    .append(" ").append(field.getKey()) // Field name
                    .append("\n");
        }

        // Add Methods section
        stringBuilder.append("\tMethods:\n");
        for (Method method : methods) {
            stringBuilder.append("\t\t").append(method.singleMethodString())
                    .append("\n");
        }

        return stringBuilder.toString();
    }
}