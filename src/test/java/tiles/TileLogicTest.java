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
        @Override
        public void generateBoard(int size) {
            tiles.clear();
            for (int i = 1; i <= size; i++) {
                tiles.add(new LadderTile(i, 0));
            }
        }
    }

    @Test
    void testGetTileByNumber() {
        TestTileLogic logic = new TestTileLogic();
        logic.generateBoard(10);

        Tile tile = logic.getTileByNumber(5);
        assertNotNull(tile);
        assertEquals(5, tile.getTileNumber());
    }

    @Test
    void testGetTileByNumberNotFound() {
        TestTileLogic logic = new TestTileLogic();
        logic.generateBoard(10);

        Tile tile = logic.getTileByNumber(15);
        assertNull(tile);
    }

    @Test
    void testAddTile() {
        TestTileLogic logic = new TestTileLogic();
        Tile tile = new LadderTile(1, 0);

        logic.addTile(tile);
        assertEquals(1, logic.getBoardSize());
        assertEquals(tile, logic.getTileByNumber(1));
    }

    @Test
    void testGetBoardSize() {
        TestTileLogic logic = new TestTileLogic();
        logic.generateBoard(10);

        assertEquals(10, logic.getBoardSize());
    }

    @Test
    void testGetTiles() {
        TestTileLogic logic = new TestTileLogic();
        logic.generateBoard(5);

        List<Tile> tiles = logic.getTiles();
        assertEquals(5, tiles.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(i + 1, tiles.get(i).getTileNumber());
        }
    }


}