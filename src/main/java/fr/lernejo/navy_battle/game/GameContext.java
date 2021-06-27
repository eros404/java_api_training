package fr.lernejo.navy_battle.game;

import fr.lernejo.navy_battle.game.entities.Battleground;
import fr.lernejo.navy_battle.game.entities.Opponent;
import fr.lernejo.navy_battle.server.NavyHttpMessageHandler;
import fr.lernejo.navy_battle.server.request_bodies.FireResponseBody;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameContext {
    private final Battleground battleground;
    private final List<Opponent> opponents;
    private final List<Point> lastTurns;

    public GameContext() {
        this.battleground = new Battleground();
        lastTurns = new ArrayList<>();
        opponents = new ArrayList<>();
    }

    public Opponent getCurrentOpponent() {
        return opponents.get(0);
    }

    public void newGame(Opponent opponent) {
        opponents.add(opponent);
    }

    public void endGame() {
        opponents.remove(getCurrentOpponent());
    }

    public void attack() throws IOException {
        Point cell;
        do {
            cell = new Point(ThreadLocalRandom.current().nextInt(1, battleground.nbRowsAndColumns + 1), ThreadLocalRandom.current().nextInt(1, battleground.nbRowsAndColumns + 1));
        } while (lastTurns.contains(cell));
        lastTurns.add(cell);
        try {
            FireResponseBody result = new NavyHttpMessageHandler().sendFireRequest(getCurrentOpponent().url, new PointTraductor().fromPointToString(cell));
            if (result.shipLeft == false) {
                System.out.println("I won against " + getCurrentOpponent().id);
                endGame();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FireResponseBody handleAttack(String cell) {
        Consequence consequence = battleground.shootBoat(new PointTraductor().fromStringToPoint(cell));
        return new FireResponseBody(consequence, battleground.shipLeft());
    }
}
