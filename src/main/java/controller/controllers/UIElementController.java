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

/**
 * Controller responsible for managing UI elements and their visual representation.
 * This controller handles the rendering of players and tiles on the game board,
 * including special tile types like ladders and their associated images.
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class UIElementController {
  /** Renderer responsible for drawing players on the board */
  private final PlayerRenderer playerRenderer = new PlayerRenderer();

  /** Map tracking the current position of each player on the board */
  private final Map<Integer, Integer> playerPositions = new HashMap<>();

  /**
   * Updates the position of a player on the board.
   *
   * @param playerId The ID of the player to update
   * @param newPos The new position of the player on the board
   */
  public void updatePlayerPosition(int playerId, int newPos) {
    playerPositions.put(playerId, newPos);
  }

  /**
   * Renders all players on the game board.
   * This method uses the PlayerRenderer to draw players at their current positions.
   *
   * @param tileNodes Map of tile numbers to their StackPane representations
   * @param startTile The StackPane representing the starting tile
   */
  public void renderPlayers(Map<Integer, StackPane> tileNodes, StackPane startTile) {
    playerRenderer.renderPlayers(playerPositions, tileNodes, startTile);
  }

  /**
   * Gets the image path for a specific tile based on its number and logic.
   * This method determines the appropriate image to use for special tiles like ladders.
   *
   * @param tileNumber The number of the tile
   * @param tileLogic The logic component containing tile information
   * @return The path to the image file for the tile
   */
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

  /**
   * Gets the image paths for all tiles on the board.
   * This method generates a list of image paths for all 100 tiles on the board.
   *
   * @param logic The tile logic component containing board information
   * @return A list of image paths for all tiles
   */
  public List<String> getTileImagePaths(TileLogic logic) {
    List<String> result = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      result.add(getTileImagePath(i, logic));
    }
    return result;
  }
}
