package tiles;

import java.util.ArrayList;
import java.util.List;

public abstract class TileLogic {
  protected List<Tile> tiles;

  protected TileLogic() {
    this.tiles = new ArrayList<>();
  }

  public Tile getTileByNumber(int tileNumber) {
    return tiles.stream()
            .filter(tile -> tile.getTileNumber() == tileNumber)
            .findFirst()
            .orElse(null);
  }

  public void addTile(Tile tile) {
    int index = tiles.indexOf(getTileByNumber(tile.getTileNumber()));
    if (index != -1) {
      tiles.set(index, tile);
    } else {
      tiles.add(tile);
    }
  }

  public int getBoardSize() {
    return tiles.size();
  }

  public List<Tile> getTiles() {
    return new ArrayList<>(tiles);
  }
}
