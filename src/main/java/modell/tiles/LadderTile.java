package modell.tiles;

/**
 * Represents a ladder tile on the game board.
 * A ladder tile moves players up or down a specified number of spaces when they land on it.
 * Positive ladder values move players up the board, while negative values move them down.
 *
 * <p>Example usage:
 * <pre>
 * LadderTile ladder = new LadderTile(5, 10);   // Ladder that moves up 10 spaces
 * LadderTile snake = new LadderTile(20, -5);   // Ladder that moves down 5 spaces
 * int effect = ladder.getEffect();             // Returns 10
 * String desc = ladder.getDescription();       // Returns "Ladder up 10 spaces"
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class LadderTile extends Tile {
    /** The value of the ladder effect (positive for up, negative for down) */
    private final int ladderValue;

    /**
     * Constructs a new ladder tile with the specified number and effect.
     * The ladder value determines how many spaces a player moves when landing on this tile.
     *
     * @param tileNumber the number of the tile on the board
     * @param ladderValue the number of spaces to move (positive for up, negative for down)
     */
    public LadderTile(int tileNumber, int ladderValue) {
        super(tileNumber);
        this.ladderValue = ladderValue;
    }   

    /**
     * Returns the ladder effect value.
     * A positive value means the player moves up the board,
     * while a negative value means they move down.
     *
     * @return the number of spaces to move
     */
    @Override
    public int getEffect() {
        return ladderValue;
    }

    /**
     * Returns a description of the ladder's effect.
     * The description indicates whether the ladder moves the player up or down
     * and by how many spaces.
     *
     * @return a string describing the ladder's effect
     */
    @Override
    public String getDescription() {
        return ladderValue > 0 ?
                "Ladder up " + ladderValue + " spaces" :
                "Ladder down " + Math.abs(ladderValue) + " spaces";
    }
}