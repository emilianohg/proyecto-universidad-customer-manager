package storage;

final public class CustomerIndex {
    final public String     rfc;
    final public Integer    index;
    final public Character  status;

    public CustomerIndex(String rfc, Integer index, Character status) {
        this.rfc    = rfc;
        this.index  = index;
        this.status = status;
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
