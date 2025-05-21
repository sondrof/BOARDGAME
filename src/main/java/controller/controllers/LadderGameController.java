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
import modell.gameboard.GameBoardFactory;
import modell.gameboard.Gameboard;
import modell.players.Player;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import view.ui.ResourceLoader;
import view.ui.UIRenderer;

import java.util.*;

public class LadderGameController {

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

  public LadderGameController(SceneManager manager) {
    this.manager = manager;

    rollDiceButton.setOnAction(e -> handleDiceRoll());
    rollDiceButton.setMaxWidth(Double.MAX_VALUE);

    logArea.setEditable(false);
    logArea.setWrapText(true);
    logArea.setPrefHeight(150);
  }

  public void launchGame(GameBoardFactory.BoardType boardType, String fileName) {
    GameBoardFactory factory = new GameBoardFactory();
    TileLogic tileLogic = factory.createBoard(boardType, fileName);
    gameboard = new Gameboard(tileLogic);

    gameboard.getPlayerLogic().addPlayer("Spiller 1");
    gameboard.getPlayerLogic().addPlayer("Spiller 2");


    List<String> tilePaths = uiElementController.getTileImagePaths(tileLogic);
    UIRenderer renderer = new UIRenderer();
    renderer.renderTiles(boardGrid, tilePaths, 10, 10, tileNodes);

    startTile = renderer.getStartTile();


    Platform.runLater(() ->
        drawLaddersWithRepeatedSprites(
            ((LadderTileLogic) gameboard.getTileLogic()).getLadderMap()
        )
    );



    // Første visning av spillere
    for (int i = 0; i < gameboard.getPlayerLogic().getPlayerList().size(); i++) {
      int startPos = gameboard.getPlayerLogic().getPlayerList().get(i).getPlayerPosition();
      uiElementController.updatePlayerPosition(i, startPos);
    }
    uiElementController.renderPlayers(tileNodes, startTile);
  }

  private void handleDiceRoll() {
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
      logArea.appendText(currentPlayer.getPlayerName() + " vant spillet!\n");
      currentPlayerText.setText(currentPlayer.getPlayerName() + " har vunnet!");
    } else {
      logArea.appendText(currentPlayer.getPlayerName() + " kastet: " + roll + " og flyttet til: " + newPosition + "\n");
      currentPlayerIndex = (currentPlayerIndex + 1) % gameboard.getPlayerLogic().getPlayerList().size();
      currentPlayerText.setText(gameboard.getPlayerLogic().getPlayerList().get(currentPlayerIndex).getPlayerName() + " sin tur");
    }
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
