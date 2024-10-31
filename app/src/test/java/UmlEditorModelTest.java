// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import Model.RelationshipType;
// import Model.UmlClass;
// import Model.UmlEditorModel;
// import Model.UmlRelationship;

// /**
//  * A test class for UmlEditorModel.
//  */
// public class UmlEditorModelTest { 
//     /** The model object to be tested */
//     UmlEditorModel model;
    
//     /**
//      * Creates an instance of a UmlEditorModel object to be used in tests.
//      */
//     @BeforeEach
//     public void setUp() {
//         model = new UmlEditorModel();
//     }

// /*----------------------------------------------------------------------------------------------------------------*/

//     /**
//      * Test that the constructor initializes the object properly.
//      */
//     @Test
//     @DisplayName ("Constructor: Construct a UmlEditorModel Object ")
//     public void testUmlEditor() {
//     	model.addClass("ClassA");
//         model.addClass("ClassB");

//         RelationshipType type = RelationshipType.AGGREGATION;
//         model.addRelationship("ClassA", "ClassB", type);
        
//         assertTrue ((((model.getUmlClass("ClassA")) != null) && ((model.findRelationship("ClassA", "ClassB", type))!= null)), 
//     		() -> "Could not construct the UmlEditor.");
//     }

// /*----------------------------------------------------------------------------------------------------------------*/   

//     /**
//      * Test that getClasses returns the map of classes.
//      */
//     @Test
//     @DisplayName ("getClasses: Return the Map of classes ")
//     public void testGetClasses() {
//     	Map<String, UmlClass> temp = new HashMap<>();

//         temp.put("ClassA", new UmlClass("ClassA"));
//         temp.put("ClassB", new UmlClass("ClassB"));
        
//         model.addClass("ClassA");
//         model.addClass("ClassB");
        
//         assertTrue(model.getClasses() != null);
//         assertEquals(model.getClasses().toString(), temp.toString());
//     }

//     /**
//      * Test that setClasses changes the map of classes.
//      */
//     @Test
//     @DisplayName ("setClasses: Changes the Map of classes ")
//     public void testSetClasses() {
//     	Map<String, UmlClass> temp = new HashMap<>();

//         temp.put("ClassA", new UmlClass("ClassA"));
//         temp.put("ClassB", new UmlClass("ClassB"));
        
//         model.setClasses(temp);

//         assertEquals(model.getClasses(), temp);
//     }

//     /**
//      * Test that getRelationships returns the list of relationships.
//      */
//     @Test
//     @DisplayName ("getRelationships: Return the list of relationships ")
//     public void testGetRelationships() {
//     	List<UmlRelationship> temp = new ArrayList<>();
//         RelationshipType type = RelationshipType.AGGREGATION;
//         temp.add(new UmlRelationship("ClassA", "ClassB", type));

//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         model.addRelationship("ClassA", "ClassB", type);

//         assertEquals(model.getRelationships(), temp);
//     }

//     /**
//      * Test that setRelationships changes the list of relationships.
//      */
//     @Test
//     @DisplayName ("setRelationships: Return the Map of classes ")
//     public void testSetRelationships() {
//     	List<UmlRelationship> temp = new ArrayList<>();
//         RelationshipType type = RelationshipType.AGGREGATION;
//         temp.add(new UmlRelationship("ClassA", "ClassB", type));

//         model.setRelationships(temp);

//         assertEquals(model.getRelationships().toString(), temp.toString());
//     }

// /*----------------------------------------------------------------------------------------------------------------*/  

//     /**
//      * Test that getClass returns the asked for class given its name.
//      */
//     @Test
//     @DisplayName ("getClass: Return the a class given its name ")
//     public void testGetClass() {
//         UmlClass temp = new UmlClass("ClassA");
//         model.addClass("ClassA");

//         assertEquals(model.getUmlClass("ClassA").toString(), temp.toString());
//     }

