import View.CLI;
import View.GUI;

public class ApplicationLauncher {
    public static void main(String[] args) {
        // Check for the "--cli" command-line parameter
        if (args.length > 0 && args[0].equals("--cli")) {
            CLI.main(args); // Launch CLI
        } else {
            GUI.main(args); // Launch GUI by default
        }
    }
}