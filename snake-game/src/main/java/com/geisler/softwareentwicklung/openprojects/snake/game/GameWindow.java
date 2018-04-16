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

    public boolean isVisible();
    
    public static GameWindow defaultInstance(){
        return new DefaultGameWindow();
    }

    public void show();
    
    public String title();

    public static class DefaultGameWindow implements GameWindow {

        private boolean isVisible;

        public DefaultGameWindow() {
            this.isVisible = false;
        }

        @Override
        public boolean isVisible() {
            return this.isVisible;
        }        

        @Override
        public void show() {
            this.isVisible = true;
        }
        
        public String title() {
            return "Default Game Title";
        }
    }
}
