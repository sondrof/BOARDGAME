package players;

import modell.players.Player;
import modell.players.PlayerFileLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for PlayerFileLoader.
 */
class PlayerFileLoaderTest {
    private static final String TEST_PLAYERS_PATH = "src/test/resources/test_players.csv";

    /**
     * Tests loading players from a CSV file using PlayerFileLoader.
     */
    @Test
    void testLoadPlayers() throws Exception {
        List<Player> players = PlayerFileLoader.loadPlayers(TEST_PLAYERS_PATH);

        assertEquals(2, players.size());

        Player player1 = players.get(0);
        assertEquals("Test1", player1.getPlayerName());
        assertEquals("Token1", player1.getPlayerToken());
        assertEquals(5, player1.getPlayerPosition());

        Player player2 = players.get(1);
        assertEquals("Test2", player2.getPlayerName());
        assertEquals("Token2", player2.getPlayerToken());
        assertEquals(10, player2.getPlayerPosition());
    }
}

