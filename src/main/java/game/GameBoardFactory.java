package game;

import com.google.gson.*;
import tiles.TileLogic;

import java.io.*;

public class GameBoardFactory {

    private static final String BOARD_FILES_DIR = "src/main/resources/boards/";

    public enum BoardType {
        STANDARD, STANDARD2, FROM_FILE
    }


    public static TileLogic createTileLogic(BoardType boardType, String fileName) {
        TileLogic tileLogic = new TileLogic();
        tileLogic.generateTiles(100);

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
            TileLogic tileLogic = new TileLogic();
            tileLogic.generateTiles(boardSize);

            JsonArray specialTilesArray = boardJson.getAsJsonArray("specialTiles");
            for (JsonElement tileElement : specialTilesArray) {
                JsonObject tileJson = tileElement.getAsJsonObject();
                int tileNumber = tileJson.get("tileNumber").getAsInt();
                int specialValue = tileJson.get("specialValue").getAsInt();
                tileLogic.setSpecialTile(tileNumber, specialValue);
            }

            return tileLogic;

        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException("Failed to load board from file: " + fileName, e);
        }
    }

    private static void setStandardTiles(TileLogic tileLogic) {
        tileLogic.setSpecialTile(4, 10);
        tileLogic.setSpecialTile(8, 22);
        tileLogic.setSpecialTile(28, 48);
        tileLogic.setSpecialTile(40, 36);
        tileLogic.setSpecialTile(80, 19);

        tileLogic.setSpecialTile(17, -10);
        tileLogic.setSpecialTile(54, -20);
        tileLogic.setSpecialTile(62, -43);
        tileLogic.setSpecialTile(64, -4);
        tileLogic.setSpecialTile(87, -63);
        tileLogic.setSpecialTile(93, -20);
        tileLogic.setSpecialTile(95, -20);
        tileLogic.setSpecialTile(99, -21);
    }

    private static void setStandardTiles2(TileLogic tileLogic) {
        tileLogic.setSpecialTile(1, 19);
        tileLogic.setSpecialTile(9, 7);
        tileLogic.setSpecialTile(17, 10);
        tileLogic.setSpecialTile(36, 40);
        tileLogic.setSpecialTile(83, 15);

        tileLogic.setSpecialTile(12, -10);
        tileLogic.setSpecialTile(15, -10);
        tileLogic.setSpecialTile(40, -11);
        tileLogic.setSpecialTile(54, -8);
        tileLogic.setSpecialTile(86, -80);
    }
}