//     /**
//      * Test that classExist returns true if a given class has been created.
//      */
//     @Test
//     @DisplayName ("classExist: Return true if a class of a given name has been made ")
//     public void testClassExist() {
//         model.addClass("ClassA");

//         assertTrue(model.classExist("ClassA"));
//     }

//     /**
//      * Test that classExist returns false if a given class has not been created, should fail.
//      */
//     @Test
//     @DisplayName ("classExist: Return false if a class of a given name has not been made, failure test ")
//     public void testClassExistFalse() {
//         assertFalse(model.classExist("ClassA"));
//     }

//     /**
//      * Test that getClassesValues returns the values from the classes Map
//      */
//     @Test
//     @DisplayName ("getClassesValues: Return the values from the classes Map ")
//     public void testGetClassesValues() {
//         Collection<UmlClass> temp = new ArrayList<UmlClass>();
//         temp.add(new UmlClass("ClassA"));
//         temp.add(new UmlClass("ClassB"));
        
//         model.addClass("ClassA");
//         model.addClass("ClassB");

//         assertEquals(model.getClassesValues().toString(), temp.toString());
//     }

// /*----------------------------------------------------------------------------------------------------------------*/  

//     /**
//      * Test adding a class.
//      */
//     @Test
//     @DisplayName ("AddClass: Add a class")
//     public void testAddClass() {
//         assertTrue(model.addClass("ClassA"), 
//         	() -> "Could not add class.");
        
//         assertNotNull(model.getUmlClass("ClassA"), 
//         	() -> "Could not retrieve the class.");
//     }
    
//     /**
//      * Test that class names are case sensitive.
//      */
//     @Test
//     @DisplayName ("AddClass: Add a class with the same name as another, just a different case")
//     public void testAddClassCaseSensitivityInClassNames() {
//         model.addClass("ClassA");
        
//         assertTrue(model.addClass("classa"), 
//         	() -> "Could not add the class, case sensitive test.");
//     }
    
//     /**
//      * Test adding a class with a duplicate name, should fail.
//      */
//     @Test
//     @DisplayName ("AddClass: Add a duplicate class, failure test")
//     public void testAddClassDup() {
//     	model.addClass("ClassA");

//     	assertNotNull(model.getUmlClass("ClassA"), 
//     		() -> "Could not retrieve the class.");

//     	assertFalse(model.addClass("ClassA"), 
//     	    () -> "Error with adding duplicate class.");
//     }
    
//     /**
//      * Test adding a class with no input, should fail.
//      */
//     @Test
//     @DisplayName ("AddClass: Add a class with no input, failure test")
//     public void testAddClassEmpty() {
//     	assertFalse(model.addClass(""),
//     		() -> "Error with adding a class with no name.");

//     	assertNull(model.getUmlClass(""), 
//     		() -> "Error with assertNull on adding a class with an empty name.");
//     }
    
//     /**
//      * Test adding a class with null as its name, should fail.
//      */
//     @Test
//     @DisplayName ("AddClass: Add a class with \"null\" as its name, failure test")
//     public void testAddNullClassName() {
//         assertFalse(model.addClass(null), 
//         	() -> "Error with adding a class null as its name.");
//     }

// /*----------------------------------------------------------------------------------------------------------------*/

// /**
//      * Test deleting a class.
//      */
//     @Test
//     @DisplayName ("DeleteClass: Delete a class")
//     public void testDeleteClass() {
//         model.addClass("ClassA");
        
//         assertTrue(model.deleteClass("ClassA"), 
//         	() -> "Could not delete the class.");
        
//         assertNull(model.getUmlClass("ClassA"), 
//         	() -> "Error with assertNull on deleting a class.");
//     }
    
//     /**
//      * Test deleting a class that has not been added, should fail.
//      */
//     @Test
//     @DisplayName ("DeleteClass: Delete a class that does not exist, failure test")
//     public void testDeleteClassNotExist() {
//     	assertFalse(model.deleteClass("ClassA"), 
//     		() -> "Error on deleting a non-existent class.");
//     }
    
