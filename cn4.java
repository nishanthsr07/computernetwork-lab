
import java.util.Scanner;

public class CRC1 {

    // Performs CRC division using XOR
    static int[] divide(int[] data, int[] divisor) {

        int[] temp = data.clone();

        for (int i = 0; i <= temp.length - divisor.length; i++) {

            if (temp[i] == 1) {
                for (int j = 0; j < divisor.length; j++) {
                    temp[i + j] ^= divisor[j];
                }
            }
        }

        return temp;
    }

    // Checks whether the input contains only binary digits
    static boolean isBinary(String input) {

        if (input == null || input.isEmpty()) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '0' &&
                input.charAt(i) != '1') {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message bits: ");
        String message = sc.nextLine();

        System.out.print("Enter generator: ");
        String generator = sc.nextLine();

        if (!isBinary(message) || !isBinary(generator)
                || generator.length() < 2
                || generator.charAt(0) != '1'
                || generator.charAt(generator.length() - 1) != '1') {

            System.out.println("Invalid binary input or generator.");
            sc.close();
            return;
        }

        int[] divisor = new int[generator.length()];

        for (int i = 0; i < generator.length(); i++) {
            divisor[i] = generator.charAt(i) - '0';
        }

        // Append zeros to the original message
        int[] data = new int[message.length() + generator.length() - 1];

        for (int i = 0; i < message.length(); i++) {
            data[i] = message.charAt(i) - '0';
        }

        // Calculate CRC remainder
        int[] remainder = divide(data, divisor);

        // Construct transmitted codeword
        StringBuilder codeword = new StringBuilder(message);

        for (int i = message.length(); i < data.length; i++) {
            codeword.append(remainder[i]);
        }

        System.out.println("The checksum code is: " + codeword);

        // Verify received codeword
        System.out.print("Enter checksum code: ");
        String received = sc.nextLine();

        if (!isBinary(received) ||
                received.length() < generator.length()) {

            System.out.println("Invalid checksum code.");
            sc.close();
            return;
        }

        int[] receivedData = new int[received.length()];

        for (int i = 0; i < received.length(); i++) {
            receivedData[i] = received.charAt(i) - '0';
        }

        // Check received data using the same generator
        int[] check = divide(receivedData, divisor);

        boolean valid = true;

        for (int bit : check) {
            if (bit != 0) {
                valid = false;
                break;
            }
        }

        if (valid) {
            System.out.println("Data stream is valid");
        } else {
            System.out.println("Data stream is invalid. CRC error occurred.");
        }

        sc.close();
    }
}
