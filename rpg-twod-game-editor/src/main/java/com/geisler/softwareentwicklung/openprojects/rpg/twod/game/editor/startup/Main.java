package com.geisler.softwareentwicklung.openprojects.rpg.twod.game.editor.startup;

/**
 *
 * @author ngeis
 */
public class Main {
    
    public static void main(String[] args) {
        GameEditor2DRPGStartup start = new GameEditor2DRPGStartup("D:/temp");
        start.createNewProject("myOne");
        start.startEditor("myOne");
    }
}
