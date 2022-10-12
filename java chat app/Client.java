import java.net.Socket;
import java.io.*;

public class Client {

    BufferedReader br;
    PrintWriter out;
    Socket socket;

    public Client() {

        try {
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWritting();
        }

        catch (Exception e) {

        }
    }

    public void startReading() {
        Runnable r1 = () -> {

            System.out.println("reader started");
            try {
                while (true) {

                    String msg = br.readLine();

                    if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server :" + msg);
                }
            }

            catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Connection  closed");

            }

        };

        new Thread(r1).start();

    }

    public void startWritting() {
        Runnable r2 = () -> {
            System.out.println("Writter started");
            try {
                while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

                }

                System.out.println("Connection  closed");
            }

            catch (Exception e) {
                e.printStackTrace();
                
            }
        };

        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("This is client");
        new Client();
    }
}
