package app;

import java.util.Objects;

/**
 * Represents a UML relationship between two classes.
 * Each relationship has a source class and a destination class.
 */
public class UmlRelationship {
	private String source;      // The source class in the relationship
	private String destination; // The destination class in the relationship

	/**
	 * Constructs a new UML relationship with the specified source and destination.
	 * 
	 * @param source the name of the source class in the relationship
	 * @param destination the name of the destination class in the relationship
	 */
	public UmlRelationship(String source, String destination) {
		this.source = source;
		this.destination = destination;
	}

	/**
	 * Gets the source class of the relationship.
	 * 
	 * @return the name of the source class
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Gets the destination class of the relationship.
	 * 
	 * @return the name of the destination class
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Returns a string representation of the UML relationship.
	 * 
	 * @return a string in the format "Relationship from 'source' to 'destination'"
	 */
	@Override
	public String toString() {
		return "Relationship from '" + source + "' to '" + destination + "'";
	}

	/**
	 * Generates a hash code for the UML relationship object.
	 * The hash code is computed based on the source and destination strings.
	 * 
	 * @return an integer representing the hash code of the object
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
	 * Compares this UML relationship with another object for equality.
	 * Two UML relationships are considered equal if they have the same source
	 * and destination classes.
	 * 
	 * @param obj the object to be compared
	 * @return true if the objects are equal, false otherwise
	 */
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
		UmlRelationship other = (UmlRelationship) obj;
		
		// Compare the source fields for equality
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		
		// Compare the destination fields for equality
		if (destination == null) {
			if (other.destination != null) {
				return false;
			}
		} else if (!destination.equals(other.destination)) {
			return false;
		}
		
		// If both source and destination are equal, the objects are equal
		return true;
	}
}