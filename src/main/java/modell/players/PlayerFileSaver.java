package modell.players;

import modell.exceptions.PlayerSaveException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handles saving player data to CSV files.
 * This class provides methods to persist player information between game sessions.
 *
 * <p>Player data is stored in a CSV format with each line containing a player's
 * name, token, and position on the board. The file is stored at a predefined location
 * in the resources' directory.
 *
 * <p>Example usage:
 * <pre>
 * // Save players to file
 * List<Player> players = playerLogic.getPlayerList();
 * PlayerFileSaver.savePlayers(players);
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class PlayerFileSaver {
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
        savePlayers(players, PLAYER_FILE_PATH);
    }

    /**
     * Saves a list of players to a CSV file.
     * Each player's name, token, and position are written as a comma-separated line.
     *
     * @param players the list of players to save
     * @param filePath the path to the CSV file where player data is stored
     * @throws PlayerSaveException if an error occurs while writing to the file
     */
    public static void savePlayers(List<Player> players, String filePath) throws PlayerSaveException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Player player : players) {
                writer.println(player.getName() + "," + player.getToken().name() + "," + player.getPlayerPosition());
            }
        } catch (IOException e) {
            throw new PlayerSaveException("Failed to save players to CSV", e);
        }
    }
}