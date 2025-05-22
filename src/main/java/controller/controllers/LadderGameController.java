package controller.controllers;

import controller.SceneManager;
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
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;
import view.ui.AnimationRenderer;
import view.ui.ResourceLoader;
import view.ui.UIRenderer;
import view.ui.GameStandingsDialog;
import modell.players.Player;

import java.util.*;

/**
 * Controller for "Stiger og slanger"-scenen, nå delt opp i faser:
 * IDLE → ROLL_DICE → MOVE_PLAYER → SPECIAL_TILE → IDLE.
 * Save/load-metodene er uberørt fra originalen.
 */
public class LadderGameController extends AbstractGameController implements PhaseSetupHelper {

  private final SceneManager manager;
  private Gameboard gameboard;

  // UI-elementer (samme som før)
  private final UIElementController uiElementController = new UIElementController();
  private final Map<Integer, StackPane> tileNodes     = new HashMap<>();
  private final GridPane boardGrid                    = new GridPane();
  private final Canvas arrowCanvas                    = new Canvas(600, 660);
  private final TextArea logArea                      = new TextArea("Spill-logg: ");
  private final Text currentPlayerText                = new Text("Spiller 1 sin tur");
  private final Text diceResultText                   = new Text("Roll: ");
  private final Button rollDiceButton                 = new Button("Roll Dice");
  // Nye ImageView-felter for dice-animasjon
  private final javafx.scene.image.ImageView die1View = new javafx.scene.image.ImageView();
  private final javafx.scene.image.ImageView die2View = new javafx.scene.image.ImageView();

  // Spill-tilstand
  private StackPane startTile;
  private int currentPlayerIndex = 0;
  private boolean gameStarted    = false;
  private boolean gameEnded      = false;
  private List<Player> standings  = new ArrayList<>();
  private int lastDiceResult;

  // Fasestyring
  private final GamePhaseController phaseController = new GamePhaseController();

  public LadderGameController(SceneManager manager, List<Player> players, String gameMode) {
    super(manager, new ArrayList<>(players), gameMode);
    this.manager = manager;

    // --- UI-oppsett (identisk med original) ---
    rollDiceButton.setMaxWidth(Double.MAX_VALUE);
    logArea.setEditable(false);
    logArea.setWrapText(true);
    logArea.setPrefHeight(150);

    // Skjul dice ImageView inntil IDLE-fasen
    die1View.setVisible(false);
    die2View.setVisible(false);
    die1View.setFitWidth(50);
    die1View.setFitHeight(50);
    die2View.setFitWidth(50);
    die2View.setFitHeight(50);

    // --- Bind alle faser (IDLE + roll/move/special) til handler-metodene ---
    phaseController.registerPhaseAction(GamePhase.IDLE, this::idlePhase);
    configurePhases(phaseController);
  }

  @Override
  public void startGame() {
    if (gameStarted) throw new IllegalStateException("Game is already started");
    if (players.size() < 2) throw new IllegalStateException("Need at least 2 players to start the game");
    gameStarted = true;
  }

