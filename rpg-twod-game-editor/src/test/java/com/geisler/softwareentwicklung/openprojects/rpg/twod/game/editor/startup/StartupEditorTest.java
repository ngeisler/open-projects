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
         String userPath = System.getProperty("user.home");
         String initialPath = userPath + "/rpgengine";
         GameEditor2DRPGStartup startup = new GameEditor2DRPGStartup("");
         
         assertThat(new File(initialPath).exists(), is(true));
         
         new File(initialPath).delete();
     }

     @Test
     public void shouldCreateAnEngineFolderAtGivenPathOnStartup() {
         String enginePath = System.getProperty("user.home") + "/myOne";
         GameEditor2DRPGStartup startup = new GameEditor2DRPGStartup(enginePath);
         
         assertThat(new File(enginePath).exists(), is(true));
         
         new File(enginePath).delete();
     }
     
     @Test
     public void shouldCreateANewGameProjectFolderIfCreationWasCalled() {
         String projectName = "gameproject";
         GameEditor2DRPGStartup startup = new GameEditor2DRPGStartup("");
         startup.createNewProject(projectName);
         String userPath = System.getProperty("user.home");
         String projectPath = userPath + "/rpgengine/" + projectName;
         assertThat(new File(projectPath).exists(), is(true));
         
         new File(projectPath).delete();
     }
     
     @Test
     public void engineShouldBeRunningWithGivenProjectNameIfEditorIsStarted() {
         String projectName = "gameproject";
         GameEditor2DRPGStartup startup = new GameEditor2DRPGStartup("");
         startup.createNewProject(projectName);
         startup.startEditor(projectName);
         assertThat(startup.getEditorEngine().isRunning(), is(true));
     }
     
     @Test
     public void engineShouldNotBeRunningWithGivenProjectNameIfEditorIsNotStarted() {
         String projectName = "gameproject";
         GameEditor2DRPGStartup startup = new GameEditor2DRPGStartup("");
         startup.createNewProject(projectName);
         GameEditor2DRPG editor = GameEditor2DRPG.getInstanceForProject(projectName);
         assertThat(editor.isRunning(), is(false));
     }
}
