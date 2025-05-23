package modell.gameboard;

import modell.players.Player;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class to calculate final standings for a board game.
 * Winner is placed first, others are sorted by position descending.
 *
 * @author didrik
 * @version 1.0
 */
public class GameResultCalculator {
  /**
   * Returns the final standings: winner first, then others by position descending.
   * @param players List of all players
   * @param winner The player who won
   * @return Ordered list of players by place
   */
  public static List<Player> calculateStandings(List<Player> players, Player winner) {
    List<Player> standings = new ArrayList<>();
    standings.add(winner);
    List<Player> others = new ArrayList<>(players);
    others.remove(winner);
    others.sort(Comparator.comparingInt(Player::getPlayerPosition).reversed());
    standings.addAll(others);
    return standings;
  }

  private GameResultCalculator() {}
}