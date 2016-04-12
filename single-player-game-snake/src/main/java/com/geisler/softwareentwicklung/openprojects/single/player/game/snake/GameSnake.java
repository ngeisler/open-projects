/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The main class for the game snake.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
class GameSnake extends JPanel {

    /**
     * Default width of the game snake view.
     */
    public static final int DEFAULT_WIDTH = 800;

    /**
     * Default width diff of the game frame.
     */
    public static final int WIN_WIDTH_DIFF = 16;

    /**
     * Default height of the game snake view.
     */
    public static final int DEFAULT_HEIGHT = 600;

    /**
     * Default height diff of the game frame.
     */
    public static final int WIN_HEIGHT_DIFF = 39;

    /**
     * The running state of the game as boolean type.
     */
    private boolean running;

    /**
     * The snake of the game as Snake type.
     */
    private transient Snake snake;

    /**
     * Starts the game snake.
     */
    public void startGame() {
        final JFrame parent = new JFrame("Snake");
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.running = true;
        this.snake = new Snake();
        this.setMinimumSize(
            new Dimension(
                GameSnake.DEFAULT_WIDTH,
                GameSnake.DEFAULT_HEIGHT
            )
        );
        parent.add(this);
        parent.setMinimumSize(
            new Dimension(
                GameSnake.DEFAULT_WIDTH + GameSnake.WIN_WIDTH_DIFF,
                GameSnake.DEFAULT_HEIGHT + GameSnake.WIN_HEIGHT_DIFF
            )
        );
        parent.pack();
        parent.setVisible(true);
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

    /**
     * Returns the snake of the running game.
     *
     * @return Returns an instance of snake if the game is running,
     *  otherwise null.
     */
    public Snake getSnake() {
        return this.snake;
    }
//
//    public static void main(String[] args) {
//        GameSnake game = new GameSnake();
//        game.startGame();
//    }
}
