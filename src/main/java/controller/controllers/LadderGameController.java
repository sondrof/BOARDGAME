package controller.controllers;

import controller.SceneManager;
import modell.players.Player;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import modell.gameboard.Gameboard;
import modell.gameboard.GameBoardFactory;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

public class LadderGameController {
  private final SceneManager manager;
  private Gameboard gameboard;

  private final GridPane boardGrid = new GridPane();
  private final Map<Integer, Button> tileButtons = new HashMap<>();
  private final Canvas arrowCanvas = new Canvas(600, 660);
  private final TextArea logArea = new TextArea("Spill-logg:\n");
  private final Text currentPlayerText = new Text("Spiller 1 sin tur");
  private final Text diceResultText = new Text("Roll: ");
  private final Button rollDiceButton = new Button("Roll Dice");

  private Button startTile;
  private int currentPlayerIndex = 0;
  //TODO Vi må fikse UIRenderer og UIElementController for å håndtere hva som skal kontrolleres i scenene for enklere kode.
  //TODO Finne en løsning som skal lages i UIElementsController for å replace metoder som hvordan vi tegner brette osv.
  //Ikke sikkert på hvor disse metodene skulle vært siden controller skal egentlig kun snakke med view og modell for å endre de
  //Så vi trenger et passende sted, finner ut av det
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

    buildBoardGrid();

    Platform.runLater(() -> drawArrows(((LadderTileLogic) gameboard.getTileLogic()).getLadderMap()));
    drawPlayers();
  }

  private void buildBoardGrid() {
    boardGrid.setGridLinesVisible(true);
    boardGrid.setHgap(2);
    boardGrid.setVgap(2);
    boardGrid.setPadding(new Insets(10));

    int cols = 10, rows = 10, tileNum = 1;
    for (int row = rows - 1; row >= 0; row--) {
      if ((rows - 1 - row) % 2 == 0) {
        for (int col = 0; col < cols; col++) {
          addTile(tileNum++, col, row);
        }
      } else {
        for (int col = cols - 1; col >= 0; col--) {
          addTile(tileNum++, col, row);
        }
      }
    }

    startTile = new Button("Start");
    startTile.setPrefSize(60, 60);
    boardGrid.add(startTile, 0, 10);
  }

  private void addTile(int tileNum, int col, int row) {
    Button tile = new Button(String.valueOf(tileNum));
    tile.setPrefSize(60, 60);
    boardGrid.add(tile, col, row);
    tileButtons.put(tileNum, tile);
  }

  private void handleDiceRoll() {
    Player currentPlayer = gameboard.getPlayerLogic().getPlayerList().get(currentPlayerIndex);
    int roll = gameboard.getPlayerLogic().getDiceSet().roll();
    int oldPosition = currentPlayer.getPlayerPosition();

    currentPlayer.setPlayerPosition(oldPosition + roll);
    gameboard.getGameboardLogic().handlePlayerLanding(currentPlayer, gameboard.getTileLogic());

    int newPosition = currentPlayer.getPlayerPosition();
    diceResultText.setText("Roll: " + roll);

    if (newPosition >= 100) {
      logArea.appendText(currentPlayer.getPlayerName() + " vant spillet!\n");
      currentPlayerText.setText(currentPlayer.getPlayerName() + " har vunnet!");
    } else {
      logArea.appendText(currentPlayer.getPlayerName() + " kastet: " + roll + " og flyttet til: " + newPosition + "\n");
      currentPlayerIndex = (currentPlayerIndex + 1) % gameboard.getPlayerLogic().getPlayerList().size();
      currentPlayerText.setText(gameboard.getPlayerLogic().getPlayerList().get(currentPlayerIndex).getPlayerName() + " sin tur");
    }

    drawPlayers();
  }

  private void drawPlayers() {
    for (Button b : tileButtons.values()) {
      b.setText(b.getText().split("\\n")[0]);
    }
    startTile.setText("Start");

    List<Player> players = gameboard.getPlayerLogic().getPlayerList();
    for (int i = 0; i < players.size(); i++) {
      Player p = players.get(i);
      int pos = p.getPlayerPosition();
      String symbol = "P" + (i + 1);
      if (pos == 0) {
        startTile.setText("Start\n" + symbol);
      } else {
        Button tile = tileButtons.get(pos);
        if (tile != null) tile.setText(tile.getText().split("\\n")[0] + "\n" + symbol);
      }
    }
  }

  private void drawArrows(Map<Integer, Integer> specialTiles) {
    GraphicsContext gc = arrowCanvas.getGraphicsContext2D();
    gc.clearRect(0, 0, arrowCanvas.getWidth(), arrowCanvas.getHeight());
    gc.setLineWidth(2);

    specialTiles.forEach((from, offset) -> {
      int to = from + offset;
      Button fromBtn = tileButtons.get(from);
      Button toBtn = tileButtons.get(to);

      if (fromBtn == null || toBtn == null) return;

      Bounds fromBounds = fromBtn.localToScene(fromBtn.getBoundsInLocal());
      Bounds toBounds = toBtn.localToScene(toBtn.getBoundsInLocal());
      Bounds canvasBounds = arrowCanvas.localToScene(arrowCanvas.getBoundsInLocal());

      double startX = fromBounds.getMinX() + fromBounds.getWidth() / 2 - canvasBounds.getMinX();
      double startY = fromBounds.getMinY() + fromBounds.getHeight() / 2 - canvasBounds.getMinY();
      double endX = toBounds.getMinX() + toBounds.getWidth() / 2 - canvasBounds.getMinX();
      double endY = toBounds.getMinY() + toBounds.getHeight() / 2 - canvasBounds.getMinY();

      gc.setStroke(offset > 0 ? Color.BLUE : Color.RED);
      gc.strokeLine(startX, startY, endX, endY);
    });
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
}
