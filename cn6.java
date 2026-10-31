
import java.util.Scanner;

public class ford {

    private int[] D;
    private int num_ver;

    public static final int MAX_VALUE = 999;

    public ford(int num_ver) {
        this.num_ver = num_ver;
        D = new int[num_ver + 1];
    }

    public void BellmanFordEvaluation(int source, int A[][]) {

        // Step 1: Initialize distances
        for (int i = 1; i <= num_ver; i++) {
            D[i] = MAX_VALUE;
        }

        D[source] = 0;

        // Step 2: Relax all edges V-1 times
        for (int i = 1; i <= num_ver - 1; i++) {

            for (int sn = 1; sn <= num_ver; sn++) {

                for (int dn = 1; dn <= num_ver; dn++) {

                    if (A[sn][dn] != MAX_VALUE &&
                        D[sn] != MAX_VALUE) {

                        if (D[dn] > D[sn] + A[sn][dn]) {
                            D[dn] = D[sn] + A[sn][dn];
                        }
                    }
                }
            }
        }

        // Step 3: Check for negative-weight cycle
        for (int sn = 1; sn <= num_ver; sn++) {

            for (int dn = 1; dn <= num_ver; dn++) {

                if (A[sn][dn] != MAX_VALUE &&
                    D[sn] != MAX_VALUE) {

                    if (D[dn] > D[sn] + A[sn][dn]) {

                        System.out.println(
                            "The Graph contains negative edge cycle"
                        );
                        return;
                    }
                }
            }
        }

        // Display shortest distances
        System.out.println("Shortest distances from source vertex " + source);

        for (int vertex = 1; vertex <= num_ver; vertex++) {

            if (D[vertex] == MAX_VALUE) {
                System.out.println(
                    "Distance to vertex " + vertex + " is INF (Unreachable)"
                );
            } else {
                System.out.println(
                    "Distance from source " + source +
                    " to vertex " + vertex + " is " + D[vertex]
                );
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of vertices:");
        int num_ver = scanner.nextInt();

        if (num_ver <= 0) {
            System.out.println("Invalid number of vertices.");
            scanner.close();
            return;
        }

        int A[][] = new int[num_ver + 1][num_ver + 1];

        System.out.println("Enter the adjacency matrix:");

        for (int sn = 1; sn <= num_ver; sn++) {

            for (int dn = 1; dn <= num_ver; dn++) {

                A[sn][dn] = scanner.nextInt();

                if (sn == dn) {
                    A[sn][dn] = 0;
                } else if (A[sn][dn] == 0) {
                    A[sn][dn] = MAX_VALUE;
                }
            }
        }

        System.out.println("Enter the source vertex:");
        int source = scanner.nextInt();

        if (source < 1 || source > num_ver) {
            System.out.println("Invalid source vertex.");
            scanner.close();
            return;
        }

        ford b = new ford(num_ver);
        b.BellmanFordEvaluation(source, A);

        scanner.close();
    }
}
