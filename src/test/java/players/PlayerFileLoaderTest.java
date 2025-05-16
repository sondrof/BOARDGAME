package players;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PlayerFileLoaderTest {
    private static final String TEST_PLAYERS_PATH = "src/test/resources/test_players.csv";

    @Test
    void testLoadPlayers() throws Exception {
        // Load the players
        List<Player> players = PlayerFileLoader.loadPlayers(TEST_PLAYERS_PATH);

        // Verify the loaded data
        assertEquals(2, players.size());

        // Verify first player
        Player player1 = players.get(0);
        assertEquals("Test1", player1.getPlayerName());
        assertEquals("Token1", player1.getPlayerToken());
        assertEquals(5, player1.getPlayerPosition());

        // Verify second player
        Player player2 = players.get(1);
        assertEquals("Test2", player2.getPlayerName());
        assertEquals("Token2", player2.getPlayerToken());
        assertEquals(10, player2.getPlayerPosition());
    }
}

