package com.geisler.softwareentwicklung.openprojects.card.game.mau;

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
    private HashMap<Integer, GameMauPlayer> players;
    
    public GameMau() {
        players = new HashMap<>();
        drawStack = new Stack();
        initialiseDrawStack();
        middleStack = new Stack<>();
        choosedColor = null;
    }
    /**
     * Adds a new Player with given name to the Mau-Game
     * 
     * @param playerName the name of the new player
     * @param newPlayer the new player
     */
    void addNewPlayerToGame(GameMauPlayer newPlayer) throws NoMorePlayersAllowedException {
        if(this.players.size() == 4) {
            throw new NoMorePlayersAllowedException();
        }
        List<SkatCard> listHandCards = getPlayersInitialCards();
        newPlayer.giveHandCards(listHandCards);
        activePlayer = newPlayer;
        this.players.put(this.players.size() + 1, newPlayer);
    }
    
    GameMauPlayer getActivePlayer() {
        return activePlayer;
    }

    Stack<SkatCard> getMiddleStack() {
        return middleStack;
    }

    Stack<SkatCard> getDrawStack() {
        return drawStack;
    }
    
    void throwPlayerCardToMiddleStack() throws CardByRulesNotAllowedException {
        // TODO: Ask active player for card
        SkatCard card = getActivePlayer().getSelectedHandCardToThrow(0);
        try {
            checkRulesForCard(card);
            if(card.getValue().equals(EnumSkatValue.JACK)) {
                choosedColor = getActivePlayer().getSelectedColor();
            }
            middleStack.push(card);
        } catch (CardByRulesNotAllowedException e) {
            getActivePlayer().giveHandCard(drawStack.pop());
        }        
        pushToNextPlayerTurn();
    }

    HashMap<Integer, GameMauPlayer> getPlayers() {
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
    private List<SkatCard> getPlayersInitialCards() {
        List<SkatCard> listCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listCards.add(drawStack.pop());
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
        if(idx == players.size()) {
            idx = 1;
        } else {
            idx++;
        }
        activePlayer = players.get(idx);
    }

    void checkRulesForCard(SkatCard playerCard) throws CardByRulesNotAllowedException {
        SkatCard stackCard = getMiddleStack().peek();
        boolean ruleBreak = false;
        if(!stackCard.getColor().equals(playerCard.getColor())
                && !stackCard.getValue().equals(playerCard.getValue())
                && !playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        if(stackCard.getValue().equals(EnumSkatValue.JACK)
                && playerCard.getValue().equals(EnumSkatValue.JACK)) {
            ruleBreak = true;
        }
        if(!ruleBreak) {
            
        } else {
            throw new CardByRulesNotAllowedException();
        }
    }

    EnumSkatColor getChoosedColor() {
        return choosedColor;
    }
}
