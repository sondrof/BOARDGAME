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
    playerList.add(new Player(name, 0, id));
  }

  public void printPlayerStatus() {
    for (Player player : playerList) {
      System.out.println("players.Player " + player.getPlayerName() + " on tile " + player.getPlayerPos());
    }
  }

  public void generatePlayers(int amountOfPlayers) {
    for (int i = 0; i < amountOfPlayers; i++) {
      playerList.add(new Player("players.Player" + (i + 1), 0, i + 1));
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
    return null;
  }

  public void movePlayer(int playerId) {
    Player player = getPlayerById(playerId);
    if (player == null) {
      System.out.println("players.Player not found");
      return;
    }

    int roll = diceSet.roll();
    player.setPlayerPos(player.getPlayerPos() + roll);
    System.out.println(player.getPlayerName() + " rolled " + roll + ", moved to position " + player.getPlayerPos());
  }



}
