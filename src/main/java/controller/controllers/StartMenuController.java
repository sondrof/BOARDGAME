package controller.controllers;

import controller.SceneManager;
import view.scenes.GameSetupSceneView;

/**
 * Controller responsible for managing the start menu functionality.
 * This controller handles the initial game setup process and transitions
 * between the start menu and game setup scenes.
 *
 * @author didrik
 * @version 1.0
 */
public class StartMenuController {
  /** The scene manager responsible for handling scene transitions */
  private final SceneManager sceneManager;

  /**
   * Constructs a new StartMenuController with the specified scene manager.
   *
   * @param sceneManager The scene manager for handling UI transitions
   */
  public StartMenuController(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  /**
   * Launches the game setup process for the specified game type.
   * This method creates and registers a new game setup scene, then transitions to it.
   *
   * @param gameType The type of game to set up (e.g., "ladder", "snake")
   */
  public void launchGameSetup(String gameType) {
    GameSetupSceneView setupScene = new GameSetupSceneView(sceneManager, gameType);
    sceneManager.registerScene("gameSetup", setupScene);
    sceneManager.switchTo("gameSetup");
  }

}
