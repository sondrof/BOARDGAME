package modell.gameboard;

import com.google.gson.*;
import modell.tiles.LadderTile;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import java.io.*;

public class LadderGameBoardFactory {

    private static final int MAX_BOARD_SIZE = 1000;

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

    public TileLogic createBoard(LadderBoardType type, String fileName) {
        switch (type) {
            case STANDARD:
                return createStandardBoard();
            case MANY_LADDERS:
                return createManyLaddersBoard();
            case FEW_LADDERS:
                return createFewLaddersBoard();
            case ONLY_UP:
                return createOnlyUpBoard();
            case ONLY_DOWN:
                return createOnlyDownBoard();
            case ONLY_SHORT:
                return createOnlyShortBoard();
            case ONLY_LONG:
                return createOnlyLongBoard();
            default:
                throw new IllegalArgumentException("Unknown board type: " + type);
        }
    }

    private TileLogic createStandardBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        setStandardTiles(logic);
        return logic;
    }

    private TileLogic createManyLaddersBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        setManyLaddersTiles(logic);
        return logic;
    }

    private TileLogic createFewLaddersBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        setFewLaddersTiles(logic);
        return logic;
    }

    private TileLogic createOnlyUpBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        setUpLaddersTiles(logic);
        return logic;
    }

    private TileLogic createOnlyDownBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        setDownLaddersTiles(logic);
        return logic;
    }

    private TileLogic createOnlyShortBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        logic.addLadder(5,10 );
        logic.addLadder(12, -3);
        logic.addLadder(18, 4);
        logic.addLadder(21, -3);
        logic.addLadder(34, -5);
        logic.addLadder(37, 6);
        logic.addLadder(48, 6);
        logic.addLadder(55, -8);
        logic.addLadder(70, -2);
        logic.addLadder(74, 7);
        logic.addLadder(88, 4);
        logic.addLadder(94, -10);
        return logic;
    }


    private TileLogic createOnlyLongBoard() {
        LadderTileLogic logic = createTileLogic();
        generateBoard(logic, 100);
        logic.addLadder(5, 40);
        logic.addLadder(13, 70);
        logic.addLadder(20, -19);
        logic.addLadder(35, 60);
        logic.addLadder(60, -38);
        logic.addLadder(75, 24);
        logic.addLadder(90, -80);
        logic.addLadder(40, -33);
        return logic;
    }

    private void setStandardTiles(LadderTileLogic logic) {
        logic.addLadder(2, 32);
        logic.addLadder(8, 8);
        logic.addLadder(28, 30);
        logic.addLadder(40, 20);
        logic.addLadder(73, 10);
        logic.addLadder(80, 12);
        logic.addLadder(12, -3);
        logic.addLadder(24, -6);
        logic.addLadder(57, -15);
        logic.addLadder(64, -29);
        logic.addLadder(78, -10);
        logic.addLadder(98, -60);
    }

    private void setManyLaddersTiles(LadderTileLogic logic) {

        logic.addLadder(3, 7);
        logic.addLadder(7, -5);
        logic.addLadder(12, 6);
        logic.addLadder(15, -4);
        logic.addLadder(20, 8);
        logic.addLadder(25, -6);
        logic.addLadder(30, 5);
        logic.addLadder(35, -7);
        logic.addLadder(40, 6);
        logic.addLadder(45, -5);
        logic.addLadder(50, 7);
        logic.addLadder(55, -6);
        logic.addLadder(60, 5);
        logic.addLadder(65, -4);
        logic.addLadder(70, 8);
        logic.addLadder(75, -7);
        logic.addLadder(80, 6);
        logic.addLadder(85, -5);
        logic.addLadder(90, 7);
        logic.addLadder(95, -4);
    }

    private void setFewLaddersTiles(LadderTileLogic logic) {
        logic.addLadder(12, 50);
        logic.addLadder(45, 40);
        logic.addLadder(30, -28);
        logic.addLadder(91, -82);
    }

    private void setUpLaddersTiles(LadderTileLogic logic) {
        logic.addLadder(10, 15);
        logic.addLadder(25, 20);
        logic.addLadder(40, 25);
        logic.addLadder(55, 30);
        logic.addLadder(70, 20);
    }

    private void setDownLaddersTiles(LadderTileLogic logic) {
        logic.addLadder(20, -15);
        logic.addLadder(38, -16);
        logic.addLadder(50, -20);
        logic.addLadder(65, -10);
        logic.addLadder(80, -70);
        logic.addLadder(99, -80);
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
}
