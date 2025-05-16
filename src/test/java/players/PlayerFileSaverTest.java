package players;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for PlayerFileSaver.
 */
class PlayerFileSaverTest {
    private static final String TEST_SAVE_PATH = "src/test/resources/saved_players.csv";

    /**
     * Tests saving players to a CSV file using PlayerFileSaver.
     */
    @Test
    void testSavePlayers() throws Exception {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Test1", "Token1");
        Player player2 = new Player("Test2", "Token2");
        player1.setPlayerPosition(5);
        player2.setPlayerPosition(10);
        players.add(player1);
        players.add(player2);

        PlayerFileSaver.savePlayers(players, TEST_SAVE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_SAVE_PATH))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            assertTrue(line1.contains("Test1,Token1,5"));
            assertTrue(line2.contains("Test2,Token2,10"));
        }
    }
}

