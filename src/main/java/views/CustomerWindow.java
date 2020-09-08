package views;

import domain.Customer;
import domain.CustomerFormValidator;
import storage.CustomerStorageHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class CustomerWindow extends JFrame {

    private final Font fontTitle = new Font("Verdana", Font.PLAIN, 20);

    JTextField inputRFC, inputName, inputAge, inputCountryId;
    JButton btnCreate, btnUpdate, btnShow, btnIndex, btnDelete;

    CustomerStorageHandler storage;

    public CustomerWindow(String title) {
        super(title);
        makeLayout(title);
        makeActions();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        try {
            storage = new CustomerStorageHandler();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public CustomerWindow () {
        this("Administrador de clientes");
    }

    private void makeLayout (String title) {
        setSize(300, 375);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = (JPanel) getContentPane();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(fontTitle);
        lblTitle.setAlignmentX(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        inputRFC        = new JTextField();
        inputRFC.addKeyListener(new InputFormat(InputFormat.LETTERS_AND_NUMBERS_ONLY, 10));

        inputName       = new JTextField();
        inputName.addKeyListener(new InputFormat(InputFormat.LETTERS_ONLY, 40));

        inputAge        = new JTextField();
        inputAge.addKeyListener(new InputFormat(InputFormat.NUMBERS_ONLY));

        inputCountryId  = new JTextField();
        inputCountryId.addKeyListener(new InputFormat(InputFormat.NUMBERS_ONLY));

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
        formButtonPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.ipady = 10;
        constraints.fill = 1;
        constraints.insets = new Insets(3,3,3,3);

        btnCreate   = new JButton("Crear", new ImageIcon("src/main/resources/save-color.png"));
        constraints.gridx = 0;
        constraints.gridy = 0;
        formButtonPanel.add(btnCreate, constraints);

        btnUpdate   = new JButton("Actualizar", new ImageIcon("src/main/resources/edit-color.png"));
        constraints.gridx = 1;
        constraints.gridy = 0;
        formButtonPanel.add(btnUpdate, constraints);

        btnShow     = new JButton("Buscar", new ImageIcon("src/main/resources/search-color.png"));
        constraints.gridx = 0;
        constraints.gridy = 1;
        formButtonPanel.add(btnShow, constraints);

        btnDelete   = new JButton("Eliminar", new ImageIcon("src/main/resources/delete-color.png"));
        constraints.gridx = 1;
        constraints.gridy = 1;
        formButtonPanel.add(btnDelete, constraints);

        btnIndex    = new JButton("Listar", new ImageIcon("src/main/resources/checklist.png"));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        formButtonPanel.add(btnIndex, constraints);

        add(formButtonPanel, BorderLayout.SOUTH);

    }

    private void makeActions () {
        btnCreate.addActionListener(action -> {
            if (!CustomerFormValidator.valid(
                    inputRFC.getText(),
                    inputName.getText(),
                    inputAge.getText(),
                    inputCountryId.getText()
            )) {
                return;
            }

            String rfc          = inputRFC.getText();
            String name         = inputName.getText();
            int age             = Integer.parseInt(inputAge.getText());
            int countryId       = Integer.parseInt(inputCountryId.getText());

            Customer customer = new Customer(rfc, name, age, countryId);
            boolean saved = storage.save(customer);

            if (!saved) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("No se puede guardar el cliente con el RFC %s.", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente guardado con exito",
                    "Guardado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        btnShow.addActionListener(action -> {
            String rfc          = inputRFC.getText();

            if (rfc.length() == 0)
                return;

            Customer customer = storage.find(rfc);

            if (customer == null) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("No se encontro el cliente con el RFC %s.", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (customer.isDeleted()) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("El cliente con RFC %s esta eliminado", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            inputName.setText(customer.getName());
            inputAge.setText(Integer.toString(customer.getAge()));
            inputCountryId.setText(Integer.toString(customer.getCountryId()));

        });

        btnDelete.addActionListener(action -> {
            String rfc          = inputRFC.getText();
            if (rfc.length() == 0)
                return;

            Customer customer = storage.find(rfc);

            if (customer == null) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("El usuario con RFC %s no esta registrado.", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            boolean deleted = storage.delete(rfc);

            if (!deleted) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("No se puede eliminar el cliente con el RFC %s.", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente eliminado con exito",
                    "Guardado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        btnUpdate.addActionListener(action -> {

            if (!CustomerFormValidator.valid(
                    inputRFC.getText(),
                    inputName.getText(),
                    inputAge.getText(),
                    inputCountryId.getText()
            )) {
                return;
            }

            String rfc          = inputRFC.getText();
            String name         = inputName.getText();
            int age             = Integer.parseInt(inputAge.getText());
            int countryId       = Integer.parseInt(inputCountryId.getText());

            Customer customerFinded = storage.find(rfc);


            if (customerFinded == null) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("El usuario con RFC %s no esta registrado.", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (customerFinded.isDeleted()) {
                JOptionPane.showMessageDialog(
                        this,
                        String.format("El cliente con RFC %s esta eliminado", rfc),
                        "Ocurrio un error",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Customer customer = new Customer(rfc, name, age, countryId);
            storage.update(customer);

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente actualizado con exito",
                    "Actualizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });

        btnIndex.addActionListener(action -> {
            CustomerTableWindow tableWindow = new CustomerTableWindow();
            tableWindow.setVisible(true);
        });
    }

}
