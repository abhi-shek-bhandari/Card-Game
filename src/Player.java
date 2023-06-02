import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public boolean hasValidCard(Card topCard) {
        for (Card card : hand) {
            if (card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank()) {
                return true;
            }
        }
        return false;
    }

    public void receiveInitialCards(List<Card> initialCards) {
        hand.addAll(initialCards);
    }

    public boolean playCard(Card card, Card topCard) {
        if (hand.contains(card) && (card.getSuit() == topCard.getSuit() || card.getRank() == topCard.getRank())) {
            hand.remove(card);
            return true;
        }
        return false;
    }

    public Card drawCard(Deck deck) {
        Card card = deck.drawCard();
        if (card != null) {
            hand.add(card);
        }
        return card;
    }
}
