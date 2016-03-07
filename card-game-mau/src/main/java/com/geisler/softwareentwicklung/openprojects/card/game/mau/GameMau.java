/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.CardByRulesNotAllowedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author ngeis
 */
public class GameMau {

    private GameMauPlayer activePlayer;
    
    private EnumSkatColor choosedColor;
    
    private Stack<SkatCard> drawStack;
    private Stack<SkatCard> middleStack;
    private Stack<SkatCard> extraDrawStack;
    private HashMap<Integer, GameMauPlayer> players;
    private boolean nextPlayerStay;
    private boolean running;
    private GameMauPlayer winner;

    
    public GameMau() {
        players = new HashMap<>();
        drawStack = new Stack();
        initialiseDrawStack();
        middleStack = new Stack<>();
        extraDrawStack = new Stack<>();
        choosedColor = null;
        winner = null;
    }
    /**
     * Adds a new Player with given name to the Mau-Game
     * 
     * @param newPlayer the new player
     */
    public void addNewPlayerToGame(GameMauPlayer newPlayer) throws NoMorePlayersAllowedException {
        if(this.players.size() == 4) {
            throw new NoMorePlayersAllowedException();
        }
        List<SkatCard> listHandCards = getPlayersInitialCards();
        newPlayer.giveHandCards(listHandCards);
        activePlayer = newPlayer;
        this.players.put(this.players.size() + 1, newPlayer);
    }
    
    public GameMauPlayer getActivePlayer() {
        return activePlayer;
    }

    public Stack<SkatCard> getMiddleStack() {
        return middleStack;
    }

    Stack<SkatCard> getDrawStack() {
        if(drawStack.isEmpty()) {
            pushMiddleStackToDrawStackShuffled();
        }
        return drawStack;
    }
    
    public void throwPlayerCardToMiddleStack(Integer index) throws CardByRulesNotAllowedException {
        if(index == null) {
            index = 0;
        }
        SkatCard card = getActivePlayer().getSelectedHandCardToThrow(index);
        try {
            checkRulesForCard(card);
            if(card.getValue().equals(EnumSkatValue.JACK) && choosedColor == null) {
                choosedColor = getActivePlayer().getSelectedColor();
            }
            middleStack.push(card);
            if(getActivePlayer().getHandSize() < 1) {
                winner = getActivePlayer();
                setRunning(false);
            }
            if(card.getValue().equals(EnumSkatValue.SEVEN)) {
                extraDrawStack.push(getDrawStack().pop());
                extraDrawStack.push(getDrawStack().pop());
            }
        } catch (CardByRulesNotAllowedException e) {
            getActivePlayer().giveHandCard(getDrawStack().pop());
            getActivePlayer().giveHandCard(card);
        }        
        pushToNextPlayerTurn();
        checkActivePlayersNextCardsForSeven();
    }

    public HashMap<Integer, GameMauPlayer> getPlayers() {
        return this.players;
    }

    public void popFirstCardToMiddleStack() {
        this.middleStack.add(getDrawStack().firstElement()); // initialise Middle-Stack for playing
    }
    
    /**
     * Returns the initial cards from the Skat-Stack
     * 
     * @return 
     */
    List<SkatCard> getPlayersInitialCards() {
        List<SkatCard> listCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listCards.add(getDrawStack().pop());
        }
        return listCards;
    }

    /**
     * initialise and shuffles the drawStack for play
     */
    private void initialiseDrawStack() {
        for (EnumSkatColor color : EnumSkatColor.values()) {
            for (EnumSkatValue value : EnumSkatValue.values()) {
                this.drawStack.add(new SkatCard(color, value));
            }
        }
        Collections.shuffle(drawStack);
    }

    private void pushToNextPlayerTurn() {
        Integer idx = 0;
        for (HashMap.Entry<Integer, GameMauPlayer> entry : players.entrySet()) {
            if(entry.getValue().equals(activePlayer)) {
                idx = entry.getKey();
            }
        }
        // Standard
        if(idx == players.size()) {
            idx = 1;
        } else {
            idx++;
        }
        // Ace-Rule
        if(isNextPlayerStay()) {
            if(idx == players.size()) {
                idx = 1;
            } else {
                idx++;
            }
            nextPlayerStay = false;
        }
        activePlayer = players.get(idx);
    }

    void checkRulesForCard(SkatCard playerCard) throws CardByRulesNotAllowedException {
        SkatCard stackCard = getMiddleStack().peek();
        boolean ruleBreak = false;
        // Standard-Rules
        if(!stackCard.getColor().equals(playerCard.getColor())
                && !stackCard.getValue().equals(playerCard.getValue())
                && !playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        // Color-Chooser Rule
        if(choosedColor != null && !choosedColor.equals(playerCard.getColor())) {
            ruleBreak = true;
        } else if (choosedColor != null
                && choosedColor.equals(playerCard.getColor())) {
            ruleBreak = false;
            choosedColor = null;
        }
        // Jack on Jack Rule
        if(stackCard.getValue().equals(EnumSkatValue.JACK)
                && playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        if(!ruleBreak) {
            if(playerCard.getValue().equals(EnumSkatValue.ACE)) {
                nextPlayerStay = true;
            }
        } else {
            throw new CardByRulesNotAllowedException();
        }
    }

    public EnumSkatColor getChoosedColor() {
        return choosedColor;
    }

    /**
     * if player doesn't have a seven then he draw the extra cards
     * this method checks activePlayers Cards and give new handcards 
     * if there is no seven on hand
     */
    void checkActivePlayersNextCardsForSeven() {
        if(checkPlayerHasCard(EnumSkatValue.SEVEN)) {
            return;
        }
        int size = extraDrawStack.size();
        for(int i = 0; i < size ; i++) {
            activePlayer.giveHandCard(extraDrawStack.pop());
        }
    }
    
    public boolean isNextPlayerStay() {
        return nextPlayerStay;
    }

    boolean checkPlayerHasCard(EnumSkatValue enumSkatValue) {
        for (SkatCard skatCard : activePlayer.getHandCards()) {
            if(skatCard.getValue().equals(EnumSkatValue.SEVEN)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isRunning() {
        return running;
    }

    private void setRunning(boolean b) {
        this.running = b;
    }

    public void start() {
        popFirstCardToMiddleStack();
        this.running = true;
    }
    
    public GameMauPlayer getWinner() {
        return this.winner;
    }

    void pushMiddleStackToDrawStackShuffled() {
        SkatCard topMiddleStackCard = getMiddleStack().pop();
        
        while(!getMiddleStack().isEmpty()) {
            drawStack.push(getMiddleStack().pop());
        }
        // return CardOnTop to MiddleStack
        getMiddleStack().push(topMiddleStackCard);
        
        // Shuffle drawStack
        Collections.shuffle(drawStack);
    }
}
