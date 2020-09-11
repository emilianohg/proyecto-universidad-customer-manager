package storage;

final public class CustomerIndex {
    final public String     rfc;
    final public int        index;
    final public Character  status;

    public CustomerIndex(String rfc, int index, Character status) {
        this.rfc    = rfc;
        this.index  = index;
        this.status = status;
    }

    public boolean isDeleted () {
        return status == 'E';
    }

    @Override
    public String toString() {
        return "CustomerIndex{" +
                "rfc='" + rfc + '\'' +
                ", index=" + index +
                ", status=" + status +
                '}';
    }
}
