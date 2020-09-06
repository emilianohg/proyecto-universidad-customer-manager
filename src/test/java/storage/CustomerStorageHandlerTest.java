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
        Customer customer = customers[customers.length - 1];

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

    public void test_find_middle_customer () {

        System.out.println("test_find_middle_customer");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();
        Customer customer6 = customers[5];


        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);

            Customer customerFinded = storage.find(customer6.getRFC());
            assertEquals(customer6, customerFinded);

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

        Customer[] customers = getArrayCustomers();

        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);
            ArrayList<Customer> customersList = storage.getAll();

            customersList.forEach(customer -> System.out.println(customer));

            assertEquals((long) storage.totalRecords(), customersList.size());


            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }
    }

    public void test_update_customer () {

        System.out.println("test_update_customer");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();
        Customer customer3 = customers[2];

        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);

            String name = "Usuario 3 modificado";
            Customer customer3Modified = new Customer(customer3.getRFC(), name, customer3.getAge(), customer3.getCountryId());

            if (!storage.update(customer3Modified))
                fail("No se pudo actualizar el registro");

            Customer customerFinded = storage.find(customer3.getRFC());

            assertEquals(customerFinded.getName(), name);

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_update_not_exist_customer () {

        System.out.println("test_update_customer");

        CustomerStorageHandler storage;

        Customer[] customers = getArrayCustomers();
        Customer customer3 = customers[2];

        try {
            storage = new CustomerStorageHandler();
            storage.save(customers);

            String name = "Usuario 3 modificado";
            Customer customer3Modified = new Customer("NOTEXISTS", name, customer3.getAge(), customer3.getCountryId());

            assertFalse(storage.update(customer3Modified));

            storage.close();
            storage.flush();
        } catch (IOException e) {
            System.out.println("No puedo acceder al archivo");
            fail();
        }

    }

    public void test_delete_customer () {

        System.out.println("test_save_customer");

        CustomerStorageHandler storage;

        Customer customer = getArrayCustomers()[0];

        try {
            storage = new CustomerStorageHandler();
            boolean result = storage.save(customer);
            assertTrue(result);

            ArrayList<Customer> customersBeforeDelete = storage.getAll();
            assertEquals(1, customersBeforeDelete.size());

            assertEquals(1, (int) storage.totalRecords());

            storage.delete(customer);

            ArrayList<Customer> customers = storage.getAll();
            assertEquals(0, customers.size());

            assertEquals(1, (int) storage.totalRecords());

            Customer customerFinded = storage.find(customer.getRFC());
            assertTrue(customerFinded.isDeleted());

            ArrayList<Customer> allCustomers = storage.getAll(false);
            assertEquals(1, allCustomers.size());

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
                new Customer("HFR2SF233W", "Usuario 4", 21, 33),
                new Customer("1245SF4M3W", "Usuario 5", 21, 33),
                new Customer("KOW2345M3W", "Usuario 6", 21, 33),
                new Customer("JJU79S0DII", "Usuario 7", 21, 33),
                new Customer("8JD2SF4M3W", "Usuario 8", 21, 33),
                new Customer("3DS2SF4M3W", "Usuario 9", 21, 33),
                new Customer("ZZZ2SF4DDD", "Usuario 10", 21, 33),
                new Customer("AAA2SFSD3W", "Usuario 11", 21, 33),
                new Customer("ADE2SF4M3W", "Usuario 12", 21, 33),
                new Customer("KJUBUF4M3W", "Usuario 13", 21, 33),
                new Customer("SBYWEF4M3W", "Usuario 14", 21, 33),
        };

        return customers;
    }



}