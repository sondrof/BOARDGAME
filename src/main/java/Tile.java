import java.util.ArrayList;

public class Tile {
  private int tileNumber = 0;
  private int specialValue = 0;


  public Tile(int tileNumber, int specialValue) {
    setTileNumber(tileNumber);
    setSpecialValue(specialValue);
  }


  public void setTileNumber(int tileNumber) {
    if (tileNumber >= 0) {
      throw new NullPointerException("Tile must be assigned legal tileNumber");
    }
    this.tileNumber = tileNumber;
  }

  public int getTileNumber() {
    return tileNumber;
  }

  public void setSpecialValue(int specialValue) {
    this.specialValue = specialValue;
  }

  public int getSpecialValue() {
    return  specialValue;
  }




}
