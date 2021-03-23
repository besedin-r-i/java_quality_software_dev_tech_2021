import textprocessors.TextProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TextProcessorSocketServer {
    final String STOP_SERVER_COMMAND = "close";
    private final int port;
    private final TextProcessor processor;
    public TextProcessorSocketServer(int port, TextProcessor processor) {
        this.port = port;
        this.processor = processor;
    }
    public void Start() {
        new Thread(
            () -> {
                try (ServerSocket serverSocket = new ServerSocket(port);
                     Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.equals(STOP_SERVER_COMMAND)) {
                            break;
                        }
                        out.println(processor.Process(inputLine));
                    }
                } catch (IOException e) {
                    System.out.println("Got server I/O error:");
                    e.printStackTrace();
                }
            }
        ).start();
    }
}
