package tiles;

import modell.tiles.LadderTile;
import modell.tiles.LadderTileLogic;
import modell.tiles.Tile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

/**
 * Test class for LadderTileLogic functionality.
 * Tests ladder management, validation, and board operations.
 */
class LadderTileLogicTest {

    private void initializeBoard(LadderTileLogic logic, int size) {
        for (int i = 1; i <= size; i++) {
            logic.addTile(new LadderTile(i, 0));
        }
    }

    /**
     * Tests adding a ladder to a valid tile position.
     */
    @Test
    void testAddLadder() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);

        Tile tile = logic.getTileByNumber(5);
        assertEquals(10, tile.getEffect());
    }

    /**
     * Tests adding a ladder to a non-existent tile position.
     */
    @Test
    void testAddLadderToNonExistentTile() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(1000, 10);
        Tile tile = logic.getTileByNumber(1000);
        assertNull(tile);
    }

    /**
     * Tests adding a ladder to a tile that already has one.
     */
    @Test
    void testAddLadderToTileWithExistingLadder() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);
        assertThrows(IllegalStateException.class, () -> logic.addLadder(5, 15));
    }

    /**
     * Tests adding a ladder with an effect value exceeding the maximum allowed.
     */
    @Test
    void testAddLadderWithLargeEffectValue() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        assertThrows(IllegalArgumentException.class, () -> logic.addLadder(5, 101));
    }

    /**
     * Tests adding a ladder that would create a circular path.
     */
    @Test
    void testAddCircularLadder() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);
        assertThrows(IllegalArgumentException.class, () -> logic.addLadder(15, -10));
    }

    /**
     * Tests adding a ladder that would create a circular path through multiple steps.
     */
    @Test
    void testAddCircularLadderWithMultipleSteps() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 100);
        logic.addLadder(5, 10);
        logic.addLadder(15, 10);
        assertThrows(IllegalArgumentException.class, () -> logic.addLadder(25, -20));
    }

    /**
     * Tests retrieving the map of all ladders on the board.
     */
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

    /**
     * Tests retrieving the ladder map when no ladders exist.
     */
    @Test
    void testGetLadderMapEmpty() {
        LadderTileLogic logic = new LadderTileLogic();
        initializeBoard(logic, 10);
        Map<Integer, Integer> ladderMap = logic.getLadderMap();
        assertTrue(ladderMap.isEmpty());
    }
}