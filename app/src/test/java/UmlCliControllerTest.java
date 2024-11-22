import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Controller.UmlCliController;
import Controller.UmlEditor;
import Model.RelationshipType;
import Model.UmlEditorModel;
import View.CLI;

public class UmlCliControllerTest {
    /** The model for this test class */
    private UmlEditorModel model;

    /** The view for this test class */
    private CLI view;

    /** The UmlEditor for this test class */
    private UmlEditor umlEditor;

    /** The main controller for this class */
    private UmlCliController controller;
    
    /**
     * Creates an instance of a UmlCliController object to be used in tests.
     */
    @BeforeEach
    public void setUp() {
        model = UmlEditorModel.getInstance();
        umlEditor = new UmlEditor(model);
        view = new CLI();

        controller = new UmlCliController(model, umlEditor, view);
    }
    
/*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test that the display of classes and their contents are correct.
     */
    @Test
    @DisplayName ("ListClasses: List all classes")
    public void testListClasses() {
    	System.out.println("ListClasses: List all classes");

        System.out.println("Expected Output: ");
    	System.out.println("Class: ClassA");
        System.out.println("Methods: []");
        System.out.println("Class: ClassB");
        System.out.println("Methods: []");
    	
    	umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        
        System.out.println("Output: ");
        controller.handleListClasses();
        System.out.println();
    }
    
    /**
     * Test listing all classes when there are no classes.
     */
    @Test
    @DisplayName ("ListClasses: Display no classes when there are none")
    public void testListClassesNoClasses() {
    	System.out.println("ListClasses: Display no classes when there are none");

        System.out.println("Expected Output: (no output)");
        
    	System.out.println("Output: ");
        controller.handleListClasses();
        System.out.println();
    }

 /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * Test listing relationships between classes.
     */
    @Test
    @DisplayName ("ListRelationships: List the relationships between classes")
    public void testListRelationships() {
    	System.out.println("ListRelationships: List the relationships between classes");
        
        System.out.println("Expected Output: Relationship from \'ClassA\' to \'ClassB\'");
    	
    	umlEditor.addClass("ClassA");
        umlEditor.addClass("ClassB");
        RelationshipType type = RelationshipType.AGGREGATION;
        umlEditor.addRelationship("ClassA", "ClassB", type);
        
        System.out.print("Output: ");
        controller.handleListRelationships(); 
        System.out.println();
    }
    
    /**
     * Test calling ListRelationships when there are no relationships.
     */
    @Test
    @DisplayName ("ListRelationships: List relationships when there are none")
    public void testListRelationshipsNotExist() {
        System.out.println("Expected Output: (no output)");
        
        System.out.print("Output: ");
        controller.handleListRelationships();
    	System.out.println();
    }
}
