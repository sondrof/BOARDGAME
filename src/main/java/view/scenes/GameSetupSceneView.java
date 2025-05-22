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
import modell.gameboard.LadderBoardType;
import view.ui.ResourceLoader;
import view.ui.UIFactory;

public class GameSetupSceneView extends AbstractScene {
    private final GameSetupController controller;
    private final VBox playerSetupBox;
    private final ComboBox<LadderBoardType> boardTypeComboBox;
    private final Label playerCountLabel;
    private Button addPlayerButton;

    public GameSetupSceneView(SceneManager manager, String gameType) {
        super("gameSetup", buildScene(manager, gameType));
        this.controller = new GameSetupController(manager, gameType);
        this.controller.setView(this);
        this.playerSetupBox = new VBox(10);
        this.boardTypeComboBox = new ComboBox<>();
        this.playerCountLabel = new Label("Players: 0/5");

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

        // Board Type Selection
        HBox boardTypeBox = new HBox(10);
        Label boardTypeLabel = new Label("Board Type:");
        boardTypeLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        boardTypeComboBox.getItems().addAll(LadderBoardType.values());
        boardTypeComboBox.setValue(LadderBoardType.STANDARD);
        boardTypeComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(LadderBoardType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.name().replace("_", " ").toLowerCase());
            }
        });
        boardTypeComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(LadderBoardType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.name().replace("_", " ").toLowerCase());
            }
        });
        boardTypeComboBox.setOnAction(e -> controller.setBoardType(boardTypeComboBox.getValue()));
        boardTypeBox.getChildren().addAll(boardTypeLabel, boardTypeComboBox);
        boardTypeBox.setAlignment(Pos.CENTER);

        // Player Setup Section
        Label playerSetupLabel = new Label("Player Setup");
        playerSetupLabel.setFont(new Font("Arial", 18));
        playerSetupLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // Player count label
        playerCountLabel.setFont(new Font("Arial", 14));
        playerCountLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // Add Player Button
        addPlayerButton = UIFactory.button("Add Player", this::showAddPlayerDialog);
        addPlayerButton.setDisable(false);

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
                boardTypeBox,
                playerSetupLabel,
                playerCountLabel,
                playerSetupBox,
                addPlayerButton,
                actionButtons
        );
        refreshPlayerList();
    }

    private void showAddPlayerDialog() {
        if (controller.getModel().getPlayerSetups().size() >= 5) {
            addPlayerButton.setDisable(true);
            return;
        }
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Player");
        VBox dialogVBox = new VBox(10);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(10));
        TextField nameField = new TextField();
        nameField.setPromptText("Player Name");
        nameField.setMaxWidth(200);
        ComboBox<PlayerToken> tokenComboBox = new ComboBox<>();
        tokenComboBox.getItems().addAll(PlayerToken.values());
        tokenComboBox.setMaxWidth(150);
        tokenComboBox.setPromptText("Select Token");
        dialogVBox.getChildren().addAll(new Label("Name:"), nameField, new Label("Token:"), tokenComboBox);
        dialog.getDialogPane().setContent(dialogVBox);
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String name = nameField.getText();
                PlayerToken token = tokenComboBox.getValue();
                if (name == null || name.isBlank() || token == null) {
                    showError("Invalid Input", "Please enter a name and select a token.");
                    return null;
                }
                controller.addPlayer(name, token);
                return null;
            }
            return null;
        });
        dialog.showAndWait();
    }

    public void refreshPlayerList() {
        playerSetupBox.getChildren().clear();
        int playerCount = controller.getModel().getPlayerSetups().size();
        for (int i = 0; i < playerCount; i++) {
            final int index = i;
            modell.setup.GameSetupModel.PlayerSetup setup = controller.getModel().getPlayerSetups().get(i);
            HBox playerBox = new HBox(10);
            playerBox.setAlignment(Pos.CENTER);
            Label nameLabel = new Label(setup.getName());
            nameLabel.setFont(new Font("Arial", 14));
            nameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
            Label tokenLabel = new Label(setup.getToken().toString());
            tokenLabel.setFont(new Font("Arial", 14));
            tokenLabel.setTextFill(javafx.scene.paint.Color.WHITE);
            Button removeButton = UIFactory.button("Remove", () -> controller.removePlayer(index));
            playerBox.getChildren().addAll(nameLabel, tokenLabel, removeButton);
            playerSetupBox.getChildren().add(playerBox);
        }
        playerCountLabel.setText("Players: " + playerCount + "/5");
        if (addPlayerButton != null) {
            addPlayerButton.setDisable(playerCount >= 5);
        }
    }

    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}