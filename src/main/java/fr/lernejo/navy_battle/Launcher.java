package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.server.NavyPingHttpHandler;
import fr.lernejo.navy_battle.server.NavyStartGameHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Launcher {
    public static void main(String[] args){
        Logger logger = Logger.getLogger(String.valueOf(Launcher.class));
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.createContext("/ping", new NavyPingHttpHandler());
            server.createContext("/api/game/start", new NavyStartGameHttpHandler(server));
            server.start();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
