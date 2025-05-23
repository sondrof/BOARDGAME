package modell.tiles;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the ExtraThrowTile instances on the game board.
 * Provides functionality to add extra-throw tiles and retrieve their positions.
 * Extra-throw tiles grant the player an additional dice roll when landed on.
 *
 * <p>Example usage:
 * <pre>
 * ExtraThrowTileLogic logic = new ExtraThrowTileLogic();
 * logic.addExtraThrowTile(8);                     // Enable extra throw on tile 8
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */

public class ExtraThrowTileLogic extends TileLogic {

  /**
   * Adds an ExtraThrowTile at the specified tile number.
   * If the tile already has a non-zero effect, an exception is thrown.
   *
   * @param fromTile the tile number to grant an extra throw
   * @throws IllegalStateException if the tile already has a special effect
   */
  public void addExtraThrowTile(int fromTile) {
    Tile tile = getTileByNumber(fromTile);
    if (tile == null) {
      return;
    }
    if (tile.getEffect() != 0) {
      throw new IllegalStateException("Tile " + fromTile + " already has a special effect");
    }
    tiles.set(tiles.indexOf(tile), new ExtraThrowTile(fromTile));
  }

  /**
   * Returns a map of all extra-throw tiles on the board.
   * The map keys are tile numbers, and the values are their effect values (always 0).
   *
   * @return a map of tile numbers to effect values for ExtraThrowTile
   */
  public Map<Integer, Integer> getExtraThrowMap() {
    return tiles.stream()
        .filter(t -> t instanceof ExtraThrowTile)
        .collect(Collectors.toMap(
            Tile::getTileNumber,
            Tile::getEffect
        ));
  }
}

