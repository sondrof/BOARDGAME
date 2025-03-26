package players;

import exceptions.PlayerLoadException;
import exceptions.PlayerSaveException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles saving and loading player data to/from CSV files.
 * This class provides methods to persist player information between game sessions,
 * allowing games to be saved and resumed later.
 *
 * <p>Player data is stored in a CSV format with each line containing a player's
 * name, token, and position on the board. The file is stored at a predefined location
 * in the resources' directory.
 *
 * <p>Example usage:
 * <pre>
 * // Save players to file
 * List<Player> players = playerLogic.getPlayerList();
 * PlayerFileHandler.savePlayers(players);
 *
 * // Load players from file
 * List<Player> loadedPlayers = PlayerFileHandler.loadPlayers();
 * </pre>
 */
public class PlayerFileHandler {

    /** The path to the CSV file where player data is stored */
    private static final String PLAYER_FILE_PATH = "src/main/resources/players.csv";


    /**
     * Saves a list of players to a CSV file.
     * Each player's name, token, and position are written as a comma-separated line.
     *
     * @param players the list of players to save
     * @throws PlayerSaveException if an error occurs while writing to the file
     */
    public static void savePlayers(List<Player> players) throws PlayerSaveException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PLAYER_FILE_PATH))) {
            for (Player player : players) {
                writer.println(player.getPlayerName() + "," + player.getPlayerToken() + "," + player.getPlayerPosition());
            }
        } catch (IOException e) {
            throw new PlayerSaveException("Failed to save players to CSV", e);
        }
    }

    /**
     * Loads players from a CSV file.
     * Creates a new Player object for each valid line in the file.
     * If the file doesn't exist, returns an empty list.
     *
     * @return a list of players loaded from the file
     * @throws PlayerLoadException if an error occurs while reading from the file
     */
    public static List<Player> loadPlayers() throws PlayerLoadException {
        List<Player> players = new ArrayList<>();
        File file = new File(PLAYER_FILE_PATH);

        if (!file.exists()) {
            return players;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");


                if (parts.length == 3) {
                    String name = parts[0];
                    String token = parts[1];
                    int position = Integer.parseInt(parts[2]);

                    Player player = new Player(name, token);
                    player.setPlayerPosition(position);
                    players.add(player);
                }
            }
            return players;
        } catch (IOException | NumberFormatException e) {
            throw new PlayerLoadException("Failed to load players from CSV", e);
        }
    }
}
