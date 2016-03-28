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
import com.geisler.softwareentwicklung.openprojects.card.game.mau.interfaces.IGameMau;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * The main game class to play the mau game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public class GameMau implements IGameMau {
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
    private GameMau() {
        this.players = new HashMap<>();
        this.drawstack = new Stack();
        this.initialiseDrawStack();
        this.middlestack = new Stack<>();
        this.extracards = new Stack<>();
        this.choosedcolor = null;
        this.winner = null;
    }

    /**
     * The standard constructor for a maugame. Most parts here are to initialise
     * the stacks and maps for later acting.
     *
     * @param playernames A List of playernames for the game.
     * @throws NoMorePlayersAllowedException If more than 4 players added.
     */
    public GameMau(final List<String> playernames)
        throws NoMorePlayersAllowedException {
        this();
        for (final String playername : playernames) {
            final GameMauPlayer gameplayer = new GameMauPlayer(playername);
            this.addNewPlayerToCurrentGame(gameplayer);
        }
    }

    /**
     * Returns an instance of GameMau with defined options and initializations.
     *
     * @return GameMau An instance of GameMau.
     */
    public static IGameMau getInstanceOfGameMau() {
        return new GameMau();
    }

    @Override
    public final void addNewPlayerToGame(final GameMauPlayer newplayer)
        throws NoMorePlayersAllowedException {
        this.addNewPlayerToCurrentGame(newplayer);
    }

    @Override
    public final int getMiddleStackSize() {
        return this.getMiddlestack().size();
    }

    @Override
    public final int getPlayersHandSize() {
        return this.getPlayer().getHandSize();
    }

    @Override
    public final String getPlayersName() {
        return this.getPlayer().getName();
    }

    @Override
    public final int getPlayerHandSizeWithId(final Integer id) {
        return this.getPlayers().get(id).getHandSize();
    }

    @Override
    public final String getPlayerNameWithId(final Integer id) {
        return this.getPlayers().get(id).getName();
    }

    @Override
    public final List<SkatCard> getPlayerHandCardsWithId(final Integer id) {
        return this.getPlayers().get(id).getHandCards();
    }

    @Override
    public final SkatCard getMiddleStackCardOnTop() {
        return this.getMiddlestack().peek();
    }

    @Override
    public final Set<Integer> getPlayerIds() {
        return this.getPlayers().keySet();
    }

    @Override
    public void checkRulesForCard(final SkatCard playercard)
        throws CardByRulesNotAllowedException {
        checkGameRules(playercard);
    }

    @Override
    public void checkActivePlayersNextCardsForSeven() {
        this.checkRuleSeven();
    }

    @Override
    public final void throwUncheckedCardToMiddleStack(final SkatCard card) {
        this.getMiddlestack().push(card);
    }

    @Override
    public void throwPlayerCardToMiddleStack(final Integer cardindex)
        throws CardByRulesNotAllowedException {
        Integer handindex = 0;
        if (cardindex != null) {
            handindex = cardindex;
        }
        SkatCard card = getPlayer().getSelectedHandCardToThrow(handindex);
        try {
            checkRulesForCard(card);
            if (card.getValue().equals(EnumSkatValue.JACK) && getChoosedGameColor() == null) {
                this.choosedcolor = getPlayer().getSelectedColor();
            }
            getMiddlestack().push(card);
            if (getPlayersHandSize() < 1) {
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

    @Override
    public EnumSkatColor getChoosedGameColor() {
        return this.choosedcolor;
    }

    @Override
    public boolean hasNextPlayerToStay() {
        return this.playerstay;
    }

    @Override
    public boolean isGameRunning() {
        return this.running;
    }

    @Override
    public void start() {
        this.popFirstCardToMiddleStack();
        this.running = true;
    }

    @Override
    public List<SkatCard> popCardsFromStackByAmount(final int numberofcards) {
        List<SkatCard> listCards = new ArrayList<>();
        for (int i = 0; i < numberofcards; i++) {
            listCards.add(this.getDrawstack().pop());
        }
        return listCards;
    }

    @Override
    public String getWinnersName() {
        return this.winner.getName();
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
     * Returns a Map of players which are currently playing the game.
     * 
     * @return a map of players of type GameMauPlayer.
     */
    public final Map<Integer, GameMauPlayer> getPlayers() {
        return this.players;
    }

    /**
     * Initialise the Middle-Stack for playing and pop
     * the first card of drawstack to middlestack.
     */
    public final void popFirstCardToMiddleStack() {
        this.getMiddlestack().add(getDrawstack().firstElement());
    }

    /**
     * Returns the drawstack of the game. If the drawstack is empty the
     * middlestack will be shuffled an placed to the drawstack.
     *
     * @return The current drawstack with type Stack
     */
    final Stack<SkatCard> getDrawstack() {
        if (this.drawstack.isEmpty()) {
            this.pushMiddleStackToDrawStackShuffled();
        }
        return this.drawstack;
    }

    @Override
    public boolean hasPlayerCardValue(final EnumSkatValue cardvalue) {
        for (SkatCard skatCard : getPlayer().getHandCards()) {
            if (skatCard.getValue().equals(cardvalue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Pushes the middlestack to the drawstack, except for the card on top, 
     * and shuffled all to fill the drawstack.
     */
    final void pushMiddleStackToDrawStackShuffled() {
        SkatCard topMiddleStackCard = getMiddlestack().pop();
        
        while(!getMiddlestack().isEmpty()) {
            drawstack.push(getMiddlestack().pop());
        }
        // return CardOnTop to MiddleStack
        getMiddlestack().push(topMiddleStackCard);
        
        // Shuffle drawstack
        Collections.shuffle(drawstack);
    }

    /**
     * Returns the current middlestack of the running game.
     *
     * @return The current middlestack of type Stack
     */
    private Stack<SkatCard> getMiddlestack() {
        return this.middlestack;
    }

    /**
     * Checks a given SkatCard to the last middlestack card and the rules
     * of the mau game.
     * Throws an exception if a rule is violated.
     *
     * @param playercard Usually the player card to check for rule-violations.
     * @throws CardByRulesNotAllowedException if a rule is violated by given card.
     */
    private void checkGameRules(final SkatCard playercard) throws CardByRulesNotAllowedException {
        SkatCard stackcard = this.getMiddlestack().peek();
        boolean rulebreak = false;
        if (stackcard == null || playercard == null) {
            throw new CardByRulesNotAllowedException();
        }
        // Standard-Rules
        if (!stackcard.getColor().equals(playercard.getColor())
                && !stackcard.getValue().equals(playercard.getValue())
                && !playercard.getValue().equals(EnumSkatValue.JACK)) {
            rulebreak = true;
        }
        // Color-Chooser Rule
        if (this.choosedcolor != null && !this.choosedcolor.equals(playercard.getColor())) {
            rulebreak = true;
        } else if (this.choosedcolor != null
                && this.choosedcolor.equals(playercard.getColor())) {
            rulebreak = false;
            this.choosedcolor = null;
        }
        // Jack on Jack Rule
        if (stackcard.getValue().equals(EnumSkatValue.JACK)
                && playercard.getValue().equals(EnumSkatValue.JACK)) {
            rulebreak = true;
        }
        if (!rulebreak) {
            if (playercard.getValue().equals(EnumSkatValue.ACE)) {
                this.playerstay = true;
            }
        } else {
            throw new CardByRulesNotAllowedException();
        }
    }

    /**
     * Adds a new player to the current game.
     * The player is given by the parameter.
     * An Exception is thrown if the max player size is reached.
     *
     * @param newplayer The new player given as a GameMauPlayer-instance.
     * @throws NoMorePlayersAllowedException if max size of players is reached.
     */
    private void addNewPlayerToCurrentGame(final GameMauPlayer newplayer)
        throws NoMorePlayersAllowedException {
        if (this.getPlayers().size() == MAX_PLAYERS) {
            throw new NoMorePlayersAllowedException();
        }
        final List<SkatCard> handcards = this.popCardsFromStackByAmount(5);
        newplayer.giveHandCards(handcards);
        this.player = newplayer;
        this.getPlayers().put(this.getPlayers().size() + 1, newplayer);
    }
    /**
     * If player doesn't have a seven then he draw the extra cards.
     * This method checks active players cards and give new handcards 
     * if there is no seven on hand.
     */
    private void checkRuleSeven() {
        if (this.hasPlayerCardValue(EnumSkatValue.SEVEN)) {
            return;
        }
        int size = this.extracards.size();
        for(int i = 0; i < size ; i++) {
            getPlayer().giveHandCard(this.extracards.pop());
        }
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
        for (HashMap.Entry<Integer, GameMauPlayer> entry : this.getPlayers().entrySet()) {
            if (entry.getValue().equals(this.getPlayer())) {
                idx = entry.getKey();
            }
        }
        // Standard
        if (idx == this.getPlayers().size()) {
            idx = 1;
        } else {
            idx++;
        }
        // Ace-Rule
        if (this.hasNextPlayerToStay()) {
            if (idx == getPlayers().size()) {
                idx = 1;
            } else {
                idx++;
            }
            this.playerstay = false;
        }
        this.player = this.getPlayers().get(idx);
    }
    /**
     * Set the active game state with given parameter.
     *
     * @param b the active game state as boolean.
     */
    private void setRunning(final boolean b) {
        this.running = b;
    }
}
