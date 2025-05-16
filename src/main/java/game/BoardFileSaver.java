package game;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import exceptions.BoardSaveException;
import tiles.LadderTileLogic;
import tiles.TileLogic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class BoardFileSaver {

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
}
