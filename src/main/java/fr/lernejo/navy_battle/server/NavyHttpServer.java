package fr.lernejo.navy_battle.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.server.handlers.NavyFireHttpHandler;
import fr.lernejo.navy_battle.server.handlers.NavyPingHttpHandler;
import fr.lernejo.navy_battle.server.handlers.NavyStartGameHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class NavyHttpServer {
    private final HttpServer httpServer;

    public NavyHttpServer(int port) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.createContext("/ping", new NavyPingHttpHandler());
        httpServer.createContext("/api/game/start", new NavyStartGameHttpHandler(httpServer));
        httpServer.createContext("/api/game/fire", new NavyFireHttpHandler());
    }

    public void startServer() {
        httpServer.start();
    }
}
