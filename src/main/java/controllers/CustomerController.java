package controllers;

import domain.Customer;
import storage.CustomerStorageHandler;

import java.io.IOException;

public class CustomerController {
    public boolean store (String rfc, String name, int age, int countryId) {
        Customer customer = new Customer(rfc, name, age, countryId);

        try {
            CustomerStorageHandler storage = new CustomerStorageHandler();
            return storage.save(customer);
        } catch (IOException ignored) {  }

        return false;
    }
}
