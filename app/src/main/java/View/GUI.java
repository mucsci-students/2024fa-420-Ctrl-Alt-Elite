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
import javax.swing.border.EmptyBorder;

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
        JButton renameRelationshipButton = new JButton("Change Relationship");
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
        // Create a new JDialog
        JDialog dialog = new JDialog(this, "Add Class", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Create the add class panel with input fields
        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(new BoxLayout(addClassPanel, BoxLayout.Y_AXIS));
        addClassPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        JTextField classNameField = new JTextField(15); // Adjust width
        addClassPanel.add(new JLabel("Class Name:"));
        addClassPanel.add(Box.createVerticalStrut(5)); // Add space between label and text field
        addClassPanel.add(classNameField);
        addClassPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        // Create the Submit button
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
            dialog.dispose(); // Close the dialog after submission
        });

        // Add the submit button to the panel
        addClassPanel.add(submitButton);

        // Add the panel to the dialog, set size, and center it
        dialog.getContentPane().add(addClassPanel);
        dialog.pack();
        dialog.setSize(300, 150); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showDeleteClassPanel() {
        JDialog dialog = new JDialog(this, "Delete Class", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel deleteClassPanel = new JPanel();
        deleteClassPanel.setLayout(new BoxLayout(deleteClassPanel, BoxLayout.Y_AXIS));
        deleteClassPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField deleteClassField = new JTextField(15); // Adjust width
        deleteClassPanel.add(new JLabel("Class Name to Delete:"));
        deleteClassPanel.add(Box.createVerticalStrut(5)); // Add space between label and text field
        deleteClassPanel.add(deleteClassField);
        deleteClassPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String className = deleteClassField.getText();
            if (umlEditor.deleteClass(className)) {
                outputArea.append("Class '" + className + "' deleted.\n");
                removeClassRectangle(className);
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to delete class '" + className + "'.\n");
            }
            deleteClassField.setText("");
            dialog.dispose();
        });

        deleteClassPanel.add(submitButton);
        dialog.getContentPane().add(deleteClassPanel);
        dialog.pack();
        dialog.setSize(300, 150); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showRenameClassPanel() {
        JDialog dialog = new JDialog(this, "Rename Class", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel renameClassPanel = new JPanel();
        renameClassPanel.setLayout(new BoxLayout(renameClassPanel, BoxLayout.Y_AXIS));
        renameClassPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField oldClassNameField = new JTextField(15); // Adjust width
        JTextField newClassNameField = new JTextField(15); // Adjust width

        renameClassPanel.add(new JLabel("Old Class Name:"));
        renameClassPanel.add(Box.createVerticalStrut(5));
        renameClassPanel.add(oldClassNameField);
        renameClassPanel.add(Box.createVerticalStrut(10)); // Add space between fields
        renameClassPanel.add(new JLabel("New Class Name:"));
        renameClassPanel.add(Box.createVerticalStrut(5));
        renameClassPanel.add(newClassNameField);
        renameClassPanel.add(Box.createVerticalStrut(10)); // Add space before the button

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String oldName = oldClassNameField.getText();
            String newName = newClassNameField.getText();
            if (umlEditor.renameClass(oldName, newName)) {
                outputArea.append("Class '" + oldName + "' renamed to '" + newName + "'.\n");
                renameClassRectangle(oldName, newName);
                drawingPanel.revalidate();
                drawingPanel.repaint();
            } else {
                outputArea.append("Failed to rename class from '" + oldName + "' to '" + newName + "'.\n");
            }
            oldClassNameField.setText("");
            newClassNameField.setText("");
            dialog.dispose();
        });

        renameClassPanel.add(submitButton);
        dialog.getContentPane().add(renameClassPanel);
        dialog.pack();
        dialog.setSize(350, 200); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAddFieldPanel() {
        JDialog dialog = new JDialog(this, "Add Field", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel addFieldPanel = new JPanel();
        addFieldPanel.setLayout(new BoxLayout(addFieldPanel, BoxLayout.Y_AXIS));
        addFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(15); // Adjust width
        JTextField fieldNameField = new JTextField(15); // Adjust width
        addFieldPanel.add(new JLabel("Class Name:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(classNameField);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space between fields
        addFieldPanel.add(new JLabel("Field Name:"));
        addFieldPanel.add(Box.createVerticalStrut(5));
        addFieldPanel.add(fieldNameField);
        addFieldPanel.add(Box.createVerticalStrut(10)); // Add space before the button

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
        deleteFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(15); // Adjust width
        JTextField fieldNameField = new JTextField(15); // Adjust width
        deleteFieldPanel.add(new JLabel("Class Name:"));
        deleteFieldPanel.add(Box.createVerticalStrut(5));
        deleteFieldPanel.add(classNameField);
        deleteFieldPanel.add(Box.createVerticalStrut(10)); // Add space between fields
        deleteFieldPanel.add(new JLabel("Field Name:"));
        deleteFieldPanel.add(Box.createVerticalStrut(5));
        deleteFieldPanel.add(fieldNameField);
        deleteFieldPanel.add(Box.createVerticalStrut(10)); // Add space before the button

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
            dialog.dispose();
        });

        deleteFieldPanel.add(submitButton);
        dialog.getContentPane().add(deleteFieldPanel);
        dialog.pack();
        dialog.setSize(300, 200); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showRenameFieldPanel() {
        JDialog dialog = new JDialog(this, "Rename Field", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel renameFieldPanel = new JPanel();
        renameFieldPanel.setLayout(new BoxLayout(renameFieldPanel, BoxLayout.Y_AXIS));
        renameFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(15); // Adjust width
        JTextField oldFieldNameField = new JTextField(15); // Adjust width
        JTextField newFieldNameField = new JTextField(15); // Adjust width
        renameFieldPanel.add(new JLabel("Class Name:"));
        renameFieldPanel.add(Box.createVerticalStrut(5));
        renameFieldPanel.add(classNameField);
        renameFieldPanel.add(Box.createVerticalStrut(10));
        renameFieldPanel.add(new JLabel("Old Field Name:"));
        renameFieldPanel.add(Box.createVerticalStrut(5));
        renameFieldPanel.add(oldFieldNameField);
        renameFieldPanel.add(Box.createVerticalStrut(10));
        renameFieldPanel.add(new JLabel("New Field Name:"));
        renameFieldPanel.add(Box.createVerticalStrut(5));
        renameFieldPanel.add(newFieldNameField);
        renameFieldPanel.add(Box.createVerticalStrut(10)); // Add space before the button

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
            dialog.dispose();
        });

        renameFieldPanel.add(submitButton);
        dialog.getContentPane().add(renameFieldPanel);
        dialog.pack();
        dialog.setSize(350, 250); // Set a preferred size
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showAddMethodPanel() {
        JDialog dialog = new JDialog(this, "Add Method", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel addMethodPanel = new JPanel();
        addMethodPanel.setLayout(new BoxLayout(addMethodPanel, BoxLayout.Y_AXIS));
        addMethodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        JTextField classNameField = new JTextField(15); // Adjust width
        JTextField methodNameField = new JTextField(15); // Adjust width
        JTextField parameterListField = new JTextField(15); // Adjust width

        addMethodPanel.add(new JLabel("Class Name:"));
        addMethodPanel.add(Box.createVerticalStrut(5));
        addMethodPanel.add(classNameField);
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
            dialog.dispose(); // Close dialog after submission
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

        JTextField sourceField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        // Create a combo box for RelationshipType
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" };
        JComboBox<String> typeComboBox = new JComboBox<>(relationshipTypes);

        // Set alignment for each component to the left
        JLabel sourceLabel = new JLabel("Source Class:");
        sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel destinationLabel = new JLabel("Destination Class:");
        destinationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        destinationField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Relationship Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the panel
        addRelationshipPanel.add(sourceLabel);
        addRelationshipPanel.add(sourceField);
        addRelationshipPanel.add(destinationLabel);
        addRelationshipPanel.add(destinationField);
        addRelationshipPanel.add(typeLabel);
        addRelationshipPanel.add(typeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align button to the left
        submitButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String typeString = (String) typeComboBox.getSelectedItem();

            // Convert selected item to enum
            RelationshipType type;
            try {
                type = RelationshipType.valueOf(typeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid relationship type: '" + typeString + "'. Please use a valid type.\n");
                return;
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
            typeComboBox.setSelectedIndex(0);
        });

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

        JTextField sourceField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        // Create a combo box for RelationshipType
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" };
        JComboBox<String> typeComboBox = new JComboBox<>(relationshipTypes);

        // Create and align labels and fields
        JLabel sourceLabel = new JLabel("Source Class:");
        sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel destinationLabel = new JLabel("Destination Class:");
        destinationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        destinationField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel typeLabel = new JLabel("Relationship Type:");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the panel
        deleteRelationshipPanel.add(sourceLabel);
        deleteRelationshipPanel.add(sourceField);
        deleteRelationshipPanel.add(destinationLabel);
        deleteRelationshipPanel.add(destinationField);
        deleteRelationshipPanel.add(typeLabel);
        deleteRelationshipPanel.add(typeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align button to the left
        submitButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String typeString = (String) typeComboBox.getSelectedItem();

            RelationshipType type;
            // Attempt to convert the string to RelationshipType
            try {
                type = RelationshipType.valueOf(typeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid relationship type: '" + typeString + "'. Please use a valid type.\n");
                return;
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
            typeComboBox.setSelectedIndex(0);
        });

        deleteRelationshipPanel.add(submitButton);

        // Show the panel in a dialog without OK button
        JDialog dialog = new JDialog(this, "Delete Relationship", true);
        dialog.getContentPane().add(deleteRelationshipPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showChangeRelationshipTypePanel() {
        JPanel changeRelationshipTypePanel = new JPanel();
        changeRelationshipTypePanel.setLayout(new BoxLayout(changeRelationshipTypePanel, BoxLayout.Y_AXIS));
        changeRelationshipTypePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Set padding

        JTextField sourceField = new JTextField(10);
        JTextField destinationField = new JTextField(10);

        // Create combo boxes for Current and New Relationship Type
        String[] relationshipTypes = { "REALIZATION", "AGGREGATION", "COMPOSITION", "INHERITANCE" };
        JComboBox<String> currentTypeComboBox = new JComboBox<>(relationshipTypes);
        JComboBox<String> newTypeComboBox = new JComboBox<>(relationshipTypes);

        // Create and align labels and fields
        JLabel sourceLabel = new JLabel("Source Class:");
        sourceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sourceField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel destinationLabel = new JLabel("Destination Class:");
        destinationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        destinationField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel currentTypeLabel = new JLabel("Current Relationship Type:");
        currentTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        currentTypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel newTypeLabel = new JLabel("New Relationship Type:");
        newTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        newTypeComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the panel
        changeRelationshipTypePanel.add(sourceLabel);
        changeRelationshipTypePanel.add(sourceField);
        changeRelationshipTypePanel.add(destinationLabel);
        changeRelationshipTypePanel.add(destinationField);
        changeRelationshipTypePanel.add(currentTypeLabel);
        changeRelationshipTypePanel.add(currentTypeComboBox);
        changeRelationshipTypePanel.add(newTypeLabel);
        changeRelationshipTypePanel.add(newTypeComboBox);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Align button to the left
        submitButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            String currentTypeString = (String) currentTypeComboBox.getSelectedItem();
            String newTypeString = (String) newTypeComboBox.getSelectedItem();

            RelationshipType currentType;
            RelationshipType newType;

            // Try to convert strings to RelationshipType enums
            try {
                currentType = RelationshipType.valueOf(currentTypeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append(
                        "Invalid current relationship type: '" + currentTypeString + "'. Please use a valid type.\n");
                return;
            }

            try {
                newType = RelationshipType.valueOf(newTypeString);
            } catch (IllegalArgumentException ex) {
                outputArea.append("Invalid new relationship type: '" + newTypeString + "'. Please use a valid type.\n");
                return;
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
            currentTypeComboBox.setSelectedIndex(0);
            newTypeComboBox.setSelectedIndex(0);
        });

        changeRelationshipTypePanel.add(submitButton);

        // Show the panel in a dialog without OK button
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