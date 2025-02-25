public class GameboardLogic {
    private static final int BOARD_SIZE = 100;

    public void handlePlayerLanding(Player player, TileLogic tileLogic) {
        if (player.getPlayerPos() >= BOARD_SIZE) {
            player.setPlayerPos(BOARD_SIZE); // Set to exactly 100
            return;
        }

        Tile currentTile = tileLogic.getTileByNumber(player.getPlayerPos());
        if (currentTile.getSpecialValue() != 0) {
            movePlayerSpecial(player, currentTile);
        }
    }

    private void movePlayerSpecial(Player player, Tile tile) {
        int newPos = player.getPlayerPos() + tile.getSpecialValue();
        player.setPlayerPos(newPos);

        String moveType = tile.getSpecialValue() > 0 ? "climbed a ladder to" : "slid down a snake to";
        System.out.println(player.getPlayerName() + " " + moveType + " " + newPos);
    }

    public boolean checkWinCondition(Player player) {
        return player.getPlayerPos() >= BOARD_SIZE;
    }
}