package com.geisler.softwareentwicklung.openprojects.card.game.mau;

/**
 *
 * @author ngeis
 */
public enum EnumSkatColor {
    DIAMONDS(0, "Karo"),
    HEARTS(1, "Herz"),
    SPADES(2, "Pik"),
    CLUBS(3, "Kreuz");
    
    private EnumSkatColor(int idx, String color) {
        this.idx = idx;
        this.germanName = color;
    }
    public String getGermanName() {
        return this.germanName;
    }
    private int idx;

    public int getIdx() {
        return idx;
    }
    private String germanName;
}
