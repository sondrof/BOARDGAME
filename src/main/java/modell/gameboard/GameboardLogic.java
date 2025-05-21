package modell.gameboard;

import modell.players.Player;
import modell.tiles.Tile;
import modell.tiles.TileLogic;

public class GameboardLogic {
    private static final int BOARD_SIZE = 100;

    public void handlePlayerLanding(Player player, TileLogic tileLogic) {
        int currentPos = player.getPlayerPosition();

        if (currentPos > BOARD_SIZE) {
            int bounce = currentPos - BOARD_SIZE;
            currentPos = BOARD_SIZE - bounce;
            player.setPlayerPosition(currentPos);
            System.out.println(player.getName() + " bounced back to " + currentPos);
        }

        Tile currentTile = tileLogic.getTileByNumber(currentPos);
        if (currentTile != null && currentTile.getEffect() != 0) {
            movePlayerSpecial(player, currentTile);
        }
    }

    private void movePlayerSpecial(Player player, Tile tile) {
        int newPos = player.getPlayerPosition() + tile.getEffect();
        player.setPlayerPosition(newPos);

        String moveType;
        if (tile.getEffect() > 0) {
            moveType = "climbed a ladder to";
        } else {
            moveType = "fell down a ladder to";
        }
        System.out.println(player.getName() + " " + moveType + " " + newPos);
    }

    public boolean checkWinCondition(Player player) {
        return player.getPlayerPosition() >= BOARD_SIZE;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
}