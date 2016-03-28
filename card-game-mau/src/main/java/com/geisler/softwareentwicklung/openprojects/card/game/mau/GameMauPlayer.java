/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
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
