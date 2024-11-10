package dev.bkrk.poker;

import dev.bkrk.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokerHand {
    private List<Card> hand;
    private List<Card> keepers;
    private List<Card> discards;
    private Ranking score = Ranking.NONE;
    private int playerNo;

    public PokerHand(int playerNo, List<Card> hand) {
        hand.sort(Card.sortRankReversedSuit());
        this.hand = hand;
        this.playerNo = playerNo;

        keepers = new ArrayList<>(hand.size());
        discards = new ArrayList<>(hand.size());
    }

    public Ranking getScore() {
        return score;
    }
    public int getPlayerNo() {
        return playerNo;
    }

    @Override
    public String toString() {
        return "%d. %-16s Rank:%d %-40s %s".formatted(
                playerNo, score, score.ordinal(), hand,
                (!discards.isEmpty()) ? "You Can Discard: " + discards : "No Discard Available");
    }

    public void evalHand() {
        if (isRoyalFlush()) {
            score = Ranking.ROYAL_FLUSH;
        } else if (isStraightFlush()) {
            score = Ranking.STRAIGHT_FLUSH;
        } else if (isFourOfAKind()) {
            score = Ranking.FOUR_OF_A_KIND;
        } else if (isFullHouse()) {
            score = Ranking.FULL_HOUSE;
        } else if (isFlush()) {
            score = Ranking.FLUSH;
        } else if (isStraight()) {
            score = Ranking.STRAIGHT;
        } else if (isThreeOfAKind()) {
            score = Ranking.THREE_OF_A_KIND;
        } else if (isTwoPair()) {
            score = Ranking.TWO_PAIR;
        } else if (isOnePair()) {
            score = Ranking.ONE_PAIR;
        } else {
            score = Ranking.NONE;
        }
        pickDiscards();
    }

    private boolean faceFrequency(int count) {
        return hand.stream()
                .collect(Collectors.groupingBy(Card::face, Collectors.counting()))
                .values()
                .contains((long) count);
    }

    private int countPairs() {
        return (int) hand.stream()
                .collect(Collectors.groupingBy(Card::face, Collectors.counting()))
                .values()
                .stream()
                .filter(count -> count == 2)
                .count();
    }

    private boolean isOnePair() {
        return countPairs() == 1;
    }
    private boolean isTwoPair() {
        return countPairs() == 2;
    }
    private boolean isThreeOfAKind() {
        return faceFrequency(3);
    }
    private boolean isStraight() {
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i).rank() - hand.get(i + 1).rank() != 1) {
                return false;
            }
        }
        return true;
    }
    private boolean isFlush() {
        Card.Suit suit = hand.get(0).suit();
        return hand.stream().allMatch(card -> card.suit() == suit);
    }
    private boolean isFullHouse() {
        return faceFrequency(3) && faceFrequency(2);
    }
    private boolean isFourOfAKind() {
        return faceFrequency(4);
    }
    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }
    private boolean isRoyalFlush() {
        return isStraightFlush() && hand.stream().anyMatch(card -> card.face().equals("A"));
    }

    public void pickDiscards() {
        DiscardStrategy.pickDiscards(this);
    }
    protected List<Card> getHand() {
        return hand;
    }
    protected List<Card> getKeepers() {
        return keepers;
    }
    protected List<Card> getDiscards() {
        return discards;
    }

    public void setKeepers(List<Card> keepers) {
        this.keepers = keepers;
    }

    public void setDiscards(List<Card> newDiscards) {
        this.discards = new ArrayList<>(newDiscards);
    }

    public String getDiscardRecommendation() {
        if (discards.isEmpty()) {
            return "No discards";
        }
        return "You Can Discard: " + discards;
    }

    public void drawNewCards(List<Card> remainingCards) {
        hand = new ArrayList<>(hand);
        hand.removeAll(discards);

        int cardsNeeded = discards.size();
        for (int i = 0; i < cardsNeeded && !remainingCards.isEmpty(); i++) {
            Card newCard = remainingCards.remove(0);
            hand.add(newCard);
        }
        discards.clear();
    }

    public void clearDiscards() {
        discards.clear();
    }
}
