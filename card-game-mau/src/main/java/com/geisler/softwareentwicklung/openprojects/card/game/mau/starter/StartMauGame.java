/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau.starter;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.CardByRulesNotAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMau;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.GameMauPlayer;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.exceptions.NoMorePlayersAllowedException;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.SkatCard;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.view.GameMauView;
import java.util.Scanner;

/**
 *
 * @author ngeis
 */
public class StartMauGame {

    public static void main(String[] args) throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
        startWindowGame();        
    }

    public static void startWindowGame() throws NoMorePlayersAllowedException {
        GameMauView view = new GameMauView();
    }
    
    public static void startConsoleGame() throws NoMorePlayersAllowedException, CardByRulesNotAllowedException {
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
            System.out.println("Aktuelle Karte auf dem Stapel: " + mau.getMiddlestack().peek().toString());
            // Show choosed color
            if(mau.getChoosedcolor() != null) {
                System.out.println("Gewünschte Farbe ist: " + mau.getChoosedcolor().getGerman());
            }
            Integer cardIdx = start.getCardIndexFromUser(mau.getPlayer());
            SkatCard playerCard = mau.getPlayer().getHandCards().get(cardIdx-1);
            if(playerCard.getValue().equals(EnumSkatValue.JACK)) {
                EnumSkatColor choosedColor = start.getChoosedPlayerColor();
                mau.getPlayer().setJacksColor(choosedColor);
            }
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
            System.out.println("" + i + ". " + card.getColor().getGerman() + " " + card.getValue().getGerman());
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

    private EnumSkatColor getChoosedPlayerColor() {
        int i = 0;
        System.out.println("Welche Farbe wünschst du dir?");
        for (EnumSkatColor enumColor : EnumSkatColor.values()) {
            i++;
            System.out.println("" + i + ": " + enumColor.getGerman());
        }
        Scanner scanIn = new Scanner(System.in);
        Integer intColor = 0;
        if(scanIn.hasNextInt()) {
             intColor = scanIn.nextInt();
        }
        return EnumSkatColor.values()[intColor - 1];
    }
    
}
