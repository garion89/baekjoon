/*
input
10
8 8 4 16 32 0 0 8 8 8
8 8 4 0 0 8 0 0 0 0
16 0 0 16 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 16
0 0 0 0 0 0 0 0 0 2

output
128
 */

/*
    회고
    1. initialBoard.clone()으로 깊은 복사가 될줄 알았는데 안되어 한참 시간을 날렸다. 이중 배열의 경우 일일이 복사를 해주어야 한다.
    2. 다 잘해놓고 moveOne에서 계속 움직여야 하는 상황에서 end를 true로 만들고 움직이지 말아야 하는 상황에서 end를 false로 만들어서 3시간을 헤맸다.
       특히 어떤 함수의 종료 조건 등을 유심히 살펴보자.
 */

import java.util.Scanner;

public class _12100_2048_Easy_lv2 {
    public static int N;
    public static boolean[][] united;
    public static int[][] initialBoard;

    public static int maxValueOf(int[][] arr) {
        int max = 0;

        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                max = max > arr[i][j] ? max : arr[i][j];

        return max;
    }

    public static int[][] move2048(int K, int num) {
        int[][] board = new int[N][N];

        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                board[i][j] = initialBoard[i][j];

        int[] direction = new int[K];

        for(int n = 0; n < K; n++) {
            direction[n] = num % 4;
            num /= 4;
        }

        for(int n = 0; n < K; n++)
            moveBoard(direction[n], board);

        return board;
    }

    public static void moveBoard(int direction, int[][] board) {
        united = new boolean[N][N];

        // 0쪽 방향이 아래이고 왼쪽임. 1사분면을 생각하면 됨
        switch(direction) {
            case 0: // 아래로 이동
                for(int y = 0; y < N; y++)
                    for(int x = 0; x < N; x++)
                        moveBlock(direction, board, y, x);
                break;
            case 1: // 위로 이동
                for(int y = N-1; 0 <= y; y--)
                    for(int x = 0; x < N; x++)
                        moveBlock(direction, board, y, x);
                break;
            case 2: // 왼쪽으로 이동
                for(int y = 0; y < N; y++)
                    for(int x = 0; x < N; x++)
                        moveBlock(direction, board, y, x);
                break;
            case 3: // 오른쪽으로 이동
                for(int y = 0; y < N; y++)
                    for(int x = N-1; 0 <= x; x--)
                        moveBlock(direction, board, y, x);
                break;
        }
    }
    
    // 현재 위치의 블락 이동이 끝났으면 true, 아니면 false를 반환하는 함수
    public static boolean moveOne(int[][] board, int y, int x, int dy, int dx) {
        // 1. 옮기려고 하는 위치에 블락이 없는 경우
        if (board[y+dy][x+dx] == 0) {
            board[y+dy][x+dx] = board[y][x];
            board[y][x] = 0;
            return false;
        }
        // 2. 옮기려고 하는 위치에 블락이 있는 경우
        // 2.1 합쳐진 적이 없는 블락이면서 충돌되는 블락의 숫자가 같은 경우
        if(!united[y+dy][x+dx] && board[y][x] == board[y+dy][x+dx]) {
            board[y][x] = 0;
            board[y+dy][x+dx] *= 2;
            united[y+dy][x+dx] = true;
        }
        // 2.2 합쳐진 적이 있는 블락이거나 충돌되는 블락의 숫자가 다른 경우
        // 아무것도 하지 않으면 되고 더이상 이동하지 않으면 된다
        return true;
    }

    public static void moveBlock(int direction, int[][] board, int y, int x) {
        // 옮기려는 블락이 비어있으면 바로 종료한다
        if(board[y][x] == 0)
            return;

        switch(direction) {
            case 0: // 아래로 이동
                for(int ptr = y; 0 < ptr; ptr--) {
                    boolean end = moveOne(board, ptr, x, -1, 0);
                    if(end) break;
                }
                break;
            case 1: // 위로 이동
                for(int ptr = y; ptr < N-1; ptr++) {
                    boolean end = moveOne(board, ptr, x, 1, 0);
                    if(end) break;
                }
                break;
            case 2: // 왼쪽으로 이동
                for(int ptr = x; 0 < ptr; ptr--) {
                    boolean end = moveOne(board, y, ptr, 0, -1);
                    if(end) break;
                }
                break;
            case 3: // 오른쪽으로 이동
                for(int ptr = x; ptr < N-1; ptr++) {
                    boolean end = moveOne(board, y, ptr, 0, 1);
                    if(end) break;
                }
                break;
        }
    }

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();

        initialBoard = new int[N][N];

        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                initialBoard[i][j] = sc.nextInt();

        int max = 0;

        for(int num = 0; num < 1024; num++)
            max = Math.max(max, maxValueOf(move2048(5, num)));

        System.out.println(max);
    }
}
