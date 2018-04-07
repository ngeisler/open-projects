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

    private Snake snake;
    
    public SnakeGame(){
        this.snake = new Snake();
    }
    
    @Override
    public GameStatus status() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void start() {
        this.snake.addToSize();
    }

    @Override
    public GameWindow window() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Snake snake() {
        return this.snake;
    }
    
}
