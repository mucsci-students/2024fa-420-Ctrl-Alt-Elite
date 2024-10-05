import java.util.ArrayList;

//TODO check comments
/**
 * Represents a UML class with a name and a list of methods.
 */
public class UmlClass {
    private String name;
    private ArrayList<Method> methods;

    /**
     * Constructs a new UmlClass with the specified name.
     * 
     * @param name the name of the UML class
     */
    public UmlClass(String name) {
        this.name = name;
        this.methods = new ArrayList<>();
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
     * Repersents a method that has a list of parameters.
     */
    public class Method {
        /** The name of the method. */
        private String name;
        /** A list of parameters. */
        private ArrayList<String> parameters;
        
        /**
         * Creates a new method with a list of parameters.
         * 
         * @param name The name of the method as provided by the user.
         */
        public Method(String name, ArrayList<String> parameters) {
            this.name = name;
            this.parameters = new ArrayList<>(parameters);
        }

        /**
         * Retrieves the name of the method.
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
         * Retrieves the list of parameters.
         * 
         * @return The list of parameters.
         */
        public ArrayList<String> getParameters() {
            return parameters;
        }

        /**
         * Changes the list of parameters to a completely new list.
         * 
         * @param parameters The new list of parameters.
         */
        public void setParameters(ArrayList<String> parameters) {
            this.parameters = parameters;
        }


        @Override
	    public int hashCode() {
		    final int prime = 31;
		    int result = 1;
		    result = prime * result + ((name == null) ? 0 : name.hashCode());
		    result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		    return result;
	    }

        @Override
	    public boolean equals(Object obj) {
            if (this == obj) { // If both references point to the same object, they are equal
                return true;
            }
            if (obj == null) { // If the other object is null, they are not equal
                return false;
            }
            if (getClass() != obj.getClass()) { // If the classes are different, they are not equal
                return false;
            }
            
            // Cast the object to UmlRelationship for comparison
            Method other = (Method) obj;
            
            // Compare the source fields for equality
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }
            
            // Compare the destination fields for equality
            if (parameters == null) {
                if (other.parameters != null) {
                    return false;
                }
            } else if (!parameters.equals(other.parameters)) {
                return false;
            }
            
            // If both source and destination are equal, the objects are equal
            return true;
        }

        //TODO
        @Override
        public String toString() {
            String string = "\tMethod: " + name;
            string = string.concat(" (");
            if (!parameters.isEmpty()) {
                for (int i = 0; i < parameters.size() - 1; i++) {
                    string = string.concat(parameters.get(i));
                    string = string.concat(", ");
                }
                string = string.concat(parameters.getLast());
            }
            string = string.concat(")\n");
            return string;
        }

    }


    //TODO methods cannot share names
    /**
     * Adds a new method to the UML class.
     * 
     * @param methodName the name of the method to add
     * @param parameters the list of parameters for the method
     * @return {@code true} if the method was added, {@code false} if the method already exists
     */
    public boolean addMethod(String methodName, ArrayList<String> parameters) {
        //create a new method object
        //then add that object to the set of methods
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return false;
            }
        }
       
        Method newMethod = new Method(methodName, parameters);
        return methods.add(newMethod);
    }
    
    //TODO methods cannot share names
    /**
     * Deletes a method from the UML class.
     * 
     * @param methodName the name of the method to delete
     * @return {@code true} if the method was removed, {@code false} if the method was not found
     */
    public boolean deleteMethod(String methodName) {
        //remove the method object, by looking for its name, from the set of methods
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return methods.remove(method);
            }
        }

        return false;
    }

    //TODO methods cannot share names
    /**
     * Renames an existing method in the UML class.
     * 
     * @param oldName the current name of the method to rename
     * @param newName the new name of the method
     * @return {@code true} if the method was successfully renamed, {@code false} if the old name was not found 
     *         or if the new name already exists
     */
    public boolean renameMethod(String oldName, String newName) {
        /** If there are no methods to rename, return false */
        if (oldName == null || newName == null || methods.isEmpty()) {
            return false;
        }
        
        /** The index of the method to be renamed. */
        int index = 0;

        for (Method method : methods) {
            String name = method.getName();
            if (name.equals(newName)) {
                return false;
            }
            
            if (name.equals(oldName)) {
                index = methods.indexOf(method);
            }
        }
        
        methods.get(index).setName(newName);
        return true;
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
        //call the method toString on all methods in the set
        String string = new String();
        string = string.concat("Class: " + name + "\n");
        for (Method method : methods) {
            string = string.concat(method.toString());
        }

        return string;
    }
}
