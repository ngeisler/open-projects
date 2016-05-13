/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.CardByRulesNotAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.interfaces.IGameMau;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    public void whenAPlayerThrowsAHandCardThenTheMiddleStackHasOneCardMore() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);

        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        spyGame.start();
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        int size = spyGame.getMiddleStackSize();
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyGame.getMiddleStackSize(), is(size + 1));
    }

    @Test
    public void whenAPlayerThrowsAHandCardThenThePlayerHasOneHandCardLess() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);

        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        spyGame.start();
        int size = spyGame.getPlayersHandSize();
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyGame.getPlayersHandSize(), is(size - 1));
    }

    @Test
    public void whenAGameIsRunningThenTheStackHaveAtLeastOneCard() {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        game.start();
        assertThat(game.getMiddleStackSize(), is(greaterThan(0)));
    }

    @Test
    public void whenAGameStartsThenThePlayersHave5HandCards() throws
        NoMorePlayersAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        Set<Integer> ids = game.getPlayerIds();
        ids.stream().forEach((id) -> {
            assertThat(game.getPlayerHandSizeWithId(id), is(5));
        });
    }

    @Test
    public void whenAGameStartsThenMinimumTwoPlayersAreRequiredForTheGame() throws
        NoMorePlayersAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        Set<Integer> players = game.getPlayerIds();
        assertThat(players.size(), is(greaterThanOrEqualTo(2)));
    }

    @Test
    public void whenAGameStartsThenThePlayersHaveNotTheSameCards() throws
        NoMorePlayersAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        List<SkatCard> oneCards = game.getPlayerHandCardsWithId(1);
        List<SkatCard> twoCards = game.getPlayerHandCardsWithId(2);
        twoCards.stream().forEach((twoCard) -> {
            assertThat(oneCards, not(hasItem(twoCard)));
        });
    }

    @Test(expected = NoMorePlayersAllowedException.class)
    public void whenMoreThanFourPlayersBeAddedToGameThereWillBeAnError() throws
        NoMorePlayersAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        game.addNewPlayerToGame(new GameMauPlayer("one"));
        game.addNewPlayerToGame(new GameMauPlayer("two"));
        game.addNewPlayerToGame(new GameMauPlayer("three"));
        game.addNewPlayerToGame(new GameMauPlayer("four"));
        game.addNewPlayerToGame(new GameMauPlayer("five"));
    }

    @Test
    public void whenAPlayerThrowedACardThenTheNextPlayerIsOnTurnAndThatsNotTheSame() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);

        spyGame.addNewPlayerToGame(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(new GameMauPlayer("two"));
        spyGame.start();
        doNothing().when(spyGame).checkRulesForCard(anyObject());
        String playerNameOne = spyGame.getPlayersName();
        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(playerNameOne, is(not(spyGame.getPlayersName())));
    }

    @Test
    public void whenAPlayerThrowsACardThenTheLastMiddleStackCardShouldBeSameColorOrValue() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        int handsize = spyPlayerOne.getHandSize();
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.ACE))
            .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));

        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyPlayerOne.getHandSize(), is(handsize + 2));
    }

    @Test
    public void whenAPlayerThrowsAJackThenTheLastMiddleStackCardShouldNotBeAJack() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        int handsize = spyPlayerOne.getHandSize();
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
            .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.HEARTS, EnumSkatValue.JACK));

        spyGame.throwPlayerCardToMiddleStack(null);
        assertThat(spyPlayerOne.getHandSize(), is(handsize + 2));
    }

    @Test
    public void whenAPlayerCanNotThrowACardThenTheHandSizeOfThePlayerHasOneMore() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        int handsize = spyPlayerOne.getHandSize();
        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
            .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.HEARTS, EnumSkatValue.JACK));

        spyGame.throwPlayerCardToMiddleStack(null);

        assertThat(spyPlayerOne.getHandSize(), is(handsize + 2));
    }

    @Test
    public void whenAPlayerThrowAJackThenThisPlayerChooseTheNextColor() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);

        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
            .when(spyPlayerOne).getSelectedHandCardToThrow(anyInt());
        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));

        spyGame.throwPlayerCardToMiddleStack(null);

        assertThat(spyGame.getChoosedGameColor(), is(not(nullValue())));
    }

    @Test
    public void whenAPlayerThrowsRightColorAfterChoosingThenChoosedColorIsNull() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        spyGame.addNewPlayerToGame(spyPlayerOne);

        when(spyPlayerOne.getSelectedHandCardToThrow(anyInt()))
            .thenReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.JACK))
            .thenReturn(
                new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.SEVEN));

        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));
        doReturn(EnumSkatColor.DIAMONDS).when(spyPlayerOne).getSelectedColor();

        // throw Jack
        spyGame.throwPlayerCardToMiddleStack(null);

        // throw right Color
        spyGame.throwPlayerCardToMiddleStack(null);

        assertThat(spyGame.getChoosedGameColor(), is(nullValue()));

    }

    @Test
    public void whenAPlayerThrowsValueSevenThenNextPlayerHaveToDrawTwoExtraCards() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        List<SkatCard> cards = Arrays.asList(
            new SkatCard[]{
                new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.EIGHT),
                new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.EIGHT),
                new SkatCard(EnumSkatColor.SPADES, EnumSkatValue.EIGHT),
                new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.NINE),
                new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.NINE)});
        when(spyGame.popCardsFromStackByAmount(5)).thenReturn(cards)
            .thenCallRealMethod();

        spyGame.addNewPlayerToGame(spyPlayerOne);
        spyGame.addNewPlayerToGame(spyPlayerTwo);

        when(spyPlayerTwo.getSelectedHandCardToThrow(anyInt()))
            .thenReturn(
                new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.SEVEN));

        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.DIAMONDS, EnumSkatValue.EIGHT));

        spyGame.throwPlayerCardToMiddleStack(null);

        assertThat(spyGame.getPlayersHandSize(), is(7));

    }

    @Test
    public void whenAPlayerHaveToDrawTwoExtraCardsThenAnotherValueSevenCardCanRedirectWithTwoExtraCardsToNextPlayer() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        spyGame.addNewPlayerToGame(spyPlayerTwo);

        when(spyPlayerTwo.getSelectedHandCardToThrow(anyInt()))
            .thenReturn(
                new SkatCard(EnumSkatColor.DIAMONDS, EnumSkatValue.SEVEN));

        when(spyPlayerOne.getSelectedHandCardToThrow(anyInt()))
            .thenReturn(new SkatCard(EnumSkatColor.HEARTS, EnumSkatValue.SEVEN));

        spyGame.throwUncheckedCardToMiddleStack(new SkatCard(
            EnumSkatColor.DIAMONDS, EnumSkatValue.EIGHT));

        when(spyGame.hasNextPlayerToStay()).thenReturn(Boolean.FALSE);

        doReturn(true).when(spyGame).hasPlayerCardValue(any());

        spyGame.throwPlayerCardToMiddleStack(null);

        doReturn(false).when(spyGame).hasPlayerCardValue(any());

        spyGame.throwPlayerCardToMiddleStack(null);

        assertThat(spyGame.getPlayersHandSize(), is(8));

    }

    @Test
    public void whenAPlayerThrowsAnAceValueThenNextPlayerHasToStay() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));
        spyGame.addNewPlayerToGame(spyPlayerOne);
        spyGame.addNewPlayerToGame(spyPlayerTwo);

        doReturn(new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.ACE))
            .when(spyPlayerTwo).getSelectedHandCardToThrow(anyInt());
        spyGame.throwUncheckedCardToMiddleStack(
            new SkatCard(EnumSkatColor.CLUBS, EnumSkatValue.EIGHT));

        spyGame.throwPlayerCardToMiddleStack(null);

        // so the next player should be player two again
        assertThat(spyGame.getPlayersName(), is("two"));
    }

    @Test
    public void whenAllPlayersHaveMinimumOneCardThenTheGameIsNotOver() throws
        NoMorePlayersAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));

        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);

        spyGame.start();

        assertThat(spyGame.isGameRunning(), is(true));
    }

    @Test
    public void whenOnePlayersHaveNoCardsLeftAfterTurnThenTheGameIsOver() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));

        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);

        spyGame.start();
        doNothing().when(spyGame).checkRulesForCard(any());
        doNothing().when(spyGame).checkActivePlayersNextCardsForSeven();
        doReturn(false).when(spyGame).hasNextPlayerToStay();

        for (int i = 0; i < 9; i++) {
            spyGame.throwPlayerCardToMiddleStack(null);
        }

        assertThat(spyGame.isGameRunning(), is(false));
    }

    @Test
    public void whenAPlayerHasNoHandCardsThenItIsTheWinnerOfTheGame() throws
        NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        // Arrange Act Assert
        IGameMau game = GameMau.getInstanceOfGameMau();
        IGameMau spyGame = spy(game);
        GameMauPlayer spyPlayerOne = spy(new GameMauPlayer("one"));
        GameMauPlayer spyPlayerTwo = spy(new GameMauPlayer("two"));

        spyGame.addNewPlayerToGame(spyPlayerTwo);
        spyGame.addNewPlayerToGame(spyPlayerOne);

        spyGame.start();
        doNothing().when(spyGame).checkRulesForCard(any());
        doNothing().when(spyGame).checkActivePlayersNextCardsForSeven();
        doReturn(false).when(spyGame).hasNextPlayerToStay();

        for (int i = 0; i < 9; i++) {
            spyGame.throwPlayerCardToMiddleStack(null);
        }

        assertThat(spyGame.getWinnersName(), is("one"));
    }

    public void whenDrawStackIsEmptyThenMiddleStackShouldBeShuffledAndSwitched() {

    }

}
