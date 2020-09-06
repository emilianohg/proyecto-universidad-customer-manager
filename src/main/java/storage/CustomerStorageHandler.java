package storage;

import domain.Customer;
import utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerStorageHandler extends StorageHandler {

    CustomerIndexStorageHandler indexStorageHandler;

    public CustomerStorageHandler(String filename) throws IOException {
        super(filename);
        indexStorageHandler = new CustomerIndexStorageHandler();
    }

    public CustomerStorageHandler() throws IOException {
        this("customers.dat");
    }

    @Override
    public int getRecordSize() {
        return 50;
    }

    public boolean save (Customer customer) {
        try {
            int index = totalRecords();

            file.seek(file.length());
            file.writeUTF(StringUtils.rightpad(customer.getName(), 40));
            file.writeInt(customer.getAge());
            file.writeInt(customer.getCountryId());

            return indexStorageHandler.save(customer, index);

        } catch (IOException e) {
            return false;
        }
    }

    public boolean save(Customer[] customers) {
        int countCustomersSaved = 0;
        for (Customer customer : customers) {
            if (save(customer))
                countCustomersSaved++;
        }
        return countCustomersSaved == customers.length;
    }

    public ArrayList<Customer> getAll () throws IOException {
        return getAll(true);
    }

    public ArrayList<Customer> getAll (boolean onlyActive) throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();
        int position = 0;
        while (position <= totalRecords() - 1) {
            Customer customer = find(position);
            if (onlyActive && customer.isDeleted()) {
                position++;
                continue;
            }

            customers.add(customer);
            position++;
        }
        return customers;
    }

    public Customer find (String rfc) throws IOException, CustomerNotFoundException {
        CustomerIndex customerIndex = indexStorageHandler.getByRFC(rfc);
        return getCustomer(customerIndex);
    }

    public Customer find (long index) throws IOException, CustomerNotFoundException {
        CustomerIndex customerIndex = indexStorageHandler.getByPosition(index);
        return getCustomer(customerIndex);
    }

    private Customer getCustomer (CustomerIndex customerIndex) throws IOException {
        file.seek(customerIndex.index * getRecordSize());
        String      name        = file.readUTF().trim();
        Integer     age         = file.readInt();
        Integer     countryId   = file.readInt();
        return new Customer(customerIndex.rfc, name, age, countryId, customerIndex.status);
    }

    public boolean update (Customer customer) {
        try {
            CustomerIndex customerIndex = indexStorageHandler.getByRFC(customer.getRFC());

            if (customerIndex.isDeleted()) {
                return false;
            }

            file.seek(customerIndex.index * getRecordSize());
            file.writeUTF(StringUtils.rightpad(customer.getName(), 40));
            file.writeInt(customer.getAge());
            file.writeInt(customer.getCountryId());
            return true;
        } catch (IOException | CustomerNotFoundException e) {
            return false;
        }
    }

    public boolean delete (Customer customer) {
        try {
            return indexStorageHandler.deleteByRFC(customer.getRFC());
        } catch (IOException e) {
            return false;
        }
    }

    public boolean close () {
        try {
            indexStorageHandler.close();
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean flush () {
        File fileCustomer = new File(getFilename());
        File fileIndexCustomer = new File(indexStorageHandler.getFilename());
        return fileCustomer.delete() && fileIndexCustomer.delete();
    }

}
