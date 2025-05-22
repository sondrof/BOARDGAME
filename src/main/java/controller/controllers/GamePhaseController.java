// src/main/java/controller/controllers/GamePhaseController.java
package controller.controllers;

import modell.gameboard.GamePhase;
import java.util.EnumMap;
import java.util.Map;

public class GamePhaseController {
  private GamePhase currentPhase = GamePhase.IDLE;
  private final Map<GamePhase, Runnable> phaseActions = new EnumMap<>(GamePhase.class);

  public void registerPhaseAction(GamePhase phase, Runnable action) {
    phaseActions.put(phase, action);
  }

  public void startPhase() {
    Runnable action = phaseActions.get(currentPhase);
    if (action != null) action.run();
  }

  public void nextPhase() {
    switch (currentPhase) {
      case IDLE:         currentPhase = GamePhase.ROLL_DICE;    break;
      case ROLL_DICE:    currentPhase = GamePhase.MOVE_PLAYER;  break;
      case MOVE_PLAYER:  currentPhase = GamePhase.SPECIAL_TILE; break;
      case SPECIAL_TILE: currentPhase = GamePhase.IDLE;         break;
    }
    startPhase();
  }

  public GamePhase getCurrentPhase() { return currentPhase; }
  public void setCurrentPhase(GamePhase phase) { this.currentPhase = phase; }
}
