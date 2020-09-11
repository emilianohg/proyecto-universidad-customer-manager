package domain;

import junit.framework.TestCase;

public class CustomerTest extends TestCase {

    public void test_getters_customer () {
        String  rfc         = "ABCD354356";
        String  name        = "Emiliano Hernandez";
        int     age         = 21;
        int     countryId   = 48;

        Customer customer = new Customer(rfc, name, age, countryId);

        assertEquals(rfc,       customer.getRFC());
        assertEquals(name,      customer.getName());
        assertEquals(age,       customer.getAge());
        assertEquals(countryId, customer.getCountryId());
    }

}