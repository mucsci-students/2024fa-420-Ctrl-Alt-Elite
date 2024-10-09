import java.util.ArrayList;
import java.util.List;

/**
 * Enum to define types of relationships.
 * These include AGGREGATION, COMPOSITION, INHERITANCE, and REALIZATION.
 */
enum RelationshipType {
    AGGREGATION,
    COMPOSITION,
    INHERITANCE,
    REALIZATION
}

// Class representing a Relationship between two entities (e.g., classes).
class UmlRelationship {
    // Source entity (could be a class or any other entity) in the relationship.
    private String source;
    
    // Destination entity in the relationship.
    private String destination;
    
    // Type of relationship (AGGREGATION, COMPOSITION, etc.).
    private RelationshipType type;
    
    // List to store all the relationships created.
    private static List<UmlRelationship> relationships = new ArrayList<>();  

    /**
     * Constructor to create a new Relationship.
     * 
     * @param source - the source entity (e.g., class name).
     * @param destination - the destination entity (e.g., class name).
     * @param type - the type of the relationship.
     */
    public UmlRelationship(String source, String destination, RelationshipType type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
    }

    /**
     * Get the source entity.
     * 
     * @return the source entity (e.g., class name).
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the destination entity.
     * 
     * @return the destination entity (e.g., class name).
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Get the type of the relationship.
     * 
     * @return the relationship type.
     */
    public RelationshipType getType() {
        return type;
    }

    /**
     * Set a new relationship type.
     * 
     * @param newType - the new type of the relationship.
     */
    public void setType(RelationshipType newType) {
        this.type = newType;
    }

    /**
     * Adds a new relationship to the list of relationships.
     * 
     * @param source The source entity.
     * @param destination The destination entity.
     * @param type The type of the relationship.
     * @return {@code true} if the relationship was added, {@code false} if the relationship was not added.
     */
    public static boolean  addRelationship(String source, String destination, RelationshipType type) {
        // If any of the parameters are empty, return false.
        if (source.isEmpty() || destination.isEmpty() || type == null) {
            return false;
        }
        
        // Create a new relationship and add it to the list.
        UmlRelationship relationship = new UmlRelationship(source, destination, type);
        return relationships.add(relationship);
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
    public static boolean deleteRelationship(String source, String destination, RelationshipType type) {
        // If any of the parameters are empty, return false.
        if (source.isEmpty() || destination.isEmpty() || type == null) {
            return false;
        }
        
        // Iterate over the relationships to find a match.
        for (UmlRelationship relationship : relationships) {
            if (relationship.getSource().equals(source) &&
                    relationship.getDestination().equals(destination) &&
                    relationship.getType().equals(type)) {
                return relationships.remove(relationship); // Remove the relationship if found.
            }
        }
        // If no matching relationship is found.
        return false;
    }

    /**
     * Changes the type of an existing relationship.
     * 
     * @param source The source entity.
     * @param destination The destination entity.
     * @param newType The new type to change the relationship to.
     * @return {@code true} if the relationship was changed, {@code false} if the relationship could not be changed.
     */
    public static boolean changeRelationshipType(String source, String destination, RelationshipType newType) {
        // If any of the parameters are empty, return false.
        if (source.isEmpty() || destination.isEmpty() || newType == null) {
            return false;
        }
        
        // Iterate over the relationships to find a match.
        for (UmlRelationship relationship : relationships) {
            if (relationship.getSource().equals(source) &&
                relationship.getDestination().equals(destination)) {
                // Change the type of the relationship if found.
                relationship.setType(newType);
                return true;
            }
        }
        // If no matching relationship is found.
        return false;
    }

    /**
     * Compares this UmlRelationship object with another 
     * UmlRelationship object for equality. Two UmlRelationships are considered 
     * equal if they have the same source, destination, and type.
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
        UmlRelationship other = (UmlRelationship) obj;

        // Compare the source fields for equality.
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }

        // Compare the destination fields for equality.
        if (destination == null) {
            if (other.destination != null) {
                return false;
            }
        } else if (!destination.equals(other.destination)) {
            return false;
        }

        // Compare the type fields for equality.
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }

        // If both source and destination are equal, the objects are equal.
        return true;
    }

    /**
     * Generates a hash code for the UmlRelationship object.
     * The hash code is computed based on the source, destination, and type fields.
     * 
     * @return An integer representing the hash code of the object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * Provides a string representation of the relationship.
     * 
     * @return the string representation of the relationship.
     */
    @Override
    public String toString() {
        return "Relationship from '" + source + "' to '" + destination + "' (" + type + ")";
    }
}