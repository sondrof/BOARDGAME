package players;

import exceptions.PlayerLoadException;
import exceptions.PlayerSaveException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerFileHandler {

    private static final String PLAYER_FILE_PATH = "src/main/resources/players.csv";


    public static void savePlayers(List<Player> players) throws PlayerSaveException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PLAYER_FILE_PATH))) {
            for (Player player : players) {
                writer.println(player.getPlayerName() + "," + player.getPlayerToken() + "," + player.getPlayerPosition());
            }
        } catch (IOException e) {
            throw new PlayerSaveException("Failed to save players to CSV", e);
        }
    }


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
