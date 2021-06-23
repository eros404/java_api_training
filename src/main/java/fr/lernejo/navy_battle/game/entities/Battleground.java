package fr.lernejo.navy_battle.game.entities;

import fr.lernejo.navy_battle.game.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Battleground {
    private final List<Boat> boats;
    private final int nbRowsAndColumns = 10;

    public Battleground() {
        boats = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            placeBoat(5);
        }
        for (int i = 0; i < 1; i++) {
            placeBoat(4);
        }
        for (int i = 0; i < 2; i++) {
            placeBoat(3);
        }
        for (int i = 0; i < 1; i++) {
            placeBoat(2);
        }
    }

    private void placeBoat(int length) {
        Point startPoint = new Point();
        Direction direction;
        do {
            startPoint.x = ThreadLocalRandom.current().nextInt(1, nbRowsAndColumns + 1);
            startPoint.y = ThreadLocalRandom.current().nextInt(1, nbRowsAndColumns + 1);
            direction = getEndDirection(startPoint, length);
        } while (direction == null);
        boats.add(new Boat(startPoint, direction, length));
    }

    private Direction getEndDirection(Point startPoint, int length) {
        List<Direction> directions = new ArrayList(Arrays.asList(Direction.values()));
        Collections.shuffle(directions);
        for (Direction dir: directions) {
            Point endPoint = (Point) startPoint.clone();
            for (int i = 0; i < length; i++) {
                if (!isEmpty(moveTo(endPoint, dir))) {
                    break;
                }
                if (i == length - 1) {
                    return dir;
                }
            }
        }
        return null;
    }

    private boolean isEmpty(Point point) {
        return point.x > 0 && point.x <= nbRowsAndColumns && point.y > 0 && point.y <= nbRowsAndColumns && boats.stream().map(boat -> boat.cells).noneMatch(cells -> cells.contains(point));
    }

    private Point moveTo(Point point, Direction direction) {
        switch (direction) {
            case top -> point.y++;
            case bottom -> point.y--;
            case left -> point.x--;
            case right -> point.x++;
        }
        return point;
    }
}
