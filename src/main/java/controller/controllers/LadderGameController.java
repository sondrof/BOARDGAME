package controller.controllers;

import controller.SceneManager;
import java.util.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import modell.gameboard.*;
import modell.players.Player;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import view.ui.AnimationRenderer;
import view.ui.GameStandingsDialog;
import view.ui.ResourceLoader;
import view.ui.UiRenderer;


/**
 * Controller for the "Snakes and Ladders" game scene.
 * This controller implements the game logic and UI management for the Snakes and Ladders game.
 * It follows a phase-based approach with the following sequence:
 * IDLE → ROLL_DICE → MOVE_PLAYER → SPECIAL_TILE → IDLE
 *
 * <p></p>
 * The controller handles:
 * - Game state management
 * - Player turns and movement
 * - Dice rolling and animations
 * - Special tile effects
 * - UI updates and rendering
 * - Game save/load functionality
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class LadderGameController extends AbstractGameController implements PhaseSetupHelper {

  /** The scene manager for handling scene transitions. */
  private final SceneManager manager;

  /** The game board model. */
  private Gameboard gameboard;

  /** Controller for managing UI elements and their visual representation. */
  private final UiElementController uiElementController = new UiElementController();

  /** Map of tile numbers to their StackPane representations. */
  private final Map<Integer, StackPane> tileNodes = new HashMap<>();

  /** The main game board grid. */
  private final GridPane boardGrid = new GridPane();

  /** Canvas for rendering ladder/snake arrows. */
  private final Canvas arrowCanvas = new Canvas(600, 660);

  /** Text area for game log. */
  private final TextArea logArea = new TextArea("Spill-logg: ");

  /** Text display for current player. */
  private final Text currentPlayerText = new Text("Spiller 1 sin tur");

  /** Text display for dice results. */
  private final Text diceResultText = new Text("Roll: ");

  /** Button for rolling dice. */
  private final Button rollDiceButton = new Button("Roll Dice");

  /** Image views for dice animation. */
  private final ImageView die1View = new ImageView();
  private final ImageView die2View = new ImageView();

  /** The starting tile of the game. */
  private StackPane startTile;

  /** Index of the current player. */
  private int currentPlayerIndex = 0;

  /** Flag indicating if the game has started. */
  private boolean gameStarted = false;

  /** List of players in order of finish. */
  private List<Player> standings = new ArrayList<>();

  /** The result of the last dice roll. */
  private int lastDiceResult;

  /** Controller for managing game phases. */
  private final GamePhaseController phaseController = new GamePhaseController();

  /**
   * Constructs a new LadderGameController with the specified parameters.
   *
   * @param manager The scene manager for handling UI transitions
   * @param players The list of players participating in the game
   * @param gameMode The mode of the game being played
   */
  public LadderGameController(SceneManager manager, List<Player> players, String gameMode) {
    super(manager, new ArrayList<>(players), gameMode);
    this.manager = manager;

    rollDiceButton.setMaxWidth(Double.MAX_VALUE);
    logArea.setEditable(false);
    logArea.setWrapText(true);
    logArea.setPrefHeight(150);

    die1View.setVisible(false);
    die2View.setVisible(false);
    die1View.setFitWidth(50);
    die1View.setFitHeight(50);
    die2View.setFitWidth(50);
    die2View.setFitHeight(50);

    phaseController.registerPhaseAction(GamePhase.IDLE, this::idlePhase);
    configurePhases(phaseController);
  }

  /**
    * Starts the game.
    * Initializes the game state and begins the game loop.
    *
    * @throws IllegalStateException if the game is already started or if there
    * are fewer than 2 players
    */
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


  /**
   * Saves the current game state.
   * This method is currently a placeholder for future implementation.
   *
   * @throws IllegalStateException if there is no game in progress
   */
  @Override
  public void saveGame() {
    if (!gameStarted) {
      throw new IllegalStateException("No game in progress to save");
    }
  }

  /**
   * Loads a previously saved game.
   * This method is currently a placeholder for future implementation.
   *
   * @throws IllegalStateException if a game is already in progress
   */
  @Override
  public void loadGame() {
    if (gameStarted) {
      throw new IllegalStateException("Cannot load game while another game is in progress");
    }
  }


  /**
   * Gets the player whose turn it currently is.
   *
   * @return The current player
   * @throws IllegalStateException if the game hasn't started
   */
  @Override
  public Player getCurrentPlayer() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return players.get(currentPlayerIndex);
  }

  /**
   * Advances to the next player's turn.
   *
   * @throws IllegalStateException if the game hasn't started
   */
  @Override
  public void nextTurn() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  /**
   * Launches the game with the specified board type and optional file name.
   * Initializes the game board, renders the UI, and starts the game.
   *
   * @param boardType The type of board to use
   * @param fileName Optional file name for custom board configuration
   */
  public void launchGame(LadderBoardType boardType, String fileName) {
    LadderGameBoardFactory factory = new LadderGameBoardFactory();
    TileLogic logic = factory.createBoard(boardType, fileName);
    gameboard = new Gameboard(logic);
    players.forEach(p -> gameboard.getPlayerLogic().addPlayer(p.getName()));

    UiRenderer renderer = new UiRenderer();
    List<String> paths = uiElementController.getTileImagePaths(logic);
    renderer.renderTiles(boardGrid, paths, 10, 10, tileNodes);
    startTile = renderer.getStartTile();
    Platform.runLater(() ->
            renderer.renderLadders(arrowCanvas,
                    ((LadderTileLogic) logic).getLadderMap(),
                    tileNodes
            )
    );
    for (int i = 0; i < players.size(); i++) {
      uiElementController.updatePlayerPosition(
              i,
              gameboard.getPlayerLogic().getPlayerList().get(i).getPlayerPosition()
      );
    }
    uiElementController.renderPlayers(tileNodes, startTile);

    startGame();
    phaseController.startPhase();
  }



  /**
   * Animates the movement of a player token from one tile to another.
   *
   * @param fromTileIndex The starting tile index
   * @param toTileIndex The destination tile index
   * @param onFinished Callback to execute when animation completes
   */
  private void animateMovement(int fromTileIndex, int toTileIndex, Runnable onFinished) {
    StackPane from = tileNodes.get(fromTileIndex);
    StackPane to = tileNodes.get(toTileIndex);
    if (from == null || to == null) {
      onFinished.run();
      return;
    }


    ImageView token = new ImageView(
            ResourceLoader.getPlayerIcon("player" + currentPlayerIndex + ".png")
    );
    token.setFitWidth(24);
    token.setFitHeight(24);
    from.getChildren().add(token);


    Point2D p0 = from.localToScene(0, 0);
    Point2D p1 = to.localToScene(0, 0);
    double dx = p1.getX() - p0.getX();
    double dy = p1.getY() - p0.getY();


    TranslateTransition tt = new TranslateTransition(Duration.millis(400), token);
    tt.setByX(dx);
    tt.setByY(dy);
    tt.setOnFinished(e -> {
      from.getChildren().remove(token);
      onFinished.run();
    });
    tt.play();
  }


  /**
   * Handles the idle phase of the game.
   * Enables the roll dice button and sets up the next phase transition.
   */
  public void idlePhase() {
    rollDiceButton.setDisable(false);
    rollDiceButton.setOnAction(e -> {
      rollDiceButton.setDisable(true);
      phaseController.nextPhase(); // → ROLL_DICE
    });
  }

  /**
   * Handles the dice rolling phase.
   * Animates the dice roll and updates the UI with the results.
   */
  @Override
  public void rollDicePhase() {

    die1View.setVisible(true);
    die2View.setVisible(true);

    AnimationRenderer.playDiceRoll(die1View, die2View, (a, b) -> {

      die1View.setImage(ResourceLoader.getDiceImage("die_" + a + ".png"));
      die2View.setImage(ResourceLoader.getDiceImage("die_" + b + ".png"));

      int total = a + b;
      lastDiceResult = total;

      diceResultText.setText("Roll: " + a + " + " + b + " = " + total);
      logArea.appendText(getCurrentPlayer().getName()
              + " Threw: " + a + " + " + b + " = " + total + "\n");

      phaseController.nextPhase();
    });
  }

  @Override
  public void movePlayerPhase() {
    Player p = getCurrentPlayer();
    int oldPos = p.getPlayerPosition();
    int newPos = oldPos + lastDiceResult;
    p.setPlayerPosition(newPos);
    if (newPos >= 100) {
      endGameWithStandings(p);
      return;
    }

    uiElementController.renderPlayers(tileNodes, startTile);

    animateMovement(oldPos, newPos, () -> {
      uiElementController.updatePlayerPosition(currentPlayerIndex, newPos);
      uiElementController.renderPlayers(tileNodes, startTile);
      logArea.appendText(p.getName() + "Moved to: " + newPos + "\n");
      phaseController.nextPhase();
    });
  }

  @Override
  public void specialTilePhase() {
    Player p = getCurrentPlayer();
    int landed = p.getPlayerPosition();


    gameboard.getGameboardLogic().handlePlayerLanding(p, gameboard.getTileLogic());
    int target = p.getPlayerPosition();

    if (target >= 100) {
      endGameWithStandings(p);
      return;
    }

    if (target == landed) {
      nextTurn();
      currentPlayerText.setText(getCurrentPlayer().getName() + "'s turn");
      phaseController.nextPhase();
      return;
    }

    uiElementController.renderPlayers(tileNodes, startTile);

    animateMovement(landed, target, () -> {
      uiElementController.updatePlayerPosition(currentPlayerIndex, target);
      uiElementController.renderPlayers(tileNodes, startTile);
      logArea.appendText(p.getName() + " Got effect on: " + target + "\n");


      nextTurn();
      currentPlayerText.setText(getCurrentPlayer().getName() + "'s turn");
      phaseController.nextPhase();
    });
  }

  private void endGameWithStandings(Player winner) {
    rollDiceButton.setDisable(true);
    List<Player> result = new ArrayList<>();
    result.add(winner);
    for (Player p : players) {
      if (!p.equals(winner)) {
        result.add(p);
      }
    }
    standings = result;
    Platform.runLater(() ->
            GameStandingsDialog.show(standings, () -> manager.switchTo("startMenu"))
    );
  }

  @Override
  public void returnToMenu() {
    manager.switchTo("startMenu");
  }

  public GamePhaseController getPhaseController() {
    return phaseController;
  }

  // === Getters for SceneView ===
  public GridPane getBoardGrid()             {
    return boardGrid;
  }

  public Canvas getArrowCanvas()             {
    return arrowCanvas;
  }

  public TextArea getLogArea()               {
    return logArea;
  }

  public Text getCurrentPlayerText()         {
    return currentPlayerText;
  }

  public Text getDiceResultText()            {
    return diceResultText;
  }

  public Button getRollDiceButton()          {
    return rollDiceButton;
  }

  public ImageView getDie1View()             {
    return die1View;
  }

  public ImageView getDie2View()             {
    return die2View;
  }

}
