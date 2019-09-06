package connector;

import java.io.IOException;
import java.io.OutputStream;

public class SyncOutput {

    private static SyncOutput syncOutputInstance = null;
    private OutputStream out;

    private SyncOutput() {
    }

    public static SyncOutput getInstance() {
        if (syncOutputInstance == null)
            syncOutputInstance = new SyncOutput();

        return syncOutputInstance;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public synchronized void write(byte[] bytes) throws IOException {
        out.write(bytes);
    }
}
