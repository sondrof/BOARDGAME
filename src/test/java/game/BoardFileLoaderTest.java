package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

/**
 * Unit tests for BoardFileLoader.
 */
class BoardFileLoaderTest {
    private static final String TEST_BOARD_PATH = "src/test/resources/test_board.json";

    /**
     * Tests loading a board configuration from a JSON file using BoardFileLoader.
     */
    @Test
    void testLoadBoard() throws Exception {
        BoardConfig config = BoardFileLoader.loadBoard(TEST_BOARD_PATH);

        assertEquals(10, config.getBoardSize());

        Map<Integer, Integer> tileConfigs = config.getTileConfigs();
        assertEquals(2, tileConfigs.size());

        assertEquals(7, tileConfigs.get(3));

        assertEquals(-6, tileConfigs.get(8));
    }
}