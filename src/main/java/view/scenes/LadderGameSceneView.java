package view.scenes;

import controller.SceneManager;
import controller.controllers.AbstractGameController;
import controller.controllers.LadderGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.ui.ResourceLoader;
import view.ui.UIFactory;

import static view.ui.UIFactory.button;

public class LadderGameSceneView extends AbstractScene {
  private final AbstractGameController controller;

  public LadderGameSceneView(AbstractGameController controller) {
    super("game", buildScene(controller));
    this.controller = controller;
    initializeUI();
  }

  private static Scene buildScene(AbstractGameController controller) {
    BorderPane root = new BorderPane();
    root.setPrefSize(1000, 750);

    // Add background (same as start menu)
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
    if (!(controller instanceof LadderGameController)) {
      throw new IllegalArgumentException("LadderGameSceneView requires a LadderGameController");
    }
    LadderGameController ladderController = (LadderGameController) controller;

    BorderPane root = (BorderPane) getScene().getRoot();

    // --- Board and Ladders (Left) ---
    StackPane boardStack = new StackPane();
    boardStack.setAlignment(Pos.CENTER);
    boardStack.setPrefSize(700, 700);
    boardStack.setMaxSize(700, 700);
    boardStack.setMinSize(700, 700);
    ladderController.getBoardGrid().setPrefSize(700, 700);
    ladderController.getArrowCanvas().setWidth(700);
    ladderController.getArrowCanvas().setHeight(700);
    boardStack.getChildren().addAll(
            ladderController.getBoardGrid(),
            ladderController.getArrowCanvas()
    );

    // --- Right Side: Log, Controls, Actions ---
    VBox rightBox = new VBox(20);
    rightBox.setAlignment(Pos.TOP_CENTER);
    rightBox.setPadding(new Insets(20, 10, 20, 10));
    rightBox.setPrefWidth(280);
    rightBox.setMaxWidth(280);
    rightBox.setMinWidth(280);

    // Game Log
    TextArea logArea = ladderController.getLogArea();
    logArea.setPrefHeight(350);
    logArea.setPrefWidth(260);
    logArea.setMaxWidth(260);
    logArea.setWrapText(true);

    // Game Controls
    VBox controlsBox = new VBox(10);
    controlsBox.setAlignment(Pos.CENTER);
    Text currentPlayerText = ladderController.getCurrentPlayerText();
    currentPlayerText.setFill(Color.WHITE);
    Text diceResultText = ladderController.getDiceResultText();
    diceResultText.setFill(Color.WHITE);
    controlsBox.getChildren().addAll(
            currentPlayerText,
            diceResultText,
            ladderController.getRollDiceButton()
    );

    // Action Buttons
    HBox actionButtons = new HBox(20);
    actionButtons.setAlignment(Pos.CENTER);
    actionButtons.getChildren().addAll(
            UIFactory.button("Save Game", controller::saveGame),
            UIFactory.button("Load Game", controller::loadGame),
            UIFactory.button("Return to Menu", controller::endGame)
    );

    rightBox.getChildren().addAll(logArea, controlsBox, actionButtons);

    // --- Layout ---
    HBox mainBox = new HBox();
    mainBox.setAlignment(Pos.CENTER);
    mainBox.getChildren().addAll(boardStack, rightBox);
    mainBox.setSpacing(0);
    mainBox.setPadding(new Insets(0));

    root.setCenter(mainBox);
  }

  @Override
  public void onEnter() {
    ResourceLoader.preloadLadderGameAssets();
  }
}
