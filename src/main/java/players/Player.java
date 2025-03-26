package players;

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
 */
public class Player {
  /** The name of the player */
  private String playerName;
  /** The token representing the player on the board */
  private String playerToken;
  /** The current position of the player on the board */
  private int playerPosition;

  /**
   * Constructs a new player with the specified name and token.
   * The initial position is set to 0 (starting position).
   *
   * @param playerName the name of the player
   * @param playerToken the token representing the player
   * @throws IllegalArgumentException if playerName or playerToken is null or blank
   */
  public Player(String playerName, String playerToken) {
    setPlayerName(playerName);
    setPlayerToken(playerToken);
    this.playerPosition = 0;
  }

  /**
   * Sets the player's name.
   * The name is used to identify the player during the game.
   *
   * @param playerName the name to set
   * @throws IllegalArgumentException if playerName is null or blank
   */
  public void setPlayerName(String playerName) {
    if (playerName == null || playerName.isBlank()) {
      throw new IllegalArgumentException("Player cannot be blank");
    }
    this.playerName = playerName;
  }

  /**
   * Returns the player's name.
   *
   * @return the player's name
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * Sets the player's token.
   * The token represents the player's piece on the game board.
   *
   * @param token the token to set
   * @throws IllegalArgumentException if token is null or blank
   */
  public void setPlayerToken(String token) {
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("Player token cannot be blank");
    }
    this.playerToken = token;
  }

  /**
   * Returns the player's token.
   *
   * @return the player's token
   */
  public String getPlayerToken() {
    return playerToken;
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
