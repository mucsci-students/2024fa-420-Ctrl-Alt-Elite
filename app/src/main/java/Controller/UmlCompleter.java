package Controller;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class UmlCompleter {
    private static final String[] VALID_COMMANDS = {
            "help", "add-class", "delete-class", "rename-class", "add-method", 
            "delete-method", "rename-method", "add-field", "delete-field", 
            "rename-field", "add-relationship", "delete-relationship", "list", 
            "undo", "redo", "exit"
    };

    private Completer completer;

    public UmlCompleter() {
        // Initialize the completer with the list of valid commands
        this.completer = new StringsCompleter(VALID_COMMANDS);
    }

    public LineReader setupLineReader() throws IOException {
        Terminal terminal = TerminalBuilder.builder().build();
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(this.completer)
                .build();
    
        // Set the 'completion' configuration to avoid asking the user for confirmation
        lineReader.setVariable(LineReader.LIST_MAX, 50); // You can adjust 50 to your preference
    
        return lineReader;
    }
}