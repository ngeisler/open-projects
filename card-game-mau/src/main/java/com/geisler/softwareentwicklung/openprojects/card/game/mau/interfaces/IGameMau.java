/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau.interfaces;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMau;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMauPlayer;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.SkatCard;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.CardByRulesNotAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import java.util.List;
import java.util.Set;

/**
 * The main game interface to play the mau game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public interface IGameMau {

    /**
     * Starts the mau game with initialising the middlestack
     * and switching the game state to running.
     */
    public void start();

    /**
     * Returns true if the game is currently running. 
     *
     * @return boolean If game is running than true, otherwise false.
     */
    public boolean isGameRunning();

    /**
     * Returns true if the active player has given card value. 
     *
     * @param cardvalue The card to check with players cards.
     * @return boolean If player has given card value than true, otherwise false.
     */
    public boolean hasPlayerCardValue(EnumSkatValue cardvalue);

    /**
     * Returns true if the next player in game have to stay. 
     *
     * @return boolean If next player has to stay than true, otherwise false.
     */
    public boolean hasNextPlayerToStay();

    /**
     * Checks a given SkatCard to the last middlestack card and the rules
     * of the mau game.
     * Throws an exception if a rule is violated.
     *
     * @param playercard Usually the player card to check for rule-violations.
     * @throws CardByRulesNotAllowedException if a rule is violated by given card.
     */
    public void checkRulesForCard(SkatCard playercard) throws CardByRulesNotAllowedException;

    /**
     * If player doesn't have a seven then he draw the extra cards.
     * This method checks active players cards and give new handcards 
     * if there is no seven on hand.
     */
    public void checkActivePlayersNextCardsForSeven();

    /**
     * Adds a new player to the current game.
     * The player is given by the parameter.
     * An Exception is thrown if the max player size is reached.
     *
     * @param newplayer The new player given as a GameMauPlayer-instance.
     * @throws NoMorePlayersAllowedException if max size of players is reached.
     */
    public void addNewPlayerToGame(GameMauPlayer newplayer) throws NoMorePlayersAllowedException;

    /**
     * Get the size of the middlestack as int value.
     *
     * @return int The middlestach size.
     */
    public int getMiddleStackSize();

    /**
     * Get the card on top of the middlestack.
     *
     * @return SkatCard The card on top of the middlestack.
     */
    public SkatCard getMiddleStackCardOnTop();

    /**
     * Throws a card from active Player with given index. If no index is passed
     * the first card of players hand will be choosed.
     * Could throw an Exception if card is not allowed by the rules.
     *
     * @param cardindex The index of the choosed player card.
     * @throws CardByRulesNotAllowedException if card is not allowed by rules.
     */
    public void throwPlayerCardToMiddleStack(Integer cardindex)
        throws CardByRulesNotAllowedException;

    /**
     * Throws a card to middlestack from given parameter. Card will not
     * be checked for rules or other things.
     *
     * @param card The card to throw to middlestack.
     */
    public void throwUncheckedCardToMiddleStack(final SkatCard card);

    /**
     * Returns the hand size of the active player.
     *
     * @return int The active players hand size.
     */
    public int getPlayersHandSize();

    /**
     * Returns the name of the active player.
     *
     * @return String The active players name.
     */
    public String getPlayersName();

    /**
     * Returns the hand size of the player with given id.
     *
     * @param id The id of the player in game.
     * @return int The hand size of the player with given id.
     */
    public int getPlayerHandSizeWithId(Integer id);

    /**
     * Returns the name of the player with given id.
     *
     * @param id The id of the player in game.
     * @return String The name of the player with given id.
     */
    public String getPlayerNameWithId(Integer id);

    /**
     * Returns the hand cards of the player with given id as an instance of
     * type List.
     *
     * @param id The id of the player in game.
     * @return List The hand cards of the player with given id.
     */
    public List<SkatCard> getPlayerHandCardsWithId(Integer id);

    /**
     * Returns the ids of all players in the game.
     *
     * @return Integer The player ids of the game.
     */
    public Set<Integer> getPlayerIds();

    /**
     * Returns the skatcard color which was choosed by a player.
     *
     * @return the choosed color of the game if a player choosed one.
     */
    public EnumSkatColor getChoosedGameColor();

    /**
     * Returns an amount of cards from the drawstack which is given ba param.
     *
     * @param numberofcards The count of the number of cards to pop.
     * @return A list of cards from drawstack.
     */
    public List<SkatCard> popCardsFromStackByAmount
        (int numberofcards);

    /**
     * Returns the name of the game winner player.
     *
     * @return String The game winner players name.
     */
    public String getWinnersName();
}
