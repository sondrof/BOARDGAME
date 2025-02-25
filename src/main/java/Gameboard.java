import dice.DiceSet;
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


        tileLogic.setSpecialTile(4, 10);
        tileLogic.setSpecialTile(8, 22);
        tileLogic.setSpecialTile(28, 48);
        tileLogic.setSpecialTile(40, 36);
        tileLogic.setSpecialTile(80, 19);


        tileLogic.setSpecialTile(17, -10);
        tileLogic.setSpecialTile(54, -20);
        tileLogic.setSpecialTile(62, -43);
        tileLogic.setSpecialTile(64, -4);
        tileLogic.setSpecialTile(87, -63);
        tileLogic.setSpecialTile(93, -20);
        tileLogic.setSpecialTile(95, -20);
        tileLogic.setSpecialTile(99, -21);


        System.out.println("How many players? ");
        int numberOfPlayers = scanner.nextInt();
        scanner.nextLine();


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

    public void playRound() {
        System.out.println("Round number " + roundNumber);

        for (Player player : playerLogic.getPlayerList()) {
            playerLogic.movePlayer(player.getPlayerId());

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