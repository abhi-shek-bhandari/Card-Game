import java.util.ArrayList;
import java.util.List;

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

    private Card getValidCard(List<Card> hand, Card topCard) {
        for (Card card : hand) {
            if (card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank()) {
                return card;
            }
        }
        return null;
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

        Card topCard = deck.drawCard();
        discardPile.add(topCard);

        while (!gameEnded) {
            Player currentPlayer = players.get(currentPlayerIndex);

            System.out.println("Top card: " + topCard.getSuit() + " " + topCard.getRank());
            System.out.println("Current player: " + currentPlayer.getName());
            System.out.println("Player's hand: " + currentPlayer.getHand());

            // Player's turn
            if (!currentPlayer.hasValidCard(topCard)) {
                Card drawnCard = currentPlayer.drawCard(deck);
                if (drawnCard != null) {
                    System.out.println(currentPlayer.getName() + " drew a card: " + drawnCard.getSuit() + " " + drawnCard.getRank());
                } else {
                    System.out.println("Draw pile is empty. The game ends in a draw.");
                    gameEnded = true;
                    break;
                }
            }

            // Simulate playing a valid card
            Card validCard = getValidCard(currentPlayer.getHand(), topCard);
            if (validCard != null) {
                currentPlayer.playCard(validCard, topCard);
                discardPile.add(validCard);
                topCard = validCard;

                // Check if the player has won
                if (currentPlayer.getHand().isEmpty()) {
                    System.out.println(currentPlayer.getName() + " has won the game!");
                    gameEnded = true;
                    break;
                }

                // Handle action cards (bonus feature)
                if (validCard.isActionCard()) {
                    handleActionCard(validCard);
                }
            }

            // Move to the next player
            updateCurrentPlayerIndex();
        }
    }


}