//     /**
//      * Test trying to delete a class with invalid input, should fail.
//      */
//     @Test
//     @DisplayName ("DeleteClass: Delete a class with invalid input, failure test")
//     public void testDeleteClassInvalid() {
//     	assertFalse(model.deleteClass(" "), 
//     		() -> "Error on trying to delete a class with invalid inputs.");
//     }
    
// /*----------------------------------------------------------------------------------------------------------------*/

//     /**
//      * Test renaming a class.
//      */
//     @Test
//     @DisplayName ("RenameClass: Rename a class")
//     public void testRenameClass() {
//         model.addClass("ClassA");

//         assertTrue(model.renameClass("ClassA", "ClassB"), 
//         	() -> "Could not rename the class.");

//         assertNull(model.getUmlClass("ClassA"), 
//         	() -> "Error with asserting that the old class name does not exist.");

//         assertNotNull(model.getUmlClass("ClassB"), 
//         	() -> "Error with asserting that the new class name exists.");
//     }
    
//     /**
//      * Test renaming a class to an empty string, should fail.
//      */
//     @Test
//     @DisplayName ("RenameClass: Rename a class to an empty string, failure test")
//     public void testRenameClassToEmptyString() {
//         model.addClass("ClassA");
        
//         assertFalse(model.renameClass("ClassA", ""), 
//         	() -> "Error with renaming a class to an empty string.");
//     }
    
//     /**
//      * Test renaming a class to null, should fail.
//      */
//     @Test
//     @DisplayName ("RenameClass: Rename a class to null, failure test")
//     public void testRenameClassToNull() {
//         model.addClass("ClassA");

//         assertFalse(model.renameClass("ClassA", null), 
//         	() -> "Error with trying to rename a class to null.");
//     }

// /*----------------------------------------------------------------------------------------------------------------*/

// /**
//      * Test adding a relationship between classes.
//      */
//     @Test
//     @DisplayName ("AddRelationship: Add a relationship between classes")
//     public void testAddRelationship() {
//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         RelationshipType type = RelationshipType.AGGREGATION;

//         assertTrue(model.addRelationship("ClassA", "ClassB", type),
//         	() -> "Error with adding a relationship between classes");
//     }
    
//     /**
//      * Test adding a relationship between the same class
//      */
//     @Test
//     @DisplayName ("AddRelationship: Add a relationship between a class and itself")
//     public void testAddRelationshipBetweenSameClass() {
//         model.addClass("ClassA");
//         RelationshipType type = RelationshipType.AGGREGATION;

//         assertTrue(model.addRelationship("ClassA", "ClassA", type),
//         	() -> "Error with adding a relationship between a class and itself.");
//     }

//     /**
//      * Test adding a relationship between non-existent classes, should fail.
//      */
//     @Test
//     @DisplayName ("AddRelationship: Add a relationship between non-existent classes, failure test")
//     public void testAddRelationshipNonExistentClasses() {
//         model.addClass("ClassA");
//         RelationshipType type = RelationshipType.AGGREGATION;

//         assertFalse(model.addRelationship("ClassA", "NonExistentClass", type),
//         	() -> "Error with adding a relationship between non-existent classes (Test 1).");

//         assertFalse(model.addRelationship("NonExistentClass", "ClassA", type),
//         	() -> "Error with adding a relationship between non-existent classes (Test 1).");
//     }
    
// /*----------------------------------------------------------------------------------------------------------------*/

// /**
//      * Test deleting a relationship between classes.
//      */
//     @Test
//     @DisplayName ("DeleteRelationship: Delete a relationship between two classes")
//     public void testDeleteRelationship() {
//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         RelationshipType type = RelationshipType.AGGREGATION;
//         model.addRelationship("ClassA", "ClassB", type);

//         assertTrue(model.deleteRelationship("ClassA", "ClassB", type),
//         	() -> "Error with deleting a relationship.");
//     }
    
