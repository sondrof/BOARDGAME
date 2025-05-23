package controller.controllers;

import controller.SceneManager;
import java.util.List;
import modell.players.Player;


/**
 * Factory class responsible for creating game controller instances.
 * This factory implements the Factory Method pattern to create appropriate
 * game controllers based on the specified game type and mode.
 *
 * @author didrik
 * @version 1.0
 */
public class GameControllerFactory {
  /**
   * Creates a new game controller instance based on the specified parameters.
   * This method serves as the factory method for creating different types of game controllers.
   *
   * @param gameType The type of game to create a controller for (e.g., "ladder")
   * @param sceneManager The scene manager for handling UI transitions
   * @param players The list of players participating in the game
   * @param gameMode The mode of the game to be played
   * @return A new instance of AbstractGameController appropriate for the game type
   * @throws IllegalArgumentException if the specified game type is not supported
   */
  public static AbstractGameController createGameController(
      String gameType,
      SceneManager sceneManager,
      List<Player> players,
      String gameMode) {

    switch (gameType.toLowerCase()) {
      case "ladder":
        return new LadderGameController(sceneManager, players, gameMode);
      default:
        throw new IllegalArgumentException("Unsupported game type: " + gameType);
    }
  }

  public GameControllerFactory() {
  }
}