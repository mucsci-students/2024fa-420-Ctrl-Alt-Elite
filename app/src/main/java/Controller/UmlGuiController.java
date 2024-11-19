package Controller;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlEditorModel;
import Model.UmlRelationship;

public class UmlGuiController extends JFrame {
    private UmlEditorModel umlEditorModel;
    private UmlEditor umlEditor;
    private HashMap<String, Point> classPositions;
    private DrawingPanel drawingPanel;
    private JTextArea outputArea;

    // Declare menu items as instance variables
    private JMenuItem deleteClassItem;
    private JMenuItem renameClassItem;
    private JMenuItem addFieldItem;
    private JMenuItem deleteFieldItem;
    private JMenuItem renameFieldItem;
    private JMenuItem addMethodItem;
    private JMenuItem deleteMethodItem;
    private JMenuItem renameMethodItem;
    private JMenuItem changeParametersItem;
    private JMenuItem deleteParameterItem;
    private JMenuItem addRelationshipItem;
    private JMenuItem deleteRelationshipItem;
    private JMenuItem changeRelationshipItem;
    private JMenuItem changeFieldTypeItem;
    private JMenuItem changeReturnTypeItem;

    public UmlGuiController() {
        umlEditorModel = new UmlEditorModel();
        umlEditor = new UmlEditor(umlEditorModel);
        classPositions = new HashMap<>(); // Initialize class positions
        setTitle("UML Editor");
        setSize(800, 400); // Increased width to accommodate drawing panel
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new DrawingPanel(); // Create the drawing panel
        add(drawingPanel, BorderLayout.CENTER); // Add it to the center

        // Initialize the output area and scroll pane
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the "File" menu and initialize menu items
        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "Save UML File", e -> showSaveFilePanel());
        addMenuItem(fileMenu, "Load UML File", e -> showLoadFilePanel());
        // Add the "Export as Image" menu item
        addMenuItem(fileMenu, "Export as Image", e -> exportAsImage());
        menuBar.add(fileMenu);

        // Create the "Class" menu and initialize menu items
        JMenu classMenu = new JMenu("Class");
        addMenuItem(classMenu, "Add Class", e -> showAddClassPanel());
        deleteClassItem = addMenuItem(classMenu, "Delete Class", e -> showDeleteClassPanel());
        renameClassItem = addMenuItem(classMenu, "Rename Class", e -> showRenameClassPanel());
        menuBar.add(classMenu);

        // Create the "Field" menu and initialize menu items
        JMenu fieldMenu = new JMenu("Field");
        addFieldItem = addMenuItem(fieldMenu, "Add Field", e -> showAddFieldPanel());
        deleteFieldItem = addMenuItem(fieldMenu, "Delete Field", e -> showDeleteFieldPanel());
        renameFieldItem = addMenuItem(fieldMenu, "Rename Field", e -> showRenameFieldPanel());
        changeFieldTypeItem = addMenuItem(fieldMenu, "Change Field Type", e -> showChangeFieldTypePanel());
        menuBar.add(fieldMenu);

        // Create the "Method" menu and initialize menu items
        JMenu methodMenu = new JMenu("Method");
        addMethodItem = addMenuItem(methodMenu, "Add Method", e -> showAddMethodPanel());
        deleteMethodItem = addMenuItem(methodMenu, "Delete Method", e -> showDeleteMethodPanel());
        renameMethodItem = addMenuItem(methodMenu, "Rename Method", e -> showRenameMethodPanel());
        changeReturnTypeItem = addMenuItem(methodMenu, "Change Return Type", e -> showChangeReturnTypePanel());
        changeParametersItem = addMenuItem(methodMenu, "Change Parameters", e -> showChangeParameterPanel());
        deleteParameterItem = addMenuItem(methodMenu, "Delete Parameter", e -> showDeleteParameterPanel());
        menuBar.add(methodMenu);

        // Create the "Relationship" menu and initialize menu items
        JMenu relationshipMenu = new JMenu("Relationship");
        addRelationshipItem = addMenuItem(relationshipMenu, "Add Relationship", e -> showAddRelationshipPanel());
        deleteRelationshipItem = addMenuItem(relationshipMenu, "Delete Relationship",
                e -> showDeleteRelationshipPanel());
        changeRelationshipItem = addMenuItem(relationshipMenu, "Change Relationship",
                e -> showChangeRelationshipTypePanel());
        menuBar.add(relationshipMenu);

        // Set the menu bar
        setJMenuBar(menuBar);

