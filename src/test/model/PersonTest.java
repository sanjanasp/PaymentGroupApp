package model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

    private Person bob;

    @BeforeEach
    public void runBefore(){
        bob = new Person("Bob",50,40);

    }

    @Test
    public void testGetAmountToGive(){
        assertEquals(50, bob.getAmountToGive());
    }
    @Test
    public void testGetAmountToTake(){
        assertEquals(40, bob.getAmountToTake());
    }
    @Test
    public void testSetAmountToTake(){
        bob.setAmountToTake(20);
        assertEquals(20, bob.getAmountToTake());
    }
    @Test
    public void testSetAmountToGive(){
       bob.setAmountToGive(30);
       assertEquals(30, bob.getAmountToGive());

    }

    @Test
    public void testGetName(){
        assertEquals("Bob",bob.getName());
    }

    @Test
    public void testEquality() {
        Person alice = new Person("Alice", 10, 10);
        assertNotEquals(bob, alice);
        assertNotEquals(bob, null);
        assertNotEquals(alice.hashCode(), bob.hashCode());
    }

}
