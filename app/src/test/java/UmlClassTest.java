import java.util.ArrayList;
import java.util.List;

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
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        parameters.add(P1);
        parameters.add(P2);
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
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(P2);

        UmlClass.Method methodA = umlClass.new Method("Method1", parametersA, "void");
        UmlClass.Method methodB = umlClass.new Method("Method1", parametersB, "int");
        
        assertTrue(methodA.equals(methodB));
    }
    
    /**
     * Test that two methods with different names, but the same parameters, do not equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods, that are not the same, are not equal, failure test")
    public void testNotEquality() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        UmlClass.Method methodA = umlClass.new Method("Method1", parameters, "void");
        UmlClass.Method methodB = umlClass.new Method("Method2", parameters, "void");
       
        assertFalse(methodA.equals(methodB));
    }

    /**
     * Test that two methods with same name, but different parameter types, do not equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods with same name, but different parameter types, do not equal each other, failure test")
    public void testNotEqualityDiffPara() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] otherP2 = {"int", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(otherP2);

        UmlClass.Method methodA = umlClass.new Method("Method1", parametersA, "void");
        UmlClass.Method methodB = umlClass.new Method("Method1", parametersB, "int");
       
        assertFalse(methodA.equals(methodB));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the same objects have the same hash code.
     */
    @Test
    @DisplayName ("HashCode: Test that the same objects have the same hash code")
    public void testHashCode() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        UmlClass.Method methodA = umlClass.new Method("Method1", parameters, "int");
        UmlClass.Method methodB = umlClass.new Method("Method1", parameters, "int");
        
        assertEquals(methodA.hashCode(), methodB.hashCode());
    }
    
    /**
     * Test that different objects have different hash code, should fail.
     */
    @Test
    @DisplayName ("HashCode: Test that different objects different hash code, failure test")
    public void testHashCodeDifferent() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        UmlClass.Method methodA = umlClass.new Method("Method1", parameters, "int");
        UmlClass.Method methodB = umlClass.new Method("Method2", parameters, "int");
        
        assertNotEquals(methodA.hashCode(), methodB.hashCode());
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that representation's toString returns what it should.
     */
    @Test
    @DisplayName ("MethodToString: Print out the object in the correct way with the correct information")
    public void testMethodToString() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        
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
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
            assertTrue(umlClass.addMethod("Method1", parameters, "int"));
    }

    /**
     * Test adding two methods with the same name, but different parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add methods with the same name, but different parameters")
    public void testAddMethodSameNameDiffPara() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] otherP2 = {"int", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(otherP2);
        
        umlClass.addMethod("Method1", parametersA, "int");
       
        assertTrue(umlClass.addMethod("Method1", parametersB, "int"));
    }

    /**
     * Test adding a method with zero parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with zero parameters")
    public void testAddMethodNoPara() {
        List<String[]> parameters = new ArrayList<>();
        
        assertTrue(umlClass.addMethod("Method1", parameters, "int"));
    }

    /**
     * Test a duplicate method being added, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a duplicate method, failure test")
    public void testAddMethodNotExist() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        
        assertFalse(umlClass.addMethod("Method1", parameters, "void"));
    }

    /**
     * Test adding a method with an empty name, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with an empty name, failure test")
    public void testAddMethodNameNotExist() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("", parameters, "int"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a method.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method")
    public void testDeleteMethod() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
       
        assertTrue(umlClass.deleteMethod("Method1", parameters, "int"));
    }
    
    /**
     * Test deleting a method that has the same name as another.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method that has the same name as another")
    public void testDeleteMethodSameName() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] otherP2 = {"int", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(otherP2);

        umlClass.addMethod("Method1", parametersA, "int");
        umlClass.addMethod("Method1", parametersB, "int");
       
        assertTrue(umlClass.deleteMethod("Method1", parametersA, "int"));
    }

    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete an method that dose not exist, failure test")
    public void testDeleteMethodNotExist() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        assertFalse(umlClass.deleteMethod("Method1", parameters, "int"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test renaming a method.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethod() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
       
        assertTrue(umlClass.renameMethod("Method1", parameters, "int", "Method2"));
    }
    
    /**
     * Test renaming a method to the same name as another method, but with different parameters.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethodSameName() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] otherP2 = {"int", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(otherP2);

        umlClass.addMethod("Method1", parametersA, "int");
        umlClass.addMethod("Method2", parametersB, "void");

        assertTrue(umlClass.renameMethod("Method1", parametersA, "int", "Method2"));
    }

    /**
     * Test renaming a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename a method that does not exist, failure test")
    public void testRenameMethodNotExist() {
        List<String[]> parameters = new ArrayList<>();

        assertFalse(umlClass.renameMethod("Method1", parameters, "int", "Method2"));
    }

    /**
     * Test renaming Method1, which has the same parameters as Method2, to Method2, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethodSameNameFalse() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
        umlClass.addMethod("Method2", parameters, "void");

        assertFalse(umlClass.renameMethod("Method1", parameters, "int", "Method2"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test removing a parameter from a method.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a method")
    public void testRemoveParameter() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        String[] parameterPair = {"int", "P1"};
        umlClass.addMethod("Method1", parameters, "int");

        assertTrue(umlClass.removeParameter("Method1", parameters, "int", parameterPair));
    }

    /**
     * Test removing a parameter that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Try to remove a parameter that does not exist, failure test")
    public void testRemoveParameterNotExist() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        String[] parameterPair = {"int", "P3"};
        umlClass.addMethod("Method1", parameters, "int");
        
        assertFalse(umlClass.removeParameter("MethodA", parameters, "int", parameterPair));
    }

    /**
     * Test removing a parameter from a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter from a method that does not exist, failure test")
    public void testRemoveParameterMethodNotExist() {
        List<String[]> parameters = new ArrayList<>();
        String[] parameterPair = {"P1", "int"};

        assertFalse(umlClass.removeParameter("MethodA", parameters, "int", parameterPair));
    }

    /** 
     * Test trying to remove a parameter from a method with invalid input, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter with invalid input, failure test")
    public void testRemoveParameterInvalidInput() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        String[] parameterPair = {"int", "P 1"};
        umlClass.addMethod("Method1", parameters, "int");
        
        assertFalse(umlClass.removeParameter("MethodA", parameters, "int", parameterPair));
    }

/*----------------------------------------------------------------------------------------------------------------*/   
    
    /**
     * Test changing the list of parameters of a method.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method")
    public void testChangeParameters() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] P3 = {"int", "P3"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P3);

        umlClass.addMethod("Method1", parametersA, "int");
        
        assertTrue(umlClass.changeParameters("Method1", parametersA, "int", parametersB));
    }

    /**
     * Test changing the list of parameters from none to a few parameters
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method from none to a few")
    public void testChangeParametersNoneFew() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        umlClass.addMethod("Method1", parametersA, "int");
        
        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(P2);
        
        assertTrue(umlClass.changeParameters("Method1", parametersA, "int", parametersB));
    }

    /**
     * Test changing the list of parameters from a few to none.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters of a method from a few to none")
    public void testChangeParametersFewNone() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        
        umlClass.addMethod("Method1", parametersA, "int");
        
        assertTrue(umlClass.changeParameters("Method1", parametersA, "int", parametersB));
    }

    /**
     * Test changing the list of parameters of a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters from a method that does not exist, failure test")
    public void testChangeParametersMethodNotExist() {
        String[] P1 = {"int", "P1"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        List<String[]> parametersB = new ArrayList<>();
        
        assertFalse(umlClass.changeParameters("MethodB", parametersA, "int", parametersB));
    }

    /**
     * Test changing the list of parameters to the same list, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters to the same list, failure test")
    public void testChangeParametersSameList() {
        List<String[]> parametersA = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parametersA.add(P1);
        parametersA.add(P2);
       
        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(P2);

        umlClass.addMethod("Method1", parametersA, "int");
        
        assertFalse(umlClass.changeParameters("Method1", parametersA, "int", parametersB));
    }

    /**
     * Test trying to change a parameter with invalid input, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters with invalid input, failure test")
    public void testChangeParametersInvalidInput() {
        List<String[]> parametersA = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parametersA.add(P1);
        parametersA.add(P2);
        
        List<String[]> parametersB = new ArrayList<>();
        String[] P3 = {"int", "P 3"};
        parametersA.add(P3);

        umlClass.addMethod("MethodA", parametersA, "int");
        
        assertFalse(umlClass.changeParameters("MethodA", parametersA, "int", parametersB));
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

        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
        umlClass.addMethod("Method2", parameters, "void");
        
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
