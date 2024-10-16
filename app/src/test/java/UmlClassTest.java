import java.util.Arrays;
import java.util.LinkedHashSet;

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

    //TODO UmlClass constructor

/*----------------------------------------------------------------------------------------------------------------*/

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
        assertTrue(umlClass.addField("Field1"));
    }
    /**
     * Test adding a field that already exists, should fail.
     */
    @Test
    @DisplayName("AddField: Add a duplicate field, failure test")
    public void testAddFieldDuplicate() {
        umlClass.addField("Field1");
        assertFalse(umlClass.addField("Field1"));
    }
    /**
     * Test adding a field with an empty name, should fail.
     */
    @Test
    @DisplayName("AddField: Add a field with an empty name, failure test")
    public void testAddFieldEmptyName() {
        assertFalse(umlClass.addField(""));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that a field can be successfully deleted.
     */
    @Test
    @DisplayName("DeleteField: Delete a field from the class")
    public void testDeleteField() {
        umlClass.addField("Field1");
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
        umlClass.addField("Field1");
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
        umlClass.addField("Field1");
        assertFalse(umlClass.renameField("Field1", ""));
    }

    
/*----------------------------------------------------------------------------------------------------------------*/

    //TODO method constructor

/*----------------------------------------------------------------------------------------------------------------*/

    //TODO get name
    //TODO set name

/*----------------------------------------------------------------------------------------------------------------*/

    //TODO get parameters
    //TODO set parameters

/*----------------------------------------------------------------------------------------------------------------*/

    //TODO remove parameter

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test that two methods that are the same equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods that are the same equal each other")
    public void testEquality() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        UmlClass.Method methodA = umlClass.new Method("Method1", lst);
        UmlClass.Method methodB = umlClass.new Method("Method1", lst);
        
        assertEquals(methodA, methodB);
    }
    
    /**
     * Test that two methods with different names, but the same parameters, do not equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods, that are not the same, are not equal, failure test")
    public void testNotEquality() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        UmlClass.Method methodA = umlClass.new Method("Method1", lst);
        UmlClass.Method methodB = umlClass.new Method("Method2", lst);
       
        assertNotEquals(methodA, methodB);
    }

    /**
     * Test that two methods with same name, but different parameters, do not equal each other.
     */
    @Test
    @DisplayName ("Equals: Test that two methods, that are not the same, are not equal, failure test")
    public void testNotEqualityDiffPara() {
        LinkedHashSet<String> lstA = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        LinkedHashSet<String> lstB = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B"));

        UmlClass.Method methodA = umlClass.new Method("Method1", lstA);
        UmlClass.Method methodB = umlClass.new Method("Method1", lstB);
        
        assertNotEquals(methodA, methodB);
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the same objects have the same hash code.
     */
    @Test
    @DisplayName ("HashCode: Test that the same objects have the same hash code")
    public void testHashCode() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        
        UmlClass.Method methodA = umlClass.new Method("Method1", lst);
        UmlClass.Method methodB = umlClass.new Method("Method1", lst);
        
        assertEquals(methodA.hashCode(), methodB.hashCode());
    }
    
    /**
     * Test that different objects have different hash code, should fail.
     */
    @Test
    @DisplayName ("HashCode: Test that different objects different hash code, failure test")
    public void testHashCodeDifferent() {
        LinkedHashSet<String> lstA = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        LinkedHashSet<String> lstB = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B"));

        UmlClass.Method methodA = umlClass.new Method("Method1", lstA);
        UmlClass.Method methodB = umlClass.new Method("Method1", lstB);
        
        assertNotEquals(methodA.hashCode(), methodB.hashCode());
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that representation's toString returns what it should.
     */
    @Test
    @DisplayName ("MethodToString: Print out the object in the correct way with the correct information")
    public void testMethodToString() {
        LinkedHashSet<String> lst = new LinkedHashSet<>();
        lst.add("Para-A");
        lst.add("Para-B");
        lst.add("Para-C");
        
        umlClass.addMethod("Method1", lst);
        
        assertEquals("""
                     Class: ClassA
                     \tFields:
                     \tMethods:
                     \t\tMethod1 (Para-A, Para-B, Para-C)
                     """, umlClass.toString());
    }

/*----------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Test that a method can be successfully added.
     */
    @Test
    @DisplayName ("AddMethod: Add an method to the class")
    public void testAddMethod() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        
            assertTrue(umlClass.addMethod("Method1", lst));
    }

    /**
     * Test adding two methods with the same name, but different parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add methods with the same name, but different parameters")
    public void testAddMethodSameNameDiffPara() {
        LinkedHashSet<String> lstA = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        LinkedHashSet<String> lstB = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B"));
        
        umlClass.addMethod("Method1", lstA);
       
        assertTrue(umlClass.addMethod("Method1", lstB));
    }

    /**
     * Test adding a method with zero parameters.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with zero parameters")
    public void testAddMethodNoPara() {
        LinkedHashSet<String> lst = new LinkedHashSet<>();
        
        assertTrue(umlClass.addMethod("Method1", lst));
    }

    /**
     * Test a duplicate method being added, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a duplicate method, failure test")
    public void testAddMethodNotExist() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        
        umlClass.addMethod("Method1", lst);
        
        assertFalse(umlClass.addMethod("Method1", lst));
    }

    /**
     * Test adding a method with an empty name, should fail.
     */
    @Test
    @DisplayName ("AddMethod: Add a method with an empty name, failure test")
    public void testAddMethodNameNotExist() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        
        assertFalse(umlClass.addMethod("", lst));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test deleting a method.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method")
    public void testDeleteMethod() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        umlClass.addMethod("Method1", lst);
       
        assertTrue(umlClass.deleteMethod("Method1", lst));
    }
    
    /**
     * Test deleting a method that has the same name as another.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete a method that has the same name as another")
    public void testDeleteMethodSameName() {
        LinkedHashSet<String> lstA = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        LinkedHashSet<String> lstB = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B"));

        umlClass.addMethod("Method1", lstA);
        umlClass.addMethod("Method1", lstB);
       
        assertTrue(umlClass.deleteMethod("Method1", lstA));
    }

    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete an method that dose not exist, failure test")
    public void testDeleteMethodNotExist() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        assertFalse(umlClass.deleteMethod("Method1", lst));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test renaming a method.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethod() {
        LinkedHashSet<String> lst = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        umlClass.addMethod("Method1", lst);
       
        assertTrue(umlClass.renameMethod("Method1", lst, "Method2"));
    }
    
    /**
     * Test renaming a method to the same name as another method, but with different parameters.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethodSameName() {
        LinkedHashSet<String> lstA = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));
        LinkedHashSet<String> lstB = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B"));

        umlClass.addMethod("Method1", lstA);
        umlClass.addMethod("Method2", lstB);

        assertTrue(umlClass.renameMethod("Method1", lstA, "Method2"));
    }

    /**
     * Test renaming a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename a method that does not exist, failure test")
    public void testRenameMethodNotExist() {
        LinkedHashSet<String> lst = new LinkedHashSet<>();

        assertFalse(umlClass.renameMethod("Method1", lst, "Method2"));
    }

    /**
     * Test renaming Method1, which has the same parameters as Method2, to Method2, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Rename an method")
    public void testRenameMethodSameNameFalse() {
        LinkedHashSet<String> lstA = new LinkedHashSet<>(
            Arrays.asList("Para-A", "Para-B", "Para-C"));

        umlClass.addMethod("Method1", lstA);
        umlClass.addMethod("Method2", lstA);

        assertFalse(umlClass.renameMethod("Method1", lstA, "Method2"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    // TODO remove parameter
    
/*----------------------------------------------------------------------------------------------------------------*/   
    
    // TODO change parameter

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that toString returns what it should.
     */
    @Test
    @DisplayName ("ToString: Print out the object in the correct way with the correct information")
    public void testToString() {
        umlClass.addField("Field-A");
        umlClass.addField("Field-B");
        umlClass.addField("Field-C");

        LinkedHashSet<String> lst = new LinkedHashSet<>();
        lst.add("Para-A");
        lst.add("Para-B");
        lst.add("Para-C");

        umlClass.addMethod("Method1", lst);
        umlClass.addMethod("Method2", lst);
        
        assertEquals("""
                     Class: ClassA
                     \tFields:
                     \t\tField-A
                     \t\tField-B
                     \t\tField-C
                     \tMethods:
                     \t\tMethod1 (Para-A, Para-B, Para-C)
                     \t\tMethod2 (Para-A, Para-B, Para-C)
                     """, umlClass.toString());
    }
}
