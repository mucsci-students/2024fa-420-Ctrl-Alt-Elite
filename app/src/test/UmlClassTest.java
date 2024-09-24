package app;

import static org.junit.jupiter.api.Assertions.*;
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
    
    /**
     * Test that an attribute can be successfully added.
     */
    @Test
    @DisplayName ("AddAttribute: Add an attribute to the class")
    public void testAddAttribute() {
        assertTrue(umlClass.addAttribute("Attribute1"));
    }
    
    /**
     * Test a duplicate attribute being added, should fail.
     */
    @Test
    @DisplayName ("AddAttribute: Add a duplicate attribute, failure test")
    public void testAddAttributeNotExist() {
        umlClass.addAttribute("Attribute1");
        assertFalse(umlClass.addAttribute("Attribute1"));
    }

    /**
     * Test deleting an attribute.
     */
    @Test
    @DisplayName ("DeleteAttribute: Delete an attribute")
    public void testDeleteAttribute() {
        umlClass.addAttribute("Attribute1");
        assertTrue(umlClass.deleteAttribute("Attribute1"));
    }
    
    /**
     * Test deleting an attribute that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteAttribute: Delete an attribute that dose not exist, failure test")
    public void testDeleteAttributeNotExist() {
        assertFalse(umlClass.deleteAttribute("Attribute1"));
    }

    /**
     * Test renaming an attribute.
     */
    @Test
    @DisplayName ("RenameAttribute: Rename an attribute")
    public void testRenameAttribute() {
        umlClass.addAttribute("Attribute1");
        assertTrue(umlClass.renameAttribute("Attribute1", "Attribute2"));
    }
    
    /**
     * Test renaming an attribute that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameAttribute: Rename an attribute that does not exist, failure test")
    public void testRenameAttributeNotExist() {
        assertFalse(umlClass.renameAttribute("Attribute1", "Attribute2"));
    }

    /**
     * Test that toString returns what it should.
     */
    @Test
    @DisplayName ("ToString: Print out the object in the correct way with the correct information")
    public void testToString() {
        umlClass.addAttribute("Attribute1");
        assertEquals("Class: ClassA\nAttributes: [Attribute1]", umlClass.toString().trim());
    }
}
