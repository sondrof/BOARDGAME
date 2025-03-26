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
    playerList.add(new Player(name,"DefaultToken"));
  }

  public void printPlayerStatus() {
    for (Player player : playerList) {
      System.out.println("Player " + player.getPlayerName() + " on tile " + player.getPlayerPosition());
    }
  }

  public void generatePlayers(int amountOfPlayers) {
    for (int i = 0; i < amountOfPlayers; i++) {
      playerList.add(new Player("Player" + (i + 1), "DefaultToken"));
    }
  }

  public ArrayList<Player> getPlayerList() {
    return playerList;
  }

  public void movePlayer(int playerIndex) {
    if (playerIndex < 0 || playerIndex >= playerList.size()) {
      throw new IllegalArgumentException("Invalid player index: " + playerIndex);
    }

    Player player = playerList.get(playerIndex);
    int roll = diceSet.roll();
    player.setPlayerPosition(player.getPlayerPosition() + roll);
    System.out.println(player.getPlayerName() + " rolled " + roll + ", moved to position " + player.getPlayerPosition());
  }
}
