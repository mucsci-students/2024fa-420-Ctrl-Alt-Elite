package Test;

import static org.junit.jupiter.api.Assertions.*;  // JUnit 5 assertions
import org.junit.jupiter.api.BeforeEach;  // JUnit 5 setup
import org.junit.jupiter.api.DisplayName; // JUnit DisplayName annotation
import org.junit.jupiter.api.Test;  // JUnit 5 Test annotation

import UmlEditor;

/**
 * A test class for the UmlEditor class
 */
public class UmlEditorTest {
	/** A UmlEditor object for that will be tested on. */
    private UmlEditor umlEditor;

    /**
     * Creates an instance of a UmlEditor object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
        umlEditor = new UmlEditor();
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test that the constructor initializes the object properly.
     */
    @Test
    @DisplayName ("Construcntor: Construct a UmlEditor Object ")
    public void testUmlEditor() {
    	assertTrue (((umlEditor.getClasses() != null) && (umlEditor.getRelationships() != null)), 
    			() -> "Could not construct the UmlEditor.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test adding a class.
     */
    @Test
    @DisplayName ("AddClass: Add a class")
    public void testAddClass() {
        assertTrue(umlEditor.addClass("ClassA"), 
        		() -> "Could not add class.");
        assertNotNull(umlEditor.getClasses().get("ClassA"), 
        		() -> "Could not retrive the class.");
    }
    
    /**
     * Test that class names are case sensitive.
     */
    @Test
    @DisplayName ("AddClass: Add a class with the same name as another, just a different case")
    public void testAddClassCaseSensitivityInClassNames() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addClass("classa"), 
        		() -> "Could not add the class, case sensitive test.");
    }
    
    /**
     * Test adding a class with a duplicate name, should fail.
     */
    @Test
    @DisplayName ("AddClass: Add a duplicate class, failure test")
    public void testAddClassDup() {
    	umlEditor.addClass("ClassA");
    	assertNotNull(umlEditor.getClasses().get("ClassA"), 
    			() -> "Could not retrive the class.");
    	assertFalse(umlEditor.addClass("ClassA"), 
    			() -> "Error with adding duplicate class.");
    }
    
    /**
     * Test adding a class with no input, should fail.
     */
    @Test
    @DisplayName ("AddClass: Add a class with no input, failure test")
    public void testAddClassEmpty() {
    	assertFalse(umlEditor.addClass(""),
    			() -> "Error with adding a class with no name.");
    	assertNull(umlEditor.getClasses().get(""), 
    			() -> "Error with assertNull on adding a class with an empty name.");
    }
    
