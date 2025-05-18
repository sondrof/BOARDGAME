package tiles;

import modell.tiles.LadderTile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LadderTile functionality.
 * Tests ladder effects, descriptions, and edge cases.
 */
class LadderTileTest {

    /**
     * Tests that a ladder with a positive effect value returns the correct effect.
     */
    @Test
    void testUpLadderEffect() {
        LadderTile tile = new LadderTile(5, 10);
        assertEquals(10, tile.getEffect());
    }

    /**
     * Tests that a ladder with a negative effect value returns the correct effect.
     */
    @Test
    void testDownLadderEffect() {
        LadderTile tile = new LadderTile(5, -10);
        assertEquals(-10, tile.getEffect());
    }

    /**
     * Tests that a ladder with a positive effect returns the correct description.
     */
    @Test
    void testUpLadderDescription() {
        LadderTile tile = new LadderTile(5, 10);
        assertEquals("Ladder up 10 spaces", tile.getDescription());
    }

    /**
     * Tests that a ladder with a negative effect returns the correct description.
     */
    @Test
    void testDownLadderDescription() {
        LadderTile tile = new LadderTile(5, -10);
        assertEquals("Ladder down 10 spaces", tile.getDescription());
    }

    /**
     * Tests that a ladder with zero effect returns the correct effect and description.
     */
    @Test
    void testZeroEffectLadder() {
        LadderTile tile = new LadderTile(5, 0);
        assertEquals(0, tile.getEffect());
        assertEquals("Ladder down 0 spaces", tile.getDescription());
    }

    /**
     * Tests that a ladder with maximum positive effect value works correctly.
     */
    @Test
    void testLargeEffectValue() {
        LadderTile tile = new LadderTile(5, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, tile.getEffect());
        assertEquals("Ladder up " + Integer.MAX_VALUE + " spaces", tile.getDescription());
    }

    /**
     * Tests that a ladder with maximum negative effect value works correctly.
     */
    @Test
    void testLargeNegativeEffectValue() {
        LadderTile tile = new LadderTile(5, Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, tile.getEffect());
        assertEquals("Ladder down " + Math.abs(Integer.MIN_VALUE) + " spaces", tile.getDescription());
    }
}