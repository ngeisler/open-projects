/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * The main class for the snake of the game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
class Snake {

    /**
     * The headposition of the snake.
     */
    private Point2D headposition;

    /**
     * The bodyparts of the snake.
     */
    private Stack<Point2D> bodyparts;

    /**
     * Returns the head position of the snake on the parent game view.
     *
     * @return The current head position of the snake as Point2D.
     */
    public Point2D getHeadposition() {
        return this.headposition;
    }

    /**
     * Update the actual headposition of the snake by distance.
     *
     * @param distx The X diff of the head.
     * @param disty The Y diff of the head.
     */
    public void
        updateHeadpositionByDistanceCoordinates(
            final int distx, final int disty) {
        if (this.headposition != null) {
            this.headposition.setLocation(
                this.headposition.getX() + distx,
                this.headposition.getY() + disty
            );
        }
    }

    /**
     * Initialise the snake instance, on parent parameter base.
     *
     * @param parentwidth The width of the parent component as int.
     * @param parentheight The height of the parent component as int.
     */
    public void init(final int parentwidth, final int parentheight) {
        final double headx = BigDecimal.valueOf(parentwidth)
            .divide(
                BigDecimal.valueOf(2),
                2,
                RoundingMode.HALF_DOWN
            )
            .doubleValue();
        final double heady = BigDecimal.valueOf(parentheight)
            .divide(
                BigDecimal.valueOf(2),
                2,
                RoundingMode.HALF_DOWN
            )
            .doubleValue();
        this.headposition = new Point2D.Double(
            headx,
            heady
        );
        this.bodyparts = new Stack<>();
        this.bodyparts.add(this.headposition);
    }

    /**
     * Returns the bodyparts of the snake as Point2D-instances.
     * @return The bodyparts of the snake as Point2D-instances.
     */
    public Stack<Point2D> getBodyparts() {
        return this.bodyparts;
    }
}