    /**
     * Test adding a class with null as its name, should fail.
     */
    @Test
    @DisplayName ("AddClass: Add a class with \"null\" as its name, failure test")
    public void testAddNullClassName() {
        assertFalse(umlEditor.addClass(null), 
        		() -> "Error with adding a class null as its name.");
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test deleting a class.
     */
    @Test
    @DisplayName ("DeleteClass: Delete a class")
    public void testDeleteClass() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.deleteClass("ClassA"), 
        		() -> "Could not delete the class.");
        assertNull(umlEditor.getClasses().get("ClassA"), 
        		() -> "Error with assertNull on deleting a class.");
    }
    
    /**
     * Test deleting a class that has not been added, should fail.
     */
    @Test
    @DisplayName ("DeleteClass: Delete a class that does not exist, failure test")
    public void testDeleteClassNotExist() {
    	assertFalse(umlEditor.deleteClass("ClassA"), 
    			() -> "Error on deleting a non-existent class.");
    }
    
    /**
     * Test trying to delete a class with invalid input, should fail.
     */
    @Test
    @DisplayName ("DeleteClass: Delete a class with invalid input, failure test")
    public void testDeleteClassInvalid() {
    	assertFalse(umlEditor.deleteClass(" "), 
    			() -> "Error on trying to delete a class with invalid inputs.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/   
    
    /**
     * Test renaming a class.
     */
    @Test
    @DisplayName ("RenameClass: Rename a class")
    public void testRenameClass() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.renameClass("ClassA", "ClassB"), 
        		() -> "Could not rename the class.");
        assertNull(umlEditor.getClasses().get("ClassA"), 
        		() -> "Error with asserting that the old class name does not exist.");
        assertNotNull(umlEditor.getClasses().get("ClassB"), 
        		() -> "Error with asserting that the new class name exists.");
    }
    
    /**
     * Test renaming a class to an empty string, should fail.
     */
    @Test
    @DisplayName ("RenameClass: Rename a class to an empty string, failure test")
    public void testRenameClassToEmptyString() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.renameClass("ClassA", ""), 
        		() -> "Error with renaming a class to an empty string.");
    }
    
    /**
     * Test renaming a class to null, should fail.
     */
    @Test
    @DisplayName ("RenameClass: Rename a class to null, failure test")
    public void testRenameClassToNull() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.renameClass("ClassA", null), 
        		() -> "Error with tyring to rename a class to null.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test adding an attribute to a class.
     */
    @Test
    @DisplayName ("AddAttribute: Add an attribute to a class")
    public void testAddAttribute() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addAttribute("ClassA", "Attribute1"), 
        		() -> "Error with adding attribute.");
    }
    
    /**
     * Test adding an attribute to a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("AddAttribute: Add an attribute to a class that does not exist, failure test")
    public void testAddAttributeFalseClass() {
    	assertFalse(umlEditor.addAttribute("ClassB", "Attribute1"), 
    			() -> "Error with adding to non-existent class.");
    }
    
    /**
     * Test adding duplicate attribute names, should fail.
     */
    @Test
    @DisplayName ("AddAttribute: Add an attribute with a duplicate name, failure test")
    public void testAddDuplicateAttribute() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addAttribute("ClassA", "Attribute1"), 
        		() -> "Error with adding attribute in duplicate attribute test.");
        assertFalse(umlEditor.addAttribute("ClassA", "Attribute1"),
        		() -> "Error with trying to add duplicate attribute.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    //TODO start back here
    /**
     * Test deleting an attribute from a class.
     */
    @Test
    public void testDeleteAttribute() {
        umlEditor.addClass("ClassA");
        umlEditor.addAttribute("ClassA", "Attribute1");
        assertTrue(umlEditor.deleteAttribute("ClassA", "Attribute1"));  // Successfully deleted attribute
        assertFalse(umlEditor.deleteAttribute("ClassA", "Attribute2"));  // Attribute doesn't exist
    }
    
 // Test deleting a non-existent attribute
    @Test
    public void testDeleteNonExistentAttribute() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.deleteAttribute("ClassA", "NonExistentAttribute"));  // Attribute does not exist
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    // Test renaming an attribute
    @Test
    public void testRenameAttribute() {
        umlEditor.addClass("ClassA");
        umlEditor.addAttribute("ClassA", "Attribute1");
        assertTrue(umlEditor.renameAttribute("ClassA", "Attribute1", "Attribute2"));  // Successfully renamed
        assertFalse(umlEditor.renameAttribute("ClassA", "Attribute1", "Attribute3"));  // Old attribute doesn't exist
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    // Test adding a relationship between classes
    @Test
    public void testAddRelationship() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        assertTrue(umlEditor.addRelationship("ClassA", "ClassB"));  // Relationship added
        assertFalse(umlEditor.addRelationship("ClassA", "ClassC"));  // ClassC doesn't exist
    }
    
 // Test adding a relationship between the same class
    @Test
    public void testAddRelationshipBetweenSameClass() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.addRelationship("ClassA", "ClassA"));  // Can't relate a class to itself
    }
    
    // Test adding a relationship between non-existent classes
    @Test
    public void testAddRelationshipNonExistentClasses() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.addRelationship("ClassA", "NonExistentClass"));  // ClassB doesn't exist
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    // Test deleting a relationship between classes
    @Test
    public void testDeleteRelationship() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        umlEditor.addRelationship("ClassA", "ClassB");
        assertTrue(umlEditor.deleteRelationship("ClassA", "ClassB"));  // Relationship deleted
        assertFalse(umlEditor.deleteRelationship("ClassA", "ClassC"));  // Relationship doesn't exist
    }
    
 // Test deleting a relationship between non-existent classes
    @Test
    public void testDeleteRelationshipNonExistentClasses() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.deleteRelationship("ClassA", "NonExistentClass"));  // Relationship doesn't exist
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test listing all classes.
     */
    @Test
    @DisplayName ("ListClasses: List all classes")
    public void testListClasses() {
    	System.out.println("Expected Output: ");
    	System.out.println("Class: ClassA");
        System.out.println("Attributes: []");
        System.out.println("Class: ClassB");
        System.out.println("Attributes: []");
    	
    	umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        
        System.out.println("Output: ");
        umlEditor.listClasses();
        System.out.println();
    }
    
    /**
     * Test listing all classes when there are no classes.
     */
    @Test
    @DisplayName ("ListClasses: Display no classes when there are none")
    public void testListClassesNoClasses() {
    	System.out.println("Expected Output: (no output)");
        
    	System.out.println("Output: ");
        umlEditor.listClasses();
        System.out.println();
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test listing a specific class.
     */
    @Test
    @DisplayName ("ListClass: List a class")
    public void testListClass() {
    	System.out.println("Expected Output: ");
        System.out.println("Class: ClassA");
        System.out.println("Attributes: []");
    	
    	umlEditor.addClass("ClassA");
          
        System.out.println("Output: ");
        umlEditor.listClass("ClassA");
        System.out.println();
    }
    
    /**
     * Test listing a non-existent class, should fail.
     */
    @Test
    @DisplayName ("ListClass: Display a non-existent class, failure test")
    public void testListClassNotExist() {
    	System.out.println("Expected Output: Class \'ClassB\' does not exist.");
    	
    	System.out.print("Output: ");
    	umlEditor.listClass("ClassB"); 
    	System.out.println();
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test listing relationships between classes.
     */
    @Test
    @DisplayName ("ListRelationships: List the relationships between classes")
    public void testListRelationships() {
    	System.out.println("Expected Output: Relationship from \'ClassA\' to \'ClassB\'");
    	
    	umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        umlEditor.addRelationship("ClassA", "ClassB");
        
        System.out.print("Output: ");
        umlEditor.listRelationships(); 
        System.out.println();
    }
    
    /**
     * Test calling ListRelationships when there are no relationships.
     */
    @Test
    @DisplayName ("ListRelationships: List relationships when there are none")
    public void testListRelationshipsNotexist() {
        System.out.println("Expected Output: (no output)");
        
        System.out.print("Output: ");
        umlEditor.listRelationships();
    	System.out.println();
    }
}
