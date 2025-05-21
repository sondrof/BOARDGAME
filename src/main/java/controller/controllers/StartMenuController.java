package controller.controllers;

import controller.SceneManager;
import view.scenes.GameSetupSceneView;

public class StartMenuController {
  private final SceneManager sceneManager;

  public StartMenuController(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public void launchGameSetup(String gameType) {
    GameSetupSceneView setupScene = new GameSetupSceneView(sceneManager, gameType);
    sceneManager.registerScene("gameSetup", setupScene);
    sceneManager.switchTo("gameSetup");
  }
}
