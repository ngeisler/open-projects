package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

import java.io.File;

/**
 *
 * @author ngeis
 */
public class GameEditor2DRPGStartup {
    
    /*
    * Constructor of Startup Class with the path
    * where the user files are saved
    */
    public GameEditor2DRPGStartup(String enginePath) {
        // check Engine Path
        createEnginePath(enginePath);
    }
    
    
    private void createEnginePath(String path) {
        File filePath;
        if(path == null || path.isEmpty()) {
            filePath = new File(System.getProperty("user.home") + "/rpgengine");
        } else {
            filePath = new File(path);
        }
        filePath.mkdir();
    }
}
