package view;

import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import view.scenes.StartMenuSceneView;

/**
 * Main entry point for the board game application.
 * Initializes the primary stage and configures the SceneManager
 * to display the start menu scene.
 * <p></p>
 * Responsibilities:
 * <ul>
 *   <li>Initialize JavaFX Application lifecycle</li>
 *   <li>Register and display the "startMenu" scene</li>
 *   <li>Show the primary stage with application title and dimensions</li>
 * </ul>
 *
 * @author didrik
 * @version 1.0
 */
public class BoardGameView extends Application {

  /**
   * Called when the application is launched.
   * Sets up the SceneManager, registers the start menu,
   * and shows the primary stage.
   *
   * @param primaryStage the primary stage for this application
   */
  @Override
  public void start(Stage primaryStage) {
    SceneManager sceneManager = new SceneManager(primaryStage);
    sceneManager.registerScene("startMenu", new StartMenuSceneView(sceneManager));
    sceneManager.switchTo("startMenu");
    primaryStage.setTitle("Board Game");
    primaryStage.setWidth(1000);
    primaryStage.setHeight(750);
    primaryStage.show();
  }

  /**
   * Main method. Launches the JavaFX application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    launch(args);
  }
}