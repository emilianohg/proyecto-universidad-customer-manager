package storage;

import domain.Customer;
import utils.StringUtils;

import java.io.IOException;

public class CustomerIndexStorageHandler extends StorageHandler{

    private class CustomerIndex {
        final public String     rfc;
        final public Integer    index;
        final public Character  status;

        public CustomerIndex(String rfc, Integer index, Character status) {
            this.rfc    = rfc;
            this.index  = index;
            this.status = status;
        }
    }

    public CustomerIndexStorageHandler(String filename) throws IOException {
        super(filename);
    }

    public CustomerIndexStorageHandler() throws IOException {
        this("customers-index.dat");
    }

    @Override
    public int getRecordSize() {
        return 18;
    }

    public boolean save (Customer customer, Integer indexRegistered) {
        try {
            file.seek(file.length());
            file.writeUTF(StringUtils.rightpad(customer.getRFC(), 10));
            file.writeInt(indexRegistered);
            file.writeChar('a');

            orderLastRecord();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public Integer getIndex (String rfc) throws IOException, CustomerNotFoundException {
        if (file.length() == 0)
            throw new CustomerNotFoundException();

        long position = 0;
        CustomerIndex record = getRecordByPosition(position);
        while (!record.rfc.equals(rfc)) {
            if (position >= totalRecords() - 1)
                throw new CustomerNotFoundException();
            record = getRecordByPosition(++position);
        }
        return record.index;
    }

    private void orderLastRecord () throws IOException {

        if (totalRecords() == 0) {
            return;
        }

        long position = totalRecords() - 1;

        while (position >= 1) {

            CustomerIndex previusRecord = getRecordByPosition(position - 1);
            CustomerIndex record = getRecordByPosition(position);

            if (record.rfc.compareTo(record.rfc) > 0) {
                return;
            }

            setRecordByPosition(record, position - 1);
            setRecordByPosition(previusRecord, position);

            position--;
        }


    }

    private void setRecordByPosition (CustomerIndex customerIndex, long position) throws IOException {
        file.seek(position * getRecordSize());
        file.writeUTF(customerIndex.rfc);
        file.writeInt(customerIndex.index);
        file.writeChar(customerIndex.status);
    }

    private CustomerIndex getRecordByPosition (long position) throws IOException {
        file.seek(position * getRecordSize());
        String      rfc     = file.readUTF();
        Integer     index   = file.readInt();
        Character   status  = file.readChar();
        return new CustomerIndex(rfc, index, status);
    }


}
