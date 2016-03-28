package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor;

import javax.swing.JPanel;

/**
 *
 * @author ngeis
 */
class MapResourceView extends JPanel {

    private String activeResource;

    public String getActiveResource() {
        return activeResource;
    }

    public void setActiveResource(String activeResource) {
        this.activeResource = activeResource;
    }
    
    private boolean resourceLoaded;

    boolean isResourceLoaded() {
        return resourceLoaded;
    }
    
    void setResourceLoaded(boolean resourceLoaded) {
        this.resourceLoaded = resourceLoaded; 
    }
}
