package storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class StorageHandler {
    final private String filename;
    protected RandomAccessFile file;

    public StorageHandler(String filename) throws IOException {
        this.filename = filename;
        file = new RandomAccessFile(new File(this.filename),"rw");
    }

    public String getFilename() {
        return filename;
    }

    public boolean close () {
        try {
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int totalRecords () throws IOException {
        return (int) file.length() / getRecordSize();
    }

    public abstract int getRecordSize();
}
