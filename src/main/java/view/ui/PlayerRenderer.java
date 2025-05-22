package view.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.*;

/**
 * Utility class for rendering player tokens on the game board.
 * Manages the visual representation and positioning of player pieces.
 *
 * <p>This class handles:
 * <ul>
 *     <li>Player token placement on board tiles</li>
 *     <li>Multiple player positioning on the same tile</li>
 *     <li>Token image loading and scaling</li>
 *     <li>Dynamic updates of player positions</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class PlayerRenderer {

  /**
   * Renders player tokens on the game board based on their current positions.
   * Clears existing player tokens and places them according to the provided positions.
   *
   * <p>The rendering process:
   * <ol>
   *     <li>Removes all existing player tokens from tiles</li>
   *     <li>Groups players by their current tile position</li>
   *     <li>Creates and positions player tokens with appropriate offsets</li>
   * </ol>
   *
   * @param playerPositions Map of player IDs to their current tile positions
   * @param tileNodes Map of tile numbers to their StackPane containers
   * @param startTile StackPane for the start position (position 0)
   */
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


  /**
   * Creates an ImageView for a player token with the specified ID.
   * Loads the appropriate player icon and configures its display properties.
   *
   * @param playerId The ID of the player whose token to create
   * @return An ImageView configured with the player's token image
   */
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
