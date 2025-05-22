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

public class GameSetupController {
    private final SceneManager sceneManager;
    private final GameSetupModel model;
    private GameSetupSceneView view;
    private LadderBoardType selectedBoardType = LadderBoardType.STANDARD;

    public GameSetupController(SceneManager sceneManager, String gameType) {
        this.sceneManager = sceneManager;
        this.model = new GameSetupModel(gameType);
    }

    public void setView(GameSetupSceneView view) {
        this.view = view;
    }

    public void addPlayer(String name, PlayerToken token) {
        try {
            model.addPlayerSetup(name, token);
            view.refreshPlayerList();
        } catch (IllegalArgumentException e) {
            view.showError("Invalid Player", e.getMessage());
        }
    }

    public void removePlayer(int index) {
        model.removePlayerSetup(index);
        view.refreshPlayerList();
    }

    public void setGameMode(String gameMode) {
        try {
            model.setGameMode(gameMode);
        } catch (IllegalArgumentException e) {
            view.showError("Invalid Game Mode", e.getMessage());
        }
    }

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

    public void returnToMenu() {
        sceneManager.switchTo("startMenu");
    }

    public GameSetupModel getModel() {
        return model;
    }

    public void setBoardType(LadderBoardType boardType) {
        this.selectedBoardType = boardType;
    }

    public LadderBoardType getSelectedBoardType() {
        return selectedBoardType;
    }
}