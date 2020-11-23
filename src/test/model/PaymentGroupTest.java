package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



public class PaymentGroupTest {
    private PaymentGroup group;
    private Person alice;
    private Person bob;
    private Person charlie;


    @BeforeEach
    public void runBefore(){
        group = new PaymentGroup();
        alice = new Person("Alice", 20,40);
        bob = new Person("Bob", 100,200);
        charlie = new Person("Charlie", 10,50);

    }

    @Test
    public void testAddPeople(){
        group.addPeople(alice);
        group.addPeople(bob);
        group.addPeople(charlie);

        assertEquals(3,group.sizeOfGroup());

    }

    @Test
    public void testTotalAmountToBeTaken(){
        group.addPeople(alice);
        group.addPeople(bob);
        group.addPeople(charlie);

        assertEquals(290,group.totalAmountToBeTaken());
    }

    @Test
    public void testTotalAmountToBeGiven(){
        group.addPeople(alice);
        group.addPeople(bob);
        group.addPeople(charlie);

        assertEquals(130,group.totalAmountToBeGiven());
    }

    @Test
    public void testDeductAmountThatIsGivenWithoutException() {
        group.addPeople(alice);
        try {
            group.deductAmountThatIsGiven(alice, 10);
            assertEquals(10, alice.getAmountToGive());
        } catch (InvalidAmountException e) {
            fail("Exception should be thrown");
        }
    }
    @Test
    public void testDeductAmountThatIsGivenWithException(){
        group.addPeople(alice);
        try {
            group.deductAmountThatIsGiven(alice, 100);
            assertEquals(20, alice.getAmountToGive());
        } catch (InvalidAmountException e){
            System.out.println("exception caught");
        }
    }

    @Test
    public void testDeductAmountThatIsTakenWithoutException() {
        group.addPeople(charlie);
        try {
            group.deductAmountThatIsTaken(charlie, 20);
            assertEquals(30, charlie.getAmountToTake());
        } catch (InvalidAmountException e) {
            fail("exception should be thrown");
        }
    }

    @Test
    public void testDeductAmountThatIsTakenWithException(){
        group.addPeople(charlie);
        try {
            group.deductAmountThatIsTaken(charlie,250);
            assertEquals(50,charlie.getAmountToTake());
        } catch (InvalidAmountException e) {
            System.out.println("exception caught");
        }
    }

    @Test
    public void testRemovePerson(){
        group.addPeople(alice);
        group.addPeople(charlie);
        group.addPeople(bob);

        assertEquals(3,group.sizeOfGroup());
        group.removePerson(bob);
        group.removePerson(alice);

        assertEquals(1,group.sizeOfGroup());

    }

    @Test
    public void testSizeOfGroup(){
        assertEquals(0,group.sizeOfGroup());
        group.addPeople(alice);
        group.addPeople(charlie);

        assertEquals(2, group.sizeOfGroup());
    }

    @Test
    public void paymentGroupTest(){
        group.addPeople(alice);
        group.addPeople(charlie);

        group.getPaymentGroup().contains(alice);

        group.getPaymentGroup().contains(charlie);
    }

}
