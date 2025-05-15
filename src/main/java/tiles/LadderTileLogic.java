package tiles;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LadderTileLogic extends TileLogic {
    @Override
    public void generateBoard(int size) {
        tiles.clear();
        for (int i = 1; i <= size; i++) {
            tiles.add(new LadderTile(i, 0));
        }
    }

    public void addLadder(int fromTile, int ladderValue) {
        Tile tile = getTileByNumber(fromTile);
        if (tile != null) {
            tiles.set(tiles.indexOf(tile), new LadderTile(fromTile, ladderValue));
        }
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