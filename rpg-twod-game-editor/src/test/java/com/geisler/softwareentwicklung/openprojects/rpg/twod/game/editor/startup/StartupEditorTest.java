/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
    
    // Class under test
    GameEditor2DRPGStartup cut;
    
    // testpath
    private static final String TEST_PATH = System.getProperty("user.home") + "/testengine/";
    
    // testproject
    private static final String TEST_PROJECT = "/testproject";
    
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
        cut = new GameEditor2DRPGStartup(TEST_PATH);
    }
    
    @After
    public void tearDown() throws URISyntaxException, IOException {
        File file = new File(TEST_PATH);
        if(!file.exists()) {
            return;
        }
        Files.walkFileTree(file.toPath(), new SimpleFileVisitor<Path>() {
         @Override
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
             throws IOException
         {
             Files.delete(file);
             return FileVisitResult.CONTINUE;
         }
         @Override
         public FileVisitResult postVisitDirectory(Path dir, IOException e)
             throws IOException
         {
             if (e == null) {
                 Files.delete(dir);
                 return FileVisitResult.CONTINUE;
             } else {
                 // directory iteration failed
                 throw e;
             }
         }
     });
    }

    
     @Test
     public void shouldCreateAnInitialEngineFolderAtUserPathOnStartup() {
        String initialPath = System.getProperty("user.home") + "/rpgengine";
        // initial startup without path for files and folders
        cut = new GameEditor2DRPGStartup("");
         
        assertThat(new File(initialPath).exists(), is(true));
        
        new File(initialPath).deleteOnExit();
     }

     @Test
     public void shouldCreateAnEngineFolderAtGivenPathOnStartup() {
         
         assertThat(new File(TEST_PATH).exists(), is(true));
         
         new File(TEST_PATH).delete();
     }
     
     @Test
     public void shouldCreateANewGameProjectFolderIfCreationWasCalled() {
         cut.createNewProject(TEST_PROJECT);
         
         assertThat(new File(TEST_PATH + TEST_PROJECT).exists(), is(true));
         
     }
     
}
