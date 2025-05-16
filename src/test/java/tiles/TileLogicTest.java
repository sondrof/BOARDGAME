package tiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Test class for tiles.TileLogic functionality.
 * Tests the base functionality of the abstract TileLogic class using a test implementation.
 */
class TileLogicTest {

    private static class TestTileLogic extends TileLogic {

    }

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
            return "Test tile " + getTileNumber();
        }
    }

    @Test
    void testGetTileByNumber() {
        TestTileLogic logic = new TestTileLogic();
        logic.addTile(new TestTile(5, 0));

        Tile tile = logic.getTileByNumber(5);
        assertNotNull(tile);
        assertEquals(5, tile.getTileNumber());
    }

    @Test
    void testGetTileByNumberNotFound() {
        TestTileLogic logic = new TestTileLogic();
        logic.addTile(new TestTile(5, 0));

        Tile tile = logic.getTileByNumber(15);
        assertNull(tile);
    }

    @Test
    void testAddTile() {
        TestTileLogic logic = new TestTileLogic();
        Tile newTile = new TestTile(11, 5);
        logic.addTile(newTile);

        assertEquals(1, logic.getBoardSize());
        Tile addedTile = logic.getTileByNumber(11);
        assertNotNull(addedTile);
        assertEquals(5, addedTile.getEffect());
    }

    @Test
    void testGetBoardSize() {
        TestTileLogic logic = new TestTileLogic();
        logic.addTile(new TestTile(1, 0));
        logic.addTile(new TestTile(2, 0));

        assertEquals(2, logic.getBoardSize());
    }

    @Test
    void testGetTiles() {
        TestTileLogic logic = new TestTileLogic();
        logic.addTile(new TestTile(1, 0));
        logic.addTile(new TestTile(2, 0));

        List<Tile> tiles = logic.getTiles();
        assertEquals(2, tiles.size());
        assertEquals(1, tiles.get(0).getTileNumber());
        assertEquals(2, tiles.get(1).getTileNumber());
    }

    @Test
    void testReplaceExistingTile() {
        TestTileLogic logic = new TestTileLogic();
        logic.addTile(new TestTile(5, 0));
        Tile replacementTile = new TestTile(5, 10);
        logic.addTile(replacementTile);

        assertEquals(1, logic.getBoardSize());
        Tile replacedTile = logic.getTileByNumber(5);
        assertNotNull(replacedTile);
        assertEquals(10, replacedTile.getEffect());
    }

    @Test
    void testGetTilesMaintainsOrder() {
        TestTileLogic logic = new TestTileLogic();
        logic.addTile(new TestTile(1, 0));
        logic.addTile(new TestTile(2, 0));

        List<Tile> tiles = logic.getTiles();
        assertEquals(1, tiles.get(0).getTileNumber());
        assertEquals(2, tiles.get(1).getTileNumber());
    }
}