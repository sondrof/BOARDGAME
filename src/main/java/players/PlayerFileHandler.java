package players;

import exceptions.PlayerLoadException;
import exceptions.PlayerSaveException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PlayerFileHandler {

    private static final String PLAYER_FILE_PATH = "src/main/resources/players.csv";

  static void savePlayers(List<Player> players) throws PlayerSaveException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PLAYER_FILE_PATH))) {
            for (Player player : players) {
                writer.println(player.getPlayerName() + "," + player.getPlayerId());
            }
        } catch (IOException e) {
            throw new PlayerSaveException("Failed to save players to CSV", e);
        }
    }


    public static List<Player> loadPlayers() throws PlayerLoadException {
        List<Player> players = new ArrayList<>();
        File file = new File(PLAYER_FILE_PATH);

        if (!file.exists()) {
            return players; // Return empty list if file doesn't exist yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String name = parts[0];
                    int id = Integer.parseInt(parts[1]);
                    players.add(new Player(name, 0, id)); // Start at position 0
                }
            }
            return players;
        } catch (IOException | NumberFormatException e) {
            throw new PlayerLoadException("Failed to load players from CSV", e);
        }
    }


}