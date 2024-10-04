import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A test class that checks the functionality of UnlClass.
 */
public class UmlClassTest {
	/** A private UmlClass object for testing. */
    private UmlClass umlClass;

    /**
     * A function to initialize the UmlClass object.
     */
    @BeforeEach
    public void setUp() {
        umlClass = new UmlClass("ClassA");
    }

    /**
     * Test that getName returns the name of the class.
     */
    @Test
    @DisplayName ("GetName: Retrive the name of the class")
    public void testGetName() {
        assertEquals("ClassA", umlClass.getName());
    }
    
    /**
     * Test that setName changes the name of the class.
     */
    @Test
    @DisplayName ("SetName: Set the name of the class")
    public void testSetName() {
        umlClass.setName("ClassB");
        assertEquals("ClassB", umlClass.getName());
    }
    
    //TODO
    /**
     * Test that a method can be successfully added.
     */
    @Test
    @DisplayName ("AddMethod: Add an method to the class")
    public void testAddMethod() {
        assertTrue(umlClass.addMethod("Method1"));
    }
    
    //TODO
    /**
     * Test a duplicate method being added, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a duplicate method, failure test")
    public void testAddMethodNotExist() {
        umlClass.addMethod("Method1");
        assertFalse(umlClass.addMethod("Method1"));
    }

    //TODO
    /**
     * Test deleting a method.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method")
    public void testDeleteMethod() {
        umlClass.addMethod("Method1");
        assertTrue(umlClass.deleteMethod("Method1"));
    }
    
    //TODO
    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete an method that dose not exist, failure test")
    public void testDeleteMethodNotExist() {
        assertFalse(umlClass.deleteMethod("Method1"));
    }

    //TODO
    /**
     * Test renaming a method.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethod() {
        umlClass.addMethod("Method1");
        assertTrue(umlClass.renameMethod("Method1", "Method2"));
    }
    
    //TODO
    /**
     * Test renaming a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename a method that does not exist, failure test")
    public void testRenameMethodNotExist() {
        assertFalse(umlClass.renameMethod("Method1", "Method2"));
    }

    //TODO
    /**
     * Test that toString returns what it should.
     */
    @Test
    @DisplayName ("ToString: Print out the object in the correct way with the correct information")
    public void testToString() {
        umlClass.addMethod("Method1");
        assertEquals("Class: ClassA\nMethods: [Method1]", umlClass.toString().trim());
    }
}
