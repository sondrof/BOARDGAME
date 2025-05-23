package view.ui;

import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


/**
 * Core rendering class for game board visualization.
 * Handles the creation and management of game board elements and their visual representation.
 *
 * <p>This class manages:
 * <ul>
 *     <li>Game board tile rendering and layout</li>
 *     <li>Ladder placement and visualization</li>
 *     <li>Start tile creation and positioning</li>
 *     <li>Grid-based board organization</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class UiRenderer {

  private StackPane startTile;

  /**
   * Creates a new UIRenderer instance.
   * The renderer maintains no state and performs all rendering through method calls.
   */
  public UiRenderer() {
    //helper class for rendering with methods for demand.
  }


  /**
   * Renders a single tile on the game board grid.
   * Creates a tile with an image and a number label.
   *
   * @param grid The GridPane to add the tile to
   * @param imagePath The path to the tile's image
   * @param tileNumber The number to display on the tile
   * @param col The column position in the grid
   * @param row The row position in the grid
   * @return The created StackPane containing the tile
   */
  public StackPane renderTile(GridPane grid, String imagePath, int tileNumber, int col, int row) {
    ImageView imageView = ResourceLoader.getTileIcon(imagePath, 60);
    Label label = new Label(String.valueOf(tileNumber));
    label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

    StackPane tileStack = new StackPane(imageView, label);
    tileStack.setPrefSize(60, 60);
    grid.add(tileStack, col, row);
    return tileStack;
  }

  /**
   * Renders the complete game board with tiles arranged in a snake pattern.
   * Creates and positions all tiles, including the start tile.
   *
   * @param grid The GridPane to render the board on
   * @param tileImagePaths List of image paths for each tile
   * @param rows Number of rows in the board
   * @param cols Number of columns in the board
   * @param tileNodes Map to store tile number to StackPane mappings
   */
  public void renderTiles(GridPane grid, List<String> tileImagePaths,
                          int rows, int cols,
                          Map<Integer, StackPane> tileNodes) {
    int tileNum = 1;

    for (int row = rows - 1; row >= 0; row--) {
      boolean leftToRight = (rows - 1 - row) % 2 == 0;

      if (leftToRight) {
        for (int col = 0; col < cols; col++) {
          String imagePath = tileImagePaths.get(tileNum - 1);
          StackPane tile = renderTile(grid, imagePath, tileNum, col, row);
          tileNodes.put(tileNum, tile);
          tileNum++;
        }
      } else {
        for (int col = cols - 1; col >= 0; col--) {
          String imagePath = tileImagePaths.get(tileNum - 1);
          StackPane tile = renderTile(grid, imagePath, tileNum, col, row);
          tileNodes.put(tileNum, tile);
          tileNum++;
        }
      }
    }

    Label label = new Label("Start");
    label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

    startTile = new StackPane(label);
    startTile.setPrefSize(60, 60);
    grid.add(startTile, 0, rows);
  }

  /**
   * Renders ladders on the game board using a canvas.
   * Draws ladder sprites between connected tiles based on the ladder map.
   *
   * @param canvas The canvas to draw the ladders on
   * @param ladderMap Map of tile numbers to their ladder destinations
   * @param tileNodes Map of tile numbers to their StackPane containers
   */
  public void renderLadders(Canvas canvas,
                            Map<Integer, Integer> ladderMap,
                            Map<Integer, StackPane> tileNodes) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    Image sprite = ResourceLoader.getLadderSprite();
    double w = sprite.getWidth();
    double h = sprite.getHeight();
    final double y_Ofsett = 5;

    for (var entry : ladderMap.entrySet()) {
      int start = entry.getKey();
      int end   = start + entry.getValue();
      StackPane fromNode = tileNodes.get(start);
      StackPane toNode   = tileNodes.get(end);
      if (fromNode == null || toNode == null) {
        continue;
      }

      Bounds b1 = fromNode.getBoundsInParent();
      Bounds b2 = toNode.getBoundsInParent();
      double x1 = b1.getMinX() + b1.getWidth() / 2;
      double y1 = b1.getMinY() + b1.getHeight() / 2;
      double x2 = b2.getMinX() + b2.getWidth() / 2;
      double y2 = b2.getMinY() + b2.getHeight() / 2;
      double dx = x2 - x1;
      double dy = y2 - y1;
      double distance = Math.hypot(dx, dy);
      double angle = Math.toDegrees(Math.atan2(dy, dx)) - 90;
      int segments = Math.max(2, (int) Math.ceil(distance / h) + 1);
      double step = distance / (segments - 1);

      gc.save();
      gc.translate(x1, y1 - y_Ofsett);
      gc.rotate(angle);
      for (int i = 0; i < segments; i++) {
        double drawY = i * step - h / 2;
        gc.drawImage(sprite, -w / 2, drawY);
      }

      gc.restore();
    }
  }


  /**
   * Gets the start tile StackPane.
   *
   * @return The StackPane representing the start tile
   */
  public StackPane getStartTile() {
    return startTile;
  }
}
