package controller.controllers;

import controller.SceneManager;
import modell.exceptions.PlayerLoadException;
import modell.players.Player;
import modell.players.PlayerToken;
import modell.players.PlayerFileLoader;
import modell.setup.GameSetupModel;
import modell.gameboard.LadderGameBoardFactory;
import modell.gameboard.LadderBoardType;
import view.scenes.GameSetupSceneView;
import view.scenes.LadderGameSceneView;

import java.util.List;

/**
 * Controller responsible for managing the game setup process.
 * This controller handles player management, game mode selection, and the transition
 * from setup to actual gameplay. It coordinates between the setup model and view,
 * ensuring all game prerequisites are met before starting.
 *
 * @author didrik
 * @version 1.0
 */
public class GameSetupController {
    /** The scene manager responsible for handling scene transitions */
    private final SceneManager sceneManager;

    /** The model containing game setup state and logic */
    private final GameSetupModel model;

    /** The view component for game setup */
    private GameSetupSceneView view;

    /** The currently selected board type for the game */
    private LadderBoardType selectedBoardType = LadderBoardType.STANDARD;

    /**
     * Constructs a new GameSetupController with the specified scene manager and game type.
     *
     * @param sceneManager The scene manager for handling UI transitions
     * @param gameType The type of game being set up
     */
    public GameSetupController(SceneManager sceneManager, String gameType) {
        this.sceneManager = sceneManager;
        this.model = new GameSetupModel(gameType);
    }

    /**
     * Sets the view component for this controller.
     *
     * @param view The GameSetupSceneView instance to use
     */
    public void setView(GameSetupSceneView view) {
        this.view = view;
    }

    /**
     * Adds a new player to the game setup.
     *
     * @param name The name of the player to add
     * @param token The token/color to assign to the player
     * @throws IllegalArgumentException if the player setup is invalid
     */
    public void addPlayer(String name, PlayerToken token) {
        try {
            model.addPlayerSetup(name, token);
            view.refreshPlayerList();
        } catch (IllegalArgumentException e) {
            view.showError("Invalid Player", e.getMessage());
        }
    }

    /**
     * Removes a player from the game setup.
     *
     * @param index The index of the player to remove
     */
    public void removePlayer(int index) {
        model.removePlayerSetup(index);
        view.refreshPlayerList();
    }

    /**
     * Sets the game mode for the current setup.
     *
     * @param gameMode The game mode to set
     * @throws IllegalArgumentException if the game mode is invalid
     */
    public void setGameMode(String gameMode) {
        try {
            model.setGameMode(gameMode);
        } catch (IllegalArgumentException e) {
            view.showError("Invalid Game Mode", e.getMessage());
        }
    }

    /**
     * Initiates the game with the current setup.
     * This method validates the setup, creates players, and transitions to the game scene.
     * It also initializes the appropriate game controller based on the game type and mode.
     *
     * @throws IllegalStateException if the game setup is invalid
     * @throws IllegalArgumentException if there are issues with the setup parameters
     */
    public void startGame() {
        try {
            model.validateGameSetup();
            List<Player> players = model.createPlayers();

            // Create appropriate game controller based on game type and mode
            AbstractGameController gameController = GameControllerFactory.createGameController(
                    model.getGameType(),
                    sceneManager,
                    players,
                    model.getGameMode()
            );

            // Register and switch to the game scene
            sceneManager.registerScene("game", new LadderGameSceneView(gameController));
            sceneManager.switchTo("game");

            // Launch the game with the selected board type
            if (gameController instanceof LadderGameController ladderController) {
                ladderController.launchGame(selectedBoardType, null);
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            view.showError("Cannot Start Game", e.getMessage());
        }
    }

    /**
     * Loads a previously saved game.
     * This method attempts to load saved player data and transitions to the game scene
     * if successful.
     *
     * @throws PlayerLoadException if there are issues loading the saved game
     */
    public void loadGame() {
        try {
            List<Player> players = PlayerFileLoader.loadPlayers();
            if (players.isEmpty()) {
                view.showError("No saved game", "No saved game was found.");
                return;
            }

            // Create game controller with loaded players
            AbstractGameController gameController = GameControllerFactory.createGameController(
                    model.getGameType(),
                    sceneManager,
                    players,
                    "Standard"
            );

            sceneManager.registerScene("game", new LadderGameSceneView(gameController));
            sceneManager.switchTo("game");
        } catch (PlayerLoadException e) {
            view.showError("Load Error", "Failed to load saved game: " + e.getMessage());
        }
    }

    /**
     * Returns to the main menu.
     * This method handles the transition back to the start menu scene.
     */
    public void returnToMenu() {
        sceneManager.switchTo("startMenu");
    }

    /**
     * Gets the game setup model.
     *
     * @return The GameSetupModel instance
     */
    public GameSetupModel getModel() {
        return model;
    }

    /**
     * Sets the board type for the game.
     *
     * @param boardType The LadderBoardType to use
     */
    public void setBoardType(LadderBoardType boardType) {
        this.selectedBoardType = boardType;
    }

    /**
     * Gets the currently selected board type.
     *
     * @return The selected LadderBoardType
     */
    public LadderBoardType getSelectedBoardType() {
        return selectedBoardType;
    }
}