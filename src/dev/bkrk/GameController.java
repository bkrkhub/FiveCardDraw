package dev.bkrk;

import dev.bkrk.poker.PokerGame;

public class GameController {

    public static void main(String[] args) {

        PokerGame fiveCardDraw = new PokerGame(5, 5);
        fiveCardDraw.startPlay();

    }
}
