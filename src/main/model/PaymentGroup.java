package model;

import java.util.HashSet;
import java.util.Set;

//PaymentGroup is a group of people who owe money and/or who will get money.

public class PaymentGroup {

    private Set<Person> paymentGroup;

    public PaymentGroup() {
        paymentGroup = new HashSet<>();
    }

    public void addPeople(Person p) {
        paymentGroup.add(p);
    }

    public int totalAmountToBeTaken() {
        int count = 0;
        for (Person p : paymentGroup) {
            count = count + p.getAmountToTake();
        }
        return count;
    }

    public int totalAmountToBeGiven() {
        int count = 0;
        for (Person p : paymentGroup) {
            count = count + p.getAmountToGive();
        }
        return count;
    }

    public void deductAmountThatIsGiven(Person p, int amountGiven) {
        p.setAmountToGive(p.getAmountToGive() - amountGiven);
    }

    public void deductAmountThatIsTaken(Person p, int amountTaken) {
        p.setAmountToTake(p.getAmountToTake() - amountTaken);
    }

    public void removePerson(Person p) {
        paymentGroup.remove(p);
    }

    public int sizeOfGroup() {
        int count = 0;
        for (Person p : paymentGroup) {
            count = count + 1;
        }
        return count;
    }

    public Set<Person> getPaymentGroup() {
        return paymentGroup;
    }


}
