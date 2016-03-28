/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau.enums;

/**
 * An enumeration that describes the color of a skat card.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public enum EnumSkatColor {
    /**
     * The SkatCard Color for Diamonds with additional info id and german name.
     */
    DIAMONDS(0, "Karo"),
    /**
     * The SkatCard Color for Hearts with additional info id and german name.
     */
    HEARTS(1, "Herz"),
    /**
     * The SkatCard Color for Spades with additional info id and german name.
     */
    SPADES(2, "Pik"),
    /**
     * The SkatCard Color for Clubs with additional info id and german name.
     */
    CLUBS(3, "Kreuz");
    /**
     * The additional id for the color.
     */
    private int idx;
    /**
     * The additional german name of the color.
     */
    private String german;
    /**
     * The SkatCard-Color with the additional Id
     * and the german name of the color.
     *
     * @param idx The id of the color as int
     * @param german The german name of the color as String
     */
    EnumSkatColor(final int idx, final String german) {
        this.idx = idx;
        this.german = german;
    }
    /**
     * Returns the german name of the color.
     * @return The german color as String.
     */
    public String getGerman() {
        return this.german;
    }
    /**
     * Returns the additional id of the color.
     * @return The additional id of the color as int.
     */
    public int getIdx() {
        return this.idx;
    }
}
