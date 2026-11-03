
import java.io.*;
import java.net.*;

public class udpc {
    public static void main(String[] args) {
        try {
            DatagramSocket skt = new DatagramSocket();

            String msg = "textmessage";
            byte[] b = msg.getBytes();

            InetAddress host =
                InetAddress.getByName("127.0.0.1");

            int serverSocket = 2400;

            DatagramPacket request = new DatagramPacket(
                b, b.length, host, serverSocket
            );

            skt.send(request);

            byte[] buffer = new byte[1000];

            DatagramPacket reply =
                new DatagramPacket(buffer, buffer.length);

            skt.receive(reply);

            String received = new String(
                reply.getData(), 0, reply.getLength()
            );

            System.out.println("Client received: " + received);

            skt.close();

        } catch (Exception ex) {
            System.out.println("Client error: " + ex.getMessage());
        }
    }
}
