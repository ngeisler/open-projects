/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau.enums;

/**
 * An enumeration that describes the value of a skat card.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public enum EnumSkatValue {
    /**
     * The SkatCard value for seven with additional info id and german name.
     */
    SEVEN(0, "Sieben"),
    /**
     * The SkatCard value for eight with additional info id and german name.
     */
    EIGHT(1, "Acht"),
    /**
     * The SkatCard value for nine with additional info id and german name.
     */
    NINE(2, "Neun"),
    /**
     * The SkatCard value for ten with additional info id and german name.
     */
    TEN(3, "Zehn"),
    /**
     * The SkatCard value for jack with additional info id and german name.
     */
    JACK(4, "Bube"),
    /**
     * The SkatCard value for queen with additional info id and german name.
     */
    QUEEN(5, "Dame"),
    /**
     * The SkatCard value for king with additional info id and german name.
     */
    KING(6, "König"),
    /**
     * The SkatCard value for ace with additional info id and german name.
     */
    ACE(7, "Ass");
    /**
     * The additional id for the value.
     */
    private int idx;
    /**
     * The additional german name of the value.
     */
    private String german;
    /**
     * The SkatCard-Value with the additional id
     * and the german name of the value.
     *
     * @param idx The id of the value as int
     * @param german The german name of the value as String
     */
    EnumSkatValue(final int idx, final String german) {
        this.idx = idx;
        this.german = german;
    }
    /**
     * Returns the german name of the value.
     * @return The german value name as String.
     */
    public String getGerman() {
        return this.german;
    }
    /**
     * Returns the additional id of the value.
     * @return The additional id of the value as int.
     */
    public int getIdx() {
        return this.idx;
    }
}
