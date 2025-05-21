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
import javafx.scene.text.Text;
import view.ui.ResourceLoader;
import view.ui.UIFactory;

import static view.ui.UIFactory.button;

public class LadderGameSceneView extends AbstractScene {
  private final AbstractGameController controller;

  public LadderGameSceneView(AbstractGameController controller) {
    super("game", buildScene());
    this.controller = controller;
    initializeUI();
  }

  private static Scene buildScene() {
    VBox root = new VBox(20);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));

    // Add background
    BackgroundImage bg = new BackgroundImage(
            ResourceLoader.getBackground("game_background.png"),
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

    VBox root = (VBox) getScene().getRoot();

    // Game Board
    HBox gameBoardBox = new HBox(20);
    gameBoardBox.setAlignment(Pos.CENTER);
    gameBoardBox.getChildren().addAll(
            ladderController.getBoardGrid(),
            ladderController.getArrowCanvas()
    );

    // Game Controls
    VBox controlsBox = new VBox(10);
    controlsBox.setAlignment(Pos.CENTER);
    controlsBox.getChildren().addAll(
            ladderController.getCurrentPlayerText(),
            ladderController.getDiceResultText(),
            ladderController.getRollDiceButton()
    );

    // Game Log
    VBox logBox = new VBox(10);
    logBox.setAlignment(Pos.CENTER);
    logBox.getChildren().add(ladderController.getLogArea());

    // Action Buttons
    HBox actionButtons = new HBox(20);
    actionButtons.setAlignment(Pos.CENTER);
    actionButtons.getChildren().addAll(
            UIFactory.button("Save Game", controller::saveGame),
            UIFactory.button("Load Game", controller::loadGame),
            UIFactory.button("Return to Menu", controller::endGame)
    );

    // Add all components to the root
    root.getChildren().addAll(
            gameBoardBox,
            controlsBox,
            logBox,
            actionButtons
    );
  }

  @Override
  public void onEnter() {
    ResourceLoader.preloadLadderGameAssets();
  }
}
