package com.geisler.softwareentwicklung.openprojects.card.game.mau;

/**
 *
 * @author ngeis
 */
public enum EnumSkatValue {
    SEVEN   (0, "Sieben"),
    EIGHT   (1, "Acht"),
    NINE    (2, "Neun"),
    TEN     (3, "Zehn"),
    JACK    (4, "Bube"),
    QUEEN   (5, "Dame"),
    KING    (6, "König"),
    ACE     (7, "Ass");
    
    private EnumSkatValue(int idx, String color) {
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
