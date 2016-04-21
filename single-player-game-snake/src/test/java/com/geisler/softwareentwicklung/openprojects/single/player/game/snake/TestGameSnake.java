/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JPanel;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * The test game class for the tests of the snake game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public class TestGameSnake {

    /**
     * The snacke as mock.
     */
    private final Snake mock;

    /**
     * The class under test.
     */
    private final GameSnake cut;

    /**
     * The constructor to initilize the final members of the test class.
     */
    public TestGameSnake() {
        this.mock = Mockito.mock(Snake.class);
        this.cut = new GameSnake(this.mock);
    }

    /**
     * Test should ckeck if the game is in a running state after starting it.
     */
    @Test
    public final void gameSnakeShouldBeRunningIfGameIsStarted() {
        this.cut.startGame();
        MatcherAssert.assertThat(this.cut.isRunning(), Matchers.is(true));
    }

    /**
     * Test should ckeck if the game is not in a running state before starting
     * it.
     */
    @Test
    public final void gameSnakeShouldNotBeRunningIfGameIsNotStarted() {
        MatcherAssert.assertThat(this.cut.isRunning(), Matchers.is(false));
    }

    /**
     * Test should ckeck if the game is not in a running state after stopping
     * it.
     */
    @Test
    public final void gameSnakeShouldNotBeRunningIfGameIsStopped() {
        this.cut.stopGame();
        MatcherAssert.assertThat(this.cut.isRunning(), Matchers.is(false));
    }

    /**
     * Test should ckeck if the game is a visual Component like JPanel and is
     * visible after starting it.
     */
    @Test
    public final void
        gameSnakeShouldBeAVisualComponentAndVisibleIfGameIsStarted() {
        this.cut.startGame();
        MatcherAssert.assertThat(this.cut, Matchers.instanceOf(JPanel.class));
        MatcherAssert.assertThat(this.cut.isVisible(), Matchers.is(true));
    }

    /**
     * Test should ckeck if the game has a visual Component as parent like
     * JFrame and is visible after starting it.
     */
    @Test
    public final void
        gameSnakeShouldHaveAVisualParentAndVisibleIfGameIsStarted() {
        this.cut.startGame();
        MatcherAssert.assertThat(
            this.cut.getParent(),
            Matchers.notNullValue()
        );
        MatcherAssert.assertThat(
            this.cut.getParent().isVisible(),
            Matchers.is(true)
        );
    }

    /**
     * Test should ckeck if the game has default size of the view after starting
     * it.
     */
    @Test
    public final void
        gameSnakeShouldHaveDefaultViewSizeOnStartupIfSizeNotSet() {
        this.cut.startGame();
        MatcherAssert.assertThat(
            this.cut.getSize().width,
            Matchers.is(GameSnake.DEFAULT_WIDTH)
        );
        MatcherAssert.assertThat(
            this.cut.getSize().height,
            Matchers.is(GameSnake.DEFAULT_HEIGHT)
        );
    }

    /**
     * Checks if the game is running in an own Thread to do the logic outside
     * the event-thread.
     */
    @Test
    public final void gameSnakeShouldBeRunnableForLogicDetail() {
        MatcherAssert.assertThat(this.cut, Matchers.instanceOf(Runnable.class));
    }

    /**
     * Test should ckeck if the game has a snake for playing after starting.
     */
    @Test
    public final void gameSnakeShouldHaveASnakeOnStartup() {
        this.cut.startGame();
        MatcherAssert.assertThat(
            this.cut.getSnake(),
            Matchers.instanceOf(Snake.class)
        );
    }

    /**
     * Test should ckeck if the game has drawed the existing snake after
     * starting.
     *
     * @throws InvocationTargetException An exception on failure.
     * @throws InterruptedException An exception on failure.
     */
    @Test
    public final void gameSnakeShouldDrawTheSnakeAfterStart()
        throws InvocationTargetException, InterruptedException {
        this.cut.startGame();
        new Thread(this.cut).start();
        Thread.sleep(GameSnake.DEFAULT_WIDTH);
        Mockito.verify(this.mock, Mockito.atLeastOnce())
            .drawComponent(org.mockito.Matchers.anyObject());
    }
}
