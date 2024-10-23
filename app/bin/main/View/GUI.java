package View;

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

import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlRelationship;

public class GUI extends JFrame {
    private UmlEditor umlEditor;
    private HashMap<String, Point> classPositions;
    private DrawingPanel drawingPanel;
    private JTextArea outputArea;
    private JPanel collapsiblePanel;
    private JButton toggleButton;
    private JPanel cardPanel; // Panel for switching between text input panels

    public GUI() {
        umlEditor = new UmlEditor();
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

        // Create the card panel for buttons and text fields
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Add Class Button and Action
        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(e -> showAddClassPanel());
        cardPanel.add(addClassButton);

        // Delete Class Button and Action
        JButton deleteClassButton = new JButton("Delete Class");
        deleteClassButton.addActionListener(e -> showDeleteClassPanel());
        cardPanel.add(deleteClassButton);

        // Rename Class Button and Action
        JButton renameClassButton = new JButton("Rename Class");
        renameClassButton.addActionListener(e -> showRenameClassPanel());
        cardPanel.add(renameClassButton);

        // Add Field Button and Action
        JButton addFieldButton = new JButton("Add Field");
        addFieldButton.addActionListener(e -> showAddFieldPanel());
        cardPanel.add(addFieldButton);

        // Delete Field Button and Action
        JButton deleteFieldButton = new JButton("Delete Field");
        deleteFieldButton.addActionListener(e -> showDeleteFieldPanel());
        cardPanel.add(deleteFieldButton);

        // Rename Field Button and Action
        JButton renameFieldButton = new JButton("Rename Field");
        renameFieldButton.addActionListener(e -> showRenameFieldPanel());
        cardPanel.add(renameFieldButton);

        // Add Method Button and Action
        JButton addMethodButton = new JButton("Add Method");
        addMethodButton.addActionListener(e -> showAddMethodPanel());
        cardPanel.add(addMethodButton);

        // Delete Method Button and Action
        JButton deleteMethodButton = new JButton("Delete Method");
        deleteMethodButton.addActionListener(e -> showDeleteMethodPanel());
        cardPanel.add(deleteMethodButton);

        // Rename Method Button and Action
        JButton renameMethodButton = new JButton("Rename Method");
        renameMethodButton.addActionListener(e -> showRenameMethodPanel());
        cardPanel.add(renameMethodButton);

        // Change Parameters Button and Action
        JButton changeParametersButton = new JButton("Change Parameters");
        changeParametersButton.addActionListener(e -> showChangeParameterPanel());
        cardPanel.add(changeParametersButton);

        // Delete Parameter Button and Action
        JButton deleteParameterButton = new JButton("Delete Parameter");
        deleteParameterButton.addActionListener(e -> showDeleteParameterPanel());
        cardPanel.add(deleteParameterButton);

        // Add Relationship Button and Action
        JButton addRelationshipButton = new JButton("Add Relationship");
        addRelationshipButton.addActionListener(e -> showAddRelationshipPanel());
        cardPanel.add(addRelationshipButton);

        // Delete Relationship Button and Action
        JButton deleteRelationshipButton = new JButton("Delete Relationship");
        deleteRelationshipButton.addActionListener(e -> showDeleteRelationshipPanel());
        cardPanel.add(deleteRelationshipButton);

        // Rename Relationship Button and Action
        JButton renameRelationshipButton = new JButton("Rename Relationship");
        renameRelationshipButton.addActionListener(e -> showChangeRelationshipTypePanel());
        cardPanel.add(renameRelationshipButton);

        // List Classes Button and Action
        JButton listClassesButton = new JButton("List Classes");
        listClassesButton.addActionListener(e -> showListClassesPanel());
        cardPanel.add(listClassesButton);

        // List Relationships Button and Action
        JButton listRelationshipsButton = new JButton("List Relationships");
        listRelationshipsButton.addActionListener(e -> showListRelationshipsPanel());
        cardPanel.add(listRelationshipsButton);

        // Save UML Project Button and Action
        JButton saveButton = new JButton("Save UML Project");
        saveButton.addActionListener(e -> showSaveProjectPanel());
        cardPanel.add(saveButton);

        // Load UML Project Button and Action
        JButton loadButton = new JButton("Load UML Project");
        loadButton.addActionListener(e -> showLoadProjectPanel());
        cardPanel.add(loadButton);

        // Add the card panel to the frame
        add(cardPanel, BorderLayout.EAST);
    }

