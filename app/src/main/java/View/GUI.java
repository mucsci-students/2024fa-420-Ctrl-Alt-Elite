package View;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Controller.UmlGuiController;

public class GUI extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UmlGuiController gui = new UmlGuiController();
            gui.setVisible(true);
        });
    }
}
