package players;

import modell.players.Player;
import modell.players.PlayerToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Player functionality.
 * Tests player creation, property getters and setters, and validation logic.
 */
class PlayerTest {

    /**
     * Tests that a player can be created with valid name and token.
     */
    @Test
    void testPlayerWithValidParameters() {
        Player player = new Player("Zoro", PlayerToken.SWORD);
        assertEquals("Zoro", player.getName());
        assertEquals(PlayerToken.SWORD, player.getToken());
        assertEquals(0, player.getPlayerPosition());
    }

    /**
     * Tests that creating a player with invalid parameters throws exceptions.
     */
    @Test
    void testPlayerWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Player("", PlayerToken.DEFAULT));
        assertThrows(IllegalArgumentException.class, () -> new Player(null, PlayerToken.DEFAULT));
        assertThrows(IllegalArgumentException.class, () -> new Player("Miyamoto", null));
    }

    /**
     * Tests setting valid player positions.
     */
    @Test
    void testSetValidPlayerPosition() {
        Player player = new Player("Zoro", PlayerToken.SWORD);
        player.setPlayerPosition(10);
        assertEquals(10, player.getPlayerPosition());
        player.setPlayerPosition(2);
        assertEquals(2, player.getPlayerPosition());
        player.setPlayerPosition(95);
        assertEquals(95, player.getPlayerPosition());
    }

    /**
     * Tests that setting invalid player positions throws exceptions.
     */
    @Test
    void testSetInvalidPlayerPosition() {
        Player player = new Player("Zoro", PlayerToken.SWORD);
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerPosition(-1));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerPosition(-14));
    }
}