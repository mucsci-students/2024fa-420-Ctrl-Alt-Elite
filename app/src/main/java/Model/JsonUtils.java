package Model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * Utility class for saving and loading UML editor data in JSON format.
 */
public class JsonUtils {
    // Register the UmlClassAdapter with GsonBuilder
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(UmlClass.class, new UmlClassAdapter()) // Custom adapter for UmlClass
            .registerTypeAdapter(UmlEditorModel.class, new UmlEditorModelAdapter())  // Register UmlEditorModelAdapter
            .create();

            public static void save(UmlEditorModel editorModel, String filename) throws IOException {
                if (editorModel == null) {
                    throw new IllegalArgumentException("Editor model is null and cannot be saved.");
                }
            
                try (FileWriter writer = new FileWriter(filename)) {
                    String json = gson.toJson(editorModel);
                    System.out.println("Serialized JSON: " + json); // Debugging
                    gson.toJson(editorModel, writer); // Uses UmlClassAdapter for UmlClass objects
                    System.out.println("Data saved successfully to " + filename);
                } catch (IOException e) {
                    System.err.println("Error saving data to file: " + e.getMessage());
                    throw e;
                }
            }
            

    /**
     * Loads the UML editor data (classes and relationships) from a JSON file.
     *
     * @param filename The name of the file to load the data from.
     * @return An {@link UmlEditorModel} instance populated with the loaded classes and relationships.
     * @throws IOException If there is an issue reading the file or if the JSON is invalid.
     */
    public static UmlEditorModel load(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            Type type = new TypeToken<UmlEditorModel>() {}.getType();
            try {
                return gson.fromJson(reader, type); // Deserializes the JSON
            } catch (JsonParseException e) {
                throw new IOException("Invalid JSON format", e);
            }
        }
    }
}