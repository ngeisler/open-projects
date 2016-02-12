package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

/**
 *  
 * 
 * @author ngeis
 */
class GameEditor2DRPG {

    private boolean running;
    
    private GameEditor2DRPG(String projectName) {
        
    }
    
    public static GameEditor2DRPG getInstanceForProject(String name) {
       return new GameEditor2DRPG(name);
    }
    
    public void startEditor() {
        running = true;
    }
    
    /**
     * Says if the Editor was started and is running at the moment
     * 
     * @return true if editor is started and running, otherwise false 
     */
    public boolean isRunning() {
        return running;
    }
}
