package view;

import javafx.application.Application;
import javafx.stage.Stage;
import controller.SceneManager;
import view.scenes.StartMenuSceneView;

public class BoardGameView extends Application {
    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the scene manager
        sceneManager = new SceneManager(primaryStage);

        // Set up the primary stage
        primaryStage.setTitle("Board Game");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(750);

        // Register the start menu scene
        sceneManager.registerScene("startMenu", new StartMenuSceneView(sceneManager));

        // Switch to the start menu scene
        sceneManager.switchTo("startMenu");

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}