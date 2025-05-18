package modell.tiles;

/**
 * Represents a tile on a game board.
 * This abstract class serves as the base for all types of tiles in the game,
 * providing common functionality and defining the interface that all tile types
 * must implement. Each tile has a unique number and can have an effect that
 * influences gameplay.
 *
 * <p>The tile number must be between 1 and {@value #MAX_TILE_NUMBER}.
 * The effect of a tile is determined by its concrete implementation.
 *
 * <p>Example usage:
 * <pre>
 * Tile tile = new LadderTile(5, 10);  // Create a ladder tile at position 5
 * int effect = tile.getEffect();       // Get the tile's effect
 * String desc = tile.getDescription(); // Get a description of the tile
 * </pre>
 */
public abstract class Tile {
  /** Maximum allowed tile number on the board */
  private static final int MAX_TILE_NUMBER = 1000;
  /** The unique number identifying this tile on the board */
  private final int tileNumber;

  /**
   * Constructs a new tile with the specified number.
   * The tile number must be positive and not exceed the maximum allowed value.
   *
   * @param tileNumber the number of the tile (must be between 1 and {@value #MAX_TILE_NUMBER})
   * @throws IllegalArgumentException if tileNumber is not positive or exceeds {@value #MAX_TILE_NUMBER}
   */
  public Tile(int tileNumber) {
    if (tileNumber < 0) {
      throw new IllegalArgumentException("Tile number cannot be negative, got: " + tileNumber);
    }
    if (tileNumber > MAX_TILE_NUMBER) {
      throw new IllegalArgumentException("Tile number exceeds maximum allowed value of " + MAX_TILE_NUMBER);
    }
    this.tileNumber = tileNumber;
  }

  /**
   * Returns the number of this tile.
   * The tile number uniquely identifies the tile on the board.
   *
   * @return the tile number
   */
  public int getTileNumber() {
    return tileNumber;
  }

  /**
   * Returns the effect of this tile.
   * The effect represents how this tile influences gameplay when a player lands on it.
   * For example, a ladder tile might move the player up or down a certain number of spaces.
   *
   * @return the effect value of this tile
   */
  public abstract int getEffect();

  /**
   * Returns a description of this tile.
   * The description should explain what happens when a player lands on this tile.
   *
   * @return a string describing the tile's effect
   */
  public abstract String getDescription();
}
