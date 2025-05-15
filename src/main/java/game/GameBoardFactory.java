package game;

import com.google.gson.*;
import tiles.LadderTileLogic;
import tiles.TileLogic;

import java.io.*;

public class GameBoardFactory {

    private static final String BOARD_FILES_DIR = "src/main/resources/boards/";

    public enum BoardType {
        STANDARD, STANDARD2, FROM_FILE
    }


    public static TileLogic createTileLogic(BoardType boardType, String fileName) {
        LadderTileLogic tileLogic = new LadderTileLogic();
        tileLogic.generateBoard(100);

        switch (boardType) {
            case STANDARD:
                setStandardTiles(tileLogic);
                break;
            case STANDARD2:
                setStandardTiles2(tileLogic);
                break;
            case FROM_FILE:
                if (fileName == null || fileName.isBlank()) {
                    throw new IllegalArgumentException("File name must be provided for FROM_FILE board type.");
                }
                return loadTileLogicFromFile(fileName);
            default:
                throw new IllegalArgumentException("Invalid board type selected.");
        }

        return tileLogic;
    }


    public static TileLogic loadTileLogicFromFile(String fileName) {
        File file = new File(BOARD_FILES_DIR + fileName);
        if (!file.exists()) {
            throw new IllegalArgumentException("Board file not found: " + file.getAbsolutePath());
        }

        try (Reader reader = new FileReader(file)) {
            JsonObject boardJson = JsonParser.parseReader(reader).getAsJsonObject();

            int boardSize = boardJson.get("boardSize").getAsInt();
            LadderTileLogic tileLogic = new LadderTileLogic();
            tileLogic.generateBoard(boardSize);

            JsonArray specialTilesArray = boardJson.getAsJsonArray("specialTiles");
            for (JsonElement tileElement : specialTilesArray) {
                JsonObject tileJson = tileElement.getAsJsonObject();
                int tileNumber = tileJson.get("tileNumber").getAsInt();
                int ladderValue = tileJson.get("ladderValue").getAsInt();
                tileLogic.addLadder(tileNumber, ladderValue);
            }

            return tileLogic;

        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException("Failed to load board from file: " + fileName, e);
        }
    }

    private static void setStandardTiles(LadderTileLogic tileLogic) {
        tileLogic.addLadder(4, 10);
        tileLogic.addLadder(8, 22);
        tileLogic.addLadder(28, 48);
        tileLogic.addLadder(40, 36);
        tileLogic.addLadder(80, 19);

        tileLogic.addLadder(17, -10);
        tileLogic.addLadder(54, -20);
        tileLogic.addLadder(62, -43);
        tileLogic.addLadder(64, -4);
        tileLogic.addLadder(87, -63);
        tileLogic.addLadder(93, -20);
        tileLogic.addLadder(95, -20);
        tileLogic.addLadder(99, -21);
    }

    private static void setStandardTiles2(LadderTileLogic tileLogic) {
        tileLogic.addLadder(1, 19);
        tileLogic.addLadder(9, 7);
        tileLogic.addLadder(17, 10);
        tileLogic.addLadder(36, 40);
        tileLogic.addLadder(83, 15);

        tileLogic.addLadder(12, -10);
        tileLogic.addLadder(15, -10);
        tileLogic.addLadder(40, -11);
        tileLogic.addLadder(54, -8);
        tileLogic.addLadder(86, -80);
    }
}