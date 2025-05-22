package modell.tiles;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the game board and ladder tiles in a board game.
 * This class handles the creation and management of the game board, including
 * adding ladders and ensuring they follow game rules. It prevents invalid ladder
 * placements such as circular paths or ladders that would move players beyond
 * the board boundaries.
 *
 * <p>The maximum ladder effect is limited to {@value #MAX_EFFECT} spaces.
 * Ladders cannot create circular paths or move players beyond the board boundaries.
 *
 * <p>Example usage:
 * <pre>
 * LadderTileLogic logic = new LadderTileLogic();
 * logic.addTile(new LadderTile(1, 0));  // Add a normal tile
 * logic.addLadder(5, 10);               // Add a ladder that moves up 10 spaces
 * Map<Integer, Integer> ladders = logic.getLadderMap();  // Get all ladders
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class LadderTileLogic extends TileLogic {
    /** Maximum allowed ladder effect value */
    private static final int MAX_EFFECT = 100;

    /**
     * Adds a ladder to the specified tile.
     * The ladder will move players by the specified number of spaces when they land on it.
     * Positive values move players up the board, negative values move them down.
     *
     * @param fromTile the tile number to add the ladder to
     * @param ladderValue the number of spaces to move (positive for up, negative for down)
     * @throws IllegalStateException if the tile already has a ladder
     * @throws IllegalArgumentException if the ladder would move players beyond board boundaries
     *         or if the effect value exceeds {@value #MAX_EFFECT}
     */
    public void addLadder(int fromTile, int ladderValue) {
        Tile tile = getTileByNumber(fromTile);
        if (tile == null) {
            return;
        }

        if (tile.getEffect() != 0) {
            throw new IllegalStateException("Tile " + fromTile + " already has a ladder");
        }

        int targetTile = fromTile + ladderValue;
        if (targetTile <= 0 || targetTile > getBoardSize()) {
            throw new IllegalArgumentException("Ladder effect would make player go beyond board boundaries");
        }

        if (Math.abs(ladderValue) > MAX_EFFECT) {
            throw new IllegalArgumentException("Ladder effect value " + ladderValue + " exceeds maximum allowed value of " + MAX_EFFECT);
        }


        if (wouldCreateCircularPath(fromTile, targetTile)) {
            throw new IllegalArgumentException("Adding ladder would create a circular path");
        }

        tiles.set(tiles.indexOf(tile), new LadderTile(fromTile, ladderValue));
    }

    /**
     * Checks if adding a ladder would create a circular path.
     * A circular path occurs when a sequence of ladders would eventually
     * lead back to the starting tile.
     *
     * @param startTile the tile where the new ladder starts
     * @param targetTile the tile where the new ladder ends
     * @return true if adding the ladder would create a circular path
     */
    private boolean wouldCreateCircularPath(int startTile, int targetTile) {
        int currentTile = targetTile;
        int steps = 0;
        int maxSteps = getBoardSize();

        while (steps < maxSteps) {
            Tile tile = getTileByNumber(currentTile);
            if (tile == null || tile.getEffect() == 0) {
                return false;
            }

            currentTile = currentTile + tile.getEffect();
            if (currentTile == startTile) {
                return true;
            }

            steps++;
        }

        return false;
    }

    /**
     * Returns a map of all ladder tiles on the board.
     * The map keys are tile numbers, and the values are the ladder effects.
     * Only tiles with non-zero effects (i.e., ladders) are included.
     *
     * @return a map of tile numbers to ladder effects
     */
    public Map<Integer, Integer> getLadderMap() {
        return tiles.stream()
                .filter(tile -> tile.getEffect() != 0)
                .collect(Collectors.toMap(
                        Tile::getTileNumber,
                        Tile::getEffect
                ));
    }
}