/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau.view;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMau;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMauPlayer;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.SkatCard;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.interfaces.IGameMau;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ngeis
 */
public class GameMauView extends JFrame {

    
    public GameMauView() throws NoMorePlayersAllowedException {
        GameMauPanel panel = new GameMauPanel(1024, 600, this);
        IGameMau game = GameMau.getInstanceOfGameMau();
        this.setTitle("Mau Mau Spiel");
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        
        doInitialization(panel, game);
        
        Thread thread = new Thread(panel);
        thread.start();
    }
    
    private void doInitialization(GameMauPanel panel, IGameMau game) throws NoMorePlayersAllowedException {
        panel.setLast(System.nanoTime());
        panel.loadCardPics();
        Integer playerCount = getPlayerCountFromUser();
        
        // Add players
        for(int i = 1; i <= playerCount; i++) {
            game.addNewPlayerToGame(new GameMauPlayer(getPlayerNameFromUser(i)));
        }
        panel.startGame(game);
    }

    private Integer getPlayerCountFromUser() {
        String players = JOptionPane.showInputDialog("Bitte geben Sie die Anzahl Spieler ein.", 2);
        if(players == null) {
            System.exit(0);
        }
        return Integer.parseInt(players);
    }

    private String getPlayerNameFromUser(int playerIdx) {
        String playerName = JOptionPane.showInputDialog("Wie lautet der Name von Spieler Nr. " + playerIdx + " ?", "Spielername");
        return playerName;
        
    }
    
    public class GameMauPanel extends JPanel implements Runnable {
        
        JFrame parent;
        
        BufferedImage[][] images;
        
        long delta;
        long last;
        private IGameMau game;

        public void setLast(long last) {
            this.last = last;
        }
        long fps;
        
        public GameMauPanel(int w, int h, JFrame parent) {
            this.setPreferredSize(new Dimension(w, h));
            this.parent = parent;
        }

        @Override
        public void run() {
            
            while(parent.isVisible()
                    && getGame().isGameRunning()) {
                
                computeDelta();
                
                checkKeys();
                doLogic();
                moveObjects();
                
                repaint();
                
                try {
                    Thread.sleep(10);
                } catch(InterruptedException e) {
                    
                }
                
            }
            
        }

        private void computeDelta() {
            delta = System.nanoTime() - last;
            last = System.nanoTime();
            fps = ((long) 1e9) / delta;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            
            g.setColor(Color.red);
            g.drawString("FPS: " + Long.toString(fps), 20, 10);
            
            // draw player areas and names
            g.setColor(Color.blue);
            g.fillRoundRect(256, 450, 512, 150, 50, 50);
            
            g.setColor(Color.green);
            g.fillRoundRect(256, 0, 512, 150, 50, 50);
            
            g.setColor(Color.magenta);
            g.fillRoundRect(0, 150, 256, 300, 50, 50);
            
            g.setColor(Color.orange);
            g.fillRoundRect(768, 150, 256, 300, 50, 50);
            
            if(getGame() != null) {
                g.setColor(Color.black);
                if(getGame().getPlayerIds().size() > 0) {
                    g.drawString(getGame().getPlayerNameWithId(1), 460, 460);
                }
                if(getGame().getPlayerIds().size() > 1) {
                    g.drawString(getGame().getPlayerNameWithId(2), 75, 160);
                }
                if(getGame().getPlayerIds().size() > 2) {
                    g.drawString(getGame().getPlayerNameWithId(3), 460, 10);
                }
                if(getGame().getPlayerIds().size() > 3) {
                    g.drawString(getGame().getPlayerNameWithId(4), 850, 160);
                }
            }
            // draw middleCard
            SkatCard card = null;
            if(this.getGame() != null) {
                card = getGame().getMiddleStackCardOnTop();
            }
            if(card != null && images != null 
                    && images[card.getColor().getIdx()][card.getValue().getIdx()] != null) {
                g.drawImage(images[card.getColor().getIdx()][card.getValue().getIdx()], 470, 260, parent);
            }
            // draw player cards
            if(this.getGame() != null) {
                int i = 0;
                for(SkatCard playerCard : this.getGame().getPlayerHandCardsWithId(1)) {
                    int space = i * 5;
                    BufferedImage img = images[playerCard.getColor().getIdx()][playerCard.getValue().getIdx()];
                    g.drawImage(img, 
                            256 +(i*img.getWidth()) + space, 
                            470, 
                            parent);
                    i++;
                }
            }
        }

        private void checkKeys() {
            
        }

        private void doLogic() {
            
        }

        private void moveObjects() {
            
        }
    
        public void loadCardPics() {
            BufferedImage[][] pics = new BufferedImage[EnumSkatColor.values().length][EnumSkatValue.values().length];
            BufferedImage source = null;
            
            URL picUrl = getClass().getClassLoader().getResource("SkatKarten.png");
            
            try {
                source = ImageIO.read(picUrl);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            int picWidth = (int)(source.getWidth()/EnumSkatValue.values().length);
            int picHeight = (int)(source.getHeight()/EnumSkatColor.values().length);
            for (int i = 0; i < EnumSkatColor.values().length; i++) {
                for(int j = 0; j < EnumSkatValue.values().length; j++) {
                    pics[i][j] = source.getSubimage(j*picWidth, i*picHeight, picWidth, picHeight);
                }
            }
            this.images = pics;
        }

        private void startGame(IGameMau game) {
            this.game = game;
            this.game.start();
        }

        private IGameMau getGame() {
            return this.game;
        }
    }
}
