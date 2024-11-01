package Controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlRelationship;
import Model.JsonUtils;
import Model.UmlEditorModel;

public class UmlGuiController extends JFrame {
    private UmlEditorModel umlEditorModel;
    private UmlEditor umlEditor;
    private HashMap<String, Point> classPositions;
    private DrawingPanel drawingPanel;
    private JTextArea outputArea;
    private JPanel collapsiblePanel;
    private JButton toggleButton;

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

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

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
        menuBar.add(fieldMenu);

        // Create the "Method" menu and initialize menu items
        JMenu methodMenu = new JMenu("Method");
        addMethodItem = addMenuItem(methodMenu, "Add Method", e -> showAddMethodPanel());
        deleteMethodItem = addMenuItem(methodMenu, "Delete Method", e -> showDeleteMethodPanel());
        renameMethodItem = addMenuItem(methodMenu, "Rename Method", e -> showRenameMethodPanel());
        changeParametersItem = addMenuItem(methodMenu, "Change Parameters", e -> showChangeParameterPanel());
        deleteParameterItem = addMenuItem(methodMenu, "Delete Parameter", e -> showDeleteParameterPanel());
        menuBar.add(methodMenu);

        // Create the "Relationship" menu and initialize menu items
        JMenu relationshipMenu = new JMenu("Relationship");
        addRelationshipItem = addMenuItem(relationshipMenu, "Add Relationship", e -> showAddRelationshipPanel());
        deleteRelationshipItem = addMenuItem(relationshipMenu, "Delete Relationship", e -> showDeleteRelationshipPanel());
        changeRelationshipItem = addMenuItem(relationshipMenu, "Change Relationship", e -> showChangeRelationshipTypePanel());
        menuBar.add(relationshipMenu);

        // Create the "Project" menu and initialize menu items
        JMenu projectMenu = new JMenu("Project");
        addMenuItem(projectMenu, "Save UML Project", e -> showSaveProjectPanel());
        addMenuItem(projectMenu, "Load UML Project", e -> showLoadProjectPanel());
        menuBar.add(projectMenu);

        // Create the "List" menu
        JMenu listMenu = new JMenu("List");
        addMenuItem(listMenu, "List Classes", e -> showListClassesPanel());
        addMenuItem(listMenu, "List Relationships", e -> showListRelationshipsPanel());
        menuBar.add(listMenu);


        // Set the menu bar
        setJMenuBar(menuBar);

