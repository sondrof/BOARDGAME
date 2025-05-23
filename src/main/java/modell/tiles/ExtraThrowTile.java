package modell.tiles;

/**
 * Represents a special tile that grants the player an extra dice throw
 * when they land on it.
 * No positional movement is applied immediately, but the game’s phase logic
 * should detect this tile type and allow an additional roll before ending the turn.
 *
 * <p>Example usage:
 * <pre>
 * ExtraThrowTile t = new ExtraThrowTile(8);      // Tile number 8
 * int effect = t.getEffect();                   // Returns 0
 * String desc = t.getDescription();             // "Extra throw: roll again"
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class ExtraThrowTile extends Tile {

  /**
   * Constructs a new ExtraThrowTile at the given board position.
   *
   * @param tileNumber the unique number of this tile on the board
   */
  public ExtraThrowTile(int tileNumber) {
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
    return "Extra throw: roll again";
  }
}

