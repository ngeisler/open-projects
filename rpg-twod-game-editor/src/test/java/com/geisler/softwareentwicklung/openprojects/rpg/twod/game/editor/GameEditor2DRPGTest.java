/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor;

import com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup.GameEditor2DRPGStartup;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author ngeis
 */
public class GameEditor2DRPGTest {

    // testpath
    private static final String TEST_PATH = System.getProperty("user.home") + "/testengine/";
    
    // testproject
    private static final String TEST_PROJECT = "/testproject";
    
    // Class under test
    GameEditor2DRPG cut;
    
    // mocked Class under test
    GameEditor2DRPG spy;    
    
    // needed for start
    GameEditor2DRPGStartup startup;
    
    public GameEditor2DRPGTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        startup = new GameEditor2DRPGStartup(TEST_PATH);
        startup.createNewProject(TEST_PROJECT);
        cut = new GameEditor2DRPG(TEST_PROJECT);
        spy = spy(cut);
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
    public void theEditorShouldImportMapResourceIfNothingIsInProjectFolder() throws IOException {
        String userPath = System.getProperty("user.home");
        
        File[] mockValues = new File[1];
        mockValues[0] = new File(userPath + "/test.png");
        mockValues[0].createNewFile();
        // we have to mock the dialog call and return a test file
        doReturn(mockValues).when(spy).callDialogAndGetSelectedFiles();
        
        // no existing map resources
        assertThat(spy.existMapResources(TEST_PATH), is(false));
        
        // start and import resources
        spy.startEditor(TEST_PATH);
        
        // map resources should exist
        assertThat(spy.existMapResources(TEST_PATH), is(true));
        
        mockValues[0].deleteOnExit();
    }
    
    @Test
    public void engineShouldNotBeRunningWithGivenProjectNameIfEditorIsNotStarted() {
        assertThat(cut.isRunning(), is(false));
    }
    
    @Test
    public void engineShouldBeRunningWithGivenProjectNameIfEditorIsStarted() {
        doNothing().when(spy).importResourcesFromDialog(TEST_PATH);

        spy.startEditor(TEST_PATH);
         
        assertThat(spy.isRunning(), is(true));         
    }

    @Test
    public void engineShouldBeRunningIfEditorIsStartedAndResourceImportIsEmpty() {
        doReturn(new File[0]).when(spy).callDialogAndGetSelectedFiles();

        spy.startEditor(TEST_PATH);
        
        assertThat(spy.isRunning(), is(true));
    }
     
    @Test
    public void engineShouldBeRunningIfEditorIsStartedAndResourceImportIsNull() {
        doReturn(null).when(spy).callDialogAndGetSelectedFiles();

        spy.startEditor(TEST_PATH);
        
        assertThat(spy.isRunning(), is(true));
    }
    
    @Test
    public void engineShouldBeAVisibleFrameAfterStart() {
        doNothing().when(spy).importResourcesFromDialog(TEST_PATH);

        spy.startEditor(TEST_PATH);
        
        assertThat(spy, instanceOf(JFrame.class));
        assertThat(spy.isActive(), is(true));
        
        spy.setVisible(false);
    }
}
