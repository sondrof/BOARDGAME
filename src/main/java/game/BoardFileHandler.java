package game;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.BoardLoadException;
import exceptions.BoardSaveException;
import tiles.LadderTileLogic;
import tiles.TileLogic;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BoardFileHandler {
    private static final String DEFAULT_BOARD_FILE_PATH = "src/main/resources/board.json";

    public static void saveBoard(GameboardLogic gameboardLogic, TileLogic tileLogic) throws BoardSaveException {
        if (!(tileLogic instanceof LadderTileLogic)) {
            throw new BoardSaveException("BoardFileHandler requires LadderTileLogic", null);
        }
        saveBoard(gameboardLogic, (LadderTileLogic) tileLogic, DEFAULT_BOARD_FILE_PATH);
    }

    public static void saveBoard(GameboardLogic gameboardLogic, LadderTileLogic tileLogic, String filePath) throws BoardSaveException {
        try (FileWriter writer = new FileWriter(filePath)) {
            JsonObject boardJson = serializeBoardConfig(gameboardLogic, tileLogic);
            writer.write(boardJson.toString());
        } catch (IOException e) {
            throw new BoardSaveException("Failed to save board configuration to JSON file: " + filePath, e);
        }
    }

    private static JsonObject serializeBoardConfig(GameboardLogic gameboardLogic, LadderTileLogic tileLogic) {
        JsonObject boardJson = new JsonObject();
        boardJson.addProperty("boardSize", gameboardLogic.getBoardSize());

        JsonArray specialTilesArray = new JsonArray();
        Map<Integer, Integer> specialTiles = tileLogic.getLadderMap();

        for (Map.Entry<Integer, Integer> entry : specialTiles.entrySet()) {
            JsonObject tileJson = new JsonObject();
            tileJson.addProperty("tileNumber", entry.getKey());
            tileJson.addProperty("ladderValue", entry.getValue());
            specialTilesArray.add(tileJson);
        }

        boardJson.add("specialTiles", specialTilesArray);
        return boardJson;
    }

    public static BoardConfig loadBoard() throws BoardLoadException {
        return loadBoard(DEFAULT_BOARD_FILE_PATH);
    }

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

    public static class BoardConfig {
        private int boardSize;
        private Map<Integer, Integer> tileConfigs;

        public int getBoardSize() {
            return boardSize;
        }

        public void setBoardSize(int boardSize) {
            this.boardSize = boardSize;
        }

        public Map<Integer, Integer> getTileConfigs() {
            return tileConfigs;
        }

        public void setTileConfigs(Map<Integer, Integer> tileConfigs) {
            this.tileConfigs = tileConfigs;
        }
    }
}