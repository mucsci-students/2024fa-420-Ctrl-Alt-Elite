package fa.ctrl.alt.elite;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A test class that checks the functionality of the save and load commands
 */
public class JsonUtilsTest {

    private UmlEditor umlEditor; // The UML editor instance to be tested
    private static final String TEST_FILENAME = "test_data.json"; // Filename for test data

    /**
     * Setup method to initialize the UML editor before each test.
     */
    @BeforeEach
    public void setUp() {
        umlEditor = new UmlEditor();
        umlEditor.addClass("ClassA"); // Add a sample class
        umlEditor.addClass("ClassB"); // Add another sample class
        umlEditor.addRelationship("ClassA", "ClassB"); // Add a relationship between the classes
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
     * Test saving and loading UML editor data.
     * @throws IOException
     */
    @Test
    @DisplayName ("Save and Load: Save data about the Uml Editor to a JSON file and loat it")
    public void testSaveAndLoad() throws IOException {
        // Save the UML editor data to a JSON file
        JsonUtils.save(umlEditor, TEST_FILENAME);

        // Load the UML editor data from the JSON file
        UmlEditor loadedEditor = JsonUtils.load(TEST_FILENAME);

        // Validate that the loaded data is not null and matches the original data
        assertNotNull(loadedEditor);
        assertEquals(2, loadedEditor.getClasses().size()); // Check the number of classes
        assertEquals(1, loadedEditor.getRelationships().size()); // Check the number of relationships
        assertTrue(loadedEditor.getClasses().containsKey("ClassA")); // Verify ClassA exists
        assertTrue(loadedEditor.getClasses().containsKey("ClassB")); // Verify ClassB exists
    }

    /**
     * Test saving a null UML editor, should fail.
     */
    @Test
    @DisplayName ("Save: Save a null UNL editor, failure test")
    public void testSaveNullEditor() {
        // Expect a NullPointerException when trying to save a null editor
        assertThrows(NullPointerException.class, () -> {
            JsonUtils.save(null, TEST_FILENAME);
        });
    }

    /**
     * Test saving and loading an empty UML edito.r
     * @throws IOException
     */
    @Test
    @DisplayName ("Save: Save an empty editor")
    public void testSaveEmptyEditor() throws IOException {
        UmlEditor emptyEditor = new UmlEditor(); // Create an empty UML editor
        JsonUtils.save(emptyEditor, TEST_FILENAME); // Save the empty editor

        // Load the UML editor from the JSON file
        UmlEditor loadedEditor = JsonUtils.load(TEST_FILENAME);
        // Verify that the loaded editor is empty
        assertNotNull(loadedEditor);
        assertTrue(loadedEditor.getClasses().isEmpty()); // Check no classes
        assertTrue(loadedEditor.getRelationships().isEmpty()); // Check no relationships
    }
    
    /**
     * Test loading from a non-existent file, should fail.
     */
    @Test
    @DisplayName ("Load: Load a file that does not exist, failure test")
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
    @DisplayName ("Load: Load and invalid JSON file, failure test")
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
