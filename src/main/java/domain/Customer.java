package domain;

final public class Customer {

    final private String  rfc;
    final private String  name;
    final private Integer age;
    final private Integer countryId;

    public Customer (String rfc, String name, Integer age, Integer countryId) {
        this.rfc        = rfc;
        this.name       = name;
        this.age        = age;
        this.countryId  = countryId;
    }

    public String getRFC () {
        return rfc;
    }

    public String getName () {
        return name;
    }

    public Integer getAge () {
        return age;
    }

    public Integer getCountryId() {
        return countryId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "rfc='" + rfc + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", countryId=" + countryId +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        Customer customer = (Customer) obj;
        return  this.name.equals(customer.getName())
                && this.rfc.equals(customer.getRFC())
                && this.age.equals(customer.getAge())
                && this.countryId.equals(customer.getCountryId());
    }
}
