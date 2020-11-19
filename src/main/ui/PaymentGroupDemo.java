package ui;

import model.PaymentGroup;
import model.Person;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.ActionListener;
import java.util.Vector;

//Creates a GUI for PaymentGroup using JTable. User can add, delete, save and load people
//from their group.
public class PaymentGroupDemo extends JFrame {
    private JTable table;
    private JFrame frame;
    private JTextField textName;
    private JTextField textAmountGive;
    private JTextField textAmountTake;
    private JButton addButton;
    private DefaultTableModel model;
    private JButton deleteButton;
    private PaymentGroup paymentGroup;
    private JButton saveButton;
    private JButton loadButton;

    // store
    private static final String JSON_FILE = "./data/paymentgroup.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //effect: initializes the field
    public PaymentGroupDemo() throws IOException {
        table = new JTable();
        frame = new JFrame("My Payment Group");
        textName = new JTextField();
        textAmountGive = new JTextField();
        textAmountTake = new JTextField();
        addButton = new JButton("Add");
        model = new DefaultTableModel();
        deleteButton = new JButton("Delete");

        paymentGroup = new PaymentGroup();

        saveButton = new JButton("Save Table");
        loadButton = new JButton("Load Table");

        jsonReader = new JsonReader(JSON_FILE);
        jsonWriter = new JsonWriter(JSON_FILE);

        setWindowFrame();
        makeTable();
    }


    //effect: sets up the window for GUI
    public void setWindowFrame() throws IOException {

        BufferedImage bf = ImageIO.read(new File("./data/backgroundPic.jpg"));
        frame.setContentPane(new ImagePanel(bf));

        textName.setBounds(20, 220, 100, 25);
        textAmountGive.setBounds(20, 250, 100, 25);
        textAmountTake.setBounds(20, 280, 100, 25);
        addButton.setBounds(150, 230, 100, 25);
        deleteButton.setBounds(150, 265, 100, 25);

        frame.setSize(880, 600);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        frame.add(textName);
        frame.add(textAmountGive);
        frame.add(textAmountTake);

        frame.add(addButton);
        addButton.setEnabled(false);
        frame.add(deleteButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //effect: saves the payment group table
    private void save() {
        try {
            jsonWriter.openWriter();
            jsonWriter.writeFile(paymentGroup);
            jsonWriter.closeWriter();
            System.out.println("Saved to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE);
        }
    }

    //effect: loads the payment group table
    private void load() {
        try {

            for (int i = paymentGroup.getPaymentGroup().size() - 1; i >= 0; i--) {
                model.removeRow(i);
            }

            paymentGroup = jsonReader.read();
            System.out.println("Loaded from " + JSON_FILE);

            for (Person p : paymentGroup.getPaymentGroup()) {
                String[] row = new String[3];
                row[0] = p.getName();
                row[1] = Integer.toString(p.getAmountToGive());
                row[2] = Integer.toString(p.getAmountToTake());
                model.addRow(row);
            }


        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_FILE);
        }
    }

    //modifies: this
    //effect: makes a table with person name and amounts.
    //performs action listener on save and load buttons.
    public void makeTable() {
        Object[] columns = {"Name", "Amount to give", "Amount to take"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 880, 200);
        frame.add(scrollPane);

        disableNameField();

        loadButton.setBounds(400, 230, 100, 25);
        saveButton.setBounds(400, 260, 100, 25);
        frame.add(saveButton);
        frame.add(loadButton);


        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
                paymentGroup.toJson();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
    }


    //effect: disables textField for name when all entries are not inputted
    public void disableNameField() {
        textName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                addButton.setEnabled(!textName.getText().isEmpty() && !textAmountGive.getText().isEmpty()
                        && !textAmountTake.getText().isEmpty());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                addButton.setEnabled(!textName.getText().isEmpty() && !textAmountGive.getText().isEmpty()
                        && !textAmountTake.getText().isEmpty());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        disableGiveAmountField();
    }

    //effect: disables textField for given amount when other entries are not inputted
    public void disableGiveAmountField() {

        textAmountGive.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                addButton.setEnabled(!textName.getText().isEmpty() && !textAmountGive.getText().isEmpty()
                        && !textAmountTake.getText().isEmpty());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                addButton.setEnabled(!textName.getText().isEmpty() && !textAmountGive.getText().isEmpty()
                        && !textAmountTake.getText().isEmpty());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        disableTakeAmountField();
    }

    //effect: disables textField for taken amount when other entries are not inputted
    public void disableTakeAmountField() {
        textAmountTake.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                addButton.setEnabled(!textName.getText().isEmpty() && !textAmountGive.getText().isEmpty()
                        && !textAmountTake.getText().isEmpty());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                addButton.setEnabled(!textName.getText().isEmpty() && !textAmountGive.getText().isEmpty()
                        && !textAmountTake.getText().isEmpty());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        buttonsForAdd();
    }

    //modifies: this
    //effect: creates an ActionEvent for add buttons.
    // makes person from given inputs and adds to payment group
    public void buttonsForAdd() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] row = new String[3];
                row[0] = textName.getText();
                row[1] = textAmountGive.getText();
                row[2] = textAmountTake.getText();

                Person curPerson = new Person(row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2]));
                paymentGroup.addPeople(curPerson);

                model.addRow(row);
                textName.setText("");
                textAmountGive.setText("");
                textAmountTake.setText("");
            }
        });
        buttonForDelete();
    }

    //modifies: this
    //effect: creates an ActionEvent for delete button and deletes person
    //from payment group.
    public void buttonForDelete() {

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = table.getSelectedRow();
                if (k >= 0) {
                    Person curPerson = new Person((String) table.getValueAt(k, 0),
                            Integer.parseInt((String) table.getValueAt(k, 1)),
                            Integer.parseInt((String) table.getValueAt(k, 2)));

                    model.removeRow(k);
                    paymentGroup.removePerson(curPerson);
                }
            }
        });

    }

    //effect: runs  PaymentGroupDemo
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new PaymentGroupDemo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

