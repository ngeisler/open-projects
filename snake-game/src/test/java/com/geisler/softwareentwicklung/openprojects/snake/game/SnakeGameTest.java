package com.geisler.softwareentwicklung.openprojects.snake.game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author Nico
 */
public class SnakeGameTest {
    
    public SnakeGameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void snakeGameShouldContainASnakeWithSizeOfOneWhenGameIsStarted() {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.start();
        Snake snake = snakeGame.snake();
        assertThat(snake.size(), is(1));
    }
//     @Test
//     public void shouldShowASnakeInAWindowWhenGameIsStarted() {
//         
//     }
}
