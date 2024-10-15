import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Model.RelationshipType;
import Model.UmlEditorModel;

/**
 * A test class for UmlEditorModel.
 */
public class UmlEditorModelTest { 
    /** The model object to be tested */
    UmlEditorModel model;
    
    /**
     * Creates an instance of a UmlEditorModel object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
        model = new UmlEditorModel();
    }

/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the constructor initializes the object properly.
     */
    @Test
    @DisplayName ("Constructor: Construct a UmlEditorModel Object ")
    public void testUmlEditor() {
    	model.addClass("ClassA");
        model.addClass("ClassB");

        RelationshipType type = RelationshipType.AGGREGATION;
        model.addRelationship("ClassA", "ClassB", type);
        
        assertTrue (((model.getClass("ClassA")) != null), 
    		() -> "Could not construct the UmlEditor.");
    }


}
