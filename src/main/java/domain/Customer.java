package domain;

final public class Customer {

    final private String    rfc;
    final private String    name;
    final private int       age;
    final private int       countryId;
    final private Character status;

    public Customer (String rfc, String name, int age, int countryId, Character status) {
        this.rfc        = rfc;
        this.name       = name;
        this.age        = age;
        this.countryId  = countryId;
        this.status     = status;
    }

    public Customer (String rfc, String name, int age, int countryId) {
        this(rfc, name, age, countryId, 'A');
    }

    public String getRFC () {
        return rfc;
    }

    public String getName () {
        return name;
    }

    public int getAge () {
        return age;
    }

    public int getCountryId() {
        return countryId;
    }

    public boolean isDeleted () {
        return status == 'E';
    }

    @Override
    public String toString() {
        return "Customer{" +
                "rfc='" + rfc + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", countryId=" + countryId +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        Customer customer = (Customer) obj;
        return  this.name.equals(customer.getName())
                && this.rfc.equals(customer.getRFC())
                && this.age == customer.getAge()
                && this.countryId == customer.getCountryId();
    }
}
