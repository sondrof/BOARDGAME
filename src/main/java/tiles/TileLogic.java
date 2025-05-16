package tiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of tiles that form a game board.
 * This abstract class provides the base functionality for managing tiles on a game board,
 * including adding tiles, retrieving tiles by number, and maintaining the board's structure.
 * Concrete implementations can add specific game rules and tile behaviors.
 *
 * <p>The board is represented as a list of tiles, where each tile has a unique number.
 * Tiles can be added, replaced, or retrieved by their number.
 *
 * <p>Example usage:
 * <pre>
 * TileLogic logic = new LadderTileLogic();
 * logic.addTile(new LadderTile(1, 0));  // Add a normal tile
 * Tile tile = logic.getTileByNumber(1);  // Get tile by number
 * int size = logic.getBoardSize();       // Get total number of tiles
 * List<Tile> allTiles = logic.getTiles(); // Get all tiles
 * </pre>
 */
public abstract class TileLogic {
  /** List containing all tiles on the board */
  protected List<Tile> tiles;

  /**
   * Constructs a new empty game board.
   * Initializes an empty list to store the board's tiles.
   */
  protected TileLogic() {
    this.tiles = new ArrayList<>();
  }

  /**
   * Returns the tile with the specified number.
   * Searches through the board's tiles to find one with the matching number.
   *
   * @param tileNumber the number of the tile to find
   * @return the tile with the specified number, or null if not found
   */
  public Tile getTileByNumber(int tileNumber) {
    return tiles.stream()
            .filter(tile -> tile.getTileNumber() == tileNumber)
            .findFirst()
            .orElse(null);
  }

  /**
   * Adds or replaces a tile on the board.
   * If a tile with the same number already exists, it will be replaced.
   * Otherwise, the new tile will be added to the board.
   *
   * @param tile the tile to add or replace
   */
  public void addTile(Tile tile) {
    int index = tiles.indexOf(getTileByNumber(tile.getTileNumber()));
    if (index != -1) {
      tiles.set(index, tile);
    } else {
      tiles.add(tile);
    }
  }

  /**
   * Returns the total number of tiles on the board.
   *
   * @return the number of tiles
   */
  public int getBoardSize() {
    return tiles.size();
  }

  /**
   * Returns a copy of all tiles on the board.
   * The returned list is a new ArrayList containing all tiles,
   * preventing external modification of the internal tile list.
   *
   * @return a new list containing all tiles
   */
  public List<Tile> getTiles() {
    return new ArrayList<>(tiles);
  }
}
