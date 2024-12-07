package Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Adapter class for converting UmlClass objects into JSON format.
 * Implements the Adapter design pattern to facilitate custom serialization
 * of UmlClass attributes, methods, and position.
 */
public class UmlClassAdapter implements JsonSerializer<UmlClass> {

    /**
     * Converts a UmlClass object into its JSON representation,
     * including its name, fields, methods, and position.
     */
    @Override
    public JsonElement serialize(UmlClass src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonClass = new JsonObject();

        // Add name
        jsonClass.addProperty("name", src.getName());

        // Serialize fields as an array of objects
        ArrayList<JsonObject> fieldsArray = new ArrayList<>();
        for (Map.Entry<String, String> entry : src.getFields().entrySet()) {
            JsonObject field = new JsonObject();
            field.addProperty("name", entry.getKey());
            field.addProperty("type", entry.getValue());
            fieldsArray.add(field);
        }
        jsonClass.add("fields", context.serialize(fieldsArray));

        // Serialize methods as an array of objects (name, return_type, params)
        ArrayList<JsonObject> methodsList = new ArrayList<>();
        for (String methodString : src.getMethods()) {
            JsonObject methodObj = new JsonObject();

            try {
                // Extract method details (methodName(returnType, params))
                String[] methodParts = methodString.split("\\(");
                if (methodParts.length < 1)
                    continue; // Skip if no method name or params

                String methodNameWithReturnType = methodParts[0].trim(); // Example: "setPSI"
                String[] nameAndType = methodNameWithReturnType.split(" ");
                if (nameAndType.length < 2)
                    continue; // Skip if no return type or method name

                String returnType = nameAndType[0].trim(); // Extract return type
                String methodName = nameAndType[1].trim(); // Extract method name

                methodObj.addProperty("name", methodName);
                methodObj.addProperty("return_type", returnType);

                // Extract parameters
                ArrayList<JsonObject> paramsList = new ArrayList<>();
                if (methodParts.length > 1 && methodParts[1].contains(")")) {
                    String paramString = methodParts[1].split("\\)")[0].trim(); // Extract the part inside parentheses
                    if (!paramString.isEmpty()) {
                        String[] params = paramString.split(",");
                        for (String param : params) {
                            JsonObject paramObj = new JsonObject();
                            String[] paramParts = param.trim().split(" "); // Assuming "type name" format
                            if (paramParts.length == 2) {
                                paramObj.addProperty("name", paramParts[1]);
                                paramObj.addProperty("type", paramParts[0]);
                            }
                            paramsList.add(paramObj);
                        }
                    }
                }
                methodObj.add("params", context.serialize(paramsList));
                methodsList.add(methodObj);
            } catch (Exception e) {
                System.err.println("Error serializing method: " + methodString);
                e.printStackTrace(); // Log the error for debugging
            }
        }

        jsonClass.add("methods", context.serialize(methodsList));

        // Only add position if it is not null
        if (src.getPosition() != null) {
            JsonObject position = new JsonObject();
            position.addProperty("x", src.getPosition().getX());
            position.addProperty("y", src.getPosition().getY());
            jsonClass.add("position", position);
        }

        return jsonClass;
    }

}
