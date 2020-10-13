package ui;

import model.PaymentGroup;
import model.Person;

import java.util.Scanner;

public class PaymentApp {
    private Person alice;
    private Person bob;
    private Person charlie;
    private Scanner input;
    private PaymentGroup group;

    public PaymentApp() {
        runPayment();
    }

    private void runPayment() {
        boolean keepGoing = true;
        String userInput = null;

        init(); //creates new people for group

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

    private void displayOptions() {
        System.out.println("\nSelect from the following options:");
        System.out.println("\ta -> Add a new person to your payment group");
        System.out.println("\tr -> Show total amount to be received from everyone");
        System.out.println("\tg -> Show total amount to be given to everyone");
        System.out.println("\tp -> Pay someone");
        System.out.println("\to -> Receive money from someone");
        System.out.println("\td -> Delete someone");
        System.out.println("\tq -> Quit");
    }

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
    }

    private void showReceivingAmount() {
        int amount = group.totalAmountToBeTaken();
        System.out.println("Total amount you will receive is $" + amount + ".");

    }

    private void showGivingAmount() {
        int amount = group.totalAmountToBeGiven();
        System.out.println("Total amount you will give is $" + amount + ".");
    }

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

        System.out.println("The people in your group are:");
        for (Person p : group.getPaymentGroup()) {
            System.out.println(p.getName());
        }
    }
}
