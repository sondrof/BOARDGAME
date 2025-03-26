package players;

import dice.DiceSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLogicTest {

    private PlayerLogic playerLogic;

    @BeforeEach
    void setUp() {
        playerLogic = new PlayerLogic(new DiceSet(2));
    }

    @Test
    void testAddPlayer() {
        playerLogic.addPlayer("Ronaldo");
        ArrayList<Player> players = playerLogic.getPlayerList();
        assertEquals(1, players.size());
        assertEquals("Ronaldo", players.get(0).getPlayerName());
        assertEquals("DefaultToken", players.get(0).getPlayerToken());
        assertEquals(0, players.get(0).getPlayerPosition());
    }

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

    @Test
    void testGeneratePlayers(){
        playerLogic.generatePlayers(3);
        ArrayList<Player> players = playerLogic.getPlayerList();
        assertEquals(3, players.size());
        assertEquals("Player1", players.get(0).getPlayerName());
        assertEquals("Player2", players.get(1).getPlayerName());
        assertEquals("Player3", players.get(2).getPlayerName());
    }

    @Test
    void testMovePlayer() {
        playerLogic.addPlayer("Ronaldo");
        playerLogic.movePlayer(0);
        int position = playerLogic.getPlayerList().get(0).getPlayerPosition();
        assertTrue(position > 0 && position <= 12);
    }

    @Test
    void testMovePlayerWithInvalidIndex() {
        playerLogic.addPlayer("Ronaldo");
        assertThrows(IllegalArgumentException.class, () -> playerLogic.movePlayer(-1));
        assertThrows(IllegalArgumentException.class, () -> playerLogic.movePlayer(1));
    }

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

    @Test
    void testInitialPlayerListIsEmpty() {
        assertEquals(0, playerLogic.getPlayerList().size());
    }
}