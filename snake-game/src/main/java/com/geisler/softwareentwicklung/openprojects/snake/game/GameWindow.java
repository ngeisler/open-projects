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
interface GameWindow {

    public Boolean isVisible();
    
    public static GameWindow defaultInstance(){
        return new DefaultGameWindow();
    }

    public void showWindow();

    public static class DefaultGameWindow implements GameWindow {

        private boolean isVisible;

        public DefaultGameWindow() {
            this.isVisible = false;
        }

        @Override
        public Boolean isVisible() {
            return this.isVisible;
        }        

        @Override
        public void showWindow() {
            this.isVisible = true;
        }
    }
}
