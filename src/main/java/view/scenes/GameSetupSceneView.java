package view.scenes;

import controller.SceneManager;
import controller.controllers.GameSetupController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import modell.players.PlayerToken;
import view.ui.ResourceLoader;
import view.ui.UIFactory;

public class GameSetupSceneView extends AbstractScene {
    private final GameSetupController controller;
    private final VBox playerSetupBox;
    private final ComboBox<String> gameModeComboBox;
    private final Label playerCountLabel;

    public GameSetupSceneView(SceneManager manager, String gameType) {
        super("gameSetup", buildScene(manager, gameType));
        this.controller = new GameSetupController(manager, gameType);
        this.controller.setView(this);
        this.playerSetupBox = new VBox(10);
        this.gameModeComboBox = new ComboBox<>();
        this.playerCountLabel = new Label("Players: 0/4");

        initializeUI();
    }

    private static Scene buildScene(SceneManager manager, String gameType) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Add background
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
        VBox root = (VBox) getScene().getRoot();

        // Title
        Label titleLabel = new Label("Game Setup");
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // Game Mode Selection
        HBox gameModeBox = new HBox(10);
        Label gameModeLabel = new Label("Game Mode:");
        gameModeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        gameModeComboBox.getItems().addAll("Standard", "Fun Board 1", "Fun Board 2", "Fun Board 3");
        gameModeComboBox.setValue("Standard");
        gameModeComboBox.setOnAction(e -> controller.setGameMode(gameModeComboBox.getValue()));
        gameModeBox.getChildren().addAll(gameModeLabel, gameModeComboBox);
        gameModeBox.setAlignment(Pos.CENTER);

        // Player Setup Section
        Label playerSetupLabel = new Label("Player Setup");
        playerSetupLabel.setFont(new Font("Arial", 18));
        playerSetupLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // Player count label
        playerCountLabel.setFont(new Font("Arial", 14));
        playerCountLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // Add initial player setup fields
        addPlayerSetupFields();

        // Add Player Button
        Button addPlayerButton = UIFactory.button("Add Player", this::addPlayerSetupFields);
        addPlayerButton.setDisable(true); // Will be enabled when player count is less than max

        // Action Buttons
        HBox actionButtons = new HBox(20);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.getChildren().addAll(
                UIFactory.button("Back to Menu", controller::returnToMenu),
                UIFactory.button("Load Previous Game", controller::loadGame),
                UIFactory.button("Start Game", controller::startGame)
        );

        // Add all components to the root
        root.getChildren().addAll(
                titleLabel,
                gameModeBox,
                playerSetupLabel,
                playerCountLabel,
                playerSetupBox,
                addPlayerButton,
                actionButtons
        );
    }

    private void addPlayerSetupFields() {
        HBox playerBox = new HBox(10);
        playerBox.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setPromptText("Player Name");
        nameField.setMaxWidth(200);

        ComboBox<PlayerToken> tokenComboBox = new ComboBox<>();
        tokenComboBox.getItems().addAll(PlayerToken.values());
        tokenComboBox.setMaxWidth(150);
        tokenComboBox.setButtonCell(new ListCell<PlayerToken>() {
            @Override
            protected void updateItem(PlayerToken item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Select Token");
                } else {
                    setText(item.toString());
                }
            }
        });
        tokenComboBox.setCellFactory(lv -> new ListCell<PlayerToken>() {
            @Override
            protected void updateItem(PlayerToken item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });

        Button removeButton = UIFactory.button("Remove", () -> {
            int index = playerSetupBox.getChildren().indexOf(playerBox);
            if (index >= 0) {
                controller.removePlayer(index);
            }
        });

        playerBox.getChildren().addAll(nameField, tokenComboBox, removeButton);
        playerSetupBox.getChildren().add(playerBox);

        // Add the player when both fields are filled
        nameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty() && tokenComboBox.getValue() != null) {
                controller.addPlayer(newVal, tokenComboBox.getValue());
            }
        });

        tokenComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !nameField.getText().isEmpty()) {
                controller.addPlayer(nameField.getText(), newVal);
            }
        });
    }

    public void refreshPlayerList() {
        playerSetupBox.getChildren().clear();
        for (modell.setup.GameSetupModel.PlayerSetup setup : controller.getModel().getPlayerSetups()) {
            HBox playerBox = new HBox(10);
            playerBox.setAlignment(Pos.CENTER);

            TextField nameField = new TextField(setup.getName());
            nameField.setPromptText("Player Name");
            nameField.setMaxWidth(200);

            ComboBox<PlayerToken> tokenComboBox = new ComboBox<>();
            tokenComboBox.getItems().addAll(PlayerToken.values());
            tokenComboBox.setValue(setup.getToken());
            tokenComboBox.setMaxWidth(150);
            tokenComboBox.setButtonCell(new ListCell<PlayerToken>() {
                @Override
                protected void updateItem(PlayerToken item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select Token");
                    } else {
                        setText(item.toString());
                    }
                }
            });
            tokenComboBox.setCellFactory(lv -> new ListCell<PlayerToken>() {
                @Override
                protected void updateItem(PlayerToken item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            });

            Button removeButton = UIFactory.button("Remove", () -> {
                int index = playerSetupBox.getChildren().indexOf(playerBox);
                if (index >= 0) {
                    controller.removePlayer(index);
                }
            });

            playerBox.getChildren().addAll(nameField, tokenComboBox, removeButton);
            playerSetupBox.getChildren().add(playerBox);
        }

        // Update player count label
        int playerCount = controller.getModel().getPlayerSetups().size();
        playerCountLabel.setText("Players: " + playerCount + "/4");

        // Enable/disable add player button based on player count
        Button addButton = (Button) ((VBox) getScene().getRoot()).getChildren().get(5);
        addButton.setDisable(playerCount >= 4);
    }

    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}