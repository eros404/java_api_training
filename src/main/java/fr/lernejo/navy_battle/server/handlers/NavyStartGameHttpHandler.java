package fr.lernejo.navy_battle.server.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.game.GameContext;
import fr.lernejo.navy_battle.game.entities.Opponent;
import fr.lernejo.navy_battle.server.HttpHelper;
import fr.lernejo.navy_battle.server.request_bodies.NavyStartGameBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NavyStartGameHttpHandler implements HttpHandler {
    private final HttpServer server;
    private final GameContext gameContext;
    public NavyStartGameHttpHandler(HttpServer server, GameContext gameContext) {
        this.server = server;
        this.gameContext = gameContext;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            new HttpHelper().send404(exchange);
        }
        else {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            try {
                NavyStartGameBody requestBody = new HttpHelper().parseStartGameBody(body);
                gameContext.newGame(new Opponent(requestBody.id, requestBody.url));
                sendResponse(exchange);
                gameContext.attack();
            } catch (Exception e) {
                new HttpHelper().send400(exchange);
            }
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
