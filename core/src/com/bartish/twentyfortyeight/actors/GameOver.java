package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameOver extends Image {
    public GameOver() {
        super(new Texture(Gdx.files.internal("gameOver.png")));

        setOrigin(getWidth() / 2, getHeight() / 2);

        addAction(alpha(0, 0));
        addAction(scaleTo(2f, 2f));
    }

    public void active() {
        addAction(parallel(
                alpha(1, 2),
                scaleTo(1, 1, 2)
        ));
    }
}
