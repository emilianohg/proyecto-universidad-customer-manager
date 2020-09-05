package views;

import javax.swing.*;
import java.awt.*;

public class CustomerWindow extends JFrame {

    JTextField inputRFC, inputName, inputAge, inputCountryId;
    JButton btnCreate, btnUpdate, btnShow, btnIndex, btnDelete;

    public CustomerWindow(String title) {
        super(title);
        makeLayout(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public CustomerWindow () {
        this("Administrador de clientes");
    }

    private void makeLayout (String title) {
        setSize(500, 200);
        setResizable(true);
        setLocationRelativeTo(null);

        JLabel lblTitle = new JLabel(title);
        add(lblTitle, BorderLayout.PAGE_START);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));

        inputRFC        = new JTextField();
        inputName       = new JTextField();
        inputAge        = new JTextField();
        inputCountryId  = new JTextField();

        GridBagConstraints gbc = new GridBagConstraints();
        formPanel.add(new JLabel("RFC:"));
        formPanel.add(inputRFC);

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(inputName);

        formPanel.add(new JLabel("Edad:"));
        formPanel.add(inputAge);

        formPanel.add(new JLabel("ID Ciudad:"));
        formPanel.add(inputCountryId);

        add(formPanel, BorderLayout.CENTER);

        JPanel formButtonPanel = new JPanel();
        formButtonPanel.setLayout(new FlowLayout());

        btnCreate   = new JButton("Crear");
        btnUpdate   = new JButton("Actualizar");
        btnShow     = new JButton("Buscar");
        btnIndex    = new JButton("Listar");
        btnDelete   = new JButton("Eliminar");

        formButtonPanel.add(btnCreate);
        formButtonPanel.add(btnUpdate);
        formButtonPanel.add(btnShow);
        formButtonPanel.add(btnIndex);
        formButtonPanel.add(btnDelete);

        add(formButtonPanel, BorderLayout.SOUTH);

    }



}
