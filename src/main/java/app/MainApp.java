package app;

import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import view.scenes.StartMenuSceneView;

/**
 * Main application entry point for the board game launcher.
 *
 * <p>This class bootstraps the JavaFX application by:
 * <ul>
 *   <li>Initializing the JavaFX lifecycle</li>
 *   <li>Configuring the SceneManager with the start menu scene</li>
 *   <li>Displaying the primary stage with title</li>
 * </ul>
 *
 * Usage:
 * <pre>{@code
 *   public static void main(String[] args) {
 *     launch(args);
 *   }
 * }</pre>
 *
 * @author didrik
 * @version 1.0
 */

public class MainApp extends Application {
  @Override
  public void start(Stage stage) {
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.registerScene("startMenu", new StartMenuSceneView(sceneManager));
    sceneManager.switchTo("startMenu");
    stage.setTitle("Ladder Game Launcher");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}