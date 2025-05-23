package controller.controllers;


import controller.SceneManager;
import java.util.List;
import modell.players.Player;


/**
 * Abstract base class for all game controllers in the application.
 * This class defines the common interface and shared functionality for
 * different game implementations.
 * It provides a template for game flow management, player handling, and game state persistence.
 *
 * @author didrik
 * @version 1.0
 */
public abstract class AbstractGameController {
  /** The scene manager responsible for handling scene transitions. */
  protected final SceneManager sceneManager;

  /** List of players participating in the current game. */
  protected final List<Player> players;

  /** The current game mode being played. */
  protected final String gameMode;

  /**
   * Constructs a new AbstractGameController with the specified parameters.
   *
   * @param sceneManager The scene manager for handling UI transitions
   * @param players The list of players participating in the game
   * @param gameMode The mode of the game being played
   */
  protected AbstractGameController(SceneManager sceneManager,
                                   List<Player> players, String gameMode) {
    this.sceneManager = sceneManager;
    this.players = players;
    this.gameMode = gameMode;
  }

  /**
   * Initializes and starts the game.
   * This method should handle all necessary setup and begin the game loop.
   */
  public abstract void startGame();

  /**
   * Saves the current game state to persistent storage.
   * This method should handle serialization of the game state.
   */
  public abstract void saveGame();

  /**
   * Loads a previously saved game state from persistent storage.
   * This method should handle deserialization and state restoration.
   */
  public abstract void loadGame();

  /**
   * Gets the player whose turn it currently is.
   *
   * @return The current player
   */
  public abstract Player getCurrentPlayer();

  /**
   * Advances the game to the next player's turn.
   * This method should handle turn transition logic and any necessary state updates.
   */
  public abstract void nextTurn();

  /**
   * Returns to the main menu.
   * This method should handle cleanup and scene transition to the menu.
   */
  public abstract void returnToMenu();

}