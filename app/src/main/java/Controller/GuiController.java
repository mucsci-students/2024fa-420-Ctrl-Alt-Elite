import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GuiController {
    private UmlEditor umlEditor;
    private JTextArea outputArea;
    private DrawingPanel drawingPanel; // Panel for drawing
    private Map<String, Point> classPositions;

    public GuiController(UmlEditor umlEditor, JTextArea outputArea, DrawingPanel drawingPanel, Map<String, Point> classPositions) {
        this.umlEditor = umlEditor;
        this.outputArea = outputArea;
        this.drawingPanel = drawingPanel;
        this.classPositions = classPositions;
    }

    // Add Class
    public void addClass(String className) {
        if (!className.isEmpty()) {
            umlEditor.addClass(className);
            outputArea.append("Class \"" + className + "\" added successfully.\n");
            updateDrawingPanel();
        } else {
            outputArea.append("Class name cannot be empty.\n");
        }
    }

    // Delete Class
    public void deleteClass(String className) {
        if (umlEditor.deleteClass(className)) {
            outputArea.append("Class \"" + className + "\" deleted successfully.\n");
            updateDrawingPanel();
        } else {
            outputArea.append("Class \"" + className + "\" not found.\n");
        }
    }

    // Rename Class
    public void renameClass(String oldClassName, String newClassName) {
        if (umlEditor.renameClass(oldClassName, newClassName)) {
            outputArea.append("Class \"" + oldClassName + "\" renamed to \"" + newClassName + "\" successfully.\n");
            updateDrawingPanel();
        } else {
            outputArea.append("Class \"" + oldClassName + "\" not found.\n");
        }
    }

    // Add Field
    public void addField(String className, String fieldName) {
        if (umlEditor.addField(className, fieldName)) {
            outputArea.append("Field \"" + fieldName + "\" added to class \"" + className + "\" successfully.\n");
        } else {
            outputArea.append("Class \"" + className + "\" not found.\n");
        }
        updateDrawingPanel();
    }

    // Delete Field
    public void deleteField(String className, String fieldName) {
        if (umlEditor.deleteField(className, fieldName)) {
            outputArea.append("Field \"" + fieldName + "\" deleted from class \"" + className + "\" successfully.\n");
        } else {
            outputArea.append("Class \"" + className + "\" or field \"" + fieldName + "\" not found.\n");
        }
        updateDrawingPanel();
    }

    // Add Relationship
    public void addRelationship(String sourceClassName, String destinationClassName, RelationshipType type) {
        if (umlEditor.addRelationship(sourceClassName, destinationClassName, type)) {
            outputArea.append("Relationship from \"" + sourceClassName + "\" to \"" + destinationClassName + "\" added successfully.\n");
            updateDrawingPanel();
        } else {
            outputArea.append("Could not add relationship. Check class names.\n");
        }
    }

    // Delete Relationship
    public void deleteRelationship(String sourceClassName, String destinationClassName, RelationshipType type) {
        if (umlEditor.deleteRelationship(sourceClassName, destinationClassName, type)) {
            outputArea.append("Relationship from \"" + sourceClassName + "\" to \"" + destinationClassName + "\" deleted successfully.\n");
            updateDrawingPanel();
        } else {
            outputArea.append("Relationship not found.\n");
        }
    }

    // Change Relationship Type
    public void changeRelationshipType(String sourceClassName, String destinationClassName, RelationshipType oldType, RelationshipType newType) {
        if (umlEditor.changeRelationshipType(sourceClassName, destinationClassName, oldType, newType)) {
            outputArea.append("Changed relationship type from \"" + oldType + "\" to \"" + newType + "\" between \"" + sourceClassName + "\" and \"" + destinationClassName + "\".\n");
            updateDrawingPanel();
        } else {
            outputArea.append("Could not change relationship type. Check class names and types.\n");
        }
    }

    // Add Method
    public void addMethod(String className, String methodName, String parameters) {
        if (umlEditor.addMethod(className, methodName, parameters)) {
            outputArea.append("Method \"" + methodName + "\" added to class \"" + className + "\" successfully.\n");
        } else {
            outputArea.append("Class \"" + className + "\" not found.\n");
        }
        updateDrawingPanel();
    }

    // Delete Method
    public void deleteMethod(String className, String methodName) {
        if (umlEditor.deleteMethod(className, methodName)) {
            outputArea.append("Method \"" + methodName + "\" deleted from class \"" + className + "\" successfully.\n");
        } else {
            outputArea.append("Class \"" + className + "\" or method \"" + methodName + "\" not found.\n");
        }
        updateDrawingPanel();
    }

    // Helper method to update the drawing panel
    private void updateDrawingPanel() {
        drawingPanel.repaint(); // Redraw the panel to reflect changes
    }
}
