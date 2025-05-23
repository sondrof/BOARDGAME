package modell.gameboard;

import modell.dice.DiceSet;
import modell.players.PlayerLogic;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;

/**
 * Represents the main game board for a ladder game.
 * This class manages the game state, player interactions, and game flow.
 *
 * <p>The game board consists of:
 * <ul>
 *     <li>A 100-tile board with ladders and snakes</li>
 *     <li>2-6 players who take turns rolling dice and moving</li>
 *     <li>Special tiles that can move players up (ladders) or down (snakes)</li>
 * </ul>
 *
 * <p>The game flow:
 * <ol>
 *     <li>Initialize the board with ladders and snakes</li>
 *     <li>Set up players (2-6 players)</li>
 *     <li>Players take turns rolling dice and moving</li>
 *     <li>Special tiles are activated when players land on them</li>
 *     <li>First player to reach tile 100 wins</li>
 * </ol>
 *
 * @author didrik
 * @version 1.0
 */
public class Gameboard {

  /** Logic for handling special tiles (ladders and snakes). */
  private final LadderTileLogic tileLogic;

  /** Logic for managing player actions and state. */
  private final PlayerLogic playerLogic;

  /** Logic for managing the game board state. */
  private final GameboardLogic gameboardLogic;




  /**
   * Creates a new game board with custom tile logic.
   *
   * @param tileLogic the custom tile logic to use
   * @throws IllegalArgumentException if the provided tile logic is not a LadderTileLogic
   */
  public Gameboard(TileLogic tileLogic) {
    if (!(tileLogic instanceof LadderTileLogic)) {
      throw new IllegalArgumentException("Gameboard requires LadderTileLogic");
    }
    this.tileLogic = (LadderTileLogic) tileLogic;
    this.playerLogic = new PlayerLogic(new DiceSet(2));
    this.gameboardLogic = new GameboardLogic();
  }



  /**
   * Gets the player logic component.
   *
   * @return the PlayerLogic instance
   */
  public PlayerLogic getPlayerLogic() {
    return playerLogic;
  }

  /**
   * Gets the game board logic component.
   *
   * @return the GameboardLogic instance
   */
  public GameboardLogic getGameboardLogic() {
    return gameboardLogic;
  }

  /**
   * Gets the tile logic component.
   *
   * @return the LadderTileLogic instance
   */
  public LadderTileLogic getTileLogic() {
    return tileLogic;
  }
}