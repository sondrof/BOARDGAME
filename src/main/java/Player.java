public class Player {
  private String playerName = "";
  private int playerPos = 0;
  private int playerId = 0;

  public Player(String playerName, int playerPos, int playerId) {
    setPlayerName(playerName);
    setPlayerPos(playerPos);
    setPlayerId(playerId);
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
    this.playerPos = newPlayerPos;
  }

  public int getPlayerPos() {
    return playerPos;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public int getPlayerId() {
    return playerId;
  }






}
