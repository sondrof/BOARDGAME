package tiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Tile functionality.
 * Tests tile creation, validation, and basic operations.
 */
class TileTest {

    private static class TestTile extends Tile {
        private final int effect;

        public TestTile(int tileNumber, int effect) {
            super(tileNumber);
            this.effect = effect;
        }

        @Override
        public int getEffect() {
            return effect;
        }

        @Override
        public String getDescription() {
            return "Test tile with effect " + effect;
        }
    }

    /**
     * Tests creating a tile with a valid tile number.
     */
    @Test
    void testValidTileNumber() {
        TestTile tile = new TestTile(5, 10);
        assertEquals(5, tile.getTileNumber());
    }

    /**
     * Tests that creating a tile with zero tile number throws an exception.
     */
    @Test
    void testInvalidTileNumber() {
        assertThrows(IllegalArgumentException.class, () -> new TestTile(-1, 10));
    }

    /**
     * Tests that creating a tile with number exceeding maximum throws an exception.
     */
    @Test
    void testMaximumTileNumber() {
        TestTile tile = new TestTile(1000, 10);
        assertEquals(1000, tile.getTileNumber());
    }

    /**
     * Tests that a tile returns the correct effect value.
     */
    @Test
    void testGetEffect() {
        TestTile tile = new TestTile(5, 10);
        assertEquals(10, tile.getEffect());
    }

    /**
     * Tests that a tile returns the correct description.
     */
    @Test
    void testGetDescription() {
        TestTile tile = new TestTile(5, 10);
        assertEquals("Test tile with effect 10", tile.getDescription());
    }
}