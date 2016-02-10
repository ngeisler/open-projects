/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

import java.io.File;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ngeis
 */
public class StartupEditorTest {
    
    public StartupEditorTest() {
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
     public void shouldCreateAnInitialEngineFolderAtUserPathOnStartup() {
         GameEditor2DRPGStartup startup = new GameEditor2DRPGStartup("");
         String userPath = System.getProperty("user.home");
         
         assertThat(new File(userPath + "/rpgengine").exists(), is(true));
         
     }
}
