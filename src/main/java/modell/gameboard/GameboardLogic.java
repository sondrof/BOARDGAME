package modell.gameboard;

import modell.players.Player;
import modell.tiles.Tile;
import modell.tiles.TileLogic;

/**
 * Handles the core game board logic and rules.
 * This class manages player movement, special tile effects, and win conditions.
 *
 * <p>The game board logic includes:
 * <ul>
 *     <li>Handling player landings on tiles</li>
 *     <li>Managing bounce-back when exceeding board size</li>
 *     <li>Processing special tile effects (ladders and snakes)</li>
 *     <li>Checking win conditions</li>
 * </ul>
 *
 * @author didrik
 * @version 1.0
 */
public class GameboardLogic {
    /** Total number of tiles on the board */
    private static final int BOARD_SIZE = 100;

    /**
     * Handles the logic when a player lands on a tile.
     * This method:
     * <ol>
     *     <li>Checks if the player has exceeded the board size and bounces them back if necessary</li>
     *     <li>Checks if the landed tile is a special tile (ladder or snake)</li>
     *     <li>Applies any special tile effects</li>
     * </ol>
     *
     * @param player the player who landed on the tile
     * @param tileLogic the tile logic component for handling special tiles
     */
    public void handlePlayerLanding(Player player, TileLogic tileLogic) {
        int currentPos = player.getPlayerPosition();

        if (currentPos > BOARD_SIZE) {
            int bounce = currentPos - BOARD_SIZE;
            currentPos = BOARD_SIZE - bounce;
            player.setPlayerPosition(currentPos);
            System.out.println(player.getName() + " bounced back to " + currentPos);
        }

        Tile currentTile = tileLogic.getTileByNumber(currentPos);
        if (currentTile != null && currentTile.getEffect() != 0) {
            movePlayerSpecial(player, currentTile);
        }
    }

    /**
     * Moves a player based on a special tile effect.
     * Updates the player's position and provides feedback about the movement.
     *
     * @param player the player to move
     * @param tile the special tile that was landed on
     */
    private void movePlayerSpecial(Player player, Tile tile) {
        int newPos = player.getPlayerPosition() + tile.getEffect();
        player.setPlayerPosition(newPos);

        String moveType;
        if (tile.getEffect() > 0) {
            moveType = "climbed a ladder to";
        } else {
            moveType = "fell down a ladder to";
        }
        System.out.println(player.getName() + " " + moveType + " " + newPos);
    }

    /**
     * Checks if a player has won the game.
     * A player wins when they reach or exceed the final tile (BOARD_SIZE).
     *
     * @param player the player to check
     * @return true if the player has won, false otherwise
     */
    public boolean checkWinCondition(Player player) {
        return player.getPlayerPosition() >= BOARD_SIZE;
    }

    /**
     * Gets the total number of tiles on the board.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}