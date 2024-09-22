package fa.ctrl.alt.elite;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UmlRelationshipTest {

    @Test
    public void testConstructorAndGetters() {
        UmlRelationship relationship = new UmlRelationship("ClassA", "ClassB");
        assertEquals("ClassA", relationship.getSource());
        assertEquals("ClassB", relationship.getDestination());
    }

    @Test
    public void testEquality() {
        UmlRelationship relationship1 = new UmlRelationship("ClassA", "ClassB");
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB");
        UmlRelationship relationship3 = new UmlRelationship("ClassB", "ClassA");
        
        assertEquals(relationship1, relationship2);  // Same source and destination
        assertNotEquals(relationship1, relationship3);  // Different source and destination
    }

    @Test
    public void testHashCode() {
        UmlRelationship relationship1 = new UmlRelationship("ClassA", "ClassB");
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB");
        
        assertEquals(relationship1.hashCode(), relationship2.hashCode());  // Same hash code for equal objects
    }
}