        // Initially update button states
        updateButtonStates();
    }

    // Helper method to create menu items and add to the menu
    private JMenuItem addMenuItem(JMenu menu, String title, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(action);
        menu.add(menuItem);
        return menuItem;
    }

    private void updateButtonStates() {
        boolean hasClasses = !umlEditorModel.getClasses().isEmpty(); // Check if there are any classes
        boolean hasFields = hasClasses && !umlEditorModel.getClasses().values().iterator().next().getFields().isEmpty(); // Check if there are fields in the first class
        boolean hasMethods = hasClasses && !umlEditorModel.getClasses().values().iterator().next().getMethods().isEmpty(); // Check if there are methods in the first class
        boolean hasRelationships = !umlEditorModel.getRelationships().isEmpty(); // Check if there are any relationships
    
        // Enable/disable relevant items
        deleteClassItem.setEnabled(hasClasses);
        renameClassItem.setEnabled(hasClasses);
    
        // Field menu items
        addFieldItem.setEnabled(hasClasses); // Enable "Add Field" if there's at least one class
        deleteFieldItem.setEnabled(hasFields); // Enable "Delete Field" if there are fields in the class
        renameFieldItem.setEnabled(hasFields); // Enable "Rename Field" if there are fields in the class
    
        // Method menu items
        addMethodItem.setEnabled(hasClasses); // Enable "Add Method" if there's at least one class
        deleteMethodItem.setEnabled(hasMethods); // Enable "Delete Method" if there are methods in the class
        renameMethodItem.setEnabled(hasMethods); // Enable "Rename Method" if there are methods in the class
    
        // Relationship menu items
        addRelationshipItem.setEnabled(hasClasses); // Enable "Add Relationship" if there's at least one class
        deleteRelationshipItem.setEnabled(hasRelationships); // Enable "Delete Relationship" if there are relationships
    
        // Other related items
        changeRelationshipItem.setEnabled(hasRelationships); // Enable "Change Relationship" if there are relationships
        changeParametersItem.setEnabled(hasMethods); // Enable "Change Parameters" if there are methods in the class
        deleteParameterItem.setEnabled(hasMethods); // Enable "Delete Parameter" if there are methods in the class
    }
    
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
                outputArea.append("Class '" + className + "' added at position (" + xPosition + ", " + yPosition + ").\n");
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
    
        addFieldPanel.add(new JLabel("Class Name:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(classNameComboBox);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space between fields
        addFieldPanel.add(new JLabel("Field Name:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(fieldNameField);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space before the button
    
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem(); // Get selected class name
            String fieldName = fieldNameField.getText();
            
            // Assuming you have a method to add the field
            if (umlEditor.addField(className, fieldName)) {
                outputArea.append("Field '" + fieldName + "' added to class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after adding the field
            } else {
                outputArea.append("Failed to add field '" + fieldName + "' to class '" + className + "'.\n");
            }
    
            fieldNameField.setText(""); // Clear the field
            dialog.dispose();
        });
    
        addFieldPanel.add(submitButton);
        dialog.getContentPane().add(addFieldPanel);
        dialog.pack();
        dialog.setSize(300, 200); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
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
                outputArea.append("Field '" + oldFieldName + "' renamed to '" + newFieldName + "' in class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to rename field '" + oldFieldName + "' to '" + newFieldName + "' in class '" + className + "'.\n");
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
    
    private void showAddMethodPanel() {
        JDialog dialog = new JDialog(this, "Add Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    
        JPanel addMethodPanel = new JPanel();
        addMethodPanel.setLayout(new BoxLayout(addMethodPanel, BoxLayout.Y_AXIS));
        addMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
    
        // Get class names from the model
        String[] classNames = umlEditorModel.getClassNames(); 
    
        // Create a combo box for selecting the old class name
        JComboBox<String> classNameComboBox = new JComboBox<>(classNames);
        JTextField methodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width
    
        addMethodPanel.add(new JLabel("Class Name:"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(classNameComboBox);
        addMethodPanel.add(Box.createVerticalStrut(10));
        addMethodPanel.add(new JLabel("Method Name:"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(methodNameField);
        addMethodPanel.add(Box.createVerticalStrut(10));
        addMethodPanel.add(new JLabel("Parameter List (comma-separated):"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(parameterListField);
        addMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the button
    
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = (String) classNameComboBox.getSelectedItem();
            String methodName = methodNameField.getText();
            LinkedHashSet<String> paraList = parseParameterList(parameterListField.getText());
    
            if (umlEditor.addMethod(className, methodName, paraList)) {
                outputArea.append("Method '" + methodName + "' added to class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
                updateButtonStates(); // Update button states after adding the method
            } else {
                outputArea.append("Failed to add method '" + methodName + "' to class '" + className + "'.\n");
            }
    
            // Clear input fields and close dialog after submission
            methodNameField.setText("");
            parameterListField.setText("");
            dialog.dispose();
        });
    
        addMethodPanel.add(submitButton);
    
        dialog.add(addMethodPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }
    
    private void showDeleteMethodPanel() {
        JDialog dialog = new JDialog(this, "Delete Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteMethodPanel = new JPanel();
        deleteMethodPanel.setLayout(new BoxLayout(deleteMethodPanel, BoxLayout.Y_AXIS));
        deleteMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(15); // Adjust width
        JTextField methodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width

        deleteMethodPanel.add(new JLabel("Class Name:"));
        deleteMethodPanel.add(Box.createVerticalStrut(5));
        deleteMethodPanel.add(classNameField);
        deleteMethodPanel.add(Box.createVerticalStrut(10));
        deleteMethodPanel.add(new JLabel("Method Name:"));
        deleteMethodPanel.add(Box.createVerticalStrut(5));
        deleteMethodPanel.add(methodNameField);
        deleteMethodPanel.add(Box.createVerticalStrut(10));
        deleteMethodPanel.add(new JLabel("Parameter List (comma-separated):"));
        deleteMethodPanel.add(Box.createVerticalStrut(5));
        deleteMethodPanel.add(parameterListField);
        deleteMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            String methodName = methodNameField.getText();
            LinkedHashSet<String> paraList = parseParameterList(parameterListField.getText());
            if (umlEditor.deleteMethod(className, methodName, paraList)) {
                outputArea.append("Method '" + methodName + "' deleted from class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete method '" + methodName + "' from class '" + className + "'.\n");
            }
            classNameField.setText("");
            methodNameField.setText("");
            parameterListField.setText("");
            dialog.dispose(); // Close dialog after submission
        });
        deleteMethodPanel.add(submitButton);

        dialog.add(deleteMethodPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }
    
    private void showRenameMethodPanel() {
        JDialog dialog = new JDialog(this, "Rename Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel renameMethodPanel = new JPanel();
        renameMethodPanel.setLayout(new BoxLayout(renameMethodPanel, BoxLayout.Y_AXIS));
        renameMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(15); // Adjust width
        JTextField oldMethodNameField = new JTextField(15); // Adjust width
        JTextField newMethodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width

        renameMethodPanel.add(new JLabel("Class Name:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(classNameField);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("Old Method Name:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(oldMethodNameField);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("New Method Name:"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(newMethodNameField);
        renameMethodPanel.add(Box.createVerticalStrut(10));
        renameMethodPanel.add(new JLabel("Parameter List (comma-separated):"));
        renameMethodPanel.add(Box.createVerticalStrut(5));
        renameMethodPanel.add(parameterListField);
        renameMethodPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            String oldName = oldMethodNameField.getText();
            String newName = newMethodNameField.getText();
            LinkedHashSet<String> paraList = parseParameterList(parameterListField.getText());
            if (umlEditor.renameMethod(className, oldName, paraList, newName)) {
                outputArea.append(
                        "Method '" + oldName + "' renamed to '" + newName + "' in class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to rename method '" + oldName + "' to '" + newName + "' in class '"
                        + className + "'.\n");
            }
            classNameField.setText("");
            oldMethodNameField.setText("");
            newMethodNameField.setText("");
            parameterListField.setText("");
            dialog.dispose(); // Close dialog after submission
        });
        renameMethodPanel.add(submitButton);

        dialog.add(renameMethodPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }

    private LinkedHashSet<String> parseParameterList(String input) {
        LinkedHashSet<String> parameters = new LinkedHashSet<>();

        // Check if the input is empty
        if (input.trim().isEmpty()) {
            return parameters; // Return an empty set if no parameters are provided
        }

        // Split the input by commas and add non-empty trimmed values to the set
        for (String param : input.split(",")) {
            String trimmedParam = param.trim();
            if (!trimmedParam.isEmpty()) {
                parameters.add(trimmedParam);
            }
        }
        return parameters;
    }

    private void showDeleteParameterPanel() {
        JDialog dialog = new JDialog(this, "Delete Parameter", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteParameterPanel = new JPanel();
        deleteParameterPanel.setLayout(new BoxLayout(deleteParameterPanel, BoxLayout.Y_AXIS));
        deleteParameterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(10);
        JTextField methodNameField = new JTextField(10);
        JTextField parameterNameField = new JTextField(10);

        deleteParameterPanel.add(new JLabel("Class Name:"));
        deleteParameterPanel.add(Box.createVerticalStrut(5)); // Add space
        deleteParameterPanel.add(classNameField);
        deleteParameterPanel.add(Box.createVerticalStrut(10)); // Add space
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
            String className = classNameField.getText();
            String methodName = methodNameField.getText();
            String parameterName = parameterNameField.getText();
            if (umlEditor.removeParameter(className, methodName, parameterName)) {
                outputArea.append("Parameter '" + parameterName + "' deleted from method '" + methodName
                        + "' in class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete parameter '" + parameterName + "' from method '" + methodName
                        + "' in class '" + className + "'.\n");
            }
            classNameField.setText("");
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
        JDialog dialog = new JDialog(this, "Change Method Parameters", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel changeParameterPanel = new JPanel();
        changeParameterPanel.setLayout(new BoxLayout(changeParameterPanel, BoxLayout.Y_AXIS));
        changeParameterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField changeParameterClassField = new JTextField(10);
        JTextField changeParameterMethodField = new JTextField(10);
        JTextField changeParameterNewParamsField = new JTextField(10);

        changeParameterPanel.add(new JLabel("Class Name for Parameter Change:"));
        changeParameterPanel.add(Box.createVerticalStrut(5)); // Add space
        changeParameterPanel.add(changeParameterClassField);
        changeParameterPanel.add(Box.createVerticalStrut(10)); // Add space
        changeParameterPanel.add(new JLabel("Method Name:"));
        changeParameterPanel.add(Box.createVerticalStrut(5)); // Add space
        changeParameterPanel.add(changeParameterMethodField);
        changeParameterPanel.add(Box.createVerticalStrut(10)); // Add space
        changeParameterPanel.add(new JLabel("New Parameters (comma-separated):"));
        changeParameterPanel.add(Box.createVerticalStrut(5)); // Add space
        changeParameterPanel.add(changeParameterNewParamsField);
        changeParameterPanel.add(Box.createVerticalStrut(10)); // Add space

        JButton changeParameterButton = new JButton("Change Parameters");
        changeParameterButton.addActionListener(e -> {
            String className = changeParameterClassField.getText();
            String methodName = changeParameterMethodField.getText();
            String newParametersInput = changeParameterNewParamsField.getText();

            LinkedHashSet<String> newParametersSet = new LinkedHashSet<>();
            if (!newParametersInput.trim().isEmpty()) {
                Arrays.stream(newParametersInput.split(","))
                        .map(String::trim)
                        .forEach(newParametersSet::add);
            }

            if (umlEditor.changeParameters(className, methodName, newParametersSet)) {
                outputArea.append("Parameters of method '" + methodName + "' in class '" + className + "' changed to: "
                        + newParametersInput + ".\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append(
                        "Failed to change parameters of method '" + methodName + "' in class '" + className + "'.\n");
            }

            changeParameterClassField.setText("");
            changeParameterMethodField.setText("");
            changeParameterNewParamsField.setText("");
        });
        changeParameterPanel.add(Box.createVerticalStrut(10)); // Add space before the button
        changeParameterPanel.add(changeParameterButton);

        dialog.add(changeParameterPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center dialog
        dialog.setVisible(true);
    }

    private void showAddRelationshipPanel() {
        JPanel addRelationshipPanel = new JPanel();
        addRelationshipPanel.setLayout(new BoxLayout(addRelationshipPanel, BoxLayout.Y_AXIS));
        addRelationshipPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set padding
    
        // Sample class names for the combo boxes (replace these with actual class names)
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
                outputArea.append("Deleted relationship of type '" + type + "' between '" + source + "' and '" + destination + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete relationship between '" + source + "' and '" + destination + "'.\n");
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
                outputArea.append("Changed relationship type from '" + currentType + "' to '" + newType + "' between '" + source + "' and '" + destination + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to change relationship type between '" + source + "' and '" + destination + "'.\n");
            }
    
        });
    
        changeRelationshipTypePanel.add(submitButton);
    
        JDialog dialog = new JDialog(this, "Change Relationship Type", true);
        dialog.getContentPane().add(changeRelationshipTypePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showListClassesPanel() {
        // Clear the output area before displaying the classes
        outputArea.setText(""); // Assuming you have an outputArea to display the classes

        // Call the listClasses method and display the classes
        for (UmlClass umlClass : umlEditorModel.getClasses().values()) {
            outputArea.append(umlClass + "\n");
        }
    }

    private void showListRelationshipsPanel() {
        // Clear the output area before displaying the relationships
        outputArea.setText(""); // Assuming you have an outputArea to display the relationships

        // Call the listRelationships method and display the relationships
        for (UmlRelationship relationship : umlEditorModel.getRelationships()) {
            outputArea.append(relationship + "\n");
        }
    }

    private void showSaveProjectPanel() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                JsonUtils.save(umlEditorModel, file.getAbsolutePath()); // Ensure save method includes positions
                outputArea.append("Project saved successfully to " + file.getAbsolutePath() + ".\n");
            } catch (IOException ex) {
                outputArea.append("Failed to save project: " + ex.getMessage() + "\n");
            }
        }
    }
    
    private void showLoadProjectPanel() {
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
                    Point position = entry.getValue().getPosition(); // Assuming you have a getPosition() method in UmlClass
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
                
                outputArea.append("Project loaded successfully from " + file.getAbsolutePath() + ".\n");
            } catch (IOException ex) {
                outputArea.append("Failed to load project: " + ex.getMessage() + "\n");
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
                        Rectangle rect = new Rectangle(position.x, position.y, 100,
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
                            umlEditorModel.updateClassPosition(selectedClassName, new Point(newX, newY)); // Update model
        
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
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start from the bottom middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 10); // Line ends below the box
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
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start from the bottom middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 10); // Line ends below the box
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
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start from the bottom middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 20); // Line ends below the box
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
                                        sourcePosition.y + getBoxHeight(relationship.getSource()) + offset, // Start from the bottom middle
                                        destinationPosition.x + 50,
                                        destinationPosition.y + getBoxHeight(relationship.getDestination()) + 20); // Line ends below the box
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

                // Calculate the width for attributes
                if (umlClass != null) {
                    for (String attribute : umlClass.getFields()) {
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
                    for (String attribute : umlClass.getFields()) {
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
    }
}