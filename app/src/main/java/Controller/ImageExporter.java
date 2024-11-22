package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageExporter {

    // Method to capture a JPanel as an image and export it to a file
    public static void exportPanelAsImage(JPanel panel, String fileName) {
        // Create a BufferedImage object to hold the image data
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Paint the panel's content into the image
        Graphics2D graphics = image.createGraphics();
        panel.paint(graphics);
        graphics.dispose();

        try {
            // Save the image to a file
            ImageIO.write(image, "png", new File(fileName)); // You can change the format (e.g., "jpeg")
            System.out.println("Image exported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error exporting image.");
        }
    }
}
