import java.util.Objects;

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