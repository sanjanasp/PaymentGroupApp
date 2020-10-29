package model;
import Persistence.JsonReader;
import Persistence.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
// Modeled from sample application provided by CPSC 210

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PaymentGroup pg = new PaymentGroup();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.openWriter();
            fail("IOException should have been caught");
        } catch (IOException e) {
        }
    }
    @Test
    void testWriterWhenPaymentGroupIsEmpty() {
        try {
            PaymentGroup pg = new PaymentGroup();
            JsonWriter writer = new JsonWriter("./data/testWriterWhenPaymentGroupIsEmpty.json");
            writer.openWriter();
            writer.writeFile(pg);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterWhenPaymentGroupIsEmpty.json");
            pg = reader.read();
            assertEquals(0, pg.getPaymentGroup().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterWhenPaymentGroupIsNotEmpty(){
        try {
            PaymentGroup pg = new PaymentGroup();
            pg.addPeople(new Person("bob",20,20));
            pg.addPeople(new Person("charlie",20,30));
            JsonWriter writer = new JsonWriter("./data/testWriterWhenPaymentGroupIsNotEmpty.json");
            writer.openWriter();
            writer.writeFile(pg);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterWhenPaymentGroupIsNotEmpty.json");
            pg = reader.read();
            List<Person> people = pg.getPaymentGroup();
            assertEquals(2, people.size());
            assertEquals("bob", people.get(0).getName());
            assertEquals(20,people.get(0).getAmountToGive());
            assertEquals(20,people.get(0).getAmountToTake());

            assertEquals("charlie", people.get(1).getName());
            assertEquals(20,people.get(1).getAmountToGive());
            assertEquals(30,people.get(1).getAmountToTake());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }
}
