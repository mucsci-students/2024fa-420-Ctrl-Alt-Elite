import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlEditorModel;
import Model.UmlRelationship;

/**
 * A test class for UmlEditorModel.
 */
public class UmlEditorModelTest { 
    /** The model object to be tested */
    UmlEditorModel model;
    
    /**
     * Creates an instance of a UmlEditorModel object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
        model = UmlEditorModel.getInstance();
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the constructor initializes the object properly.
     */
    @Test
    @DisplayName ("Constructor: Construct a UmlEditorModel Object ")
    public void testUmlEditor() {
    	model.addClass("ClassA");
        model.addClass("ClassB");

        RelationshipType type = RelationshipType.Aggregation;
        model.addRelationship("ClassA", "ClassB", type);
        
        assertTrue ((((model.getUmlClass("ClassA")) != null) && ((model.findRelationship("ClassA", "ClassB", type))!= null)), 
    		() -> "Could not construct the UmlEditor.");

        // Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteRelationship("ClassA", "ClassB", type);
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test the clone method.
     */
    @Test
    @DisplayName ("Clone: Test the clone method")
    public void testClone() {
        model.clone();
    }

    /**
     * Test that the clone method throws its exception when needed.
     */
    @Test
    @DisplayName ("Clone: Test that the clone method throws its exception when needed")
    public void testCloneThrow() {
        model.setTestString("GO");
        model.clone();

        //Clean up
        model.setTestString("");
    }

/*----------------------------------------------------------------------------------------------------------------*/
//GUI Method Tests

    /**
     * Test that the method was renamed.
     */
    @Test
    @DisplayName ("RenameMethod: Test that the method was renamed")
    public void testRenameMethod() {
        model.addClass("ClassA");
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        List<String[]> parameters = new ArrayList<>();
        parameters.add(P1);
        parameters.add(P2);
        
        model.getClass("ClassA").addMethod("Method1", parameters, "int");
        assertTrue(model.renameMethod("ClassA", "Method1", "Method2"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test that the method was renamed, but there is no class.
     */
    @Test
    @DisplayName ("RenameMethod: Test that the method was renamed, but there is no class")
    public void testRenameMethodNoClass() {
        assertFalse(model.renameMethod("ClassA", "Method1", "Method2"));
    }

    /**
     * Test that the method was deleted.
     */
    @Test
    @DisplayName ("deleteMethod: Test that the method was deleted")
    public void testDeleteMethod() {
        model.addClass("ClassA");
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        List<String[]> parameters = new ArrayList<>();
        parameters.add(P1);
        parameters.add(P2);
        
        model.getClass("ClassA").addMethod("Method1", parameters, "int");
        assertTrue(model.deleteMethod("ClassA", "Method1"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test that the method was deleted, but there is no class.
     */
    @Test
    @DisplayName ("deleteMethod: Test that the method was deleted, but there is no class")
    public void testDeleteMethodNoClass() {
        assertFalse(model.deleteMethod("ClassA", "Method1"));
    }

    /**
     * Test getting the parameters of a method.
     */
    @Test
    @DisplayName ("getParameters: Test getting the parameters of a method")
    public void testGetParameters() {
        model.addClass("ClassA");
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        List<String[]> parameters = new ArrayList<>();
        parameters.add(P1);
        parameters.add(P2);
        
        model.getClass("ClassA").addMethod("Method1", parameters, "int");
        assertEquals(parameters, model.getParameters("ClassA", "Method1"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test getting the parameters of a method, but there is no class.
     */
    @Test
    @DisplayName ("getParameters: Test getting the parameters of a method, but there is no class")
    public void testGetParametersNoClass() {
        assertNull(model.getParameters("ClassA", "Method1"));
    }

    /**
     * Test getting the parameters of a method.
     */
    @Test
    @DisplayName ("getMethodReturnType: Test getting the parameters of a method")
    public void testGetMethodReturnType() {
        model.addClass("ClassA");
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        List<String[]> parameters = new ArrayList<>();
        parameters.add(P1);
        parameters.add(P2);
        
        model.getClass("ClassA").addMethod("Method1", parameters, "int");
        assertEquals("int", model.getMethodReturnType("ClassA", "Method1"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test getting the parameters of a method, but there is no class.
     */
    @Test
    @DisplayName ("GetMethodReturnType: Test getting the parameters of a method, but there is no class")
    public void testGetMethodReturnTypeNoClass() {
        assertNull(model.getMethodReturnType("ClassA", "Method1"));
    }

    /**
     * Test getting the method names.
     */
    @Test
    @DisplayName ("GetMethodNames: Test getting the names of a method")
    public void testGetMethodNames() {
        model.addClass("ClassA");
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        List<String[]> parameters = new ArrayList<>();
        parameters.add(P1);
        parameters.add(P2);
        
        model.getClass("ClassA").addMethod("Method1", parameters, "int");
        String[] methodNames = model.getMethodNames("ClassA");
        assertEquals("Method1", methodNames[0]);

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test getting the method names, but there is no class.
     */
    @Test
    @DisplayName ("GetMethodNames: Test getting the names of a method, but there is no class")
    public void testGetMethodNamesNoClass() {
        assertNotNull(model.getMethodNames("ClassA"));
    }

    /**
     * Test getting the names of the classes.
     */
    @Test
    @DisplayName ("GetClassNames: Test getting the names of the classes")
    public void testGetClassNames() {
        assertNotNull(model.getClassNames());
    }

    /**
     * Test updating a classes position in the GUI.
     */
    @Test
    @DisplayName ("UpdateClassPosition: Test updating a classes position in the GUI")
    public void testUpdateClassPosition() {
        model.addClass("ClassA");
        Point classPoint = new Point(0, 0);
        model.updateClassPosition("ClassA", classPoint);

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test updating a classes position in the GUI, but with no class.
     */
    @Test
    @DisplayName ("UpdateClassPosition: Test updating a classes position in the GUI, but with no class")
    public void testUpdateClassPositionNoClass() {
        Point classPoint = new Point(0, 0);
        model.updateClassPosition("ClassA", classPoint);
    }

    /**
     * Test getting the class's position.
     */
    @Test
    @DisplayName ("GetClassPosition: Test getting the class's position")
    public void testGetClassPosition() {
        Point classPoint = new Point(0, 0);
        model.addClass("ClassA", classPoint);

        assertEquals(classPoint, model.getClassPosition("ClassA"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test getting the class, but with invalid input.
     */
    @Test
    @DisplayName ("GetUmlClass: Test getting the class, but with invalid input")
    public void testGetUmlClass() {
        model.addClass("ClassA");

        assertNull(model.getUmlClass(null));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test getting the class exists, but with invalid input.
     */
    @Test
    @DisplayName ("classExist: Test getting the class, but with invalid input")
    public void testClassExistGUI() {
        model.addClass("ClassA");

        assertFalse(model.classExist(null));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test getting the class exists, but with invalid input.
     */
    @Test
    @DisplayName ("classExist: Test getting the class, but with invalid input")
    public void testClassExistGUI2() {
        model.addClass("ClassA");

        assertFalse(model.classExist(""));

        //Clean up
        model.deleteClass("ClassA");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that getClasses returns the map of classes.
     */
    @Test
    @DisplayName ("getClasses: Return the Map of classes ")
    public void testGetClasses() {
    	Map<String, UmlClass> temp = new HashMap<>();

        temp.put("ClassA", new UmlClass("ClassA"));
        temp.put("ClassB", new UmlClass("ClassB"));
        
        model.addClass("ClassA");
        model.addClass("ClassB");
        
        assertTrue(model.getClasses() != null);
        assertEquals(model.getClasses().toString(), temp.toString());

        // Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

    /**
     * Test that setClasses changes the map of classes.
     */
    @Test
    @DisplayName ("setClasses: Changes the Map of classes ")
    public void testSetClasses() {
    	Map<String, UmlClass> temp = new HashMap<>();

        temp.put("ClassA", new UmlClass("ClassA"));
        temp.put("ClassB", new UmlClass("ClassB"));
        
        model.setClasses(temp);

        assertEquals(model.getClasses(), temp);

        // Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

    /**
     * Test that getRelationships returns the list of relationships.
     */
    @Test
    @DisplayName ("getRelationships: Return the list of relationships ")
    public void testGetRelationships() {
    	List<UmlRelationship> temp = new ArrayList<>();
        RelationshipType type = RelationshipType.Aggregation;
        temp.add(new UmlRelationship("ClassA", "ClassB", type));

        model.addClass("ClassA");
        model.addClass("ClassB");
        model.addRelationship("ClassA", "ClassB", type);

        assertEquals(model.getRelationships(), temp);

        // Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteRelationship("ClassA", "ClassB", type);
    }

    /**
     * Test that setRelationships changes the list of relationships.
     */
    @Test
    @DisplayName ("setRelationships: Return the Map of classes ")
    public void testSetRelationships() {
    	List<UmlRelationship> temp = new ArrayList<>();
        RelationshipType type = RelationshipType.Aggregation;
        temp.add(new UmlRelationship("ClassA", "ClassB", type));

        model.addClass("ClassA");
        model.addClass("ClassB");
        model.setRelationships(temp);

        assertEquals(model.getRelationships().toString(), temp.toString());

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteRelationship("ClassA", "ClassB", type);
    }

/*----------------------------------------------------------------------------------------------------------------*/  

    /**
     * Test that getClass returns the asked for class given its name.
     */
    @Test
    @DisplayName ("getClass: Return the a class given its name ")
    public void testGetClass() {
        UmlClass temp = new UmlClass("ClassA");
        model.addClass("ClassA");

        assertEquals(model.getUmlClass("ClassA").toString(), temp.toString());

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test that classExist returns true if a given class has been created.
     */
    @Test
    @DisplayName ("classExist: Return true if a class of a given name has been made ")
    public void testClassExist() {
        model.addClass("ClassA");

        assertTrue(model.classExist("ClassA"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test that classExist returns false if a given class has not been created, should fail.
     */
    @Test
    @DisplayName ("classExist: Return false if a class of a given name has not been made, failure test ")
    public void testClassExistFalse() {
        assertFalse(model.classExist("ClassA"));
    }

    /**
     * Test that getClassesValues returns the values from the classes Map
     */
    @Test
    @DisplayName ("getClassesValues: Return the values from the classes Map ")
    public void testGetClassesValues() {
        Collection<UmlClass> temp = new ArrayList<>();
        temp.add(new UmlClass("ClassA"));
        temp.add(new UmlClass("ClassB"));
        
        model.addClass("ClassA");
        model.addClass("ClassB");

        assertEquals(model.getClassesValues().toString(), temp.toString());

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

/*----------------------------------------------------------------------------------------------------------------*/  

    /**
     * Test adding a class.
     */
    @Test
    @DisplayName ("AddClass: Add a class")
    public void testAddClass() {
        assertTrue(model.addClass("ClassA"), 
        	() -> "Could not add class.");
        
        assertNotNull(model.getUmlClass("ClassA"), 
        	() -> "Could not retrieve the class.");

        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test that class names are case sensitive.
     */
    @Test
    @DisplayName ("AddClass: Add a class with the same name as another, just a different case")
    public void testAddClassCaseSensitivityInClassNames() {
        model.addClass("ClassA");
        
        assertTrue(model.addClass("classa"), 
        	() -> "Could not add the class, case sensitive test.");

        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test adding a class with a duplicate name, should fail.
     */
    @Test
    @DisplayName ("AddClass: Add a duplicate class, failure test")
    public void testAddClassDup() {
    	model.addClass("ClassA");

    	assertNotNull(model.getUmlClass("ClassA"), 
    		() -> "Could not retrieve the class.");

    	assertFalse(model.addClass("ClassA"), 
    	    () -> "Error with adding duplicate class.");
        
        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test adding a class with no input, should fail.
     */
    @Test
    @DisplayName ("AddClass: Add a class with no input, failure test")
    public void testAddClassEmpty() {
    	assertFalse(model.addClass(""),
    		() -> "Error with adding a class with no name.");

    	assertNull(model.getUmlClass(""), 
    		() -> "Error with assertNull on adding a class with an empty name.");

        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test adding a class with null as its name, should fail.
     */
    @Test
    @DisplayName ("AddClass: Add a class with \"null\" as its name, failure test")
    public void testAddNullClassName() {
        assertFalse(model.addClass(null), 
        	() -> "Error with adding a class null as its name.");
    }

    /**
     * Test adding a class with invalid input.
     */
    @Test
    @DisplayName ("AddClass: Test adding a class with invalid input")
    public void testAddClassInvalidWithPoint() {
        Point classPoint = new Point(0, 0);
        model.addClass("ClassA");
        
        assertFalse(model.addClass(null, classPoint));
        assertFalse(model.addClass("", classPoint));
        assertFalse(model.addClass("C lassA", classPoint));
        assertFalse(model.addClass("ClassA", classPoint));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test adding a class with invalid input.
     */
    @Test
    @DisplayName ("AddClass: Test adding a class with invalid input")
    public void testAddClassInvalid() {
        model.addClass("ClassA");
        
        assertFalse(model.addClass(null));
        assertFalse(model.addClass(""));
        assertFalse(model.addClass("C lassA"));
        assertFalse(model.addClass("ClassA"));

        //Clean up
        model.deleteClass("ClassA");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a class.
     */
    @Test
    @DisplayName ("DeleteClass: Delete a class")
    public void testDeleteClass() {
        model.addClass("ClassA");
        
        assertTrue(model.deleteClass("ClassA"), 
        	() -> "Could not delete the class.");
        
        assertNull(model.getUmlClass("ClassA"), 
        	() -> "Error with assertNull on deleting a class.");

        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test deleting a class that has not been added, should fail.
     */
    @Test
    @DisplayName ("DeleteClass: Delete a class that does not exist, failure test")
    public void testDeleteClassNotExist() {
    	assertFalse(model.deleteClass("ClassA"), 
    		() -> "Error on deleting a non-existent class.");
    }
    
    /**
     * Test trying to delete a class with invalid input, should fail.
     */
    @Test
    @DisplayName ("DeleteClass: Delete a class with invalid input, failure test")
    public void testDeleteClassInvalid() {
    	assertFalse(model.deleteClass(" "), 
    		() -> "Error on trying to delete a class with invalid inputs.");
    }

    /**
     * Test deleting a class with invalid input.
     */
    @Test
    @DisplayName ("DeleteClass: Test deleting a class with invalid input")
    public void testDeleteClassInvalid2() {
        model.addClass("ClassA");
        
        assertFalse(model.deleteClass(null));
        assertFalse(model.deleteClass(""));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test deleting a class with a relationship.
     */
    @Test
    @DisplayName ("DeleteClass: Test deleting a class with a relationship")
    public void testDeleteClassRel() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        model.addRelationship("ClassA", "ClassB", RelationshipType.AGGREGATION);
        model.addRelationship("ClassB", "ClassA", RelationshipType.AGGREGATION);
        
        assertTrue(model.deleteClass("ClassA"));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test renaming a class.
     */
    @Test
    @DisplayName ("RenameClass: Rename a class")
    public void testRenameClass() {
        model.addClass("ClassA");

        assertTrue(model.renameClass("ClassA", "ClassB"), 
        	() -> "Could not rename the class.");

        assertNull(model.getUmlClass("ClassA"), 
        	() -> "Error with asserting that the old class name does not exist.");

        assertNotNull(model.getUmlClass("ClassB"), 
        	() -> "Error with asserting that the new class name exists.");

        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test renaming a class to an empty string, should fail.
     */
    @Test
    @DisplayName ("RenameClass: Rename a class to an empty string, failure test")
    public void testRenameClassToEmptyString() {
        model.addClass("ClassA");
        
        assertFalse(model.renameClass("ClassA", ""), 
        	() -> "Error with renaming a class to an empty string.");

        //Clean up
        model.deleteClass("ClassA");
    }
    
    /**
     * Test renaming a class to null, should fail.
     */
    @Test
    @DisplayName ("RenameClass: Rename a class to null, failure test")
    public void testRenameClassToNull() {
        model.addClass("ClassA");

        assertFalse(model.renameClass("ClassA", null), 
        	() -> "Error with trying to rename a class to null.");

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test renaming a class with invalid input.
     */
    @Test
    @DisplayName ("RenameClass: Test renaming a class with invalid input")
    public void testRenameClassInvalid() {
        model.addClass("ClassA");
        
        assertFalse(model.renameClass("ClassA", null));
        assertFalse(model.renameClass("ClassA", ""));
        assertFalse(model.renameClass("ClassC", "ClassB"));
        assertFalse(model.renameClass("ClassA", "ClassA"));
        assertFalse(model.renameClass("ClassA", "Class B"));

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test renaming a class with a relationship.
     */
    @Test
    @DisplayName ("RenameClass: Test renaming a class with invalid input")
    public void testRenameClassRel() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        model.addRelationship("ClassA", "ClassB", RelationshipType.AGGREGATION);
        model.addRelationship("ClassB", "ClassA", RelationshipType.AGGREGATION);
        model.addRelationship("ClassB", "ClassB", RelationshipType.AGGREGATION);
        
        assertTrue(model.renameClass("ClassA", "ClassC"));

        //Clean up
        model.deleteClass("ClassC");
        model.deleteClass("ClassB");
    }

/*----------------------------------------------------------------------------------------------------------------*/

/**
     * Test adding a relationship between classes.
     */
    @Test
    @DisplayName ("AddRelationship: Add a relationship between classes")
    public void testAddRelationship() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType type = RelationshipType.Aggregation;

        assertTrue(model.addRelationship("ClassA", "ClassB", type),
        	() -> "Error with adding a relationship between classes");

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteRelationship("ClassA", "ClassB", type);
    }
    
    /**
     * Test adding a relationship between the same class
     */
    @Test
    @DisplayName ("AddRelationship: Add a relationship between a class and itself")
    public void testAddRelationshipBetweenSameClass() {
        model.addClass("ClassA");
        RelationshipType type = RelationshipType.Aggregation;

        assertTrue(model.addRelationship("ClassA", "ClassA", type),
        	() -> "Error with adding a relationship between a class and itself.");

        //Clean up
        model.deleteClass("ClassA");
        model.deleteRelationship("ClassA", "ClassA", type);
    }

    /**
     * Test adding a relationship between non-existent classes, should fail.
     */
    @Test
    @DisplayName ("AddRelationship: Add a relationship between non-existent classes, failure test")
    public void testAddRelationshipNonExistentClasses() {
        model.addClass("ClassA");
        RelationshipType type = RelationshipType.Aggregation;

        assertFalse(model.addRelationship("ClassA", "NonExistentClass", type),
        	() -> "Error with adding a relationship between non-existent classes (Test 1).");

        assertFalse(model.addRelationship("NonExistentClass", "ClassA", type),
        	() -> "Error with adding a relationship between non-existent classes (Test 1).");

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     * Test adding a relationship with invalid input.
     */
    @Test
    @DisplayName ("AddRelationship: Test adding a relationship with invalid input")
    public void testAddRelationshipINvalidInput() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;

        assertFalse(model.addRelationship("", "ClassB", type));
        assertFalse(model.addRelationship("ClassA", "", type));
        assertFalse(model.addRelationship("ClassA", "ClassB", null));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

/**
     * Test deleting a relationship between classes.
     */
    @Test
    @DisplayName ("DeleteRelationship: Delete a relationship between two classes")
    public void testDeleteRelationship() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType type = RelationshipType.Aggregation;
        model.addRelationship("ClassA", "ClassB", type);

        assertTrue(model.deleteRelationship("ClassA", "ClassB", type),
        	() -> "Error with deleting a relationship.");

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteRelationship("ClassA", "ClassB", type);
    }
    
    /**
     * Test deleting a relationship that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteRelationship: Delete a relationship that does not exist, failure test")
    public void testDeleteRelationshipNotExist() {
    	model.addClass("ClassA");
        model.addClass("ClassB");
    	RelationshipType type = RelationshipType.Aggregation;

        assertFalse(model.deleteRelationship("ClassA", "ClassB", type),
        		() -> "Error with deleting a relationship that does not exist.");

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }
    
    /**
     *  Test deleting a relationship between non-existent classes, should fail.
     */
    @Test
    @DisplayName ("DeleteRelationship: Delete a relationship between two classes that do not exist, failure test")
    public void testDeleteRelationshipNonExistentClasses() {
        model.addClass("ClassA");
        RelationshipType type = RelationshipType.Aggregation;
        
        assertFalse(model.deleteRelationship("ClassA", "NonExistentClass", type),
        	() -> "Error with deleting a relationship from non-existent classes (Test 1).");

        assertFalse(model.deleteRelationship("NonExistentClass", "ClassA", type),
        	() -> "Error with deleting a relationship from non-existent classes (Test 2).");

        //Clean up
        model.deleteClass("ClassA");
    }

    /**
     *  Test deleting a relationship with invalid input.
     */
    @Test
    @DisplayName ("DeleteRelationship: Test deleting a relationship with invalid input")
    public void testDeleteRelationshipInvalid() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;
        
        assertFalse(model.deleteRelationship("", "ClassB", type));
        assertFalse(model.deleteRelationship("ClassA", "", type));
        assertFalse(model.deleteRelationship("ClassA", "ClassB", null));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

/*----------------------------------------------------------------------------------------------------------------*/

/**
     * Test changing the relationship type.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship")
    public void testChangeRelationshipType() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType typeA = RelationshipType.Aggregation;
        model.addRelationship("ClassA", "ClassB", typeA);

        RelationshipType typeB = RelationshipType.Composition;
        assertTrue(model.changeRelationshipType("ClassA", "ClassB", typeA, typeB));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteRelationship("ClassA", "ClassB", typeA);
    }

    /**
     * Test changing the relationship type to the same type, should fail.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship to its current type, failure test.")
    public void testChangeRelationshipTypeSameType() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType typeA = RelationshipType.Aggregation;

        assertFalse(model.changeRelationshipType("ClassA", "ClassB", typeA, typeA));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

    /**
     * Test changing the relationship type of a relationship from classes that do not exist, should fail.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship between classes that do not exist, failure test.")
    public void testChangeRelationshipTypeClassNotExist() {
        RelationshipType typeA = RelationshipType.Aggregation;
        RelationshipType typeB = RelationshipType.Composition;
        assertFalse(model.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
    }

    /**
     * Test changing the relationship type of a relationship that does not exist, should fail.
     */
    @Test
    @DisplayName ("changeRelationshipType: Change the type of a relationship that does not exist, failure test.")
    public void testChangeRelationshipTypeNotExist() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType typeA = RelationshipType.Aggregation;

        RelationshipType typeB = RelationshipType.Composition;
        assertFalse(model.changeRelationshipType("ClassA", "ClassB", typeA, typeB));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

    /**
     *  Test changing a relationship type with invalid input.
     */
    @Test
    @DisplayName ("ChangeRelationshipType: Test changing a relationship with invalid input")
    public void testChangeRelationshipTypeINvalid() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;
        RelationshipType typeB = RelationshipType.COMPOSITION;
        
        assertFalse(model.changeRelationshipType("", "ClassB", type, typeB));
        assertFalse(model.changeRelationshipType("ClassA", "", type, typeB));
        assertFalse(model.changeRelationshipType("ClassA", "ClassB", null, typeB));
        assertFalse(model.changeRelationshipType("ClassA", "ClassB", type, null));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test finding a relationship given its source, destination, and type.
     */
    @Test
    @DisplayName ("findRelationship: Find a relationship given its source, destination, and type.")
    public void testFindRelationship() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType typeA = RelationshipType.Aggregation;
        model.addRelationship("ClassA", "ClassB", typeA);

        model.addClass("ClassC");
        RelationshipType typeB = RelationshipType.Composition;
        model.addRelationship("ClassB", "ClassC", typeB);

        assertTrue(model.findRelationship("ClassA", "ClassB", typeA).equals(model.getRelationships().getFirst()));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteClass("ClassC");
        model.deleteRelationship("ClassA", "ClassB", typeA);
        model.deleteRelationship("ClassB", "ClassC", typeB);
    }

    /**
     * Test finding a relationship given its source, destination, and type, but it does not exist.
     */
    @Test
    @DisplayName ("findRelationship: Find a relationship given its source, destination, and type, but it does not exist")
    public void testFindRelationshipNotExist() {
        model.addClass("ClassA");
        model.addClass("ClassB");
        RelationshipType typeA = RelationshipType.AGGREGATION;
        //model.addRelationship("ClassA", "ClassB", typeA);

        model.addClass("ClassC");
        RelationshipType typeB = RelationshipType.COMPOSITION;
        model.addRelationship("ClassB", "ClassC", typeB);

        assertNull(model.findRelationship("ClassA", "ClassB", typeA));
        assertNull(model.findRelationship("ClassB", "ClassA", typeA));
        assertNull(model.findRelationship("ClassB", "ClassC", typeA));

        //Clean up
        model.deleteClass("ClassA");
        model.deleteClass("ClassB");
        model.deleteClass("ClassC");
        model.deleteRelationship("ClassA", "ClassB", typeA);
        model.deleteRelationship("ClassB", "ClassC", typeB);
    }


}

/*----------------------------------------------------------------------------------------------------------------*/
