import java.net.*;
import java.io.*;

public class ChatServer {
    private ServerSocket server;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter out;

    public ChatServer() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready for connections.");
            System.out.println("Waiting for a client...");

            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Client connected.");

            startReading();
            startWriting();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        Runnable reader = () -> {
            System.out.println("Reader thread started.");

            try {
                String msg;
                while ((msg = br.readLine()) != null) {
                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat.");
                        break;
                    }
                    System.out.println("Client: " + msg);
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
        new ChatServer();
    }
}
