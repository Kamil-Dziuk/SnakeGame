package pl.kamildziuk.snakegame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnLeft;
    @FXML
    private Button btnRight;

    private final static int POINT_SIZE = 20;
    private GraphicsContext graphicsContext;
    private SnakeGame snakeGame;

    public void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        Point head = new Point(3, 3);
        int xBound = (int) (canvas.getWidth() / POINT_SIZE);
        int yBound = (int) (canvas.getHeight() / POINT_SIZE);
        snakeGame = new SnakeGame(xBound, yBound);
        snakeGame.setDisplayBoardStrategy(() -> Platform.runLater(() -> {
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            drawPoint(snakeGame.getApple(), Color.RED);

            Snake snake = snakeGame.getSnake();
            drawPoint(snake.getHead(), Color.BLACK);
            snake.getBody()
                    .forEach(point -> drawPoint(point, Color.GREEN));
        }));
        setDirectionButtonsClickHandlers();
        Thread thread = new Thread(snakeGame::start);
        thread.setDaemon(true);
        thread.start();
    }
    private void setDirectionButtonsClickHandlers() {
        btnDown.setOnAction(event -> snakeGame.setSnakeDirection(Direction.DOWN));
        btnUp.setOnAction(event -> snakeGame.setSnakeDirection(Direction.UP));
        btnLeft.setOnAction(event -> snakeGame.setSnakeDirection(Direction.LEFT));
        btnRight.setOnAction(event -> snakeGame.setSnakeDirection(Direction.RIGHT));
    }
    private void drawPoint(Point point, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(point.getX() * POINT_SIZE, point.getY() * POINT_SIZE, POINT_SIZE, POINT_SIZE);
    }

}
