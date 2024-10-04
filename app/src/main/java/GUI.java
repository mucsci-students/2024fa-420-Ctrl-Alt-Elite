import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GUI extends JFrame {
    private UmlEditor umlEditor;
    private JTextArea outputArea;
    private DrawingPanel drawingPanel;  // Panel for drawing


    // To keep track of class positions
    private Map<String, Point> classPositions;


    public GUI() {
        umlEditor = new UmlEditor();
        classPositions = new HashMap<>();  // Initialize class positions
        setTitle("UML Editor");
        setSize(800, 400);  // Increased width to accommodate drawing panel
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.WEST);  // Moved to the left


        drawingPanel = new DrawingPanel();  // Create the drawing panel
        add(drawingPanel, BorderLayout.CENTER);  // Add it to the center


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));


        // Add Class
        JTextField classNameField = new JTextField();
        JButton addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(e -> {
            String className = classNameField.getText();
            if (umlEditor.addClass(className)) {
                outputArea.append("Class '" + className + "' added.\n");
                addClassRectangle(className);  // Draw rectangle for the new class
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
                removeClassRectangle(className);  // Remove the rectangle for the deleted class
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
                renameClassRectangle(oldName, newName);  // Rename the rectangle
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
                drawingPanel.repaint();  // Repaint to show the updated attributes
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
                drawingPanel.repaint();  // Repaint to show the updated attributes
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
    String source = sourceClassField.getText();
    String destination = destinationClassField.getText();
    if (umlEditor.addRelationship(source, destination)) {
        outputArea.append("Relationship added from '" + source + "' to '" + destination + "'.\n");
        drawingPanel.repaint();  // Repaint to show the updated relationships
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
                if (Math.abs(point.x - x) < 120 && Math.abs(point.y - y) < 70) { // Adjust distance to your rectangle size
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
    
        classPositions.put(className, new Point(x, y));  // Store position
        drawingPanel.repaint();  // Repaint the drawing panel to show the new rectangle
    }


    private void removeClassRectangle(String className) {
        classPositions.remove(className);  // Remove the class position
        drawingPanel.repaint();  // Repaint to reflect changes
    }


    private void renameClassRectangle(String oldName, String newName) {
        Point position = classPositions.remove(oldName);  // Remove old entry
        if (position != null) {
            classPositions.put(newName, position);  // Add new entry with same position
            drawingPanel.repaint();  // Repaint to show changes
        }
    }


    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            // Draw rectangles for each class
            for (Map.Entry<String, Point> entry : classPositions.entrySet()) {
                String className = entry.getKey();
                Point position = entry.getValue();
    
                // Draw rectangle with a section for the class name and another for attributes
                int rectWidth = 100;
                int rectHeight = 80;
                int classNameSectionHeight = 30;  // Height for class name section
    
                // Draw the outer rectangle
                g.drawRect(position.x, position.y, rectWidth, rectHeight);
    
                // Draw the divider line between class name and attributes section
                g.drawLine(position.x, position.y + classNameSectionHeight, position.x + rectWidth, position.y + classNameSectionHeight);
    
                // Draw class name in the top section
                g.drawString(className, position.x + 10, position.y + 20);  // Centered vertically in the class name section
    
                // Draw attributes in the lower section
                UmlClass umlClass = umlEditor.getClass(className);
                if (umlClass != null) {
                    int attributeY = position.y + classNameSectionHeight + 20;  // Start position for attributes
                    for (String attribute : umlClass.getAttributes()) {
                        g.drawString(attribute, position.x + 10, attributeY);  // Draw each attribute
                        attributeY += 15;  // Increment Y position for next attribute
                    }
                }
            }
    
            // Draw relationships
            for (UmlRelationship relationship : umlEditor.getRelationships()) {
                Point source = classPositions.get(relationship.getSource());
                Point destination = classPositions.get(relationship.getDestination());
                if (source != null && destination != null) {
                    // Calculate the Y positions for the source and destination
                    int sourceY = source.y + 40;  // Middle of the source rectangle (adjusted for the divided sections)
                    int destinationY = destination.y + 40;  // Middle of the destination rectangle
    
                    // Draw a line between the source and destination
                    g.drawLine(source.x + 50, sourceY, destination.x + 50, destinationY);
                }
            }
        }
    }
    
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}