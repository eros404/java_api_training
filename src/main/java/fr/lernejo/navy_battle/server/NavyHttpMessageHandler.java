package fr.lernejo.navy_battle.server;

import fr.lernejo.navy_battle.server.request_bodies.FireResponseBody;
import fr.lernejo.navy_battle.server.request_bodies.NavyStartGameBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NavyHttpMessageHandler {
    public NavyStartGameBody consumeApi(String port, String urlToConsume) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlToConsume + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .setHeader("User-Agent", "NavyPlayer/0.0")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"I will crush you!\"}"))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 202) {
            return new HttpHelper().parseStartGameBody(response.body());
        }
        return null;
    }
    public FireResponseBody sendFireRequest(String url, String cell) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "/api/game/fire?cell=" + cell))
            .setHeader("Accept", "application/json")
            .setHeader("User-Agent", "NavyPlayer/0.0")
            .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 202) {
            return new HttpHelper().parseFireResponseBody(response.body());
        }
        return null;
    }
}
