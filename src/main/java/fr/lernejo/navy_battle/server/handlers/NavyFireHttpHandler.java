package fr.lernejo.navy_battle.server.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.Consequence;
import fr.lernejo.navy_battle.server.request_bodies.FireResponseBody;
import fr.lernejo.navy_battle.server.HttpHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NavyFireHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            new HttpHelper().send404(exchange);
        }
        String cellParameter = new HttpHelper().getQueryParameters(exchange.getRequestURI().getQuery()).get("cell");
        if (cellParameter == null) {
            new HttpHelper().send400(exchange);
        }
        sendResponse(exchange);
    }
    public void sendResponse(HttpExchange exchange) throws IOException {
        String response = new ObjectMapper().writeValueAsString(new FireResponseBody(Consequence.sunk, false));
        exchange.getResponseHeaders().set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
        exchange.sendResponseHeaders(202, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
