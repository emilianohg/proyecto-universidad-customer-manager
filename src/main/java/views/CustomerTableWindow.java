package views;

import domain.Customer;
import storage.CustomerStorageHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerTableWindow extends JFrame {

    CustomerStorageHandler storage;
    JTable table;

    CustomerTableWindow () {
        super("Listado de usuarios");
        setLocationRelativeTo(null);
        setSize(400, 300);
        setResizable(false);

        try {
            storage = new CustomerStorageHandler();
            ArrayList<Customer> customers = storage.getAll(false);

            String[] titles = {"RFC", "Nombre", "Edad", "ID Ciudad"};
            DefaultTableModel tableModel = new DefaultTableModel(titles, 0);

            customers.forEach(customer -> {
                Object[] row = {customer.getRFC(),customer.getName(), customer.getAge(), customer.getCountryId()};
                tableModel.addRow(row);
            });

            table = new JTable(tableModel);
            table.setEnabled(false);
            add(new JScrollPane(table), BorderLayout.CENTER);
        } catch (IOException ignored) { }
    }
}
