package view.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.*;

public class PlayerRenderer {

  public void renderPlayers(Map<Integer, Integer> playerPositions,
                            Map<Integer, StackPane> tileNodes,
                            StackPane startTile) {


    for (StackPane tile : tileNodes.values()) {
      tile.getChildren().removeIf(node -> node instanceof ImageView && "player".equals(node.getUserData()));
    }
    if (startTile != null) {
      startTile.getChildren().removeIf(node -> node instanceof ImageView && "player".equals(node.getUserData()));
    }


    Map<Integer, List<Integer>> playersPerTile = new HashMap<>();
    for (Map.Entry<Integer, Integer> entry : playerPositions.entrySet()) {
      playersPerTile.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
    }


    for (Map.Entry<Integer, List<Integer>> entry : playersPerTile.entrySet()) {
      int pos = entry.getKey();
      List<Integer> playerIds = entry.getValue();

      StackPane target = (pos == 0) ? startTile : tileNodes.get(pos);
      if (target == null) continue;

      for (int i = 0; i < playerIds.size(); i++) {
        int playerId = playerIds.get(i);
        ImageView icon = createPlayerIcon(playerId);


        double offset = 8; // px
        icon.setTranslateX((i % 2 == 0 ? -1 : 1) * ((i + 1) / 2) * offset);
        icon.setTranslateY((i / 2) * offset);

        target.getChildren().add(icon);
      }
    }
  }


  private ImageView createPlayerIcon(int playerId) {
    String filename = "player" + playerId + ".png";
    Image image = ResourceLoader.getPlayerIcon(filename);
    ImageView view = new ImageView(image);
    view.setFitWidth(24);
    view.setFitHeight(24);
    view.setUserData("player");
    return view;
  }
}
