import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numPlayers = 0;
        System.out.println("Enter the game supports Max 4 players:");
        Scanner scanner = new Scanner(System.in);
        while (numPlayers > 4 || numPlayers < 1) {
            System.out.println("Enter the number of players:");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }
        List<Player> playerNames = getPlayerNames(scanner, numPlayers);

        Game game = new Game(playerNames);
        game.start();
    }

    private static List<Player> getPlayerNames(Scanner scanner, int numPlayers) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Enter the name for player " + (i+1) + ":");
            list.add(new Player(scanner.nextLine()));
        }
        return list;
    }
}