//     /**
//      * Test deleting a relationship that does not exist, should fail.
//      */
//     @Test
//     @DisplayName ("DeleteRelationship: Delete a relationship that does not exist, failure test")
//     public void testDeleteRelationshipNotExist() {
//     	model.addClass("ClassA");
//         model.addClass("ClassB");
//     	RelationshipType type = RelationshipType.AGGREGATION;

//         assertFalse(model.deleteRelationship("ClassA", "ClassB", type),
//         		() -> "Error with deleting a relationship that does not exist.");
//     }
    
//     /**
//      *  Test deleting a relationship between non-existent classes, should fail.
//      */
//     @Test
//     @DisplayName ("DeleteRelationship: Delete a relationship between two classes that do not exist, failure test")
//     public void testDeleteRelationshipNonExistentClasses() {
//         model.addClass("ClassA");
//         RelationshipType type = RelationshipType.AGGREGATION;
        
//         assertFalse(model.deleteRelationship("ClassA", "NonExistentClass", type),
//         	() -> "Error with deleting a relationship from non-existent classes (Test 1).");

//         assertFalse(model.deleteRelationship("NonExistentClass", "ClassA", type),
//         	() -> "Error with deleting a relationship from non-existent classes (Test 2).");
//     }

// /*----------------------------------------------------------------------------------------------------------------*/

// /**
//      * Test changing the relationship type.
//      */
//     @Test
//     @DisplayName ("changeRelationshipType: Change the type of a relationship")
//     public void testChangeRelationshipType() {
//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         RelationshipType typeA = RelationshipType.AGGREGATION;
//         model.addRelationship("ClassA", "ClassB", typeA);

//         RelationshipType typeB = RelationshipType.COMPOSITION;
//         assertTrue(model.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
//     }

//     /**
//      * Test changing the relationship type to the same type, should fail.
//      */
//     @Test
//     @DisplayName ("changeRelationshipType: Change the type of a relationship to its current type, failure test.")
//     public void testChangeRelationshipTypeSameType() {
//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         RelationshipType typeA = RelationshipType.AGGREGATION;

//         assertFalse(model.changeRelationshipType("ClassA", "ClassB", typeA, typeA));
//     }

//     /**
//      * Test changing the relationship type of a relationship from classes that do not exist, should fail.
//      */
//     @Test
//     @DisplayName ("changeRelationshipType: Change the type of a relationship between classes that do not exist, failure test.")
//     public void testChangeRelationshipTypeClassNotExist() {
//         RelationshipType typeA = RelationshipType.AGGREGATION;
//         RelationshipType typeB = RelationshipType.COMPOSITION;
//         assertFalse(model.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
//     }

//     /**
//      * Test changing the relationship type of a relationship that does not exist, should fail.
//      */
//     @Test
//     @DisplayName ("changeRelationshipType: Change the type of a relationship that does not exist, failure test.")
//     public void testChangeRelationshipTypeNotExist() {
//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         RelationshipType typeA = RelationshipType.AGGREGATION;

//         RelationshipType typeB = RelationshipType.COMPOSITION;
//         assertFalse(model.changeRelationshipType("ClassA", "ClassB", typeA, typeB));
//     }

// /*----------------------------------------------------------------------------------------------------------------*/

//     /**
//      * Test finding a relationship given its source, destination, and type.
//      */
//     @Test
//     @DisplayName ("findRelationship: Find a relationship given its source, destination, and type.")
//     public void testFindRelationship() {
//         model.addClass("ClassA");
//         model.addClass("ClassB");
//         RelationshipType typeA = RelationshipType.AGGREGATION;
//         model.addRelationship("ClassA", "ClassB", typeA);

//         model.addClass("ClassC");
//         RelationshipType typeB = RelationshipType.COMPOSITION;
//         model.addRelationship("ClassB", "ClassC", typeB);

//         assertTrue(model.findRelationship("ClassA", "ClassB", typeA).equals(model.getRelationships().getFirst()));
//     }
// }

// /*----------------------------------------------------------------------------------------------------------------*/
