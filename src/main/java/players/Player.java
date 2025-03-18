package players;

public class Player {
  private String playerName = "";
  private int playerId = 0;
  private String playerToken = "";
  private int playerPos = 0;



  public Player(String playerName, int playerId, String token) {
    setPlayerName(playerName);
    setPlayerId(playerId);
    setPlayerToken(token);
    this.playerPos = 0;
  }


  public Player(String playerName, int playerId, String token, int playerPos) {
    setPlayerName(playerName);
    setPlayerId(playerId);
    setPlayerToken(token);
    setPlayerPos(playerPos);
  }


  public void setPlayerName(String playerName) {
    if (playerName.isBlank()) {
      throw new IllegalArgumentException("Name can not be blank");
    }
    this.playerName = playerName;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerPos(int newPlayerPos) {
    if (newPlayerPos < 0) {
      throw new IllegalArgumentException("Player position can not be negative");
    }
    this.playerPos = newPlayerPos;
  }

  public int getPlayerPos() {
    return playerPos;
  }

  public void setPlayerId(int playerId) {
    if (playerId <= 0) {
      throw new IllegalArgumentException("Player id can not be negative");
    }
    this.playerId = playerId;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerToken() {
    return playerToken;
  }

  public void setPlayerToken(String token) {
    this.playerToken = token;
  }



}
