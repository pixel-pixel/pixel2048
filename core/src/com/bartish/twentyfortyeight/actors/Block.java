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

/**
 * A class of game blocks with numbers. Must works with {@link GameBoard}
 *
 * @author Andrew Bartish (pixel-pixel)
 * @version 1.0
 * @see GameBoard
 */
public class Block extends Group {
    /**A number by which can know image for Block and Color via {@link Tones} <b>(not 2, 4, 8, 16... but 0, 1, 2, 3...)</b>*/
    private int num;

    /**An ancillary property to correct some problem. <b>Don`t use</b>*/
    public boolean connected = false;

    /**An image of Block`s background*/
    private Image image;

    /**
     * A constructor which configure the Block
     * @param num Number of Block <b>(not 2, 4, 8, 16... but 0, 1, 2, 3...)</b>
     */
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

    /**
     * A getter of {@link Block#num}
     * @return Number of Block <b>(not 2, 4, 8, 16... but 0, 1, 2, 3...)</b>
     */
    public int getNum() {
        return num;
    }

    /**
     * Add +1 to {@link Block#num} and change it Color to new by {@link Tones}
     */
    public void addOneToNum() {
        num++;
        image.setColor(Tones.get(num));
    }

}
