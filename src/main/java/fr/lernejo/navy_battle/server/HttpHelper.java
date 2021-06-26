package fr.lernejo.navy_battle.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.game.Consequence;
import fr.lernejo.navy_battle.game.GameContext;
import fr.lernejo.navy_battle.server.request_bodies.FireResponseBody;
import fr.lernejo.navy_battle.server.request_bodies.NavyStartGameBody;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class HttpHelper {

    public void send404(HttpExchange exchange) throws IOException {
        String response = "Not Found";
        exchange.sendResponseHeaders(404, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    public void send400(HttpExchange exchange) throws IOException {
        String response = "Bad Request";
        exchange.sendResponseHeaders(400, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public Map<String, String> getQueryParameters(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public NavyStartGameBody parseStartGameBody(String body) throws IOException {
        JsonNode nodeBody = new ObjectMapper().readTree(body);
        return new NavyStartGameBody(nodeBody.get("id").asText(), nodeBody.get("url").asText(), nodeBody.get("message").asText());
    }

    public FireResponseBody parseFireResponseBody(String body) throws IOException {
        JsonNode nodeBody = new ObjectMapper().readTree(body);
        return new FireResponseBody(Consequence.valueOf(nodeBody.get("consequence").asText()), nodeBody.get("shipLeft").asBoolean());
    }
}
