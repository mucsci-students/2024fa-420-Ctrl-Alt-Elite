package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a UML class with a name and a list of methods.
 */
public class UmlClass {
    private String name;
    private LinkedHashMap<String, String> fields = new LinkedHashMap<>(); // Corrected type to LinkedHashMap
    private ArrayList<Method> methods;
    private Point position; // Position as a Point object
    /** An object to be used in the case that a method has no parameters */
    private List<String[]> parametersNull;

    /**
     * Constructs a new UmlClass with the specified name.
     * 
     * @param name The name of the UML class.
     */
    public UmlClass(String name) {
        this.name = name;
        this.methods = new ArrayList<>();
    }
    
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
        this.parametersNull = new ArrayList<>();
    }

    // Copy constructor
    public UmlClass(UmlClass other) {
        this.name = other.name;
        this.position = (other.position != null) ? new Point(other.position) : new Point(0, 0);
        this.fields = new LinkedHashMap<>(other.fields); // Deep copy of fields
    }

 /*----------------------------------------------------------------------------------------------------------------*/
    // GUI Methods

    public Method getMethod(String methodName) {
        if (methodName == null || methodName.isEmpty()) {
            return null;
        }
    
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
    
        return null; // Return null if the method is not found
    }
    
    /**
     * Return the position of a point.
     * 
     * @return The point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Set a position to a new point.
     * 
     * @param position The new point.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Get the names of all methods in this UML class.
     *
     * @return A list of method names.
     */
    public List<String> getMethodNames() {
        if (methods == null || methods.isEmpty()) {
            return new ArrayList<>(); // Return empty list if no methods
        }
        return methods.stream()
                      .map(Method::getName) // Assumes Method class has a getName() method
                      .collect(Collectors.toList());
    }

    /**
     * Return the list of parameters of a method.
     * 
     * @param methodName The name of the method.
     * @return The list of parameters.
     */
    public List<String[]> getMethodParameters(String methodName) {
        if (methods == null || methods.isEmpty()) {
            return null;
        }

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method.getParameters(); // Assumes Method class has getParameters() method
            }
        }

        return null; // Return null if method is not found
    }

    /**
     * Return the return type of the given method.
     * 
     * @param methodName The name of the method.
     * @return The method's return type.
     */
    public String getMethodReturnType(String methodName) {
        if (methods == null || methods.isEmpty()) {
            return null;
        }

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method.getReturnType(); // Assumes Method class has getReturnType method
            }
        }

        return null; // Return null if method not found
    }
    
    /**
     * Rename a method.
     * 
     * @param oldMethodName The old method name.
     * @param newMethodName The new method name.
     * @return {@code true} if the method name was changed, {@code false} otherwise.
     */
    public boolean renameMethod(String oldMethodName, String newMethodName) {
        for (Method method : methods) {
            if (method.getName().equals(oldMethodName)) {
                if (methods.stream().anyMatch(m -> m.getName().equals(newMethodName))) {
                    return false; // New method name already exists
                }
                method.setName(newMethodName);
                return true; // Method renamed successfully
            }
        }
        return false; // Old method not found
    }
    
    /**
     * Delete a method.
     * 
     * @param methodName The name of the method.
     * @return {@code true} if the method was deleted, {@code false} otherwise.
     */
    public boolean deleteMethod(String methodName) {
        for (Iterator<Method> iterator = methods.iterator(); iterator.hasNext();) {
            Method method = iterator.next();
            if (method.getName().equals(methodName)) {
                iterator.remove(); // Remove the method
                return true;
            }
        }
        return false; // Return false if the method was not found
    }

