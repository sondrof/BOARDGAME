package game;

import dice.DiceSet;
import players.Player;
import players.PlayerLogic;
import tiles.TileLogic;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Gameboard {
    private TileLogic tileLogic = new TileLogic();
    private PlayerLogic playerLogic;
    private GameboardLogic gameboardLogic;
    private static final int BOARD_SIZE = 100;
    private int roundNumber = 1;
    private Scanner scanner;

    public Gameboard() {
        playerLogic = new PlayerLogic(new DiceSet(2));
        gameboardLogic = new GameboardLogic();
        scanner = new Scanner(System.in);
    }

    public void initBoard() {
        tileLogic.generateTiles(BOARD_SIZE);


        setUpLadders();

        int numberOfPlayers = getValidPlayerCount();


        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter name for player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            playerLogic.addPlayer(playerName);
        }

        System.out.println("The following players are playing the game:");
        for (Player player : playerLogic.getPlayerList()) {
            System.out.println("Name: " + player.getPlayerName());
        }
    }

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

    private void setUpLadders(){
        Map<Integer, Integer>  upLadders = Map.of(
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

        upLadders.forEach(tileLogic::setSpecialTile);
        downLadders.forEach(tileLogic::setSpecialTile);
    }

    public void playRound() {
        System.out.println("Round number " + roundNumber);

        for (int i = 0; i < playerLogic.getPlayerList().size(); i++) {
            Player player = playerLogic.getPlayerList().get(i);
            playerLogic.movePlayer(i);

            gameboardLogic.handlePlayerLanding(player, tileLogic);


            if (gameboardLogic.checkWinCondition(player)) {
                System.out.println(player.getPlayerName() + " has won the game!");
                System.exit(0);
            }
        }

        playerLogic.printPlayerStatus();
        roundNumber++;
    }

    public void closeScanner() {
        scanner.close();
    }
}