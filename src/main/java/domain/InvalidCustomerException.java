package domain;

final public class InvalidCustomerException extends RuntimeException {
    InvalidCustomerException (String message) {
        super(message);
    }
}
