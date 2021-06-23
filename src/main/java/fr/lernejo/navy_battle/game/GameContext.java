package fr.lernejo.navy_battle.game;

import fr.lernejo.navy_battle.game.entities.Battleground;

public class GameContext {
    private final Battleground battleground;
    private final String OpponentId;

    public GameContext(String opponentId) {
        OpponentId = opponentId;
        this.battleground = new Battleground();
    }
}
