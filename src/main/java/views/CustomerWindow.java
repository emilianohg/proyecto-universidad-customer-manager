package views;

import domain.Customer;
import domain.CustomerFormValidator;
import storage.CustomerStorageHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import static domain.CustomerFormValidator.RFC_LENGTH;

public class CustomerWindow extends JFrame implements WindowListener {

    private final Font fontTitle = new Font("Verdana", Font.PLAIN, 20);

    JTextField inputRFC, inputName, inputAge, inputCountryId;
    JButton btnCreate, btnUpdate, btnShow, btnIndex, btnDelete;

    CustomerStorageHandler storage;

    public CustomerWindow(String title) {
        super(title);
        makeLayout(title);
        makeActions();
        addWindowListener(this);
        setVisible(true);

        try {
            storage = new CustomerStorageHandler();
        } catch (IOException ignored) {  }

    }

    public CustomerWindow () {
        this("Administrador de clientes");
    }

    private void makeLayout (String title) {
        setSize(300, 375);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/resources/book.png").getImage());

        JPanel panel = (JPanel) getContentPane();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(fontTitle);
        lblTitle.setAlignmentX(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        inputRFC        = new JTextField();
        inputRFC.addKeyListener(new InputFormat(InputFormat.RFC, RFC_LENGTH));

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
            Customer customer = getCustomer();

            if (customer == null) {
                alert("Precaucion", "Complete el formulario correctamente", "warning");
                return;
            }

            Customer customerFinded = storage.find(customer.getRFC());

            if (customerFinded != null) {
                alert("Ocurrio un error", String.format("ya existe un cliente con RFC %s registrado.", customer.getRFC()), "error");
                return;
            }

            boolean saved = storage.save(customer);

            if (!saved) {
                alert("Ocurrio un error", String.format("No se puede guardar el cliente con RFC %s.", customer.getRFC()), "error");
                return;
            }

            alert("Guardado", "Cliente guardado con exito");
            resetForm();
        });

        btnShow.addActionListener(action -> {
            String rfc          = inputRFC.getText();

            if (rfc.length() != RFC_LENGTH) {
                alert("Precaucion", "Complete el formulario correctamente", "warning");
                return;
            }

            Customer customer = storage.find(rfc);

            if (customer == null) {
                alert("Ocurrio un error", String.format("No se encontro el cliente con RFC %s.", rfc), "error");
                return;
            }

            if (customer.isDeleted()) {
                alert("Precaucion", String.format("El cliente con RFC %s ya esta eliminado", rfc), "warning");
                return;
            }

            inputName.setText(customer.getName());
            inputAge.setText(Integer.toString(customer.getAge()));
            inputCountryId.setText(Integer.toString(customer.getCountryId()));
        });

        btnDelete.addActionListener(action -> {
            String rfc          = inputRFC.getText();

            if (rfc.length() != RFC_LENGTH) {
                alert("Precaucion", "Complete el formulario correctamente", "warning");
                return;
            }

            Customer customer = storage.find(rfc);

            if (customer == null) {
                alert("Precaucion", String.format("El usuario con RFC %s no esta registrado.", rfc), "warning");
                return;
            }

            boolean deleted = storage.delete(rfc);

            if (!deleted) {
                alert("Precaucion", String.format("No se puede eliminar el cliente con el RFC %s.", rfc), "warning");
                return;
            }

            alert("Eliminado","Cliente eliminado con exito");
            resetForm();
        });

        btnUpdate.addActionListener(action -> {
            Customer customer = getCustomer();

            if (customer == null) {
                alert("Precaucion", "Complete el formulario correctamente", "warning");
                return;
            }

            Customer customerFinded = storage.find(customer.getRFC());

            if (customerFinded == null) {
                alert("Ocurrio un error", String.format("El usuario con RFC %s no esta registrado.", customer.getRFC()), "error");
                return;
            }

            if (customerFinded.isDeleted()) {
                alert("Precaucion", String.format("El cliente con RFC %s esta eliminado", customer.getRFC()), "warning");
                return;
            }

            storage.update(customer);
            alert("Actualizado","Cliente actualizado con exito");
            resetForm();
        });

        btnIndex.addActionListener(action -> {
            CustomerTableWindow tableWindow = new CustomerTableWindow();
            tableWindow.setVisible(true);
        });
    }

    private Customer getCustomer () {
        if (!CustomerFormValidator.valid(
                inputRFC.getText(),
                inputName.getText(),
                inputAge.getText(),
                inputCountryId.getText()
        )) {
            return null;
        }

        String rfc          = inputRFC.getText();
        String name         = inputName.getText();
        int age             = Integer.parseInt(inputAge.getText());
        int countryId       = Integer.parseInt(inputCountryId.getText());

        return new Customer(rfc, name, age, countryId);
    }

    private void resetForm () {
        inputRFC.setText("");
        inputName.setText("");
        inputAge.setText("");
        inputCountryId.setText("");
    }

    private void alert (String title, String message, String type) {
        int option = JOptionPane.INFORMATION_MESSAGE;
        switch (type.toLowerCase()) {
            case "error":
                option = JOptionPane.ERROR_MESSAGE;
                break;
            case "warning":
                option = JOptionPane.WARNING_MESSAGE;
                break;
            case "question":
                option = JOptionPane.QUESTION_MESSAGE;
        }
        JOptionPane.showMessageDialog(this, message, title, option);
    }

    private void alert (String title, String message) {
        alert(title, message, "");
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent event) {
        storage.close();
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {  }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {  }

    @Override
    public void windowActivated(WindowEvent windowEvent) {  }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {  }

}
