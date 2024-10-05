import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<String> lst = new ArrayList<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        assertTrue(umlClass.addMethod("Method1", lst));
    }
    
    //TODO
    /**
     * Test a duplicate method being added, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a duplicate method, failure test")
    public void testAddMethodNotExist() {
        ArrayList<String> lst = new ArrayList<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        
        umlClass.addMethod("Method1", lst);
        assertFalse(umlClass.addMethod("Method1", lst));
    }

    //TODO
    /**
     * Test deleting a method.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method")
    public void testDeleteMethod() {
        ArrayList<String> lst = new ArrayList<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        umlClass.addMethod("Method1", lst);
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
        ArrayList<String> lst = new ArrayList<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        umlClass.addMethod("Method1", lst);
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
        ArrayList<String> lst = new ArrayList<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        
        umlClass.addMethod("Method1", lst);
        umlClass.addMethod("Method2", lst);
        assertEquals("Class: ClassA\n\tMethod: Method1 (Para-A, Para-B, Para-C)\n\tMethod: Method2 (Para-A, Para-B, Para-C)", umlClass.toString().trim());
    }
}
