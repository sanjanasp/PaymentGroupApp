package model;

import persistance.Writeable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

// PaymentGroup is a group of people who owe money and/or who will get money from the user.
public class PaymentGroup implements Writeable {

    private List<Person> paymentGroup;

    //effect: constructs payment group
    public PaymentGroup() {
        paymentGroup = new LinkedList<>();
    }

    //modifies: this
    //effect: adds people to this payment group
    public void addPeople(Person p) {
        paymentGroup.add(p);
    }

    //effect: returns the total amount to be received by the user
    public int totalAmountToBeTaken() {
        int count = 0;
        for (Person p : paymentGroup) {
            count = count + p.getAmountToTake();
        }
        return count;
    }

    //effect: returns the total amount to be given by the user
    public int totalAmountToBeGiven() {
        int count = 0;
        for (Person p : paymentGroup) {
            count = count + p.getAmountToGive();
        }
        return count;
    }

    //modifies: this
    //effect: deducts amount that is paid by the user to p
    // throws InvalidAmountException if the more is paid than is supposed to.
    public void deductAmountThatIsGiven(Person p, int amountGiven) throws InvalidAmountException {
        if ((p.getAmountToGive() - amountGiven) < 0) {
            throw new InvalidAmountException();
        }
        p.setAmountToGive(p.getAmountToGive() - amountGiven);
    }


    //modifies: this
    //effect: deducts amount that is taken by the user from p
    // throws InvalidAmountException if the more is received than is supposed to.
    public void deductAmountThatIsTaken(Person p, int amountTaken) throws InvalidAmountException {
        if ((p.getAmountToTake() - amountTaken) < 0) {
            throw new InvalidAmountException();
        }
        p.setAmountToTake(p.getAmountToTake() - amountTaken);
    }


    //modifies: this
    //effect: removes p from the group
    public void removePerson(Person p) {
        if (getPaymentGroup().contains(p)) {
            paymentGroup.remove(p);
        }
    }

    //effect: returns size of the group
    public int sizeOfGroup() {
        int count = 0;
        for (Person p : paymentGroup) {
            count = count + 1;
        }
        return count;
    }

    //effect: returns the payment group
    public List<Person> getPaymentGroup() {
        return paymentGroup;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("People", peopleToJson());
        return json;
    }


    private JSONArray peopleToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Person p : paymentGroup) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
