// src/main/java/controller/controllers/PhaseSetupHelper.java
package controller.controllers;

import modell.gameboard.GamePhase;

/**
 * Interface defining the setup and handling of different game phases.
 * This interface provides methods for implementing the logic of each game phase
 * and a default method for configuring phase actions in a GamePhaseController.
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public interface PhaseSetupHelper {
  /**
   * Handles the roll dice phase of the game.
   * This method should implement the logic for rolling dice and determining
   * the number of steps a player can move.
   */
  void rollDicePhase();

  /**
   * Handles the move player phase of the game.
   * This method should implement the logic for moving a player based on
   * the dice roll result.
   */
  void movePlayerPhase();

  /**
   * Handles the special tile phase of the game.
   * This method should implement the logic for handling special tile effects
   * when a player lands on them.
   */
  void specialTilePhase();

  /**
   * Configures the phase actions in a GamePhaseController.
   * This default method registers the phase-specific methods with their
   * corresponding game phases.
   *
   * @param controller The GamePhaseController to configure
   */
  default void configurePhases(GamePhaseController controller) {
    controller.registerPhaseAction(GamePhase.ROLL_DICE,    this::rollDicePhase);
    controller.registerPhaseAction(GamePhase.MOVE_PLAYER,  this::movePlayerPhase);
    controller.registerPhaseAction(GamePhase.SPECIAL_TILE, this::specialTilePhase);
  }
}
