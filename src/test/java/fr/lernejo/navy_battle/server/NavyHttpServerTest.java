package fr.lernejo.navy_battle.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.game.GameContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class NavyHttpServerTest {

    @Test
    void test404() throws IOException, InterruptedException {
        NavyHttpServer server = new NavyHttpServer(9855, new GameContext());
        server.startServer();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .setHeader("User-Agent", "NavyPlayer/0.0")
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(), 404);
    }

    @Test
    void test400() throws IOException, InterruptedException {
        NavyHttpServer server = new NavyHttpServer(9856, new GameContext());
        server.startServer();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/api/game/fire"))
            .setHeader("Accept", "application/json")
            .setHeader("User-Agent", "NavyPlayer/0.0")
            .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(), 400);
    }
}
