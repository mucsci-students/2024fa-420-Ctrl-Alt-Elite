/**
 * The UmlRelationship class represents a relationship between two UML classes.
 * A relationship has a source class and a destination class.
 */
public class UmlRelationship {
    private String source;     
    private String destination;   

    /**
     * Constructs a new UmlRelationship with the specified source and destination.
     * 
     * @param source the name of the source class
     * @param destination the name of the destination class
     */
    public UmlRelationship(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Returns the source class of this relationship.
     * 
     * @return the source class name
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns the destination class of this relationship.
     * 
     * @return the destination class name
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Returns a string representation of this relationship.
     * 
     * @return a string describing the relationship
     */
    @Override
    public String toString() {
        return "Relationship from '" + source + "' to '" + destination + "'";
    }

    /**
     * Generates a hash code for this relationship based on the source and destination.
     * 
     * @return the hash code for this relationship
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        return result;
    }

    /**
     * Compares this relationship to another object for equality.
     * Two relationships are considered equal if they have the same source 
     * and destination.
     * 
     * @param obj the object to be compared for equality
     * @return {@code true} if the specified object is equal to this relationship;
     *         {@code false} otherwise
     */
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