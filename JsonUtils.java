// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import com.google.gson.reflect.TypeToken;

// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.lang.reflect.Type;
// import java.util.Map;
// import java.util.Set;

// public class JsonUtils {
//     private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

//     public static void save(UmlEditor editor, String filename) throws IOException {
//         try (FileWriter writer = new FileWriter(filename)) {
//             Map<String, UmlClass> classes = editor.getClasses();
//             Set<UmlRelationship> relationships = editor.getRelationships();
//             gson.toJson(new UmlEditorData(classes, relationships), writer);
//         }
//     }

//     public static UmlEditor load(String filename) throws IOException {
//         try (FileReader reader = new FileReader(filename)) {
//             Type type = new TypeToken<UmlEditorData>() {}.getType();
//             UmlEditorData data = gson.fromJson(reader, type);
//             UmlEditor editor = new UmlEditor();
//             editor.setClasses(data.getClasses());
//             editor.setRelationships(data.getRelationships());
//             return editor;
//         }
//     }

//     private static class UmlEditorData {
//         private Map<String, UmlClass> classes;
//         private Set<UmlRelationship> relationships;

//         public UmlEditorData(Map<String, UmlClass> classes, Set<UmlRelationship> relationships) {
//             this.classes = classes;
//             this.relationships = relationships;
//         }

//         public Map<String, UmlClass> getClasses() {
//             return classes;
//         }

//         public Set<UmlRelationship> getRelationships() {
//             return relationships;
//         }
//     }
// }
