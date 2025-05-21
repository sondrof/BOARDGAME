package controller.controllers;

import controller.SceneManager;
import modell.players.Player;
import java.util.List;

public abstract class AbstractGameController {
    protected final SceneManager sceneManager;
    protected final List<Player> players;
    protected final String gameMode;

    public AbstractGameController(SceneManager sceneManager, List<Player> players, String gameMode) {
        this.sceneManager = sceneManager;
        this.players = players;
        this.gameMode = gameMode;
    }

    public abstract void startGame();
    public abstract void endGame();
    public abstract void saveGame();
    public abstract void loadGame();
    public abstract void movePlayer(Player player, int steps);
    public abstract boolean isGameOver();
    public abstract Player getCurrentPlayer();
    public abstract void nextTurn();
}