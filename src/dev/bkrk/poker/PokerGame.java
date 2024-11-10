package dev.bkrk.poker;

import dev.bkrk.Card;

import java.util.*;

public class PokerGame {
    private final List<Card> deck = Card.getStandardDeck();
    private final int playerCount;
    private final int cardsInHand;
    private final List<PokerHand> pokerHands;

    public PokerGame(int playerCount, int cardsInHand) {
        this.playerCount = playerCount;
        this.cardsInHand = cardsInHand;
        pokerHands = new ArrayList<>(cardsInHand);
    }

    private static void accept(PokerHand hand) {
        System.out.printf("%d. %-16s Rank:%-4d %-40s %n",
                hand.getPlayerNo(), hand.getScore(), hand.getScore().ordinal(), hand.getHand());
    }

    public void startPlay() {
        Collections.shuffle(deck);
        int randomMiddle = new Random().nextInt(15, 35);
        Collections.rotate(deck, randomMiddle);

        deal();
        System.out.println("=".repeat(150));
        System.out.println(" ".repeat(55) + "Five Card Draw - Poker Game ");
        System.out.println("=".repeat(150));

        System.out.println("\nPlayer Hands (Initial Evaluation):");
        System.out.println("-".repeat(150));

        pokerHands.forEach(hand -> {
            hand.evalHand();
            System.out.printf("%d. %-16s Rank:%-4d %-40s %s%n",
                    hand.getPlayerNo(), hand.getScore(), hand.getScore().ordinal(), hand.getHand(), hand.getDiscardRecommendation());
        });
        System.out.println("-".repeat(150));

        int cardsDealt = playerCount * cardsInHand;
        List<Card> remainingCards = new ArrayList<>(deck.subList(cardsDealt, deck.size()));
        System.out.println("\nRemaining Cards:");
        Card.printDeck(remainingCards, "Remaining Cards", 2);
        System.out.println("-".repeat(150));

        for (PokerHand playerHand : pokerHands) {
            playerHand.drawNewCards(remainingCards);
            playerHand.evalHand();
            playerHand.clearDiscards();
        }

        System.out.println("\nPlayer Hands (After Draw):");
        System.out.println("-".repeat(150));
        for (PokerHand pokerHand : pokerHands) {
            accept(pokerHand);
        }
        System.out.println("*".repeat(150));

        PokerHand winner = getWinner();
        if (winner != null) {
            System.out.println(" ".repeat(65) + "🌟 Winner 🌟");
            System.out.println("*".repeat(150));
            System.out.printf(" ".repeat(35) + "🥇 %d. %-16s Rank:%-4d %-40s %n",
                    winner.getPlayerNo(), winner.getScore(), winner.getScore().ordinal(), winner.getHand());
            System.out.println("*".repeat(150));
        } else {
            System.out.println("No winner found.");
        }
    }

    private void deal() {
        Card[][] hands = new Card[playerCount][cardsInHand];
        for (int deckIndex = 0, i = 0; i < cardsInHand; i++) {
            for (int j = 0; j < playerCount; j++) {
                hands[j][i] = deck.get(deckIndex++);
            }
        }
        int playerNo = 1;
        for (Card[] hand : hands) {
            pokerHands.add(new PokerHand(playerNo++, Arrays.asList(hand)));
        }
    }

    private PokerHand getWinner() {
        return pokerHands.stream()
                .max(Comparator.comparing(PokerHand::getScore))
                .orElse(null);
    }
}
