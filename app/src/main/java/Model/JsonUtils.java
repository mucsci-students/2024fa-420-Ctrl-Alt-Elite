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
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves the UML editor data (classes and relationships) to a JSON file.
     *
     * @param editorModel The {@link UmlEditorModel} containing the classes and relationships.
     * @param filename    The name of the file to save the data to.
     * @throws IOException If there is an issue writing to the file.
     */
    public static void save(UmlEditorModel editorModel, String filename) throws IOException, NullPointerException {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(editorModel, writer);
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
                return gson.fromJson(reader, type);
            } catch (JsonParseException e) {
                throw new IOException("Invalid JSON format", e);
            }
        }
    }
}