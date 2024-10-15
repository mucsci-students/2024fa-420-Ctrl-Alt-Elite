import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class newGUI extends JFrame {
    private UmlEditor umlEditor;
    private JTextArea outputArea;
    private DrawingPanel drawingPanel; // Panel for drawing
    private Map<String, Point> classPositions;
    private JPanel collapsiblePanel; // Panel for the output area
    private JButton toggleButton; // Button to collapse/expand
    private GuiController controller; // Controller for handling events

    public GUI() {
        umlEditor = new UmlEditor();
        classPositions = new HashMap<>(); // Initialize class positions
        setTitle("UML Editor");
        setSize(800, 600); // Adjusted height to accommodate all components
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel(); // Create the drawing panel
        add(drawingPanel, BorderLayout.CENTER); // Add it to the center

        // Initialize the output area and scroll pane
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Create a collapsible panel for the output area
        collapsiblePanel = new JPanel();
        collapsiblePanel.setLayout(new BorderLayout());

        // Create the toggle button
        toggleButton = new JButton("Collapse Output");
        toggleButton.addActionListener(new ActionListener() {
            private boolean isCollapsed = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isCollapsed) {
                    // Expand the output area
                    collapsiblePanel.add(scrollPane, BorderLayout.CENTER);
                    toggleButton.setText("Collapse Output");
                    isCollapsed = false;
                } else {
                    // Collapse the output area
                    collapsiblePanel.remove(scrollPane);
                    toggleButton.setText("Expand Output");
                    isCollapsed = true;
                }
                collapsiblePanel.revalidate(); // Refresh the panel to show changes
                collapsiblePanel.repaint(); // Repaint the panel
            }
        });

        // Add the button and scroll pane to the collapsible panel
        collapsiblePanel.add(toggleButton, BorderLayout.NORTH);
        collapsiblePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the collapsible panel to the frame
        add(collapsiblePanel, BorderLayout.WEST);

        // Create a controller for handling GUI events
        controller = new GuiController(umlEditor, outputArea, drawingPanel, classPositions);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Add Class
        JTextField classNameField = new JTextField();
        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(e -> controller.addClass(classNameField.getText()));
        panel.add(new JLabel("Class Name:"));
        panel.add(classNameField);
        panel.add(addClassButton);

        // Delete Class
        JTextField deleteClassField = new JTextField();
        JButton deleteClassButton = new JButton("Delete Class");
        deleteClassButton.addActionListener(e -> controller.deleteClass(deleteClassField.getText()));
        panel.add(new JLabel("Class Name to Delete:"));
        panel.add(deleteClassField);
        panel.add(deleteClassButton);

        // Rename Class
        JTextField oldClassNameField = new JTextField();
        JTextField newClassNameField = new JTextField();
        JButton renameClassButton = new JButton("Rename Class");
        renameClassButton.addActionListener(e -> controller.renameClass(oldClassNameField.getText(), newClassNameField.getText()));
        panel.add(new JLabel("Old Class Name:"));
        panel.add(oldClassNameField);
        panel.add(new JLabel("New Class Name:"));
        panel.add(newClassNameField);
        panel.add(renameClassButton);

        // Add Field
        JTextField addFieldClassField = new JTextField();
        JTextField fieldField = new JTextField();
        JButton addFieldButton = new JButton("Add Field");
        addFieldButton.addActionListener(e -> controller.addField(addFieldClassField.getText(), fieldField.getText()));
        panel.add(new JLabel("Class Name for Field:"));
        panel.add(addFieldClassField);
        panel.add(new JLabel("Field Name:"));
        panel.add(fieldField);
        panel.add(addFieldButton);

        // Delete Field
        JTextField deleteFieldClassField = new JTextField();
        JTextField deleteFieldField = new JTextField();
        JButton deleteFieldButton = new JButton("Delete Field");
        deleteFieldButton.addActionListener(e -> controller.deleteField(deleteFieldClassField.getText(), deleteFieldField.getText()));
        panel.add(new JLabel("Class Name for Field to Delete:"));
        panel.add(deleteFieldClassField);
        panel.add(new JLabel("Field Name to Delete:"));
        panel.add(deleteFieldField);
        panel.add(deleteFieldButton);

        // Add Relationship
        JTextField sourceClassField = new JTextField();
        JTextField destinationClassField = new JTextField();
        JComboBox<RelationshipType> relationshipTypeComboBox = new JComboBox<>(RelationshipType.values());
        JButton addRelationshipButton = new JButton("Add Relationship");
        addRelationshipButton.addActionListener(e -> controller.addRelationship(sourceClassField.getText(), destinationClassField.getText(), (RelationshipType) relationshipTypeComboBox.getSelectedItem()));
        panel.add(new JLabel("Source Class Name:"));
        panel.add(sourceClassField);
        panel.add(new JLabel("Destination Class Name:"));
        panel.add(destinationClassField);
        panel.add(new JLabel("Relationship Type:"));
        panel.add(relationshipTypeComboBox);
        panel.add(addRelationshipButton);

        // Delete Relationship
        JTextField deleteSourceClassField = new JTextField();
        JTextField deleteDestinationClassField = new JTextField();
        JComboBox<RelationshipType> deleteRelationshipTypeComboBox = new JComboBox<>(RelationshipType.values());
        JButton deleteRelationshipButton = new JButton("Delete Relationship");
        deleteRelationshipButton.addActionListener(e -> controller.deleteRelationship(deleteSourceClassField.getText(), deleteDestinationClassField.getText(), (RelationshipType) deleteRelationshipTypeComboBox.getSelectedItem()));
        panel.add(new JLabel("Source Class Name to Delete:"));
        panel.add(deleteSourceClassField);
        panel.add(new JLabel("Destination Class Name to Delete:"));
        panel.add(deleteDestinationClassField);
        panel.add(new JLabel("Relationship Type:"));
        panel.add(deleteRelationshipTypeComboBox);
        panel.add(deleteRelationshipButton);

        // Change Relationship Type
        JTextField changeSourceClassField = new JTextField();
        JTextField changeDestinationClassField = new JTextField();
        JComboBox<RelationshipType> changeOldRelationshipTypeComboBox = new JComboBox<>(RelationshipType.values());
        JComboBox<RelationshipType> changeNewRelationshipTypeComboBox = new JComboBox<>(RelationshipType.values());
        JButton changeRelationshipButton = new JButton("Change Relationship Type");
        changeRelationshipButton.addActionListener(e -> controller.changeRelationshipType(changeSourceClassField.getText(), changeDestinationClassField.getText(), (RelationshipType) changeOldRelationshipTypeComboBox.getSelectedItem(), (RelationshipType) changeNewRelationshipTypeComboBox.getSelectedItem()));
        panel.add(new JLabel("Source Class Name:"));
        panel.add(changeSourceClassField);
        panel.add(new JLabel("Destination Class Name:"));
        panel.add(changeDestinationClassField);
        panel.add(new JLabel("Old Relationship Type:"));
        panel.add(changeOldRelationshipTypeComboBox);
        panel.add(new JLabel("New Relationship Type:"));
        panel.add(changeNewRelationshipTypeComboBox);
        panel.add(changeRelationshipButton);

        // Add Method
        JTextField addMethodClassField = new JTextField();
        JTextField methodField = new JTextField();
        JTextField parametersField = new JTextField(); // New field for parameters
        JButton addMethodButton = new JButton("Add Method");
        addMethodButton.addActionListener(e -> controller.addMethod(addMethodClassField.getText(), methodField.getText(), parametersField.getText()));
        panel.add(new JLabel("Class Name for Method:"));
        panel.add(addMethodClassField);
        panel.add(new JLabel("Method Name:"));
        panel.add(methodField);
        panel.add(new JLabel("Parameters (comma-separated):"));
        panel.add(parametersField);
        panel.add(addMethodButton);

        // Delete Method
        JTextField deleteMethodClassField = new JTextField();
        JTextField deleteMethodField = new JTextField();
        JButton deleteMethodButton = new JButton("Delete Method");
        deleteMethodButton.addActionListener(e -> controller.deleteMethod(deleteMethodClassField.getText(), deleteMethodField.getText()));
        panel.add(new JLabel("Class Name for Method to Delete:"));
        panel.add(deleteMethodClassField);
        panel.add(new JLabel("Method Name to Delete:"));
        panel.add(deleteMethodField);
        panel.add(deleteMethodButton);

        // List Classes and Relationships
        JButton listClassesButton = new JButton("List Classes");
        listClassesButton.addActionListener(e -> {
            outputArea.setText("");
            for (UmlClass umlClass : umlEditor.getClasses().values()) {
                outputArea.append(umlClass + "\n");
            }
        });

        JButton listRelationshipsButton = new JButton("List Relationships");
        listRelationshipsButton.addActionListener(e -> {
            outputArea.setText("");
            for (UmlRelationship relationship : umlEditor.getRelationships()) {
                outputArea.append(relationship + "\n");
            }
        });

        panel.add(listClassesButton);
        panel.add(listRelationshipsButton);

        // Save and Load
        JButton saveButton = new JButton("Save Project");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    JsonUtils.saveToFile(umlEditor, selectedFile.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton loadButton = new JButton("Load Project");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    umlEditor = JsonUtils.loadFromFile(selectedFile.getAbsolutePath());
                    outputArea.setText("Project loaded successfully.");
                    drawingPanel.repaint(); // Repaint the drawing panel to show updated UML
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(saveButton);
        panel.add(loadButton);

        add(panel, BorderLayout.EAST); // Add control panel to the east side

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
