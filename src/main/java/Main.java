import textprocessors.SentimentAnalisys;

import java.io.IOException;
import java.util.Scanner;

class Main {
    static final int PORT = 4322;
    public static void main(String[] args)  {
        TextProcessorSocketServer server = new TextProcessorSocketServer(PORT, new SentimentAnalisys());
        server.Start();

        TextEchoSocketClient echoClient = new TextEchoSocketClient("localhost", PORT);
        Scanner scanner = new Scanner(System.in);
        try {
            echoClient.Connect();
            String text;
            while((text = scanner.nextLine()) != null){
                System.out.println(echoClient.SendText(text));
                if(text.equals("close")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Got I/O error:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}