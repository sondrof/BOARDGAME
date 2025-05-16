/*package players;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class PlayerFileSaverTest {
    private static final String TEST_SAVE_PATH = "src/test/resources/saved_players.csv";

    @Test
    void testSavePlayers() throws Exception {
        // Create test players
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Test1", "Token1");
        Player player2 = new Player("Test2", "Token2");
        player1.setPlayerPosition(5);
        player2.setPlayerPosition(10);
        players.add(player1);
        players.add(player2);

        // Save the players
        PlayerFileSaver.savePlayers(players, TEST_SAVE_PATH);

        // Verify the saved data
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_SAVE_PATH))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            assertTrue(line1.contains("Test1,Token1,5"));
            assertTrue(line2.contains("Test2,Token2,10"));
        }
    }
}

 */