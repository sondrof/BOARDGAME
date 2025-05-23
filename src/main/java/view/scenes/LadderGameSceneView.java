package view.scenes;

import controller.controllers.LadderGameController;
import controller.controllers.AbstractGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.ui.ResourceLoader;
import static view.ui.UiFactory.button;

/**
 * Scene view for the main ladder game interface.
 * Manages the game board display, player controls, and game state visualization.
 *
 * <p>This scene provides:
 * <ul>
 *     <li>Interactive game board with ladders and snakes</li>
 *     <li>Dice rolling mechanics and animations</li>
 *     <li>Game log for tracking moves and events</li>
 *     <li>Save/load game functionality</li>
 *     <li>Player turn management and phase control</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class LadderGameSceneView extends AbstractScene {
  private final LadderGameController controller;

  public LadderGameSceneView(AbstractGameController baseController) {
    super("game", buildScene());
    if (!(baseController instanceof LadderGameController)) {
      throw new IllegalArgumentException("LadderGameSceneView requires a LadderGameController");
    }
    this.controller = (LadderGameController) baseController;
    initializeUI();
  }

  private static Scene buildScene() {
    BorderPane root = new BorderPane();
    root.setPrefSize(1000, 750);

    BackgroundImage bg = new BackgroundImage(
        ResourceLoader.getBackground("start_background.png"),
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(100, 100, true, true, true, true)
    );
    root.setBackground(new Background(bg));

    return new Scene(root, 1000, 750);
  }

  private void initializeUI() {
    BorderPane root = (BorderPane) getScene().getRoot();

    // --- Board and Ladders (Left) ---
    StackPane boardStack = new StackPane();
    boardStack.setAlignment(Pos.CENTER);
    boardStack.setPrefSize(700, 700);
    Canvas arrow = controller.getArrowCanvas();
    arrow.setWidth(700);
    arrow.setHeight(700);
    controller.getBoardGrid().setPrefSize(700, 700);

    boardStack.getChildren().addAll(
        controller.getBoardGrid(),
        arrow
    );

    // --- Right Side: Log, Controls, Actions ---
    VBox rightBox = new VBox(20);
    rightBox.setAlignment(Pos.TOP_CENTER);
    rightBox.setPadding(new Insets(20));
    rightBox.setPrefWidth(280);

    // Game Log
    TextArea logArea = controller.getLogArea();
    logArea.setPrefSize(260, 350);
    logArea.setWrapText(true);

    // Game Controls (phases + dice animation)
    VBox controlsBox = new VBox(10);
    controlsBox.setAlignment(Pos.CENTER);

    Text currentPlayerText = controller.getCurrentPlayerText();
    currentPlayerText.setFill(Color.WHITE);
    Text diceResultText = controller.getDiceResultText();
    diceResultText.setFill(Color.WHITE);

    ImageView die1 = controller.getDie1View();
    ImageView die2 = controller.getDie2View();
    Button rollBtn = controller.getRollDiceButton();

    controlsBox.getChildren().addAll(
        currentPlayerText,
        diceResultText,
        die1,
        die2,
        rollBtn
    );

    // Action Buttons
    HBox actionButtons = new HBox(20);
    actionButtons.setAlignment(Pos.CENTER);
    actionButtons.getChildren().addAll(
        button("Save Game", controller::saveGame),
        button("Load Game", controller::loadGame),
        button("Return to Menu", controller::returnToMenu)
    );

    rightBox.getChildren().addAll(logArea, controlsBox, actionButtons);

    // --- Assemble ---
    HBox mainBox = new HBox();
    mainBox.setAlignment(Pos.CENTER);
    mainBox.getChildren().addAll(boardStack, rightBox);

    root.setCenter(mainBox);

    // Start fase-maskinen nå som UI er satt opp
    controller.getPhaseController().startPhase();
  }

  @Override
  public void onEnter() {
    ResourceLoader.preloadLadderGameAssets();
  }
}
