package fr.lernejo.navy_battle.server;

public class NavyStartGameBody {
    public final String id;
    public final String url;
    public final String message;
    public NavyStartGameBody(String id, String url, String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }
}
