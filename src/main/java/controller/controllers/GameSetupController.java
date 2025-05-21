package controller.controllers;

import controller.SceneManager;
import modell.exceptions.PlayerLoadException;
import modell.players.Player;
import modell.players.PlayerFileLoader;
import modell.setup.GameSetupModel;
import view.scenes.GameSetupSceneView;
import view.scenes.LadderGameSceneView;

import java.util.List;

public class GameSetupController {
    private final SceneManager sceneManager;
    private final GameSetupModel model;
    private GameSetupSceneView view;

    public GameSetupController(SceneManager sceneManager, String gameType) {
        this.sceneManager = sceneManager;
        this.model = new GameSetupModel(gameType);
    }

    public void setView(GameSetupSceneView view) {
        this.view = view;
    }

    public void addPlayer(String name, String token) {
        model.addPlayerSetup(name, token);
        view.refreshPlayerList();
    }

    public void removePlayer(int index) {
        model.removePlayerSetup(index);
        view.refreshPlayerList();
    }

    public void setGameMode(String gameMode) {
        model.setGameMode(gameMode);
    }

    public void startGame() {
        List<Player> players = model.createPlayers();
        if (players.isEmpty()) {
            view.showError("No players added", "Please add at least one player to start the game.");
            return;
        }

        String gameMode = model.getGameMode();
        // TODO: Create appropriate game controller based on gameType and gameMode
        LadderGameController gameController = new LadderGameController(sceneManager, players, gameMode);
        sceneManager.registerScene("game", new LadderGameSceneView(gameController));
        sceneManager.switchTo("game");
    }

    public void loadGame() {
        try {
            List<Player> players = PlayerFileLoader.loadPlayers();
            if (players.isEmpty()) {
                view.showError("No saved game", "No saved game was found.");
                return;
            }

            // TODO: Load game state and create appropriate game controller
            LadderGameController gameController = new LadderGameController(sceneManager, players, "Standard");
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
}