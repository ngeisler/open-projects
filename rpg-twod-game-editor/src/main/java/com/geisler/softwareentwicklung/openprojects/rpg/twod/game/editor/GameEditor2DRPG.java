package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *  
 * 
 * @author ngeis
 */
public class GameEditor2DRPG extends JFrame {

    private boolean running;
    
    private final String projectName;
    
    private MapResourceView mapResourceView;
    
    public GameEditor2DRPG(String projectName) {
        this.projectName = projectName;
    }
   
    /**
     * set the running state of the editor to true, but checks
     * the resource path for the map-tiles before and calls an import.
     * 
     * @param filePath 
     */
    public void startEditor(String filePath) {
        if(!existMapResources(filePath)) {
            importResourcesFromDialog(filePath);
        }
        mapResourceView = new MapResourceView();
        if(existMapResources(filePath)) {
            String[] mapResources = getMapResources(filePath);
            mapResourceView.setActiveResource(mapResources[0]);
            mapResourceView.setResourceLoaded(true);
        }
        running = true;
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
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

    /**
     * Import selected resources from a called dialog to the defined
     * project-resources folder.
     * 
     * @param filePath 
     */
    void importResourcesFromDialog(String filePath) {
        // check resourcepath and create if not exist
        String importDest = filePath + "/" + projectName + "/resources/";
        File resourceFolder = new File(importDest);
        if (!resourceFolder.exists()) {
            resourceFolder.mkdir();
        }        
        
        // open dialog an get selected files
        File[] selectedFiles = this.callDialogAndGetSelectedFiles();
        if(selectedFiles == null) {
            return;
        }
        
        // copy files to the resource location
        for (File selectedFile : selectedFiles) {
            Path source = Paths.get(selectedFile.getAbsolutePath());
            Path destination = Paths.get(importDest + selectedFile.getName());
            
            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new RuntimeException("Error on creating copy of resources: " + source);
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
        int showDialog = fc.showDialog(null, "Import Map Ressourcen");
        if(showDialog != JFileChooser.APPROVE_OPTION
                || fc.getSelectedFiles() == null
                || fc.getSelectedFiles().length < 1) {
            JOptionPane.showMessageDialog(
                            fc, // parent component 
                            "Ressourcen zum Erstellen von Maps wurden nicht importiert. "
                                    + "Bitte importieren Sie diese über das Menü sobald der Editor gestartet wurde.", // message
                            "Ressourcen Import abgebrochen", // titel 
                            JOptionPane.INFORMATION_MESSAGE); // messagetype
        }
        return fc.getSelectedFiles();
    }
    
    /**
     * stops the editor and disposes the visible frame
     */
    void stopEditor() {

        this.running = false;
        this.setVisible(false);
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        // If window is closed, do something I want before
        if(e.getID() == WindowEvent.WINDOW_CLOSING) {
            stopEditor();
            super.processWindowEvent(e);
            return;
        }
        super.processWindowEvent(e); 
    }
    
    MapResourceView getMapResourceView() {
        return this.mapResourceView;
    }

    private String[] getMapResources(String filePath) {
        String resourcePath = filePath + "/" + projectName + "/resources/";
        File resourceFolder = new File(resourcePath);
        String[] mapResourceNames = resourceFolder.list();
        for(int i = 0; i < mapResourceNames.length; i++) {
            mapResourceNames[i] = mapResourceNames[i].substring(0, mapResourceNames[i].indexOf("."));
        }
        return mapResourceNames;
    }
}
