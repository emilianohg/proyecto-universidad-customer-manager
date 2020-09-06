package storage;

import domain.Customer;
import utils.StringUtils;

import java.io.IOException;

public class CustomerIndexStorageHandler extends StorageHandler{

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
            file.writeChar('A');

            orderLastRecord();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public CustomerIndex getByRFC(String rfc) throws IOException, CustomerNotFoundException {
        long position = getPositionByRFC(rfc);
        return getByPosition(position);
    }

    private void orderLastRecord () throws IOException {

        if (totalRecords() == 0) {
            return;
        }

        long position = totalRecords() - 1;

        while (position >= 1) {

            CustomerIndex previusRecord = getByPosition(position - 1);
            CustomerIndex record = getByPosition(position);

            if (record.rfc.compareTo(record.rfc) > 0) {
                return;
            }

            setByPosition(record, position - 1);
            setByPosition(previusRecord, position);

            position--;
        }


    }

    public boolean deleteByRFC (String rfc) throws IOException {
        long position;
        try {
            position = getPositionByRFC(rfc);
        } catch (CustomerNotFoundException e) {
            return true;
        }
        file.seek(position * getRecordSize());
        file.readUTF();
        file.readInt();
        file.writeChar('E');
        return true;
    }

    private long getPositionByRFC (String rfc) throws IOException, CustomerNotFoundException {
        if (totalRecords() == 0)
            throw new CustomerNotFoundException();

        long position = 0;
        CustomerIndex record = getByPosition(position);
        while (!record.rfc.equals(rfc)) {
            if (position >= totalRecords() - 1)
                throw new CustomerNotFoundException();
            record = getByPosition(++position);
        }
        return position;
    }

    private void setByPosition(CustomerIndex customerIndex, long position) throws IOException {
        file.seek(position * getRecordSize());
        file.writeUTF(customerIndex.rfc);
        file.writeInt(customerIndex.index);
        file.writeChar(customerIndex.status);
    }

    public CustomerIndex getByPosition(long position) throws IOException {
        file.seek(position * getRecordSize());
        String      rfc     = file.readUTF();
        Integer     index   = file.readInt();
        Character   status  = file.readChar();
        return new CustomerIndex(rfc, index, status);
    }


}
