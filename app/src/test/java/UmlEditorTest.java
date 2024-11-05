import java.util.LinkedHashMap;  // JUnit 5 assertions
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;  // JUnit 5 setup
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull; // JUnit DisplayName annotation
import static org.junit.jupiter.api.Assertions.assertTrue;  // JUnit 5 Test annotation
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Controller.UmlEditor;
import Model.RelationshipType;
import Model.UmlEditorModel;

/**
 * A test class for the UmlEditor class
 */
public class UmlEditorTest {
	
	/** A UmlEditor object that will be tested on. */
    private UmlEditor umlEditor;

    /** The model that holds the classes and relationships for this Uml Editor test file */
    private UmlEditorModel model;

    /**
     * Creates an instance of a UmlEditor object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
        model = new UmlEditorModel();
        umlEditor = new UmlEditor(model);
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test that the constructor initializes the object properly.
     */
    @Test
    @DisplayName ("Constructor: Construct a UmlEditor Object ")
    public void testUmlEditor() {
    	umlEditor.addClass("ClassA");
        
        assertTrue (((umlEditor.getClass("ClassA")) != null), 
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
        
        assertNotNull(umlEditor.getClass("ClassA"), 
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

    	assertNotNull(umlEditor.getClass("ClassA"), 
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

    	assertNull(umlEditor.getClass(""), 
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
        
        assertNull(umlEditor.getClass("ClassA"), 
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

        assertNull(umlEditor.getClass("ClassA"), 
        	() -> "Error with asserting that the old class name does not exist.");

        assertNotNull(umlEditor.getClass("ClassB"), 
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

     /**
     * Test adding a field to a class.
     */
    @Test
    @DisplayName ("AddField: Add a field to a class")
    public void testAddField() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addField("ClassA", "int", "Field1"), 
            () -> "Error with adding field.");
    }
    
    /**
     * Test adding a field to a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("AddField: Add a field to a class that does not exist, failure test")
    public void testAddFieldFalseClass() {
        assertFalse(umlEditor.addField("NonExistentClass", "int", "Field1"), 
            () -> "Error with adding field to non-existent class.");
    }
    /**
     * Test adding duplicate field names to a class, should fail.
     */
    @Test
    @DisplayName ("AddField: Add a field with a duplicate name, failure test")
    public void testAddDuplicateField() {
        umlEditor.addClass("ClassA");
        
        assertTrue(umlEditor.addField("ClassA", "int", "Field1"), 
            () -> "Error with adding field in duplicate field test.");
        assertFalse(umlEditor.addField("ClassA", "int", "Field1"), 
            () -> "Error with adding a duplicate field.");
    }
    /**
     * Test adding a field with invalid input, should fail.
     */
    @Test
    @DisplayName ("AddField: Add a field with invalid input, failure test")
    public void testAddFieldInvalidInput() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.addField("ClassA", "int", " "), 
            () -> "Error with adding a field with invalid name.");
        
        assertFalse(umlEditor.addField("ClassA", "int", ""), 
            () -> "Error with adding a field with invalid type.");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a field from a class.
     */
    @Test
    @DisplayName ("DeleteField: Delete a field from a class")
    public void testDeleteField() {
        umlEditor.addClass("ClassA");
        umlEditor.addField("ClassA", "int", "Field1");
        assertTrue(umlEditor.deleteField("ClassA", "Field1"), 
            () -> "Error with deleting a field."); 
    }
    /**
     * Test deleting a field that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteField: Delete a field that does not exist, failure test")
    public void testDeleteFieldNotExist() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.deleteField("ClassA", "NonExistentField"), 
            () -> "Error with deleting a non-existent field.");
    }
    /**
     * Test deleting a field from a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteField: Delete a field from a class that does not exist, failure test")
    public void testDeleteFieldFalseClass() {
        assertFalse(umlEditor.deleteField("NonExistentClass", "Field1"), 
            () -> "Error with deleting field from a non-existent class.");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test renaming a field.
     */
    @Test
    @DisplayName ("RenameField: Rename a field")
    public void testRenameField() {
        umlEditor.addClass("ClassA");
        umlEditor.addField("ClassA", "int", "Field1");
        assertTrue(umlEditor.renameField("ClassA", "Field1", "Field2"), 
            () -> "Error with renaming a field.");
    }
    /**
     * Test renaming a field that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameField: Rename a field that does not exist, failure test")
    public void testRenameFieldNotExist() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.renameField("ClassA", "NonExistentField", "Field2"), 
            () -> "Error with renaming a non-existent field.");
    }
    /**
     * Test renaming a field in a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameField: Rename a field in a class that does not exist, failure test")
    public void testRenameFieldFalseClass() {
        assertFalse(umlEditor.renameField("NonExistentClass", "Field1", "Field2"), 
            () -> "Error with renaming a field in a non-existent class.");
    }
    /**
     * Test renaming a field to an empty string, should fail.
     */
    @Test
    @DisplayName ("RenameField: Rename a field to an empty string, failure test")
    public void testRenameFieldToEmptyString() {
        umlEditor.addClass("ClassA");
        umlEditor.addField("ClassA", "int", "Field1");
        assertFalse(umlEditor.renameField("ClassA", "Field1", ""), 
            () -> "Error with renaming a field to an empty name.");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test adding a method to a class.
     */
    @Test
    @DisplayName ("AddMethod: Add a method to a class")
    public void testAddMethod() {
        umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        assertTrue(umlEditor.addMethod("ClassA", "Method1", map, "int"), 
        	() -> "Error with adding method.");
    }
    
    /** 
     * Test adding a method with 0 parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with 0 parameters")
    public void testAddMethodNoPara() {
        umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();

        assertTrue(umlEditor.addMethod("ClassA", "Method1", map, "int"), 
        	() -> "Error with adding method with 0 parameters.");
    }

    /**
     * Test adding a method to a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method to a class that does not exist, failure test")
    public void testAddMethodFalseClass() {
    	Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        assertFalse(umlEditor.addMethod("ClassB", "Method1", map, "int"), 
    		() -> "Error with adding to non-existent class.");
    }
    
    /**
     * Test adding duplicate method names, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add an method with a duplicate name, failure test")
    public void testAddDuplicateMethod() {
        umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        assertTrue(umlEditor.addMethod("ClassA", "Method1", map, "int"), 
        	() -> "Error with adding method in duplicate method test.");

        assertFalse(umlEditor.addMethod("ClassA", "Method1", map, "int"),
        	() -> "Error with trying to add duplicate method.");
    }

    /**
     * Test adding a method with invalid parameter input, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with invalid parameter names, failure test")
    public void testAddMethodBadParaNames() {
        umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        assertFalse(umlEditor.addMethod("ClassA", "Method1", map, "int"),
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
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        umlEditor.addMethod("ClassA", "Method1", map, "int");

        assertTrue(umlEditor.deleteMethod("ClassA", "Method1", map, "int"), 
        	() -> "Error with deleting a method."); 
    }
    
    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete and method that does not exist, failure test")
    public void testDeleteMethodNotExist() {
    	umlEditor.addClass("ClassA");
    	Map<String, String> map = new LinkedHashMap<>();

        assertFalse(umlEditor.deleteMethod("ClassA", "NonExistentMethod", map, "int"), 
    		() -> "Error with deleting a method that does not exist.");
    }
    
    /**
     * Test deleting a method with a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method in a class that does not exist, failure test")
    public void testDeleteMethodFalseClass() {
    	Map<String, String> map = new LinkedHashMap<>();

        assertFalse(umlEditor.deleteMethod("ClassA", "Method1", map, "int"), 
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
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        umlEditor.addMethod("ClassA", "Method1", map, "int");

        assertTrue(umlEditor.renameMethod("ClassA", "Method1", map, "int", "Method2"),
        	() -> "Error with renaming an method.");
    }
    
    /**
     * Test renaming a method that does not exist, should fail
     */
    @Test
    @DisplayName ("RenameMethod: Rename a method that does not exist, failure test")
    public void testRenameMethodNotExist() {
    	umlEditor.addClass("ClassA");
    	Map<String, String> map = new LinkedHashMap<>();

        assertFalse(umlEditor.renameMethod("ClassA", "Method1", map, "int", "Method3"),
    		() -> "Error with renaming a method that does not exist.");
    }
    
    /**
     * Test renaming a method with a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method to a class that does not exist, failure test")
    public void testRenameMethodFalseClass() {
    	Map<String, String> map = new LinkedHashMap<>();

        assertFalse(umlEditor.renameMethod("ClassA", "Method1", map, "int", "Method2"), 
    		() -> "Error with renaming from non-existent class.");
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test removing a parameter from a method.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a method")
    public void testRemoveParameter() {
    	umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        String[] parameterPair = {"P1", "int"};
        umlEditor.addMethod("ClassA", "MethodA", map, "int");
        
        assertTrue(umlEditor.removeParameter("ClassA", "MethodA", map, "int", parameterPair));
    }

    /**
     * Test removing a parameter that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Try to remove a parameter that does not exist, failure test")
    public void testRemoveParameterNotExist() {
    	umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        String[] parameterPair = {"P3", "int"};
        umlEditor.addMethod("ClassA", "MethodA", map, "int");
        
        assertFalse(umlEditor.removeParameter("ClassA", "MethodA", map, "int", parameterPair));
    }

    /**
     * Test removing a parameter from a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a method that does not exist, failure test")
    public void testRemoveParameterMethodNotExist() {
    	umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        String[] parameterPair = {"P3", "int"};

        assertFalse(umlEditor.removeParameter("ClassA", "MethodA", map, "int", parameterPair));
    }

    /**
     * Test removing a parameter from a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a class that does not exist, failure test")
    public void testRemoveParameterClassNotExist() {
    	umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        String[] parameterPair = {"P3", "int"};
       
        umlEditor.addMethod("ClassA", "MethodA", map, "int");

        assertFalse(umlEditor.removeParameter("ClassB", "MethodA", map, "int", parameterPair));
    }

    /** 
     * Test trying to remove a parameter from a method with invalid input, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter with invalid input, failure test")
    public void testRemoveParameterInvalidInput() {
        umlEditor.addClass("ClassA");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        String[] parameterPair = {"P 3", "int"};
       
        umlEditor.addMethod("ClassA", "MethodA", map, "int");
        
        assertFalse(umlEditor.removeParameter("ClassA", "MethodA", map, "int", parameterPair));
    }

    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test changing the list of parameters of a method.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method")
    public void testChangeParameters() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P3", "int");

        umlEditor.addMethod("ClassA", "MethodA", mapA, "int");
        
        assertTrue(umlEditor.changeParameters("ClassA", "MethodA", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters from none to a few parameters
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method from none to a few")
    public void testChangeParametersNoneFew() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "String");

        umlEditor.addMethod("ClassA", "MethodA", mapA, "int");
        
        assertTrue(umlEditor.changeParameters("ClassA", "MethodA", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters from a few to none.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method from a few to none")
    public void testChangeParametersFewNone() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        Map<String, String> mapB = new LinkedHashMap<>();

        umlEditor.addMethod("ClassA", "MethodA", mapA, "int");
        
        assertTrue(umlEditor.changeParameters("ClassA", "MethodA", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters of a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters from a method that does not exist, failure test")
    public void testChangeParametersMethodNotExist() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        
        assertFalse(umlEditor.changeParameters("ClassA", "MethodB", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters of a method from a class that does not exist, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters from a class that does not exist, failure test")
    public void testChangeParametersClassNotExist() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        umlEditor.addMethod("ClassA", "MethodA", mapA, "int");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P3", "int");
        
        assertFalse(umlEditor.changeParameters("ClassB", "MethodB", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters to the same list, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters to the same list, failure test")
    public void testChangeParametersSameList() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        umlEditor.addMethod("ClassA", "MethodA", mapA, "int");
        
        assertFalse(umlEditor.changeParameters("ClassB", "MethodB", mapA, "int", mapA));
    }

    /**
     * Test trying to change a parameter with invalid input, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters with invalid input, failure test")
    public void testChangeParametersInvalidInput() {
    	umlEditor.addClass("ClassA");
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        umlEditor.addMethod("ClassA", "MethodA", mapA, "int");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P 3", "int");
        
        assertFalse(umlEditor.changeParameters("ClassA", "MethodA", mapA, "int", mapB));
    }

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
     * Test changing the relationship type.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship")
    public void testChangeRelationshipType() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType typeA = RelationshipType.AGGREGATION;
        umlEditor.addRelationship("ClassA", "ClassB", typeA);

        RelationshipType typeB = RelationshipType.COMPOSITION;
        assertTrue(umlEditor.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
    }

    /**
     * Test changing the relationship type to the same type, should fail.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship to its current type, failure test.")
    public void testChangeRelationshipTypeSameType() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType typeA = RelationshipType.AGGREGATION;

        assertFalse(umlEditor.changeRelationshipType("ClassA", "ClassB", typeA, typeA));
    }

    /**
     * Test changing the relationship type of a relationship from classes that do not exist, should fail.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship between classes that do not exist, failure test.")
    public void testChangeRelationshipTypeClassNotExist() {
        RelationshipType typeA = RelationshipType.AGGREGATION;
        RelationshipType typeB = RelationshipType.COMPOSITION;
        assertFalse(umlEditor.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
    }

    /**
     * Test changing the relationship type of a relationship that does not exist, should fail.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship that does not exist, failure test.")
    public void testChangeRelationshipTypeNotExist() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType typeA = RelationshipType.AGGREGATION;

        RelationshipType typeB = RelationshipType.COMPOSITION;
        assertFalse(umlEditor.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
    }
}

/*----------------------------------------------------------------------------------------------------------------*/
