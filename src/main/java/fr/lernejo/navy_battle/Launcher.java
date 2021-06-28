package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.game.GameContext;
import fr.lernejo.navy_battle.game.entities.Opponent;
import fr.lernejo.navy_battle.server.NavyHttpMessageHandler;
import fr.lernejo.navy_battle.server.NavyHttpServer;
import fr.lernejo.navy_battle.server.request_bodies.NavyStartGameBody;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        try {
            GameContext gameContext = new GameContext();
            NavyHttpServer navyServer = new NavyHttpServer(Integer.parseInt(args[0]), gameContext);
            navyServer.startServer();
            if (args.length == 2) {
                NavyStartGameBody startGameBody =  new NavyHttpMessageHandler().consumeApi(args[0], args[1]);
                gameContext.newGame(new Opponent(startGameBody.id, startGameBody.url));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
