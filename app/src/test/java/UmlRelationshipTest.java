import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A test class that checks the functionality of UmlRelationship.
 */
public class UmlRelationshipTest {
	
	/** A UmlRelationship object that will be tested on. */
	private UmlRelationship relationship;
	
	/**
     * Creates an instance of a UmlRelationship object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
    	relationship = new UmlRelationship("ClassA", "ClassB");
    }
	
	/**
	 * Test that the object was initialized correctly.
	 */
    @Test
    @DisplayName ("UmlRelationship: The UmlRelationship object was created properly")
    public void testUmlRelationship() {
        assertEquals("ClassA", relationship.getSource());
        assertEquals("ClassB", relationship.getDestination());
    }

    /**
     * Test that getSource returns the source of the relationship.
     */
    @Test
    @DisplayName ("GetSource: Retrieve the source of the relationship")
    public void testGetSource() {
        assertEquals("ClassA", relationship.getSource());
    }
    
    /**
     * Test that getDestination returns the destination of the relationship.
     */
    @Test
    @DisplayName ("GetDestination: Retrieve the destination of the relationship")
    public void testGetDestination() {
        assertEquals("ClassB", relationship.getDestination());
    }
    
    /**
     * Test that two relationships that are the same equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two relationships that are the same equal each other")
    public void testEquality() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB");
        assertEquals(relationship, relationship2);
    }
    
    /**
     * Test that two relationships that are not the same do not equal each other, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that two relationships, that are not the same, are not equal, failure test")
    public void testNotEquality() {
    	UmlRelationship relationship2 = new UmlRelationship("ClassB", "ClassA");
    	assertNotEquals(relationship, relationship2);
    }

    
    /**
     * Test that the same objects have the same hash code.
     */
    @Test
    @DisplayName ("HashCode: Test that the same objects have the same hash code")
    public void testHashCode() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB");
        assertEquals(relationship.hashCode(), relationship2.hashCode());
    }
    
    /**
     * Test that different objects have different hash code, should fail.
     */
    @Test
    @DisplayName ("HashCode: Test that different objects different hash code, failure test")
    public void testHashCodeDifferent() {
        UmlRelationship relationship2 = new UmlRelationship("ClassB", "ClassA");
        assertNotEquals(relationship.hashCode(), relationship2.hashCode());
    }
}
