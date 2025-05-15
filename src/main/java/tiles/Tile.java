package tiles;

public abstract class Tile {
  private static final int MAX_TILE_NUMBER = 1000;
  private final int tileNumber;

  public Tile(int tileNumber) {
    if (tileNumber < 0) {
      throw new IllegalArgumentException("Tile number cannot be negative, got: " + tileNumber);
    }
    if (tileNumber > MAX_TILE_NUMBER) {
      throw new IllegalArgumentException("Tile number exceeds maximum allowed value of " + MAX_TILE_NUMBER);
    }
    this.tileNumber = tileNumber;
  }

  public int getTileNumber() {
    return tileNumber;
  }


  public abstract int getEffect();


  public abstract String getDescription();
}
