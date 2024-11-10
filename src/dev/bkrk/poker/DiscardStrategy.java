package dev.bkrk.poker;

import dev.bkrk.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DiscardStrategy {

    public static void pickDiscards(PokerHand hand) {
        List<Card> keepers = new ArrayList<>();
        List<Card> discards = new ArrayList<>();

        List<Card> pairsAndGroups = getPairsAndGroups(hand);
        keepers.addAll(pairsAndGroups);

        List<Card> flushCandidates = getFlushCandidates(hand);
        if (flushCandidates.size() >= 4) {
            keepers.addAll(flushCandidates);
        }

        List<Card> straightCandidates = getStraightCandidates(hand);
        if (straightCandidates.size() >= 4) {
            keepers.addAll(straightCandidates);
        }

        List<Card> highValueCards = getHighValueCards(hand);
        for (Card card : highValueCards) {
            if (!keepers.contains(card)) {
                keepers.add(card);
            }
        }

        List<Card> remainingCards = new ArrayList<>(hand.getHand());
        remainingCards.removeAll(keepers);

        int discardCount = 0;
        for (Card card : remainingCards) {
            if (discardCount < 3) {
                discards.add(card);
                discardCount++;
            } else {
                keepers.add(card);
            }
        }

        hand.setKeepers(keepers);
        hand.setDiscards(discards);
    }
    private static List<Card> getPairsAndGroups(PokerHand hand) {
        return hand.getHand().stream()
                .collect(Collectors.groupingBy(Card::face))
                .values()
                .stream()
                .filter(cards -> cards.size() >= 2)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
    private static List<Card> getPairCandidates(PokerHand hand) {
        return hand.getHand().stream()
                .collect(Collectors.groupingBy(Card::face))
                .values()
                .stream()
                .filter(cards -> cards.size() == 2)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
    private static List<Card> getFlushCandidates(PokerHand hand) {
        return hand.getHand().stream()
                .collect(Collectors.groupingBy(Card::suit))
                .values()
                .stream()
                .filter(cards -> cards.size() >= 4)
                .findFirst()
                .orElse(Collections.emptyList());
    }

    private static List<Card> getStraightCandidates(PokerHand hand) {
        List<Card> sortedCards = hand.getHand().stream()
                .sorted(Comparator.comparing(Card::rank))
                .distinct()
                .collect(Collectors.toList());

        List<Card> straightCandidates = new ArrayList<>();
        List<Card> tempSequence = new ArrayList<>();

        for (int i = 0; i < sortedCards.size() - 1; i++) {
            tempSequence.add(sortedCards.get(i));
            if (sortedCards.get(i + 1).rank() - sortedCards.get(i).rank() == 1) {
                tempSequence.add(sortedCards.get(i + 1));
            } else {
                if (tempSequence.size() >= 4) break;
                tempSequence.clear();
            }
        }

        if (tempSequence.size() >= 4) straightCandidates.addAll(tempSequence);
        return straightCandidates;
    }
    private static List<Card> getHighValueCards(PokerHand hand) {
        return hand.getHand().stream()
                .filter(card -> card.rank() >= 10)
                .collect(Collectors.toList());
    }
}
