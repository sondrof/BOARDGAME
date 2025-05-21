package modell.setup;

import modell.players.Player;
import modell.players.PlayerToken;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class GameSetupModel {
    private final List<PlayerSetup> playerSetups;
    private String selectedGameMode;
    private final String gameType;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private static final Set<String> VALID_GAME_MODES = Set.of("Standard", "Fun Board 1", "Fun Board 2", "Fun Board 3");

    public GameSetupModel(String gameType) {
        this.gameType = gameType;
        this.playerSetups = new ArrayList<>();
        this.selectedGameMode = "Standard";
    }

    public void addPlayerSetup(String name, PlayerToken token) throws IllegalArgumentException {
        validatePlayerName(name);
        validatePlayerCount();
        validateUniquePlayer(name, token);

        playerSetups.add(new PlayerSetup(name, token));
    }

    public void removePlayerSetup(int index) {
        if (index >= 0 && index < playerSetups.size()) {
            playerSetups.remove(index);
        }
    }

    public void setGameMode(String gameMode) throws IllegalArgumentException {
        if (!VALID_GAME_MODES.contains(gameMode)) {
            throw new IllegalArgumentException("Invalid game mode: " + gameMode);
        }
        this.selectedGameMode = gameMode;
    }

    public String getGameMode() {
        return selectedGameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public List<Player> createPlayers() throws IllegalStateException {
        validatePlayerCount();
        List<Player> players = new ArrayList<>();
        for (PlayerSetup setup : playerSetups) {
            players.add(new Player(setup.getName(), setup.getToken()));
        }
        return players;
    }

    public List<PlayerSetup> getPlayerSetups() {
        return new ArrayList<>(playerSetups);
    }

    private void validatePlayerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("Player name cannot be longer than 20 characters");
        }
    }

    private void validatePlayerCount() {
        if (playerSetups.size() >= MAX_PLAYERS) {
            throw new IllegalStateException("Maximum number of players (" + MAX_PLAYERS + ") reached");
        }
    }

    private void validateUniquePlayer(String name, PlayerToken token) {
        for (PlayerSetup setup : playerSetups) {
            if (setup.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("Player name must be unique");
            }
            if (setup.getToken() == token) {
                throw new IllegalArgumentException("Player token must be unique");
            }
        }
    }

    public void validateGameSetup() throws IllegalStateException {
        if (playerSetups.size() < MIN_PLAYERS) {
            throw new IllegalStateException("Minimum " + MIN_PLAYERS + " players required to start the game");
        }
        if (!VALID_GAME_MODES.contains(selectedGameMode)) {
            throw new IllegalStateException("Invalid game mode selected");
        }
    }

    public static class PlayerSetup {
        private final String name;
        private final PlayerToken token;

        public PlayerSetup(String name, PlayerToken token) {
            this.name = name;
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public PlayerToken getToken() {
            return token;
        }
    }
}