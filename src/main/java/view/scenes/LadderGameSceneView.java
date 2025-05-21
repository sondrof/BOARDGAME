package view.scenes;

import controller.controllers.LadderGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import view.ui.ResourceLoader;

import static view.ui.UIFactory.button;

public class LadderGameSceneView extends AbstractScene {

  public LadderGameSceneView(LadderGameController controller) {
    super("ladderGame", buildScene(controller));
  }

  private static Scene buildScene(LadderGameController controller) {
    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));


    BackgroundImage bg = new BackgroundImage(
        ResourceLoader.getBackground("ladder_background.png"),
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(100, 100, true, true, true, true)
    );
    root.setBackground(new Background(bg));


    StackPane boardPane = new StackPane(controller.getBoardGrid());
    root.setLeft(boardPane);


    VBox rightPane = new VBox(10);
    rightPane.setPadding(new Insets(10));
    rightPane.setPrefWidth(180);

    Button backButton = button("Tilbake til meny", controller::returnToMenu);
    backButton.setMaxWidth(Double.MAX_VALUE);

    VBox diceBox = new VBox(10,
        controller.getRollDiceButton(),
        controller.getDiceResultText()
    );
    diceBox.setAlignment(Pos.CENTER);

    rightPane.getChildren().addAll(
        backButton,
        controller.getLogArea(),
        controller.getCurrentPlayerText(),
        diceBox
    );
    root.setRight(rightPane);

    Canvas sceneCanvas = controller.getArrowCanvas();
    sceneCanvas.setMouseTransparent(true);
    sceneCanvas.widthProperty().bind(root.widthProperty());
    sceneCanvas.heightProperty().bind(root.heightProperty());

    StackPane overlay = new StackPane(sceneCanvas);
    overlay.setPickOnBounds(false);


    StackPane sceneWrapper = new StackPane(root, overlay);
    return new Scene(sceneWrapper, 1000, 750);
  }

  @Override
  public void onEnter() {
    ResourceLoader.preloadLadderGameAssets();
  }
}
