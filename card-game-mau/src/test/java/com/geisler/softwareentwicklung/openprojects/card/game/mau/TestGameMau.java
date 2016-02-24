/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
/**
 *
 * @author ngeis
 */
public class TestGameMau {
    
    public TestGameMau() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void whenAPlayerThrowsAHandCardThenTheMiddleStackHasOneCardMore() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);       
        
        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        spyGame.popFirstCardToMiddleStack();
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        int size = spyGame.getMiddleStack().size();
        spyGame.throwPlayerCardToMiddleStack();
        assertThat(spyGame.getMiddleStack().size(), is(size+1));
    }
    
    @Test
    public void whenAPlayerThrowsAHandCardThenThePlayerHasOneHandCardLess() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);       

        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        spyGame.popFirstCardToMiddleStack();
        int size = spyGame.getActivePlayer().getHandSize();
        spyGame.throwPlayerCardToMiddleStack();
        assertThat(spyGame.getActivePlayer().getHandSize(), is(size-1));
    }
    
    @Test
    public void whenAGameIsRunningThenTheStackHaveAtLeastOneCard() {
        // Arrange Act Assert
        GameMau game = new GameMau();
        game.popFirstCardToMiddleStack();
        assertThat(game.getMiddleStack().size(), is(greaterThan(0)));
    }
    
    @Test
    public void whenAGameStartsThenThePlayersHave5HandCards() throws NoMorePlayersAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        HashMap<Integer, GameMauPlayer> players = game.getPlayers();
        players.values().stream().forEach((player) -> {
            assertThat(player.getHandSize(), is(5));
        });
    }
    
    @Test
    public void whenAGameStartsThenMinimumTwoPlayersAreRequiredForTheGame() throws NoMorePlayersAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        HashMap<Integer, GameMauPlayer> players = game.getPlayers();
        assertThat(players.size(), is(greaterThanOrEqualTo(2)));
    }
    
    @Test
    public void whenAGameStartsThenThePlayersHaveNotTheSameCards() throws NoMorePlayersAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        List<SkatCard> oneCards = game.getPlayers().get(1).getHandCards();
        List<SkatCard> twoCards = game.getPlayers().get(2).getHandCards();
        twoCards.stream().forEach((twoCard) -> {
            assertThat(oneCards, not(hasItem(twoCard)));
        });
    }
    
    @Test(expected = NoMorePlayersAllowedException.class)
    public void whenMoreThanFourPlayersBeAddedToGameThereWillBeAnError() throws NoMorePlayersAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        game.addNewPlayerToGame(new GameMauPlayer("three"));
        game.addNewPlayerToGame(new GameMauPlayer("four"));
        game.addNewPlayerToGame(new GameMauPlayer("five"));
    }
    
    @Test
    public void whenAPlayerThrowedACardThenTheNextPlayerIsOnTurnAndThatsNotTheSame() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game); 
        
        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(new GameMauPlayer("two"));
        spyGame.popFirstCardToMiddleStack();
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        String playerNameOne = spyGame.getActivePlayer().getName();
        spyGame.throwPlayerCardToMiddleStack();
        assertThat(playerNameOne, is(not(spyGame.getActivePlayer().getName())));
    }
    
    @Test
    public void whenAPlayerThrowsACardThenTheLastMiddleStackCardShouldBeSameColorOrValue() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        int handsize = spyPlayerOne.getHandSize();
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.ACE))
                .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.getMiddleStack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
                
        spyGame.throwPlayerCardToMiddleStack();
        assertThat(spyPlayerOne.getHandSize(), is(handsize+1));
    }
    
    @Test
    public void whenAPlayerThrowsAJackThenTheLastMiddleStackCardShouldNotBeAJack() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        int handsize = spyPlayerOne.getHandSize();
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
                .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.getMiddleStack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.JACK));
                
        spyGame.throwPlayerCardToMiddleStack();
        assertThat(spyPlayerOne.getHandSize(), is(handsize+1));
    }
    
    @Test
    public void whenAPlayerCanNotThrowACardThenTheHandSizeOfThePlayerHasOneMore() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        int handsize = spyPlayerOne.getHandSize();
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
                .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.getMiddleStack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.JACK));
                
        spyGame.throwPlayerCardToMiddleStack();
        
        assertThat(spyPlayerOne.getHandSize(), is(handsize+1));
    }
    
    @Test
    public void whenAPlayerThrowAJackThenThisPlayerChooseTheNextColor() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
                .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.getMiddleStack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
                
        spyGame.throwPlayerCardToMiddleStack();
        
        assertThat(spyGame.getChoosedColor(), is(not(nullValue())));
    }
}
