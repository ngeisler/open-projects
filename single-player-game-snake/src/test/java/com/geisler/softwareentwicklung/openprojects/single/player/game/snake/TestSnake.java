/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.geom.Point2D;
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
public class TestSnake {

    /**
     * A static update value for test-cases with updated values.
     */
    public static final int UPDATE_VALUE = 50;

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
    public final void snakeShouldHaveOneBodyPartAfterInitialising() {
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
}
