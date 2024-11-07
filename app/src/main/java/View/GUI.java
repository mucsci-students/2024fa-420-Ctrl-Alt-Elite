package View;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import Controller.UmlGuiController;

public class GUI extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the native look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }

            UmlGuiController gui = new UmlGuiController();
            gui.setVisible(true);
        });
    }
}
