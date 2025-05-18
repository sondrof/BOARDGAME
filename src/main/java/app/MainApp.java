package app;

import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import view.scenes.StartMenuSceneView;

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