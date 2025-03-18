package tiles;

import java.util.ArrayList;

public class TileLogic {
  private int numberOfTiles = 0;
  private ArrayList<Tile> tileList = new ArrayList<>();

  public void setNumberOfTiles(int numberOfTiles) {
    if (numberOfTiles <= 0) {
      throw new IllegalArgumentException("The number of tiles can't be 0 or negative");
    }
    this.numberOfTiles = numberOfTiles;
  }

  public int getNumberOfTiles() {
    return numberOfTiles;
  }

  public void generateTiles(int numberOfTiles) {
    tileList.clear(); // Clear the list to avoid duplicate tiles if method is called multiple times
    for (int tilenumber = 1; tilenumber <= numberOfTiles; tilenumber++) {
      Tile newTile = new Tile(tilenumber, 0);
      addTile(newTile);
    }
  }

  public void addTile(Tile newTile) {
    tileList.add(newTile);
  }

  public void printTiles() {
    for (Tile tile : tileList) {
      System.out.println(tile.getTileNumber());
    }
  }

  public Tile getTileByNumber(int number) {
    for (Tile tile : tileList) {
      if (tile.getTileNumber() == number) {
        return tile;
      }
    }
    return null;
  }

  public void setSpecialTile(int tileNumber, int specialValue) {
    Tile tile = getTileByNumber(tileNumber);
    if (tile != null) {
      tile.setSpecialValue(specialValue);
      int destination = tileNumber + specialValue;
      System.out.println("Added ladder from " + tileNumber + " to " + destination);
    }
  }

}
