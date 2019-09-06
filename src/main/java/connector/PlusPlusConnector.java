package connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class PlusPlusConnector {

    private static PlusPlusConnector plusPlusConnectorInstance = null;
    private static Socket socket;

    private PlusPlusConnector() {
    }

    public static PlusPlusConnector getInstance() {
        if (plusPlusConnectorInstance == null)
            plusPlusConnectorInstance = new PlusPlusConnector();

        return plusPlusConnectorInstance;
    }

    public void createConection(String url, int port) throws IOException {
        this.socket = new Socket(url, port);
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getConnectorInputStream() throws IOException {
        InputStream inputStream = socket.getInputStream();
        return inputStream;
    }

    public OutputStream getConnectorOutputStream() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        return outputStream;
    }

}
