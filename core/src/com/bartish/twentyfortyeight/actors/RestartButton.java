package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bartish.twentyfortyeight.shields.GameShield;

/**
 * A class for display restart button and check if it click
 *
 * @author Andrew Bartish (pixel-pixel)
 * @version 1.0
 * @see GameShield#start()
 */
public class RestartButton extends Group {
    private Image button;

    /**
     * A Constructor which create images for button and configures them
     */
    public RestartButton() {
        super();

        button = new Image(new Texture(Gdx.files.internal("block.png")));
        addActor(button);

        setSize(button.getWidth(), button.getHeight());
    }

    /**
     * Set size of images to button`s size
     *
     * @param delta Time between frames
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        button.setSize(getWidth(), getHeight());
    }
}
