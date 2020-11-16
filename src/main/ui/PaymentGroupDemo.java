package ui;

import model.PaymentGroup;
import model.Person;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.text.JTextComponent;

//Creates a GUI for PaymentGroup using JTable. User can add and delete people
//from their group.
public class PaymentGroupDemo extends JPanel {
    private JTable table;
    private JFrame frame;
    private JTextField textName;
    private JTextField textAmountGive;
    private JTextField textAmountTake;
    private JButton addButton;
    private DefaultTableModel model;
    private JButton deleteButton;
    final String filename = "text.out";
    final JTextField textField = new JTextField();
    private Person p1;
    private Person p2;
    private PaymentGroup paymentGroup;
    private JButton jbtSave;
    private JButton jbtLoad;

    //effect: initializes the field
    public PaymentGroupDemo() {
        table = new JTable();
        frame = new JFrame("My Payment Group");
        textName = new JTextField();
        textAmountGive = new JTextField();
        textAmountTake = new JTextField();
        addButton = new JButton("Add");
        model = new DefaultTableModel();
        deleteButton = new JButton("Delete");
        p1 = new Person("Charlie", 20, 30);
        p2 = new Person("Hanna", 50, 100);
        jbtSave = new JButton("Save Table");
        jbtLoad = new JButton("Load Table");

        setWindow();
    }

    //modifies: this
    //effect: sets up the window for GUI
    public void setWindow() {
        textName.setBounds(20, 220, 100, 25);

        textAmountGive.setBounds(20, 250, 100, 25);
        textAmountTake.setBounds(20, 280, 100, 25);

        addButton.setBounds(150, 230, 100, 25);
        deleteButton.setBounds(150, 265, 100, 25);

        frame.setSize(880, 400);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        frame.add(textName);
        frame.add(textAmountGive);
        frame.add(textAmountTake);

        frame.add(addButton);
        addButton.setEnabled(false);
        frame.add(deleteButton);

        makeTable();
    }

    //modifies: this
    //effect: makes a table with person name and amounts.
    public void makeTable() {
        Object[] columns = {"Name", "Amount to give", "Amount to take"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        model.addRow(new Object[]{p1.getName(), p1.getAmountToGive(), p1.getAmountToTake()});
        model.addRow(new Object[]{p2.getName(), p2.getAmountToGive(), p2.getAmountToTake()});


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 880, 200);
        frame.add(scrollPane);

        disableNameField();

        jbtLoad.setBounds(400, 230, 100, 25);
        jbtSave.setBounds(400, 260, 100, 25);
        frame.add(jbtSave);
        frame.add(jbtLoad);


        jbtSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveTable();
            }
        });

        jbtLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadTable();
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
        buttonsForAddAndDelete();
    }

    //effect: creates an ActionEvent for add and delete buttons.
    public void buttonsForAddAndDelete() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] row = new String[3];
                row[0] = textName.getText();
                row[1] = textAmountGive.getText();
                row[2] = textAmountTake.getText();

//                int row1 = Integer.parseInt(row[1]);
//                int row2 = Integer.parseInt(row[2]);

                model.addRow(row);
                textName.setText("");
                textAmountGive.setText("");
                textAmountTake.setText("");
                // int n = model.getRowCount();
//                for (int i = 0; i < n; i++) {
//                    paymentGroup.addPeople(new Person(row[0], row1, row2));
//                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k = table.getSelectedRow();
                if (k >= 0) {
                    model.removeRow(k);
                }
            }
        });

    }

    private JFileChooser myJFileChooser = new JFileChooser(new File(filename));

    private void saveTable() {
        if (myJFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            saveTable(myJFileChooser.getSelectedFile());
        }
    }

    private void saveTable(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file));
            out.writeObject(model.getDataVector());
            out.writeObject(getColumnNames());
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<String>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnNames.add(model.getColumnName(i));
        }
        return columnNames;
    }

    private void loadTable() {
        if (myJFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadTable(myJFileChooser.getSelectedFile());
        }
    }

    private void loadTable(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(file));
            Vector rowData = (Vector) in.readObject();
            Vector columnNames = (Vector) in.readObject();
            model.setDataVector(rowData, columnNames);
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //effect: runs  PaymentGroupDemo
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PaymentGroupDemo();
            }
        });
    }


}
