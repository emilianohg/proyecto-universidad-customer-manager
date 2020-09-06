package storage;

import domain.Customer;
import junit.framework.TestCase;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerStorageHandlerTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        try {
            CustomerStorageHandler storage = new CustomerStorageHandler();
            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
        }
    }

    public void test_save_customer () {

        System.out.println("test_save_customer");

        CustomerStorageHandler storage;

        Customer customer = getArrayCustomers()[0];

        try {
            storage = new CustomerStorageHandler();
            boolean result = storage.save(customer);
            assertTrue(result);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_save_many_customers () {

        System.out.println("test_save_many_customers");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();

        try {
            storage = new CustomerStorageHandler();
            boolean result = storage.save(customers);
            assertTrue(result);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_find_first_customer_with_one_record () {

        System.out.println("test_find_first_customer_with_one_record");

        CustomerStorageHandler storage;

        Customer customer = getArrayCustomers()[0];

        try {
            storage = new CustomerStorageHandler();
            storage.save(customer);

            Customer customerFinded = storage.find(customer.getRFC());

            assertEquals(customer, customerFinded);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_find_first_customer () {

        System.out.println("test_find_first_customer");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();
        Customer customer = customers[0];
        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);

            Customer customerFinded = storage.find(customer.getRFC());

            assertEquals(customer, customerFinded);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_find_last_customer () {

        System.out.println("test_find_last_customer");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();
        Customer customer4 = customers[3];

        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);

            Customer customerFinded = storage.find(customer4.getRFC());
            assertEquals(customer4, customerFinded);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_find_middle_customer () {

        System.out.println("test_find_middle_customer");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();
        Customer customer2 = customers[1];


        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);

            Customer customerFinded = storage.find(customer2.getRFC());
            assertEquals(customer2, customerFinded);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_find_not_exists_customer () {

        System.out.println("test_find_not_exists_customer");

        CustomerStorageHandler storage;

        Customer customer = getArrayCustomers()[0];

        try {
            storage = new CustomerStorageHandler();
            storage.save(customer);
            storage.find("dededede");
            fail();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        } catch (CustomerNotFoundException e) {
            assertTrue(true);
        }

    }

    public void test_find_customer_with_empty_storage () {

        System.out.println("test_find_customer_with_empty_storage");

        CustomerStorageHandler storage;

        try {
            storage = new CustomerStorageHandler();
            storage.find("dededede");
            fail();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        } catch (CustomerNotFoundException e) {
            assertTrue(true);
        }

    }

    public void test_get_all_customer () {
        System.out.println("test_get_all_customer");

        CustomerStorageHandler storage;

        Customer customer1 = getArrayCustomers()[0];
        Customer customer2 = getArrayCustomers()[1];
        Customer customer3 = getArrayCustomers()[2];
        Customer customer4 = getArrayCustomers()[3];

        try {
            storage = new CustomerStorageHandler();
            storage.save(customer1);
            storage.save(customer2);
            storage.save(customer3);
            storage.save(customer4);

            ArrayList<Customer> customers = storage.getAll();
            System.out.println(customers.size());
            customers.forEach(customer -> System.out.println(customer));

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }
    }

    private Customer[] getArrayCustomers () {
        Customer[] customers = {
                new Customer("ABCD354356", "Emiliano Hernandez", 21, 34),
                new Customer("FGM32DM31M", "Juan Perez", 12, 31),
                new Customer("H4G32DM3AS", "Pedro Fernandez", 53, 34),
                new Customer("32D2SF4M3W", "Gabriela Rodriguez", 21, 33),
        };

        return customers;
    }



}