package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.game.GameContext;
import fr.lernejo.navy_battle.server.HttpHelper;
import fr.lernejo.navy_battle.server.NavyHttpServer;

import java.io.IOException;
import java.net.http.HttpResponse;

public class Launcher {
    public static void main(String[] args) {
        try {
            GameContext gameContext = new GameContext("");
            NavyHttpServer navyServer = new NavyHttpServer(Integer.parseInt(args[0]));
            navyServer.startServer();
            if (args.length == 2) {
                HttpResponse<String> response = new HttpHelper().consumeApi(args[0], args[1]);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
