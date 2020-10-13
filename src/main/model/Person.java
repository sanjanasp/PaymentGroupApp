package model;

// Person is someone who will be added to the payment group. Person has a name,
// amount they owe to the user and amount they will receive from the user.
// Amount is dollars in whole number.
public class Person {
    private String name;             //name of the person
    private int amountToGive;        //how much amount(in dollars) this person has to give to me
    private int amountToTake;        //how much amount(in dollars) I have to take from this person

    //effect: sets up Person constructor, amountGive and amountTake are
    // positive integers assigned to person.
    public Person(String name, int amountGive, int amountTake) {
        this.name = name;
        this.amountToGive = amountGive;
        this.amountToTake = amountTake;
    }

    //effect: returns name of Person
    public String getName() {
        return this.name;
    }

    //modifies: this
    //effect: sets the amount to be given to this person
    public void setAmountToGive(int amountToGive) {
        this.amountToGive = amountToGive;
    }

    //modifies: this
    //effect: sets the amount to be taken from this person
    public void setAmountToTake(int amountToTake) {
        this.amountToTake = amountToTake;
    }

    //effect: returns the amount to be given to this person
    public int getAmountToGive() {
        return amountToGive;
    }

    //effect: returns the amount to be taken from this person
    public int getAmountToTake() {
        return amountToTake;
    }
}
