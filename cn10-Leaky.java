import java.util.Random;
import java.util.Scanner;
public class Leaky {
    public static int bsize = 0, packet, tgen, j = 1;
    public static volatile String stop = null;
    public static final int bmax = 1024;
    public static final int orate = 100;
    public static final int delay = 1500;
    public static Random r = new Random();
    public static Random t = new Random();
    public class generating extends Thread {
        public void run() {
            while (stop == null) {
                tgen = t.nextInt(3000);
                packet = r.nextInt(512);
                synchronized (Leaky.class) {
                    if (bsize + packet < bmax) {
                        bsize = bsize + packet;
                        System.out.printf(
                            "%13d%10d%15d%20d\n",
                            j++, packet, bsize, bmax - bsize
                        );
                    } else {
                        System.out.println(
                            "Bucket OverFlow," + packet +
                            " size of packet discarded"
                        );
                    }
                }
                try {
                    Thread.sleep(tgen);
                } catch (Exception e) {
                    // Handle interruption
                }
            }
        }
    }
    public class leaking extends Thread {
        public void run() {
            while (true) {
                synchronized (Leaky.class) {
                    if (bsize > 0 && bsize - orate > 0) {
                        bsize = bsize - orate;
                        System.out.printf(
                            "%38d%20d%15d\n",
                            bsize, (bmax - bsize), orate
                        );
                    } else {
                        System.out.printf(
                            "%38d%20d%15d\n",
                            0, bmax, bsize
                        );
                        bsize = 0;
                        if (stop != null) {
                            return;
                        }
                    }
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    // Handle interruption
                }
            }
        }
    }
    public static void main(String[] args) {
        Leaky le = new Leaky();
        Scanner in = new Scanner(System.in);
        generating g = le.new generating();
        leaking l = le.new leaking();
        System.out.println("Started");
        System.out.println(
            "Output Rate is:" + orate +
            "\nAnd it is flowing at interval:" +
            ((float) delay / 1000) + "sec"
        );
        System.out.println("Enter any key to stop input");
        System.out.printf(
            "Packet number | Input Packet | Bucket filled | Remaining space| Output rate"
        );
        System.out.println();
        g.start();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            // Handle interruption
        }
        l.start();
        stop = in.next();
        try {
            g.join();
            l.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        in.close();
    }
}
