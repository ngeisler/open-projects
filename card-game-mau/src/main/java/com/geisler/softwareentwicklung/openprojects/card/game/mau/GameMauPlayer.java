package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ngeis
 */
public class GameMauPlayer {
    
    String playerName;
    
    List<SkatCard> handCards;
    
    EnumSkatColor jacksColor = EnumSkatColor.HEARTS;

    public GameMauPlayer(String name) {
        playerName = name;
        handCards = new ArrayList<>();
    }
        
    SkatCard getSelectedHandCardToThrow(int selectedCard) {
        return this.handCards.remove(selectedCard);
    }

    public int getHandSize() {
        return getHandCards().size();
    }

    public List<SkatCard> getHandCards() {
        return this.handCards;
    }

    void giveHandCards(List<SkatCard> listHandCards) {
        this.handCards.addAll(listHandCards);
    }

    public String getName() {
        return this.playerName;
    }

    void giveHandCard(SkatCard newCard) {
        this.handCards.add(newCard);
    }

    EnumSkatColor getSelectedColor() {
        return jacksColor;
    }

    public void setJacksColor(EnumSkatColor color) {
        this.jacksColor = color;
    }
}
