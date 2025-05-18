package view.scenes;

import controller.SceneManager;
import controller.controllers.StartMenuController;
import modell.gameboard.GameBoardFactory.BoardType;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import static view.ui.UIFactory.*;

public class StartMenuSceneView extends AbstractScene {

  public StartMenuSceneView(SceneManager manager) {
    super("startMenu", buildScene(manager));
  }

  private static Scene buildScene(SceneManager manager) {
    StartMenuController controller = new StartMenuController(manager);

    Button gameAButton = button("Start Spill A (standard)", () ->
        controller.launchGame(BoardType.STANDARD, null));

    Button gameBButton = button("Start Spill B (custom)", () ->
        controller.launchGame(BoardType.FROM_FILE, "custom_board.json"));

    VBox layout = groupButtons(Pos.CENTER, 20, new Insets(20), gameAButton, gameBButton);

    return new Scene(layout, 1000, 750);
  }
}
