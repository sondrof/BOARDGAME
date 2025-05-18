package view.scenes;

import javafx.scene.Scene;

public abstract class AbstractScene {

  private String name;
  private Scene scene;

  protected AbstractScene(String name, Scene scene) {
    setName(name);
    setScene(scene);
  }

  public void onEnter() {} //Her kan vi loade save files, ressurser/sprites og alt av random ting som skjer i loading screens.
  public void onExit() {} // Når senen skal lukkes, hvor vi kan f.eks save daten før man lokker senen. Exit loading screen typ.

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

