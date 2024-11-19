package Model;

import java.util.ArrayDeque;
import java.util.Deque;

public class Memento {

    private final Deque<UmlEditorModel> undoStack = new ArrayDeque<>();
    private final Deque<UmlEditorModel> redoStack = new ArrayDeque<>();

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

    public UmlEditorModel peekUndo() {
        return undoStack.peek(); // Preview the next undo state
    }

    public UmlEditorModel peekRedo() {
        return redoStack.peek(); // Preview the next redo state
    }

    public boolean hasUndo() {
        return !undoStack.isEmpty();
    }

    public boolean hasRedo() {
        return !redoStack.isEmpty();
    }
}
