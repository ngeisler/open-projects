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
    public void snakeGameStatusShouldBeOfflineAtInitialState() {
        SnakeGame game = new SnakeGame(GameWindow.defaultInstance());
        
        GameStatus gameStatus  = game.status();
        
        assertThat(gameStatus, is(GameStatus.Offline));
    }
    
    @Test
    public void snakeGameStatusShouldBeStartedWhenGameIsStarted() {
        SnakeGame game = new SnakeGame(GameWindow.defaultInstance());
        
        game.start();
        
        GameStatus gameStatus  = game.status();
        
        assertThat(gameStatus, is(GameStatus.Started));
    }
        
    @Test
    public void snakeGameWindowShouldBeNotVisibleAtInitialState() {
        SnakeGame game = new SnakeGame(GameWindow.defaultInstance());
        
        GameWindow gameWindow = game.window();
        
        assertThat(gameWindow.isVisible(), is(Boolean.FALSE));
    }
    
    @Test
    public void snakeGameWindowShouldBeVisibleWhenGameIsStarted() {
        SnakeGame game = new SnakeGame(GameWindow.defaultInstance());
        
        game.start();
        
        GameWindow gameWindow = game.window();
        
        assertThat(gameWindow.isVisible(), is(Boolean.TRUE));
    }
    @Test
    public void snakeGameShouldContainASnakeWithSizeOfOneWhenGameIsStarted() {
        SnakeGame game = new SnakeGame(GameWindow.defaultInstance());
        game.start();
        Snake snake = game.snake();
        assertThat(snake.size(), is(1));
    }
//     @Test
//     public void shouldShowASnakeInAWindowWhenGameIsStarted() {
//         
//     }
}
