package com.geisler.softwareentwicklung.openprojects.card.game.mau.view;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.EnumSkatValue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ngeis
 */
public class GameMauView extends JFrame {

    
    public GameMauView() {
        GameMauPanel panel = new GameMauPanel(1024, 600, this);
        this.setTitle("Mau Mau Spiel");
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        
        doInitialization(panel);
        
        Thread thread = new Thread(panel);
        thread.start();
    }
    
    private void doInitialization(GameMauPanel panel) {
        panel.setLast(System.nanoTime());
        panel.loadCardPics();
    }
    
    public class GameMauPanel extends JPanel implements Runnable {
        
        JFrame parent;
        
        BufferedImage[][] images;
        
        long delta;
        long last;

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
            
            while(parent.isVisible()) {
                
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
            
            // REMOVE draw test card 
            g.drawImage(images[2][5], 30, 30, null);
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
    }
}
