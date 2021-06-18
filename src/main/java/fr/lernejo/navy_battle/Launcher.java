package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.server.NavyPingHttpHandler;
import fr.lernejo.navy_battle.server.NavyStartGameHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Launcher {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(String.valueOf(Launcher.class));
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(args[0])), 0);
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.createContext("/ping", new NavyPingHttpHandler());
            server.createContext("/api/game/start", new NavyStartGameHttpHandler(server));
            server.start();
            if (args.length == 2) {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(args[1] + "/api/game/start"))
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + args[0] + "\", \"message\":\"I will crush you!\"}"))
                    .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
