package game;

import com.google.gson.*;
import tiles.LadderTile;
import tiles.LadderTileLogic;
import tiles.TileLogic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameBoardFactory {

    private static final String BOARD_FILES_DIR = "src/main/resources/boards/";
    private static final int MAX_BOARD_SIZE = 1000;

    public enum BoardType {
        STANDARD, STANDARD2, FROM_FILE
    }

    public LadderTileLogic createTileLogic() {
        return new LadderTileLogic();
    }

    public static void generateBoard(LadderTileLogic logic, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size must be positive, got: " + size);
        }
        if (size > MAX_BOARD_SIZE) {
            throw new IllegalArgumentException("Board size exceeds maximum allowed value of " + MAX_BOARD_SIZE);
        }

        for (int i = 1; i <= size; i++) {
            logic.addTile(new LadderTile(i, 0));
        }
    }

    public LadderTileLogic createBoard(BoardType type, String fileName) {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100); // Default size for standard boards

        switch (type) {
            case STANDARD:
                setStandardTiles(logic);
                break;
            case STANDARD2:
                setStandardTiles2(logic);
                break;
            case FROM_FILE:
                if (fileName == null || fileName.isBlank()) {
                    throw new IllegalArgumentException("File name must be provided for FROM_FILE board type");
                }
                return loadTileLogicFromFile(BOARD_FILES_DIR + fileName);
            default:
                throw new IllegalArgumentException("Invalid board type selected");
        }

        return logic;
    }

    public LadderTileLogic loadTileLogicFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            int boardSize = jsonObject.get("boardSize").getAsInt();

            LadderTileLogic logic = createTileLogic();
            generateBoard(logic, boardSize);

            JsonObject specialTiles = jsonObject.getAsJsonObject("specialTiles");
            if (specialTiles != null) {
                specialTiles.entrySet().forEach(entry -> {
                    int tileNumber = Integer.parseInt(entry.getKey());
                    int ladderValue = entry.getValue().getAsInt();
                    logic.addLadder(tileNumber, ladderValue);
                });
            }

            return logic;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load board from file: " + filePath, e);
        }
    }

    private void setStandardTiles(LadderTileLogic logic) {
        logic.addLadder(4, 10);
        logic.addLadder(8, 22);
        logic.addLadder(28, 48);
        logic.addLadder(40, 36);
        logic.addLadder(80, 19);

        logic.addLadder(17, -10);
        logic.addLadder(54, -20);
        logic.addLadder(62, -43);
        logic.addLadder(64, -4);
        logic.addLadder(87, -63);
        logic.addLadder(93, -20);
        logic.addLadder(95, -20);
        logic.addLadder(99, -21);
    }

    private void setStandardTiles2(LadderTileLogic logic) {
        logic.addLadder(1, 19);
        logic.addLadder(9, 7);
        logic.addLadder(17, 10);
        logic.addLadder(36, 40);
        logic.addLadder(83, 15);

        logic.addLadder(12, -10);
        logic.addLadder(15, -10);
        logic.addLadder(40, -11);
        logic.addLadder(54, -8);
        logic.addLadder(86, -80);
    }
}