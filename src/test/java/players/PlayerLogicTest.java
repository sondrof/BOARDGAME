package players;

import dice.DiceSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PlayerLogic functionality.
 * Tests player management, movement, and status tracking.
 */
class PlayerLogicTest {

    private PlayerLogic playerLogic;

    /**
     * Sets up test environment before each test.
     */
    @BeforeEach
    void setUp() {
        playerLogic = new PlayerLogic(new DiceSet(2));
    }

    /**
     * Tests adding a player to the game.
     */
    @Test
    void testAddPlayer() {
        playerLogic.addPlayer("Ronaldo");
        ArrayList<Player> players = playerLogic.getPlayerList();
        assertEquals(1, players.size());
        assertEquals("Ronaldo", players.get(0).getPlayerName());
        assertEquals("DefaultToken", players.get(0).getPlayerToken());
        assertEquals(0, players.get(0).getPlayerPosition());
    }

    /**
     * Tests adding multiple players to the game.
     */
    @Test
    void testAddMultiplePlayers() {
        playerLogic.addPlayer("Ronaldo");
        playerLogic.addPlayer("Messi");
        playerLogic.addPlayer("Neymar");
        ArrayList<Player> players = playerLogic.getPlayerList();
        assertEquals("Ronaldo", players.get(0).getPlayerName());
        assertEquals("Messi", players.get(1).getPlayerName());
        assertEquals("Neymar", players.get(2).getPlayerName());
    }

    /**
     * Tests generating multiple players with default names.
     */
    @Test
    void testGeneratePlayers(){
        playerLogic.generatePlayers(3);
        ArrayList<Player> players = playerLogic.getPlayerList();
        assertEquals(3, players.size());
        assertEquals("Player1", players.get(0).getPlayerName());
        assertEquals("Player2", players.get(1).getPlayerName());
        assertEquals("Player3", players.get(2).getPlayerName());
    }

    /**
     * Tests moving a player.
     * Since we can't control the dice roll, we just verify the position changes.
     */
    @Test
    void testMovePlayer() {
        playerLogic.addPlayer("Ronaldo");
        playerLogic.movePlayer(0);
        int position = playerLogic.getPlayerList().get(0).getPlayerPosition();
        assertTrue(position > 0 && position <= 12);
    }

    /**
     * Tests that moving a player with an invalid index throws an exception.
     */
    @Test
    void testMovePlayerWithInvalidIndex() {
        playerLogic.addPlayer("Ronaldo");
        assertThrows(IllegalArgumentException.class, () -> playerLogic.movePlayer(-1));
        assertThrows(IllegalArgumentException.class, () -> playerLogic.movePlayer(1));
    }

    /**
     * Tests moving different players in a multi-player game.
     */
    @Test
    void testMoveMultiplePlayers() {
        playerLogic.addPlayer("Ronaldo");
        playerLogic.addPlayer("Messi");
        playerLogic.movePlayer(0);
        playerLogic.movePlayer(1);
        int position1 = playerLogic.getPlayerList().get(0).getPlayerPosition();
        int position2 = playerLogic.getPlayerList().get(1).getPlayerPosition();
        assertTrue(position1 > 0 && position1 <= 12);
        assertTrue(position2 > 0 && position2 <= 12);
    }

    /**
     * Tests that the player list is initially empty.
     */
    @Test
    void testInitialPlayerListIsEmpty() {
        assertEquals(0, playerLogic.getPlayerList().size());
    }
}