
import java.net.*;
import java.io.*;

public class TCPC {
    public static void main(String[] args) {
        try {
            // Connect to server on the same computer
            Socket socket = new Socket("127.0.0.1", 4000);

            System.out.println("Enter the filename:");

            BufferedReader keyRead = new BufferedReader(
                new InputStreamReader(System.in)
            );

            String fileName = keyRead.readLine();

            PrintWriter pwrite = new PrintWriter(
                socket.getOutputStream(), true
            );

            // Send filename to server
            pwrite.println(fileName);

            BufferedReader socketRead = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            System.out.println("\nFile contents:");

            String line;
            while ((line = socketRead.readLine()) != null) {
                System.out.println(line);
            }

            socketRead.close();
            pwrite.close();
            keyRead.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
