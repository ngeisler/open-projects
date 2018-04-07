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
class SnakeGame {

    GameStatus status;
    
    public SnakeGame() {
        this.status = GameStatus.Offline;
    }
    
    GameStatus getStatus() {
       return status;
    }

    void start() {
        this.status = GameStatus.Started;
    }
    
}
