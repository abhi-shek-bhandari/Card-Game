# Card-Game


_This is a multiplayer card game implementation of Card Game in Java. It supports multiple players (up to 4) and different types of cards, including number cards and action cards. The game follows the standard rules given below._

Rules of the Game
---
* Each player starts with a hand of 5 cards.
* The game starts with a deck of 52 cards (a standard deck of playing cards).
* Players take turns playing cards from their hand, following rules that define what cards can be played when.
* A player can only play a card if it matches either the suit or the rank of the top card on the discard pile or the card is a action card.
* The Queen cannot be played by two players simultaneously.
* If a player cannot play a card, they must draw a card from the draw pile. If the draw pile is empty, the game ends in a draw and no player is declared a winner.
* The game ends when one player runs out of cards and is declared the winner.

### How to Run the Code


1. Ensure you have Java installed on your system.
2. Clone or download the project to your local machine.
3. Open the project in your preferred Java IDE.
4. Build the project to compile the code.
5. Run the Main class to start the game.
6. Follow the on-screen instructions to play the game.
7. Enjoy playing Card game with your friends!

#### Bonus Feature
In addition to the standard rules, the game includes a bonus feature where Aces, Kings, Queens, and Jacks are action cards. When one of these cards is played, the following actions occur:

* Ace (A): Skip the next player in turn.
* King (K): Reverse the sequence of who plays next.
* Queen (Q): The next player draws two cards.
* Jack (J): The next player draws four cards.


Note: Actions are not stackable, meaning if a Queen is played and the next player draws two cards, they cannot play another Queen on their turn even if available.