package controller.controllers;

import controller.SceneManager;
import modell.players.Player;
import java.util.List;

public class GameControllerFactory {
    public static AbstractGameController createGameController(
            String gameType,
            SceneManager sceneManager,
            List<Player> players,
            String gameMode) {

        switch (gameType.toLowerCase()) {
            case "ladder":
                return new LadderGameController(sceneManager, players, gameMode);
            // Add more game types here as they are implemented
            default:
                throw new IllegalArgumentException("Unsupported game type: " + gameType);
        }
    }
}