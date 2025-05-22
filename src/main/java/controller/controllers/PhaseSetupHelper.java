// src/main/java/controller/controllers/PhaseSetupHelper.java
package controller.controllers;

import modell.gameboard.GamePhase;

public interface PhaseSetupHelper {
  void rollDicePhase();
  void movePlayerPhase();
  void specialTilePhase();

  default void configurePhases(GamePhaseController controller) {
    controller.registerPhaseAction(GamePhase.ROLL_DICE,    this::rollDicePhase);
    controller.registerPhaseAction(GamePhase.MOVE_PLAYER,  this::movePlayerPhase);
    controller.registerPhaseAction(GamePhase.SPECIAL_TILE, this::specialTilePhase);
  }
}
