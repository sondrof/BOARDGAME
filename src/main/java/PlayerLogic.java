import dice.DiceSet;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class PlayerLogic {

  private DiceSet diceSet;
  private ArrayList<Player> playerList = new ArrayList<>();

  public void generatePlayers(int amountOfPlayers) {
    for (int i = 0; i < amountOfPlayers; i++) {
      playerList.add(new Player("Player" + (i + 1), 0, i + 1));
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
      System.out.println("Player not found");
      return;
    }

    int roll = diceSet.roll();
    player.setPlayerPos(player.getPlayerPos() + roll);
    System.out.println(player.getPlayerName() + " rolled " + roll + ", moved to position " + player.getPlayerPos());
  }



}
