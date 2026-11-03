
import java.io.*;
import java.net.*;
import java.util.*;

public class udps {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            DatagramSocket skt = new DatagramSocket(2400);
            byte[] buffer = new byte[1000];

            System.out.println("UDP Server is ready...");

            while (true) {
                DatagramPacket request =
                    new DatagramPacket(buffer, buffer.length);

                skt.receive(request);

                System.out.print("Enter message to send to client: ");
                String message = sc.nextLine();

                byte[] sendMsg = message.getBytes();

                DatagramPacket reply = new DatagramPacket(
                    sendMsg,
                    sendMsg.length,
                    request.getAddress(),
                    request.getPort()
                );

                skt.send(reply);
                System.out.println("Message sent to client.");
            }

        } catch (Exception ex) {
            System.out.println("Server error: " + ex.getMessage());
        }

        sc.close();
    }
}
