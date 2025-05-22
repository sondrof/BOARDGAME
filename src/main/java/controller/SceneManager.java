package controller;

import view.scenes.AbstractScene;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages scene transitions and scene lifecycle in the application.
 * This class is responsible for handling the registration of scenes,
 * transitioning between scenes, and managing scene lifecycle events.
 * It maintains a registry of all available scenes and ensures proper
 * cleanup and initialization during scene transitions.
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class SceneManager {
  /** The JavaFX stage that hosts the scenes */
  private final Stage stage;

  /** Registry of all available scenes, mapped by their names */
  private final Map<String, AbstractScene> scenes = new HashMap<>();

  /** The currently active scene */
  private AbstractScene currentScene;
  //TODO oppgradere constructor osv
  //TODO få inn custom exception på scene bytte? SceneLoadException/SceneSaveException

  /**
   * Constructs a new SceneManager with the specified stage.
   *
   * @param stage The JavaFX stage to manage scenes for
   */
  public SceneManager(Stage stage) {
    this.stage = stage;
  }

  /**
   * Registers a new scene with the manager.
   * The scene can later be activated using its registered name.
   *
   * @param name The unique identifier for the scene
   * @param scene The scene to register
   */
  public void registerScene(String name, AbstractScene scene) {
    scenes.put(name, scene);
  }

  /**
   * Switches to a different scene.
   * This method handles the transition between scenes, including:
   * - Calling onExit() on the current scene
   * - Setting up the new scene
   * - Calling onEnter() on the new scene
   *
   * @param name The name of the scene to switch to
   * @throws RuntimeException if the specified scene is not registered
   */
  public void switchTo(String name) {
    if (currentScene != null) currentScene.onExit();
    AbstractScene next = scenes.get(name);
    if (next == null) throw new RuntimeException("Scene not registered: " + name);
    currentScene = next;
    stage.setScene(currentScene.getScene());
    currentScene.onEnter();
  }
}
