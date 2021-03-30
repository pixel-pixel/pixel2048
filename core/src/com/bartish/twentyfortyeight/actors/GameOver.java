package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.bartish.twentyfortyeight.utils.Constants.*;

public class GameOver extends Image {
    public GameOver() {
        super(new Texture(Gdx.files.internal("gameOver.png")));

        setOrigin(getWidth() / 2, getHeight() / 2);

        addAction(parallel(
                alpha(0, 0),
                scaleTo(2f, 2f, 0)
        ));
    }

    public void active() {
        addAction(parallel(
                alpha(1, GAME_OVER_TIME),
                scaleTo(1, 1, GAME_OVER_TIME)
        ));
    }

    public void disactive()  {
        addAction(parallel(
                alpha(0, GAME_OVER_TIME),
                scaleTo(2f, 2f, GAME_OVER_TIME)
        ));
    }
}
