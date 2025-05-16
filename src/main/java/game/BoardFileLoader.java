package game;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.BoardLoadException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles loading board configurations from JSON files.
 * This class provides methods to load board information from previous game sessions.
 *
 * <p>Board data is loaded from a JSON format containing:
 * <ul>
 *     <li>Board size: The total number of tiles on the board</li>
 *     <li>Special tiles: An array of tile configurations, each containing a tile number and its ladder value</li>
 * </ul>
 *
 * <p>The file is stored at a predefined location in the resources' directory.
 *
 * <p>Example usage:
 * <pre>
 * // Load board configuration
 * BoardConfig config = BoardFileLoader.loadBoard();
 * int boardSize = config.getBoardSize();
 * Map<Integer, Integer> specialTiles = config.getTileConfigs();
 * </pre>
 */
public class BoardFileLoader {
    /** The path to the JSON file where board data is stored */
    private static final String DEFAULT_BOARD_FILE_PATH = "src/main/resources/board.json";

    /**
     * Loads a board configuration from the default JSON file.
     * Creates a new BoardConfig object with the board size and special tile configurations.
     *
     * @return a BoardConfig object containing the loaded board configuration
     * @throws BoardLoadException if an error occurs while reading from the file
     */
    public static BoardConfig loadBoard() throws BoardLoadException {
        return loadBoard(DEFAULT_BOARD_FILE_PATH);
    }

    /**
     * Loads a board configuration from a specified JSON file.
     * Creates a new BoardConfig object with the board size and special tile configurations.
     *
     * @param filePath the path to the JSON file containing the board configuration
     * @return a BoardConfig object containing the loaded board configuration
     * @throws BoardLoadException if an error occurs while reading from the file
     */
    public static BoardConfig loadBoard(String filePath) throws BoardLoadException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new BoardLoadException("Board configuration file not found: " + filePath, null);
        }

        try (FileReader reader = new FileReader(file)) {
            StringBuilder jsonContent = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                jsonContent.append((char) character);
            }

            return deserializeBoardConfig(jsonContent.toString());
        } catch (IOException e) {
            throw new BoardLoadException("Failed to load board configuration from JSON file: " + filePath, e);
        } catch (Exception e) {
            throw new BoardLoadException("Error parsing board configuration", e);
        }
    }

    /**
     * Deserializes a JSON string into a BoardConfig object.
     * Parses the board size and special tile configurations from the JSON string.
     *
     * @param jsonString the JSON string containing the board configuration
     * @return a BoardConfig object containing the parsed board configuration
     */
    private static BoardConfig deserializeBoardConfig(String jsonString) {
        JsonObject boardJson = JsonParser.parseString(jsonString).getAsJsonObject();

        int boardSize = boardJson.get("boardSize").getAsInt();

        Map<Integer, Integer> specialTiles = new HashMap<>();
        JsonArray specialTilesArray = boardJson.getAsJsonArray("specialTiles");

        for (JsonElement tileElement : specialTilesArray) {
            JsonObject tileJson = tileElement.getAsJsonObject();
            int tileNumber = tileJson.get("tileNumber").getAsInt();
            int ladderValue = tileJson.get("ladderValue").getAsInt();
            specialTiles.put(tileNumber, ladderValue);
        }

        BoardConfig config = new BoardConfig();
        config.setBoardSize(boardSize);
        config.setTileConfigs(specialTiles);
        return config;
    }
}