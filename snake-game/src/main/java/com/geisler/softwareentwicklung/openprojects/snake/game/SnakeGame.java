/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.snake.game;

/**
 *
 * @author Nico
 */
class SnakeGame implements Game {

    private final Snake snake;
    
    private GameStatus status;
    
    private final GameWindow window;
    
    public SnakeGame(GameWindow window){
        this.window = window;
        this.snake = new Snake();
        this.status = GameStatus.Offline;
    }
    
    @Override
    public GameStatus status() {
        return this.status;
    }

    @Override
    public void start() {        
        this.window.show();
        this.snake.addToSize();
        this.status = GameStatus.Started;
    }

    @Override
    public GameWindow window() {
        return this.window;
    }

    Snake snake() {
        return this.snake;
    }
    
}
