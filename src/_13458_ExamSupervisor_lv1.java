/*
input
5
1000000 1000000 1000000 1000000 1000000
5 7

output
714290
 */

/*
    회고
    자료형이 문제가 있었다 total을 int가 아닌 long으로 해야한다. total의 가능한 최대값이 max(N) * max(A)인데 그 값이 10^12이므로 int의 범위를 넘어선다.
    int범위가 -2^32 ~2^32-1 인데 대략 2 * 10^9 정도이므로 10^12보다 작은 값이기 때문이다. 이걸 주의하자
 */

import java.util.Scanner;

public class _13458_ExamSupervisor_lv1 {
        public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        int N, B, C;

        N = sc.nextInt();

        int[] A = new int[N];

        for (int i = 0; i < N; i++)
            A[i] = sc.nextInt();

        B = sc.nextInt();
        C = sc.nextInt();

        long total = 0;

        for (int i = 0; i < N; i++) {
            if(A[i] > 0) {
                A[i] -= B;
                total += (long) 1;
            }

            if(A[i] > 0) {
                total += (long) (A[i] + C - 1) / C;
            }
        }

        System.out.println(total);
    }
}
