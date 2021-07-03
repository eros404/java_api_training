package fr.lernejo.navy_battle.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.GameContext;
import fr.lernejo.navy_battle.server.request_bodies.FireResponseBody;
import fr.lernejo.navy_battle.server.HttpHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NavyFireHttpHandler implements HttpHandler {
    final GameContext gameContext;

    public NavyFireHttpHandler(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            new HttpHelper().send404(exchange);
        }
        String cellParameter = new HttpHelper().getQueryParameters(exchange.getRequestURI().getQuery()).get("cell");
        if (cellParameter == null) {
            new HttpHelper().send400(exchange);
        }
        else {
            FireResponseBody result = gameContext.handleAttack(cellParameter);
            sendResponse(exchange, result);
            if (result.shipLeft) { gameContext.attack(); } else { gameContext.endGame(); }
        }
    }
    public void sendResponse(HttpExchange exchange, FireResponseBody response) throws IOException {
        String responseString = new ObjectMapper().writeValueAsString(response);
        exchange.getResponseHeaders().set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
        exchange.getResponseHeaders().set("User-Agent", "NavyPlayer/0.0");
        exchange.sendResponseHeaders(202, responseString.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseString.getBytes());
        }
    }
}
