package view.scenes;

import controller.controllers.AbstractGameController;
import controller.controllers.Spill2Controller;
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
import view.ui.UIFactory;

import static view.ui.UIFactory.button;

/**
 * Scene view for the Spill2 game interface.
 * Manages the game board display, player controls, and game state visualization for Spill2 mode.
 *
 * <p>This scene provides:
 * <ul>
 *     <li>Interactive placeholder for the game board</li>
 *     <li>Dice rolling mechanics and animations</li>
 *     <li>Game log for tracking moves and events</li>
 *     <li>Player turn management and phase control</li>
 * </ul>
 *
 * @author didrik
 * @version 1.0
 */
public class Spill2SceneView extends AbstractScene {
  private final Spill2Controller controller;

  public Spill2SceneView(AbstractGameController baseController) {
    super("spill2", buildScene());
    if (!(baseController instanceof Spill2Controller)) {
      throw new IllegalArgumentException("Spill2SceneView requires a Spill2Controller");
    }
    this.controller = (Spill2Controller) baseController;
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

    // --- Board and Placeholder (Left) ---
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

    // Start phase machine after UI setup
    controller.getPhaseController().startPhase();
  }

  @Override
  public void onEnter() {
    ResourceLoader.preloadSpill2Assets();
  }
}
