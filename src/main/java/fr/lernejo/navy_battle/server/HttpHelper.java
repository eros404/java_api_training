package fr.lernejo.navy_battle.server;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

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
}