  @Override
  public void endGame() {
    if (!gameStarted) throw new IllegalStateException("Game has not been started");
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

  /**
   * Initialiserer modell + view, så starter vi i IDLE-fasen.
   */
  public void launchGame(LadderBoardType boardType, String fileName) {
    LadderGameBoardFactory factory = new LadderGameBoardFactory();
    TileLogic logic = factory.createBoard(boardType, fileName);
    gameboard = new Gameboard(logic);
    players.forEach(p -> gameboard.getPlayerLogic().addPlayer(p.getName()));

    UIRenderer renderer = new UIRenderer();
    List<String> paths = uiElementController.getTileImagePaths(logic);
    renderer.renderTiles(boardGrid, paths, 10, 10, tileNodes);
    startTile = renderer.getStartTile();
    Platform.runLater(() ->
        renderer.renderLadders(arrowCanvas,
            ((LadderTileLogic)logic).getLadderMap(),
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

  private void animateMovement(int fromTileIndex, int toTileIndex, Runnable onFinished) {
    StackPane from = tileNodes.get(fromTileIndex);
    StackPane to   = tileNodes.get(toTileIndex);
    if (from == null || to == null) {
      onFinished.run();
      return;
    }

    // Opprett token med riktig spiller‐ikon
    ImageView token = new ImageView(
        ResourceLoader.getPlayerIcon("player" + currentPlayerIndex + ".png")
    );
    token.setFitWidth(24);
    token.setFitHeight(24);
    from.getChildren().add(token);

    // Beregn scene-koordinater
    Point2D p0 = from.localToScene(0,0);
    Point2D p1 = to.localToScene(0,0);
    double dx = p1.getX() - p0.getX();
    double dy = p1.getY() - p0.getY();

    // Spill animasjon
    TranslateTransition tt = new TranslateTransition(Duration.millis(400), token);
    tt.setByX(dx);
    tt.setByY(dy);
    tt.setOnFinished(e -> {
      from.getChildren().remove(token);
      onFinished.run();
    });
    tt.play();
  }

  // === PhaseSetupHelper-implementasjon ===

  public void idlePhase() {
    rollDiceButton.setDisable(false);
    rollDiceButton.setOnAction(e -> {
      rollDiceButton.setDisable(true);
      phaseController.nextPhase(); // → ROLL_DICE
    });
  }

  @Override
  public void rollDicePhase() {
    // Vis ImageView-ene
    die1View.setVisible(true);
    die2View.setVisible(true);

    // Kjør animasjon; når den er ferdig får vi 'a' og 'b'
    AnimationRenderer.playDiceRoll(die1View, die2View, (a, b) -> {
      // --- Her setter vi de endelige ansiktene ---
      die1View.setImage(ResourceLoader.getDiceImage("die_" + a + ".png"));
      die2View.setImage(ResourceLoader.getDiceImage("die_" + b + ".png"));

      // Summen som før
      int total = a + b;
      lastDiceResult = total;

      // Oppdater UI-tekst og logg
      diceResultText.setText("Roll: " + a + " + " + b + " = " + total);
      logArea.appendText(getCurrentPlayer().getName()
          + " kastet: " + a + " + " + b + " = " + total + "\n");

      // Gå videre i flyten
      phaseController.nextPhase();
    });
  }


  @Override
  public void movePlayerPhase() {
    Player p = getCurrentPlayer();
    int oldPos = p.getPlayerPosition();
    int newPos = oldPos + lastDiceResult;

    // Modell‐oppdatering
    p.setPlayerPosition(newPos);

    // Hvis noen vinner rett etter å ha flyttet (land på eller over 100):
    if (newPos >= 100) {
      endGameWithStandings(p);
      return;
    }

    // Fjern alle eksisterende token‐noder lagt ut av PlayerRenderer
    uiElementController.renderPlayers(tileNodes, startTile);

    // Spillers animasjon fra oldPos → newPos
    animateMovement(oldPos, newPos, () -> {
      // Tegn "offisiell" ny posisjon
      uiElementController.updatePlayerPosition(currentPlayerIndex, newPos);
      uiElementController.renderPlayers(tileNodes, startTile);
      logArea.appendText(p.getName() + " flyttet til: " + newPos + "\n");
      phaseController.nextPhase();  // → SPECIAL_TILE
    });
  }

  @Override
  public void specialTilePhase() {
    Player p = getCurrentPlayer();
    int landed = p.getPlayerPosition();

    // Utfør modell‐effekter (stige/slange etc.)
    gameboard.getGameboardLogic().handlePlayerLanding(p, gameboard.getTileLogic());
    int target = p.getPlayerPosition();

    // Sjekk om vi har vunnet via en stige som tar deg over 100
    if (target >= 100) {
      endGameWithStandings(p);
      return;
    }

    // Hvis ingen stige/slange, gå rett til neste spiller
    if (target == landed) {
      nextTurn();
      currentPlayerText.setText(getCurrentPlayer().getName() + " sin tur");
      phaseController.nextPhase();  // → IDLE
      return;
    }

    // Ellers har vi en stige/slange fra `landed` til `target`
    // Fjern gamle tokens
    uiElementController.renderPlayers(tileNodes, startTile);

    animateMovement(landed, target, () -> {
      // Når animasjonen er ferdig, oppdater UI‐posisjon
      uiElementController.updatePlayerPosition(currentPlayerIndex, target);
      uiElementController.renderPlayers(tileNodes, startTile);
      logArea.appendText(p.getName() + " fikk effekt, nå på: " + target + "\n");

      // Neste spiller
      nextTurn();
      currentPlayerText.setText(getCurrentPlayer().getName() + " sin tur");
      phaseController.nextPhase();  // → IDLE
    });
  }




  private void endGameWithStandings(Player winner) {
    gameEnded = true;
    rollDiceButton.setDisable(true);
    List<Player> result = new ArrayList<>();
    result.add(winner);
    for (Player p : players) {
      if (!p.equals(winner)) result.add(p);
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

  public GamePhaseController getPhaseController() { return phaseController; }

  // === Getters for SceneView ===
  public GridPane getBoardGrid()             { return boardGrid; }
  public Canvas getArrowCanvas()             { return arrowCanvas; }
  public TextArea getLogArea()               { return logArea; }
  public Text getCurrentPlayerText()         { return currentPlayerText; }
  public Text getDiceResultText()            { return diceResultText; }
  public Button getRollDiceButton()          { return rollDiceButton; }
  public ImageView getDie1View()             { return die1View; }
  public ImageView getDie2View()             { return die2View; }
  public Map<Integer, StackPane> getTileNodes() { return tileNodes; }
}
