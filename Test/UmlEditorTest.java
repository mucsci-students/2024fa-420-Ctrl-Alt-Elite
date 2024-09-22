import static org.junit.jupiter.api.Assertions.*;  // JUnit 5 assertions
import org.junit.jupiter.api.BeforeEach;  // JUnit 5 setup
import org.junit.jupiter.api.Test;  // JUnit 5 Test annotation

public class UmlEditorTest {

    private UmlEditor umlEditor;

    @BeforeEach
    public void setUp() {
        umlEditor = new UmlEditor();
    }

    // Test adding a class
    @Test
    public void testAddClass() {
        assertTrue(umlEditor.addClass("ClassA"));
        assertFalse(umlEditor.addClass("ClassA"));  // Class already exists
        assertNotNull(umlEditor.getClasses().get("ClassA"));  // Check if the class was added
    }

    // Test deleting a class
    @Test
    public void testDeleteClass() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.deleteClass("ClassA"));  // Successfully deleted
        assertNull(umlEditor.getClasses().get("ClassA"));  // Class no longer exists
        assertFalse(umlEditor.deleteClass("ClassA"));  // Class doesn't exist anymore
    }

    // Test renaming a class
    @Test
    public void testRenameClass() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.renameClass("ClassA", "ClassB"));  // Successfully renamed
        assertNull(umlEditor.getClasses().get("ClassA"));  // Old name doesn't exist
        assertNotNull(umlEditor.getClasses().get("ClassB"));  // New name exists
        assertFalse(umlEditor.renameClass("ClassA", "ClassC"));  // ClassA doesn't exist anymore
    }

    // Test adding an attribute to a class
    @Test
    public void testAddAttribute() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addAttribute("ClassA", "Attribute1"));  // Successfully added attribute
        assertFalse(umlEditor.addAttribute("ClassB", "Attribute1"));  // ClassB doesn't exist
    }

    // Test deleting an attribute from a class
    @Test
    public void testDeleteAttribute() {
        umlEditor.addClass("ClassA");
        umlEditor.addAttribute("ClassA", "Attribute1");
        assertTrue(umlEditor.deleteAttribute("ClassA", "Attribute1"));  // Successfully deleted attribute
        assertFalse(umlEditor.deleteAttribute("ClassA", "Attribute2"));  // Attribute doesn't exist
    }

    // Test renaming an attribute
    @Test
    public void testRenameAttribute() {
        umlEditor.addClass("ClassA");
        umlEditor.addAttribute("ClassA", "Attribute1");
        assertTrue(umlEditor.renameAttribute("ClassA", "Attribute1", "Attribute2"));  // Successfully renamed
        assertFalse(umlEditor.renameAttribute("ClassA", "Attribute1", "Attribute3"));  // Old attribute doesn't exist
    }

    // Test adding a relationship between classes
    @Test
    public void testAddRelationship() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        assertTrue(umlEditor.addRelationship("ClassA", "ClassB"));  // Relationship added
        assertFalse(umlEditor.addRelationship("ClassA", "ClassC"));  // ClassC doesn't exist
    }

    // Test deleting a relationship between classes
    @Test
    public void testDeleteRelationship() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        umlEditor.addRelationship("ClassA", "ClassB");
        assertTrue(umlEditor.deleteRelationship("ClassA", "ClassB"));  // Relationship deleted
        assertFalse(umlEditor.deleteRelationship("ClassA", "ClassC"));  // Relationship doesn't exist
    }

    // Test listing all classes
    @Test
    public void testListClasses() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        umlEditor.listClasses();  // Print output expected: "ClassA", "ClassB"
    }

    // Test listing a specific class
    @Test
    public void testListClass() {
        umlEditor.addClass("ClassA");
        umlEditor.listClass("ClassA");  // Print output expected: "ClassA"
        umlEditor.listClass("ClassB");  // Print output expected: "Class 'ClassB' does not exist."
    }

    // Test listing relationships between classes
    @Test
    public void testListRelationships() {
        umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        umlEditor.addRelationship("ClassA", "ClassB");
        umlEditor.listRelationships();  // Print output expected: Relationship between "ClassA" and "ClassB"
    }
/*----------------------------------------------------------------------------------------------------------------*/
    // Additional test cases for edge scenarios

    // Test adding an empty class name
    @Test
    public void testAddEmptyClassName() {
        assertFalse(umlEditor.addClass(""));  // Class name should not be empty
    }

    // Test renaming a class to an empty string
    @Test
    public void testRenameClassToEmptyString() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.renameClass("ClassA", ""));  // Class name should not be empty
    }

    // Test adding a null class name
    @Test
    public void testAddNullClassName() {
        assertFalse(umlEditor.addClass(null));  // Should return false, class name can't be null
    }

    // Test renaming a class to null
    @Test
    public void testRenameClassToNull() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.renameClass("ClassA", null));  // Should return false, class name can't be null
    }

    // Test adding duplicate attribute names
    @Test
    public void testAddDuplicateAttribute() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addAttribute("ClassA", "Attribute1"));
        assertFalse(umlEditor.addAttribute("ClassA", "Attribute1"));  // Should not allow duplicate attributes
    }

    // Test deleting a non-existent attribute
    @Test
    public void testDeleteNonExistentAttribute() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.deleteAttribute("ClassA", "NonExistentAttribute"));  // Attribute does not exist
    }

    // Test adding a relationship between the same class
    @Test
    public void testAddRelationshipBetweenSameClass() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.addRelationship("ClassA", "ClassA"));  // Can't relate a class to itself
    }

    // Test case sensitivity in class names
    @Test
    public void testCaseSensitivityInClassNames() {
        umlEditor.addClass("ClassA");
        assertTrue(umlEditor.addClass("classa"));  // If class names are case-sensitive, this should pass
        assertFalse(umlEditor.addClass("ClassA"));  // ClassA already exists
    }

    // Test adding a relationship between non-existent classes
    @Test
    public void testAddRelationshipNonExistentClasses() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.addRelationship("ClassA", "NonExistentClass"));  // ClassB doesn't exist
    }

    // Test deleting a relationship between non-existent classes
    @Test
    public void testDeleteRelationshipNonExistentClasses() {
        umlEditor.addClass("ClassA");
        assertFalse(umlEditor.deleteRelationship("ClassA", "NonExistentClass"));  // Relationship doesn't exist
    }
}
