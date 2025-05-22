package controller.controllers;

import controller.SceneManager;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import view.ui.ResourceLoader;
import view.ui.UIRenderer;
import modell.gameboard.GameResultCalculator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;
import view.ui.GameStandingsDialog;
import modell.gameboard.Gameboard;
import modell.players.Player;
import modell.gameboard.LadderGameBoardFactory;
import modell.gameboard.LadderBoardType;

import java.util.*;

public class LadderGameController extends AbstractGameController {

  private final SceneManager manager;
  private Gameboard gameboard;

  private final UIElementController uiElementController = new UIElementController();
  private final Map<Integer, StackPane> tileNodes = new HashMap<>();
  private final Map<Integer, Button> tileButtons = new HashMap<>(); // kun brukt i drawArrows()

  private final GridPane boardGrid = new GridPane();
  private final Canvas arrowCanvas = new Canvas(600, 660);
  private final TextArea logArea = new TextArea("Spill-logg:\n");
  private final Text currentPlayerText = new Text("Spiller 1 sin tur");
  private final Text diceResultText = new Text("Roll: ");
  private final Button rollDiceButton = new Button("Roll Dice");

  private StackPane startTile;
  private int currentPlayerIndex = 0;
  private boolean gameStarted;
  private boolean gameEnded = false;
  private List<Player> standings = new ArrayList<>();

  public LadderGameController(SceneManager manager, List<Player> players, String gameMode) {
    super(manager, new ArrayList<>(players), gameMode);
    this.manager = manager;
    this.currentPlayerIndex = 0;
    this.gameStarted = false;

    rollDiceButton.setOnAction(e -> handleDiceRoll());
    rollDiceButton.setMaxWidth(Double.MAX_VALUE);

    logArea.setEditable(false);
    logArea.setWrapText(true);
    logArea.setPrefHeight(150);
  }

  @Override
  public void startGame() {
    if (gameStarted) {
      throw new IllegalStateException("Game is already started");
    }
    if (players.size() < 2) {
      throw new IllegalStateException("Need at least 2 players to start the game");
    }
    gameStarted = true;
  }

  @Override
  public void endGame() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    gameStarted = false;
  }

  @Override
  public void saveGame() {
    if (!gameStarted) {
      throw new IllegalStateException("No game in progress to save");
    }
    // TODO: Implement game saving logic
  }

  @Override
  public void loadGame() {
    if (gameStarted) {
      throw new IllegalStateException("Cannot load game while another game is in progress");
    }
    // TODO: Implement game loading logic
  }

  @Override
  public void movePlayer(Player player, int steps) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    if (player != getCurrentPlayer()) {
      throw new IllegalStateException("Not this player's turn");
    }
    player.setPlayerPosition(player.getPlayerPosition() + steps);
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      return false;
    }
    // TODO: Implement win condition check
    return false;
  }

  @Override
  public Player getCurrentPlayer() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return players.get(currentPlayerIndex);
  }

  @Override
  public void nextTurn() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  public void launchGame(LadderBoardType boardType, String fileName) {
    LadderGameBoardFactory factory = new LadderGameBoardFactory();
    TileLogic tileLogic = factory.createBoard(boardType, fileName);
    gameboard = new Gameboard(tileLogic);

    // Add players from the controller's player list
    for (Player player : players) {
      gameboard.getPlayerLogic().addPlayer(player.getName());
    }

    List<String> tilePaths = uiElementController.getTileImagePaths(tileLogic);
    UIRenderer renderer = new UIRenderer();
    renderer.renderTiles(boardGrid, tilePaths, 10, 10, tileNodes);

    startTile = renderer.getStartTile();

    Platform.runLater(() ->
            drawLaddersWithRepeatedSprites(
                    ((LadderTileLogic) gameboard.getTileLogic()).getLadderMap()
            )
    );

    // Initial player positions
    for (int i = 0; i < gameboard.getPlayerLogic().getPlayerList().size(); i++) {
      int startPos = gameboard.getPlayerLogic().getPlayerList().get(i).getPlayerPosition();
      uiElementController.updatePlayerPosition(i, startPos);
    }
    uiElementController.renderPlayers(tileNodes, startTile);

    // Start the game
    startGame();
  }

  private void handleDiceRoll() {
    if (gameEnded) return;
    Player currentPlayer = gameboard.getPlayerLogic().getPlayerList().get(currentPlayerIndex);
    int roll = gameboard.getPlayerLogic().getDiceSet().roll();
    int oldPosition = currentPlayer.getPlayerPosition();

    currentPlayer.setPlayerPosition(oldPosition + roll);
    gameboard.getGameboardLogic().handlePlayerLanding(currentPlayer, gameboard.getTileLogic());

    int newPosition = currentPlayer.getPlayerPosition();
    diceResultText.setText("Roll: " + roll);

    // Bruk index som spiller-ID
    int playerId = currentPlayerIndex;
    uiElementController.updatePlayerPosition(playerId, newPosition);
    uiElementController.renderPlayers(tileNodes, startTile);

    if (newPosition >= 100) {
      logArea.appendText(currentPlayer.getName() + " vant spillet!\n");
      currentPlayerText.setText(currentPlayer.getName() + " har vunnet!");
      endGameWithStandings(currentPlayer);
    } else {
      logArea.appendText(currentPlayer.getName() + " kastet: " + roll + " og flyttet til: " + newPosition + "\n");
      currentPlayerIndex = (currentPlayerIndex + 1) % gameboard.getPlayerLogic().getPlayerList().size();
      currentPlayerText.setText(gameboard.getPlayerLogic().getPlayerList().get(currentPlayerIndex).getName() + " sin tur");
    }
  }

  private void endGameWithStandings(Player winner) {
    gameEnded = true;
    rollDiceButton.setDisable(true);
    // Use GameResultCalculator for standings
    standings = GameResultCalculator.calculateStandings(gameboard.getPlayerLogic().getPlayerList(), winner);
    GameStandingsDialog.show(standings, () -> manager.switchTo("startMenu"));
  }

  public List<Player> getStandings() {
    return standings;
  }

  public boolean isGameEnded() {
    return gameEnded;
  }

  private void drawLaddersWithRepeatedSprites(Map<Integer, Integer> ladderMap) {
    // Delegér all tegning til View-laget
    UIRenderer renderer = new UIRenderer();
    renderer.renderLadders(
            arrowCanvas,
            ladderMap,
            tileNodes
    );
  }

  public void returnToMenu() {
    manager.switchTo("startMenu");
  }

  public GridPane getBoardGrid() {
    return boardGrid;
  }

  public Canvas getArrowCanvas() {
    return arrowCanvas;
  }

  public TextArea getLogArea() {
    return logArea;
  }

  public Text getCurrentPlayerText() {
    return currentPlayerText;
  }

  public Text getDiceResultText() {
    return diceResultText;
  }

  public Button getRollDiceButton() {
    return rollDiceButton;
  }

  public Map<Integer, StackPane> getTileNodes() {
    return tileNodes;
  }
}
