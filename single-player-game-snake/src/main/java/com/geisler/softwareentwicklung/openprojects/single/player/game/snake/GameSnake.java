/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

/**
 * The main class for the game snake.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
class GameSnake {

    /**
     * The running state of the game as boolean type.
     */
    private boolean running;

    /**
     * Starts the game snake.
     */
    public void startGame() {
        this.running = true;
    }

    /**
     * Stops the game snake.
     */
    public void stopGame() {
        this.running = false;
    }

    /**
     * Returns the running state of the game snake.
     *
     * @return Returns true if the game is running, otherwise false.
     */
    public boolean isRunning() {
        return this.running;
    }

}
