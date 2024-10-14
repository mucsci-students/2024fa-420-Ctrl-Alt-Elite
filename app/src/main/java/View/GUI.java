package View;
import javax.swing.*;

import Controller.UmlEditor;
import Model.JsonUtils;
import Model.RelationshipType;
import Model.UmlClass;
import Model.UmlRelationship;

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

public class GUI extends JFrame {
    private UmlEditor umlEditor;
    private JTextArea outputArea;
    private DrawingPanel drawingPanel; // Panel for drawing

    // To keep track of class positions
    private Map<String, Point> classPositions;

    public GUI() {
        umlEditor = new UmlEditor();
        classPositions = new HashMap<>(); // Initialize class positions
        setTitle("UML Editor");
        setSize(800, 400); // Increased width to accommodate drawing panel
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.WEST); // Moved to the left

        drawingPanel = new DrawingPanel(); // Create the drawing panel
        add(drawingPanel, BorderLayout.CENTER); // Add it to the center

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Add Class
        JTextField classNameField = new JTextField();
        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(e -> {
            String className = classNameField.getText();
            if (umlEditor.addClass(className)) {
                outputArea.append("Class '" + className + "' added.\n");
                addClassRectangle(className); // Draw rectangle for the new class
            } else {
                outputArea.append("Failed to add class '" + className + "'.\n");
            }
            classNameField.setText("");
        });

        panel.add(new JLabel("Class Name:"));
        panel.add(classNameField);
        panel.add(addClassButton);

        // Delete Class
        JTextField deleteClassField = new JTextField();
        JButton deleteClassButton = new JButton("Delete Class");
        deleteClassButton.addActionListener(e -> {
            String className = deleteClassField.getText();
            if (umlEditor.deleteClass(className)) {
                outputArea.append("Class '" + className + "' deleted.\n");
                removeClassRectangle(className); // Remove the rectangle for the deleted class
            } else {
                outputArea.append("Failed to delete class '" + className + "'.\n");
            }
            deleteClassField.setText("");
        });

        panel.add(new JLabel("Class Name to Delete:"));
        panel.add(deleteClassField);
        panel.add(deleteClassButton);

        // Rename Class
        JTextField oldClassNameField = new JTextField();
        JTextField newClassNameField = new JTextField();
        JButton renameClassButton = new JButton("Rename Class");
        renameClassButton.addActionListener(e -> {
            String oldName = oldClassNameField.getText();
            String newName = newClassNameField.getText();
            if (umlEditor.renameClass(oldName, newName)) {
                outputArea.append("Class '" + oldName + "' renamed to '" + newName + "'.\n");
                renameClassRectangle(oldName, newName); // Rename the rectangle
            } else {
                outputArea.append("Failed to rename class from '" + oldName + "' to '" + newName + "'.\n");
            }
            oldClassNameField.setText("");
            newClassNameField.setText("");
        });

        panel.add(new JLabel("Old Class Name:"));
        panel.add(oldClassNameField);
        panel.add(new JLabel("New Class Name:"));
        panel.add(newClassNameField);
        panel.add(renameClassButton);

