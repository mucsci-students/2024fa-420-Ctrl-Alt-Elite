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
     * @return the source entity (e.g., class name).
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the destination entity.
     * @return the destination entity (e.g., class name).
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Get the type of the relationship.
     * @return the relationship type.
     */
    public RelationshipType getType() {
        return type;
    }

    /**
     * Set a new relationship type.
     * @param newType - the new type of the relationship.
     */
    public void setType(RelationshipType newType) {
        this.type = newType;
    }

    /**
     * Provides a string representation of the relationship.
     * @return the string representation of the relationship.
     */
    @Override
    public String toString() {
        return "Relationship from '" + source + "' to '" + destination + "' (" + type + ")";
    }

    /**
     * Adds a new relationship to the list of relationships.
     * @param source - the source entity.
     * @param destination - the destination entity.
     * @param type - the type of the relationship.
     */
    public static void addRelationship(String source, String destination, RelationshipType type) {
        // Create a new relationship and add it to the list.
        UmlRelationship relationship = new UmlRelationship(source, destination, type);
        relationships.add(relationship);  
        System.out.println("Relationship added: " + relationship);
    }

    /**
     * Deletes an existing relationship from the list.
     * It looks for a relationship matching the given source, destination, and type.
     * @param source - the source entity.
     * @param destination - the destination entity.
     * @param type - the type of the relationship.
     */
    public static void deleteRelationship(String source, String destination, RelationshipType type) {
        // Iterate over the relationships to find a match.
        for (UmlRelationship relationship : relationships) {
            if (relationship.getSource().equals(source) &&
                relationship.getDestination().equals(destination) &&
                relationship.getType().equals(type)) {
                relationships.remove(relationship);  // Remove the relationship if found.
                System.out.println("Relationship deleted: " + relationship);
                return;
            }
        }
        // If no matching relationship is found.
        System.out.println("Relationship not found.");
    }

    /**
     * Changes the type of an existing relationship.
     * @param source - the source entity.
     * @param destination - the destination entity.
     * @param newType - the new type to change the relationship to.
     */
    public static void changeRelationshipType(String source, String destination, RelationshipType newType) {
        // Iterate over the relationships to find a match.
        for (UmlRelationship relationship : relationships) {
            if (relationship.getSource().equals(source) &&
                relationship.getDestination().equals(destination)) {
                // Change the type of the relationship if found.
                relationship.setType(newType);
                System.out.println("Relationship type changed to: " + relationship);
                return;
            }
        }
        // If no matching relationship is found.
        System.out.println("Relationship not found.");
    }

}