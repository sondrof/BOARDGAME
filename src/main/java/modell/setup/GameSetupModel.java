package modell.setup;

import modell.players.Player;
import modell.players.PlayerToken;
import java.util.ArrayList;
import java.util.List;

import java.util.Set;

/**
 * Model class for managing game setup configuration.
 * This class handles player setup, game mode selection, and validation
 * of game configuration before starting.
 *
 * <p>The setup process includes:
 * <ul>
 *     <li>Adding and removing players with unique names and tokens</li>
 *     <li>Selecting game modes from predefined options</li>
 *     <li>Validating player count and configuration</li>
 *     <li>Creating player instances for game start</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 * GameSetupModel setup = new GameSetupModel("Ladder Game");
 * setup.addPlayerSetup("Player1", PlayerToken.RED);
 * setup.addPlayerSetup("Player2", PlayerToken.BLUE);
 * setup.setGameMode("Standard");
 * List<Player> players = setup.createPlayers();
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class GameSetupModel {
    /** List of player setup configurations */
    private final List<PlayerSetup> playerSetups;

    /** Currently selected game mode */
    private String selectedGameMode;

    /** Type of game being set up */
    private final String gameType;

    /** Minimum number of players required */
    private static final int MIN_PLAYERS = 2;

    /** Maximum number of players allowed */
    private static final int MAX_PLAYERS = 4;

    /** Set of valid game modes */
    private static final Set<String> VALID_GAME_MODES = Set.of("Standard", "Fun Board 1", "Fun Board 2", "Fun Board 3");

    /**
     * Creates a new game setup model with the specified game type.
     *
     * @param gameType the type of game being set up
     */
    public GameSetupModel(String gameType) {
        this.gameType = gameType;
        this.playerSetups = new ArrayList<>();
        this.selectedGameMode = "Standard";
    }

    /**
     * Adds a new player setup with the specified name and token.
     * Validates the player name, count, and uniqueness before adding.
     *
     * @param name the player's name
     * @param token the player's token
     * @throws IllegalArgumentException if the name is invalid or not unique, or if the token is not unique
     * @throws IllegalStateException if the maximum number of players is reached
     */
    public void addPlayerSetup(String name, PlayerToken token) throws IllegalArgumentException {
        validatePlayerName(name);
        validatePlayerCount();
        validateUniquePlayer(name, token);

        playerSetups.add(new PlayerSetup(name, token));
    }

    /**
     * Removes a player setup at the specified index.
     *
     * @param index the index of the player setup to remove
     */
    public void removePlayerSetup(int index) {
        if (index >= 0 && index < playerSetups.size()) {
            playerSetups.remove(index);
        }
    }

    /**
     * Sets the game mode to the specified value.
     *
     * @param gameMode the game mode to set
     * @throws IllegalArgumentException if the game mode is not valid
     */
    public void setGameMode(String gameMode) throws IllegalArgumentException {
        if (!VALID_GAME_MODES.contains(gameMode)) {
            throw new IllegalArgumentException("Invalid game mode: " + gameMode);
        }
        this.selectedGameMode = gameMode;
    }

    /**
     * Gets the currently selected game mode.
     *
     * @return the selected game mode
     */
    public String getGameMode() {
        return selectedGameMode;
    }

    /**
     * Gets the type of game being set up.
     *
     * @return the game type
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Creates player instances from the current setup.
     * Validates the player count before creating players.
     *
     * @return a list of created player instances
     * @throws IllegalStateException if the minimum number of players is not met
     */
    public List<Player> createPlayers() throws IllegalStateException {
        validatePlayerCount();
        List<Player> players = new ArrayList<>();
        for (PlayerSetup setup : playerSetups) {
            players.add(new Player(setup.getName(), setup.getToken()));
        }
        return players;
    }

    /**
     * Gets a copy of the current player setups.
     *
     * @return a list of player setups
     */
    public List<PlayerSetup> getPlayerSetups() {
        return new ArrayList<>(playerSetups);
    }

    /**
     * Validates that the player name is not empty and within length limits.
     *
     * @param name the player name to validate
     * @throws IllegalArgumentException if the name is invalid
     */
    private void validatePlayerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        if (name.length() > 20) {
            throw new IllegalArgumentException("Player name cannot be longer than 20 characters");
        }
    }

    /**
     * Validates that the maximum number of players has not been reached.
     *
     * @throws IllegalStateException if the maximum number of players is reached
     */
    private void validatePlayerCount() {
        if (playerSetups.size() >= MAX_PLAYERS) {
            throw new IllegalStateException("Maximum number of players (" + MAX_PLAYERS + ") reached");
        }
    }

    /**
     * Validates that the player name and token are unique.
     *
     * @param name the player name to validate
     * @param token the player token to validate
     * @throws IllegalArgumentException if the name or token is not unique
     */
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

    /**
     * Validates the entire game setup configuration.
     * Checks player count and game mode validity.
     *
     * @throws IllegalStateException if the setup is invalid
     */
    public void validateGameSetup() throws IllegalStateException {
        if (playerSetups.size() < MIN_PLAYERS) {
            throw new IllegalStateException("Minimum " + MIN_PLAYERS + " players required to start the game");
        }
        if (!VALID_GAME_MODES.contains(selectedGameMode)) {
            throw new IllegalStateException("Invalid game mode selected");
        }
    }

    /**
     * Inner class representing a player setup configuration.
     * Holds the player's name and token.
     */
    public static class PlayerSetup {
        /** The player's name */
        private final String name;

        /** The player's token */
        private final PlayerToken token;

        /**
         * Creates a new player setup with the specified name and token.
         *
         * @param name the player's name
         * @param token the player's token
         */
        public PlayerSetup(String name, PlayerToken token) {
            this.name = name;
            this.token = token;
        }

        /**
         * Gets the player's name.
         *
         * @return the player name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the player's token.
         *
         * @return the player token
         */
        public PlayerToken getToken() {
            return token;
        }
    }
}