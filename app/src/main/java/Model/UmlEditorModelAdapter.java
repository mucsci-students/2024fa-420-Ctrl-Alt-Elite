package Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Adapter class for converting UmlEditorModel objects into JSON format.
 * Implements the Adapter design pattern to bridge UmlEditorModel with Gson serialization.
 */
public class UmlEditorModelAdapter implements JsonSerializer<UmlEditorModel> {

    /**
     * Converts a UmlEditorModel object into its JSON representation,
     * including its classes and relationships.
     */
    @Override
    public JsonElement serialize(UmlEditorModel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonModel = new JsonObject();

        // Serialize classes as an array
        JsonArray classArray = new JsonArray();
        for (UmlClass umlClass : src.getClasses().values()) {
            classArray.add(context.serialize(umlClass, UmlClass.class)); // Uses UmlClassAdapter for serialization
        }
        jsonModel.add("classes", classArray);

        // Serialize relationships
        jsonModel.add("relationships", context.serialize(src.getRelationships()));

        return jsonModel;
    }
}