    private void showAddClassPanel() {
        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(new BoxLayout(addClassPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        addClassPanel.add(new JLabel("Class Name:"));
        addClassPanel.add(classNameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            if (umlEditor.addClass(className)) {
                outputArea.append("Class '" + className + "' added.\n");
                addClassRectangle(className); // Draw rectangle for the new class
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to add class '" + className + "'.\n");
            }
            classNameField.setText("");
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, ""); // Go back to main panel
        });
        addClassPanel.add(submitButton);

        // Show the add class panel
        JOptionPane.showMessageDialog(this, addClassPanel, "Add Class", JOptionPane.PLAIN_MESSAGE);
    }

    private void showDeleteClassPanel() {
        JPanel deleteClassPanel = new JPanel();
        deleteClassPanel.setLayout(new BoxLayout(deleteClassPanel, BoxLayout.Y_AXIS));

        JTextField deleteClassField = new JTextField(10);
        deleteClassPanel.add(new JLabel("Class Name to Delete:"));
        deleteClassPanel.add(deleteClassField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = deleteClassField.getText();
            if (umlEditor.deleteClass(className)) {
                outputArea.append("Class '" + className + "' deleted.\n");
                removeClassRectangle(className); // Remove the rectangle for the deleted class
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete class '" + className + "'.\n");
            }
            deleteClassField.setText("");
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, ""); // Go back to main panel
        });
        deleteClassPanel.add(submitButton);

        // Show the delete class panel
        JOptionPane.showMessageDialog(this, deleteClassPanel, "Delete Class", JOptionPane.PLAIN_MESSAGE);
    }

    private void showRenameClassPanel() {
        JPanel renameClassPanel = new JPanel();
        renameClassPanel.setLayout(new BoxLayout(renameClassPanel, BoxLayout.Y_AXIS));

        JTextField oldClassNameField = new JTextField(10);
        JTextField newClassNameField = new JTextField(10);

        renameClassPanel.add(new JLabel("Old Class Name:"));
        renameClassPanel.add(oldClassNameField);
        renameClassPanel.add(new JLabel("New Class Name:"));
        renameClassPanel.add(newClassNameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String oldName = oldClassNameField.getText();
            String newName = newClassNameField.getText();
            if (umlEditor.renameClass(oldName, newName)) {
                outputArea.append("Class '" + oldName + "' renamed to '" + newName + "'.\n");
                renameClassRectangle(oldName, newName); // Rename the rectangle
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to rename class from '" + oldName + "' to '" + newName + "'.\n");
            }
            oldClassNameField.setText("");
            newClassNameField.setText("");
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, ""); // Go back to main panel
        });
        renameClassPanel.add(submitButton);

        // Show the rename class panel
        JOptionPane.showMessageDialog(this, renameClassPanel, "Rename Class", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAddFieldPanel() {
        JPanel addFieldPanel = new JPanel();
        addFieldPanel.setLayout(new BoxLayout(addFieldPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField fieldNameField = new JTextField(10);
        addFieldPanel.add(new JLabel("Class Name:"));
        addFieldPanel.add(classNameField);
        addFieldPanel.add(new JLabel("Field Name:"));
        addFieldPanel.add(fieldNameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            String fieldName = fieldNameField.getText();
            if (umlEditor.addField(className, fieldName)) {
                outputArea.append("Field '" + fieldName + "' added to class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to add field '" + fieldName + "' to class '" + className + "'.\n");
            }
            classNameField.setText("");
            fieldNameField.setText("");
        });
        addFieldPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, addFieldPanel, "Add Field", JOptionPane.PLAIN_MESSAGE);
    }

    private void showDeleteFieldPanel() {
        JPanel deleteFieldPanel = new JPanel();
        deleteFieldPanel.setLayout(new BoxLayout(deleteFieldPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField fieldNameField = new JTextField(10);
        deleteFieldPanel.add(new JLabel("Class Name:"));
        deleteFieldPanel.add(classNameField);
        deleteFieldPanel.add(new JLabel("Field Name:"));
        deleteFieldPanel.add(fieldNameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            String fieldName = fieldNameField.getText();
            if (umlEditor.deleteField(className, fieldName)) {
                outputArea.append("Field '" + fieldName + "' deleted from class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete field '" + fieldName + "' from class '" + className + "'.\n");
            }
            classNameField.setText("");
            fieldNameField.setText("");
        });
        deleteFieldPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, deleteFieldPanel, "Delete Field", JOptionPane.PLAIN_MESSAGE);
    }

    private void showRenameFieldPanel() {
        JPanel renameFieldPanel = new JPanel();
        renameFieldPanel.setLayout(new BoxLayout(renameFieldPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField oldFieldNameField = new JTextField(10);
        JTextField newFieldNameField = new JTextField(10);
        renameFieldPanel.add(new JLabel("Class Name:"));
        renameFieldPanel.add(classNameField);
        renameFieldPanel.add(new JLabel("Old Field Name:"));
        renameFieldPanel.add(oldFieldNameField);
        renameFieldPanel.add(new JLabel("New Field Name:"));
        renameFieldPanel.add(newFieldNameField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            String oldFieldName = oldFieldNameField.getText();
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
            classNameField.setText("");
            oldFieldNameField.setText("");
            newFieldNameField.setText("");
        });
        renameFieldPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, renameFieldPanel, "Rename Field", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAddMethodPanel() {
        JPanel addMethodPanel = new JPanel();
        addMethodPanel.setLayout(new BoxLayout(addMethodPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField methodNameField = new JTextField(10);
        JTextField parameterListField = new JTextField(10);

        addMethodPanel.add(new JLabel("Class Name:"));
        addMethodPanel.add(classNameField);
        addMethodPanel.add(new JLabel("Method Name:"));
        addMethodPanel.add(methodNameField);
        addMethodPanel.add(new JLabel("Parameter List (comma-separated):"));
        addMethodPanel.add(parameterListField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = classNameField.getText();
            String methodName = methodNameField.getText();
            LinkedHashSet<String> paraList = parseParameterList(parameterListField.getText());
            if (umlEditor.addMethod(className, methodName, paraList)) {
                outputArea.append("Method '" + methodName + "' added to class '" + className + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to add method '" + methodName + "' to class '" + className + "'.\n");
            }
            classNameField.setText("");
            methodNameField.setText("");
            parameterListField.setText("");
        });
        addMethodPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, addMethodPanel, "Add Method", JOptionPane.PLAIN_MESSAGE);
    }

    private void showDeleteMethodPanel() {
        JPanel deleteMethodPanel = new JPanel();
        deleteMethodPanel.setLayout(new BoxLayout(deleteMethodPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField methodNameField = new JTextField(10);
        JTextField parameterListField = new JTextField(10);

        deleteMethodPanel.add(new JLabel("Class Name:"));
        deleteMethodPanel.add(classNameField);
        deleteMethodPanel.add(new JLabel("Method Name:"));
        deleteMethodPanel.add(methodNameField);
        deleteMethodPanel.add(new JLabel("Parameter List (comma-separated):"));
        deleteMethodPanel.add(parameterListField);

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
        });
        deleteMethodPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, deleteMethodPanel, "Delete Method", JOptionPane.PLAIN_MESSAGE);
    }

    private void showRenameMethodPanel() {
        JPanel renameMethodPanel = new JPanel();
        renameMethodPanel.setLayout(new BoxLayout(renameMethodPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField oldMethodNameField = new JTextField(10);
        JTextField newMethodNameField = new JTextField(10);
        JTextField parameterListField = new JTextField(10);

        renameMethodPanel.add(new JLabel("Class Name:"));
        renameMethodPanel.add(classNameField);
        renameMethodPanel.add(new JLabel("Old Method Name:"));
        renameMethodPanel.add(oldMethodNameField);
        renameMethodPanel.add(new JLabel("New Method Name:"));
        renameMethodPanel.add(newMethodNameField);
        renameMethodPanel.add(new JLabel("Parameter List (comma-separated):"));
        renameMethodPanel.add(parameterListField);

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
        });
        renameMethodPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, renameMethodPanel, "Rename Method", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Parses a comma-separated parameter list string into a LinkedHashSet.
     * 
     * @param parameterList The parameter list as a comma-separated string.
     * @return A LinkedHashSet containing the parameters.
     */
    private LinkedHashSet<String> parseParameterList(String parameterList) {
        LinkedHashSet<String> paraList = new LinkedHashSet<>();
        String[] parameters = parameterList.split(",");
        for (String parameter : parameters) {
            paraList.add(parameter.trim());
        }
        return paraList;
    }

    private void showDeleteParameterPanel() {
        JPanel deleteParameterPanel = new JPanel();
        deleteParameterPanel.setLayout(new BoxLayout(deleteParameterPanel, BoxLayout.Y_AXIS));

        JTextField classNameField = new JTextField(10);
        JTextField methodNameField = new JTextField(10);
        JTextField parameterNameField = new JTextField(10);

        deleteParameterPanel.add(new JLabel("Class Name:"));
        deleteParameterPanel.add(classNameField);
        deleteParameterPanel.add(new JLabel("Method Name:"));
        deleteParameterPanel.add(methodNameField);
        deleteParameterPanel.add(new JLabel("Parameter Name:"));
        deleteParameterPanel.add(parameterNameField);

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
        deleteParameterPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, deleteParameterPanel, "Delete Parameter", JOptionPane.PLAIN_MESSAGE);
    }

    private void showChangeParameterPanel() {
        // Create a panel with BoxLayout
        JPanel changeParameterPanel = new JPanel();
        changeParameterPanel.setLayout(new BoxLayout(changeParameterPanel, BoxLayout.Y_AXIS));

        // Input fields for class name, method name, and new parameters
        JTextField changeParameterClassField = new JTextField(10);
        JTextField changeParameterMethodField = new JTextField(10);
        JTextField changeParameterNewParamsField = new JTextField(10);

        // Button for changing parameters
        JButton changeParameterButton = new JButton("Change Parameters");

        // Action listener for the button
        changeParameterButton.addActionListener(e -> {
            String className = changeParameterClassField.getText();
            String methodName = changeParameterMethodField.getText();
            String newParametersInput = changeParameterNewParamsField.getText();

            // Convert the comma-separated parameters into a LinkedHashSet
            LinkedHashSet<String> newParametersSet = new LinkedHashSet<>();
            if (!newParametersInput.trim().isEmpty()) {
                // Split by comma and trim spaces, then add to the set
                Arrays.stream(newParametersInput.split(","))
                        .map(String::trim)
                        .forEach(newParametersSet::add);
            }

            // Call the changeParameters method with the class name, method name, and new
            // parameters set
            if (umlEditor.changeParameters(className, methodName, newParametersSet)) {
                outputArea.append("Parameters of method '" + methodName + "' in class '" + className + "' changed to: "
                        + newParametersInput + ".\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append(
                        "Failed to change parameters of method '" + methodName + "' in class '" + className + "'.\n");
            }

            // Clear the input fields
            changeParameterClassField.setText("");
            changeParameterMethodField.setText("");
            changeParameterNewParamsField.setText("");
        });

        // Add components to the panel for changing parameters
        changeParameterPanel.add(new JLabel("Class Name for Parameter Change:"));
        changeParameterPanel.add(changeParameterClassField);
        changeParameterPanel.add(new JLabel("Method Name:"));
        changeParameterPanel.add(changeParameterMethodField);
        changeParameterPanel.add(new JLabel("New Parameters (comma-separated):"));
        changeParameterPanel.add(changeParameterNewParamsField);
        changeParameterPanel.add(changeParameterButton);

        // Show the panel in a dialog
        JOptionPane.showMessageDialog(this, changeParameterPanel, "Change Method Parameters",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void showAddRelationshipPanel() {
        JPanel addRelationshipPanel = new JPanel();
        addRelationshipPanel.setLayout(new BoxLayout(addRelationshipPanel, BoxLayout.Y_AXIS));

        JTextField sourceField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        // Create a combo box for RelationshipType
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" }; // Add your
                                                                                                     // relationship
                                                                                                     // types here
        JComboBox<String> typeComboBox = new JComboBox<>(relationshipTypes);

        addRelationshipPanel.add(new JLabel("Source Class:"));
        addRelationshipPanel.add(sourceField);
        addRelationshipPanel.add(new JLabel("Destination Class:"));
        addRelationshipPanel.add(destinationField);
        addRelationshipPanel.add(new JLabel("Relationship Type:"));
        addRelationshipPanel.add(typeComboBox); // Add the combo box instead of a text field

        JButton submitButton = new JButton("Add Relationship");
        submitButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String typeString = (String) typeComboBox.getSelectedItem(); // Get selected item from combo box

            // Convert selected item to enum
            RelationshipType type;
            try {
                type = RelationshipType.valueOf(typeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid relationship type: '" + typeString + "'. Please use a valid type.\n");
                return; // Exit if the type is invalid
            }

            if (umlEditor.addRelationship(source, destination, type)) {
                outputArea.append(
                        "Added relationship from '" + source + "' to '" + destination + "' of type '" + type + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to add relationship from '" + source + "' to '" + destination + "'.\n");
            }
            // Clear the fields
            sourceField.setText("");
            destinationField.setText("");
            typeComboBox.setSelectedIndex(0); // Reset combo box to default
        });
        addRelationshipPanel.add(submitButton);

        JOptionPane.showMessageDialog(this, addRelationshipPanel, "Add Relationship", JOptionPane.PLAIN_MESSAGE);
    }

    private void showDeleteRelationshipPanel() {
        JPanel deleteRelationshipPanel = new JPanel();
        deleteRelationshipPanel.setLayout(new BoxLayout(deleteRelationshipPanel, BoxLayout.Y_AXIS));

        JTextField sourceField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        // Create a combo box for RelationshipType
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" }; // Define your
                                                                                                     // relationship
                                                                                                     // types here
        JComboBox<String> typeComboBox = new JComboBox<>(relationshipTypes);

        deleteRelationshipPanel.add(new JLabel("Source Class:"));
        deleteRelationshipPanel.add(sourceField);
        deleteRelationshipPanel.add(new JLabel("Destination Class:"));
        deleteRelationshipPanel.add(destinationField);
        deleteRelationshipPanel.add(new JLabel("Relationship Type:"));
        deleteRelationshipPanel.add(typeComboBox); // Add the combo box instead of a text field

        JButton submitButton = new JButton("Delete Relationship");
        submitButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String typeString = (String) typeComboBox.getSelectedItem(); // Get selected item from combo box

            RelationshipType type;
            // Attempt to convert the string to RelationshipType
            try {
                type = RelationshipType.valueOf(typeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid relationship type: '" + typeString + "'. Please use a valid type.\n");
                return; // Exit if the type is invalid
            }

            if (umlEditor.deleteRelationship(source, destination, type)) {
                outputArea.append("Deleted relationship of type '" + type + "' between '" + source + "' and '"
                        + destination + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea
                        .append("Failed to delete relationship between '" + source + "' and '" + destination + "'.\n");
            }

            // Clear input fields
            sourceField.setText("");
            destinationField.setText("");
            typeComboBox.setSelectedIndex(0); // Reset combo box to default
        });

        deleteRelationshipPanel.add(submitButton);

        // Show the panel in a dialog
        JOptionPane.showMessageDialog(this, deleteRelationshipPanel, "Delete Relationship", JOptionPane.PLAIN_MESSAGE);
    }

    private void showChangeRelationshipTypePanel() {
        JPanel changeRelationshipTypePanel = new JPanel();
        changeRelationshipTypePanel.setLayout(new BoxLayout(changeRelationshipTypePanel, BoxLayout.Y_AXIS));

        JTextField sourceField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        // Create a combo box for Current Relationship Type
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" }; // Define your
                                                                                                     // relationship
                                                                                                     // types here
        JComboBox<String> currentTypeComboBox = new JComboBox<>(relationshipTypes);
        JComboBox<String> newTypeComboBox = new JComboBox<>(relationshipTypes); // New relationship type combo box

        changeRelationshipTypePanel.add(new JLabel("Source Class:"));
        changeRelationshipTypePanel.add(sourceField);
        changeRelationshipTypePanel.add(new JLabel("Destination Class:"));
        changeRelationshipTypePanel.add(destinationField);
        changeRelationshipTypePanel.add(new JLabel("Current Relationship Type:"));
        changeRelationshipTypePanel.add(currentTypeComboBox); // Use combo box for current type
        changeRelationshipTypePanel.add(new JLabel("New Relationship Type:"));
        changeRelationshipTypePanel.add(newTypeComboBox); // Use combo box for new type

        JButton submitButton = new JButton("Change Relationship Type");
        submitButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String currentTypeString = (String) currentTypeComboBox.getSelectedItem(); // Get selected current type
            String newTypeString = (String) newTypeComboBox.getSelectedItem(); // Get selected new type

            RelationshipType currentType;
            RelationshipType newType;

            // Try to convert strings to RelationshipType enums
            try {
                currentType = RelationshipType.valueOf(currentTypeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append(
                        "Invalid current relationship type: '" + currentTypeString + "'. Please use a valid type.\n");
                return; // Exit if the current type is invalid
            }

            try {
                newType = RelationshipType.valueOf(newTypeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid new relationship type: '" + newTypeString + "'. Please use a valid type.\n");
                return; // Exit if the new type is invalid
            }

            if (umlEditor.changeRelationshipType(source, destination, currentType, newType)) {
                outputArea.append("Changed relationship type from '" + currentType + "' to '" + newType + "' between '"
                        + source + "' and '" + destination + "'.\n");
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append(
                        "Failed to change relationship type between '" + source + "' and '" + destination + "'.\n");
            }
            sourceField.setText("");
            destinationField.setText("");
            currentTypeComboBox.setSelectedIndex(0); // Reset combo box to default
            newTypeComboBox.setSelectedIndex(0); // Reset combo box to default
        });

        changeRelationshipTypePanel.add(submitButton);

        JOptionPane.showMessageDialog(this, changeRelationshipTypePanel, "Change Relationship Type",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void showListClassesPanel() {
        // Clear the output area before displaying the classes
        outputArea.setText(""); // Assuming you have an outputArea to display the classes

        // Call the listClasses method and display the classes
        for (UmlClass umlClass : umlEditor.getClasses().values()) {
            outputArea.append(umlClass + "\n");
        }
    }

    private void showListRelationshipsPanel() {
        // Clear the output area before displaying the relationships
        outputArea.setText(""); // Assuming you have an outputArea to display the relationships

        // Call the listRelationships method and display the relationships
        for (UmlRelationship relationship : umlEditor.getRelationships()) {
            outputArea.append(relationship + "\n");
        }
    }

    private void showSaveProjectPanel() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                JsonUtils.save(umlEditor, file.getAbsolutePath());
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
                umlEditor = JsonUtils.load(file.getAbsolutePath());
                outputArea.append("Project loaded successfully from " + file.getAbsolutePath() + ".\n");

                // Clear previous class positions
                classPositions.clear();

                // Populate classPositions based on the loaded UML editor
                for (Map.Entry<String, UmlClass> entry : umlEditor.getClasses().entrySet()) {
                    String className = entry.getKey();
                    // Set initial positions for the classes (you may need to adjust this logic)
                    classPositions.put(className, new Point(100, 100)); // Set positions as needed
                }

                // Repaint the panel to show loaded classes and relationships
                drawingPanel.repaint();
            } catch (IOException ex) {
                outputArea.append("Failed to load project: " + ex.getMessage() + "\n");
            }
        }
    }

    private void addClassRectangle(String className) {
        Random random = new Random();
        int x, y;

        // Generate random positions while avoiding overlap
        boolean overlap;
        do {
            overlap = false;
            x = random.nextInt(drawingPanel.getWidth() - 120); // Adjust width to avoid overflow
            y = random.nextInt(drawingPanel.getHeight() - 70); // Adjust height to avoid overflow

            // Check for overlap with existing positions
            for (Point point : classPositions.values()) {
                if (Math.abs(point.x - x) < 120 && Math.abs(point.y - y) < 70) { // Adjust distance to your rectangle
                                                                                 // size
                    overlap = true;
                    break;
                }
            }
        } while (overlap);

        classPositions.put(className, new Point(x, y)); // Store position
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
                                50 + (umlEditor.getClass(className).getFields().size() * 15));
                        if (rect.contains(e.getPoint())) {
                            selectedClassName = className; // Set the selected class
                            dragStartPoint = e.getPoint(); // Store the initial drag point
                            break;
                        }
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    selectedClassName = null; // Clear selection on mouse release
                    dragStartPoint = null; // Clear drag start point
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
            for (UmlRelationship relationship : umlEditor.getRelationships()) {
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

                    // Determine color and line style based on relationship type
                    switch (relationship.getType()) {
                        case INHERITANCE:
                            g2d.setColor(Color.BLUE); // Color for inheritance
                            g2d.drawLine(sourcePosition.x + 50, sourcePosition.y + 25 + offset,
                                    destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset);
                            drawArrow(g, destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset,
                                    "inheritance"); // Pass "inheritance" for arrowhead style
                            break;

                        case REALIZATION:
                            g2d.setColor(Color.GREEN); // Color for realization
                            // Draw dotted line for realization
                            float[] dottedPattern = { 2f, 5f }; // Dotted line pattern
                            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f,
                                    dottedPattern, 0f));
                            g2d.drawLine(sourcePosition.x + 50, sourcePosition.y + 25 + offset,
                                    destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset);
                            g2d.setStroke(new BasicStroke()); // Reset stroke to solid
                            drawArrow(g, destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset,
                                    "realization"); // Pass "realization" for arrowhead style
                            break;

                        case AGGREGATION:
                            g2d.setColor(Color.ORANGE); // Color for aggregation
                            // Draw dashed line for aggregation
                            float[] dashPattern = { 10f, 5f }; // Dashed line pattern
                            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f,
                                    dashPattern, 0f));
                            g2d.drawLine(sourcePosition.x + 50, sourcePosition.y + 25 + offset,
                                    destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset);
                            g2d.setStroke(new BasicStroke()); // Reset stroke to solid
                            drawArrow(g, destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset,
                                    "aggregation"); // Pass "aggregation" for arrowhead style
                            break;

                        case COMPOSITION:
                            g2d.setColor(Color.RED); // Color for composition
                            g2d.drawLine(sourcePosition.x + 50, sourcePosition.y + 25 + offset,
                                    destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset);
                            drawArrow(g, destinationPosition.x + 50,
                                    destinationPosition.y + getBoxHeight(relationship.getDestination()) + offset,
                                    "composition"); // Pass "composition" for arrowhead style
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
                UmlClass umlClass = umlEditor.getClass(className);
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
            UmlClass umlClass = umlEditor.getClass(className);
            int attributeCount = (umlClass != null) ? umlClass.getFields().size() : 0;
            int methodCount = (umlClass != null) ? umlClass.getMethods().size() : 0; // Get the number of methods
            return 50 + (attributeCount * 15) + (methodCount * 15); // Base height + dynamic attribute and method height
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}