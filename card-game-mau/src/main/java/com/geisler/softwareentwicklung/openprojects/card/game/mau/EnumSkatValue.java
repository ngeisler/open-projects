package com.geisler.softwareentwicklung.openprojects.card.game.mau;

/**
 *
 * @author ngeis
 */
public enum EnumSkatValue {
    SEVEN("Sieben"),
    EIGHT("Acht"),
    NINE("Neun"),
    TEN("Zehn"),
    JACK("Bube"),
    QUEEN("Dame"),
    KING("König"),
    ACE("Ass");
    
    private EnumSkatValue(String color) {
        this.germanName = color;
    }
    public String getGermanName() {
        return this.germanName;
    }
    private String germanName;
}
