/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.CardByRulesNotAllowedException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
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
        int size = spyGame.getMiddlestack().size();
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyGame.getMiddlestack().size(), is(size+1));
    }
    
    @Test
    public void whenAPlayerThrowsAHandCardThenThePlayerHasOneHandCardLess() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);       

        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        spyGame.popFirstCardToMiddleStack();
        int size = spyGame.getPlayer().getHandSize();
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyGame.getPlayer().getHandSize(), is(size-1));
    }
    
    @Test
    public void whenAGameIsRunningThenTheStackHaveAtLeastOneCard() {
        // Arrange Act Assert
        GameMau game = new GameMau();
        game.popFirstCardToMiddleStack();
        assertThat(game.getMiddlestack().size(), is(greaterThan(0)));
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
        String playerNameOne = spyGame.getPlayer().getName();
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(playerNameOne, is(not(spyGame.getPlayer().getName())));
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
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
                
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyPlayerOne.getHandSize(), is(handsize+2));
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
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.JACK));
                
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyPlayerOne.getHandSize(), is(handsize+2));
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
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.JACK));
                
        spyGame.throwPlayerCardToMiddleStack(null);
        
        assertThat(spyPlayerOne.getHandSize(), is(handsize+2));
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
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
                
        spyGame.throwPlayerCardToMiddleStack(null);
        
        assertThat(spyGame.getChoosedcolor(), is(not(nullValue())));
    }
    
    @Test
    public void whenAPlayerThrowsRightColorAfterChoosingThenChoosedColorIsNull() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        
        when(spyPlayerOne.getSelectedHandCardToThrow(anyInt()))
                .thenReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
                .thenReturn(new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.SEVEN));
        
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
        doReturn(EnumSkatColor.DIAMONDS).when(spyPlayerOne).getSelectedColor();
        
        // throw Jack
        spyGame.throwPlayerCardToMiddleStack(null);
        
        // throw right Color
        spyGame.throwPlayerCardToMiddleStack(null);
        
        assertThat(spyGame.getChoosedcolor(), is(nullValue()));
        
    }
    
    @Test
    public void whenAPlayerThrowsValueSevenThenNextPlayerHaveToDrawTwoExtraCards() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        List<SkatCard> cards = Arrays.asList(
                new SkatCard[]{
                    new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.EIGHT),
                    new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.EIGHT),
                    new SkatCard(EnumSkatColor.SPADES, EnumSkatValue.EIGHT),
                    new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.NINE),
                    new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.NINE)});
        when(spyGame.getPlayersInitialCards()).thenReturn(cards).thenCallRealMethod();

        spyGame.addNewPlayerToGame(spyPlayerOne);
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        
        
        when(spyPlayerTwo.getSelectedHandCardToThrow(anyInt()))
                .thenReturn(new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.SEVEN));
        
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.EIGHT));
                
        spyGame.throwPlayerCardToMiddleStack(null);
        
        assertThat(spyGame.getPlayer().getHandSize(), is(7));
        
    }
    
    @Test
    public void whenAPlayerHaveToDrawTwoExtraCardsThenAnotherValueSevenCardCanRedirectWithTwoExtraCardsToNextPlayer() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        
        when(spyPlayerTwo.getSelectedHandCardToThrow(anyInt()))
                .thenReturn(new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.SEVEN));
        
        when(spyPlayerOne.getSelectedHandCardToThrow(anyInt()))
                .thenReturn(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
        
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.EIGHT));
        
        when(spyGame.isNextplayerstay()).thenReturn(Boolean.FALSE);
        
        doReturn(true).when(spyGame).checkPlayerHasCard(any());
        
        spyGame.throwPlayerCardToMiddleStack(null);
        
        doReturn(false).when(spyGame).checkPlayerHasCard(any());
                
        spyGame.throwPlayerCardToMiddleStack(null);
        
        assertThat(spyGame.getPlayer().getHandSize(), is(8));
        
    }
    
    @Test
    public void whenAPlayerThrowsAnAceValueThenNextPlayerHasToStay() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.ACE))
                .when(spyPlayerTwo).getSelectedHandCardToThrow(anyInt());
        spyGame.getMiddlestack().push(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.EIGHT));
                
        spyGame.throwPlayerCardToMiddleStack(null);
        
        // so the next player should be player two again
        assertThat(spyGame.getPlayer().getName(), is("two"));
    }
    
    @Test
    public void whenAllPlayersHaveMinimumOneCardThenTheGameIsNotOver() throws NoMorePlayersAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);
        
        spyGame.popFirstCardToMiddleStack();
        
        spyGame.start();
        
        assertThat(spyGame.isRunning(), is(true));
    }    
    
    @Test
    public void whenOnePlayersHaveNoCardsLeftAfterTurnThenTheGameIsOver() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);
        
        spyGame.popFirstCardToMiddleStack();
        doNothing().when(spyGame).checkRulesForCard(any());
        doNothing().when(spyGame).checkActivePlayersNextCardsForSeven();
        doReturn(false).when(spyGame).isNextplayerstay();
        
        for(int i = 0; i < 9; i++) {
            spyGame.throwPlayerCardToMiddleStack(null);
        }
        
        assertThat(spyGame.isRunning(), is(false));
    }
    
    @Test
    public void whenAPlayerHasNoHandCardsThenItIsTheWinnerOfTheGame() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);
        
        spyGame.popFirstCardToMiddleStack();
        doNothing().when(spyGame).checkRulesForCard(any());
        doNothing().when(spyGame).checkActivePlayersNextCardsForSeven();
        doReturn(false).when(spyGame).isNextplayerstay();
        
        for(int i = 0; i < 9; i++) {
            spyGame.throwPlayerCardToMiddleStack(null);
        }
        
        assertThat(spyGame.getWinner().getName(), is("one"));
    }
    
    @Test
    public void whenDrawStackGetsEmptyThenMiddleStackHaveToBeShuffledAndSwitched() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        GameMau game = new GameMau();
        GameMau spyGame = spy(game);        
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        GameMauPlayer spyPlayerThree = spy(new GameMauPlayer("three"));
        GameMauPlayer spyPlayerFour = spy(new GameMauPlayer("four"));
        
        spyGame.addNewPlayerToGame(spyPlayerFour);
        spyGame.addNewPlayerToGame(spyPlayerThree);
        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);
        
        spyGame.popFirstCardToMiddleStack();
        doNothing().when(spyGame).checkRulesForCard(any());
        doNothing().when(spyGame).checkActivePlayersNextCardsForSeven();
        doReturn(false).when(spyGame).isNextplayerstay();
        
        for(int i = 0; i < 9; i++) {
            spyGame.throwPlayerCardToMiddleStack(null);
        }
        Stack<SkatCard> checkStack = new Stack();
        checkStack.setSize(spyGame.getMiddlestack().size());
        Collections.copy(checkStack, spyGame.getMiddlestack());
        spyGame.getDrawstack().removeAllElements();
        
        spyGame.getDrawstack().pop();
        
        assertThat(spyGame.getDrawstack(), hasItem(checkStack.get(1)));
    }

}
