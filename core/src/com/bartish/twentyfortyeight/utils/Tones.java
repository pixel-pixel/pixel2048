package com.bartish.twentyfortyeight.utils;

import com.badlogic.gdx.graphics.Color;

public class Tones {
    private static final Color[] tones = {
            new Color(191/255f, 77/255f, 134/255f, 1),
            new Color(191/255f, 77/255f, 77/255f, 1),
            new Color(191/255f, 134/ 255f, 77/255f, 1),
            new Color(191/255f, 191/255f, 77/255f, 1),
            new Color(134/255f, 191/255f, 77/255f, 1),
            new Color(77/255f, 191/255f, 77/255f, 1),
            new Color(77/255f, 191/255f, 134/255f, 1),
            new Color(77/255f, 191/255f, 191/255f, 1),
            new Color(77/255f, 134/255f, 191/255f, 1),
            new Color(77/255f, 77/255f, 191/255f, 1),
            new Color(134/255f, 77/255f, 191/255f, 1),
            new Color(191/255f, 77/255f, 191/255f, 1)
    };

    public static Color get(int num) {
        return tones[num];
    }
}
