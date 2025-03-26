package players;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerWithValidParameters() {
        Player player = new Player("Zoro","Swords");
        assertEquals("Zoro", player.getPlayerName());
        assertEquals("Swords", player.getPlayerToken());
        assertEquals(0, player.getPlayerPosition());
    }

    @Test
    void testPlayerWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new Player("","Katana"));
        assertThrows(IllegalArgumentException.class, () -> new Player(null,"Katana"));
        assertThrows(IllegalArgumentException.class, () -> new Player("Miyamoto",""));
        assertThrows(IllegalArgumentException.class, () -> new Player("Miyamoto",null));
    }

    @Test
    void testSetValidPlayerName(){
        Player player = new Player("Zoro","Swords");
        player.setPlayerName("Goat");
        assertEquals("Goat", player.getPlayerName());
    }

    @Test
    void testSetInvalidPlayerName(){
        Player player = new Player("Zoro","Swords");
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerName(""));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerToken(null));
    }

    @Test
    void testSetValidPlayerToken(){
        Player player = new Player("Zoro","Swords");
        player.setPlayerToken("Bandana");
        assertEquals("Bandana", player.getPlayerToken());
    }

    @Test
    void testSetInvalidPlayerToken(){
        Player player = new Player("Zoro","Swords");
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerToken(""));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerToken(null));
    }

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

    @Test
    void testSetInvalidPlayerPosition(){
        Player player = new Player("Zoro","Swords");
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerPosition(-1));
        assertThrows(IllegalArgumentException.class, () -> player.setPlayerPosition(-14));
    }

}