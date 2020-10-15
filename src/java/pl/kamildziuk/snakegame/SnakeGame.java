package pl.kamildziuk.snakegame;


import java.util.Random;

public class SnakeGame {

    private int xBound;
    private int yBound;
    private Snake snake;
    private Point apple;
    private DisplayBoardStrategy displayBoardStrategy;

    public SnakeGame(int xBound, int yBound, Snake snake) {
        this.snake = snake;
        initBoard(xBound, yBound);
    }
    public SnakeGame(int xBound, int yBound) {
        Point head = new Point(0, 0);
        this.snake = new Snake(head);
        initBoard(xBound, yBound);
    }
    public Point getApple() {
        return apple;
    }

    public void setSnakeDirection(Direction direction) {
        snake.setDirection(direction);
    }

    public Snake getSnake() {
        return snake;
    }

    public void setDisplayBoardStrategy(DisplayBoardStrategy displayBoardStrategy) {
        this.displayBoardStrategy = displayBoardStrategy;
    }
    private void initBoard(int xBound, int yBound) {
        this.xBound = xBound;
        this.yBound = yBound;
        randomizeApple();
    }
    public void start() {
        if (displayBoardStrategy != null) {
            displayBoardStrategy.display();
        }
        while (!isSnakeOutOfBounds()) {
            snake.expand();
            if (!apple.equals(snake.getHead())) {
                snake.cutTail();
            } else {
                randomizeApple();
            }
            if (displayBoardStrategy != null) {
                displayBoardStrategy.display();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    private boolean isSnakeOutOfBounds() {
        int headX = snake.getHead().getX();
        int headY = snake.getHead().getY();
        return headX < 0 || headX >= xBound
                || headY < 0 || headY >= yBound;
    }
    public void randomizeApple() {
        Random random = new Random();
        do {
            int appleX = random.nextInt(xBound);
            int appleY = random.nextInt(yBound);
            apple = new Point(appleX, appleY);
        } while (snake.contains(apple));
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < yBound; y++) {
            stringBuilder.append('|');
            for (int x = 0; x < xBound; x++) {
                Point point = new Point(x, y);
                if (point.equals(snake.getHead())) {
                    stringBuilder.append('H');
                } else if (snake.getBody().contains(point)) {
                    stringBuilder.append('B');
                } else if (point.equals(apple)) {
                    stringBuilder.append('A');
                } else {
                    stringBuilder.append('_');
                }
            }
            stringBuilder.append("|\n");
        }
        return stringBuilder.toString();
    }
}
