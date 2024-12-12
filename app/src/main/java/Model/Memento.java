package Model;
import java.util.ArrayList;
import java.util.List;

public class Memento 
{
    private List<UmlEditorModel> undoStack;
    private List<UmlEditorModel> redoStack;

    public Memento() {
        undoStack = new ArrayList<>();
        redoStack = new ArrayList<>();
    }

    public void saveState(UmlEditorModel state) {
        UmlEditorModel stateCopy = new UmlEditorModel(state);
        undoStack.add(stateCopy);
        redoStack.clear(); // Clear redo stack when new state is saved
    }

    public UmlEditorModel undoState() {
        if (undoStack.isEmpty()) {
            return null;
        }
        UmlEditorModel state = undoStack.remove(undoStack.size() - 1);
        redoStack.add(new UmlEditorModel(state));
        return new UmlEditorModel(state);
    }

    public UmlEditorModel redo() {
        if (redoStack.isEmpty()) {
            return null;
        }
        UmlEditorModel state = redoStack.remove(redoStack.size() - 1);
        undoStack.add(new UmlEditorModel(state));
        return new UmlEditorModel(state);
    }

    public void clearRedo() {
        redoStack.clear();
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}