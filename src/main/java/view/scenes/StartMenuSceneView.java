package view.scenes;

import controller.SceneManager;
import controller.controllers.StartMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.*;
import view.ui.ResourceLoader;


/**
 * Scene view for the main start menu.
 * Provides buttons to launch different game modes including the new Spill2.
 *
 * <p>Layout includes:
 * <ul>
 *   <li>Game logo</li>
 *   <li>Buttons for each game mode</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.1
 */
public class StartMenuSceneView extends AbstractScene {

  public StartMenuSceneView(SceneManager manager) {
    super("startMenu", buildScene(manager));
  }


  private static Scene buildScene(SceneManager manager) {
    StartMenuController controller = new StartMenuController(manager);

    Button ladderGameButton = new Button("Ladder Game");
    ladderGameButton.setGraphic(ResourceLoader.getButtonIcon("roll_button.png", 24));
    ladderGameButton.setContentDisplay(ContentDisplay.LEFT);
    ladderGameButton.setOnAction(e -> controller.launchGameSetup("ladder"));

    Button otherGameButton = new Button("Other Game (Coming Soon)");
    otherGameButton.setGraphic(ResourceLoader.getButtonIcon("roll_button.png", 24));
    otherGameButton.setContentDisplay(ContentDisplay.LEFT);
    otherGameButton.setDisable(true);

    VBox layout = new VBox(20, ladderGameButton, otherGameButton);
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