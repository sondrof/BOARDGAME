package view.scenes;

import controller.SceneManager;
import controller.controllers.StartMenuController;
import modell.gameboard.GameBoardFactory.BoardType;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import view.ui.ResourceLoader;

public class StartMenuSceneView extends AbstractScene {

  public StartMenuSceneView(SceneManager manager) {
    super("startMenu", buildScene(manager));
  }

  private static Scene buildScene(SceneManager manager) {
    StartMenuController controller = new StartMenuController(manager);

    Button gameAButton = new Button("Start Spill A (standard)");
    gameAButton.setGraphic(ResourceLoader.getButtonIcon("roll_button.png", 24));
    gameAButton.setContentDisplay(ContentDisplay.LEFT);
    gameAButton.setOnAction(e -> controller.launchGame(BoardType.STANDARD, null));

    Button gameBButton = new Button("Start Spill B (custom)");
    gameBButton.setGraphic(ResourceLoader.getButtonIcon("roll_button.png", 24));
    gameBButton.setContentDisplay(ContentDisplay.LEFT);
    gameBButton.setOnAction(e -> controller.launchGame(BoardType.FROM_FILE, "custom_board.json"));

    VBox layout = new VBox(20, gameAButton, gameBButton);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(20));


    BackgroundImage bg = new BackgroundImage(
        ResourceLoader.getBackground("start_background.png"),
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(100, 100, true, true, true, true)
    );
    layout.setBackground(new Background(bg));

    return new Scene(layout, 1000, 750);
  }

  @Override
  public void onEnter() {
    ResourceLoader.preloadStartMenuAssets();
  }
}
