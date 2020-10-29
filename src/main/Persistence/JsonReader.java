package Persistence;

import model.PaymentGroup;
import model.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
// Modeled from sample application provided by CPSC 210
// Represents a reader that reads payment group from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: makes a JsonReader constructor
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads payment group from file;
    // throws IOException if an error occurs reading data from file
    public PaymentGroup read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePaymentGroup(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses payment group from JSON object and returns it
    private PaymentGroup parsePaymentGroup(JSONObject jsonObject) {
        PaymentGroup pg = new PaymentGroup();
        addPeople(pg, jsonObject);
        return pg;
    }

    // MODIFIES: pg
    // EFFECTS: adds people to payment group from object
    private void addPeople(PaymentGroup pg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("People");
        for (Object json : jsonArray) {
            JSONObject nextPerson = (JSONObject) json;
            addPerson(pg, nextPerson);
        }
    }

    // MODIFIES: pg
    // EFFECTS: adds person to payment group from object
    private void addPerson(PaymentGroup pg, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        int amountToBeGiven = jsonObject.getInt("AmountToBeGiven");
        int amountToBeTaken = jsonObject.getInt("AmountToBeReceived");
        Person person = new Person(name, amountToBeGiven, amountToBeTaken);
        pg.addPeople(person);
    }
}



