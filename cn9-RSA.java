import java.util.Scanner;
import java.math.BigInteger;
public class RSA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER FIRST PRIME NUMBER:");
        BigInteger p = sc.nextBigInteger();
        System.out.println("ENTER ANOTHER PRIME NUMBER:");
        BigInteger q = sc.nextBigInteger();
        if (!p.isProbablePrime(20) ||
            !q.isProbablePrime(20) || p.equals(q)) {
            System.out.println("WRONG INPUT");
            sc.close();
            return;
        }
        BigInteger n = p.multiply(q);
        BigInteger t = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));
        System.out.println("ENTER MESSAGE:");
        BigInteger msg = sc.nextBigInteger();
        if (msg.signum() < 0 || msg.compareTo(n) >= 0) {
            System.out.println("MESSAGE MUST BE LESS THAN n");
            sc.close();
            return;
        }
        System.out.println("\nPOSSIBLE VALUES OF e AND d ARE");
        BigInteger e = BigInteger.ZERO;
        BigInteger d = BigInteger.ZERO;
        boolean first = true;
        for (BigInteger i = BigInteger.TWO;
             i.compareTo(t) < 0;
             i = i.add(BigInteger.ONE)) {
            if (i.isProbablePrime(20) &&
                !i.equals(p) &&
                !i.equals(q) &&
                i.gcd(t).equals(BigInteger.ONE)) {
                BigInteger tempD = i.modInverse(t);
                System.out.println(i + "\t" + tempD);
                if (first) {
                    e = i;
                    d = tempD;
                    first = false;
                }
            }
        }
        if (first) {
            System.out.println("No valid e and d values found.");
            sc.close();
            return;
        }
        System.out.println("\nPUBLIC KEY: (" + e + ", " + n + ")");
        System.out.println("PRIVATE KEY: (" + d + ", " + n + ")");
        BigInteger cipher = msg.modPow(e, n);
        System.out.println("\nTHE ENCRYPTED MESSAGE IS: " + cipher);
        BigInteger decrypted = cipher.modPow(d, n);
        System.out.println("THE DECRYPTED MESSAGE IS: " + decrypted);
        sc.close();
    }
}
