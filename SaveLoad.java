import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;


public class SaveLoad {
    
    
    public void save (String fileName) {
        Gson gson = new Gson();            // Creates a new Gson object to handle JSON operations.
        try (FileWriter writer = new FileWriter(fileName)) { // Tries to open a file for writing.
            gson.toJson(this, writer);  // Converts the current object (this) to JSON and writes it to the file.
            System.out.println("Saved to " + fileName);     // Prints a message saying that the file was saved.
        } catch (IOException e) {                           // If there’s a problem writing the file, it catches the error.
            System.out.println("Error saving to file: " + e.getMessage());      // Prints an error message.
        }

    }

    public void loadFromJson(String fileName) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fileName)) {
            Type type = new TypeToken<UMLManager>() {}.getType();
            UMLManager loadedManager = gson.fromJson(reader, type);
            this.classes = loadedManager.classes;
            this.relationships = loadedManager.relationships;
            System.out.println("Loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}

