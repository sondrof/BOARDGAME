package tiles;

import java.util.ArrayList;
import java.util.List;

public abstract class TileLogic {
  protected final List<Tile> tiles;

  public TileLogic() {
    this.tiles = new ArrayList<>();
  }

  public abstract void generateBoard(int size);

  public Tile getTileByNumber(int number) {
    return tiles.stream()
            .filter(tile -> tile.getTileNumber() == number)
            .findFirst()
            .orElse(null);
  }

  public void addTile(Tile tile) {
    tiles.add(tile);
  }

  public int getBoardSize() {
    return tiles.size();
  }

  public List<Tile> getTiles() {
    return new ArrayList<>(tiles);
  }
}
