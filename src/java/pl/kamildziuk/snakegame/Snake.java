package pl.kamildziuk.snakegame;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private Point head;

    private List<Point> body;

    private Direction direction;

    public Snake(Point head, List<Point> body, Direction direction) {
        this.head = head;
        this.body = new ArrayList<>(body);
        this.direction = direction;
    }
    public Snake(Point head) {
        this.head = head;
        this.body = new ArrayList<>();
        this.direction = Direction.RIGHT;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getHead() {
        return head;
    }

    public List<Point> getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
    public void move() {
        expand();
        cutTail();
    }
    public void expand() {
        body.add(0, head);
        head = head.moveTo(direction);
    }
    public void cutTail() {
        body.remove(body.size() - 1);
    }

    public boolean contains(Point point) {
        return head.equals(point) || body.contains(point);
    }
}
