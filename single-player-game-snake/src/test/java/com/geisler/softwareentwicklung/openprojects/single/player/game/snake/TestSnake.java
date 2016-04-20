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
import java.awt.image.BufferedImage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * The test game class for the tests of the snake game.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
@SuppressWarnings("PMD.TooManyMethods")
public class TestSnake {

    /**
     * A static update value for test-cases with updated values.
     */
    public static final int UPDATE_VALUE = 50;

    /**
     * A static move size value for test-cases a moving snake.
     */
    public static final double MOVE_SIZE = 5;

    /**
     * The class under test.
     */
    private final Snake cut;

    /**
     * The constructor to initilize the final members of the test class.
     */
    public TestSnake() {
        this.cut = new Snake();
    }

    /**
     * Test should ckeck if the snake have no headposition
     * after instanciating.
     */
    @Test
    public final void
        snakeShouldHaveNoHeadPositionIfSnakeIsInstanciated() {
        MatcherAssert.assertThat(
            this.cut.getHeadposition(),
            Matchers.nullValue()
        );
    }

    /**
     * Test should ckeck if the snake have headposition to center of view
     * after instanciating and initialising.
     */
    @Test
    public final void
        snakeShouldHaveCenterHeadPositionOfViewIfSnakeIsInitialised() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        MatcherAssert.assertThat(
            this.cut.getHeadposition(),
            Matchers.equalTo(
                new Point2D.Double(
                    GameSnake.DEFAULT_WIDTH / 2,
                    GameSnake.DEFAULT_HEIGHT / 2
                )
            )
        );
    }

    /**
     * Test should ckeck if the snake have an updated headposition
     * after initialising and updating.
     */
    @Test
    public final void
        snakeShouldHaveUpdatedHeadPositionIfSnakeIsInitialisedAndUpdated() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        this.cut.updateHeadpositionByDistanceCoordinates(
            TestSnake.UPDATE_VALUE,
            TestSnake.UPDATE_VALUE
        );
        MatcherAssert.assertThat(
            this.cut.getHeadposition(),
            Matchers.equalTo(
                new Point2D.Double(
                    GameSnake.DEFAULT_WIDTH / 2 + TestSnake.UPDATE_VALUE,
                    GameSnake.DEFAULT_HEIGHT / 2 + TestSnake.UPDATE_VALUE
                )
            )
        );
    }

    /**
     * Test should check if snake has an initial bodypart after initialising.
     */
    @Test
    public final void snakeShouldHaveOneBodypartAfterInitialising() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        MatcherAssert.assertThat(
            this.cut.getBodyparts(),
            Matchers.notNullValue()
        );
        MatcherAssert.assertThat(
            this.cut.getBodyparts(),
            Matchers.not(Matchers.empty())
        );
    }

    /**
     * Checks if a snake have a new bodypart after eating a fruit.
     */
    @Test
    public final void snakeShouldHaveANewBodypartAfterEatingAFruit() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        final Ellipse2D fruit = new Ellipse2D.Double(
            TestSnake.UPDATE_VALUE,
            TestSnake.UPDATE_VALUE,
            2,
            2
        );
        this.cut.eat(fruit);
        MatcherAssert.assertThat(
            this.cut.getBodyparts(),
            Matchers.containsInAnyOrder(
                new Point2D.Double(
                    TestSnake.UPDATE_VALUE,
                    TestSnake.UPDATE_VALUE
                ),
                new Point2D.Double(
                    GameSnake.DEFAULT_WIDTH / 2,
                    GameSnake.DEFAULT_HEIGHT / 2
                )
            )
        );
    }

    /**
     * Checks if a snake have a larger head x position after moving to right.
     */
    @Test
    public final void snakeShouldHaveALargerHeadXPosAfterMovingRight() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        final double oldx = this.cut.getHeadposition().getX();
        this.cut.moveRight(TestSnake.MOVE_SIZE);
        MatcherAssert.assertThat(
            this.cut.getHeadposition().getX(),
            Matchers.greaterThan(oldx)
        );
    }

    /**
     * Checks if a snake have a larger head y position after moving to right.
     */
    @Test
    public final void snakeShouldHaveALargerHeadYPosAfterMovingDown() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        final double oldy = this.cut.getHeadposition().getY();
        this.cut.moveDown(TestSnake.MOVE_SIZE);
        MatcherAssert.assertThat(
            this.cut.getHeadposition().getY(),
            Matchers.greaterThan(oldy)
        );
    }

    /**
     * Checks if a snake have a lesser head x position after moving to left.
     */
    @Test
    public final void snakeShouldHaveALesserHeadXPosAfterMovingLeft() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        final double oldx = this.cut.getHeadposition().getX();
        this.cut.moveLeft(TestSnake.MOVE_SIZE);
        MatcherAssert.assertThat(
            this.cut.getHeadposition().getX(),
            Matchers.lessThan(oldx)
        );
    }

    /**
     * Checks if a snake have a lesser head y position after moving to up.
     */
    @Test
    public final void snakeShouldHaveALesserHeadYPosAfterMovingUp() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        final double oldy = this.cut.getHeadposition().getY();
        this.cut.moveUp(TestSnake.MOVE_SIZE);
        MatcherAssert.assertThat(
            this.cut.getHeadposition().getY(),
            Matchers.lessThan(oldy)
        );
    }

    /**
     * Checks if a snake is a drawable component.
     */
    @Test
    public final void snakeShouldBeAbleToDrawItSelf() {
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        MatcherAssert.assertThat(
            this.cut,
            Matchers.instanceOf(Drawable.class)
        );
    }

    /**
     * Checks when a snake is drawn, that there are rectangles on graphics.
     */
    @Test
    public final void snakeShouldBeVisibleOnGraphicsAfterDrawing() {
        final BufferedImage bimage = new BufferedImage(
            GameSnake.DEFAULT_WIDTH,
            GameSnake.DEFAULT_HEIGHT,
            BufferedImage.TYPE_INT_ARGB
        );
        final Graphics2D graphicstwo = bimage.createGraphics();
        final BufferedImage bicopy = ImageUtil.deepCopy(bimage);
        this.cut.init(GameSnake.DEFAULT_WIDTH, GameSnake.DEFAULT_HEIGHT);
        this.cut.drawComponent(graphicstwo);
        graphicstwo.dispose();
        MatcherAssert.assertThat(
            ImageUtil.compareImages(bimage, bicopy),
            Matchers.is(false)
        );
    }
}
