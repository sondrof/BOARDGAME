package controller.controllers;

import java.util.EnumMap;
import java.util.Map;
import modell.gameboard.GamePhase;

/**
 * Controller responsible for managing the game phases and their transitions.
 * This controller implements a state machine pattern to handle the different
 * phases of the game (IDLE, ROLL_DICE, MOVE_PLAYER, SPECIAL_TILE) and their
 * associated actions.
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class GamePhaseController {
  /** The current phase of the game. */
  private GamePhase currentPhase = GamePhase.IDLE;

  /** Map of game phases to their associated actions. */
  private final Map<GamePhase, Runnable> phaseActions = new EnumMap<>(GamePhase.class);

  /**
   * Registers an action to be executed when a specific game phase is active.
   *
   * @param phase The game phase to associate with the action
   * @param action The runnable action to execute during the phase
   */
  public void registerPhaseAction(GamePhase phase, Runnable action) {
    phaseActions.put(phase, action);
  }

  /**
   * Starts the current game phase by executing its associated action.
   * If no action is registered for the current phase, nothing happens.
   */
  public void startPhase() {
    Runnable action = phaseActions.get(currentPhase);
    if (action != null) {
      action.run();
    }
  }

  /**
   * Advances to the next game phase in the sequence and starts it.
   * The phase sequence is: IDLE -> ROLL_DICE -> MOVE_PLAYER -> SPECIAL_TILE -> IDLE
   */
  public void nextPhase() {
    switch (currentPhase) {
      case IDLE:
        currentPhase = GamePhase.ROLL_DICE;
        break;

      case ROLL_DICE:
        currentPhase = GamePhase.MOVE_PLAYER;
        break;

      case MOVE_PLAYER:
        currentPhase = GamePhase.SPECIAL_TILE;
        break;

      case SPECIAL_TILE:
        currentPhase = GamePhase.IDLE;
        break;

      default:
    }
    startPhase();
  }
}
