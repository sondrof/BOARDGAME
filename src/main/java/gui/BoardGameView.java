package gui;

import game.Gameboard;
import game.GameBoardFactory;
import game.GameBoardFactory.BoardType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import players.Player;
import tiles.TileLogic;

import java.util.*;

public class BoardGameView extends Application {

  //TODO vi må dele denne klassen opp i flere klasser så vi kan legge alt senere inn i egene pakker. Denne klassen er basically View og Controller i samme..
  //TODO så etterhvert legger vi alt i pakker så vi tydelig viser M V C prinsippet med pakker som heter det.

  private Gameboard gameboard;
  private TextArea logArea;
  private Text currentPlayerText;
  private Text diceResultText;
  private Stage primaryStage;
  private int currentPlayerIndex = 0;
  private Map<Integer, Button> tileButtons = new HashMap<>();
  private Button startTile;
  private GridPane boardGrid;
  private Canvas arrowCanvas;

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    showGameSelectionMenu();
  }

  private void showGameSelectionMenu() {
    Button gameAButton = new Button("Start Spill A (standard)");
    Button gameBButton = new Button("Start Spill B (custom board)");

    gameAButton.setOnAction(e -> launchGame(BoardType.STANDARD, null));
    gameBButton.setOnAction(e -> launchGame(BoardType.FROM_FILE, "custom_board.json"));

    VBox layout = new VBox(20, gameAButton, gameBButton);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(20));

    Scene scene = new Scene(layout, 1000, 750);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Velg Spillvariant");
    primaryStage.show();
  }

  private void launchGame(BoardType boardType, String fileName) {
    TileLogic tileLogic = GameBoardFactory.createTileLogic(boardType, fileName);
    gameboard = new Gameboard(tileLogic);

    gameboard.getPlayerLogic().addPlayer("Spiller 1");
    gameboard.getPlayerLogic().addPlayer("Spiller 2");

    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));

    boardGrid = new GridPane();
    boardGrid.setGridLinesVisible(true);
    boardGrid.setHgap(2);
    boardGrid.setVgap(2);
    boardGrid.setPadding(new Insets(10));

    int cols = 10;
    int rows = 10;
    int tileNum = 1;

    for (int row = rows - 1; row >= 0; row--) {
      if ((rows - 1 - row) % 2 == 0) {
        for (int col = 0; col < cols; col++) {
          Button tile = new Button(String.valueOf(tileNum));
          tile.setPrefSize(60, 60);
          boardGrid.add(tile, col, row);
          tileButtons.put(tileNum, tile);
          tileNum++;
        }
      } else {
        for (int col = cols - 1; col >= 0; col--) {
          Button tile = new Button(String.valueOf(tileNum));
          tile.setPrefSize(60, 60);
          boardGrid.add(tile, col, row);
          tileButtons.put(tileNum, tile);
          tileNum++;
        }
      }
    }

    startTile = new Button("Start");
    startTile.setPrefSize(60, 60);
    boardGrid.add(startTile, 0, 10);

    StackPane boardPane = new StackPane();
    boardPane.getChildren().add(boardGrid);
    arrowCanvas = new Canvas(600, 660);
    boardPane.getChildren().add(arrowCanvas);

    VBox rightPane = new VBox(10);
    rightPane.setPadding(new Insets(10));
    rightPane.setPrefWidth(180);

    Button backButton = new Button("Tilbake til meny");
    backButton.setMaxWidth(Double.MAX_VALUE);
    backButton.setOnAction(e -> showGameSelectionMenu());

    logArea = new TextArea();
    logArea.setEditable(false);
    logArea.setPrefHeight(150);
    logArea.setWrapText(true);
    logArea.setText("Spill-logg:\n");

    currentPlayerText = new Text("Spiller 1 sin tur");
    diceResultText = new Text("Roll: ");

    Button rollDiceButton = new Button("Roll Dice");
    rollDiceButton.setMaxWidth(Double.MAX_VALUE);
    rollDiceButton.setOnAction(e -> handleDiceRoll());

    HBox diceBox = new HBox(10, rollDiceButton, diceResultText);
    diceBox.setAlignment(Pos.CENTER);

    rightPane.getChildren().addAll(backButton, logArea, currentPlayerText, diceBox);
    rightPane.setAlignment(Pos.TOP_CENTER);

    root.setLeft(boardPane);
    root.setRight(rightPane);

    Scene gameScene = new Scene(root, 1000, 750);
    primaryStage.setScene(gameScene);
    primaryStage.setTitle("Brettspill");
    primaryStage.show();

    Platform.runLater(() -> drawArrows(gameboard.getTileLogic().getSpecialTilesMap()));
    drawPlayers();
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
      Player nextPlayer = gameboard.getPlayerLogic().getPlayerList().get(currentPlayerIndex);
      currentPlayerText.setText(nextPlayer.getPlayerName() + " sin tur");
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
      String symbol = "P" + (i + 1);
      int pos = p.getPlayerPosition();

      if (pos == 0) {
        startTile.setText("Start\n" + symbol);
      } else {
        Button tile = tileButtons.get(pos);
        if (tile != null) {
          tile.setText(tile.getText().split("\\n")[0] + "\n" + symbol);
        }
      }
    }
  }

  private void drawArrows(Map<Integer, Integer> specialTiles) {
    GraphicsContext gc = arrowCanvas.getGraphicsContext2D();
    gc.clearRect(0, 0, arrowCanvas.getWidth(), arrowCanvas.getHeight());
    gc.setLineWidth(2);

    for (Map.Entry<Integer, Integer> entry : specialTiles.entrySet()) {
      int from = entry.getKey();
      int to = from + entry.getValue();

      Button fromBtn = tileButtons.get(from);
      Button toBtn = tileButtons.get(to);

      if (fromBtn != null && toBtn != null) {
        Bounds fromScene = fromBtn.localToScene(fromBtn.getBoundsInLocal());
        Bounds toScene = toBtn.localToScene(toBtn.getBoundsInLocal());
        Bounds canvasScene = arrowCanvas.localToScene(arrowCanvas.getBoundsInLocal());

        double startX = fromScene.getMinX() + fromScene.getWidth() / 2 - canvasScene.getMinX();
        double startY = fromScene.getMinY() + fromScene.getHeight() / 2 - canvasScene.getMinY();
        double endX = toScene.getMinX() + toScene.getWidth() / 2 - canvasScene.getMinX();
        double endY = toScene.getMinY() + toScene.getHeight() / 2 - canvasScene.getMinY();

        gc.setStroke(entry.getValue() > 0 ? Color.BLUE : Color.RED);
        gc.strokeLine(startX, startY, endX, endY);

        // Arrowhead
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowLength = 10;
        double arrowAngle = Math.PI / 6;

        double x1 = endX - arrowLength * Math.cos(angle - arrowAngle);
        double y1 = endY - arrowLength * Math.sin(angle - arrowAngle);
        double x2 = endX - arrowLength * Math.cos(angle + arrowAngle);
        double y2 = endY - arrowLength * Math.sin(angle + arrowAngle);

        gc.strokeLine(endX, endY, x1, y1);
        gc.strokeLine(endX, endY, x2, y2);

        // Sentrert prikk på startpunkt
        gc.setFill(entry.getValue() > 0 ? Color.LIGHTBLUE : Color.PINK);
        gc.fillOval(startX - 3, startY - 3, 6, 6);
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
