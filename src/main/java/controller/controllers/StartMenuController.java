package controller.controllers;

import controller.SceneManager;
import modell.gameboard.GameBoardFactory.BoardType;
import view.scenes.LadderGameSceneView;

public class StartMenuController {
  private final SceneManager manager;

  public StartMenuController(SceneManager manager) {
    this.manager = manager;
  }

  public void launchGame(BoardType type, String filename) {
    LadderGameController gameController = new LadderGameController(manager);
    LadderGameSceneView gameScene = new LadderGameSceneView(gameController);
    manager.registerScene("ladderGame", gameScene);
    gameController.launchGame(type, filename);
    manager.switchTo("ladderGame");
  }
}
