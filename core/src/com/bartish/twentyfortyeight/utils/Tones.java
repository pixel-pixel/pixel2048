package com.bartish.twentyfortyeight.utils;

import com.badlogic.gdx.graphics.Color;
import com.bartish.twentyfortyeight.actors.Block;

/**
 * A Class-utility for working with colors of {@link Block}
 *
 * @author Andrew Bartish (pixel-pixel)
 * @version 1.0
 * @see Block#Block(int)
 * @see Block#addOneToNum()
 */
public class Tones {
    /**
     * A static const than contains all colors
     */
    private static final Color[] tones = {
            new Color(191 / 255f, 77 / 255f, 134 / 255f, 1),
            new Color(191 / 255f, 77 / 255f, 77 / 255f, 1),
            new Color(191 / 255f, 134 / 255f, 77 / 255f, 1),
            new Color(191 / 255f, 191 / 255f, 77 / 255f, 1),
            new Color(134 / 255f, 191 / 255f, 77 / 255f, 1),
            new Color(77 / 255f, 191 / 255f, 77 / 255f, 1),
            new Color(77 / 255f, 191 / 255f, 134 / 255f, 1),
            new Color(77 / 255f, 191 / 255f, 191 / 255f, 1),
            new Color(77 / 255f, 134 / 255f, 191 / 255f, 1),
            new Color(77 / 255f, 77 / 255f, 191 / 255f, 1),
            new Color(134 / 255f, 77 / 255f, 191 / 255f, 1),
            new Color(191 / 255f, 77 / 255f, 191 / 255f, 1)
    };

    /**
     * Return Color by index
     *
     * @param num Position in {@link Tones#tones}
     * @return Color by it position in {@link Tones#tones}
     */
    public static Color get(int num) {
        return tones[num];
    }
}
