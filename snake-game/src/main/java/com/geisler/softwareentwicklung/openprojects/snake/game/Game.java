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
interface Game {

    public static Game defaultInstance(){
        return new DefaultGame();
    }

    public GameStatus status();

    public void start();

    public GameWindow window();
    
    class DefaultGame implements Game {
        
        private GameStatus status;
        
        private GameWindow window;
        
        public DefaultGame(){
            this.status = GameStatus.Offline;
            this.window = GameWindow.defaultInstance();
        }
        
        @Override
        public GameStatus status() {
            return this.status;
        }

        @Override
        public void start() {
            this.status = GameStatus.Started;
            this.window.showWindow();
        }

        @Override
        public GameWindow window() {
            return this.window;
        }
        
    }
}
