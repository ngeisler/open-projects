package com.geisler.softwareentwicklung.openprojects.card.game.mau;

/**
 *
 * @author ngeis
 */
enum EnumSkatColor {
    HEARTS("Herz"),
    SPADES("Pik"),
    CLUBS("Kreuz"),
    DIAMONDS("Karo");
    
    private EnumSkatColor(String color) {
        this.germanName = color;
    }
    public String getGermanName() {
        return this.germanName;
    }
    private String germanName;
}
