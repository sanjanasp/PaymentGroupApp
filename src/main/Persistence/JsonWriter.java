package Persistence;

import model.PaymentGroup;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes payment group to Json file
// Modeled from sample application provided by CPSC 210
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String location;

    // EFFECTS: constructs JsonWriter
    public JsonWriter(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: opens writer;
    // throws FileNotFoundException if location file cannot be found
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(location));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of payment group to file
    public void writeFile(PaymentGroup pg) {
        JSONObject json = pg.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
