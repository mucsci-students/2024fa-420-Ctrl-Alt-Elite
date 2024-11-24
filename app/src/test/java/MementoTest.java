import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Model.Memento;
import Model.UmlEditorModel;

public class MementoTest {
    
    Memento memento = new Memento();
    
    /**
     * Test saving the current state of the model
     */
    @Test
    @DisplayName ("saveState: Save the current state of the model")
    public void testSaveState() {
        UmlEditorModel model = UmlEditorModel.getInstance();
        memento.saveState(model);
    }

    /**
     * Test undoing the last action of the user
     */
    @Test
    @DisplayName ("undoState: Undo the last action")
    public void testUndoState() {
        memento.undoState();
    }

    /**
     * Test redoing the last action of the user
     */
    @Test
    @DisplayName ("redoState: Redo the last action")
    public void testRedoState() {
        memento.redoState();
    }

}
