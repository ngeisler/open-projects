package com.geisler.softwareentwicklung.openprojects.card.game.mau.starter;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.CardByRulesNotAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMau;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMauPlayer;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.NoMorePlayersAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.SkatCard;
import java.util.Scanner;

/**
 *
 * @author ngeis
 */
public class StartMauGame {

    public static void main(String[] args) throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        
        GameMau mau = new GameMau();
        
        StartMauGame start = new StartMauGame();
        Integer players = start.getPlayerCountFromUser();
        
        System.out.println("Anzahl Spieler: " + players);
        
        // Add players
        for(int i = 1; i <= players; i++) {
            mau.addNewPlayerToGame(new GameMauPlayer(start.getPlayerNameFromUser(i)));
        }
        
        mau.getPlayers().values().stream().forEach((player) -> {
            System.out.println("Spielername: " + player.getName());
        });
        
        mau.start();
        
        // rundenbasiert
        while(mau.isRunning()) {
            System.out.println("Aktuelle Karte auf dem Stapel: " + mau.getMiddleStack().peek().toString());
            Integer cardIdx = start.getCardIndexFromUser(mau.getActivePlayer());
            mau.throwPlayerCardToMiddleStack(cardIdx-1);
        }
        
        System.out.println("Gewinner ist: " + mau.getWinner().getName());
    }

    public Integer getCardIndexFromUser(GameMauPlayer activePlayer) {
        int handsize = activePlayer.getHandSize();
        System.out.println(activePlayer.getName() + ", bitte wähle deine Karte. (" + 1 + "-" + handsize + ")");
        System.out.println("Aktuelle Handkarten: ");
        int i = 0;
        for(SkatCard card : activePlayer.getHandCards()) {
            i++;
            System.out.println("" + i + ". " + card.getColor().getGermanName() + " " + card.getValue().getGermanName());
        }
        Scanner scanIn = new Scanner(System.in);
        Integer cardIdx = 0;
        if(scanIn.hasNextInt()) {
             cardIdx = scanIn.nextInt();
        }
        
        return cardIdx;
    }

    public String getPlayerNameFromUser(int playerIdx) {
        // ask for players
        System.out.println("Wie lautet der Name von Spieler Nr. " + playerIdx + " ?");
        Scanner scanIn = new Scanner(System.in);
        String playerName = "";
        if(scanIn.hasNextLine()) {
             playerName = scanIn.nextLine();
        }
        
        return playerName;
    }
    
    public Integer getPlayerCountFromUser() {
        
        // ask for players
        System.out.println("Wieviele Spieler möchten teilnehmen?");
        Scanner scanIn = new Scanner(System.in);
        Integer playerCount = 0;
        if(scanIn.hasNextInt()) {
             playerCount = scanIn.nextInt();
        }
        
        return playerCount;
    }
    
}
