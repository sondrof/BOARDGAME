package modell.players;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modell.exceptions.PlayerLoadException;


/**
 * Handles loading player data from CSV files.
 * This class provides methods to load player information from previous game sessions.
 *
 * <p>Player data is loaded from a CSV format where each line contains a player's
 * name, token, and position on the board. The file is stored at a predefined location
 * in the resources' directory.
 *
 * <p>Example usage:
 * <pre>
 * // Load players from file
 * List<Player> loadedPlayers = PlayerFileLoader.loadPlayers();
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class PlayerFileLoader {
  /** The path to the CSV file where player data is stored. */
  private static final String PLAYER_FILE_PATH = "src/main/resources/players.csv";

  /**
   * Loads players from a CSV file.
   * Creates a new Player object for each valid line in the file.
   * If the file doesn't exist, returns an empty list.
   *
   * @return a list of players loaded from the file
   * @throws PlayerLoadException if an error occurs while reading from the file
   */
  public static List<Player> loadPlayers() throws PlayerLoadException {
    return loadPlayers(PLAYER_FILE_PATH);
  }

  /**
   * Loads players from a CSV file.
   * Creates a new Player object for each valid line in the file.
   * If the file doesn't exist, returns an empty list.
   *
   * @param filePath the path to the CSV file
   * @return a list of players loaded from the file
   * @throws PlayerLoadException if an error occurs while reading from the file
   */
  public static List<Player> loadPlayers(String filePath) throws PlayerLoadException {
    List<Player> players = new ArrayList<>();
    File file = new File(filePath);

    if (!file.exists()) {
      return players;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        if (parts.length == 3) {
          String name = parts[0];
          String tokenStr = parts[1];
          int position = Integer.parseInt(parts[2].trim());

          try {
            PlayerToken token = PlayerToken.valueOf(tokenStr);
            Player player = new Player(name, token);
            player.setPlayerPosition(position);
            players.add(player);
          } catch (IllegalArgumentException e) {
            // If token is not found in enum, use DEFAULT
            Player player = new Player(name, PlayerToken.DEFAULT);
            player.setPlayerPosition(position);
            players.add(player);
          }
        }
      }
      return players;
    } catch (IOException | NumberFormatException e) {
      throw new PlayerLoadException("Failed to load players from CSV", e);
    }
  }

  private PlayerFileLoader() {}
}