package modell.gameboard;

import modell.dice.DiceSet;
import modell.players.Player;
import modell.players.PlayerLogic;
import modell.tiles.LadderTileLogic;
import modell.tiles.TileLogic;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the main game board for a ladder game.
 * This class manages the game state, player interactions, and game flow.
 *
 * <p>The game board consists of:
 * <ul>
 *     <li>A 100-tile board with ladders and snakes</li>
 *     <li>2-6 players who take turns rolling dice and moving</li>
 *     <li>Special tiles that can move players up (ladders) or down (snakes)</li>
 * </ul>
 *
 * <p>The game flow:
 * <ol>
 *     <li>Initialize the board with ladders and snakes</li>
 *     <li>Set up players (2-6 players)</li>
 *     <li>Players take turns rolling dice and moving</li>
 *     <li>Special tiles are activated when players land on them</li>
 *     <li>First player to reach tile 100 wins</li>
 * </ol>
 *
 * @author didrik
 * @version 1.0
 */
public class Gameboard {
    /** Factory for creating game board components */
    private final LadderGameBoardFactory factory;

    /** Logic for handling special tiles (ladders and snakes) */
    private LadderTileLogic tileLogic;

    /** Logic for managing player actions and state */
    private PlayerLogic playerLogic;

    /** Logic for managing the game board state */
    private GameboardLogic gameboardLogic;

    /** Total number of tiles on the board */
    private static final int BOARD_SIZE = 100;

    /** Current round number */
    private int roundNumber = 1;

    /** Scanner for reading user input */
    private Scanner scanner;

    /**
     * Creates a new game board with standard configuration.
     * Initializes the board with default ladders and snakes.
     */
    public Gameboard() {
        this.factory = new LadderGameBoardFactory();
        this.tileLogic = (LadderTileLogic) factory.createBoard(LadderBoardType.STANDARD, null);
        playerLogic = new PlayerLogic(new DiceSet(2));
        gameboardLogic = new GameboardLogic();
        scanner = new Scanner(System.in);
    }

    /**
     * Creates a new game board with custom tile logic.
     *
     * @param tileLogic the custom tile logic to use
     * @throws IllegalArgumentException if the provided tile logic is not a LadderTileLogic
     */
    public Gameboard(TileLogic tileLogic) {
        if (!(tileLogic instanceof LadderTileLogic)) {
            throw new IllegalArgumentException("Gameboard requires LadderTileLogic");
        }
        this.factory = new LadderGameBoardFactory();
        this.tileLogic = (LadderTileLogic) tileLogic;
        this.playerLogic = new PlayerLogic(new DiceSet(2));
        this.gameboardLogic = new GameboardLogic();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initializes the game board and sets up players.
     * This method:
     * <ol>
     *     <li>Sets up the ladders and snakes on the board</li>
     *     <li>Prompts for the number of players (2-6)</li>
     *     <li>Gets player names and creates player objects</li>
     *     <li>Displays the list of players</li>
     * </ol>
     */
    public void initBoard() {
        setUpLadders();

        int numberOfPlayers = getValidPlayerCount();

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter name for player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            playerLogic.addPlayer(playerName);
        }

        System.out.println("The following players are playing the game:");
        for (Player player : playerLogic.getPlayerList()) {
            System.out.println("Name: " + player.getName());
        }
    }

    /**
     * Prompts for and validates the number of players.
     * Ensures the number is between 2 and 6.
     *
     * @return the validated number of players
     */
    private int getValidPlayerCount() {
        while (true) {
            try {
                System.out.println("How many players? (2-6): ");
                int count = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (count >= 2 && count <= 6) {
                    return count;
                }
                System.out.println("Please enter a number between 2 and 6");
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    /**
     * Sets up the initial ladder and snake positions on the board.
     * Creates a predefined configuration of ladders and snakes.
     */
    private void setUpLadders() {
        Map<Integer, Integer> upLadders = Map.of(
                4, 10,
                8, 22,
                28, 48,
                40, 36,
                80, 19
        );

        Map<Integer, Integer> downLadders = Map.of(
                17, -10,
                54, -20,
                62, -43,
                64, -4,
                87, -63,
                93, -20,
                95, -20,
                99, -21
        );

        upLadders.forEach(tileLogic::addLadder);
        downLadders.forEach(tileLogic::addLadder);
    }

    /**
     * Executes one round of the game.
     * Each player takes their turn in sequence:
     * <ol>
     *     <li>Rolls the dice</li>
     *     <li>Moves their piece</li>
     *     <li>Handles any special tile effects</li>
     *     <li>Checks for win condition</li>
     * </ol>
     */
    public void playRound() {
        System.out.println("Round number " + roundNumber);

        for (int i = 0; i < playerLogic.getPlayerList().size(); i++) {
            Player player = playerLogic.getPlayerList().get(i);
            playerLogic.movePlayer(i);

            gameboardLogic.handlePlayerLanding(player, tileLogic);

            if (gameboardLogic.checkWinCondition(player)) {
                System.out.println(player.getName() + " has won the game!");
                System.exit(0);
            }
        }

        playerLogic.printPlayerStatus();
        roundNumber++;
    }

    /**
     * Closes the scanner used for user input.
     * Should be called when the game is finished.
     */
    public void closeScanner() {
        scanner.close();
    }

    /**
     * Gets the player logic component.
     *
     * @return the PlayerLogic instance
     */
    public PlayerLogic getPlayerLogic() {
        return playerLogic;
    }

    /**
     * Gets the game board logic component.
     *
     * @return the GameboardLogic instance
     */
    public GameboardLogic getGameboardLogic() {
        return gameboardLogic;
    }

    /**
     * Gets the tile logic component.
     *
     * @return the LadderTileLogic instance
     */
    public LadderTileLogic getTileLogic() {
        return tileLogic;
    }
}