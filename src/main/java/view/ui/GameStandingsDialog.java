package view.ui;

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modell.players.Player;


/**
 * Utility class for displaying game results and final standings.
 * Creates and manages a dialog window showing player rankings at the end of a game.
 *
 * <p>This class provides:
 * <ul>
 *     <li>Customized dialog layout for game results</li>
 *     <li>Ordered display of player standings</li>
 *     <li>Navigation back to the start menu</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class GameStandingsDialog {
  /**
   * Displays a dialog window showing the final game standings.
   * Creates an information dialog with a list of players in order of their finish position.
   *
   * <p>The dialog includes:
   * <ul>
   *     <li>A title and header indicating game completion</li>
   *     <li>A numbered list of players in order of finish</li>
   *     <li>A "Back to Start" button for navigation</li>
   * </ul>
   *
   * @param standings List of players in order of finish (1st place first, 2nd place second, etc.)
   * @param onBack Runnable to execute when the dialog is closed or "Back to Start" is pressed
   */
  public static void show(List<Player> standings, Runnable onBack) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Game Over");
    alert.setHeaderText("Final Standings");
    alert.getButtonTypes().setAll(new ButtonType("Back to Start"));

    VBox content = new VBox(10);
    content.setStyle("-fx-padding: 10;");
    int place = 1;
    for (Player player : standings) {
      String placeStr = place + ". place: " + player.getName();
      Text text = new Text(placeStr);
      text.setStyle("-fx-font-size: 16px;");
      content.getChildren().add(text);
      place++;
    }
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.setContent(content);

    alert.setOnCloseRequest(e -> onBack.run());
    alert.showAndWait();
  }

  private GameStandingsDialog() {}
}