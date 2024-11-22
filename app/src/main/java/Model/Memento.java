package Model;

import java.util.Stack;

public class Memento {

    private final Stack<UmlEditorModel> undoStack = new Stack<>();
    private final Stack<UmlEditorModel> redoStack = new Stack<>();

    // Save the current state of the model by cloning it
    public void saveState(UmlEditorModel model) {
        undoStack.push((UmlEditorModel) model.clone()); // Cast the clone to UmlEditorModel before pushing to the stack
        redoStack.clear(); // Clear the redo stack on a new action
    }

    // Undo the last action by popping from the undo stack and pushing to the redo stack
    public UmlEditorModel undoState() {
        if (undoStack.isEmpty()) {
            return null;
        }
        UmlEditorModel state = undoStack.pop();
        redoStack.push((UmlEditorModel) state.clone()); // Save for redo by cloning the state
        return state;
    }

    // Redo the last undone action by popping from the redo stack and pushing to the undo stack
    public UmlEditorModel redoState() {
        if (redoStack.isEmpty()) {
            return null;
        }
        UmlEditorModel state = redoStack.pop();
        undoStack.push((UmlEditorModel) state.clone()); // Save for undo by cloning the state
        return state;
    }
}
