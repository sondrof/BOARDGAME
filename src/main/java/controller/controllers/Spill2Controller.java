package controller.controllers;

import controller.SceneManager;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import modell.players.Player;
import modell.gameboard.GamePhase;
import view.ui.AnimationRenderer;
import view.ui.GameStandingsDialog;
import view.ui.ResourceLoader;
import controller.controllers.UIElementController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the "Spill2" game scene.
 * Implements the game logic and UI management for the Spill2 mode,
 * following a phase-based approach similar to LadderGameController.
 *
 * <p>This controller handles:
 * <ul>
 *     <li>Game state management</li>
 *     <li>Player turns and movement</li>
 *     <li>Dice rolling and animations</li>
 *     <li>UI updates and rendering</li>
 *     <li>Scene transitions</li>
 * </ul>
 *
 * @author didrik
 * @version 1.0
 */
public class Spill2Controller extends AbstractGameController implements PhaseSetupHelper {

  /** The scene manager for handling UI transitions */
  private final SceneManager manager;

  /** Controller for managing UI elements and rendering */
  private final UIElementController uiElementController = new UIElementController();

  /** Map of tile numbers to their StackPane representations (if applicable) */
  private final Map<Integer, StackPane> tileNodes = new HashMap<>();

  /** The main game board grid */
  private final GridPane boardGrid = new GridPane();

  /** Canvas for rendering arrows or custom animations */
  private final Canvas arrowCanvas = new Canvas(600, 600);

  /** Text area for game log */
  private final TextArea logArea = new TextArea("Spill2 log: ");

  /** Text display for current player */
  private final Text currentPlayerText = new Text("Player 1's turn");

  /** Text display for dice results */
  private final Text diceResultText = new Text("Roll: ");

  /** Button for rolling dice */
  private final Button rollDiceButton = new Button("Roll Dice");

  /** Image views for dice animation */
  private final ImageView die1View = new ImageView();
  private final ImageView die2View = new ImageView();

  // Game state
  private boolean gameStarted = false;
  private boolean gameEnded = false;
  private int currentPlayerIndex = 0;
  private int lastDiceResult;
  private List<Player> standings = new ArrayList<>();

  /** Controller for managing game phases */
  private final GamePhaseController phaseController = new GamePhaseController();

  /**
   * Constructs a new Spill2Controller.
   *
   * @param manager The scene manager for handling UI transitions
   * @param players The list of players participating in the game
   * @param gameMode The mode of the game being played
   */
  public Spill2Controller(SceneManager manager, List<Player> players, String gameMode) {
    super(manager, new ArrayList<>(players), gameMode);
    this.manager = manager;

    // UI initial configuration
    rollDiceButton.setMaxWidth(Double.MAX_VALUE);
    logArea.setEditable(false);
    logArea.setWrapText(true);

    die1View.setVisible(false);
    die2View.setVisible(false);
    die1View.setFitWidth(50);
    die1View.setFitHeight(50);
    die2View.setFitWidth(50);
    die2View.setFitHeight(50);

    // Register phase actions
    phaseController.registerPhaseAction(GamePhase.IDLE, this::idlePhase);
    phaseController.registerPhaseAction(GamePhase.ROLL_DICE, this::rollDicePhase);
    phaseController.registerPhaseAction(GamePhase.MOVE_PLAYER, this::movePlayerPhase);
    phaseController.registerPhaseAction(GamePhase.SPECIAL_TILE, this::specialTilePhase);
    configurePhases(phaseController);
  }

  @Override
  public void startGame() {
    if (gameStarted) throw new IllegalStateException("Game is already started");
    if (players.size() < 2) throw new IllegalStateException("Need at least 2 players to start");
    gameStarted = true;
    phaseController.startPhase();
  }

  @Override
  public void endGame() {
    if (!gameStarted) throw new IllegalStateException("Game has not been started");
    gameEnded = true;
  }

  @Override
  public void saveGame() {
    if (!gameStarted) throw new IllegalStateException("No game in progress to save");
    // TODO: Implement save logic
  }

  @Override
  public void loadGame() {
    if (gameStarted) throw new IllegalStateException("Cannot load while game is in progress");
    // TODO: Implement load logic
  }

  @Override
  public void movePlayer(Player player, int steps) {
    if (!gameStarted) throw new IllegalStateException("Game has not been started");
    if (player != getCurrentPlayer()) throw new IllegalStateException("Not this player's turn");
    player.setPlayerPosition(player.getPlayerPosition() + steps);
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) return false;
    return false;
  }

  @Override
  public Player getCurrentPlayer() {
    if (!gameStarted) throw new IllegalStateException("Game has not been started");
    return players.get(currentPlayerIndex);
  }

  @Override
  public void nextTurn() {
    if (!gameStarted) throw new IllegalStateException("Game has not been started");
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  @Override
  public void returnToMenu() {
    manager.switchTo("startMenu");
  }

  public GamePhaseController getPhaseController() { return phaseController; }

  // === Getters for SceneView ===
  public GridPane getBoardGrid() { return boardGrid; }
  public Canvas getArrowCanvas() { return arrowCanvas; }
  public TextArea getLogArea() { return logArea; }
  public Text getCurrentPlayerText() { return currentPlayerText; }
  public Text getDiceResultText() { return diceResultText; }
  public Button getRollDiceButton() { return rollDiceButton; }
  public ImageView getDie1View() { return die1View; }
  public ImageView getDie2View() { return die2View; }
  public Map<Integer, StackPane> getTileNodes() { return tileNodes; }

  // Placeholder phase methods
  public void idlePhase() { /* TODO */ }
  public void rollDicePhase() { /* TODO */ }
  public void movePlayerPhase() { /* TODO */ }
  public void specialTilePhase() { /* TODO */ }
}