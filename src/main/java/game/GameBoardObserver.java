package game;

import players.Player;


public interface GameBoardObserver {


    void onPlayerMove(Player player, int oldPosition, int newPosition, int diceRoll);


    void onSpecialTileActivated(Player player, int fromTile, int toTile, int specialValue);


    void onGameWon(Player winner);


    void onRoundStart(int roundNumber);
}