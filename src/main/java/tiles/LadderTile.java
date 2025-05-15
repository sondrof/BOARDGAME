package tiles;

public class LadderTile extends Tile {
    private final int ladderValue; // positive for up, negative for down

    public LadderTile(int tileNumber, int ladderValue) {
        super(tileNumber);
        this.ladderValue = ladderValue;
    }

    @Override
    public int getEffect() {
        return ladderValue;
    }

    @Override
    public String getDescription() {
        return ladderValue > 0 ?
                "Ladder up " + ladderValue + " spaces" :
                "Ladder down " + Math.abs(ladderValue) + " spaces";
    }
}