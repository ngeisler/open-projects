/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.Graphics2D;

/**
 * An public interface that define other classes as drawable.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
public interface Drawable {

    /**
     * Draws a component that implements this interface.
     * @param graphic The Graphics2D Context to draw a component on it.
     */
    void drawComponent(Graphics2D graphic);
}
