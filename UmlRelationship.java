import java.util.*;

/*
 * Represents the UML Relationship class
 */
class UMLRelationship {
    private String source;
    private String destination;
    private Set<UMLRelationship> relationships;

    /*
     * Constructons a new UMLRelationship with specified name
     */
    public UMLRelationship(String source, String destination) {
        this.source = source;
        this.destination = destination;
        this.relationships = new HashSet<>();
    }

    /*
     * Returns the source
     */
    public String getSource() {
        return source;
    }

    /*
     * Returns the destination 
     */
    public String getDestination() {
        return destination;
    }

    /*
     * Returns a string representation of the UML Class
     */
    @Override
    public String toString() {
        return "Relationship from " + source + " to " + destination;
    }


    /*
     * Add relationship
     * @param destination destination of the class
     * @param source the source of the class
     */
    public void addRelationship(String source, String destination) {
        UMLRelationship relationship = new UMLRelationship(source, destination);
        if (relationships.add(relationship)) {
            System.out.println("Added relationship: " + relationship);
        } else {
            System.out.println("Relationship already exists: " + relationship);
        }
    }

    /*
     * Removes the relationship
     * @param source Source of the class
     * @param destination Destination of the class
     */
    public void removeRelationship(String source, String destination) {
        UMLRelationship relationship = new UMLRelationship(source, destination);
        if (relationships.remove(relationship)) {
            System.out.println("Removed relationship: " + relationship);
        } else {
            System.out.println("Relationship not found: " + relationship);
        }
    }
}


public class UmlRelationship {
    private String source;
    private String destination;

    public UmlRelationship(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Relationship from '" + source + "' to '" + destination + "'";
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		UmlRelationship other = (UmlRelationship) obj;
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (destination == null) {
			if (other.destination != null) {
				return false;
			}
		} else if (!destination.equals(other.destination)) {
			return false;
		}
		
		return true;
	}
}


