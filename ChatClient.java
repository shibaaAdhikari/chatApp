import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public ChatClient() {

        try {
            System.out.println("Sending request to servver");
            socket = new Socket("192.168.1.69", 7777);
            System.out.println("connection done,");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReading();
            startWriting();

        } catch (Exception e) {

        }
    }
      public void startReading() {
        Runnable reader = () -> {
            System.out.println("Reader thread started.");

            try {
                String msg;
                while ((msg = br.readLine()) != null) {
                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Server terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(reader).start();
    }

    public void startWriting() {
        Runnable writer = () -> {
            System.out.println("Writer thread started.");

            try {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content;
                while (true) {
                    content = br1.readLine();
                    out.println(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(writer).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client");
        new ChatClient();
    }

}
