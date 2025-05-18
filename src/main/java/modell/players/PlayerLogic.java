package modell.players;

import modell.dice.DiceSet;
import java.util.ArrayList;

/**
 * Manages a collection of players in a board game.
 * This class handles player creation, movement, and status tracking.
 * It uses a dice set to determine player movements and maintains
 * a list of all players in the game.
 *
 * <p>Players can be added individually or generated in bulk.
 * Each player is assigned a default token upon creation.
 *
 * <p>Example usage:
 * <pre>
 * PlayerLogic playerLogic = new PlayerLogic(new DiceSet(2));
 * playerLogic.addPlayer("John");
 * playerLogic.movePlayer(0);  // Move the first player
 * playerLogic.printPlayerStatus();  // Display all players' positions
 * </pre>
 */
public class PlayerLogic {

  /** The dice set used for player movements */
  private DiceSet diceSet;
  /** The list of all players in the game */
  private ArrayList<Player> playerList = new ArrayList<>();

  /**
   * Constructs a new PlayerLogic with the specified dice set.
   * Initializes an empty list of players.
   *
   * @param diceSet the dice set used for player movements
   */
  public PlayerLogic(DiceSet diceSet) {
    this.diceSet = diceSet;
    this.playerList = new ArrayList<>();
  }

  /**
   * Adds a new player with the specified name to the game.
   * The player is assigned a default token.
   *
   * @param name the name of the player to add
   */
  public void addPlayer(String name) {
    playerList.add(new Player(name,"DefaultToken"));
  }

  /**
   * Prints the current status of all players to the console.
   * Displays each player's name and current position on the board.
   */
  public void printPlayerStatus() {
    for (Player player : playerList) {
      System.out.println("Player " + player.getPlayerName() + " on tile " + player.getPlayerPosition());
    }
  }

  /**
   * Generates a specified number of players with default names.
   * Each player is named "Player" followed by their number (e.g., "Player1").
   *
   * @param amountOfPlayers the number of players to generate
   */
  public void generatePlayers(int amountOfPlayers) {
    for (int i = 0; i < amountOfPlayers; i++) {
      playerList.add(new Player("Player" + (i + 1), "DefaultToken"));
    }
  }

  /**
   * Returns the list of all players in the game.
   *
   * @return the list of players
   */
  public ArrayList<Player> getPlayerList() {
    return playerList;
  }

  /**
   * Moves the player at the specified index by rolling the dice.
   * The player's position is updated based on the dice roll.
   *
   * @param playerIndex the index of the player to move
   * @throws IllegalArgumentException if the player index is invalid
   */
  public void movePlayer(int playerIndex) {
    if (playerIndex < 0 || playerIndex >= playerList.size()) {
      throw new IllegalArgumentException("Invalid player index: " + playerIndex);
    }

    Player player = playerList.get(playerIndex);
    int roll = diceSet.roll();
    player.setPlayerPosition(player.getPlayerPosition() + roll);
    System.out.println(player.getPlayerName() + " rolled " + roll + ", moved to position " + player.getPlayerPosition());
  }

  //Legge til doc
  public DiceSet getDiceSet() {
    return diceSet;
  }
}
