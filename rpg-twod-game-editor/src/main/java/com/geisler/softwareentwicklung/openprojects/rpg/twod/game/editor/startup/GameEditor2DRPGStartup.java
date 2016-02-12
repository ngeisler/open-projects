package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

import java.io.File;

/**
 *
 * @author ngeis
 */
public class GameEditor2DRPGStartup {
    
    private final String filePath;
    
    private GameEditor2DRPG editorEngine;
    /*
    * Constructor of Startup Class with the path
    * where the user files are saved
    */
    public GameEditor2DRPGStartup(String enginePath) {
        // check Engine Path
        filePath = createEnginePath(enginePath);
    }
    
    /**
     * Creation of the engine path, if param is not given 
     * then the user.home folder will be used to create
     * a rpgengine folder
     * 
     * @param path - given path on startup, where to place all projects
     * @return engine path will be returned for initialization
     */
    private String createEnginePath(String path) {
        File localFilePath;
        if(path == null || path.isEmpty()) {
            localFilePath = new File(System.getProperty("user.home") + "/rpgengine");
        } else {
            localFilePath = new File(path);
        }
        localFilePath.mkdir();
        return localFilePath.getAbsolutePath();
    }

    /**
     * createNewProject-Method will save a new folder with a given projectname.
     * The location is beneath the startup engine-folder.
     * 
     * @param gameproject the name of the project
     */
    void createNewProject(String gameproject) {
        File localProjectPath;
        if(gameproject == null || gameproject.isEmpty()) {
            throw new NullPointerException("Projektname darf nicht null oder leer sein!");
        } else {
            localProjectPath = new File(filePath + "/" + gameproject);
        }
        localProjectPath.mkdir();
    }

    /**
     * Starts the editor engine from Startup-Class with given project name
     * 
     * @param projectName name of the project which will be started
     * @return an instance of {@link GameEditor2DRPG}
     */
    void startEditor(String projectName) {
        editorEngine = GameEditor2DRPG.getInstanceForProject(projectName);
        editorEngine.startEditor();
    }
    
    GameEditor2DRPG getEditorEngine() {
        return editorEngine;
    }
}
