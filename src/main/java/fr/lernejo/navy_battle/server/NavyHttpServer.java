package fr.lernejo.navy_battle.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.game.GameContext;
import fr.lernejo.navy_battle.server.handlers.NavyFireHttpHandler;
import fr.lernejo.navy_battle.server.handlers.NavyPingHttpHandler;
import fr.lernejo.navy_battle.server.handlers.NavyStartGameHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class NavyHttpServer {
    private final HttpServer httpServer;
    private final GameContext gameContext;

    public NavyHttpServer(int port, GameContext gameContext) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.gameContext = gameContext;
        httpServer.setExecutor(Executors.newFixedThreadPool(1));
        httpServer.createContext("/ping", new NavyPingHttpHandler());
        httpServer.createContext("/api/game/start", new NavyStartGameHttpHandler(httpServer, gameContext));
        httpServer.createContext("/api/game/fire", new NavyFireHttpHandler(gameContext));
    }

    public void startServer() {
        httpServer.start();
    }
}
