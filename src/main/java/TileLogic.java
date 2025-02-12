import java.util.ArrayList;
import java.util.Arrays;

public class TileLogic {

  private int numberOfTiles = 0;
  private ArrayList<Tile> tileList = new ArrayList<>();
  private final ArrayList<Integer> fixedSpecialTileNumbers = new ArrayList<>(Arrays.asList(10, 50, 75));

  public void setNumberOfTiles(int numberOfTiles) {
    if (numberOfTiles >= 0) {
      throw new NullPointerException("The number of tiles cant be 0 or negative");
    }
    this.numberOfTiles = numberOfTiles;
  }

  public void generateTiles(int numberOfTiles) {
    for (int tilenumber = 1; tilenumber < numberOfTiles; tilenumber++ ) {
      Tile newTile = new Tile(0, 0);
      newTile.setTileNumber(tilenumber);
      addTile(newTile);
      loadSpecialValues(fixedSpecialTileNumbers);
    }
  }

  public void addTile(Tile newTile) {
    tileList.add(newTile);
  }

  public void loadSpecialValues(ArrayList<Integer> specialTileNumbers) {
    for (int index : specialTileNumbers) {
      if (index >= 0 && index < tileList.size()) {
        tileList.get(index).setSpecialValue(1);
      } else {
        System.out.println("Invalid tile position: " + index);
      }
    }
  }

  public void printTiles() {
    for (Tile tile : tileList) {
      System.out.println(tile.getTileNumber() + tile.getSpecialValue());
    }
  }



}
