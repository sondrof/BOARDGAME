package tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LadderTileLogic extends TileLogic {
    private static final int MAX_EFFECT = 100;
    private static final int MAX_BOARD_SIZE = 1000;

    @Override
    public void generateBoard(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Board size must be positive, got: " + size);
        }
        if (size > MAX_BOARD_SIZE) {
            throw new IllegalArgumentException("Board size exceeds maximum allowed value of " + MAX_BOARD_SIZE);
        }
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


        if (wouldCreateCircularPath(fromTile, targetTile)) {
            throw new IllegalArgumentException("Adding ladder would create a circular path");
        }

        tiles.set(tiles.indexOf(tile), new LadderTile(fromTile, ladderValue));
    }

    private boolean wouldCreateCircularPath(int startTile, int targetTile) {
        int currentTile = targetTile;
        int steps = 0;
        int maxSteps = getBoardSize();

        while (steps < maxSteps) {
            Tile tile = getTileByNumber(currentTile);
            if (tile == null || tile.getEffect() == 0) {
                return false;
            }

            currentTile = currentTile + tile.getEffect();
            if (currentTile == startTile) {
                return true;
            }

            steps++;
        }

        return false;
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