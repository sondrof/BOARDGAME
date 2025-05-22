package modell.players;

/**
 * Represents a player in a board game.
 * This class manages player information including name, token, and position
 * on the game board. Each player has a unique name and token for identification
 * and a position that tracks their progress in the game.
 *
 * <p>The player position must be non-negative, representing their tile number
 * on the game board. Position 0 represents the starting position before the first tile.
 *
 * <p>Example usage:
 * <pre>
 * Player player = new Player("John", "Car");  // Create player with name and token
 * int position = player.getPlayerPosition();  // Get current position
 * player.setPlayerPosition(position + 5);     // Move player forward 5 spaces
 * </pre>
 *
 * @author didrik
 * @version 1.0
 */
public class Player {
  /** The name of the player */
  private final String name;
  /** The token representing the player on the board */
  private final PlayerToken token;
  /** The current position of the player on the board */
  private int playerPosition;

  /**
   * Constructs a new player with the specified name and token.
   * The initial position is set to 0 (starting position).
   *
   * @param name the name of the player
   * @param token the token representing the player
   * @throws IllegalArgumentException if name or token is null or blank
   */
  public Player(String name, PlayerToken token) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Player name cannot be blank");
    }
    if (token == null) {
      throw new IllegalArgumentException("Player token cannot be null");
    }
    this.name = name;
    this.token = token;
    this.playerPosition = 0;
  }

  /**
   * Returns the player's name.
   *
   * @return the player's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the player's token.
   *
   * @return the player's token
   */
  public PlayerToken getToken() {
    return token;
  }

  /**
   * Sets the player's position on the game board.
   * The position represents the tile number the player is currently on.
   *
   * @param playerPosition the new position
   * @throws IllegalArgumentException if playerPosition is negative
   */
  public void setPlayerPosition(int playerPosition) {
    if (playerPosition < 0) {
      throw new IllegalArgumentException("Player position can not be negative");
    }
    this.playerPosition = playerPosition;
  }

  /**
   * Returns the player's current position on the game board.
   *
   * @return the player's position
   */
  public int getPlayerPosition() {
    return playerPosition;
  }
}
