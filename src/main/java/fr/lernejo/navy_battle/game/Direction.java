package fr.lernejo.navy_battle.game;

import java.awt.*;

public enum Direction {
    top,bottom,right,left;

    public Point moveTo(Point point) {
        switch (this) {
            case top -> point.y++;
            case bottom -> point.y--;
            case left -> point.x--;
            case right -> point.x++;
        }
        return point;
    }

    public Point moveAway(Point point) {
        switch (this) {
            case top -> point.y--;
            case bottom -> point.y++;
            case left -> point.x++;
            case right -> point.x--;
        }
        return point;
    }
}
