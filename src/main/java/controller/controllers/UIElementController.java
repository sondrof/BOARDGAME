package controller.controllers;

import javafx.scene.layout.StackPane;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import view.ui.PlayerRenderer;
import view.ui.ResourceLoader;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIElementController {

  private final PlayerRenderer playerRenderer = new PlayerRenderer();
  private final Map<Integer, Integer> playerPositions = new HashMap<>();

  public void updatePlayerPosition(int playerId, int newPos) {
    playerPositions.put(playerId, newPos);
  }

  public void renderPlayers(Map<Integer, StackPane> tileNodes, StackPane startTile) {
    playerRenderer.renderPlayers(playerPositions, tileNodes, startTile);
  }

  public String getTileImagePath(int tileNumber, TileLogic tileLogic) {
    if (tileLogic instanceof LadderTileLogic ladderLogic) {
      Map<Integer, Integer> ladderMap = ladderLogic.getLadderMap();
      int offset = ladderMap.getOrDefault(tileNumber, 0);

      if (offset > 0) return "ladder_up_bottom.png";
      if (offset < 0) return "ladder_down_top.png";

      for (Map.Entry<Integer, Integer> entry : ladderMap.entrySet()) {
        int start = entry.getKey();
        int end = start + entry.getValue();

        if (tileNumber == end && entry.getValue() > 0) {
          return "ladder_up_top.png";
        }
        if (tileNumber == end && entry.getValue() < 0) {
          return "ladder_down_bottom.png";
        }

      }
    }

    return "basicTile.png";
  }



  public List<String> getTileImagePaths(TileLogic logic) {
    List<String> result = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      result.add(getTileImagePath(i, logic));
    }
    return result;
  }


}
