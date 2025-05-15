package tiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for tiles.Tile functionality.
 * Tests the base functionality of the abstract Tile class using a test implementation.
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

    @Test
    void testValidTileNumber() {
        TestTile tile = new TestTile(5, 10);
        assertEquals(5, tile.getTileNumber());
    }

    @Test
    void testInvalidTileNumber() {
        assertThrows(IllegalArgumentException.class, () -> new TestTile(-1, 10));
    }

    @Test
    void testGetEffect() {
        TestTile tile = new TestTile(5, 10);
        assertEquals(10, tile.getEffect());
    }

    @Test
    void testGetDescription() {
        TestTile tile = new TestTile(5, 10);
        assertEquals("Test tile with effect 10", tile.getDescription());
    }
}