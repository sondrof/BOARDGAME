package tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LadderTileLogic extends TileLogic {
    private static final int MAX_EFFECT = 100;

    @Override
    public void generateBoard(int size) {

        tiles.clear();

        for (int i = 1; i <= size; i++) {
            tiles.add(new LadderTile(i, 0));
        }
    }

    public void addLadder(int fromTile, int ladderValue) {
        Tile tile = getTileByNumber(fromTile);
        if (tile == null) {
            return;
        }

        if (tile.getEffect() != 0) {
            throw new IllegalStateException("Tile " + fromTile + " already has a ladder");
        }

        int targetTile = fromTile + ladderValue;
        if (targetTile <= 0 || targetTile > getBoardSize()) {
            throw new IllegalArgumentException("Ladder effect would make player go beyond board boundaries");
        }

        if (Math.abs(ladderValue) > MAX_EFFECT) {
            throw new IllegalArgumentException("Ladder effect value " + ladderValue + " exceeds maximum allowed value of " + MAX_EFFECT);
        }

        tiles.set(tiles.indexOf(tile), new LadderTile(fromTile, ladderValue));
    }

    public Map<Integer, Integer> getLadderMap() {
        return tiles.stream()
                .filter(tile -> tile.getEffect() != 0)
                .collect(Collectors.toMap(
                        Tile::getTileNumber,
                        Tile::getEffect
                ));
    }
}