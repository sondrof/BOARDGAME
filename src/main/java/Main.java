import game.Gameboard;

public class Main {
  public static void main(String[] args) {
    Gameboard game = new Gameboard();
    game.initBoard();

    while (true) {
      game.playRound();
    }
  }
}