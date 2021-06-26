package fr.lernejo.navy_battle.game.entities;

import fr.lernejo.navy_battle.game.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Boat {
    public final List<Point> cells;

    public Boat(Point startPoint, Direction direction, int length) {
        cells = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            switch (direction) {
                case top -> cells.add(new Point(startPoint.x, startPoint.y + i));
                case bottom -> cells.add(new Point(startPoint.x, startPoint.y - i));
                case right -> cells.add(new Point(startPoint.x + i, startPoint.y));
                case left -> cells.add(new Point(startPoint.x - i, startPoint.y));
            }
        }
    }
}
