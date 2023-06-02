import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private List<Player> players;
    private Deck deck;
    private List<Card> discardPile;
    private int currentPlayerIndex;
    private boolean reverseDirection;
    private boolean gameEnded;

    public Game(List<Player> players){
        this.players = players;
        this.deck = new Deck();
        this.discardPile = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.reverseDirection = false;
        this.gameEnded = false;
    }

    private void reverseDirection() {
        reverseDirection = !reverseDirection;
    }

    private void updateCurrentPlayerIndex() {
        int increment = reverseDirection ? -1 : 1;
        currentPlayerIndex = (currentPlayerIndex + increment + players.size()) % players.size();
    }

    private void drawCards(int numCards) {
        Player nextPlayer = getNextPlayer();
        for (int i = 0; i < numCards; i++) {
            Card drawnCard = nextPlayer.drawCard(deck);
            if (drawnCard != null) {
                System.out.println(nextPlayer.getName() + " drew a card: " + drawnCard.getSuit() + " " + drawnCard.getRank());
            } else {
                System.out.println("Draw pile is empty. The game is a Draw.");
                gameEnded = true;
                break;
            }
        }
    }

    private Player getNextPlayer() {
        int increment = reverseDirection ? -1 : 1;
        int nextPlayerIndex = (currentPlayerIndex + increment + players.size()) % players.size();
        return players.get(nextPlayerIndex);
    }

    private void skipNextPlayer() {
        updateCurrentPlayerIndex();
    }

    private void handleActionCard(Card card) {
        switch (card.getRank()) {
            case ACE:
            skipNextPlayer();
                break;
            case KING:
                reverseDirection();
                break;
            case QUEEN:
                drawCards(2);
                break;
            case JACK:
                drawCards(4);
                break;
        }
    }

    private boolean isValidCard(List<Card> hand, Card topCard,int playerChoice) {
        if (topCard.getRank() == Rank.QUEEN && hand.get(playerChoice) == topCard) return false;
        if (topCard.isActionCard()) return true;
        int counter = 0;
        for (Card card : hand) {
            if (counter++ == playerChoice && (card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank())) {
                return true;
            }
        }
        return false;
    }

    private Card validCardAccordingToSystem(List<Card> hand, Card topCard) {
        int counter = 0;
        for (Card card : hand) {
            if (card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank()) {
                return card;
            }
        }
        return null;
    }

    public void printingInstructionsToUser(Card topCard,Player currentPlayer){
        System.out.println("\n==================================================\n");
        System.out.println("Top card: " + topCard.getSuit() + " " + topCard.getRank());
        System.out.println("Current player: " + currentPlayer.getName());
        System.out.println("Card Indexing starts from 0: ");
        if (currentPlayer.hasValidCard(topCard)){
            System.out.println("System Suggestion: you have a Valid Card for the Move");
            System.out.println("According to System: card "
                            + validCardAccordingToSystem(currentPlayer.getHand(),topCard)
                            + " is a Valid Card for the move:");
        }
        else {
            if (topCard.isActionCard()){
                System.out.println("System Suggestion: you can play any Card for the Move");
            }else {
                System.out.println("System Suggestion: you do not have a Valid Card for the Move" +
                        "\nSuggestion: draw a card from Deck");
            }
        }
        System.out.println("To Draw a Card from Deck Enter Number- 20 :");
        System.out.println("Player's hand: ");
    }

    public void start() {
        // Distribute initial cards to players
        for (Player player : players) {
            List<Card> initialCards = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Card card = deck.drawCard();
                if (card != null) {
                    initialCards.add(card);
                }
            }
            player.receiveCards(initialCards);
        }

        Scanner scanner = new Scanner(System.in);

        Card topCard = deck.drawCard();
        discardPile.add(topCard);

        while (!gameEnded) {
            Player currentPlayer = players.get(currentPlayerIndex);


            List<Card> hand = currentPlayer.getHand();

            boolean validCard = false;

            // Inputting player Choice for card
            int playerChoice = -1;
            while(!validCard && (playerChoice >= hand.size() || playerChoice < 0)){

                // Giving Instructions to the User About Play
                this.printingInstructionsToUser(topCard,currentPlayer);

                int counter = 0;
                for (Card card:hand) {
                    System.out.println("Enter " + counter++ + " " + card);
                }

                playerChoice = scanner.nextInt();
                if (playerChoice == 20){
                    Card drawnCard = currentPlayer.drawCard(deck);
                    if (drawnCard != null) {
                        System.out.println(currentPlayer.getName() + " drew a card: " + drawnCard.getSuit() + " " + drawnCard.getRank());
                    } else {
                        System.out.println("Draw pile is empty. The game ends in a draw.");
                        gameEnded = true;
                        break;
                    }
                }

                if (gameEnded) break;

                // Checking for playing a valid card if not stopping the game
                validCard = isValidCard(currentPlayer.getHand(), topCard,playerChoice);
                if (validCard) {
                    Card playerValidCard = currentPlayer.getHand().get(playerChoice);
                    currentPlayer.playCard(playerValidCard, topCard);
                    discardPile.add(playerValidCard);
                    topCard = playerValidCard;

                    // Checking if the player has won
                    if (currentPlayer.getHand().isEmpty()) {
                        System.out.println(currentPlayer.getName() + " has won the game!");
                        gameEnded = true;
                        break;
                    }

                    // Handling action cards
                    if (playerValidCard.isActionCard()) {
                        handleActionCard(playerValidCard);
                    }
                }
            }
            // Moving to the next player
            updateCurrentPlayerIndex();
        }
    }


}
