package storage;

final public class CustomerNotFoundException extends RuntimeException {
    CustomerNotFoundException (String message) {
        super(message);
    }

    CustomerNotFoundException () {
        this("No se encontro cliente");
    }
}
