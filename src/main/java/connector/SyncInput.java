package connector;

import java.io.IOException;
import java.io.InputStream;

public class SyncInput {


    private static SyncInput syncInputInstance = null;
    private InputStream in;

    private SyncInput() {
    }

    public static SyncInput getInstance() {
        if (syncInputInstance == null)
            syncInputInstance = new SyncInput();

        return syncInputInstance;
    }

    public void setOut(InputStream in) {
        this.in = in;
    }

    public synchronized InputStream getInputStream() {
        return this.in;
    }
}
