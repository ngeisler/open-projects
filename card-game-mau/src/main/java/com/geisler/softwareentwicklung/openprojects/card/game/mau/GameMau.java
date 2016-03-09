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
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.CardByRulesNotAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * The main game class to play the mau game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public class GameMau {

    /**
     * The active player of the game.
     */
    private GameMauPlayer player;
    /**
     * The choosed color of the player or npc during game.
     */
    private EnumSkatColor choosedcolor;
    /**
     * The stack where players and npcs draw their cards.
     */
    private Stack<SkatCard> drawstack;
    /**
     * The stack where players and npcs drop their cards.
     */
    private Stack<SkatCard> middlestack;
    /**
     * The stack where players and npcs draw their extra cards.
     */
    private Stack<SkatCard> extracards;
    /**
     * The map of all players with an idx for identification.
     */
    private Map<Integer, GameMauPlayer> players;
    /**
     * A boolean that shows if next player have to stay for the next round.
     */
    private boolean nextplayerstay;
    private boolean running;
    private GameMauPlayer winner;

    
    public GameMau() {
        players = new HashMap<>();
        drawstack = new Stack();
        initialiseDrawStack();
        middlestack = new Stack<>();
        extracards = new Stack<>();
        choosedcolor = null;
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
        player = newPlayer;
        this.players.put(this.players.size() + 1, newPlayer);
    }
    
    public GameMauPlayer getPlayer() {
        return player;
    }

    public Stack<SkatCard> getMiddlestack() {
        return middlestack;
    }

    Stack<SkatCard> getDrawstack() {
        if(drawstack.isEmpty()) {
            pushMiddleStackToDrawStackShuffled();
        }
        return drawstack;
    }
    
    public void throwPlayerCardToMiddleStack(Integer index) throws CardByRulesNotAllowedException {
        if(index == null) {
            index = 0;
        }
        SkatCard card = getPlayer().getSelectedHandCardToThrow(index);
        try {
            checkRulesForCard(card);
            if(card.getValue().equals(EnumSkatValue.JACK) && choosedcolor == null) {
                choosedcolor = getPlayer().getSelectedColor();
            }
            middlestack.push(card);
            if(getPlayer().getHandSize() < 1) {
                winner = getPlayer();
                setRunning(false);
            }
            if(card.getValue().equals(EnumSkatValue.SEVEN)) {
                extracards.push(getDrawstack().pop());
                extracards.push(getDrawstack().pop());
            }
        } catch (CardByRulesNotAllowedException e) {
            getPlayer().giveHandCard(getDrawstack().pop());
            getPlayer().giveHandCard(card);
        }        
        pushToNextPlayerTurn();
        checkActivePlayersNextCardsForSeven();
    }

    public Map<Integer, GameMauPlayer> getPlayers() {
        return this.players;
    }

    public void popFirstCardToMiddleStack() {
        this.middlestack.add(getDrawstack().firstElement()); // initialise Middle-Stack for playing
    }
    
    /**
     * Returns the initial cards from the Skat-Stack
     * 
     * @return 
     */
    List<SkatCard> getPlayersInitialCards() {
        List<SkatCard> listCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listCards.add(getDrawstack().pop());
        }
        return listCards;
    }

    /**
     * initialise and shuffles the drawstack for play
     */
    private void initialiseDrawStack() {
        for (EnumSkatColor color : EnumSkatColor.values()) {
            for (EnumSkatValue value : EnumSkatValue.values()) {
                this.drawstack.add(new SkatCard(color, value));
            }
        }
        Collections.shuffle(drawstack);
    }

    private void pushToNextPlayerTurn() {
        Integer idx = 0;
        for (HashMap.Entry<Integer, GameMauPlayer> entry : players.entrySet()) {
            if(entry.getValue().equals(player)) {
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
        if(isNextplayerstay()) {
            if(idx == players.size()) {
                idx = 1;
            } else {
                idx++;
            }
            nextplayerstay = false;
        }
        player = players.get(idx);
    }

    void checkRulesForCard(SkatCard playerCard) throws CardByRulesNotAllowedException {
        SkatCard stackCard = getMiddlestack().peek();
        boolean ruleBreak = false;
        // Standard-Rules
        if(!stackCard.getColor().equals(playerCard.getColor())
                && !stackCard.getValue().equals(playerCard.getValue())
                && !playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        // Color-Chooser Rule
        if(choosedcolor != null && !choosedcolor.equals(playerCard.getColor())) {
            ruleBreak = true;
        } else if (choosedcolor != null
                && choosedcolor.equals(playerCard.getColor())) {
            ruleBreak = false;
            choosedcolor = null;
        }
        // Jack on Jack Rule
        if(stackCard.getValue().equals(EnumSkatValue.JACK)
                && playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        if(!ruleBreak) {
            if(playerCard.getValue().equals(EnumSkatValue.ACE)) {
                nextplayerstay = true;
            }
        } else {
            throw new CardByRulesNotAllowedException();
        }
    }

    public EnumSkatColor getChoosedcolor() {
        return choosedcolor;
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
        int size = extracards.size();
        for(int i = 0; i < size ; i++) {
            player.giveHandCard(extracards.pop());
        }
    }
    
    public boolean isNextplayerstay() {
        return nextplayerstay;
    }

    boolean checkPlayerHasCard(EnumSkatValue enumSkatValue) {
        for (SkatCard skatCard : player.getHandCards()) {
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
        SkatCard topMiddleStackCard = getMiddlestack().pop();
        
        while(!getMiddlestack().isEmpty()) {
            drawstack.push(getMiddlestack().pop());
        }
        // return CardOnTop to MiddleStack
        getMiddlestack().push(topMiddleStackCard);
        
        // Shuffle drawstack
        Collections.shuffle(drawstack);
    }
}
