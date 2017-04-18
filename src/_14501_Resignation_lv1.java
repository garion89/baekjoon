/*
input
10
5 50
4 40
3 30
2 20
1 10
1 10
2 20
3 30
4 40
5 50

output
90
 */

//TODO: graph말고 그냥 dp로도 쉽게 풀 수 있다. 그걸로 다시 한번 풀어보기
import java.util.Scanner;

public class _14501_Resignation_lv1 {
    public static int N;
    public static int[] day, money;
    public static boolean[][] possibleGraph;
    public static int[][] moneyGraph;

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        day = new int[N];
        money = new int[N];
        possibleGraph = new boolean[N + 1][N + 1];
        moneyGraph = new int[N + 1][N + 1];

        for (int i = 0; i < N; i++) {
            day[i] = sc.nextInt();
            money[i] = sc.nextInt();
        }

        for (int i = 0; i < N; i++) {
            possibleGraph[i][i + 1] = true;
            if (i + day[i] <= N) {
                possibleGraph[i][i + day[i]] = true;
                moneyGraph[i][i + day[i]] = money[i];
            }
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = i+1; j <= N; j++) {
                    if(k <= i || j <= k || !possibleGraph[i][k] || !possibleGraph[k][j])
                        continue;

                    int temp = moneyGraph[i][k] + moneyGraph[k][j];

                    if(moneyGraph[i][j] <= temp) {
                        moneyGraph[i][j] = temp;
                        possibleGraph[i][j] = true;
                    }
                }
            }
        }

        System.out.println(moneyGraph[0][N]);
    }
}
