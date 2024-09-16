import java.util.*;

class UMLRelationship {
    private String source;
    private String destination;
    private Set<UMLRelationship> relationships;

    public UMLRelationship(String source, String destination) {
        this.source = source;
        this.destination = destination;
        this.relationships = new HashSet<>();
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Relationship from " + source + " to " + destination;
    }


    // Add a relationship
    public void addRelationship(String source, String destination) {
        UMLRelationship relationship = new UMLRelationship(source, destination);
        if (relationships.add(relationship)) {
            System.out.println("Added relationship: " + relationship);
        } else {
            System.out.println("Relationship already exists: " + relationship);
        }
    }

    // Remove a relationship
    public void removeRelationship(String source, String destination) {
        UMLRelationship relationship = new UMLRelationship(source, destination);
        if (relationships.remove(relationship)) {
            System.out.println("Removed relationship: " + relationship);
        } else {
            System.out.println("Relationship not found: " + relationship);
        }
    }
}
