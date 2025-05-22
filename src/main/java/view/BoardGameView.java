package view;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.SceneManager;
import view.scenes.StartMenuSceneView;

/**
 * Main application class for the board game.
 * Extends JavaFX Application to provide the primary window and scene management.
 *
 * <p>This class handles:
 * <ul>
 *     <li>Application initialization and startup</li>
 *     <li>Primary stage configuration</li>
 *     <li>Scene manager setup</li>
 *     <li>Initial scene registration and display</li>
 * </ul>
 *
 * @author didrik
 * @version 1.0
 */
public class BoardGameView extends Application {
    /** The scene manager responsible for handling scene transitions */
    private SceneManager sceneManager;

    /**
     * Initializes and starts the application.
     * Sets up the primary stage, scene manager, and initial scene.
     *
     * @param primaryStage The primary stage provided by JavaFX
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize the scene manager
        sceneManager = new SceneManager(primaryStage);

        // Set up the primary stage
        primaryStage.setTitle("Board Game");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(750);
        primaryStage.setResizable(false);

        // Register the start menu scene
        sceneManager.registerScene("startMenu", new StartMenuSceneView(sceneManager));

        // Switch to the start menu scene
        sceneManager.switchTo("startMenu");

        // Show the stage
        primaryStage.show();
    }

    /**
     * Main entry point for the application.
     * Launches the JavaFX application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}