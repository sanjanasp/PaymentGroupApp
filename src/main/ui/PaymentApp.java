package ui;

import model.PaymentGroup;
import model.Person;

import java.util.Scanner;

//runs payment group application
public class PaymentApp {
    private Person alice;
    private Person bob;
    private Person charlie;
    private Scanner input;
    private PaymentGroup group;

    public PaymentApp() {
        runPayment();
    }

    //modifies: this
    //effect: processes user input
    private void runPayment() {
        boolean keepGoing = true;
        String userInput = null;

        init();

        while (keepGoing) {
            displayOptions();
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (userInput.equals("q")) {
                keepGoing = false;
            } else {
                processUserInput(userInput);
            }
        }
        System.out.println("\nThank you!");
    }

    //modifies: this
    //effect: initializes the field
    private void init() {
        alice = new Person("alice", 20, 40);
        bob = new Person("bob", 100, 200);
        charlie = new Person("charlie", 0, 50);
        input = new Scanner(System.in);
        group = new PaymentGroup();
        group.addPeople(alice);
        group.addPeople(bob);
        group.addPeople(charlie);
    }

    //effect: shows the user options to choose from
    private void displayOptions() {
        System.out.println("\nSelect from the following options:");
        System.out.println("\ta -> Add a new person to your payment group");
        System.out.println("\tr -> Show total amount to be received from everyone");
        System.out.println("\tg -> Show total amount to be given to everyone");
        System.out.println("\tp -> Deduct amount you paid to someone");
        System.out.println("\to -> Deduct amount you received from someone");
        System.out.println("\td -> Delete someone");
        System.out.println("\tq -> Quit");
    }

    //modifies: this
    //effect: processes user input
    private void processUserInput(String userInput) {
        if (userInput.equals("a")) {
            doAddition();
        } else if (userInput.equals("r")) {
            showReceivingAmount();
        } else if (userInput.equals("g")) {
            showGivingAmount();
        } else if (userInput.equals("p")) {
            doPayment();
        } else if (userInput.equals("o")) {
            receivePayment();
        } else if (userInput.equals("d")) {
            delete();
        } else {
            System.out.println("Invalid input");
        }
    }

    //modifies: this
    //effect: prompts user for a valid name to be added to the group
    private void doAddition() {
        System.out.println("\nName of person you would like to add: ");
        String name = input.next();

        while (!name.matches("[a-zA-Z]+")) {
            System.out.println("Please enter a valid name!");
            name = input.next();
        }
        name = name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();

        for (Person p : group.getPaymentGroup()) {
            if (name.equals(p.getName().toLowerCase())) {
                System.out.println("Name already exists");
                doAddition();
            }
        }
        getAmounts(name);
    }

    //modifies: this
    //effect: prompts user for valid amounts to be included for the person
    //the user wants to add, creates a new Person in the group and prints everyone
    private void getAmounts(String name) {

        System.out.println("\nHow much does " + name + " owe to you?");
        int amountToTake = input.nextInt();

        while (amountToTake < 0) {
            System.out.println("\nAmount cannot be negative");
            amountToTake = input.nextInt();
        }

        System.out.println("\nHow much do you owe to " + name + " ?");
        int amountToGive = input.nextInt();

        while (amountToGive < 0) {
            System.out.println("\nAmount cannot be negative");
            amountToGive = input.nextInt();
        }

        Person person = new Person(name, amountToGive, amountToTake);
        group.addPeople(person);
        printEveryoneInGroup();

    }

    //effect: shows total amount to be received by the user
    private void showReceivingAmount() {
        int amount = group.totalAmountToBeTaken();
        System.out.println("Total amount you will receive is $" + amount + ".");

    }

    //effect: shows total amount to be given by the user
    private void showGivingAmount() {
        int amount = group.totalAmountToBeGiven();
        System.out.println("Total amount you will give is $" + amount + ".");
    }

    //modifies: this
    //effect: allows user to pay to someone in the group
    private void doPayment() {
        System.out.println("Choose someone to pay:");
        for (Person p : group.getPaymentGroup()) {
            System.out.println(p.getName());
        }
        String name = input.next();
        name = name.toLowerCase();

        for (Person p : group.getPaymentGroup()) {
            if (name.equals(p.getName().toLowerCase())) {
                System.out.println("How much would you like to pay?");
                int amount = input.nextInt();
                if (amount > p.getAmountToGive()) {
                    System.out.println("Amount given is greater than the amount to be returned. Please try again.");
                    amount = input.nextInt();
                }
                p.setAmountToGive(p.getAmountToGive() - amount);
                System.out.println("You now owe " + name + " $" + p.getAmountToGive());
            }
        }
    }

    //modifies: this
    //effect: allows user to receive payment from someone
    private void receivePayment() {
        System.out.println("Choose someone to receive money from:");
        for (Person p : group.getPaymentGroup()) {
            System.out.println(p.getName());
        }
        String name = input.next();
        name = name.toLowerCase();

        for (Person p : group.getPaymentGroup()) {
            if (name.equals(p.getName().toLowerCase())) {
                System.out.println("How much are you receiving?");
                int amount = input.nextInt();
                if (amount > p.getAmountToTake()) {
                    System.out.println("Amount inputted is greater than the amount to be taken. Please try again.");
                    amount = input.nextInt();
                }
                p.setAmountToTake(p.getAmountToTake() - amount);
                System.out.println(name + " now owes you " + " $" + p.getAmountToTake());
            }
        }

    }

    //modifies: this
    //effect: allows user to remove someone from the group
    private void delete() {
        System.out.println("Choose someone to remove:");
        for (Person p : group.getPaymentGroup()) {
            System.out.println(p.getName());
        }
        String name = input.next();
        name = name.toLowerCase();

        Person personToRemove = null;
        for (Person p : group.getPaymentGroup()) {
            if (p.getName().equals(name)) {
                personToRemove = p;
                break;
            }
        }
        if (personToRemove != null) {
            group.getPaymentGroup().remove(personToRemove);
        }

        printEveryoneInGroup();
    }

    //effect: prints people who are in the group
    private void printEveryoneInGroup() {
        System.out.println("The people in your group are:");
        for (Person p : group.getPaymentGroup()) {
            System.out.println(p.getName());
        }
    }
}
