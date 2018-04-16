package com.geisler.softwareentwicklung.openprojects.snake.game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFrame;
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
public class SnakeGameWindowTest {
    
    public SnakeGameWindowTest() {
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
    public void windowShouldBeAnInstanceOfClassJFrame() {
        GameWindow window = new SnakeGameWindow();
        assertThat(window, is(instanceOf(JFrame.class)));
    }
    
    @Test
    public void windowShouldHaveAGameTitleAtInitialState() {
        GameWindow window = new SnakeGameWindow();
        assertThat(window.title(), is(notNullValue()));
    }
//     @Test
//     public void shouldShowASnakeInAWindowWhenGameIsStarted() {
//         
//     }
}
