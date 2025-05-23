package view.scenes;

import javafx.scene.Scene;

/**
 * Abstract base class for all scene views in the application.
 * Provides common functionality for scene management including lifecycle hooks
 * and basic scene properties.
 *
 * <p>This class serves as the foundation for all scene implementations,
 * offering methods for scene transitions and resource management.
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public abstract class AbstractScene {

  private String name;
  private Scene scene;

  protected AbstractScene(String name, Scene scene) {
    setName(name);
    setScene(scene);
  }

  public void onEnter() {}
  public void onExit() {}

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

