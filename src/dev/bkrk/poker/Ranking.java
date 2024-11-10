package dev.bkrk.poker;

public enum Ranking {

    NONE,
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH;

    @Override
    public String toString() {
        return this.name().replace('_', ' ');
    }
}