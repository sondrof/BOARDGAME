package modell.gameboard;

import java.util.Map;

/**
 * Represents the configuration of a game board.
 * This class holds the board size and special tile configurations.
 *
 * <p>The configuration includes:
 * <ul>
 *     <li>Board size: The total number of tiles on the board</li>
 *     <li>Tile configurations: A map of tile numbers to their special effects (e.g., ladder values)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 * BoardConfig config = new BoardConfig();
 * config.setBoardSize(100);
 * Map<Integer, Integer> tiles = new HashMap<>();
 * tiles.put(1, 10);  // Tile 1 has a ladder going up 10 spaces
 * tiles.put(20, -5); // Tile 20 has a ladder going down 5 spaces
 * config.setTileConfigs(tiles);
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class BoardConfig {
  private int boardSize;
  private Map<Integer, Integer> tileConfigs;

  /**
   * Gets the total number of tiles on the board.
   *
   * @return the board size
   */
  public int getBoardSize() {
    return boardSize;
  }

  /**
   * Sets the total number of tiles on the board.
   *
   * @param boardSize the board size to set
   */
  public void setBoardSize(int boardSize) {
    this.boardSize = boardSize;
  }

  /**
   * Gets the map of special tile configurations.
   * The map keys are tile numbers, and the values are their special effects.
   *
   * @return the map of tile configurations
   */
  public Map<Integer, Integer> getTileConfigs() {
    return tileConfigs;
  }

  /**
   * Sets the map of special tile configurations.
   * The map keys should be tile numbers, and the values should be their special effects.
   *
   * @param tileConfigs the map of tile configurations to set
   */
  public void setTileConfigs(Map<Integer, Integer> tileConfigs) {
    this.tileConfigs = tileConfigs;
  }
}

