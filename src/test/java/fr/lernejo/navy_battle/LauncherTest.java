package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

class LauncherTest {
    @Test
    void launchParty() {
        Launcher.main(new  String[]{"9876"});
        Launcher.main(new String[]{"8080", "http://localhost:9876"});
    }
}
