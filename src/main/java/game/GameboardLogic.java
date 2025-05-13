package game;

import players.Player;
import tiles.Tile;
import tiles.TileLogic;

public class GameboardLogic {
    private static final int BOARD_SIZE = 100;

    public void handlePlayerLanding(Player player, TileLogic tileLogic) {
        int currentPos = player.getPlayerPosition();

        if (currentPos > BOARD_SIZE) {
            int bounce = currentPos - BOARD_SIZE;
            currentPos = BOARD_SIZE - bounce;
            player.setPlayerPosition(currentPos);
            System.out.println(player.getPlayerName() + " bounced back to " + currentPos);
        }

        Tile currentTile = tileLogic.getTileByNumber(currentPos);
        if (currentTile != null && currentTile.getSpecialValue() != 0) {
            movePlayerSpecial(player, currentTile);
        }
    }

    private void movePlayerSpecial(Player player, Tile tile) {
        int newPos = player.getPlayerPosition() + tile.getSpecialValue();
        player.setPlayerPosition(newPos);

        String moveType;
        if (tile.getSpecialValue() > 0) {
            moveType = "climbed a ladder to";
        } else {
            moveType = "falled down a ladder to";
        }
        System.out.println(player.getPlayerName() + " " + moveType + " " + newPos);
    }

    public boolean checkWinCondition(Player player) {
        return player.getPlayerPosition() >= BOARD_SIZE;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }


}