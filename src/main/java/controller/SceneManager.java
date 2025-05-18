package controller;

import view.scenes.AbstractScene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {
  private final Stage stage;
  private final Map<String, AbstractScene> scenes = new HashMap<>();
  private AbstractScene currentScene;
  //TODO oppgradere constructor osv
  //TODO få inn custom exception på scene bytte? SceneLoadException/SceneSaveException
  public SceneManager(Stage stage) {
    this.stage = stage;
  }

  public void registerScene(String name, AbstractScene scene) {
    scenes.put(name, scene);
  }

  public void switchTo(String name) {
    if (currentScene != null) currentScene.onExit();
    AbstractScene next = scenes.get(name);
    if (next == null) throw new RuntimeException("Scene not registered: " + name);
    currentScene = next;
    stage.setScene(currentScene.getScene());
    currentScene.onEnter();
  }
}
