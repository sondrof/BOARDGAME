package view.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modell.players.Player;
import java.util.List;

/**
 * Utility class to show a dialog with final game standings.
 */
public class GameStandingsDialog {
    /**
     * Shows the standings dialog.
     * @param standings List of players in order (1st, 2nd, ...)
     * @param onBack Runnable to execute when 'Back to Start' is pressed
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
}