/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * The test game class for the tests of the snake game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
@SuppressWarnings("PMD.TooManyMethods")
public class TestSnakeKeyListener {

    /**
     * A static update value for test-cases with updated values.
     */
    public static final int UPDATE_VALUE = 50;

    /**
     * A static move size value for test-cases a moving snake.
     */
    public static final double MOVE_SIZE = 5;

    /**
     * The class under test.
     */
    private final SnakeKeyListener cut;

    /**
     * The snacke as mock.
     */
    private final Snake mock;

    /**
     * The constructor to initilize the final members of the test class.
     */
    public TestSnakeKeyListener() {
        this.mock = Mockito.mock(Snake.class);
        this.cut = new SnakeKeyListener(this.mock);
    }

    /**
     * Test should ckeck if the listener call up method of snake after
     * pressing the up button.
     */
    @Test
    public final void listenerShouldCallUpMethodOfSnakeAfterPressingUpKey() {
        this.cut.keyTyped(
            new KeyEvent(
                new JPanel(),
                1,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UP,
                'u'
            )
        );
        Mockito.verify(
            this.mock,
            Mockito.atLeastOnce()
        ).moveUp(org.mockito.Matchers.anyDouble());
    }

    /**
     * Test should ckeck if the listener call down method of snake after
     * pressing the down button.
     */
    @Test
    public final void
        listenerShouldCallDownMethodOfSnakeAfterPressingDownKey() {
        this.cut.keyTyped(
            new KeyEvent(
                new JPanel(),
                1,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_DOWN,
                'd'
            )
        );
        Mockito.verify(
            this.mock,
            Mockito.atLeastOnce()
        ).moveDown(org.mockito.Matchers.anyDouble());
    }

    /**
     * Test should ckeck if the listener call left method of snake after
     * pressing the left button.
     */
    @Test
    public final void
        listenerShouldCallLeftMethodOfSnakeAfterPressingLeftKey() {
        this.cut.keyTyped(
            new KeyEvent(
                new JPanel(),
                1,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_LEFT,
                'l'
            )
        );
        Mockito.verify(
            this.mock,
            Mockito.atLeastOnce()
        ).moveLeft(org.mockito.Matchers.anyDouble());
    }

    /**
     * Test should ckeck if the listener call right method of snake after
     * pressing the right button.
     */
    @Test
    public final void
        listenerShouldCallRightMethodOfSnakeAfterPressingRightKey() {
        this.cut.keyTyped(
            new KeyEvent(
                new JPanel(),
                1,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_RIGHT,
                'r'
            )
        );
        Mockito.verify(
            this.mock,
            Mockito.atLeastOnce()
        ).moveRight(org.mockito.Matchers.anyDouble());
    }

}
