package tiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for tiles.LadderTile functionality.
 * Tests the behavior of ladder tiles, including their effects and descriptions.
 */
class LadderTileTest {

    @Test
    void testUpLadderEffect() {
        LadderTile tile = new LadderTile(5, 10);
        assertEquals(10, tile.getEffect());
    }

    @Test
    void testDownLadderEffect() {
        LadderTile tile = new LadderTile(5, -10);
        assertEquals(-10, tile.getEffect());
    }

    @Test
    void testUpLadderDescription() {
        LadderTile tile = new LadderTile(5, 10);
        assertEquals("Ladder up 10 spaces", tile.getDescription());
    }

    @Test
    void testDownLadderDescription() {
        LadderTile tile = new LadderTile(5, -10);
        assertEquals("Ladder down 10 spaces", tile.getDescription());
    }

    @Test
    void testZeroEffectLadder() {
        LadderTile tile = new LadderTile(5, 0);
        assertEquals(0, tile.getEffect());
        assertEquals("Ladder down 0 spaces", tile.getDescription());
    }

    @Test
    void testLargeEffectValue() {
        LadderTile tile = new LadderTile(5, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, tile.getEffect());
        assertEquals("Ladder up " + Integer.MAX_VALUE + " spaces", tile.getDescription());
    }

    @Test
    void testLargeNegativeEffectValue() {
        LadderTile tile = new LadderTile(5, Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, tile.getEffect());
        assertEquals("Ladder down " + Math.abs(Integer.MIN_VALUE) + " spaces", tile.getDescription());
    }
}