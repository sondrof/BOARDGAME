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

public class BoardFileLoader {
    private static final String DEFAULT_BOARD_FILE_PATH = "src/main/resources/board.json";

    public static BoardFileHandler.BoardConfig loadBoard() throws BoardLoadException {
        return loadBoard(DEFAULT_BOARD_FILE_PATH);
    }

    public static BoardFileHandler.BoardConfig loadBoard(String filePath) throws BoardLoadException {
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

    private static BoardFileHandler.BoardConfig deserializeBoardConfig(String jsonString) {
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

        BoardFileHandler.BoardConfig config = new BoardFileHandler.BoardConfig();
        config.setBoardSize(boardSize);
        config.setTileConfigs(specialTiles);
        return config;
    }
}
