package modell.gameboard;

import com.google.gson.*;
import modell.tiles.LadderTile;
import modell.tiles.LadderTileLogic;

import java.io.*;

public class GameBoardFactory {

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

        switch (type) {
            case STANDARD:
                generateBoard(logic, 100);
                setStandardTiles(logic);
                break;
            case STANDARD2:
                generateBoard(logic, 100);
                setStandardTiles2(logic);
                break;
            case FROM_FILE:
                if (fileName == null || fileName.isBlank()) {
                    throw new IllegalArgumentException("File name must be provided for FROM_FILE board type");
                }
                return loadTileLogicFromFile(fileName);
            default:
                throw new IllegalArgumentException("Invalid board type selected");
        }

        return logic;
    }

    public LadderTileLogic loadTileLogicFromFile(String fileName) {
        System.out.println("Loading board file: boards/" + fileName);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("boards/" + fileName)) {
            if (is == null) throw new FileNotFoundException("File not found in resources: boards/" + fileName);

            JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            int boardSize = jsonObject.get("boardSize").getAsInt();

            LadderTileLogic logic = createTileLogic();
            generateBoard(logic, boardSize);

            JsonArray specialTiles = jsonObject.getAsJsonArray("specialTiles");
            if (specialTiles != null) {
                for (JsonElement element : specialTiles) {
                    JsonObject tileObj = element.getAsJsonObject();
                    int tileNumber = tileObj.get("tileNumber").getAsInt();
                    int specialValue = tileObj.get("specialValue").getAsInt();
                    logic.addLadder(tileNumber, specialValue);
                }
            }

            return logic;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load board from file: " + fileName, e);
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
