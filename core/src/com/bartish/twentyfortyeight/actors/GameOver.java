package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bartish.twentyfortyeight.shields.GameShield;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.bartish.twentyfortyeight.utils.Constants.*;

/**
 * A class for animated end of the game
 *
 * @author pixel-pixel
 * @version 1.0
 * @see GameShield#act()
 */
public class GameOver extends Image {

    /**
     * A constructor which by default make image invisible, double-sized and set it origin in center of image
     */
    public GameOver() {
        super(new Texture(Gdx.files.internal("gameOver.png")));

        setOrigin(getWidth() / 2, getHeight() / 2);

        addAction(parallel(
                alpha(0, 0),
                scaleTo(2f, 2f, 0)
        ));
    }

    /**
     * Add animation to image. It start to be visible and normal-sized
     *
     * @see GameShield#act()
     */
    public void active() {
        addAction(parallel(
                alpha(1, GAME_OVER_TIME),
                scaleTo(1, 1, GAME_OVER_TIME)
        ));
    }

    /**
     * Add animation to image. It start to be invisible and double-sized
     *
     * @see GameShield#restart()
     */
    public void disactive() {
        addAction(parallel(
                alpha(0, GAME_OVER_TIME),
                scaleTo(2f, 2f, GAME_OVER_TIME)
        ));
    }
}