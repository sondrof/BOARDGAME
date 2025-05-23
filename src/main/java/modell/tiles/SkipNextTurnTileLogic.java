package modell.tiles;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the SkipNextTurnTile instances on the game board.
 * Provides functionality to add skip-next-turn tiles and retrieve their positions.
 * Skip-next-turn tiles force the player to lose their next turn.
 *
 * <p>Example usage:
 * <pre>
 * SkipNextTurnTileLogic logic = new SkipNextTurnTileLogic();
 * logic.addSkipNextTurnTile(15);                  // Enable skip turn on tile 15
 * Map<Integer,Integer> skips = logic.getSkipNextTurnMap();
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class SkipNextTurnTileLogic extends TileLogic {

  /**
   * Adds a SkipNextTurnTile at the specified tile number.
   * If the tile already has a non-zero effect, an exception is thrown.
   *
   * @param fromTile the tile number to enforce skip next turn
   * @throws IllegalStateException if the tile already has a special effect
   */
  public void addSkipNextTurnTile(int fromTile) {
    Tile tile = getTileByNumber(fromTile);
    if (tile == null) {
      return;
    }
    if (tile.getEffect() != 0) {
      throw new IllegalStateException("Tile " + fromTile + " already has a special effect");
    }
    tiles.set(tiles.indexOf(tile), new SkipNextTurnTile(fromTile));
  }

  /**
   * Returns a map of all skip-next-turn tiles on the board.
   * The map keys are tile numbers, and the values are their effect values (always 0).
   *
   * @return a map of tile numbers to effect values for SkipNextTurnTile
   */
  public Map<Integer,Integer> getSkipNextTurnMap() {
    return tiles.stream()
        .filter(t -> t instanceof SkipNextTurnTile)
        .collect(Collectors.toMap(
            Tile::getTileNumber,
            Tile::getEffect
        ));
  }
}