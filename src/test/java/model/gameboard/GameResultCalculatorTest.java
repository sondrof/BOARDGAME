package model.gameboard;

import modell.gameboard.GameResultCalculator;
import modell.players.Player;
import modell.players.PlayerToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameResultCalculator functionality.
 * Tests the calculation of final standings in a board game.
 */
class GameResultCalculatorTest {
    private List<Player> players;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach
    void setUp() {
        player1 = new Player("Player 1", PlayerToken.DEFAULT);
        player2 = new Player("Player 2", PlayerToken.DEFAULT);
        player3 = new Player("Player 3", PlayerToken.DEFAULT);
        player4 = new Player("Player 4", PlayerToken.DEFAULT);
        players = List.of(player1, player2, player3, player4);
    }

    @Test
    void testCalculateStandingsWithWinner() {
        // Set up player positions
        player1.setPlayerPosition(100); // Winner
        player2.setPlayerPosition(85);
        player3.setPlayerPosition(92);
        player4.setPlayerPosition(78);

        List<Player> standings = GameResultCalculator.calculateStandings(players, player1);

        // Verify order: winner first, then others by position descending
        assertEquals(player1, standings.get(0));
        assertEquals(player3, standings.get(1));
        assertEquals(player2, standings.get(2));
        assertEquals(player4, standings.get(3));
    }

    @Test
    void testCalculateStandingsWithTiedPositions() {
        // Set up player positions with ties
        player1.setPlayerPosition(100); // Winner
        player2.setPlayerPosition(85);
        player3.setPlayerPosition(85); // Tied with player2
        player4.setPlayerPosition(78);

        List<Player> standings = GameResultCalculator.calculateStandings(players, player1);

        // Verify order: winner first, then others by position descending
        assertEquals(player1, standings.get(0));
        // For tied positions, the order should be preserved as in the original list
        assertEquals(player2, standings.get(1));
        assertEquals(player3, standings.get(2));
        assertEquals(player4, standings.get(3));
    }

    @Test
    void testCalculateStandingsWithAllPlayersAtStart() {
        // Set up all players at starting position
        player1.setPlayerPosition(100); // Winner
        player2.setPlayerPosition(0);
        player3.setPlayerPosition(0);
        player4.setPlayerPosition(0);

        List<Player> standings = GameResultCalculator.calculateStandings(players, player1);

        // Verify order: winner first, then others by position descending
        assertEquals(player1, standings.get(0));
        // For tied positions at 0, the order should be preserved as in the original list
        assertEquals(player2, standings.get(1));
        assertEquals(player3, standings.get(2));
        assertEquals(player4, standings.get(3));
    }

    @Test
    void testCalculateStandingsWithLowPositions() {
        // Set up player positions with very low values
        player1.setPlayerPosition(100); // Winner
        player2.setPlayerPosition(1);
        player3.setPlayerPosition(0);
        player4.setPlayerPosition(2);

        List<Player> standings = GameResultCalculator.calculateStandings(players, player1);

        // Verify order: winner first, then others by position descending
        assertEquals(player1, standings.get(0));
        assertEquals(player4, standings.get(1));
        assertEquals(player2, standings.get(2));
        assertEquals(player3, standings.get(3));
    }

    @Test
    void testCalculateStandingsWithEmptyList() {
        List<Player> emptyList = List.of();
        Player winner = new Player("Winner", PlayerToken.DEFAULT);
        winner.setPlayerPosition(100);

        List<Player> standings = GameResultCalculator.calculateStandings(emptyList, winner);

        // Verify only winner is in the list
        assertEquals(1, standings.size());
        assertEquals(winner, standings.get(0));
    }
}