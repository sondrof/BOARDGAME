package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import tiles.LadderTileLogic;

/**
 * Unit tests for BoardFileSaver.
 */
class BoardFileSaverTest {
    private static final String TEST_SAVE_PATH = "src/test/resources/saved_board.json";

    /**
     * Tests saving a board configuration to a JSON file using BoardFileSaver.
     */
    @Test
    void testSaveBoard() throws Exception {
        GameboardLogic gameboardLogic = new GameboardLogic();
        LadderTileLogic tileLogic = new LadderTileLogic();

        for (int i = 1; i <= gameboardLogic.getBoardSize(); i++) {
            tileLogic.addTile(new tiles.LadderTile(i, 0));
        }

        tileLogic.addLadder(3, 7);

        BoardFileSaver.saveBoard(gameboardLogic, tileLogic, TEST_SAVE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_SAVE_PATH))) {
            String content = reader.readLine();
            assertTrue(content.contains("\"boardSize\":100"));
            assertTrue(content.contains("\"tileNumber\":3"));
            assertTrue(content.contains("\"ladderValue\":7"));
        }
    }
}