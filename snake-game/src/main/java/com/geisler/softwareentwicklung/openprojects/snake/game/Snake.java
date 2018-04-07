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
class Snake {

    private Integer size;
    
    public Snake() {
        this.size = 0;
    }
    
    public Integer size(){
        return this.size;
    }

    void addToSize() {
        this.size++;
    }
    
}
