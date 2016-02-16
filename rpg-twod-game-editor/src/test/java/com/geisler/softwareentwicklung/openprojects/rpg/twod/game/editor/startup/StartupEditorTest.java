/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

import com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.GameEditor2DRPG;
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
        cut = new GameEditor2DRPGStartup("");
    }
    
    @After
    public void tearDown() throws URISyntaxException, IOException {
        String userPath = System.getProperty("user.home");
        String initialPath = userPath + "/rpgengine";
        File file = new File(initialPath);
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
         String userPath = System.getProperty("user.home");
         String initialPath = userPath + "/rpgengine";
         
         assertThat(new File(initialPath).exists(), is(true));
         
         new File(initialPath).delete();
     }

     @Test
     public void shouldCreateAnEngineFolderAtGivenPathOnStartup() {
         String enginePath = System.getProperty("user.home") + "/myOne";
         cut = new GameEditor2DRPGStartup(enginePath);
         
         assertThat(new File(enginePath).exists(), is(true));
         
         new File(enginePath).delete();
     }
     
     @Test
     public void shouldCreateANewGameProjectFolderIfCreationWasCalled() {
         String projectName = "gameproject";
         cut.createNewProject(projectName);
         String userPath = System.getProperty("user.home");
         String projectPath = userPath + "/rpgengine/" + projectName;
         assertThat(new File(projectPath).exists(), is(true));
         
         new File(projectPath).delete();
     }
     
}
