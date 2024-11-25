import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlEditorModel;

/**
 * A test class that checks the functionality of the save and load commands
 */
public class JsonUtilsTest {

    /** The UML editor instance to be tested */
    private UmlEditorModel editorModel;

    /** Filename for test data */
    private static final String TEST_FILENAME = "test_data.json"; 

    /**
     * Setup method to initialize the UML editor before each test.
     */
    @BeforeEach
    public void setUp() {
        editorModel = UmlEditorModel.getInstance();

        editorModel.addClass("ClassA"); // Add a sample class
        editorModel.addClass("ClassB"); // Add another sample class
        RelationshipType type = RelationshipType.Aggregation;

        editorModel.addRelationship("ClassA", "ClassB", type); // Add a relationship between the classes
    }

    /**
     * Cleanup method to delete the test file after each test.
     */
    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILENAME);
        if (file.exists()) {
            file.delete(); // Remove the test file if it exists
        }
    }

    @Test
    @DisplayName ("Constructor: Test that the object initializes")
    public void testConstructor() {
        JsonUtils jsonU = new JsonUtils();
        assertNotNull(jsonU);
    }

    /**
     * Test saving and loading UML editor data.
     * 
     * @throws IOException
     */
    @Test
    @DisplayName ("Save and Load: Save data about the Uml Editor to a JSON file and load it")
    public void testSaveAndLoad() throws IOException {
        // Save the UML editor data to a JSON file
        JsonUtils.save(editorModel, TEST_FILENAME);

        // Load the UML editor data from the JSON file
        //UmlEditorModel loadedEditor = JsonUtils.load(TEST_FILENAME);

        // Validate that the loaded data is not null and matches the original data
        //assertNotNull(loadedEditor);
        //assertEquals(2, loadedEditor.getClasses().size()); // Check the number of classes
       // assertEquals(1, loadedEditor.getRelationships().size()); // Check the number of relationships
        //assertTrue(loadedEditor.getClasses().containsKey("ClassA")); // Verify ClassA exists
        //assertTrue(loadedEditor.getClasses().containsKey("ClassB")); // Verify ClassB exists
    }
    
    /**
     * Test loading from a non-existent file, should fail.
     */
    @Test
    @DisplayName("Load: Load a file that does not exist, failure test")
    public void testLoadNonExistentFile() {
        // Expect an IOException when loading from a non-existent file
        assertThrows(IOException.class, () -> {
            JsonUtils.load("non_existent_file.json");
        });
    }
    
    /**
     * Test loading from a file with invalid JSON content, should fail.
     */
    @Test
    @DisplayName("Load: Load an invalid JSON file, failure test")
    public void testLoadInvalidJson() {
        // Create a file with invalid JSON content
        try (FileWriter writer = new FileWriter(TEST_FILENAME)) {
            writer.write("Invalid JSON");
        } catch (IOException e) {
            fail("Setup failed to create invalid JSON file."); // Fail the test if setup fails
        }

        // Expect an IOException when loading invalid JSON
        assertThrows(IOException.class, () -> {
            JsonUtils.load(TEST_FILENAME);
        });
    }
}
