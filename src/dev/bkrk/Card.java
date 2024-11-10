package dev.bkrk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Card(Suit suit, String face, int rank) {

    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;

        public char getImage() {
            return (new char[] {9827, 9830, 9829, 9824})[this.ordinal()];
        }
    }

    public static Comparator<Card> sortRankReversedSuit() {
        return Comparator.comparing(Card::rank).reversed().thenComparing(Card::suit);
    }

    @Override
    public String toString() {
        int index = face.equals("10") ? 2 : 1;
        String faceString = face.substring(0, index);
        // Returns the part of the face string from the beginning to the index position.
        return "%s%c(%d)".formatted(faceString, suit.getImage(), rank);
    }

    public static Card getNumericCard(Suit suit, int cardNumber) {

        if (cardNumber > 1 && cardNumber < 11) {
            return new Card(suit, String.valueOf(cardNumber), cardNumber - 2);
        }
        System.out.println("Invalid Numeric Card Selected !!");
        return null;
    }

    public static Card getFaceCard(Suit suit, char abbrev) {

        int charIndex = "JQKA".indexOf(abbrev);
        if (charIndex > -1) {
            return new Card(suit, "" + abbrev, charIndex + 9);
        }
        System.out.println("Invalid Face Card Selected !!");
        return null;
    }

    public static List<Card> getStandardDeck() {
        List<Card> deck = new ArrayList<>(52);

        for (Suit suit : Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                deck.add(getNumericCard(suit, i));
                // We have added the 36 cards.
            }
            for (char c : new char[] {'J', 'K', 'Q', 'A'}) {
                deck.add(getFaceCard(suit, c));
                // We have added 16 cards
            }
            // With this method, we created 52 unique cards.
        }
        return deck;
    }

    public static void printDeck(List<Card> deck, String description, int rows) {
        System.out.println("-".repeat(150));

        if (description != null) {
            System.out.println(description);
        }
        int cardsInRow = deck.size() / rows;
        for (int i = 0; i < rows; i++) {
            int startedIndex = i * cardsInRow;
            int endIndex = startedIndex + cardsInRow;
            deck.subList(startedIndex, endIndex).forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }


}
