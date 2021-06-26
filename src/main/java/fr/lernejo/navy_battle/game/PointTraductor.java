package fr.lernejo.navy_battle.game;

import java.awt.*;
import java.util.Arrays;

public class PointTraductor {
    private final Character[] letters = new Character[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    public String fromPointToString(Point cell) {
        return letters[cell.x - 1] + String.valueOf(cell.y);
    }
    public Point fromStringToPoint(String cell) {
        int x = Arrays.asList(letters).indexOf(cell.charAt(0)) + 1;
        int y = Integer.parseInt(cell.substring(1));
        return new Point(x, y);
    }
}
