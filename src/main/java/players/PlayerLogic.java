package players;

import dice.DiceSet;
import java.util.ArrayList;


public class PlayerLogic {

  private DiceSet diceSet;
  private ArrayList<Player> playerList = new ArrayList<>();

  public PlayerLogic(DiceSet diceSet) {
    this.diceSet = diceSet;
    this.playerList = new ArrayList<>();
  }

  public void addPlayer(String name) {
    int id = playerList.size() + 1;
    playerList.add(new Player(name,id,"DefaultToken"));
  }

  public void printPlayerStatus() {
    for (Player player : playerList) {
      System.out.println("Player " + player.getPlayerName() + " on tile " + player.getPlayerPos());
    }
  }

  public void generatePlayers(int amountOfPlayers) {
    for (int i = 0; i < amountOfPlayers; i++) {
      playerList.add(new Player("Player" + (i + 1), i + 1, "DefaultToken"));
    }
  }

  public ArrayList<Player> getPlayerList() {
    return playerList;
  }

  private Player getPlayerById(int playerId) {
    for (Player player : playerList) {
      if (player.getPlayerId() == playerId) {
        return player;
      }
    }
    throw new IllegalArgumentException("Player with id " + playerId + " not found");
  }

  public void movePlayer(int playerId) {
    Player player = getPlayerById(playerId);
    if (player == null) {
      throw new IllegalArgumentException("Player not found: " + playerId);
    }

    int roll = diceSet.roll();
    player.setPlayerPos(player.getPlayerPos() + roll);
    System.out.println(player.getPlayerName() + " rolled " + roll + ", moved to position " + player.getPlayerPos());
  }



}
