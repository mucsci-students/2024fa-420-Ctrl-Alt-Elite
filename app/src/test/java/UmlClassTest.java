import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

    /** A position in the GUI */
    private Point position = new Point(0, 0);

    /**
     * A function to initialize the UmlClass object.
     */
    @BeforeEach
    public void setUp() {
        umlClass = new UmlClass("ClassA");
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test creating a UmlClass object with a name.
     */
    @Test
    @DisplayName ("Constructor: Create a UmlClass object with a name")
    public void testUmlClassConstructor() {
        UmlClass umlClassTest = new UmlClass("ClassA");
        assertEquals(umlClassTest.getName(), umlClass.getName());
    }

    /**
     * Test creating a UmlClass object with a name and position.
     */
    @Test
    @DisplayName ("Constructor: Create a UmlClass object with a name and position")
    public void testUmlClassConstructorPosition() {
        UmlClass umlClassTest = new UmlClass("ClassA", position);
        assertEquals(umlClassTest.getName(), umlClass.getName());
    }

    /**
     * Test the copy constructor.
     */
    @Test
    @DisplayName ("Constructor: Test the copy constructor")
    public void testUmlClassCopyConstructor() {
        UmlClass umlClassTest = new UmlClass("ClassB", position);
        umlClass = new UmlClass(umlClassTest);
        assertEquals(umlClassTest.getName(), umlClass.getName());
    }

    /**
     * Test the copy constructor, but with no set position for either object.
     */
    @Test
    @DisplayName ("Constructor: Test the copy constructor")
    public void testUmlClassCopyConstructorNoPosition() {
        UmlClass umlClassTest = new UmlClass("ClassB");
        umlClass = new UmlClass(umlClassTest);
        assertEquals(umlClassTest.getName(), umlClass.getName());
    }

/*----------------------------------------------------------------------------------------------------------------*/
//Test GUI methods

    /**
     * Test getting the position of a point.
     */
    @Test
    @DisplayName ("GetPosition: Test getting the position of a point")
    public void testGetPosition() {
        UmlClass umlClassTest = new UmlClass("ClassA", position);
        assertEquals(umlClassTest.getPosition(), position);
    }

    /**
     * Test setting the position of a point.
     */
    @Test
    @DisplayName ("SetPosition: Test setting the position of a point.")
    public void testSetPosition() {
        Point position2 = new Point(1, 1);
        UmlClass umlClassTest = new UmlClass("ClassA", position);
        umlClassTest.setPosition(position2);
        
        assertEquals(umlClassTest.getPosition(), position2);
    }

    /**
     * Test that method names are return properly.
     */
    @Test
    @DisplayName ("GetMethodNames: Test that method names are return properly")
    public void testGetMethodNames() {
        UmlClass umlClassTest = new UmlClass("ClassB");
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        String returnType = "String";

        List<String> parameterNames = new ArrayList<>();
        parameterNames.add("Method1");

        umlClassTest.addMethod("Method1", parameters, returnType);
        assertEquals(parameterNames, umlClassTest.getMethodNames());
    }

    /**
     * Test that a list is returned when there are no methods.
     */
    @Test
    @DisplayName ("GetMethodNames: Test that a list is returned when there are no methods")
    public void testGetMethodNamesNone() {
        UmlClass umlClassTest = new UmlClass("ClassB");
        List<String> parameterNames = new ArrayList<>();

        assertEquals(parameterNames, umlClassTest.getMethodNames());
    }

    /**
     * Test that a list is returned when there are no methods.
     */
    @Test
    @DisplayName ("GetMethodNames: Test that a list is returned when there are no methods")
    public void testGetMethodNamesNull() {
        UmlClass umlClassTest = new UmlClass("ClassB");
        List<String> parameterNames = new ArrayList<>();
       
        umlClassTest.setMethodsList(null);
        assertEquals(parameterNames, umlClassTest.getMethodNames());
    }

    /**
     * Test that the list of parameters of a method are returned.
     */
    @Test
    @DisplayName ("GetMethodParameters: Test that the list of parameters of a method are returned")
    public void testGetMethodParameters() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertEquals(parameters, umlClass.getMethodParameters("Method1"));
    }

    /**
     * Test that null is returned when the methods list is empty.
     */
    @Test
    @DisplayName ("GetMethodParameters: Test that null is returned when the methods list is empty")
    public void testGetMethodParametersNone() {
        assertNull(umlClass.getMethodParameters("Method1"));
    }

    /**
     * Test that null is returned when the method list is null.
     */
    @Test
    @DisplayName ("GetMethodParameters: Test that null is returned when the method list is null")
    public void testGetMethodParametersNull() {
        umlClass.setMethodsList(null);
        assertNull(umlClass.getMethodParameters("Method1"));
    }

    /**
     * Test that null is returned when the method asked for is not present.
     */
    @Test
    @DisplayName ("GetMethodParameters: Test that null is returned when the method asked for is not present")
    public void testGetMethodParametersNotFound() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertNull(umlClass.getMethodParameters("Method2"));
    }

    /**
     * Test that the return type of a method is returned.
     */
    @Test
    @DisplayName ("GetMethodReturnType: Test that the return type of a method is returned")
    public void testGetMethodReturnType() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertEquals("int", umlClass.getMethodReturnType("Method1"));
    }

    /**
     * Test that null is returned when the methods list is empty.
     */
    @Test
    @DisplayName ("GetMethodReturnType: Test that null is returned when the methods list is empty")
    public void testGetMethodReturnTypeNone() {
        assertNull(umlClass.getMethodReturnType("Method1"));
    }

    /**
     * Test that null is returned when the method list is null.
     */
    @Test
    @DisplayName ("GetMethodReturnType: Test that null is returned when the method list is null")
    public void testGetMethodReturnTypeNull() {
        umlClass.setMethodsList(null);
        assertNull(umlClass.getMethodReturnType("Method1"));
    }

    /**
     * Test that null is returned when the method cannot be found.
     */
    @Test
    @DisplayName ("GetMethodReturnType: Test that the return type of a method is returned")
    public void testGetMethodReturnTypeNotFound() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertNull(umlClass.getMethodReturnType("Method2"));
    }

    /**
     * Test that the method is renamed.
     */
    @Test
    @DisplayName ("RenameMethod: Test that the method is renamed")
    public void testRenameMethodGUI() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        umlClass.renameMethod("Method1", "Method2");
        assertEquals("Method2", umlClass.getMethodNames().getFirst());
    }

    /**
     * Test renaming a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Test renaming a method that does not exist, failure test")
    public void testRenameMethodGUINotFound() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertFalse(umlClass.renameMethod("Method2", "Method2"));
    }

    /**
     * Test renaming a method that is the same as another, should fail.
     */
    @Test
    @DisplayName ("RenameMethod: Test renaming a method that is the same as another, failure test")
    public void testRenameMethodGUISame() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertFalse(umlClass.renameMethod("Method1", "Method1"));
    }

    /**
     * Test that the method is deleted.
     */
    @Test
    @DisplayName ("DeleteMethod: Test that the method is renamed")
    public void testDeleteMethodGUI() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertTrue(umlClass.deleteMethod("Method1"));
    }

    /**
     * Test deleting a method when it does not exist, should fail
     */
    @Test
    @DisplayName ("DeleteMethod: Test deleting a method when it does not exist, failure test")
    public void testDeleteMethodGUINotFound() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        assertFalse(umlClass.deleteMethod("Method2"));
    }

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
// Misc.

    /**
     * Test that getFields returns the map of fields.
     */
    @Test
    @DisplayName ("GetFields: Return the map of fields")
    public void testGetFields() {
        LinkedHashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("F1", "String");
        umlClass.addField("String", "F1");
        
        assertEquals(fields, umlClass.getFields());
    }

    /**
     * Test that setParametersNull sets the parameter list to null.
     */
    @Test
    @DisplayName ("SetParametersNull: Set the parameter list to null")
    public void testSetParametersNull() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertNull(umlClass.setParametersNull(parameters));
    }

    /**
     * Test that getMethods returns the list of methods.
     */
    @Test
    @DisplayName ("GetMethods: Test that getMethods returns the list of methods")
    public void testGetMethods() {
        List<String[]> parameters = new ArrayList<>();
        List<String[]> parameters2 = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        umlClass.addMethod("Method1", parameters2, "int");
        assertEquals("[int Method1(int P1, String P2), int Method1()]", umlClass.getMethods().toString());
    }

    /**
     * Test that getMethodsList returns the list of methods.
     */
    @Test
    @DisplayName ("GetMethodsList: Test that getMethodsList returns the list of methods")
    public void testGetMethodsList() {
        List<String[]> parameters = new ArrayList<>();
        umlClass.addMethod("Method1", parameters, "String");

        assertEquals("Method1", umlClass.getMethodsList().getFirst().getName());
    }

    /**
     * Test that setReturnType sets the return type of a method.
     */
    @Test
    @DisplayName ("setReturnType: Test that setReturnType sets the return type of a method")
    public void testSetReturnType() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        umlClass.getMethodsList().getFirst().setReturnType("String");;
        
        assertEquals("String", umlClass.getMethodsList().getFirst().getReturnType());
    }

    /**
     * Test that a single method's string representation, with out any parameters, is returned.
     */
    @Test
    @DisplayName ("SingleMethodString: Test that a single method's string representation is returned")
    public void testSingleMethodString() {
        List<String[]> parameters = new ArrayList<>();
        umlClass.addMethod("Method1", parameters, "int");

        assertEquals("int Method1()", umlClass.getMethodsList().getFirst().singleMethodString());
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

    /**
     * Test adding a field with an empty type, should fail.
     */
    @Test
    @DisplayName("AddField: Add a field with an empty type, failure test")
    public void testAddFieldEmptyType() {
        assertFalse(umlClass.addField("","Field1"));
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

    /**
     * Test renaming a field to an invalid name, should fail.
     */
    @Test
    @DisplayName("RenameField: Rename a field to an invalid name, failure test")
    public void testRenameFieldInvalidName() {
        umlClass.addField("int","Field1");
        assertFalse(umlClass.renameField("Field1", "Field 2"));
    }

    /**
     * Test renaming a field to a new name that already exists, should fail.
     */
    @Test
    @DisplayName("RenameField: Rename a field to a new name that already exists, failure test")
    public void testRenameFieldDupNewName() {
        umlClass.addField("int","Field1");
        assertFalse(umlClass.renameField("Field1", "Field1"));
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test updating a field's type.
     */
    @Test
    @DisplayName("UpdateFieldType: Test updating a field's type")
    public void testUpdateFieldType() {
        umlClass.addField("int","Field1");
        assertTrue(umlClass.updateFieldType("Field1", "String"));
    }

    /**
     * Test updating a field's type that does not exist, should fail.
     */
    @Test
    @DisplayName("UpdateFieldType: Test updating a field's type that does not exist, failure test")
    public void testUpdateFieldTypeNotExist() {
        assertFalse(umlClass.updateFieldType("Field1", "String"));
    }

    /**
     * Test updating a field's type with an empty new type, should fail.
     */
    @Test
    @DisplayName("UpdateFieldType: Test updating a field's type with an empty new type, failure test")
    public void testUpdateFieldTypeIsEmpty() {
        umlClass.addField("int","Field1");
        assertFalse(umlClass.updateFieldType("Field1", ""));
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
       
        assertNotEquals(methodA, methodB);
    }

    /**
     * Test comparing two methods when one is null.
     */
    @Test
    @DisplayName ("Equals: Test comparing two methods when one is null")
    public void testNotEqualityNull() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        UmlClass.Method methodA = umlClass.new Method("Method1", parametersA, "void");

        assertNotEquals(methodA, null);
    }

    /**
     * Test comparing two methods when one is not a method.
     */
    @Test
    @DisplayName ("Equals: Test comparing two methods when one is not a method")
    public void testNotEqualityNotMethod() {
        UmlClass umlClass2 = new UmlClass("ClassB");
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        UmlClass.Method methodA = umlClass.new Method("Method1", parametersA, "void");

        assertNotEquals(methodA, umlClass2);
    }

    /**
     * Test comparing two methods when one has a null name.
     */
    @Test
    @DisplayName ("Equals: Test comparing two methods when one has a null name")
    public void testNotEqualityNullName() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] otherP2 = {"int", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(otherP2);

        UmlClass.Method methodA = umlClass.new Method(null, parametersA, "void");
        UmlClass.Method methodB = umlClass.new Method("Method2", parametersB, "int");

        assertNotEquals(methodA, methodB);
    }

    /**
     * Test comparing two methods when both have null names.
     */
    @Test
    @DisplayName ("Equals: Test comparing two methods when one has a null name")
    public void testNotEqualityNullNames() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        String[] otherP2 = {"int", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P1);
        parametersB.add(otherP2);

        UmlClass.Method methodA = umlClass.new Method(null, parametersA, "void");
        UmlClass.Method methodB = umlClass.new Method(null, parametersA, "int");
        methodA.setParameters(null);

        assertNotEquals(methodA, methodB);
    }

    /**
     * Test comparing two methods when both parameter lists are null.
     */
    @Test
    @DisplayName ("Equals: Test comparing two methods when one has a null name")
    public void testNotEqualityNullParameters() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        UmlClass.Method methodA = umlClass.new Method("Method1", parametersA, "void");
        UmlClass.Method methodB = umlClass.new Method("Method1", parametersA, "int");
        methodA.setParameters(null);
        methodB.setParameters(null);

        assertTrue(methodA.equals(methodB));
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

    /**
     * Test that different objects have different hash code, should fail.
     */
    @Test
    @DisplayName ("HashCode: Test that different objects different hash code, failure test")
    public void testHashCodeNull() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        UmlClass.Method methodA = umlClass.new Method("Method1", parameters, "int");
        methodA.setName(null);
        methodA.setParameters(null);
        methodA.setReturnType(null);

        assertNotNull(methodA.hashCode());
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the method's toString returns what it should.
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

    /**
     * Test that the methods's toString returns what it should.
     */
    @Test
    @DisplayName ("MethodToString: Print out the object")
    public void testMethodToString2() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        umlClass.addMethod("Method1", parameters, "int");
        
        assertEquals("\tMethod: int Method1 (int P1, String P2)\n", umlClass.getMethodsList().getFirst().toString());
    }

    /**
     * Test the methods's toString with no parameters.
     */
    @Test
    @DisplayName ("MethodToString: Test the methods's toString with no parameters")
    public void testMethodToStringNoParameters() {
        List<String[]> parameters = new ArrayList<>();
        umlClass.addMethod("Method1", parameters, "int");
        
        assertEquals("\tMethod: int Method1 ()\n", umlClass.getMethodsList().getFirst().toString());
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

    /**
     * Test adding a method with an invalid name.
     */
    @Test
    @DisplayName ("AddMethod: Test adding a method with an invalid name")
    public void testAddMethodNameInvalid() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("Method 1", parameters, "int"));
    }

    /**
     * Test adding a method with an invalid return type.
     */
    @Test
    @DisplayName ("AddMethod: Test adding a method with an invalid return type")
    public void testAddMethodReturnTypeInvalid() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("Method1", parameters, "in t"));
    }

    /**
     * Test adding a method with an empty return type.
     */
    @Test
    @DisplayName ("AddMethod: Test adding a method with an empty return type")
    public void testAddMethodReturnTypeEmpty() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("Method1", parameters, ""));
    }

    /**
     * Test adding a method with invalid parameters.
     */
    @Test
    @DisplayName ("AddMethod: Test adding a method with invalid parameters.")
    public void testAddMethodInvalidParametersType() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("Method1", parameters, "int"));
    }

    /**
     * Test adding a method with invalid parameters.
     */
    @Test
    @DisplayName ("AddMethod: Test adding a method with invalid parameters.")
    public void testAddMethodInvalidParameters() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", ""};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("Method1", parameters, "int"));
    }

    /**
     * Test adding a method with duplicate parameter names.
     */
    @Test
    @DisplayName ("AddMethod: Test adding a method with invalid parameters.")
    public void testAddMethodDupParameters() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P1"};
        parameters.add(P1);
        parameters.add(P2);
        
        assertFalse(umlClass.addMethod("Method1", parameters, "int"));
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

    /**
     * Test deleting a method that does not exist, should fail.
     */
    @Test
    @DisplayName ("DeleteMethod: Delete an method that dose not exist, failure test")
    public void testDeleteMethodNotExist2() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
        assertFalse(umlClass.deleteMethod("Method2", parameters, "int"));
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
    @DisplayName ("RenameMethod: Test renaming Method1, which has the same parameters as Method2, to Method2, failure test")
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

    /**
     * Test invalid method input.
     */
    @Test
    @DisplayName ("RenameMethod: Test invalid method input")
    public void testRenameMethodInvalidInput() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
        umlClass.addMethod("Method2", parameters, "void");

        assertFalse(umlClass.renameMethod("", parameters, "int", "Method2"));
        assertFalse(umlClass.renameMethod("Method1", parameters, "int", ""));
        assertFalse(umlClass.renameMethod("Method1", parameters, "int", "Method 2"));
    }  
    
    /**
     * Test renaming a method when there are no methods.
     */
    @Test
    @DisplayName ("RenameMethod: Test renaming a method when there are no methods")
    public void testRenameMethodNone() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        assertFalse(umlClass.renameMethod("Method1", parameters, "int", "Method2"));
    } 

    /**
     * Test renaming a method when the old method does not exist.
     */
    @Test
    @DisplayName ("RenameMethod: Test renaming a method when the old method does not exist")
    public void testRenameMethodNotExistOld() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
        assertFalse(umlClass.renameMethod("Method3", parameters, "int", "Method2"));
    } 

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test changing the return type of a method.
     */
    @Test
    @DisplayName ("ChangeReturnType: Test changing the return type of a method")
    public void testChangeReturnType() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");

        assertTrue(umlClass.changeReturnType("Method1", parameters, "int", "String"));
    } 

    /**
     * Test changing the return type of a method with invalid input, should fail.
     */
    @Test
    @DisplayName ("ChangeReturnType: Test changing the return type of a method with invalid input, failure test")
    public void testChangeReturnTypeInvalid() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        assertFalse(umlClass.changeReturnType("Method1", parameters, "int", ""));
        assertFalse(umlClass.changeReturnType("Method1", parameters, "int", "Str ing"));
        assertFalse(umlClass.changeReturnType("Method1", parameters, "int", "String"));
    } 

    /**
     * Test changing the return type of a method when the method does not exist, should fail.
     */
    @Test
    @DisplayName ("ChangeReturnType: Test changing the return type of a method when the method does not exist, failure test")
    public void testChangeReturnTypeNotExist() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"int", "P2"};
        parameters.add(P1);
        parameters.add(P2);

        umlClass.addMethod("Method1", parameters, "int");
        assertFalse(umlClass.changeReturnType("Method2", parameters, "int", "String"));
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

    /** 
     * Test trying to remove a parameter from a method with invalid input, should fail.
     */
    @Test
    @DisplayName ("removeParameter: Remove a parameter with invalid input, failure test")
    public void testRemoveParameterInvalidInput2() {
        List<String[]> parameters = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parameters.add(P1);
        parameters.add(P2);
        
        String[] parameterPair = {"int", "P1"};
        String[] parameterPair2 = {"int", ""};
        umlClass.addMethod("Method1", parameters, "int");
        
        assertFalse(umlClass.removeParameter("", parameters, "int", parameterPair));
        assertFalse(umlClass.removeParameter("Method1", parameters, "int", parameterPair2));
        assertFalse(umlClass.removeParameter("Method1", null, "int", parameterPair));
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

    /**
     * Test trying to change a parameter with invalid input, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters with invalid input, failure test")
    public void testChangeParametersInvalidInput2() {
        List<String[]> parametersA = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parametersA.add(P1);
        parametersA.add(P2);
        
        List<String[]> parametersB = new ArrayList<>();
        String[] P3 = {"int", "P3"};
        parametersA.add(P3);

        umlClass.addMethod("MethodA", parametersA, "int");
        
        assertFalse(umlClass.changeParameters("", parametersA, "int", parametersB));
        assertFalse(umlClass.changeParameters("MethodA", parametersA, "int", parametersA));
    }

    /**
     * Test trying to change a parameter with invalid input, should fail.
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters with invalid input, failure test")
    public void testChangeParametersInvalidInput3() {
        List<String[]> parametersA = new ArrayList<>();
        String[] P1 = {"int", "P1"};
        String[] P2 = {"String", "P2"};
        parametersA.add(P1);
        parametersA.add(P2);
        umlClass.addMethod("MethodA", parametersA, "int");
        
        List<String[]> parametersB = new ArrayList<>();
        String[] P3 = {"", "P3"};
        parametersB.add(P3);
        assertFalse(umlClass.changeParameters("MethodA", parametersA, "int", parametersB));
        
        parametersB.remove(0);
        String[] P4 = {"int", ""};
        parametersB.add(P4);
        assertFalse(umlClass.changeParameters("MethodA", parametersA, "int", parametersB));

        parametersB.remove(0);
        String[] P5 = {"in t", "P5"};
        parametersB.add(P5);
        assertFalse(umlClass.changeParameters("MethodA", parametersA, "int", parametersB));

        parametersB.remove(0);
        String[] P6 = {"int", "P6"};
        String[] P7 = {"int", "P6"};
        parametersB.add(P6);
        parametersB.add(P7);
        assertFalse(umlClass.changeParameters("MethodA", parametersA, "int", parametersB));
    }

    /**
     * Test trying to change a parameter 
     */
    @Test
    @DisplayName ("changeParameters: Change the list of parameters with invalid input, failure test")
    public void testChangeParametersAlreadyExist() {
        String[] P1 = {"int", "P1"};
        String[] P2 = {"void", "P2"};
        
        List<String[]> parametersA = new ArrayList<>();
        parametersA.add(P1);
        parametersA.add(P2);

        List<String[]> parametersB = new ArrayList<>();
        parametersB.add(P2);

        umlClass.addMethod("Method1", parametersA, "int");
        umlClass.addMethod("Method1", parametersB, "int");
        
        assertFalse(umlClass.changeParameters("Method1", parametersA, "int", parametersB));
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
