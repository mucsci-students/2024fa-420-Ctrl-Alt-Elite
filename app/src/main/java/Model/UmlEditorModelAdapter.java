package Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map;

public class UmlEditorModelAdapter implements JsonSerializer<UmlEditorModel> {

    @Override
    public JsonElement serialize(UmlEditorModel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonModel = new JsonObject();
        
        // Serialize classes (using UmlClassAdapter for each UmlClass)
        JsonObject jsonClasses = new JsonObject();
        for (Map.Entry<String, UmlClass> entry : src.getClasses().entrySet()) {
            UmlClass umlClass = entry.getValue();
            jsonClasses.add(entry.getKey(), context.serialize(umlClass, UmlClass.class));  // Uses UmlClassAdapter here
        }
        jsonModel.add("classes", jsonClasses);

        // Serialize relationships
        jsonModel.add("relationships", context.serialize(src.getRelationships()));

        // Optionally exclude 'classPositions' if you don't need it
        // jsonModel.add("classPositions", context.serialize(src.getClassPositions())); // Omit this line if unnecessary

        return jsonModel;
    }
}
