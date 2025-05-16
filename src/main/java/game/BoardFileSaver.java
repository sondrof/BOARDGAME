package game;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import exceptions.BoardSaveException;
import tiles.LadderTileLogic;
import tiles.TileLogic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Handles saving board configurations to JSON files.
 * This class provides methods to persist board information between game sessions.
 *
 * <p>Board data is stored in a JSON format containing:
 * <ul>
 *     <li>Board size: The total number of tiles on the board</li>
 *     <li>Special tiles: An array of tile configurations, each containing a tile number and its ladder value</li>
 * </ul>
 *
 * <p>The file is stored at a predefined location in the resources' directory.
 *
 * <p>Example usage:
 * <pre>
 * // Save board configuration
 * GameboardLogic gameboardLogic = new GameboardLogic();
 * LadderTileLogic tileLogic = new LadderTileLogic();
 * BoardFileSaver.saveBoard(gameboardLogic, tileLogic);
 * </pre>
 */
public class BoardFileSaver {
    /** The path to the JSON file where board data is stored */
    private static final String DEFAULT_BOARD_FILE_PATH = "src/main/resources/board.json";

    /**
     * Saves a board configuration to a JSON file.
     * The board size and special tile configurations are written to the file.
     *
     * @param gameboardLogic the game board logic containing board size information
     * @param tileLogic the tile logic containing special tile configurations
     * @throws BoardSaveException if an error occurs while writing to the file
     */
    public static void saveBoard(GameboardLogic gameboardLogic, TileLogic tileLogic) throws BoardSaveException {
        if (!(tileLogic instanceof LadderTileLogic)) {
            throw new BoardSaveException("BoardFileHandler requires LadderTileLogic", null);
        }
        saveBoard(gameboardLogic, (LadderTileLogic) tileLogic, DEFAULT_BOARD_FILE_PATH);
    }

    /**
     * Saves a board configuration to a specified JSON file.
     * The board size and special tile configurations are written to the file.
     *
     * @param gameboardLogic the game board logic containing board size information
     * @param tileLogic the tile logic containing special tile configurations
     * @param filePath the path where the board configuration should be saved
     * @throws BoardSaveException if an error occurs while writing to the file
     */
    public static void saveBoard(GameboardLogic gameboardLogic, LadderTileLogic tileLogic, String filePath) throws BoardSaveException {
        try (FileWriter writer = new FileWriter(filePath)) {
            JsonObject boardJson = serializeBoardConfig(gameboardLogic, tileLogic);
            writer.write(boardJson.toString());
        } catch (IOException e) {
            throw new BoardSaveException("Failed to save board configuration to JSON file: " + filePath, e);
        }
    }

    /**
     * Serializes the board configuration into a JSON object.
     * Creates a JSON representation of the board size and special tile configurations.
     *
     * @param gameboardLogic the game board logic containing board size information
     * @param tileLogic the tile logic containing special tile configurations
     * @return a JSON object representing the board configuration
     */
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
