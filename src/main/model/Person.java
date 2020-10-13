package model;


public class Person {
    private String name;             //name of the person
    private int amountToGive;        //how much amount(in dollars) this person has to give to me
    private int amountToTake;        //how much amount(in dollars) I have to take from this person

    public Person(String name, int amountGive, int amountTake) {
        this.name = name;
        this.amountToGive = amountGive;
        this.amountToTake = amountTake;
    }

    public String getName() {
        return name;
    }

    public void setAmountToGive(int amountToGive) {
        this.amountToGive = amountToGive;
    }

    public void setAmountToTake(int amountToTake) {
        this.amountToTake = amountToTake;
    }

    public int getAmountToGive() {
        return amountToGive;
    }

    public int getAmountToTake() {
        return amountToTake;
    }
}
