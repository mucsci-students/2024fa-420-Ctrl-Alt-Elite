import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
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