        // Add Field
        JTextField addFieldClassField = new JTextField();
        JTextField fieldField = new JTextField();
        JButton addFieldButton = new JButton("Add Field");
        addFieldButton.addActionListener(e -> {
            String className = addFieldClassField.getText();
            String field = fieldField.getText(); // Changed to 'field' to match new naming
            if (umlEditor.addField(className, field)) {
                outputArea.append("Field '" + field + "' added to class '" + className + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated fields
            } else {
                outputArea.append("Failed to add field '" + field + "' to class '" + className + "'.\n");
            }
            addFieldClassField.setText("");
            fieldField.setText(""); // Changed to clear 'fieldField'
        });

        // Add components to the panel
        panel.add(new JLabel("Class Name for Field:")); // Updated label text
        panel.add(addFieldClassField);
        panel.add(new JLabel("Field Name:")); // Updated label text
        panel.add(fieldField);
        panel.add(addFieldButton);

        // Delete Field
        JTextField deleteFieldClassField = new JTextField(); // Changed variable name to reflect 'field'
        JTextField deleteFieldField = new JTextField(); // Changed variable name to reflect 'field'
        JButton deleteFieldButton = new JButton("Delete Field"); // Updated button text
        deleteFieldButton.addActionListener(e -> {
            String className = deleteFieldClassField.getText();
            String field = deleteFieldField.getText(); // Changed to 'field'
            if (umlEditor.deleteField(className, field)) {
                outputArea.append("Field '" + field + "' deleted from class '" + className + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated fields
            } else {
                outputArea.append("Failed to delete field '" + field + "' from class '" + className + "'.\n");
            }
            deleteFieldClassField.setText("");
            deleteFieldField.setText(""); // Changed to clear 'deleteFieldField'
        });

        // Add components to the panel for deleting a field
        panel.add(new JLabel("Class Name for Field to Delete:")); // Updated label text
        panel.add(deleteFieldClassField);
        panel.add(new JLabel("Field Name to Delete:")); // Updated label text
        panel.add(deleteFieldField);
        panel.add(deleteFieldButton);

        // Add Relationship
        JTextField sourceClassField = new JTextField();
        JTextField destinationClassField = new JTextField();
        JComboBox<RelationshipType> relationshipTypeComboBox = new JComboBox<>(RelationshipType.values()); // Combo box
                                                                                                           // for
                                                                                                           // relationship
                                                                                                           // type
        JButton addRelationshipButton = new JButton("Add Relationship");

        addRelationshipButton.addActionListener(e -> {
            String source = sourceClassField.getText().trim(); // Trim whitespace
            String destination = destinationClassField.getText().trim(); // Trim whitespace
            RelationshipType type = (RelationshipType) relationshipTypeComboBox.getSelectedItem(); // Get selected
                                                                                                   // relationship type

            if (source.isEmpty() || destination.isEmpty()) {
                outputArea.append("Source and destination class names cannot be empty.\n");
                return; // Exit if either field is empty
            }

            try {
                umlEditor.addRelationship(source, destination, type); // Call the method with relationship type
                outputArea
                        .append("Relationship added from '" + source + "' to '" + destination + "' as " + type + ".\n");
                drawingPanel.repaint(); // Repaint to show the updated relationships
            } catch (IllegalArgumentException ex) {
                outputArea.append("Failed to add relationship: " + ex.getMessage() + ".\n");
            }
            sourceClassField.setText("");
            destinationClassField.setText("");
        });

        // Adding components to the panel
        panel.add(new JLabel("Source Class Name:"));
        panel.add(sourceClassField);
        panel.add(new JLabel("Destination Class Name:"));
        panel.add(destinationClassField);
        panel.add(new JLabel("Relationship Type:"));
        panel.add(relationshipTypeComboBox); // Add relationship type selection
        panel.add(addRelationshipButton);

        // Delete Relationship
        JTextField deleteSourceClassField = new JTextField();
        JTextField deleteDestinationClassField = new JTextField();
        JComboBox<RelationshipType> deleteRelationshipTypeComboBox = new JComboBox<>(RelationshipType.values()); // Combo
                                                                                                                 // box
                                                                                                                 // for
                                                                                                                 // relationship
                                                                                                                 // type
        JButton deleteRelationshipButton = new JButton("Delete Relationship");

        deleteRelationshipButton.addActionListener(e -> {
            String source = deleteSourceClassField.getText().trim();
            String destination = deleteDestinationClassField.getText().trim();
            RelationshipType type = (RelationshipType) deleteRelationshipTypeComboBox.getSelectedItem(); // Get selected
                                                                                                         // relationship
                                                                                                         // type

            if (source.isEmpty() || destination.isEmpty()) {
                outputArea.append("Source and destination class names cannot be empty.\n");
                return; // Exit if either field is empty
            }

            if (umlEditor.deleteRelationship(source, destination, type)) { // Update this to include relationship type
                outputArea.append(
                        "Relationship deleted from '" + source + "' to '" + destination + "' as " + type + ".\n");
                drawingPanel.repaint(); // Repaint to remove the line after the relationship is deleted
            } else {
                outputArea.append("Failed to delete relationship from '" + source + "' to '" + destination + "'.\n");
            }
            deleteSourceClassField.setText("");
            deleteDestinationClassField.setText("");
        });

        // Adding components to the panel
        panel.add(new JLabel("Source Class Name to Delete:"));
        panel.add(deleteSourceClassField);
        panel.add(new JLabel("Destination Class Name to Delete:"));
        panel.add(deleteDestinationClassField);
        panel.add(new JLabel("Relationship Type:"));
        panel.add(deleteRelationshipTypeComboBox); // Add relationship type selection
        panel.add(deleteRelationshipButton);

        // Change Relationship Type
        JTextField changeSourceClassField = new JTextField();
        JTextField changeDestinationClassField = new JTextField();
        JComboBox<RelationshipType> changeOldRelationshipTypeComboBox = new JComboBox<>(RelationshipType.values()); // Combo
                                                                                                                    // box
                                                                                                                    // for
                                                                                                                    // old
                                                                                                                    // relationship
                                                                                                                    // type
        JComboBox<RelationshipType> changeNewRelationshipTypeComboBox = new JComboBox<>(RelationshipType.values()); // Combo
                                                                                                                    // box
                                                                                                                    // for
                                                                                                                    // new
                                                                                                                    // relationship
                                                                                                                    // type
        JButton changeRelationshipButton = new JButton("Change Relationship Type");

        changeRelationshipButton.addActionListener(e -> {
            String source = changeSourceClassField.getText().trim();
            String destination = changeDestinationClassField.getText().trim();
            RelationshipType oldType = (RelationshipType) changeOldRelationshipTypeComboBox.getSelectedItem(); // Get
                                                                                                               // selected
                                                                                                               // old
                                                                                                               // relationship
                                                                                                               // type
            RelationshipType newType = (RelationshipType) changeNewRelationshipTypeComboBox.getSelectedItem(); // Get
                                                                                                               // selected
                                                                                                               // new
                                                                                                               // relationship
                                                                                                               // type

            if (source.isEmpty() || destination.isEmpty()) {
                outputArea.append("Source and destination class names cannot be empty.\n");
                return; // Exit if either field is empty
            }

            // Call the change relationship type method with oldType and newType
            if (umlEditor.changeRelationshipType(source, destination, oldType, newType)) {
                outputArea.append("Relationship type changed between '" + source + "' and '" + destination + "' from "
                        + oldType + " to " + newType + ".\n");
                drawingPanel.repaint(); // Repaint to reflect the updated relationship type
            } else {
                outputArea.append(
                        "Failed to change relationship type between '" + source + "' and '" + destination + "'.\n");
            }

            // Clear input fields
            changeSourceClassField.setText("");
            changeDestinationClassField.setText("");
        });

        // Adding components to the panel
        panel.add(new JLabel("Source Class Name:"));
        panel.add(changeSourceClassField);
        panel.add(new JLabel("Destination Class Name:"));
        panel.add(changeDestinationClassField);
        panel.add(new JLabel("Old Relationship Type:"));
        panel.add(changeOldRelationshipTypeComboBox); // Add old relationship type selection
        panel.add(new JLabel("New Relationship Type:"));
        panel.add(changeNewRelationshipTypeComboBox); // Add new relationship type selection
        panel.add(changeRelationshipButton);

        // Add Method
        JTextField addMethodClassField = new JTextField();
        JTextField methodField = new JTextField();
        JTextField parametersField = new JTextField(); // New field for parameters
        JButton addMethodButton = new JButton("Add Method");
        addMethodButton.addActionListener(e -> {
            String className = addMethodClassField.getText();
            String methodName = methodField.getText();
            String parametersInput = parametersField.getText(); // Get the parameters (optional)

            // Convert the comma-separated parameters into a LinkedHashSet
            LinkedHashSet<String> parametersSet = new LinkedHashSet<>();
            if (!parametersInput.trim().isEmpty()) {
                // Split by comma and trim spaces, then add to the set
                Arrays.stream(parametersInput.split(","))
                        .map(String::trim)
                        .forEach(parametersSet::add);
            }

            // Call the addMethod method with the LinkedHashSet
            if (umlEditor.addMethod(className, methodName, parametersSet)) {
                outputArea.append("Method '" + methodName + "' with parameters '" + parametersInput
                        + "' added to class '" + className + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated methods
            } else {
                outputArea.append("Failed to add method '" + methodName + "' to class '" + className + "'.\n");
            }

            // Clear the input fields
            addMethodClassField.setText("");
            methodField.setText("");
            parametersField.setText("");
        });

        // Add components to the panel for adding a method
        panel.add(new JLabel("Class Name for Method:"));
        panel.add(addMethodClassField);
        panel.add(new JLabel("Method Name:"));
        panel.add(methodField);
        panel.add(new JLabel("Parameters (comma-separated):")); // Label for parameters
        panel.add(parametersField);
        panel.add(addMethodButton);

        // Delete Method
        JTextField deleteMethodClassField = new JTextField();
        JTextField deleteMethodField = new JTextField();
        JButton deleteMethodButton = new JButton("Delete Method");
        deleteMethodButton.addActionListener(e -> {
            String className = deleteMethodClassField.getText();
            String methodName = deleteMethodField.getText();

            // Create an empty LinkedHashSet for parameters (if needed)
            LinkedHashSet<String> parameters = new LinkedHashSet<>();

            // Call the deleteMethod method with the class name, method name, and parameters
            if (umlEditor.deleteMethod(className, methodName, parameters)) {
                outputArea.append("Method '" + methodName + "' deleted from class '" + className + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated methods
            } else {
                outputArea.append("Failed to delete method '" + methodName + "' from class '" + className + "'.\n");
            }

            // Clear the input fields
            deleteMethodClassField.setText("");
            deleteMethodField.setText("");
        });

        // Add components to the panel for deleting a method
        panel.add(new JLabel("Class Name for Deletion:"));
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

        // Save and Load Functionality
        JButton saveButton = new JButton("Save UML Project");
        saveButton.addActionListener(e -> {
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
        });

        JButton loadButton = new JButton("Load UML Project");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    umlEditor = JsonUtils.load(file.getAbsolutePath());
                    outputArea.append("Project loaded successfully from " + file.getAbsolutePath() + ".\n");
                    drawingPanel.repaint(); // Repaint the panel to show loaded classes and relationships
                } catch (IOException ex) {
                    outputArea.append("Failed to load project: " + ex.getMessage() + "\n");
                }
            }
        });

        // Adding save and load buttons to the panel
        panel.add(saveButton);
        panel.add(loadButton);

        add(panel, BorderLayout.EAST);
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

                // Draw the rectangle for the class
                g.drawRect(position.x, position.y, 100, boxHeight);

                // Draw the class name
                g.drawString(className, position.x + 10, position.y + 20);

                // Draw a line to separate class name and attributes
                g.drawLine(position.x, position.y + 30, position.x + 100, position.y + 30);

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