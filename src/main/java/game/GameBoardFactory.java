package game;

import tiles.TileLogic;

import java.util.Map;


public class GameBoardFactory {


    public static TileLogic createStandardBoard() {
        TileLogic tileLogic = new TileLogic();
        tileLogic.generateTiles(100);


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

        return tileLogic;
    }

    public static TileLogic createStandardBoard2() {
        TileLogic tileLogic = new TileLogic();
        tileLogic.generateTiles(100);


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

        return tileLogic;
    }

}