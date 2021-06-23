package fr.lernejo.navy_battle.server.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.server.HttpHelper;
import fr.lernejo.navy_battle.server.request_bodies.NavyStartGameBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NavyStartGameHttpHandler implements HttpHandler {
    final HttpServer server;
    public NavyStartGameHttpHandler(HttpServer server) {
        this.server = server;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            new HttpHelper().send404(exchange);
        }
        else {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            try {
                JsonNode nodeBody = new ObjectMapper().readTree(body);
                NavyStartGameBody requestBody = new NavyStartGameBody(nodeBody.get("id").asText(), nodeBody.get("url").asText(), nodeBody.get("message").asText());
            } catch (Exception e) {
                new HttpHelper().send400(exchange);
            }
            sendResponse(exchange);
        }
    }
    public void sendResponse(HttpExchange exchange) throws IOException {
        String response = new ObjectMapper().writeValueAsString(new NavyStartGameBody("2aca7611-0ae4-49f3-bf63-75bef4769028", "http://localhost:" + server.getAddress().getPort(), "May the best code win"));
        exchange.getResponseHeaders().set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
        exchange.sendResponseHeaders(202, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