        // Initially update button states
        updateButtonStates();
    }

    private void exportAsImage() {
        // Create a file chooser for saving the file
        JFileChooser fileChooser = new JFileChooser();

        // Set the default file name and extension
        fileChooser.setSelectedFile(new File("UML_Editor_exported_image.png"));

        // Set the file filter to allow only PNG images (can change this if you want
        // other formats)
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image", "png"));

        // Open the file chooser dialog
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure the file has the correct extension if not provided
            if (!fileToSave.getName().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }

            // Call the ImageExporter to save the image at the chosen location
            ImageExporter.exportPanelAsImage(drawingPanel, fileToSave.getAbsolutePath());
        }
    }

    // Helper method to create menu items and add to the menu
    private JMenuItem addMenuItem(JMenu menu, String title, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(action);
        menu.add(menuItem);
        return menuItem;
    }

    private void updateButtonStates() {
        // Check if there are any classes
        boolean hasClasses = !umlEditorModel.getClasses().isEmpty();
        // Check if there are fields in the first class
        boolean hasFields = hasClasses && !umlEditorModel.getClasses().values().iterator().next().getFields().isEmpty();
        // Check if there are methods in the first class
        boolean hasMethods = hasClasses
                && !umlEditorModel.getClasses().values().iterator().next().getMethods().isEmpty();
        // Check if there are any relationships
        boolean hasRelationships = !umlEditorModel.getRelationships().isEmpty();

        // Enable/disable relevant items
        deleteClassItem.setEnabled(hasClasses);
        renameClassItem.setEnabled(hasClasses);

        // Field menu items
        addFieldItem.setEnabled(hasClasses); // Enable "Add Field" if there's at least one class
        deleteFieldItem.setEnabled(hasFields); // Enable "Delete Field" if there are fields in the class
        renameFieldItem.setEnabled(hasFields); // Enable "Rename Field" if there are fields in the class
        changeFieldTypeItem.setEnabled(hasFields);

        // Method menu items
        addMethodItem.setEnabled(hasClasses); // Enable "Add Method" if there's at least one class
        deleteMethodItem.setEnabled(hasMethods); // Enable "Delete Method" if there are methods in the class
        renameMethodItem.setEnabled(hasMethods); // Enable "Rename Method" if there are methods in the class
        changeReturnTypeItem.setEnabled(hasMethods);

        // Relationship menu items
        addRelationshipItem.setEnabled(hasClasses); // Enable "Add Relationship" if there's at least one class
        deleteRelationshipItem.setEnabled(hasRelationships); // Enable "Delete Relationship" if there are relationships

        // Other related items
        changeRelationshipItem.setEnabled(hasRelationships); // Enable "Change Relationship" if there are relationships
        changeParametersItem.setEnabled(hasMethods); // Enable "Change Parameters" if there are methods in the class
        deleteParameterItem.setEnabled(hasMethods); // Enable "Delete Parameter" if there are methods in the class
    }

    // Add Class Panel
    private void showAddClassPanel() {
        JDialog dialog = new JDialog(this, "Add Class", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(new BoxLayout(addClassPanel, BoxLayout.Y_AXIS));
        addClassPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        JTextField classNameField = new JTextField(15); // Adjust width
        addClassPanel.add(new JLabel("Class Name:"));
        addClassPanel.add(Box.createVerticalStrut(5)); // Add space between label and text field
        addClassPanel.add(classNameField);
        addClassPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();

            // Generate random X and Y positions
            Random random = new Random();
            int xPosition = random.nextInt(500); // Change range as needed
            int yPosition = random.nextInt(500); // Change range as needed

            if (umlEditorModel.addClass(className, new Point(xPosition, yPosition))) {
                outputArea.append(
                        "Class '" + className + "' added at position (" + xPosition + ", " + yPosition + ").\n");
                addClassRectangle(className, xPosition, yPosition); // Draw rectangle for the new class
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after adding
            } else {
                outputArea.append("Failed to add class '" + className + "'.\n");
            }
            dialog.dispose(); // Close the dialog after submission
        });

        addClassPanel.add(submitButton);
        dialog.getContentPane().add(addClassPanel);
        dialog.pack();
        dialog.setSize(300, 150); // Adjust size to accommodate the class name field
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Delete Class Panel
    private void showDeleteClassPanel() {
        JDialog dialog = new JDialog(this, "Delete Class", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteClassPanel = new JPanel();
        deleteClassPanel.setLayout(new BoxLayout(deleteClassPanel, BoxLayout.Y_AXIS));
        deleteClassPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();

        // Create a combo box for selecting the class to delete
        JComboBox<String> classComboBox = new JComboBox<>(classNames);
        deleteClassPanel.add(new JLabel("Select Class to Delete:"));
        deleteClassPanel.add(classComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classComboBox.getSelectedItem(); // Get selected class name
            if (umlEditorModel.deleteClass(className)) {
                outputArea.append("Class '" + className + "' deleted.\n");
                removeClassRectangle(className);
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after deletion
            } else {
                outputArea.append("Failed to delete class '" + className + "'.\n");
            }
            dialog.dispose();
        });

        deleteClassPanel.add(submitButton);
        dialog.getContentPane().add(deleteClassPanel);
        dialog.pack();
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Rename Class Panel
    private void showRenameClassPanel() {
        JDialog dialog = new JDialog(this, "Rename Class", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel renameClassPanel = new JPanel();
        renameClassPanel.setLayout(new BoxLayout(renameClassPanel, BoxLayout.Y_AXIS));
        renameClassPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();

        // Create a combo box for selecting the old class name
        JComboBox<String> oldClassComboBox = new JComboBox<>(classNames);
        JTextField newClassNameField = new JTextField(15);

        renameClassPanel.add(new JLabel("Old Class Name:"));
        renameClassPanel.add(oldClassComboBox);
        renameClassPanel.add(new JLabel("New Class Name:"));
        renameClassPanel.add(newClassNameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String oldName = (String) oldClassComboBox.getSelectedItem(); // Get selected old class name
            String newName = newClassNameField.getText();
            if (umlEditorModel.renameClass(oldName, newName)) {
                outputArea.append("Class '" + oldName + "' renamed to '" + newName + "'.\n");
                renameClassRectangle(oldName, newName);
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after renaming
            } else {
                outputArea.append("Failed to rename class from '" + oldName + "' to '" + newName + "'.\n");
            }
            dialog.dispose();
        });

        renameClassPanel.add(submitButton);
        dialog.getContentPane().add(renameClassPanel);
        dialog.pack();
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Add Field Panel
    private void showAddFieldPanel() {
        JDialog dialog = new JDialog(this, "Add Field", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel addFieldPanel = new JPanel();
        addFieldPanel.setLayout(new BoxLayout(addFieldPanel, BoxLayout.Y_AXIS));
        addFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames(); // Assuming getClassNames() returns String[]

        // Combo box for selecting the class name
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField fieldNameField = new JTextField(15); // Adjust width

        // Combo box or text field for field type
        JTextField fieldTypeField = new JTextField(15); // Adjust width

        addFieldPanel.add(new JLabel("Class Name:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(classNameComboBox);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space between fields

        addFieldPanel.add(new JLabel("Field Type:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(fieldTypeField);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space between fields

        addFieldPanel.add(new JLabel("Field Name:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(fieldNameField);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem(); // Get selected class name
            String fieldType = fieldTypeField.getText().trim();
            String fieldName = fieldNameField.getText().trim();

            if (fieldName.isEmpty() || fieldType.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Field name and type cannot be empty.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!umlEditor.addField(className, fieldType, fieldName)) { // Single call to addField
                JOptionPane.showMessageDialog(dialog,
                        "Field '" + fieldName + "' already exists in class '" + className + "'.",
                        "Duplicate Field",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Success path
            outputArea.append(
                    "Field '" + fieldName + "' of type '" + fieldType + "' added to class '" + className + "'.\n");
            drawingPanel.revalidate();
            drawingPanel.repaint();
            updateButtonStates(); // Update button states after adding the field

            fieldTypeField.setText(""); // Clear the field type
            fieldNameField.setText(""); // Clear the field name
            dialog.dispose();
        });

        addFieldPanel.add(submitButton);
        dialog.getContentPane().add(addFieldPanel);
        dialog.pack();
        dialog.setSize(300, 250); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Delete Field Panel
    private void showDeleteFieldPanel() {
        JDialog dialog = new JDialog(this, "Delete Field", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteFieldPanel = new JPanel();
        deleteFieldPanel.setLayout(new BoxLayout(deleteFieldPanel, BoxLayout.Y_AXIS));
        deleteFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo box for class names
        JComboBox<String> classNameComboBox = new JComboBox<>();
        for (String className : umlEditorModel.getClassNames()) {
            classNameComboBox.addItem(className);
        }

        // Combo box for field names
        JComboBox<String> fieldNameComboBox = new JComboBox<>();
        // Populate field combo box based on selected class
        classNameComboBox.addActionListener(e -> {
            fieldNameComboBox.removeAllItems(); // Clear previous fields
            String selectedClass = (String) classNameComboBox.getSelectedItem();
            if (selectedClass != null) {
                for (String fieldName : umlEditor.getFields(selectedClass)) {
                    fieldNameComboBox.addItem(fieldName);
                }
            }
        });

        deleteFieldPanel.add(new JLabel("Class Name:"));
        deleteFieldPanel.add(Box.createVerticalStrut(5));
        deleteFieldPanel.add(classNameComboBox);
        deleteFieldPanel.add(Box.createVerticalStrut(10)); // Space between fields
        deleteFieldPanel.add(new JLabel("Field Name:"));
        deleteFieldPanel.add(Box.createVerticalStrut(5));
        deleteFieldPanel.add(fieldNameComboBox);
        deleteFieldPanel.add(Box.createVerticalStrut(10)); // Space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String fieldName = (String) fieldNameComboBox.getSelectedItem();
            if (umlEditor.deleteField(className, fieldName)) {
                outputArea.append("Field '" + fieldName + "' deleted from class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete field '" + fieldName + "' from class '" + className + "'.\n");
            }
            dialog.dispose();
        });

        deleteFieldPanel.add(submitButton);
        dialog.getContentPane().add(deleteFieldPanel);
        dialog.pack();
        dialog.setSize(300, 200); // Set preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Rename Field Panel
    private void showRenameFieldPanel() {
        JDialog dialog = new JDialog(this, "Rename Field", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel renameFieldPanel = new JPanel();
        renameFieldPanel.setLayout(new BoxLayout(renameFieldPanel, BoxLayout.Y_AXIS));
        renameFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo box for class names
        JComboBox<String> classNameComboBox = new JComboBox<>();
        for (String className : umlEditorModel.getClassNames()) {
            classNameComboBox.addItem(className);
        }

        // Combo box for old field names
        JComboBox<String> oldFieldNameComboBox = new JComboBox<>();
        // Populate field combo box based on selected class
        classNameComboBox.addActionListener(e -> {
            oldFieldNameComboBox.removeAllItems(); // Clear previous fields
            String selectedClass = (String) classNameComboBox.getSelectedItem();
            if (selectedClass != null) {
                for (String fieldName : umlEditor.getFields(selectedClass)) {
                    oldFieldNameComboBox.addItem(fieldName);
                }
            }
        });

        JTextField newFieldNameField = new JTextField(15); // Text field for the new field name

        renameFieldPanel.add(new JLabel("Class Name:"));
        renameFieldPanel.add(Box.createVerticalStrut(5));
        renameFieldPanel.add(classNameComboBox);
        renameFieldPanel.add(Box.createVerticalStrut(10));
        renameFieldPanel.add(new JLabel("Old Field Name:"));
        renameFieldPanel.add(Box.createVerticalStrut(5));
        renameFieldPanel.add(oldFieldNameComboBox);
        renameFieldPanel.add(Box.createVerticalStrut(10));
        renameFieldPanel.add(new JLabel("New Field Name:"));
        renameFieldPanel.add(Box.createVerticalStrut(5));
        renameFieldPanel.add(newFieldNameField);
        renameFieldPanel.add(Box.createVerticalStrut(10));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String oldFieldName = (String) oldFieldNameComboBox.getSelectedItem();
            String newFieldName = newFieldNameField.getText();

            if (umlEditor.renameField(className, oldFieldName, newFieldName)) {
                outputArea.append("Field '" + oldFieldName + "' renamed to '" + newFieldName + "' in class '"
                        + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to rename field '" + oldFieldName + "' to '" + newFieldName + "' in class '"
                        + className + "'.\n");
            }
            dialog.dispose();
        });

        renameFieldPanel.add(submitButton);
        dialog.getContentPane().add(renameFieldPanel);
        dialog.pack();
        dialog.setSize(350, 250); // Set preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Update Field Panel (only updates the type, not the name)
    private void showChangeFieldTypePanel() {
        JDialog dialog = new JDialog(this, "Update Field Type", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel updateFieldPanel = new JPanel();
        updateFieldPanel.setLayout(new BoxLayout(updateFieldPanel, BoxLayout.Y_AXIS));
        updateFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo box for class names
        JComboBox<String> classNameComboBox = new JComboBox<>();
        for (String className : umlEditorModel.getClassNames()) {
            classNameComboBox.addItem(className);
        }

        // Combo box for field names (old field)
        JComboBox<String> oldFieldNameComboBox = new JComboBox<>();
        // Populate field combo box based on selected class
        classNameComboBox.addActionListener(e -> {
            oldFieldNameComboBox.removeAllItems(); // Clear previous fields
            String selectedClass = (String) classNameComboBox.getSelectedItem();
            if (selectedClass != null) {
                for (String fieldName : umlEditor.getFields(selectedClass)) {
                    oldFieldNameComboBox.addItem(fieldName);
                }
            }
        });

        // Text field for updating field type
        JTextField newFieldTypeField = new JTextField(15); // Text field for the new field type

        updateFieldPanel.add(new JLabel("Class Name:"));
        updateFieldPanel.add(Box.createVerticalStrut(5));
        updateFieldPanel.add(classNameComboBox);
        updateFieldPanel.add(Box.createVerticalStrut(10)); // Space between fields

        updateFieldPanel.add(new JLabel("Field Name:"));
        updateFieldPanel.add(Box.createVerticalStrut(5));
        updateFieldPanel.add(oldFieldNameComboBox);
        updateFieldPanel.add(Box.createVerticalStrut(10)); // Space between fields

        updateFieldPanel.add(new JLabel("New Field Type:"));
        updateFieldPanel.add(Box.createVerticalStrut(5));
        updateFieldPanel.add(newFieldTypeField);
        updateFieldPanel.add(Box.createVerticalStrut(10)); // Space before button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String oldFieldName = (String) oldFieldNameComboBox.getSelectedItem();
            String newFieldType = newFieldTypeField.getText().trim();

            if (newFieldType.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Field type cannot be empty.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!umlEditor.updateFieldType(className, oldFieldName, newFieldType)) {
                JOptionPane.showMessageDialog(dialog,
                        "Failed to update field type for '" + oldFieldName + "' in class '" + className + "'.",
                        "Update Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Success path
            outputArea.append("Field type for '" + oldFieldName + "' updated to '" + newFieldType + "' in class '"
                    + className + "'.\n");
            drawingPanel.revalidate();
            drawingPanel.repaint();
            updateButtonStates(); // Update button states after updating the field

            newFieldTypeField.setText(""); // Clear the field type
            dialog.dispose();
        });

        updateFieldPanel.add(submitButton);
        dialog.getContentPane().add(updateFieldPanel);
        dialog.pack();
        dialog.setSize(350, 250); // Set preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Add Method Panel
    private void showAddMethodPanel() {
        JDialog dialog = new JDialog(this, "Add Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel addMethodPanel = new JPanel();
        addMethodPanel.setLayout(new BoxLayout(addMethodPanel, BoxLayout.Y_AXIS));
        addMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();

        // Create a combo box for selecting the class name
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField methodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width
        JTextField returnTypeField = new JTextField(15); // New field for return type

        addMethodPanel.add(new JLabel("Class Name:"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(classNameComboBox);
        addMethodPanel.add(Box.createVerticalStrut(10));
        addMethodPanel.add(new JLabel("Method Name:"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(methodNameField);
        addMethodPanel.add(Box.createVerticalStrut(10));
        addMethodPanel.add(new JLabel("Parameter List (format: type1 name1, type2 name2):"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(parameterListField);
        addMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the return type field
        addMethodPanel.add(new JLabel("Return Type:")); // Label for return type
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(returnTypeField); // Add the return type input field
        addMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String methodName = methodNameField.getText();
            List<String[]> paraList = parseParameterList(parameterListField.getText());
            String returnType = returnTypeField.getText(); // Get return type from the input field

            // Check if method addition was successful
            if (umlEditor.addMethod(className, methodName, paraList, returnType)) {
                outputArea.append("Method '" + methodName + "' added to class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after adding the method
                dialog.dispose(); // Close dialog only if successful
            } else {
                // Display an error message in the output area or GUI
                JOptionPane.showMessageDialog(dialog,
                        "Method '" + methodName + "' already exists in class '" + className + "'.",
                        "Duplicate Method",
                        JOptionPane.ERROR_MESSAGE);
            }

            // Clear input fields and close dialog after submission
            methodNameField.setText("");
            parameterListField.setText("");
            returnTypeField.setText(""); // Clear the return type field
            dialog.dispose();
        });

        addMethodPanel.add(submitButton);

        dialog.add(addMethodPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }

    // Delete Method Panel
    private void showDeleteMethodPanel() {
        JDialog dialog = new JDialog(this, "Delete Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteMethodPanel = new JPanel();
        deleteMethodPanel.setLayout(new BoxLayout(deleteMethodPanel, BoxLayout.Y_AXIS));
        deleteMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();

        // Create a combo box for selecting the class name
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField methodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width

        deleteMethodPanel.add(new JLabel("Class Name:"));
        deleteMethodPanel.add(Box.createVerticalStrut(5));
        deleteMethodPanel.add(classNameComboBox);
        deleteMethodPanel.add(Box.createVerticalStrut(10));
        deleteMethodPanel.add(new JLabel("Method Name:"));
        deleteMethodPanel.add(Box.createVerticalStrut(5));
        deleteMethodPanel.add(methodNameField);
        deleteMethodPanel.add(Box.createVerticalStrut(10));
        deleteMethodPanel.add(new JLabel("Parameter List (format: type1 name1, type2 name2):"));
        deleteMethodPanel.add(Box.createVerticalStrut(5));
        deleteMethodPanel.add(parameterListField);
        deleteMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String methodName = methodNameField.getText();
            List<String[]> paraList = parseParameterList(parameterListField.getText());

            // Update this line to reflect the correct method signature
            if (umlEditor.deleteMethod(className, methodName, paraList, "returnType")) { // Provide the correct return
                                                                                         // type
                outputArea.append("Method '" + methodName + "' deleted from class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete method '" + methodName + "' from class '" + className + "'.\n");
            }

            // Clear input fields and close dialog after submission
            methodNameField.setText("");
            parameterListField.setText("");
            dialog.dispose();
        });

        deleteMethodPanel.add(submitButton);

        dialog.add(deleteMethodPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }

    // Rename Method Panel
    private void showRenameMethodPanel() {
        JDialog dialog = new JDialog(this, "Rename Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel renameMethodPanel = new JPanel();
        renameMethodPanel.setLayout(new BoxLayout(renameMethodPanel, BoxLayout.Y_AXIS));
        renameMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();

        // Create a combo box for selecting the class name
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField oldMethodNameField = new JTextField(15); // Adjust width
        JTextField newMethodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width
        JTextField returnTypeField = new JTextField(15); // Adjust width

        renameMethodPanel.add(new JLabel("Class Name:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(classNameComboBox);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("Old Method Name:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(oldMethodNameField);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("New Method Name:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(newMethodNameField);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("Parameter List (format: type1 name1, type2 name2):"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(parameterListField);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("Return Type:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(returnTypeField);
        renameMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String oldMethodName = oldMethodNameField.getText();
            String newMethodName = newMethodNameField.getText();
            List<String[]> paraList = parseParameterList(parameterListField.getText());
            String returnType = returnTypeField.getText(); // Get return type from input field

            if (umlEditor.renameMethod(className, oldMethodName, paraList, returnType, newMethodName)) {
                outputArea.append("Method '" + oldMethodName + "' renamed to '" + newMethodName + "' in class '"
                        + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to rename method '" + oldMethodName + "' in class '" + className + "'.\n");
            }

            // Clear input fields and close dialog after submission
            oldMethodNameField.setText("");
            newMethodNameField.setText("");
            parameterListField.setText("");
            returnTypeField.setText("");
            dialog.dispose();
        });

        renameMethodPanel.add(submitButton);

        dialog.add(renameMethodPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }

    // Helper Function
    private List<String[]> parseParameterList(String input) {
        List<String[]> paraList = new ArrayList<>();
        if (input != null && !input.trim().isEmpty()) {
            String[] parameters = input.split(","); // Split the input by commas
            for (String parameter : parameters) {
                parameter = parameter.trim(); // Trim whitespace
                String[] parts = parameter.split(" "); // Split by space to separate type and name
                if (parts.length == 2) {
                    paraList.add(parts); // Add to the list
                } else {
                    // Handle invalid parameter format
                    System.out.println("Invalid parameter format: " + parameter);
                }
            }
        }
        return paraList; // Return the populated parameter map
    }

    private void showChangeReturnTypePanel() {
        JDialog dialog = new JDialog(this, "Change Return Type", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel changeReturnTypePanel = new JPanel();
        changeReturnTypePanel.setLayout(new BoxLayout(changeReturnTypePanel, BoxLayout.Y_AXIS));
        changeReturnTypePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);

        JComboBox<String> methodNameComboBox = new JComboBox<>();
        JComboBox<String> oldReturnTypeField = new JComboBox<>();
        JTextField newReturnTypeField = new JTextField(10);

        // Populate methods and return types when class is selected
        classNameComboBox.addActionListener(e -> {
            String selectedClass = (String) classNameComboBox.getSelectedItem();
            if (selectedClass != null) {
                String[] methodNames = umlEditorModel.getMethodNames(selectedClass);
                methodNameComboBox.setModel(new DefaultComboBoxModel<>(methodNames));

                if (methodNames.length > 0) {
                    String selectedMethod = methodNames[0];
                    oldReturnTypeField.setModel(new DefaultComboBoxModel<>(new String[] {
                            umlEditorModel.getMethodReturnType(selectedClass, selectedMethod)
                    }));
                }
            }
        });

        // Update old return type when method changes
        methodNameComboBox.addActionListener(e -> {
            String selectedClass = (String) classNameComboBox.getSelectedItem();
            String selectedMethod = (String) methodNameComboBox.getSelectedItem();
            if (selectedClass != null && selectedMethod != null) {
                oldReturnTypeField.setModel(new DefaultComboBoxModel<>(new String[] {
                        umlEditorModel.getMethodReturnType(selectedClass, selectedMethod)
                }));
            }
        });

        changeReturnTypePanel.add(new JLabel("Class Name:"));
        changeReturnTypePanel.add(classNameComboBox);
        changeReturnTypePanel.add(Box.createVerticalStrut(10));

        changeReturnTypePanel.add(new JLabel("Method Name:"));
        changeReturnTypePanel.add(methodNameComboBox);
        changeReturnTypePanel.add(Box.createVerticalStrut(10));

        changeReturnTypePanel.add(new JLabel("Old Return Type:"));
        changeReturnTypePanel.add(oldReturnTypeField);
        changeReturnTypePanel.add(Box.createVerticalStrut(10));

        changeReturnTypePanel.add(new JLabel("New Return Type:"));
        changeReturnTypePanel.add(newReturnTypeField);
        changeReturnTypePanel.add(Box.createVerticalStrut(10));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String methodName = (String) methodNameComboBox.getSelectedItem();
            String oldType = (String) oldReturnTypeField.getSelectedItem();
            String newType = newReturnTypeField.getText();

            if (className != null && methodName != null && oldType != null && !newType.isEmpty()) {
                List<String[]> parameters = umlEditorModel.getParameters(className, methodName);

                boolean success = umlEditor.changeReturnType(className, methodName, parameters, oldType, newType);
                if (success) {
                    outputArea.append("Return type of method '" + methodName + "' in class '" + className
                            + "' changed from '" + oldType + "' to '" + newType + "'.\n");
                    drawingPanel.revalidate();
                    drawingPanel.repaint();
                } else {
                    outputArea.append("Failed to change return type for method '" + methodName + "' in class '"
                            + className + "'.\n");
                }
            } else {
                outputArea.append("Please fill in all fields before submitting.\n");
            }
        });

        changeReturnTypePanel.add(submitButton);

        dialog.add(changeReturnTypePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Delete Parameter Panel
    private void showDeleteParameterPanel() {
        JDialog dialog = new JDialog(this, "Delete Parameter", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteParameterPanel = new JPanel();
        deleteParameterPanel.setLayout(new BoxLayout(deleteParameterPanel, BoxLayout.Y_AXIS));
        deleteParameterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames();

        // Create a combo box for selecting the class name
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField methodNameField = new JTextField(10);
        JTextField parameterNameField = new JTextField(10);

        deleteParameterPanel.add(new JLabel("Class Name:"));
        deleteParameterPanel.add(Box.createVerticalStrut(5));
        deleteParameterPanel.add(classNameComboBox);
        deleteParameterPanel.add(Box.createVerticalStrut(10));
        deleteParameterPanel.add(new JLabel("Method Name:"));
        deleteParameterPanel.add(Box.createVerticalStrut(5)); // Add space
        deleteParameterPanel.add(methodNameField);
        deleteParameterPanel.add(Box.createVerticalStrut(10)); // Add space
        deleteParameterPanel.add(new JLabel("Parameter Name:"));
        deleteParameterPanel.add(Box.createVerticalStrut(5)); // Add space
        deleteParameterPanel.add(parameterNameField);
        deleteParameterPanel.add(Box.createVerticalStrut(10)); // Add space

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String methodName = methodNameField.getText();
            String parameterName = parameterNameField.getText();
            // if (umlEditor.removeParameter(className, methodName, parameterName)) {
            // outputArea.append("Parameter '" + parameterName + "' deleted from method '" +
            // methodName
            // + "' in class '" + className + "'.\n");
            // drawingPanel.revalidate();
            // drawingPanel.repaint();
            // } else {
            // outputArea.append("Failed to delete parameter '" + parameterName + "' from
            // method '" + methodName
            // + "' in class '" + className + "'.\n");
            // }

            methodNameField.setText("");
            parameterNameField.setText("");
        });
        deleteParameterPanel.add(Box.createVerticalStrut(10)); // Add space before the button
        deleteParameterPanel.add(submitButton);

        dialog.add(deleteParameterPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }

    private void showChangeParameterPanel() {
        JDialog dialog = new JDialog(this, "Change Parameters", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Class name selection
        String[] classNames = umlEditorModel.getClassNames();
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField methodNameField = new JTextField(10);
        JTextField returnTypeField = new JTextField(10);
        JTextField oldParametersField = new JTextField(20);
        JTextField newParametersField = new JTextField(20);

        panel.add(new JLabel("Class Name:"));
        panel.add(classNameComboBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Method Name:"));
        panel.add(methodNameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Return Type:"));
        panel.add(returnTypeField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Old Parameters (type name, type name):"));
        panel.add(oldParametersField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("New Parameters (type name, type name):"));
        panel.add(newParametersField);
        panel.add(Box.createVerticalStrut(10));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String methodName = methodNameField.getText();
            String returnType = returnTypeField.getText();
            String oldParamsText = oldParametersField.getText();
            String newParamsText = newParametersField.getText();

            List<String[]> oldParameters = parseParameterString(oldParamsText);
            List<String[]> newParameters = parseParameterString(newParamsText);

            boolean result = umlEditor.changeParameters(className, methodName, oldParameters, returnType,
                    newParameters);

            if (result) {
                outputArea.append("Parameters successfully changed for method '" + methodName + "' in class '"
                        + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();

            } else {
                outputArea.append(
                        "Failed to change parameters for method '" + methodName + "' in class '" + className + "'.\n");
            }

            methodNameField.setText("");
            returnTypeField.setText("");
            oldParametersField.setText("");
            newParametersField.setText("");

            dialog.dispose();
        });
        panel.add(submitButton);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private List<String[]> parseParameterString(String parameterString) {
        List<String[]> parameters = new ArrayList<>();
        String[] paramPairs = parameterString.split(",\\s*");

        for (String pair : paramPairs) {
            String[] parts = pair.trim().split("\\s+");
            if (parts.length == 2) {
                parameters.add(parts);
            }
        }
        return parameters;
    }

    // Add Relationship Panel
    private void showAddRelationshipPanel() {
        JPanel addRelationshipPanel = new JPanel();
        addRelationshipPanel.setLayout(new BoxLayout(addRelationshipPanel, BoxLayout.Y_AXIS));
        addRelationshipPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set padding

        // Sample class names for the combo boxes (replace these with actual class
        // names)
        String[] availableClasses = umlEditorModel.getClassNames();

        // Create combo boxes for source and destination classes
        JComboBox<String> sourceComboBox = new JComboBox<>(availableClasses);
        JComboBox<String> destinationComboBox = new JComboBox<>(availableClasses);

        // Create a combo box for RelationshipType
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" };
        JComboBox<String> typeComboBox = new JComboBox<>(relationshipTypes);

        // Set alignment for each component to the left
        JLabel sourceLabel = new JLabel("Source Class:");
        sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel destinationLabel = new JLabel("Destination Class:");
        destinationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        destinationComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Relationship Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the panel
        addRelationshipPanel.add(sourceLabel);
        addRelationshipPanel.add(sourceComboBox);
        addRelationshipPanel.add(destinationLabel);
        addRelationshipPanel.add(destinationComboBox);
        addRelationshipPanel.add(typeLabel);
        addRelationshipPanel.add(typeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align button to the left
        submitButton.addActionListener(e -> {
            String source = (String) sourceComboBox.getSelectedItem(); // Get selected source class
            String destination = (String) destinationComboBox.getSelectedItem(); // Get selected destination class
            String typeString = (String) typeComboBox.getSelectedItem();

            // Convert selected item to enum
            RelationshipType type;
            try {
                type = RelationshipType.valueOf(typeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid relationship type: '" + typeString + "'. Please use a valid type.\n");
                return;
            }

            if (umlEditorModel.addRelationship(source, destination, type)) {
                outputArea.append(
                        "Added relationship from '" + source + "' to '" + destination + "' of type '" + type + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after adding the relationship
            } else {
                outputArea.append("Failed to add relationship from '" + source + "' to '" + destination + "'.\n");
            }

            // Clear the selections
            sourceComboBox.setSelectedIndex(0);
            destinationComboBox.setSelectedIndex(0);
            typeComboBox.setSelectedIndex(0);
        });

        // Add the submit button to the panel
        addRelationshipPanel.add(submitButton);

        // Show the panel in a dialog without OK button
        JDialog dialog = new JDialog(this, "Add Relationship", true);
        dialog.getContentPane().add(addRelationshipPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Delete Relationship Panel
    private void showDeleteRelationshipPanel() {
        JPanel deleteRelationshipPanel = new JPanel();
        deleteRelationshipPanel.setLayout(new BoxLayout(deleteRelationshipPanel, BoxLayout.Y_AXIS));
        deleteRelationshipPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set padding

        // Assuming umlEditorModel.getClassNames() returns an array of class names
        String[] classNames = umlEditorModel.getClassNames();
        JComboBox<String> sourceComboBox = new JComboBox<>(classNames);
        JComboBox<String> destinationComboBox = new JComboBox<>(classNames);

        // Create a combo box for RelationshipType
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" };
        JComboBox<String> typeComboBox = new JComboBox<>(relationshipTypes);

        // Create and align labels and fields
        JLabel sourceLabel = new JLabel("Source Class:");
        sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel destinationLabel = new JLabel("Destination Class:");
        destinationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        destinationComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Relationship Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the panel
        deleteRelationshipPanel.add(sourceLabel);
        deleteRelationshipPanel.add(sourceComboBox);
        deleteRelationshipPanel.add(destinationLabel);
        deleteRelationshipPanel.add(destinationComboBox);
        deleteRelationshipPanel.add(typeLabel);
        deleteRelationshipPanel.add(typeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(e -> {
            String source = (String) sourceComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();
            String typeString = (String) typeComboBox.getSelectedItem();

            RelationshipType type;
            try {
                type = RelationshipType.valueOf(typeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid relationship type: '" + typeString + "'. Please use a valid type.\n");
                return;
            }

            if (umlEditorModel.deleteRelationship(source, destination, type)) {
                outputArea.append("Deleted relationship of type '" + type + "' between '" + source + "' and '"
                        + destination + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea
                        .append("Failed to delete relationship between '" + source + "' and '" + destination + "'.\n");
            }
        });

        deleteRelationshipPanel.add(submitButton);

        // Show the panel in a dialog
        JDialog dialog = new JDialog(this, "Delete Relationship", true);
        dialog.getContentPane().add(deleteRelationshipPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Change Relationship Panel
    private void showChangeRelationshipTypePanel() {
        JPanel changeRelationshipTypePanel = new JPanel();
        changeRelationshipTypePanel.setLayout(new BoxLayout(changeRelationshipTypePanel, BoxLayout.Y_AXIS));
        changeRelationshipTypePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Assuming umlEditorModel.getClassNames() returns an array of class names
        String[] classNames = umlEditorModel.getClassNames();
        JComboBox<String> sourceComboBox = new JComboBox<>(classNames);
        JComboBox<String> destinationComboBox = new JComboBox<>(classNames);

        // Combo boxes for current and new relationship types
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" };
        JComboBox<String> currentTypeComboBox = new JComboBox<>(relationshipTypes);
        JComboBox<String> newTypeComboBox = new JComboBox<>(relationshipTypes);

        JLabel sourceLabel = new JLabel("Source Class:");
        sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel destinationLabel = new JLabel("Destination Class:");
        destinationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        destinationComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel currentTypeLabel = new JLabel("Current Relationship Type:");
        currentTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        currentTypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel newTypeLabel = new JLabel("New Relationship Type:");
        newTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        newTypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        changeRelationshipTypePanel.add(sourceLabel);
        changeRelationshipTypePanel.add(sourceComboBox);
        changeRelationshipTypePanel.add(destinationLabel);
        changeRelationshipTypePanel.add(destinationComboBox);
        changeRelationshipTypePanel.add(currentTypeLabel);
        changeRelationshipTypePanel.add(currentTypeComboBox);
        changeRelationshipTypePanel.add(newTypeLabel);
        changeRelationshipTypePanel.add(newTypeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitButton.addActionListener(e -> {
            String source = (String) sourceComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();
            String currentTypeString = (String) currentTypeComboBox.getSelectedItem();
            String newTypeString = (String) newTypeComboBox.getSelectedItem();

            RelationshipType currentType;
            RelationshipType newType;

            try {
                currentType = RelationshipType.valueOf(currentTypeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid current relationship type: '" + currentTypeString + "'.\n");
                return;
            }

            try {
                newType = RelationshipType.valueOf(newTypeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid new relationship type: '" + newTypeString + "'.\n");
                return;
            }

            if (umlEditorModel.changeRelationshipType(source, destination, currentType, newType)) {
                outputArea.append("Changed relationship type from '" + currentType + "' to '" + newType + "' between '"
                        + source + "' and '" + destination + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append(
                        "Failed to change relationship type between '" + source + "' and '" + destination + "'.\n");
            }

        });

        changeRelationshipTypePanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Change Relationship Type", true);
        dialog.getContentPane().add(changeRelationshipTypePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Save File Panel
    private void showSaveFilePanel() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                JsonUtils.save(umlEditorModel, file.getAbsolutePath()); // Ensure save method includes positions
                outputArea.append("File saved successfully to " + file.getAbsolutePath() + ".\n");
            } catch (IOException ex) {
                outputArea.append("Failed to save file: " + ex.getMessage() + "\n");
            }
        }
    }

    // Load File Panel
    private void showLoadFilePanel() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                umlEditorModel = JsonUtils.load(file.getAbsolutePath()); // Load the model

                // Clear previous class positions
                classPositions.clear();

                // Populate classPositions based on the loaded UML editor
                for (Map.Entry<String, UmlClass> entry : umlEditorModel.getClasses().entrySet()) {
                    String className = entry.getKey();

                    // Load the position from the UmlClass if it exists
                    Point position = entry.getValue().getPosition();
                    if (position != null) {
                        classPositions.put(className, position);
                    } else {
                        // If no position is set, you can set a default one
                        classPositions.put(className, new Point(100, 100)); // Adjust as needed
                    }
                }

                // Repaint the panel to show loaded classes and relationships
                drawingPanel.repaint();

                // Update button states after loading the project
                updateButtonStates(); // Ensure buttons are updated based on loaded data

                outputArea.append("File loaded successfully from " + file.getAbsolutePath() + ".\n");
            } catch (IOException ex) {
                outputArea.append("Failed to load file: " + ex.getMessage() + "\n");
            }
        }
    }

    private void addClassRectangle(String className, int x, int y) {
        // Store the specified position in the classPositions map
        classPositions.put(className, new Point(x, y));
        drawingPanel.repaint(); // Repaint the drawing panel to show the new rectangle
    }

    private void removeClassRectangle(String className) {
        classPositions.remove(className); // Remove the class position
        drawingPanel.repaint(); // Repaint to reflect changes
    }

    private void renameClassRectangle(String oldName, String newName) {
        Point position = classPositions.remove(oldName); // Remove old entry
        if (position != null) {
            classPositions.put(newName, position); // Add new entry with same position
            drawingPanel.repaint(); // Repaint to show changes
        }
    }

    private class DrawingPanel extends JPanel {
        private Point dragStartPoint;
        private String selectedClassName;

        public DrawingPanel() {
            // Add mouse listeners for dragging
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Check if a class is clicked
                    for (Map.Entry<String, Point> entry : classPositions.entrySet()) {
                        String className = entry.getKey();
                        Point position = entry.getValue();

                        int boxWidth = getBoxWidth(className); // Now works without Graphics

                        Rectangle rect = new Rectangle(position.x, position.y, boxWidth,
                                50 + (umlEditorModel.getClass(className).getFields().size() * 15));
                        if (rect.contains(e.getPoint())) {
                            selectedClassName = className; // Set the selected class
                            dragStartPoint = e.getPoint(); // Store the initial drag point
                            break;
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // Clear selection on mouse release
                    selectedClassName = null;
                    dragStartPoint = null;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // If a class is selected, update its position
                    if (selectedClassName != null && dragStartPoint != null) {
                        Point currentPoint = e.getPoint();
                        Point oldPosition = classPositions.get(selectedClassName);
                        if (oldPosition != null) {
                            // Calculate the new position
                            int newX = oldPosition.x + (currentPoint.x - dragStartPoint.x);
                            int newY = oldPosition.y + (currentPoint.y - dragStartPoint.y);
                            classPositions.put(selectedClassName, new Point(newX, newY)); // Update the position

                            // Update the position in the model as well
                            umlEditorModel.updateClassPosition(selectedClassName, new Point(newX, newY)); // Update
                                                                                                          // model

                            dragStartPoint = currentPoint; // Update the drag start point for smooth dragging
                            repaint(); // Repaint the panel to show the updated position
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Create a map to keep track of how many relationships exist between class
            // pairs
            Map<String, Integer> relationshipCount = new HashMap<>();

            // Draw relationships
            for (UmlRelationship relationship : umlEditorModel.getRelationships()) {
                Point sourcePosition = classPositions.get(relationship.getSource());
                Point destinationPosition = classPositions.get(relationship.getDestination());

                if (sourcePosition != null && destinationPosition != null) {
                    Graphics2D g2d = (Graphics2D) g;

                    // Generate a unique key for the relationship between the source and destination
                    // classes
                    String relationshipKey = relationship.getSource() + "-" + relationship.getDestination();

                    // Increment the count for this specific relationship key
                    int lineOffset = relationshipCount.getOrDefault(relationshipKey, 0);
                    relationshipCount.put(relationshipKey, lineOffset + 1);

                    // Calculate vertical offset for the line to avoid overlap
                    int offset = lineOffset * 5; // Adjust this value for spacing

                    // Define an offset for the arrowhead
                    int arrowheadOffset1 = 10; // Increase this value to move the arrowhead further out
                    int arrowheadOffset2 = 20; // Increase this value to move the arrowhead further out

                    // Determine color and line style based on relationship type
                    switch (relationship.getType()) {
                        case INHERITANCE:
                            g2d.setColor(Color.BLUE); // Color for inheritance
                            // Check for self-relationship
                            if (relationship.getSource().equals(relationship.getDestination())) {
                                // Draw the arrowhead shape at the top middle of the box
                                drawArrow(g, sourcePosition.x + 50,
                                        sourcePosition.y + offset, // Arrowhead at the top of the source box
                                        "inheritance"); // Pass "inheritance" for arrowhead style
                            } else {
                                g2d.drawLine(sourcePosition.x + 50,
                                        // Start from the bottom middle
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset,
                                        destinationPosition.x + 50,
                                        // Line ends below the box
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 10);
                                drawArrow(g, destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination())
                                                + arrowheadOffset1, // Offset for arrowhead
                                        "inheritance"); // Pass "inheritance" for arrowhead style
                            }
                            break;

                        case REALIZATION:
                            g2d.setColor(Color.GREEN); // Color for realization
                            // Check for self-relationship
                            if (relationship.getSource().equals(relationship.getDestination())) {
                                // Draw the arrowhead shape at the top middle of the box
                                drawArrow(g, sourcePosition.x + 50,
                                        sourcePosition.y + offset, // Arrowhead at the top of the source box
                                        "realization"); // Pass "realization" for arrowhead style
                            } else {
                                // Draw dotted line for realization
                                float[] dottedPattern = { 2f, 5f }; // Dotted line pattern
                                g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f,
                                        dottedPattern, 0f));
                                g2d.drawLine(sourcePosition.x + 50,
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start
                                                                                                            // from the
                                                                                                            // bottom
                                                                                                            // middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 10); // Line
                                                                                                                   // ends
                                                                                                                   // below
                                                                                                                   // the
                                                                                                                   // box
                                g2d.setStroke(new BasicStroke()); // Reset stroke to solid
                                drawArrow(g, destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination())
                                                + arrowheadOffset1, // Offset for arrowhead
                                        "realization"); // Pass "realization" for arrowhead style
                            }
                            break;

                        case AGGREGATION:
                            g2d.setColor(Color.ORANGE); // Color for aggregation
                            // Check for self-relationship
                            if (relationship.getSource().equals(relationship.getDestination())) {
                                // Draw the arrowhead shape at the top middle of the box
                                drawArrow(g, sourcePosition.x + 50,
                                        sourcePosition.y + offset, // Arrowhead at the top of the source box
                                        "aggregation"); // Pass "aggregation" for arrowhead style
                            } else {
                                // Draw dashed line for aggregation
                                float[] dashPattern = { 10f, 5f }; // Dashed line pattern
                                g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f,
                                        dashPattern, 0f));
                                g2d.drawLine(sourcePosition.x + 50,
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start
                                                                                                            // from the
                                                                                                            // bottom
                                                                                                            // middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 20); // Line
                                                                                                                   // ends
                                                                                                                   // below
                                                                                                                   // the
                                                                                                                   // box
                                g2d.setStroke(new BasicStroke()); // Reset stroke to solid
                                drawArrow(g, destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination())
                                                + arrowheadOffset2, // Offset for arrowhead
                                        "aggregation"); // Pass "aggregation" for arrowhead style
                            }
                            break;

                        case COMPOSITION:
                            g2d.setColor(Color.RED); // Color for composition
                            // Check for self-relationship
                            if (relationship.getSource().equals(relationship.getDestination())) {
                                // Draw the arrowhead shape at the top middle of the box
                                drawArrow(g, sourcePosition.x + 50,
                                        sourcePosition.y + offset, // Arrowhead at the top of the source box
                                        "composition"); // Pass "composition" for arrowhead style
                            } else {
                                g2d.drawLine(sourcePosition.x + 50,
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start
                                                                                                            // from the
                                                                                                            // bottom
                                                                                                            // middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 20); // Line
                                                                                                                   // ends
                                                                                                                   // below
                                                                                                                   // the
                                                                                                                   // box
                                drawArrow(g, destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination())
                                                + arrowheadOffset2, // Offset for arrowhead
                                        "composition"); // Pass "composition" for arrowhead style
                            }
                            break;
                    }

                    // Reset the color to black for class boxes
                    g2d.setColor(Color.BLACK);
                }
            }

            // Draw rectangles for each class
            for (Map.Entry<String, Point> entry : classPositions.entrySet()) {
                String className = entry.getKey();
                Point position = entry.getValue();
                UmlClass umlClass = umlEditorModel.getClass(className);
                int boxHeight = getBoxHeight(className); // Get dynamic box height

                // Calculate the maximum width needed for the box
                int maxWidth = 100; // Start with a default width for the box
                FontMetrics metrics = g.getFontMetrics();

                // Get the width of the class name
                maxWidth = Math.max(maxWidth, metrics.stringWidth(className) + 20); // 20 for padding

                // Calculate the width for attributes and construct their representation
                if (umlClass != null) {
                    for (Map.Entry<String, String> entre : umlClass.getFields().entrySet()) {
                        String attribute = entre.getValue() + " " + entre.getKey(); // "type name"
                        maxWidth = Math.max(maxWidth, metrics.stringWidth(attribute) + 20);
                    }

                    // Calculate the width for methods
                    for (String methodSignature : umlClass.getMethods()) {
                        maxWidth = Math.max(maxWidth, metrics.stringWidth(methodSignature) + 20);
                    }
                }

                // Draw the rectangle for the class
                g.drawRect(position.x, position.y, maxWidth, boxHeight);

                // Draw the class name
                g.drawString(className, position.x + 10, position.y + 20);

                // Draw a line to separate class name and attributes
                g.drawLine(position.x, position.y + 30, position.x + maxWidth, position.y + 30);

                // Draw attributes
                if (umlClass != null) {
                    int attributeY = position.y + 45; // Start position for attributes
                    for (Map.Entry<String, String> entre : umlClass.getFields().entrySet()) {
                        String attribute = entre.getValue() + " " + entre.getKey(); // "type name"
                        g.drawString(attribute, position.x + 10, attributeY);
                        attributeY += 15; // Move down for the next attribute
                    }

                    // Draw methods
                    int methodY = attributeY + 10; // Start position for methods (after attributes)
                    for (String methodSignature : umlClass.getMethods()) {
                        g.drawString(methodSignature, position.x + 10, methodY);
                        methodY += 15; // Move down for the next method
                    }
                }
            }
        }

        // Method to draw an arrowhead for different relationship types
        private void drawArrow(Graphics g, int x, int y, String relationshipType) {
            int arrowSize = 10; // Size of the arrowhead

            switch (relationshipType.toLowerCase()) {
                case "inheritance":
                    // Open triangle arrowhead for inheritance
                    int[] inhXPoints = { x, x - arrowSize, x + arrowSize };
                    int[] inhYPoints = { y, y - arrowSize, y - arrowSize };
                    g.drawPolygon(inhXPoints, inhYPoints, 3); // Draw only the outline
                    break;

                case "realization":
                    // Open triangle arrowhead for realization, similar to inheritance
                    int[] realXPoints = { x, x - arrowSize, x + arrowSize };
                    int[] realYPoints = { y, y - arrowSize, y - arrowSize };
                    g.drawPolygon(realXPoints, realYPoints, 3); // Draw only the outline
                    break;

                case "aggregation":
                    // Hollow diamond for aggregation
                    int[] aggXPoints = { x, x - arrowSize, x, x + arrowSize };
                    int[] aggYPoints = { y, y - arrowSize, y - (2 * arrowSize), y - arrowSize };
                    g.drawPolygon(aggXPoints, aggYPoints, 4); // Draw diamond shape
                    break;

                case "composition":
                    // Filled diamond for composition
                    int[] compXPoints = { x, x - arrowSize, x, x + arrowSize };
                    int[] compYPoints = { y, y - arrowSize, y - (2 * arrowSize), y - arrowSize };
                    g.fillPolygon(compXPoints, compYPoints, 4); // Fill diamond shape
                    break;

                default:
                    // Simple filled triangle arrowhead for unrecognized relationships (association)
                    int[] defaultXPoints = { x, x - arrowSize, x + arrowSize };
                    int[] defaultYPoints = { y, y - arrowSize, y - arrowSize };
                    g.fillPolygon(defaultXPoints, defaultYPoints, 3);
                    break;
            }
        }

        // Helper method to get the box height based on the class name
        private int getBoxHeight(String className) {
            UmlClass umlClass = umlEditorModel.getClass(className);
            int attributeCount = (umlClass != null) ? umlClass.getFields().size() : 0;
            int methodCount = (umlClass != null) ? umlClass.getMethods().size() : 0; // Get the number of methods
            return 50 + (attributeCount * 15) + (methodCount * 15); // Base height + dynamic attribute and method height
        }

        // Get width of class box
        public int getBoxWidth(String className) {
            // Assuming you have access to the FontMetrics from paintComponent
            FontMetrics metrics = getFontMetrics(getFont()); // or get a specific font you are using
            int width = metrics.stringWidth(className) + 100; // 20 for padding

            // Assuming you have UmlClass object
            UmlClass umlClass = umlEditorModel.getClass(className);
            if (umlClass != null) {
                for (Map.Entry<String, String> entry : umlClass.getFields().entrySet()) {
                    String attribute = entry.getValue() + " " + entry.getKey(); // "type name"
                    width = Math.max(width, metrics.stringWidth(attribute) + 20);
                }

                // Methods width
                for (String methodSignature : umlClass.getMethods()) {
                    width = Math.max(width, metrics.stringWidth(methodSignature) + 20);
                }
            }

            return width;
        }
    }
}