package fr.lernejo.navy_battle.server.request_bodies;

import fr.lernejo.navy_battle.game.Consequence;

public class FireResponseBody {
    public final Consequence consequence;
    public final boolean shipLeft;

    public FireResponseBody(Consequence consequence, boolean shipLeft) {
        this.consequence = consequence;
        this.shipLeft = shipLeft;
    }
}
