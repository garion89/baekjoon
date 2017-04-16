/*
input
10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.##...#
#O..#....#
##########

output
7
 */

import java.util.Scanner;

public class _13460_XhaeroEscape_lv2 {
    public static int N, M;

    public static char[][] board, initialBoard;

    public static int initialRedY, initialRedX;
    public static int initialBlueY, initialBlueX;

    static class Marble {
        public int y;
        public int x;

        // type이 true이면 red, type이 false이면 blue
        public Marble(boolean type) {
            if(type) {
                y = initialRedY;
                x = initialRedX;
            } else {
                y = initialBlueY;
                x = initialBlueX;
            }
        }

        boolean move(int direction) {
            boolean goal = false;
            
            switch(direction) {
                case 0:
                    goal = goEast();
                    break;
                case 1:
                    goal = goWest();
                    break;
                case 2:
                    goal = goSouth();
                    break;
                case 3:
                    goal = goNorth();
                    break;
            }
            
            return goal;
        }

        boolean goEast() {
            boolean goal = false;

            int ptr;

            for(ptr = x; ; ptr++) {
                if(board[y][ptr+1] == '.')
                    continue;
                else if(board[y][ptr+1] == 'O') {
                    board[y][x] = '.';
                    goal = true;
                    break;
                } else { // 'R', 'B', '#'을 만나는 경우 이동 불가
                    break;
                }
            }

            char temp = board[y][ptr];
            board[y][ptr] = board[y][x];
            board[y][x] = temp;
            x = ptr;

            return goal;
        }

        boolean goWest() {
            boolean goal = false;

            int ptr;

            for(ptr = x; ; ptr--) {
                if(board[y][ptr-1] == '.')
                    continue;
                else if(board[y][ptr-1] == 'O') {
                    board[y][x] = '.';
                    goal = true;
                    break;
                } else {
                    break;
                }
            }

            char temp = board[y][ptr];
            board[y][ptr] = board[y][x];
            board[y][x] = temp;
            x = ptr;

            return goal;
        }

        boolean goSouth() {
            boolean goal = false;

            int ptr;

            for(ptr = y; ; ptr++) {
                if(board[ptr+1][x] == '.')
                    continue;
                else if(board[ptr+1][x] == 'O') {
                    board[y][x] = '.';
                    goal = true;
                    break;
                } else {
                    break;
                }
            }

            char temp = board[ptr][x];
            board[ptr][x] = board[y][x];
            board[y][x] = temp;
            y = ptr;

            return goal;
        }

        boolean goNorth() {
            boolean goal = false;

            int ptr;

            for(ptr = y; ; ptr--) {
                if(board[ptr-1][x] == '.')
                    continue;
                else if(board[ptr-1][x] == 'O') {
                    board[y][x] = '.';
                    goal = true;
                    break;
                } else {
                    break;
                }
            }

            char temp = board[ptr][x];
            board[ptr][x] = board[y][x];
            board[y][x] = temp;
            y = ptr;

            return goal;
        }
    }

    public static int minMove(int n) {
        // 처음 상태로 보드 초기화
        // (0, 0)은 최북 최서이다.
        // (y, x)순이다.
        // 남쪽으로 가면 y값이 더해지고, 동쪽으로 가면 x값이 더해진다.
        board = new char[N][M];

        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
                board[i][j] = initialBoard[i][j];

        // 구슬 초기화
        Marble red = new Marble(true);
        Marble blue = new Marble(false);

        // 동서남북 순으로 0, 1, 2, 3이다.
        int[] direction = new int[10];

        // 움직일 방향 결정
        for(int i = 0; i < 10; i++) {
            direction[i] = n % 4;
            n /= 4;
        }

        int count = 0;

        for(int i = 0; i <= 10; i++) {
            // 10번을 시행했는데도 아무일이 없다면 빨간 구슬을 구멍에 넣는 것에 실패했다는 것이다
            if(i == 10) {
                count = 888;
                break;
            }
            // state == 1 := 빨강 공만 골인한 경우
            // state == 0 := 골인한 공이 없는 경우
            // state == -1 := 파란 공이 골인한 경우
            int state = moveMarble(direction[i], red, blue);

            // 구슬을 움직일때마다 시행횟수를 1증가시킨다
            count++;

            if(state == 1) {
                break;
            } else if(state == -1) {
                count = 777;
                break;
            }
        }

        return count;
    }

    public static int moveMarble(int direction, Marble red, Marble blue) {
        // first := true이면 빨간 구슬 먼저, false이면 파란 구슬 먼저 이동
        boolean first = firstMarble(direction, red, blue);
        boolean redGoal;
        boolean blueGoal;

        if(first) {
            redGoal = red.move(direction);
            blueGoal = blue.move(direction);
        } else {
            blueGoal = blue.move(direction);
            redGoal = red.move(direction);
        }

        if(blueGoal)
            return -1;
        else if(redGoal)
            return 1;
        else
            return 0;
    }

    public static boolean firstMarble(int direction, Marble red, Marble blue) {
        switch(direction) {
            case 0:
                return red.x > blue.x;
            case 1:
                return red.x < blue.x;
            case 2:
                return red.y > blue.y;
            case 3:
                return red.y < blue.y;
        }

        return true;
    }

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        sc.nextLine();

        initialBoard = new char[N][M];

        for(int i = 0; i < N; i++)
            initialBoard[i] = sc.nextLine().toCharArray();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(initialBoard[i][j] == 'R') {
                    initialRedY = i;
                    initialRedX = j;
                } else if (initialBoard[i][j] == 'B'){
                    initialBlueY = i;
                    initialBlueX = j;
                }
            }
        }

        int repeat = (int) Math.pow(4, 10);
        int min = 999;

        for(int n = 0; n < repeat; n++)
            min = Math.min(min, minMove(n));

        if(min > 10)
            min = -1;

        System.out.println(min);
    }
}
