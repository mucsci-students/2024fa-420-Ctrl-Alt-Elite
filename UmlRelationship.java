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
}

