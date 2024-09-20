import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for saving and loading UML editor data in JSON format.
 */
public class JsonUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves the UML editor data (classes and relationships) to a JSON file.
     *
     * @param editor   The {@link UmlEditor} containing the classes and relationships.
     * @param filename The name of the file to save the data to.
     * @throws IOException If there is an issue writing to the file.
     */
    public static void save(UmlEditor editor, String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            Map<String, UmlClass> classes = editor.getClasses();
            Set<UmlRelationship> relationships = editor.getRelationships();
            gson.toJson(new UmlEditorData(classes, relationships), writer);
        }
    }

    /**
     * Loads the UML editor data (classes and relationships) from a JSON file.
     *
     * @param filename The name of the file to load the data from.
     * @return An {@link UmlEditor} instance populated with the loaded classes and relationships.
     * @throws IOException If there is an issue reading the file.
     */
    public static UmlEditor load(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            Type type = new TypeToken<UmlEditorData>() {}.getType();
            UmlEditorData data = gson.fromJson(reader, type);
            UmlEditor editor = new UmlEditor();
            editor.setClasses(data.getClasses());
            editor.setRelationships(data.getRelationships());
            return editor;
        }
    }

    /**
     * A helper class to store UML editor data (classes and relationships) in a format suitable for JSON serialization.
     */
    private static class UmlEditorData {
        private Map<String, UmlClass> classes;
        private Set<UmlRelationship> relationships;

        /**
         * Constructs an instance of {@link UmlEditorData}.
         *
         * @param classes       A map of class names to {@link UmlClass} objects.
         * @param relationships A set of {@link UmlRelationship} objects representing relationships between classes.
         */
        public UmlEditorData(Map<String, UmlClass> classes, Set<UmlRelationship> relationships) {
            this.classes = classes;
            this.relationships = relationships;
        }

        /**
         * Returns the map of class names to {@link UmlClass} objects.
         *
         * @return The classes in the UML editor.
         */
        public Map<String, UmlClass> getClasses() {
            return classes;
        }

        /**
         * Returns the set of {@link UmlRelationship} objects representing relationships between classes.
         *
         * @return The relationships in the UML editor.
         */
        public Set<UmlRelationship> getRelationships() {
             return relationships;
        }
    }
}
