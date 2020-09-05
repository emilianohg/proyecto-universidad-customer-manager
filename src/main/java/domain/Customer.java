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

}
