package dev.bkrk.poker.test;

import dev.bkrk.poker.PokerHand;
import dev.bkrk.poker.Ranking;
import dev.bkrk.Card;

import java.util.Arrays;
import java.util.List;

public class PokerTest {

    public static void main(String[] args) {
        testHighCard();
        testOnePair();
        testTwoPair();
        testThreeOfAKind();
        testStraight();
        testFlush();
        testFullHouse();
        testFourOfAKind();
        testStraightFlush();
        testRoyalFlush();
    }

    private static void testHighCard() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.SPADE, "K", 11),
                new Card(Card.Suit.CLUB, "10", 8),
                new Card(Card.Suit.DIAMOND, "6", 4),
                new Card(Card.Suit.HEART, "3", 1)
        );
        testHand(cards, Ranking.HIGH_CARD, "High Card");
    }

    private static void testOnePair() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.SPADE, "A", 12),
                new Card(Card.Suit.CLUB, "10", 8),
                new Card(Card.Suit.DIAMOND, "6", 4),
                new Card(Card.Suit.HEART, "3", 1)
        );
        testHand(cards, Ranking.ONE_PAIR, "One Pair");
    }

    private static void testTwoPair() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.SPADE, "A", 12),
                new Card(Card.Suit.CLUB, "10", 8),
                new Card(Card.Suit.DIAMOND, "10", 8),
                new Card(Card.Suit.HEART, "3", 1)
        );
        testHand(cards, Ranking.TWO_PAIR, "Two Pair");
    }

    private static void testThreeOfAKind() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.SPADE, "A", 12),
                new Card(Card.Suit.CLUB, "A", 12),
                new Card(Card.Suit.DIAMOND, "6", 4),
                new Card(Card.Suit.HEART, "3", 1)
        );
        testHand(cards, Ranking.THREE_OF_A_KIND, "Three of a Kind");
    }

    private static void testStraight() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "9", 7),
                new Card(Card.Suit.SPADE, "8", 6),
                new Card(Card.Suit.CLUB, "7", 5),
                new Card(Card.Suit.DIAMOND, "6", 4),
                new Card(Card.Suit.HEART, "5", 3)
        );
        testHand(cards, Ranking.STRAIGHT, "Straight");
    }

    private static void testFlush() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.HEART, "10", 8),
                new Card(Card.Suit.HEART, "6", 4),
                new Card(Card.Suit.HEART, "3", 1),
                new Card(Card.Suit.HEART, "2", 0)
        );
        testHand(cards, Ranking.FLUSH, "Flush");
    }

    private static void testFullHouse() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.SPADE, "A", 12),
                new Card(Card.Suit.CLUB, "6", 4),
                new Card(Card.Suit.DIAMOND, "6", 4),
                new Card(Card.Suit.HEART, "6", 4)
        );
        testHand(cards, Ranking.FULL_HOUSE, "Full House");
    }

    private static void testFourOfAKind() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "6", 4),
                new Card(Card.Suit.SPADE, "6", 4),
                new Card(Card.Suit.CLUB, "6", 4),
                new Card(Card.Suit.DIAMOND, "6", 4),
                new Card(Card.Suit.HEART, "3", 1)
        );
        testHand(cards, Ranking.FOUR_OF_A_KIND, "Four of a Kind");
    }

    private static void testStraightFlush() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "9", 7),
                new Card(Card.Suit.HEART, "8", 6),
                new Card(Card.Suit.HEART, "7", 5),
                new Card(Card.Suit.HEART, "6", 4),
                new Card(Card.Suit.HEART, "5", 3)
        );
        testHand(cards, Ranking.STRAIGHT_FLUSH, "Straight Flush");
    }

    private static void testRoyalFlush() {
        List<Card> cards = Arrays.asList(
                new Card(Card.Suit.HEART, "A", 12),
                new Card(Card.Suit.HEART, "K", 11),
                new Card(Card.Suit.HEART, "Q", 10),
                new Card(Card.Suit.HEART, "J", 9),
                new Card(Card.Suit.HEART, "10", 8)
        );
        testHand(cards, Ranking.ROYAL_FLUSH, "Royal Flush");
    }

    private static void testHand(List<Card> cards, Ranking expectedRanking, String handName) {
        PokerHand hand = new PokerHand(1, cards);
        hand.evalHand();
        System.out.printf("Testing %s: Expected %s, Got %s%n", handName, expectedRanking, hand.getScore());
        assert hand.getScore() == expectedRanking : "Test failed for " + handName;
    }
}