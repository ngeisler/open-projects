/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.card.game.mau;

import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatColor;
import com.geisler.softwareentwicklung.openprojects.card.game.mau.enums.EnumSkatValue;
import java.util.Objects;

/**
 *
 * @author ngeis
 */
public class SkatCard {

    private EnumSkatColor color;
    
    private EnumSkatValue value;

    public SkatCard(EnumSkatColor color, EnumSkatValue value) {
        this.color = color;
        this.value = value;
    }
    
    public EnumSkatColor getColor() {
        return color;
    }

    public void setColor(EnumSkatColor color) {
        this.color = color;
    }

    public EnumSkatValue getValue() {
        return value;
    }

    public void setValue(EnumSkatValue value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.color);
        hash = 41 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SkatCard other = (SkatCard) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return color.getGerman() + " " + value.getGerman();
    }
       
}
