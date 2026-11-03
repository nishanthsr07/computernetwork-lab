
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
            System.out.println("WRONG INPUT: Enter two different prime numbers.");
            sc.close();
            return;
        }

        BigInteger n = p.multiply(q);

        BigInteger t = p.subtract(BigInteger.ONE)
                        .multiply(q.subtract(BigInteger.ONE));

        System.out.println("ENTER MESSAGE (number less than " + n + "):");
        BigInteger msg = sc.nextBigInteger();

        if (msg.signum() < 0 || msg.compareTo(n) >= 0) {
            System.out.println("Invalid message. Message must be between 0 and n-1.");
            sc.close();
            return;
        }

        BigInteger e = BigInteger.valueOf(2);

        while (e.compareTo(t) < 0) {
            if (e.gcd(t).equals(BigInteger.ONE)) {
                break;
            }
            e = e.add(BigInteger.ONE);
        }

        if (e.compareTo(t) >= 0) {
            System.out.println("Unable to generate public key.");
            sc.close();
            return;
        }

        BigInteger d = e.modInverse(t);

        System.out.println("\nPUBLIC KEY: (" + e + ", " + n + ")");
        System.out.println("PRIVATE KEY: (" + d + ", " + n + ")");

        BigInteger cipher = msg.modPow(e, n);

        System.out.println("\nTHE ENCRYPTED MESSAGE IS: " + cipher);

        BigInteger decrypted = cipher.modPow(d, n);

        System.out.println("THE DECRYPTED MESSAGE IS: " + decrypted);

        sc.close();
    }
}
