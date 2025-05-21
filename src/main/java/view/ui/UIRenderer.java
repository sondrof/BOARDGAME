package view.ui;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Map;

public class UIRenderer {

  private StackPane startTile;

  public UIRenderer() {
    // Ingen tilstand, alt rendres via metodekall
  }

  public StackPane renderTile(GridPane grid, String imagePath, int tileNumber, int col, int row) {
    ImageView imageView = ResourceLoader.getTileIcon(imagePath, 60);
    Label label = new Label(String.valueOf(tileNumber));
    label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

    StackPane tileStack = new StackPane(imageView, label);
    tileStack.setPrefSize(60, 60);
    grid.add(tileStack, col, row);
    return tileStack;
  }

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

    // ✅ Lag startTile som StackPane
    Label label = new Label("Start");
    label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

    startTile = new StackPane(label);
    startTile.setPrefSize(60, 60);
    grid.add(startTile, 0, rows);
  }

  public void renderLadders(Canvas canvas,
                            Map<Integer, Integer> ladderMap,
                            Map<Integer, StackPane> tileNodes) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    Image sprite = ResourceLoader.getLadderSprite();
    double w = sprite.getWidth();
    double h = sprite.getHeight();
    // Flytt stigen UP med dette antallet piksler
    final double Y_OFFSET = 5;

    for (var entry : ladderMap.entrySet()) {
      int start = entry.getKey();
      int end   = start + entry.getValue();
      StackPane fromNode = tileNodes.get(start);
      StackPane toNode   = tileNodes.get(end);
      if (fromNode == null || toNode == null) continue;

      // Senterkoordinater i parent-rom (samme som canvas)
      Bounds b1 = fromNode.getBoundsInParent();
      Bounds b2 = toNode  .getBoundsInParent();
      double x1 = b1.getMinX() + b1.getWidth()/2;
      double y1 = b1.getMinY() + b1.getHeight()/2;
      double x2 = b2.getMinX() + b2.getWidth()/2;
      double y2 = b2.getMinY() + b2.getHeight()/2;

      double dx = x2 - x1, dy = y2 - y1;
      double distance = Math.hypot(dx, dy);
      double angle = Math.toDegrees(Math.atan2(dy, dx)) - 90;

      // Antall segmenter (minst 2 for endepunkter)
      int segments = Math.max(2, (int)Math.ceil(distance / h) + 1);
      double step = distance / (segments - 1);

      gc.save();
      // Flytt startpunkt litt OPP (negative y) før rotasjon
      gc.translate(x1, y1 - Y_OFFSET);
      gc.rotate(angle);

      // Tegn alle segmentene jevnt fordelt
      for (int i = 0; i < segments; i++) {
        double drawY = i * step - h/2;
        gc.drawImage(sprite, -w/2, drawY);
      }

      gc.restore();
    }
  }

  private void drawSingleLadder(GraphicsContext gc,
                                Image sprite,
                                StackPane startNode,
                                StackPane endNode) {
    // Hent bounding-boks for flisene i parent-koordinater (samme rom som canvas)
    Bounds bStart = startNode.getBoundsInParent();
    Bounds bEnd   = endNode.getBoundsInParent();

    double x1 = bStart.getMinX() + bStart.getWidth()  / 2;
    double y1 = bStart.getMinY() + bStart.getHeight() / 2;
    double x2 = bEnd.getMinX()   + bEnd.getWidth()    / 2;
    double y2 = bEnd.getMinY()   + bEnd.getHeight()   / 2;

    double dx = x2 - x1, dy = y2 - y1;
    double distance = Math.hypot(dx, dy);
    // Sprite er vertikal – trekk fra 90° så den følger linjen
    double angle = Math.toDegrees(Math.atan2(dy, dx)) - 90;

    gc.save();
    gc.translate(x1, y1);
    gc.rotate(angle);

    int count = Math.max(1, (int)(distance / sprite.getHeight()));
    double step = distance / count;
    for (int i = 0; i <= count; i++) {
      gc.drawImage(sprite, -sprite.getWidth()/2, i * step);
    }
    gc.restore();
  }

  private void renderLadderBetween(int from, int to,
                                   GraphicsContext gc,
                                   Image sprite,
                                   Map<Integer, StackPane> tileNodes) {
    StackPane startNode = tileNodes.get(from);
    StackPane endNode   = tileNodes.get(to);
    if (startNode == null || endNode == null) return;

    Bounds b1 = startNode.localToScene(startNode.getBoundsInLocal());
    Bounds b2 = endNode.localToScene(endNode.getBoundsInLocal());

    double x1 = b1.getMinX() + b1.getWidth()/2,
        y1 = b1.getMinY() + b1.getHeight()/2,
        x2 = b2.getMinX() + b2.getWidth()/2,
        y2 = b2.getMinY() + b2.getHeight()/2;

    double dx = x2-x1, dy = y2-y1;
    double angle = Math.toDegrees(Math.atan2(dy, dx));
    double dist  = Math.hypot(dx, dy);
    int steps = Math.max(1, (int)(dist / sprite.getHeight()));
    double spacing = dist/steps;

    gc.save();
    gc.translate(x1, y1);
    gc.rotate(angle);
    for (int i = 0; i < steps; i++) {
      gc.drawImage(sprite, -sprite.getWidth()/2, i * spacing);
    }
    gc.restore();
  }


  public StackPane getStartTile() {
    return startTile;
  }
}
