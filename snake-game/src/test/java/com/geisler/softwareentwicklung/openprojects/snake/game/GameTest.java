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
public class GameTest {
    
    public GameTest() {
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
    public void gameStatusShouldBeOfflineAtInitialState() {
        Game game = Game.defaultInstance();
        
        GameStatus gameStatus  = game.status();
        
        assertThat(gameStatus, is(GameStatus.Offline));
    }
    
    @Test
    public void gameStatusShouldBeStartedWhenGameIsStarted() {
        Game game = Game.defaultInstance();
        
        game.start();
        
        GameStatus gameStatus  = game.status();
        
        assertThat(gameStatus, is(GameStatus.Started));
    }
        
    @Test
    public void gameWindowShouldBeNotVisibleAtInitialState() {
        Game game = Game.defaultInstance();
        
        GameWindow gameWindow = game.window();
        
        assertThat(gameWindow.isVisible(), is(Boolean.FALSE));
    }
    
    @Test
    public void gameWindowShouldBeVisibleWhenGameIsStarted() {
        Game game = Game.defaultInstance();
        
        game.start();
        
        GameWindow gameWindow = game.window();
        
        assertThat(gameWindow.isVisible(), is(Boolean.TRUE));
    }

}
