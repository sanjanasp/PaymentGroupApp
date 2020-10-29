package model;

import persistance.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// Modeled from sample application provided by CPSC 210
public class JsonReaderTest {

    @Test
    void testReaderWhenFileDoesNotExist() {
        JsonReader reader = new JsonReader("./data/FileDoesNotExist.json");
        try {
            PaymentGroup pg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderWhenPaymentGroupIsEmpty() {
        JsonReader reader = new JsonReader("./data/testReaderWhenPaymentGroupIsEmpty.json");
        try {
            PaymentGroup pg = reader.read();
            assertEquals(0, pg.getPaymentGroup().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWhenPaymentGroupIsNotEmpty() {
        JsonReader reader = new JsonReader("./data/testWhenPaymentGroupIsNotEmpty.json");
        try {
            PaymentGroup pg = reader.read();
            List<Person> people = pg.getPaymentGroup();
            assertEquals(2, people.size());
            assertEquals("bob", people.get(0).getName());
            assertEquals(20,people.get(0).getAmountToGive());
            assertEquals(20,people.get(0).getAmountToTake());

            assertEquals("charlie", people.get(1).getName());
            assertEquals(20,people.get(1).getAmountToGive());
            assertEquals(30,people.get(1).getAmountToTake());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
