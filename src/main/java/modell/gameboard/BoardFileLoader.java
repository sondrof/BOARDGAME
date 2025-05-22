package modell.gameboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import modell.exceptions.BoardLoadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles loading board configurations from JSON files.
 *
 * @author didrik
 * @version 1.0
 */
public class BoardFileLoader {

    /**
     * Loads a board configuration from the default board.json in resources.
     *
     * @return a BoardConfig object containing the loaded board configuration
     * @throws BoardLoadException if the file is missing or cannot be parsed
     */
    public static BoardConfig loadBoard() throws BoardLoadException {
        return loadBoard("boards/board.json");
    }

    /**
     * Loads a board configuration from a specified file in the resources folder.
     *
     * @param filePath the relative path inside the resources folder (e.g. "boards/custom_board.json")
     * @return a BoardConfig containing board size and special tile mappings
     * @throws BoardLoadException if the file is missing or cannot be parsed
     */
    public static BoardConfig loadBoard(String filePath) throws BoardLoadException {
        try (InputStream inputStream = BoardFileLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new BoardLoadException("Board configuration file not found: " + filePath, null);
            }

            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            JsonObject boardJson = JsonParser.parseReader(reader).getAsJsonObject();

            int boardSize = boardJson.get("boardSize").getAsInt();
            JsonArray specialTilesArray = boardJson.getAsJsonArray("specialTiles");

            Map<Integer, Integer> specialTiles = new HashMap<>();
            for (JsonElement tileElement : specialTilesArray) {
                JsonObject tileJson = tileElement.getAsJsonObject();
                int tileNumber = tileJson.get("tileNumber").getAsInt();
                int specialValue = tileJson.get("specialValue").getAsInt(); // ✅ match your JSON
                specialTiles.put(tileNumber, specialValue);
            }

            BoardConfig config = new BoardConfig();
            config.setBoardSize(boardSize);
            config.setTileConfigs(specialTiles);
            return config;

        } catch (Exception e) {
            throw new BoardLoadException("Failed to load board configuration from " + filePath, e);
        }
    }
}
