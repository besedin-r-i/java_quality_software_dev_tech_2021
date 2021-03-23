import java.io.*;
import java.net.Socket;

public class TextEchoSocketClient implements Closeable {
    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    public TextEchoSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void Connect() throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public String SendText(String text) throws IOException {
        out.println(text);
        return in.readLine();
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
