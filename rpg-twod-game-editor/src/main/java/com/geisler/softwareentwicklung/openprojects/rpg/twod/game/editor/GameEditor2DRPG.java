package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *  
 * 
 * @author ngeis
 */
public class GameEditor2DRPG {

    private boolean running;
    
    private final String projectName;
    
    public GameEditor2DRPG(String projectName) {
        this.projectName = projectName;
    }
   
    public void startEditor(String filePath) {
        running = true;
        if(!existMapResources(filePath)) {
            callImportResourceDialog(filePath);
        }
    }
    
    /**
     * Says if the Editor was started and is running at the moment
     * 
     * @return true if editor is started and running, otherwise false 
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Checks if map resource files exist at project location
     * 
     * @param filePath the filepath from the GameEditorEngine
     * @return true if one or more resource files exist, false otherwise
     */
    public boolean existMapResources(String filePath) {
        File resourceFolder = new File(filePath + "/" + projectName + "/resources");
        return (resourceFolder.exists() 
                && resourceFolder.listFiles().length > 0) ;
    }

    void callImportResourceDialog(String filePath) {
        // check resourcepath and create if not exist
        String importDest = filePath + "/" + projectName + "/resources/";
        File resourceFolder = new File(importDest);
        if (!resourceFolder.exists()) {
            resourceFolder.mkdir();
        }        
        
        // open dialog an get selected files
        File[] selectedFiles = this.callDialogAndGetSelectedFiles();
        
        // copy files to the resource location
        for (File selectedFile : selectedFiles) {
            Path source = Paths.get(selectedFile.getAbsolutePath());
            Path destination = Paths.get(importDest + selectedFile.getName());
            
            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Opens a standard file chooser dialog and returns the selected files
     * 
     * @return selected files {@link File} from file chooser dialog
     */
    File[] callDialogAndGetSelectedFiles() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
        fc.setMultiSelectionEnabled(true);
        fc.showDialog(null, "Import Map Ressourcen");
        return fc.getSelectedFiles();
    }
}
