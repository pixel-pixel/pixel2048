package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RestartButton extends Group {
    private Image button;

    public RestartButton() {
        super();

        button = new Image(new Texture(Gdx.files.internal("block.png")));
        addActor(button);

        setSize(button.getWidth(), button.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        button.setSize(getWidth(), getHeight());
    }
}
