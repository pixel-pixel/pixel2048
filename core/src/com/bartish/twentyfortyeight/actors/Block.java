package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bartish.twentyfortyeight.utils.Constants;
import com.bartish.twentyfortyeight.utils.Tones;

public class Block extends Group {
    private int num;
    public boolean connected = false;

    private Image image;
//    private Label text;

    public Block(int num) {
        super();
        this.num = num;

        image = new Image(new Texture(Gdx.files.internal("block.png")));
        image.setPosition(0, 0);
        image.setColor(Tones.get(num));
        addActor(image);

        setSize(Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void doubleTheNumber() {
        num++;
        image.setColor(Tones.get(num));
    }

}
