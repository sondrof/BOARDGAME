package gui;

import game.Gameboard;
import game.GameBoardFactory;
import game.GameBoardFactory.BoardType;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tiles.TileLogic;

/**
 * Represents the graphical user interface (GUI) for the board game.
 * It takes actions from the GameBoardFactory and initializes the game logic.
 *
 * @author Sondre Odberg
 *
 * @version 0.0.1
 */
public class BoardGameView extends Application {

  /**
   * Starts the JavaFX application and displays the game selection menu.
   *
   * @param primaryStage The main stage of the application.
   */
  @Override
  public void start(Stage primaryStage) {
    showGameSelectionMenu(primaryStage);
  }

  /**
   * Displays a simple GUI menu that allows the user to choose between
   * different variants of the board game.
   *
   * @param stage The stage to display the menu on.
   */
  private void showGameSelectionMenu(Stage stage) {
    Button gameAButton = new Button("Start Spill A (standard)");
    Button gameBButton = new Button("Start Spill B (custom board)");

    gameAButton.setOnAction(e -> launchGame(stage, BoardType.STANDARD, null));
    gameBButton.setOnAction(e -> launchGame(stage, BoardType.FROM_FILE, "custom_board.json"));

    VBox layout = new VBox(20, gameAButton, gameBButton);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout, 300, 200);
    stage.setScene(scene);
    stage.setTitle("Velg Spillvariant");
    stage.show();
  }

  /**
   * Launches the selected board game using the specified input.
   *
   * @param stage     The stage to be closed when game starts.
   * @param boardType The type of board to load (standard or from file).
   * @param fileName  The filename of the board configuration (only for custom boards).
   */
  private void launchGame(Stage stage, BoardType boardType, String fileName) {
    TileLogic tileLogic = GameBoardFactory.createTileLogic(boardType, fileName);

    // Initializes the game logic with the selected tile configuration
    Gameboard game = new Gameboard(tileLogic);
    game.initBoard();

    // Currently closes the GUI and enters a CLI loop for the game logic
    stage.close();
    while (true) {
      game.playRound();
    }
  }

  /**
   * Launches the application.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
