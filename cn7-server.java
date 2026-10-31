
import java.net.*;
import java.io.*;

public class TCPS {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("Server ready for connection");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected successfully");

            BufferedReader fileRead = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            String fileName = fileRead.readLine();

            PrintWriter pwrite = new PrintWriter(
                socket.getOutputStream(), true
            );

            File file = new File(fileName);

            if (file.exists() && file.isFile()) {
                BufferedReader contentRead =
                    new BufferedReader(new FileReader(file));

                String line;
                while ((line = contentRead.readLine()) != null) {
                    pwrite.println(line);
                }

                contentRead.close();
            } else {
                pwrite.println("ERROR: File not found on server.");
            }

            pwrite.close();
            fileRead.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
