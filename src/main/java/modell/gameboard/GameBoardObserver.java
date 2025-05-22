package modell.gameboard;

import modell.players.Player;

/**
 * Observer interface for game board events.
 * Classes implementing this interface can receive notifications about
 * various game events such as player movements, special tile activations,
 * game wins, and round starts.
 *
 * <p>This interface follows the Observer pattern, allowing game components
 * to react to game state changes without tight coupling.
 *
 * <p>Example usage:
 * <pre>
 * public class GameUI implements GameBoardObserver {
 *     @Override
 *     public void onPlayerMove(int playerId, int oldPosition, int newPosition, int diceRoll) {
 *         // Update UI to show player movement
 *     }
 *
 *     @Override
 *     public void onSpecialTileActivated(Player, int fromTile, int toTile, int specialValue) {
 *         // Show special tile effect animation
 *     }
 * }
 * </pre>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public interface GameBoardObserver {
    /**
     * Called when a player moves on the board.
     *
     * @param playerId the ID of the player who moved
     * @param oldPosition the player's previous position
     * @param newPosition the player's new position
     * @param diceRoll the result of the dice roll that caused the movement
     */
    void onPlayerMove(int playerId, int oldPosition, int newPosition, int diceRoll);

    /**
     * Called when a player lands on a special tile (e.g., ladder or snake).
     *
     * @param player the player who activated the special tile
     * @param fromTile the tile number where the player landed
     * @param toTile the tile number where the player will move to
     * @param specialValue the effect value of the special tile
     */
    void onSpecialTileActivated(Player player, int fromTile, int toTile, int specialValue);

    /**
     * Called when a player wins the game.
     *
     * @param winner the player who won the game
     */
    void onGameWon(Player winner);

    /**
     * Called when a new round starts.
     *
     * @param roundNumber the number of the new round
     */
    void onRoundStart(int roundNumber);
}