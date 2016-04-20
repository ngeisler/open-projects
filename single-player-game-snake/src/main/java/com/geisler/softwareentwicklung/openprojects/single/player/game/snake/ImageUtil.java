/**
 * Copyright (c) 2015, Nico Geisler Softwareentwicklung
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: no conditions.
 */
package com.geisler.softwareentwicklung.openprojects.single.player.game.snake;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * A class for util methods of image handling.
 *
 * @author Nico Geisler (geislern85@googlemail.com)
 * @version $Id$
 * @since 0.0.1
 */
@SuppressWarnings("PMD.ProhibitPublicStaticMethods")
public final class ImageUtil {

    /**
     * Not for instanciation.
     */
    private ImageUtil() {
    }

    /**
     * A static method to copy an bimage for checks.
     *
     * @param bimage The image to copy.
     * @return The copyed image as type BufferedImage.
     */
    public static BufferedImage deepCopy(final BufferedImage bimage) {
        final ColorModel cmodel = bimage.getColorModel();
        final boolean isalpremul = cmodel.isAlphaPremultiplied();
        final WritableRaster raster = bimage.copyData(null);
        return new BufferedImage(cmodel, raster, isalpremul, null);
    }

    /**
    * Compares two images pixel by pixel.
    *
    * @param imga The first image.
    * @param imgb The second image.
    * @return Whether the images are both the same or not.
    */
    public static boolean compareImages(
        final BufferedImage imga, final BufferedImage imgb) {
        boolean check = true;
        if (imga.getWidth() == imgb.getWidth()
            && imga.getHeight() == imgb.getHeight()) {
            final int width = imga.getWidth();
            final int height = imga.getHeight();
            for (int ycoord = 0; ycoord < height; ycoord += 1) {
                for (int xcoord = 0; xcoord < width; xcoord += 1) {
                    if (imga.getRGB(xcoord, ycoord)
                        != imgb.getRGB(xcoord, ycoord)) {
                        check = false;
                    }
                }
            }
        } else {
            check = false;
        }
        return check;
    }
}
