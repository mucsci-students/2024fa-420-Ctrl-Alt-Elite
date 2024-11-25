package Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class UmlEditorModelAdapter implements JsonSerializer<UmlEditorModel> {

    @Override
    public JsonElement serialize(UmlEditorModel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonModel = new JsonObject();
        
        // Serialize classes as an array
        JsonArray classArray = new JsonArray();
        for (UmlClass umlClass : src.getClasses().values()) {
            classArray.add(context.serialize(umlClass, UmlClass.class));  // Uses UmlClassAdapter for serialization
        }
        jsonModel.add("classes", classArray);

        // Serialize relationships
        jsonModel.add("relationships", context.serialize(src.getRelationships()));

        // Optionally exclude 'classPositions' if you don't need it
        // jsonModel.add("classPositions", context.serialize(src.getClassPositions())); // Omit this line if unnecessary

        return jsonModel;
    }
}
