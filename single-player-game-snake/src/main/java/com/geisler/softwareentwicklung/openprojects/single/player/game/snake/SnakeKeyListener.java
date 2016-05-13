/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyListener for the snake game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
class SnakeKeyListener implements KeyListener {

    /**
     * A static Not supported literal to avoid duplication in code.
     */
    private static final String NOT_SUPPORTED = "Not supported yet.";

    /**
     * The gamesnake of the running game.
     */
    private final transient Snake snake;

    /**
     * The constructor for the key listener with a given snake.
     * @param gamesnake The snake that was created by game controll.
     */
    SnakeKeyListener(final Snake gamesnake) {
        this.snake = gamesnake;
    }

    @Override
    public void keyPressed(final KeyEvent pressevent) {
        throw new UnsupportedOperationException(SnakeKeyListener.NOT_SUPPORTED);
    }

    @Override
    public void keyReleased(final KeyEvent releaseevent) {
        throw new UnsupportedOperationException(SnakeKeyListener.NOT_SUPPORTED);
    }

    @Override
    public void keyTyped(final KeyEvent typedevent) {
        if (typedevent.getKeyCode() > 0) {
            switch (typedevent.getKeyCode()) {
                case KeyEvent.VK_UP:
                    this.snake.moveUp(Snake.SNAKE_STEP_SIZE);
                    break;
                case KeyEvent.VK_DOWN:
                    this.snake.moveDown(Snake.SNAKE_STEP_SIZE);
                    break;
                case KeyEvent.VK_LEFT:
                    this.snake.moveLeft(Snake.SNAKE_STEP_SIZE);
                    break;
                case KeyEvent.VK_RIGHT:
                    this.snake.moveRight(Snake.SNAKE_STEP_SIZE);
                    break;
                default:
                    break;
            }
        }
    }

}
