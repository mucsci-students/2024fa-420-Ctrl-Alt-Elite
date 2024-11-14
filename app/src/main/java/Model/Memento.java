
package Model;

import java.util.Stack;

public class Memento {

    private final Stack<UmlEditorModel> undoStack = new Stack<>();
    private final Stack<UmlEditorModel> redoStack = new Stack<>();

    public void saveState(UmlEditorModel model) {
        undoStack.push(new UmlEditorModel(model)); // Save a deep copy of the model
        redoStack.clear(); // Clear redo stack on a new action
    }

    public UmlEditorModel undoState() {
        if (undoStack.isEmpty()) {
            return null;
        }
        UmlEditorModel state = undoStack.pop();
        redoStack.push(new UmlEditorModel(state)); // Save for redo
        return state;
    }

    public UmlEditorModel redoState() {
        if (redoStack.isEmpty()) {
            return null;
        }
        UmlEditorModel state = redoStack.pop();
        undoStack.push(new UmlEditorModel(state)); // Save for undo
        return state;
    }
}
