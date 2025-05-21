package modell.setup;

import modell.players.Player;
import java.util.ArrayList;
import java.util.List;

public class GameSetupModel {
    private final List<PlayerSetup> playerSetups;
    private String selectedGameMode;
    private final String gameType;

    public GameSetupModel(String gameType) {
        this.gameType = gameType;
        this.playerSetups = new ArrayList<>();
        this.selectedGameMode = "Standard";
    }

    public void addPlayerSetup(String name, String token) {
        playerSetups.add(new PlayerSetup(name, token));
    }

    public void removePlayerSetup(int index) {
        if (index >= 0 && index < playerSetups.size()) {
            playerSetups.remove(index);
        }
    }

    public void setGameMode(String gameMode) {
        this.selectedGameMode = gameMode;
    }

    public String getGameMode() {
        return selectedGameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        for (PlayerSetup setup : playerSetups) {
            players.add(new Player(setup.getName(), setup.getToken()));
        }
        return players;
    }

    public List<PlayerSetup> getPlayerSetups() {
        return new ArrayList<>(playerSetups);
    }

    public static class PlayerSetup {
        private final String name;
        private final String token;

        public PlayerSetup(String name, String token) {
            this.name = name;
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public String getToken() {
            return token;
        }
    }
}