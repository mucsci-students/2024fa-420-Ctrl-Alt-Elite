import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlRelationship;

/**
 * A test class that checks the functionality of UmlRelationship.
 */
public class UmlRelationshipTest {
	
	/** A UmlRelationship object that will be tested on. */
	private UmlRelationship relationship;


    /* An AGGREGATION relationship type variable */
    private final RelationshipType type = RelationshipType.Aggregation;

    /* An COMPOSITION relationship type variable */
    private final RelationshipType typeComp = RelationshipType.Composition;
	
	/**
     * Creates an instance of a UmlRelationship object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
        relationship = new UmlRelationship("ClassA", "ClassB", type);
    }
	
/*----------------------------------------------------------------------------------------------------------------*/

	/**
	 * Test that the object was initialized correctly.
	 */
    @Test
    @DisplayName ("UmlRelationship: The UmlRelationship object was created properly")
    public void testUmlRelationship() {
        assertEquals("ClassA", relationship.getSource());
        assertEquals("ClassB", relationship.getDestination());
        assertEquals(type, relationship.getType());
    }

    /**
	 * Test the copy constructor.
	 */
    @Test
    @DisplayName ("UmlRelationship: The UmlRelationship object was copied properly")
    public void testUmlRelationshipCopy() {
        UmlRelationship temp = new UmlRelationship(relationship);
        
        assertEquals("ClassA", temp.getSource());
        assertEquals("ClassB", temp.getDestination());
        assertEquals(type, temp.getType());
    }

/*----------------------------------------------------------------------------------------------------------------*/

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
     * Test that getType returns the type of the relationship.
     */
    @Test
    @DisplayName ("GetType: Retrieve the type of the relationship")
    public void testGetType() {
        assertEquals(type, relationship.getType());
    }

    /**
     * Test that setType alters the type of the relationship.
     */
    @Test
    @DisplayName ("SetType: Alter the type of the relationship")
    public void testSetType() {
        RelationshipType newType = RelationshipType.Composition;
        relationship.setType(newType);
        
        assertEquals(newType, relationship.getType());
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that two relationships that are the same equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two relationships that are the same equal each other")
    public void testEquality() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB", type);
        assertEquals(relationship, relationship2);
    }

    /**
     * Test that when all fields are null the objects are equal
     */
    @Test
    @DisplayName ("Equals: Test that when all fields are null the objects are equal")
    public void testNotEqualitySourceNullBoth() {
        UmlRelationship relationship2 = new UmlRelationship(null, null, null);
        UmlRelationship relationship3 = new UmlRelationship(null, null, null);
        assertEquals(relationship2, relationship3);
    }
    
    /**
     * Test that two relationships that are not the same do not equal each other, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that two relationships, that are not the same, are not equal, failure test")
    public void testNotEquality() {
        UmlRelationship relationship2 = new UmlRelationship("ClassB", "ClassA", type);
    	assertNotEquals(relationship, relationship2);
    }

    /**
     * Test that a null relationship is not equal to a non null relationship, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that a null relationship is not equal to a non null relationship, failure test")
    public void testNotEqualityNull() {
        UmlRelationship relationship2 = null;
    	assertNotEquals(relationship, relationship2);
    }

    /**
     * Test that when the classes are different the objects are not the same, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that when the classes are different the relationships are not the same, failure test")
    public void testNotEqualityClassDiff() {
        UmlClass umlClass = new UmlClass("ClassA");
        assertNotEquals(relationship, umlClass);
    }

    /**
     * Test that when the source is null the objects are not equal, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that when the source is null the objects are not equal, failure test")
    public void testNotEqualitySourceNull() {
        UmlRelationship relationship2 = new UmlRelationship(null, "ClassA", type);
        assertNotEquals(relationship2, relationship);
    }

    /**
     * Test that when the destination is null the objects are not equal, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that when the destination is null the objects are not equal, failure test")
    public void testNotEqualityDestinationNull() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", null, type);
        assertNotEquals(relationship2, relationship);
    }

    /**
     * Test that when the destination is different the objects are not equal, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that when the destination is different the objects are not equal, failure test")
    public void testNotEqualityDestinationDiff() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassA", type);
        assertNotEquals(relationship2, relationship);
    }

    /**
     * Test that when the type is null the objects are not equal, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that when the type is null the objects are not equal, failure test")
    public void testNotEqualityTypeNull() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB", null);
        assertNotEquals(relationship2, relationship);
    }

    /**
     * Test that when the type is different the objects are not equal, should fail.
     */
    @Test
    @DisplayName ("Equals: Test that when the type is different the objects are not equal, failure test")
    public void testNotEqualityTypeDiff() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB", typeComp);
        assertNotEquals(relationship2, relationship);
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the same objects have the same hash code.
     */
    @Test
    @DisplayName ("HashCode: Test that the same objects have the same hash code")
    public void testHashCode() {
        UmlRelationship relationship2 = new UmlRelationship("ClassA", "ClassB", type);
        assertEquals(relationship.hashCode(), relationship2.hashCode());
    }

    /**
     * Test making a hash code of the object with null variables.
     */
    @Test
    @DisplayName ("HashCode: Test making a hash code of the object with null variables")
    public void testHashCodeNull() {
        UmlRelationship relationship2 = new UmlRelationship(null, null, null);
        assertNotEquals(relationship2.hashCode(), 0);
    }
    
    /**
     * Test that different objects have different hash code, should fail.
     */
    @Test
    @DisplayName ("HashCode: Test that different objects different hash code, failure test")
    public void testHashCodeDifferent() {
        UmlRelationship relationship2 = new UmlRelationship("ClassB", "ClassA", type);
        assertNotEquals(relationship.hashCode(), relationship2.hashCode());
    }
}
