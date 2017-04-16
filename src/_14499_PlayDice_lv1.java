/*
input
4 2 0 0 8
0 2
3 4
5 6
7 8
4 4 4 1 3 3 3 2

output
0
0
3
0
0
8
6
3
 */

import java.util.Scanner;

public class _14499_PlayDice_lv1 {
    public static int N, M, K;

    public static int[][] map;
    public static Dice dice;

    static class Dice {
        int top;
        int east;
        int west;
        int south;
        int north;
        int bottom;

        int r;
        int c;

        public Dice(int x, int y) {
            this.top = 0;
            this.east = 0;
            this.west = 0;
            this.south = 0;
            this.north = 0;
            this.bottom = 0;
            this.r = x;
            this.c = y;
        }

        boolean executionCommand(int command) {
            boolean movePossible = true;

            switch(command) {
                case 1:
                    movePossible = goEast();
                    break;
                case 2:
                    movePossible = goWest();
                    break;
                case 3:
                    movePossible = goNorth();
                    break;
                case 4:
                    movePossible = goSouth();
                    break;
            }

            if(movePossible) copy();

            return movePossible;
        }

        void copy() {
            // 현재 지도의 숫자가 0인 경우
            if(map[r][c] == 0)
                map[r][c] = bottom;
            else {
                bottom = map[r][c];
                map[r][c] = 0;
            }
        }

        boolean goEast() {
            if(M <= c+1)
                return false;

            // 동쪽으로 한칸 이동
            c++;

            // 주사위 면을 이동
            int temp = top;
            top = west;
            west = bottom;
            bottom = east;
            east = temp;

            return true;
        }

        boolean goWest() {
            if(c-1 < 0)
                return false;

            c--;

            int temp = top;
            top = east;
            east = bottom;
            bottom = west;
            west = temp;

            return true;
        }

        boolean goNorth() {
            if(r-1 < 0)
                return false;

            r--;

            int temp = top;
            top = south;
            south = bottom;
            bottom = north;
            north = temp;

            return true;
        }

        boolean goSouth() {
            if(N <= r+1)
                return false;

            r++;

            int temp = top;
            top = north;
            north = bottom;
            bottom = south;
            south = temp;

            return true;
        }
    }

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        int x = sc.nextInt();
        int y = sc.nextInt();
        K = sc.nextInt();

        map = new int[N][M];
        dice = new Dice(x, y);

        for(int n = 0; n < N; n++)
            for(int m = 0; m < M; m++)
                map[n][m] = sc.nextInt();

        for(int k = 0; k < K; k++) {
            int command = sc.nextInt();
            boolean normal = dice.executionCommand(command);
            if(normal) System.out.println(dice.top);
        }
    }
}
