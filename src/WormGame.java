import java.util.LinkedList;
        import java.util.Queue;
        import java.util.Random;
        import java.util.Scanner;

public class WormGame {
    public static final int BOARD_SIZE = 20; // 게임 보드 크기
    public static final char EMPTY_CELL = '.';
    public static final char FOOD_CELL = '*';
    public static final char WORM_CELL = '@';

    private char[][] board; // 게임 보드
    private Queue<Point> worm; // 지렁이의 몸

    private Point food; // 먹이의 위치
    private int directionX; // 지렁이의 방향 (x좌표)
    private int directionY; // 지렁이의 방향 (y좌표)

    private int score; // 점수

    public WormGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        worm = new LinkedList<>();
        initializeBoard();
        initializeWorm();
        placeFood();
        score = 0;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private void initializeWorm() {
        int startX = BOARD_SIZE / 2;
        int startY = BOARD_SIZE / 2;
        worm.add(new Point(startX, startY));
        board[startX][startY] = WORM_CELL;
        directionX = 0;
        directionY = -1;
    }

    private void placeFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(BOARD_SIZE);
            y = random.nextInt(BOARD_SIZE);
        } while (board[x][y] != EMPTY_CELL);

        food = new Point(x, y);
        board[x][y] = FOOD_CELL;
    }

    private void moveWorm() {
        Point head = worm.peek();
        int newX = head.getX() + directionX;
        int newY = head.getY() + directionY;

        // 벽에 부딪혔을 때 게임 종료
        if (newX < 0 || newX >= BOARD_SIZE || newY < 0 || newY >= BOARD_SIZE) {
            System.out.println("게임 오버! 점수: " + score);
            System.exit(0);
        }

        // 먹이를 먹었을 때
        if (newX == food.getX() && newY == food.getY()) {
            score += 10;
            worm.add(new Point(newX, newY));
            board[newX][newY] = WORM_CELL;
            placeFood();
        } else {
            Point tail = worm.poll();
            board[tail.getX()][tail.getY()] = EMPTY_CELL;
            worm.add(new Point(newX, newY));
            board[newX][newY] = WORM_CELL;
        }
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();
            System.out.println("점수: " + score);
            System.out.print("이동 방향을 입력하세요 (W: 위, A: 왼쪽, S: 아래, D: 오른쪽): ");
            String input = scanner.next().trim().toLowerCase();

            switch (input) {
                case "w":
                    directionX = -1;
                    directionY = 0;
                    break;
                case "a":
                    directionX = 0;
                    directionY = -1;
                    break;
                case "s":
                    directionX = 1;
                    directionY = 0;
                    break;
                case "d":
                    directionX = 0;
                    directionY = 1;
                    break;
                default:
                    System.out.println("유효하지 않은 입력입니다. 다시 입력해주세요.");
                    continue;
            }

            moveWorm();
        }
    }

    public static void main(String[] args) {
        WormGame game = new WormGame();
        game.play();
    }
}

class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

