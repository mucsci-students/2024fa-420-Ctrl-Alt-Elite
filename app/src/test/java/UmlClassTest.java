import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Model.UmlClass;

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

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test creating a UmlClass object.
     */
    @Test
    @DisplayName ("Constructor: Create a UmlClass object")
    public void testUmlClassConstructor() {
        UmlClass umlClassTest = new UmlClass("ClassA");
        assertEquals(umlClassTest.getName(), umlClass.getName());
    }

/*----------------------------------------------------------------------------------------------------------------*/

    @Test
    public void testGetMethods() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("int", "P1");
        parameters.put("void", "P2");
        String returnType = "void";

        umlClass.addMethod("Method1", parameters, returnType);
        assertEquals("ClassA", umlClass.getName());
    }




    /**
     * Test that getName returns the name of the class.
     */
    @Test
    @DisplayName ("GetName: Return the name of the class")
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

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that a field can be successfully added.
     */
    @Test
    @DisplayName("AddField: Add a field to the class")
    public void testAddField() {
        assertTrue(umlClass.addField("int","Field1"));
    }
    /**
     * Test adding a field that already exists, should fail.
     */
    @Test
    @DisplayName("AddField: Add a duplicate field, failure test")
    public void testAddFieldDuplicate() {
        umlClass.addField("int","Field1");
        assertFalse(umlClass.addField("int","Field1"));
    }
    /**
     * Test adding a field with an empty name, should fail.
     */
    @Test
    @DisplayName("AddField: Add a field with an empty name, failure test")
    public void testAddFieldEmptyName() {
        assertFalse(umlClass.addField("int",""));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that a field can be successfully deleted.
     */
    @Test
    @DisplayName("DeleteField: Delete a field from the class")
    public void testDeleteField() {
        umlClass.addField("int","Field1");
        assertTrue(umlClass.deleteField("Field1"));
    }
    /**
     * Test deleting a field that does not exist, should fail.
     */
    @Test
    @DisplayName("DeleteField: Delete a field that does not exist, failure test")
    public void testDeleteFieldNotExist() {
        assertFalse(umlClass.deleteField("Field1"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test renaming a field.
     */
    @Test
    @DisplayName("RenameField: Rename a field in the class")
    public void testRenameField() {
        umlClass.addField("int","Field1");
        assertTrue(umlClass.renameField("Field1", "Field2"));
    }
    /**
     * Test renaming a field that does not exist, should fail.
     */
    @Test
    @DisplayName("RenameField: Rename a field that does not exist, failure test")
    public void testRenameFieldNotExist() {
        assertFalse(umlClass.renameField("Field1", "Field2"));
    }
    /**
     * Test renaming a field to an empty name, should fail.
     */
    @Test
    @DisplayName("RenameField: Rename a field to an empty name, failure test")
    public void testRenameFieldEmptyName() {
        umlClass.addField("int","Field1");
        assertFalse(umlClass.renameField("Field1", ""));
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that two methods that are the same equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods that are the same equal each other")
    public void testEquality() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "String");

        UmlClass.Method methodA = umlClass.new Method("Method1", mapA, "void");
        UmlClass.Method methodB = umlClass.new Method("Method1", mapB, "int");
        
        assertTrue(methodA.equals(methodB));
    }
    
    /**
     * Test that two methods with different names, but the same parameters, do not equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods, that are not the same, are not equal, failure test")
    public void testNotEquality() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        UmlClass.Method methodA = umlClass.new Method("Method1", map, "void");
        UmlClass.Method methodB = umlClass.new Method("Method2", map, "void");
       
        assertFalse(methodA.equals(methodB));
    }

    /**
     * Test that two methods with same name, but different parameter types, do not equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods with same name, but different parameter types, do not equal each other, failure test")
    public void testNotEqualityDiffPara() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "int");

        UmlClass.Method methodA = umlClass.new Method("Method1", mapA, "void");
        UmlClass.Method methodB = umlClass.new Method("Method1", mapB, "int");
       
        assertFalse(methodA.equals(methodB));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the same objects have the same hash code.
     */
    @Test
    @DisplayName ("HashCode: Test that the same objects have the same hash code")
    public void testHashCode() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
        UmlClass.Method methodA = umlClass.new Method("Method1", map, "int");
        UmlClass.Method methodB = umlClass.new Method("Method1", map, "int");
        
        assertEquals(methodA.hashCode(), methodB.hashCode());
    }
    
    /**
     * Test that different objects have different hash code, should fail.
     */
    @Test
    @DisplayName ("HashCode: Test that different objects different hash code, failure test")
    public void testHashCodeDifferent() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
        UmlClass.Method methodA = umlClass.new Method("Method1", map, "int");
        UmlClass.Method methodB = umlClass.new Method("Method2", map, "int");
        
        assertNotEquals(methodA.hashCode(), methodB.hashCode());
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that representation's toString returns what it should.
     */
    @Test
    @DisplayName ("MethodToString: Print out the object in the correct way with the correct information")
    public void testMethodToString() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
        umlClass.addMethod("Method1", map, "int");
        
        assertEquals("""
                     Class: ClassA
                     \tFields:
                     \tMethods:
                     \t\tint Method1(int P1, String P2)
                     """, umlClass.toString());
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test that a method can be successfully added.
     */
    @Test
    @DisplayName ("AddMethod: Add an method to the class")
    public void testAddMethod() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
            assertTrue(umlClass.addMethod("Method1", map, "int"));
    }

    /**
     * Test adding two methods with the same name, but different parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add methods with the same name, but different parameters")
    public void testAddMethodSameNameDiffPara() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "int");
        
        umlClass.addMethod("Method1", mapA, "int");
       
        assertTrue(umlClass.addMethod("Method1", mapB, "int"));
    }

    /**
     * Test adding a method with zero parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with zero parameters")
    public void testAddMethodNoPara() {
        Map<String, String> map = new LinkedHashMap<>();
        
        assertTrue(umlClass.addMethod("Method1", map, "int"));
    }

    /**
     * Test a duplicate method being added, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a duplicate method, failure test")
    public void testAddMethodNotExist() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "int");
        
        umlClass.addMethod("Method1", map, "int");
        
        assertFalse(umlClass.addMethod("Method1", map, "void"));
    }

    /**
     * Test adding a method with an empty name, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with an empty name, failure test")
    public void testAddMethodNameNotExist() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "int");
        
        assertFalse(umlClass.addMethod("", map, "int"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a method.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method")
    public void testDeleteMethod() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "int");

        umlClass.addMethod("Method1", map, "int");
       
        assertTrue(umlClass.deleteMethod("Method1", map, "int"));
    }
    
    /**
     * Test deleting a method that has the same name as another.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method that has the same name as another")
    public void testDeleteMethodSameName() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "int");

        umlClass.addMethod("Method1", mapA, "int");
        umlClass.addMethod("Method1", mapB, "int");
       
        assertTrue(umlClass.deleteMethod("Method1", mapA, "int"));
    }

    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete an method that dose not exist, failure test")
    public void testDeleteMethodNotExist() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        assertFalse(umlClass.deleteMethod("Method1", map, "int"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test renaming a method.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethod() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        umlClass.addMethod("Method1", map, "int");
       
        assertTrue(umlClass.renameMethod("Method1", map, "int", "Method2"));
    }
    
    /**
     * Test renaming a method to the same name as another method, but with different parameters.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethodSameName() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "int");

        umlClass.addMethod("Method1", mapA, "int");
        umlClass.addMethod("Method2", mapB, "void");

        assertTrue(umlClass.renameMethod("Method1", mapA, "int", "Method2"));
    }

    /**
     * Test renaming a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename a method that does not exist, failure test")
    public void testRenameMethodNotExist() {
        Map<String, String> map = new LinkedHashMap<>();

        assertFalse(umlClass.renameMethod("Method1", map, "int", "Method2"));
    }

    /**
     * Test renaming Method1, which has the same parameters as Method2, to Method2, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethodSameNameFalse() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        umlClass.addMethod("Method1", map, "int");
        umlClass.addMethod("Method2", map, "void");

        assertFalse(umlClass.renameMethod("Method1", map, "int", "Method2"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test removing a parameter from a method.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a method")
    public void testRemoveParameter() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
        String[] parameterPair = {"P1", "int"};
        umlClass.addMethod("Method1", map, "int");

        assertTrue(umlClass.removeParameter("Method1", map, "int", parameterPair));
    }

    /**
     * Test removing a parameter that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Try to remove a parameter that does not exist, failure test")
    public void testRemoveParameterNotExist() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
        String[] parameterPair = {"P3", "int"};
        umlClass.addMethod("Method1", map, "int");
        
        assertFalse(umlClass.removeParameter("MethodA", map, "int", parameterPair));
    }

    /**
     * Test removing a parameter from a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a method that does not exist, failure test")
    public void testRemoveParameterMethodNotExist() {
        Map<String, String> map = new LinkedHashMap<>();
        String[] parameterPair = {"P1", "int"};

        assertFalse(umlClass.removeParameter("MethodA", map, "int", parameterPair));
    }

    /** 
     * Test trying to remove a parameter from a method with invalid input, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter with invalid input, failure test")
    public void testRemoveParameterInvalidInput() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");
        
        String[] parameterPair = {"P 1", "int"};
        umlClass.addMethod("Method1", map, "int");
        
        assertFalse(umlClass.removeParameter("MethodA", map, "int", parameterPair));
    }

    
/*----------------------------------------------------------------------------------------------------------------*/   
    
    /**
     * Test changing the list of parameters of a method.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method")
    public void testChangeParameters() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P3", "int");

        umlClass.addMethod("Method1", mapA, "int");
        
        assertTrue(umlClass.changeParameters("Method1", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters from none to a few parameters
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method from none to a few")
    public void testChangeParametersNoneFew() {
        Map<String, String> mapA = new LinkedHashMap<>();
        umlClass.addMethod("Method1", mapA, "int");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "String");
        
        assertTrue(umlClass.changeParameters("Method1", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters from a few to none.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method from a few to none")
    public void testChangeParametersFewNone() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        umlClass.addMethod("Method1", mapA, "int");

        Map<String, String> mapB = new LinkedHashMap<>();
        
        assertTrue(umlClass.changeParameters("Method1", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters of a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters from a method that does not exist, failure test")
    public void testChangeParametersMethodNotExist() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        Map<String, String> mapB = new LinkedHashMap<>();
        
        assertFalse(umlClass.changeParameters("MethodB", mapA, "int", mapB));
    }

    /**
     * Test changing the list of parameters to the same list, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters to the same list, failure test")
    public void testChangeParametersSameList() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        umlClass.addMethod("Method1", mapA, "int");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P1", "int");
        mapB.put("P2", "String");
        
        assertFalse(umlClass.changeParameters("Method1", mapA, "int", mapB));
    }

    /**
     * Test trying to change a parameter with invalid input, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters with invalid input, failure test")
    public void testChangeParametersInvalidInput() {
        Map<String, String> mapA = new LinkedHashMap<>();
        mapA.put("P1", "int");
        mapA.put("P2", "String");
        umlClass.addMethod("MethodA", mapA, "int");

        Map<String, String> mapB = new LinkedHashMap<>();
        mapB.put("P 1", "int");
        
        assertFalse(umlClass.changeParameters("MethodA", mapA, "int", mapB));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that toString returns what it should.
     */
    @Test
    @DisplayName ("ToString: Print out the object in the correct way with the correct information")
    public void testToString() {
        umlClass.addField("int", "Field-A");
        umlClass.addField("int", "Field-B");
        umlClass.addField("int", "Field-C");

        Map<String, String> map = new LinkedHashMap<>();
        map.put("P1", "int");
        map.put("P2", "String");

        umlClass.addMethod("Method1", map, "int");
        umlClass.addMethod("Method2", map, "void");
        
        assertEquals("""
                     Class: ClassA
                     \tFields:
                     \t\tint Field-A
                     \t\tint Field-B
                     \t\tint Field-C
                     \tMethods:
                     \t\tint Method1(int P1, String P2)
                     \t\tvoid Method2(int P1, String P2)
                     """, umlClass.toString());
    }
}

/*----------------------------------------------------------------------------------------------------------------*/
