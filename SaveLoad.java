import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SaveLoad {
    private UmlEditor umlEditor;

    public SaveLoad(UmlEditor umlEditor) {
        this.umlEditor = umlEditor;
    }

    public void save(String filename) {
        Gson gson = new Gson();  // Create a Gson object
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(umlEditor, writer);  // Convert umlEditor to JSON and write to file
            System.out.println("Data successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    public void loadFromJson(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Type umlEditorType = new TypeToken<UmlEditor>() {}.getType();  // Specify the type of UmlEditor
            this.umlEditor = gson.fromJson(reader, umlEditorType);  // Load the JSON data into umlEditor
            System.out.println("Data successfully loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
    }
}
