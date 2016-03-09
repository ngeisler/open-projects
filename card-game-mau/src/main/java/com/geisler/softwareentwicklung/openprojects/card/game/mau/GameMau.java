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
     * The max player size for a mau game.
     */
    public static final int MAX_PLAYERS = 4;
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
    private boolean playerstay;
    /**
     * A boolean that shows if the card game is currently running.
     */
    private boolean running;
    /**
     * A GameMauPlayer reference to save the winner
     * of the game for later purposes.
     */
    private GameMauPlayer winner;
    /**
     * The standard constructor for a maugame. Most parts here are to initialise
     * the stacks and maps for later acting.
     */
    public GameMau() {
        this.players = new HashMap<>();
        this.drawstack = new Stack();
        this.initialiseDrawStack();
        this.middlestack = new Stack<>();
        this.extracards = new Stack<>();
        this.choosedcolor = null;
        this.winner = null;
    }
    /**
     * Adds a new player to the current game.
     * The player is given by the parameter.
     * An Exception is thrown if the max player size is reached.
     *
     * @param newplayer The new player given as a GameMauPlayer-instance.
     * @throws NoMorePlayersAllowedException if max size of players is reached.
     */
    public final void addNewPlayerToGame(final GameMauPlayer newplayer)
        throws NoMorePlayersAllowedException {
        if (this.getPlayers().size() == MAX_PLAYERS) {
            throw new NoMorePlayersAllowedException();
        }
        final List<SkatCard> handcards = this.getPlayersInitialCards();
        newplayer.giveHandCards(handcards);
        this.player = newplayer;
        this.getPlayers().put(this.getPlayers().size() + 1, newplayer);
    }
    /**
     * Returns the active Player as an instance of GameMauPlayer.
     *
     * @return The active player as an instance of GameMauPlayer.
     */
    public final GameMauPlayer getPlayer() {
        return this.player;
    }
    /**
     * Returns the current middlestack of the running game.
     *
     * @return The current middlestack of type Stack
     */
    public final Stack<SkatCard> getMiddlestack() {
        return this.middlestack;
    }
    /**
     * Returns the drawstack of the game. If the drawstack is empty the
     * middlestack will be shuffled an placed to the drawstack.
     *
     * @return The current drawstack with type Stack
     */
    Stack<SkatCard> getDrawstack() {
        if (this.drawstack.isEmpty()) {
            this.pushMiddleStackToDrawStackShuffled();
        }
        return this.drawstack;
    }
    /**
     * Throws a card from active Player with given index. If no index is passed
     * the first card of players hand will be choosed.
     * Could throw an Exception if card is not allowed by the rules.
     *
     * @param index The index of the choosed player card.
     * @throws CardByRulesNotAllowedException if card is not allowed by rules.
     */
    public void throwPlayerCardToMiddleStack(Integer index)
        throws CardByRulesNotAllowedException {
        if (index == null) {
            index = 0;
        }
        SkatCard card = getPlayer().getSelectedHandCardToThrow(index);
        try {
            checkRulesForCard(card);
            if (card.getValue().equals(EnumSkatValue.JACK) && getChoosedcolor() == null) {
                this.choosedcolor = getPlayer().getSelectedColor();
            }
            getMiddlestack().push(card);
            if (getPlayer().getHandSize() < 1) {
                this.winner = getPlayer();
                setRunning(false);
            }
            if (card.getValue().equals(EnumSkatValue.SEVEN)) {
                this.extracards.push(getDrawstack().pop());
                this.extracards.push(getDrawstack().pop());
            }
        } catch (CardByRulesNotAllowedException e) {
            getPlayer().giveHandCard(getDrawstack().pop());
            getPlayer().giveHandCard(card);
        }        
        pushToNextPlayerTurn();
        checkActivePlayersNextCardsForSeven();
    }
    /**
     * Returns a Map of players which are currently playing the game.
     * 
     * @return a map of players of type GameMauPlayer.
     */
    public Map<Integer, GameMauPlayer> getPlayers() {
        return this.players;
    }
    /**
     * Initialise the Middle-Stack for playing and pop
     * the first card of drawstack to middlestack.
     */
    public void popFirstCardToMiddleStack() {
        getMiddlestack().add(getDrawstack().firstElement());
    }
    
    /**
     * Returns the initial cards from the drawstack.
     * 
     * @return a list of initial cards from drawstack to a player.
     */
    List<SkatCard> getPlayersInitialCards() {
        List<SkatCard> listCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listCards.add(getDrawstack().pop());
        }
        return listCards;
    }
    /**
     * Initialise and shuffles the drawstack for play.
     */
    private void initialiseDrawStack() {
        for (EnumSkatColor color : EnumSkatColor.values()) {
            for (EnumSkatValue value : EnumSkatValue.values()) {
                this.drawstack.add(new SkatCard(color, value));
            }
        }
        Collections.shuffle(this.drawstack);
    }
    /**
     * On a running game the next player will be pushed as active player.
     * If ace-rule is touched, the next player will be skipped.
     */
    private void pushToNextPlayerTurn() {
        Integer idx = 0;
        for (HashMap.Entry<Integer, GameMauPlayer> entry : getPlayers().entrySet()) {
            if (entry.getValue().equals(getPlayer())) {
                idx = entry.getKey();
            }
        }
        // Standard
        if (idx == getPlayers().size()) {
            idx = 1;
        } else {
            idx++;
        }
        // Ace-Rule
        if (isNextplayerstay()) {
            if (idx == getPlayers().size()) {
                idx = 1;
            } else {
                idx++;
            }
            this.playerstay = false;
        }
        this.player = getPlayers().get(idx);
    }
    /**
     * Checks a given SkatCard to the last middlestack card and the rules
     * of the mau game.
     * Throws an exception if a rule is violated.
     * 
     * @param playerCard usually the player card to check for rule-violations.
     * @throws CardByRulesNotAllowedException if a rule is violated by given card.
     */
    void checkRulesForCard(SkatCard playerCard) throws CardByRulesNotAllowedException {
        SkatCard stackCard = getMiddlestack().peek();
        boolean ruleBreak = false;
        // Standard-Rules
        if (!stackCard.getColor().equals(playerCard.getColor())
                && !stackCard.getValue().equals(playerCard.getValue())
                && !playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        // Color-Chooser Rule
        if (choosedcolor != null && !choosedcolor.equals(playerCard.getColor())) {
            ruleBreak = true;
        } else if (choosedcolor != null
                && choosedcolor.equals(playerCard.getColor())) {
            ruleBreak = false;
            choosedcolor = null;
        }
        // Jack on Jack Rule
        if (stackCard.getValue().equals(EnumSkatValue.JACK)
                && playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        if (!ruleBreak) {
            if (playerCard.getValue().equals(EnumSkatValue.ACE)) {
                playerstay = true;
            }
        } else {
            throw new CardByRulesNotAllowedException();
        }
    }
    /**
     * Returns the skatcard color which was choosed by a player.
     *
     * @return the choosed color of a player.
     */
    public EnumSkatColor getChoosedcolor() {
        return this.choosedcolor;
    }
    /**
     * If player doesn't have a seven then he draw the extra cards.
     * This method checks active players cards and give new handcards 
     * if there is no seven on hand.
     */
    void checkActivePlayersNextCardsForSeven() {
        if (checkPlayerHasCard(EnumSkatValue.SEVEN)) {
            return;
        }
        int size = this.extracards.size();
        for(int i = 0; i < size ; i++) {
            getPlayer().giveHandCard(this.extracards.pop());
        }
    }
    /**
     * Returns true if next player have to stay because an ace was thrown.
     * 
     * @return true if next player will be skipped, otherwise false. 
     */
    public boolean isNextplayerstay() {
        return this.playerstay;
    }
    /**
     * Checks if a player has a value card on his hand. The card
     * to check is given as parameter.
     *
     * @param enumSkatValue the card value to check.
     * @return true if value is on players hand, false otherwise. 
     */
    boolean checkPlayerHasCard(EnumSkatValue enumSkatValue) {
        for (SkatCard skatCard : getPlayer().getHandCards()) {
            if (skatCard.getValue().equals(enumSkatValue)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns the current game state.
     *
     * @return true if game is running, false otherwise.
     */
    public boolean isRunning() {
        return this.running;
    }
    /**
     * Set the active game state with given parameter.
     *
     * @param b the active game state as boolean.
     */
    private void setRunning(boolean b) {
        this.running = b;
    }
    /**
     * Starts the mau game with initialising the middlestack
     * and switching the game state to running.
     */
    public void start() {
        popFirstCardToMiddleStack();
        this.running = true;
    }
    /**
     * Returns the winner of the mau game as GameMauPlayer.
     * 
     * @return the winner of the mau game as GameMauPlayer.
     */
    public GameMauPlayer getWinner() {
        return this.winner;
    }
    /**
     * Pushes the middlestack to the drawstack, except for the card on top, 
     * and shuffled all to fill the drawstack.
     */
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
