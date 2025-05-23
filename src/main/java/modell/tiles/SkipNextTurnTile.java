package modell.tiles;

/**
 * Represents a special tile that causes the player to skip their
 * next turn when they land on it.
 * No positional movement is applied immediately, but the game’s phase logic
 * should detect this tile type and advance the turn marker without allowing a roll.
 *
 * <p>Example usage:
 * <pre>
 * SkipNextTurnTile t = new SkipNextTurnTile(15);  // Tile number 15
 * int effect = t.getEffect();                    // Returns 0
 * String desc = t.getDescription();              // "Skip next turn"
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class SkipNextTurnTile extends Tile {

  /**
   * Constructs a new SkipNextTurnTile at the given board position.
   *
   * @param tileNumber the unique number of this tile on the board
   */
  public SkipNextTurnTile(int tileNumber) {
    super(tileNumber);
  }

  /**
   * No immediate movement effect on the board.
   *
   * @return always 0
   */
  @Override
  public int getEffect() {
    return 0;
  }

  /**
   * Description of this tile’s special effect.
   *
   * @return a brief description for the UI/log
   */
  @Override
  public String getDescription() {
    return "Skip next turn";
  }
}