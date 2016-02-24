package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ngeis
 */
class GameMauPlayer {
    
    String playerName;
    
    List<SkatCard> handCards;

    public GameMauPlayer(String name) {
        playerName = name;
        handCards = new ArrayList<>();
    }
        
    SkatCard getSelectedHandCardToThrow(int selectedCard) {
        return this.handCards.remove(selectedCard);
    }

    int getHandSize() {
        return getHandCards().size();
    }

    List<SkatCard> getHandCards() {
        return this.handCards;
    }

    void giveHandCards(List<SkatCard> listHandCards) {
        this.handCards.addAll(listHandCards);
    }

    String getName() {
        return this.playerName;
    }

    void giveHandCard(SkatCard newCard) {
        this.handCards.add(newCard);
    }

    EnumSkatColor getSelectedColor() {
        return EnumSkatColor.HEARTS;
    }

}
