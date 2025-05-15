package tiles;

public abstract class Tile {
  private final int tileNumber;

  public Tile(int tileNumber) {
    if (tileNumber < 0) {
      throw new IllegalArgumentException("Tile number cannot be negative, got: " + tileNumber);
    }
    this.tileNumber = tileNumber;
  }

  public int getTileNumber() {
    return tileNumber;
  }


  public abstract int getEffect();


  public abstract String getDescription();
}
