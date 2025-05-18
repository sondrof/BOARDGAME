package players;

import modell.players.Player;
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
        Player player = new Player("Zoro","Swords");
        assertEquals("Zoro", player.getPlayerName());
        assertEquals("Swords", player.getPlayerToken());
        assertEquals(0, player.getPlayerPosition());
    }

    /**
     * Tests that creating a player with invalid parameters throws exceptions.
     */
    @Test
    void testPlayerWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Player("","Katana"));
        assertThrows(IllegalArgumentException.class, () -> new Player(null,"Katana"));
        assertThrows(IllegalArgumentException.class, () -> new Player("Miyamoto",""));
        assertThrows(IllegalArgumentException.class, () -> new Player("Miyamoto",null));
    }

    /**
     * Tests setting a valid player name.
     */
    @Test
    void testSetValidPlayerName(){
        Player player = new Player("Zoro","Swords");
        player.setPlayerName("Goat");
        assertEquals("Goat", player.getPlayerName());
    }

    /**
     * Tests that setting invalid player names throws exceptions.
     */
    @Test
    void testSetInvalidPlayerName(){
        Player player = new Player("Zoro","Swords");
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerName(""));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerToken(null));
    }

    /**
     * Tests setting a valid player token.
     */
    @Test
    void testSetValidPlayerToken(){
        Player player = new Player("Zoro","Swords");
        player.setPlayerToken("Bandana");
        assertEquals("Bandana", player.getPlayerToken());
    }

    /**
     * Tests that setting invalid player tokens throws exceptions.
     */
    @Test
    void testSetInvalidPlayerToken(){
        Player player = new Player("Zoro","Swords");
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerToken(""));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerToken(null));
    }

    /**
     * Tests setting valid player positions.
     */
    @Test
    void testSetValidPlayerPosition(){
        Player player = new Player("Zoro","Swords");
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
    void testSetInvalidPlayerPosition(){
        Player player = new Player("Zoro","Swords");
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerPosition(-1));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerPosition(-14));
    }

}