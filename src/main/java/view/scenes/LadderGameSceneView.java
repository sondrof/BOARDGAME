package view.scenes;

import controller.controllers.LadderGameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import static view.ui.UIFactory.button;

public class LadderGameSceneView extends AbstractScene {

  public LadderGameSceneView(LadderGameController controller) {
    super("ladderGame", buildScene(controller));
  }

  private static Scene buildScene(LadderGameController controller) {
    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));


    StackPane boardPane = new StackPane(
        controller.getBoardGrid(),
        controller.getArrowCanvas()
    );
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


    return new Scene(root, 1000, 750);
  }
}
