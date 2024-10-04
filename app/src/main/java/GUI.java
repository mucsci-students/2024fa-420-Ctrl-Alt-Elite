import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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

        // Add Attribute
        JTextField addAttributeClassField = new JTextField();
        JTextField attributeField = new JTextField();
        JButton addAttributeButton = new JButton("Add Attribute");
        addAttributeButton.addActionListener(e -> {
            String className = addAttributeClassField.getText();
            String attribute = attributeField.getText();
            if (umlEditor.addAttribute(className, attribute)) {
                outputArea.append("Attribute '" + attribute + "' added to class '" + className + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated attributes
            } else {
                outputArea.append("Failed to add attribute '" + attribute + "' to class '" + className + "'.\n");
            }
            addAttributeClassField.setText("");
            attributeField.setText("");
        });

        panel.add(new JLabel("Class Name for Attribute:"));
        panel.add(addAttributeClassField);
        panel.add(new JLabel("Attribute Name:"));
        panel.add(attributeField);
        panel.add(addAttributeButton);

        // Delete Attribute
        JTextField deleteAttributeClassField = new JTextField();
        JTextField deleteAttributeField = new JTextField();
        JButton deleteAttributeButton = new JButton("Delete Attribute");
        deleteAttributeButton.addActionListener(e -> {
            String className = deleteAttributeClassField.getText();
            String attribute = deleteAttributeField.getText();
            if (umlEditor.deleteAttribute(className, attribute)) {
                outputArea.append("Attribute '" + attribute + "' deleted from class '" + className + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated attributes
            } else {
                outputArea.append("Failed to delete attribute '" + attribute + "' from class '" + className + "'.\n");
            }
            deleteAttributeClassField.setText("");
            deleteAttributeField.setText("");
        });

        panel.add(new JLabel("Class Name for Attribute to Delete:"));
        panel.add(deleteAttributeClassField);
        panel.add(new JLabel("Attribute Name to Delete:"));
        panel.add(deleteAttributeField);
        panel.add(deleteAttributeButton);

        // Add Relationship
        JTextField sourceClassField = new JTextField();
        JTextField destinationClassField = new JTextField();
        JButton addRelationshipButton = new JButton("Add Relationship");

        addRelationshipButton.addActionListener(e -> {
            String source = sourceClassField.getText().trim(); // Trim whitespace
            String destination = destinationClassField.getText().trim(); // Trim whitespace

            if (source.isEmpty() || destination.isEmpty()) {
                outputArea.append("Source and destination class names cannot be empty.\n");
                return; // Exit if either field is empty
            }

            if (umlEditor.addRelationship(source, destination)) {
                outputArea.append("Relationship added from '" + source + "' to '" + destination + "'.\n");
                drawingPanel.repaint(); // Repaint to show the updated relationships
            } else {
                outputArea.append("Failed to add relationship from '" + source + "' to '" + destination + "'.\n");
            }
            sourceClassField.setText("");
            destinationClassField.setText("");
        });

        panel.add(new JLabel("Source Class Name:"));
        panel.add(sourceClassField);
        panel.add(new JLabel("Destination Class Name:"));
        panel.add(destinationClassField);
        panel.add(addRelationshipButton);

        // Delete Relationship
        JTextField deleteSourceClassField = new JTextField();
        JTextField deleteDestinationClassField = new JTextField();
        JButton deleteRelationshipButton = new JButton("Delete Relationship");
        deleteRelationshipButton.addActionListener(e -> {
            String source = deleteSourceClassField.getText();
            String destination = deleteDestinationClassField.getText();
            if (umlEditor.deleteRelationship(source, destination)) {
                outputArea.append("Relationship deleted from '" + source + "' to '" + destination + "'.\n");
            } else {
                outputArea.append("Failed to delete relationship from '" + source + "' to '" + destination + "'.\n");
            }
            deleteSourceClassField.setText("");
            deleteDestinationClassField.setText("");
        });

        panel.add(new JLabel("Source Class Name to Delete:"));
        panel.add(deleteSourceClassField);
        panel.add(new JLabel("Destination Class Name to Delete:"));
        panel.add(deleteDestinationClassField);
        panel.add(deleteRelationshipButton);

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
                                50 + (umlEditor.getClass(className).getAttributes().size() * 15));
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

            // Draw relationships
            for (UmlRelationship relationship : umlEditor.getRelationships()) {
                Point sourcePosition = classPositions.get(relationship.getSource());
                Point destinationPosition = classPositions.get(relationship.getDestination());

                if (sourcePosition != null && destinationPosition != null) {
                    // Draw a line from source to destination
                    g.drawLine(sourcePosition.x + 50, sourcePosition.y + 25,
                            destinationPosition.x + 50,
                            destinationPosition.y + getBoxHeight(relationship.getDestination()));

                    // Draw an arrowhead at the destination
                    drawArrow(g, destinationPosition.x + 50,
                            destinationPosition.y + getBoxHeight(relationship.getDestination()));
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
                    for (String attribute : umlClass.getAttributes()) {
                        g.drawString(attribute, position.x + 10, attributeY);
                        attributeY += 15; // Move down for the next attribute
                    }
                }
            }
        }

        // Method to draw an arrowhead
        private void drawArrow(Graphics g, int x, int y) {
            int arrowSize = 10; // Size of the arrowhead
            int[] xPoints = { x, x - arrowSize, x + arrowSize };
            int[] yPoints = { y, y - arrowSize, y - arrowSize };
            g.fillPolygon(xPoints, yPoints, 3); // Draw filled polygon for arrowhead
        }

        // Helper method to get the box height based on the class name
        private int getBoxHeight(String className) {
            UmlClass umlClass = umlEditor.getClass(className);
            int attributeCount = (umlClass != null) ? umlClass.getAttributes().size() : 0;
            return 50 + (attributeCount * 15); // Base height + dynamic attribute height
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}