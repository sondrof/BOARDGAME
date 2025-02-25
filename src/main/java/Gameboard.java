public class Gameboard {
  TileLogic tileLogic = new TileLogic();
  PlayerLogic playerLogic = new PlayerLogic();

  public void initBoard() {
    tileLogic.generateTiles(100);
    playerLogic.generatePlayers(2);

  }


}
