import java.util.Arrays;  // JUnit 5 assertions
import java.util.LinkedHashSet;  // JUnit 5 setup

import static org.junit.jupiter.api.Assertions.assertFalse; // JUnit DisplayName annotation
import static org.junit.jupiter.api.Assertions.assertNotNull;  // JUnit 5 Test annotation
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A test class for the UmlEditor class
 */
public class UmlEditorTest {
	
	/** A UmlEditor object that will be tested on. */
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
    @DisplayName ("Constructor: Construct a UmlEditor Object ")
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
        	() -> "Could not retrieve the class.");
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
    		() -> "Could not retrieve the class.");

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
        	() -> "Error with trying to rename a class to null.");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    //TODO addField tests

    //TODO deleteField tests

    //TODO renameField tests
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test adding a method to a class.
     */
    @Test
    @DisplayName ("AddMethod: Add a method to a class")
    public void testAddMethod() {
        umlEditor.addClass("ClassA");
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        assertTrue(umlEditor.addMethod("ClassA", "Method1", lst), 
        	() -> "Error with adding method.");
    }
    
    /** 
     * Test adding a method with 0 parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with 0 parameters")
    public void testAddMethodNoPara() {
        umlEditor.addClass("ClassA");
        LinkedHashSet<String> lst = new LinkedHashSet<>();

        assertTrue(umlEditor.addMethod("ClassA", "Method1", lst), 
        	() -> "Error with adding method with 0 parameters.");
    }

    /**
     * Test adding a method to a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method to a class that does not exist, failure test")
    public void testAddMethodFalseClass() {
    	LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        assertFalse(umlEditor.addMethod("ClassB", "Method1", lst), 
    		() -> "Error with adding to non-existent class.");
    }
    
    /**
     * Test adding duplicate method names, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add an method with a duplicate name, failure test")
    public void testAddDuplicateMethod() {
        umlEditor.addClass("ClassA");
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        assertTrue(umlEditor.addMethod("ClassA", "Method1", lst), 
        	() -> "Error with adding method in duplicate method test.");

        assertFalse(umlEditor.addMethod("ClassA", "Method1", lst),
        	() -> "Error with trying to add duplicate method.");
    }

    /**
     * Test adding a method with invalid parameter input, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with invalid parameter names, failure test")
    public void testAddMethodBadParaNames() {
        umlEditor.addClass("ClassA");
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList(" Para A ", " Para B", "Para C "));

        assertFalse(umlEditor.addMethod("ClassA", "Method1", lst),
        	() -> "Error with trying to add method with bad parameter names.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a method from a class.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method from a class")
    public void testDeleteMethod() {
        umlEditor.addClass("ClassA");
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        umlEditor.addMethod("ClassA", "Method1", lst);

        assertTrue(umlEditor.deleteMethod("ClassA", "Method1", lst), 
        	() -> "Error with deleting a method."); 
    }
    
    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete and method that does not exist, failure test")
    public void testDeleteMethodNotExist() {
    	umlEditor.addClass("ClassA");
    	LinkedHashSet<String> lst = new LinkedHashSet<>();

        assertFalse(umlEditor.deleteMethod("ClassA", "NonExistentMethod", lst), 
    		() -> "Error with deleting a method that does not exist.");
    }
    
    /**
     * Test deleting a method with a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method in a class that does not exist, failure test")
    public void testDeleteMethodFalseClass() {
    	LinkedHashSet<String> lst = new LinkedHashSet<>();

        assertFalse(umlEditor.deleteMethod("ClassA", "Method1", lst), 
    		() -> "Error with deleting from non-existent class.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test renaming a method.
     */
    @Test
    @DisplayName ("RenameMethod: Rename and method")
    public void testRenameMethod() {
        umlEditor.addClass("ClassA");
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        umlEditor.addMethod("ClassA", "Method1", lst);

        assertTrue(umlEditor.renameMethod("ClassA", "Method1", lst, "Method2"),
        	() -> "Error with renaming an method.");
    }
    
    /**
     * Test renaming a method that does not exist, should fail
     */
    @Test
    @DisplayName ("RenameMethod: Rename a method that does not exist, failure test")
    public void testRenameMethodNotExist() {
    	umlEditor.addClass("ClassA");
    	LinkedHashSet<String> lst = new LinkedHashSet<>();

        assertFalse(umlEditor.renameMethod("ClassA", "Method1", lst, "Method3"),
    		() -> "Error with renaming a method that does not exist.");
    }
    
    /**
     * Test renaming a method with a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method to a class that does not exist, failure test")
    public void testRenameMethodFalseClass() {
    	LinkedHashSet<String> lst = new LinkedHashSet<>();

        assertFalse(umlEditor.renameMethod("ClassA", "Method1", lst, "Method2"), 
    		() -> "Error with renaming from non-existent class.");
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    //TODO remove parameter
    
/*----------------------------------------------------------------------------------------------------------------*/

    //TODO change parameter

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test adding a relationship between classes.
     */
    @Test
    @DisplayName ("AddRelationship: Add a relationship between classes")
    public void testAddRelationship() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;

        assertTrue(umlEditor.addRelationship("ClassA", "ClassB", type),
        	() -> "Error with adding a relationship between classes");
    }
    
    /**
     * Test adding a relationship between the same class
     */
    @Test
    @DisplayName ("AddRelationship: Add a relationship between a class and itself")
    public void testAddRelationshipBetweenSameClass() {
        umlEditor.addClass("ClassA");
        RelationshipType type = RelationshipType.AGGREGATION;

        assertTrue(umlEditor.addRelationship("ClassA", "ClassA", type),
        	() -> "Error with adding a relationship between a class and itself.");
    }

    /**
     * Test adding a relationship between non-existent classes, should fail.
     */
    @Test
    @DisplayName ("AddRelationship: Add a relationship between non-existent classes, failure test")
    public void testAddRelationshipNonExistentClasses() {
        umlEditor.addClass("ClassA");
        RelationshipType type = RelationshipType.AGGREGATION;

        assertFalse(umlEditor.addRelationship("ClassA", "NonExistentClass", type),
        	() -> "Error with adding a relationship between non-existent classes (Test 1).");

        assertFalse(umlEditor.addRelationship("NonExistentClass", "ClassA", type),
        	() -> "Error with adding a relationship between non-existent classes (Test 1).");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a relationship between classes.
     */
    @Test
    @DisplayName ("DeleteRelationship: Delete a relationship between two classes")
    public void testDeleteRelationship() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;
        umlEditor.addRelationship("ClassA", "ClassB", type);

        assertTrue(umlEditor.deleteRelationship("ClassA", "ClassB", type),
        	() -> "Error with deleting a relationship.");
    }
    
    /**
     * Test deleting a relationship that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteRelationship: Delete a relationship that does not exist, failure test")
    public void testDeleteRelationshipNotExist() {
    	umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
    	RelationshipType type = RelationshipType.AGGREGATION;

        assertFalse(umlEditor.deleteRelationship("ClassA", "ClassB", type),
        		() -> "Error with deleting a relationship that does not exist.");
    }
    
    /**
     *  Test deleting a relationship between non-existent classes, should fail.
     */
    @Test
    @DisplayName ("DeleteRelationship: Delete a relationship between two classes that do not exist, failure test")
    public void testDeleteRelationshipNonExistentClasses() {
        umlEditor.addClass("ClassA");
        RelationshipType type = RelationshipType.AGGREGATION;
        
        assertFalse(umlEditor.deleteRelationship("ClassA", "NonExistentClass", type),
        	() -> "Error with deleting a relationship from non-existent classes (Test 1).");

        assertFalse(umlEditor.deleteRelationship("NonExistentClass", "ClassA", type),
        	() -> "Error with deleting a relationship from non-existent classes (Test 2).");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test listing all classes.
     */
    @Test
    @DisplayName ("ListClasses: List all classes")
    public void testListClasses() {
    	System.out.println("ListClasses: List all classes");

        System.out.println("Expected Output: ");
    	System.out.println("Class: ClassA");
        System.out.println("Methods: []");
        System.out.println("Class: ClassB");
        System.out.println("Methods: []");
    	
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
    	System.out.println("ListClasses: Display no classes when there are none");

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
    	System.out.println("ListClass: List a class");

        System.out.println("Expected Output: ");
        System.out.println("Class: ClassA");
        System.out.println("Methods: []");
    	
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
    	System.out.println("ListClass: Display a non-existent class, failure test");

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
    	System.out.println("ListRelationships: List the relationships between classes");
        
        System.out.println("Expected Output: Relationship from \'ClassA\' to \'ClassB\'");
    	
    	umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;
        umlEditor.addRelationship("ClassA", "ClassB", type);
        
        System.out.print("Output: ");
        umlEditor.listRelationships(); 
        System.out.println();
    }
    
    /**
     * Test calling ListRelationships when there are no relationships.
     */
    @Test
    @DisplayName ("ListRelationships: List relationships when there are none")
    public void testListRelationshipsNotExist() {
        System.out.println("Expected Output: (no output)");
        
        System.out.print("Output: ");
        umlEditor.listRelationships();
    	System.out.println();
    }
}