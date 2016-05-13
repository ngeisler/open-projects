/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
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
class Snake implements Drawable {

    /**
     * The size of one snake step in pixel.
     */
    public static final int SNAKE_STEP_SIZE = 25;

    /**
     * The size of one bodypart in pixel.
     */
    private static final int BODYPART_SIZE = 10;

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
        final Point2D head;
        if (this.bodyparts == null || this.bodyparts.isEmpty()) {
            head = null;
        } else {
            head = this.bodyparts.peek();
        }
        return head;
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
        if (this.getHeadposition() != null) {
            this.getHeadposition().setLocation(
                this.getHeadposition().getX() + distx,
                this.getHeadposition().getY() + disty
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
        final Point2D.Double headposition = new Point2D.Double(
            headx,
            heady
        );
        this.bodyparts = new Stack<>();
        this.bodyparts.add(headposition);
    }

    /**
     * Returns the bodyparts of the snake as Point2D-instances.
     * @return The bodyparts of the snake as Point2D-instances.
     */
    public Stack<Point2D> getBodyparts() {
        return this.bodyparts;
    }

    /**
     * The eating method of the snake. If there is a fruit on the head
     * the fruit will be eaten and the body is growing.
     * @param fruit The fruit as an ellipse which is eaten by the snake.
     */
    public void eat(final Ellipse2D fruit) {
        this.bodyparts.add(new Point2D.Double(fruit.getX(), fruit.getY()));
    }

    /**
     * The movement of the snake in the right direction with a given size.
     * @param movesize The size of the movement of the snake as double value.
     */
    public void moveRight(final double movesize) {
        this.getHeadposition().setLocation(
            this.getHeadposition().getX() + movesize,
            this.getHeadposition().getY()
        );
    }

    /**
     * The movement of the snake in the down direction with a given size.
     * @param movesize The size of the movement of the snake as double value.
     */
    public void moveDown(final double movesize) {
        this.getHeadposition().setLocation(
            this.getHeadposition().getX(),
            this.getHeadposition().getY() + movesize
        );
    }

    /**
     * The movement of the snake in the up direction with a given size.
     * @param movesize The size of the movement of the snake as double value.
     */
    public void moveUp(final double movesize) {
        this.getHeadposition().setLocation(
            this.getHeadposition().getX(),
            this.getHeadposition().getY() - movesize
        );
    }

    /**
     * The movement of the snake in the left direction with a given size.
     * @param movesize The size of the movement of the snake as double value.
     */
    public void moveLeft(final double movesize) {
        this.getHeadposition().setLocation(
            this.getHeadposition().getX() - movesize,
            this.getHeadposition().getY()
        );
    }

    @Override
    public void drawComponent(final Graphics2D graphic) {
        for (final Point2D bodypart : this.bodyparts) {
            graphic.fillRect(
                (int) bodypart.getX(),
                (int) bodypart.getY(),
                Snake.BODYPART_SIZE,
                Snake.BODYPART_SIZE
            );
        }
    }
}
