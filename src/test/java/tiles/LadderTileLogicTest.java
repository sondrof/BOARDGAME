package tiles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

/**
 * Test class for tiles.LadderTileLogic functionality.
 * Tests the behavior of ladder tile logic, including ladder management.
 */
class LadderTileLogicTest {

    private void initializeBoard(LadderTileLogic logic, int size) {
        for (int i = 1; i <= size; i++) {
            logic.addTile(new LadderTile(i, 0));
        }
    }

    @Test
    void testAddLadder() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);

        Tile tile = logic.getTileByNumber(5);
        assertEquals(10, tile.getEffect());
    }

    @Test
    void testAddLadderToNonExistentTile() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(1000, 10);
        Tile tile = logic.getTileByNumber(1000);
        assertNull(tile);
    }

    @Test
    void testAddLadderToTileWithExistingLadder() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);
        assertThrows(IllegalStateException.class, () -> logic.addLadder(5, 15));
    }

    @Test
    void testAddLadderWithLargeEffectValue() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        assertThrows(IllegalArgumentException.class, () -> logic.addLadder(5, 101));
    }

    @Test
    void testAddCircularLadder() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);
        assertThrows(IllegalArgumentException.class, () -> logic.addLadder(15, -10));
    }

    @Test
    void testAddCircularLadderWithMultipleSteps() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);
        logic.addLadder(15, 10);
        assertThrows(IllegalArgumentException.class, () -> logic.addLadder(25, -20));
    }

    @Test
    void testGetLadderMap() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 10);
        logic.addLadder(3, 5);
        logic.addLadder(7, -3);

        Map<Integer, Integer> ladderMap = logic.getLadderMap();
        assertEquals(2, ladderMap.size());
        assertEquals(5, ladderMap.get(3));
        assertEquals(-3, ladderMap.get(7));
    }

    @Test
    void testGetLadderMapEmpty() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 10);
        Map<Integer, Integer> ladderMap = logic.getLadderMap();
        assertTrue(ladderMap.isEmpty());
    }
}