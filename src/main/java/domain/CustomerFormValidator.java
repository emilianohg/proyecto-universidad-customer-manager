package domain;

public class CustomerFormValidator {

    public static boolean valid (String rfc, String name, String age, String countryId) {
        if (
               rfc.length() != 10
            || name.length() == 0
            || age == null
            || countryId == null
        ) {
            return false;
        }

        if (!isNumber(age) || !isNumber(countryId))
            return false;

        if (age.length() == 0 || countryId.length() == 0)
            return false;

        return true;
    }

    private static boolean isNumber (String number) {
        return number.matches("^[0-9]*$");
    }
}