/*----------------------------------------------------------------------------------------------------------------*/  

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
     * Returns the null parameter object.
     * 
     * @return The null parameter object.
     */
    public List<String[]> getParametersNull() {
        return parametersNull;
    }

    /**
     * Sets the methods parameters to the null parameter object.
     */
    public List<String[]> setParametersNull(List<String[]> parameters) {
        return parameters = parametersNull;
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

            // Add method parameters
            Iterator<String[]> paramIterator = method.getParameters().iterator();
            if (paramIterator.hasNext()) {
                String[] element = paramIterator.next();
                methodString.append(element[0]).append(" ");
                methodString.append(element[1]);
            }
            while (paramIterator.hasNext()) {
                methodString.append(", ");

                String[] element = paramIterator.next();
                methodString.append(element[0]).append(" ");
                methodString.append(element[1]);
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

    /**
     * Sets the list of method objects.
     * 
     * @param methodList The new list of methods.
     */
    public void setMethodsList(ArrayList<Method> methodList) {
        methods = methodList;
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

    // Updates the type of an existing field. Returns true if the field exists and
    // the type is updated, false otherwise.
    public boolean updateFieldType(String fieldName, String newFieldType) {
        if (fields.containsKey(fieldName) && !newFieldType.isEmpty()) {
            fields.put(fieldName, newFieldType); // Update the field's type
            return true; // Type update successful
        }
        return false; // Field does not exist or new type is invalid
    }

    /**
     * Represents a method with a list of parameters.
     */
    public class Method {
        /** The name of the method. */
        private String name;
        /** The return type of the method */
        private String returnType;
        /** A list of parameters, with the name as the key and the type as the value. */
        private List<String[]> parameters;

        /**
         * Creates a new method with a list of parameters.
         * 
         * @param name       The name of the method as provided by the user.
         * @param parameters The parameters that belong to the method.
         * @param returnType The return type of the method.
         */
        public Method(String name, List<String[]> parameters, String returnType) {
            this.name = name;
            this.parameters = new ArrayList<>(parameters);
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
        public List<String[]> getParameters() {
            return parameters;
        }

        /**
         * Changes the list of parameters to a completely new list.
         * 
         * @param parameters The new list of parameters.
         */
        public void setParameters(List<String[]> parameters) {
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
            int index = 0;
            for (String[] element : parameters) {
                if (element[0].equals(parameterPair[0]) && element[1].equals(parameterPair[1])) {
                    parameters.remove(index);
                    return true;
                }

                index++;
            }

            return false; 
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
        public String singleMethodString() {
            StringBuilder methodString = new StringBuilder();
            methodString.append(this.getReturnType()).append(" ");
            methodString.append(this.getName()).append("(");

            // Add method parameters
            Iterator<String[]> paramIterator = this.getParameters().iterator();
            if (paramIterator.hasNext()) {
                String[] element = paramIterator.next();
                methodString.append(element[0]).append(" ");
                methodString.append(element[1]);
            }
            while (paramIterator.hasNext()) {
                methodString.append(", ");

                String[] element = paramIterator.next();
                methodString.append(element[0]).append(" ");
                methodString.append(element[1]);
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
                else {
                    return true;
                }
            } else if (parameters.size() != other.parameters.size()) {
                return false;
            } else {
                Iterator<String[]> iter = parameters.iterator();
                Iterator<String[]> otherIter = other.parameters.iterator();

                while (iter.hasNext()) {
                    String[] type = iter.next();
                    String[] otherType = otherIter.next();

                    if (!type[0].equals(otherType[0])) {
                        return false;
                    }
                }
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
         * Generates a string representation of the method object.
         * 
         * @return A string representation of a Method.
         */
        @Override
        public String toString() {
            String string = "\tMethod: " + returnType + " " + name;
            string = string.concat(" (");
            if (!parameters.isEmpty()) {
                Iterator<String[]> iter = this.getParameters().iterator();

                String[] entry = iter.next();
                string = string.concat(entry[0]);
                string = string.concat(" ");
                string = string.concat(entry[1]);
                for (int i = 1; i < parameters.size(); i++) {
                    string = string.concat(", ");

                    String[] loopEntry = iter.next();
                    string = string.concat(loopEntry[0]);
                    string = string.concat(" ");
                    string = string.concat(loopEntry[1]);
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
    public boolean addMethod(String methodName, List<String[]> parameters, String returnType) {
        // The method must have a name and valid return type
        if (methodName.isEmpty() || methodName.contains(" ") || returnType.isEmpty() || returnType.contains(" ")) {
            return false;
        }

        Method newMethod = new Method(methodName, parameters, returnType);

        Set<String> nameList = new LinkedHashSet<>();
        if (!parameters.isEmpty()) {
            // Check that all of the parameter names are valid
            for (String[] element : parameters) {
                if (element[0].isEmpty() || element[1].isEmpty()
                        || element[0].contains(" ") || element[1].contains(" ")
                        || !nameList.add(element[1])) {
                    return false;
                }
            }
        }

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
    public boolean deleteMethod(String methodName, List<String[]> parameters, String returnType) {
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
    public boolean renameMethod(String oldName, List<String[]> parameters, String returnType, String newName) {
        // If the names are empty, if there are no methods, or if there is white space
        // in the new name, return false.
        if (oldName.isEmpty() || newName.isEmpty() || newName.contains(" ")) {
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
     * Change the return type of a method.
     * 
     * @param methodName The name of the method
     * @param parameters The parameter list of the method
     * @param oldType The old return type
     * @param newType The new return type
     * @return {@code true} if the method's return type was successfully changed, 
     *         {@code false} if the return type could not be changed
     */
    public boolean changeReturnType(String methodName, List<String[]> parameters, String oldType, String newType) {
        if (newType.isEmpty() || newType.contains(" ")) {
            return false;
        }
        
        // Loop through the methods to find and change
        //  the return type of the given method.
        Method testMethod = new Method(methodName, parameters, oldType);
        for (Method method : methods) {
            if (method.equals(testMethod)) {
                method.setReturnType(newType);
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
    public boolean removeParameter(String methodName, List<String[]> parameters, String returnType,
            String[] parameterPair) {
        // If any of the function parameters are invalid, return false.
        // You can't remove a parameter if none exist
        if (methodName.isEmpty() || parameterPair[1].isEmpty() || parameters == getParametersNull()) {
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
    public boolean changeParameters(String methodName, List<String[]> oldParameters, String returnType,
            List<String[]> newParameters) {
        // The method name must be valid and the new parameters
        //  must be different from the old parameters.
        if (methodName.isEmpty() || oldParameters.equals(newParameters)) {
            return false;
        }

        Set<String> nameList = new LinkedHashSet<>();
        if (!newParameters.isEmpty()) {
            // Check that all of the parameter names are valid
            for (String[] element : newParameters) {
                if (element[0].isEmpty() 
                        || element[1].isEmpty()
                        || element[0].contains(" ") 
                        || element[1].contains(" ")
                        || !nameList.add(element[1])) {
                    return false;
                }
            }
        }

        Method newMethod = new Method(methodName, newParameters, returnType);
        // Check that the new method being created does not already exist
        for (Method method : methods) {
            if (method.equals(newMethod)) {
                return false;
            }
        }
        
        Method findMethod = new Method(methodName, oldParameters, returnType);
        // Find the method and replace its parameters
        for (Method method : methods) {
            if (method.equals(findMethod)) {
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