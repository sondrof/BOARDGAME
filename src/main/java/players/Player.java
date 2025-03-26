package players;

public class Player {
  private String playerName;
  private String playerToken;
  private int playerPosition;


  public Player(String playerName, String playerToken) {
    setPlayerName(playerName);
    setPlayerToken(playerToken);
    this.playerPosition = 0;
  }

  public void setPlayerName(String playerName) {
    if (playerName == null || playerName.isBlank()) {
      throw new IllegalArgumentException("Player cannot be blank");
    }
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerPosition(int playerPosition) {
    if (playerPosition < 0) {
      throw new IllegalArgumentException("Player position can not be negative");
    }
    this.playerPosition = playerPosition;
  }

  public int getPlayerPosition() {
    return playerPosition;
  }


  public void setPlayerToken(String token) {
    if (token == null || token.isBlank()) {
      throw new IllegalArgumentException("Player token cannot be blank");
    }
    this.playerToken = token;
  }

  public String getPlayerToken() {
    return playerToken;
  }


}
