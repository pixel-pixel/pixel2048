package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.bartish.twentyfortyeight.utils.Constants;
import com.bartish.twentyfortyeight.utils.Tones;

/**
 * A class of game blocks with numbers. Must works with {@link GameBoard}
 *
 * @author pixel-pixel
 * @version 1.0
 * @see GameBoard
 */
public class Block extends Group {
    /**A number by which can know image for Block and Color via {@link Tones} <b>(not 2, 4, 8, 16... but 0, 1, 2, 3...)</b>*/
    private int num;

    /**An ancillary property to correct some problem. <b>Don`t use</b>*/
    public boolean connected = false;

    /**An image of Block`s background*/
    private Image back;

    private Image number;

    /**
     * A constructor which configure the Block
     * @param num Number of Block <b>(not 2, 4, 8, 16... but 0, 1, 2, 3...)</b>
     */
    public Block(int num) {
        super();
        this.num = num;

        back = new Image(new Texture(Gdx.files.internal("block.png")));
        back.setPosition(0, 0);
        back.setColor(Tones.get(num));
        addActor(back);

        number = new Image(new Texture(Gdx.files.internal("numbers/" + num + ".png")));
        number.setPosition((Constants.BLOCK_SIZE - number.getWidth()) / 2, (Constants.BLOCK_SIZE - number.getHeight()) / 2);
        addActor(number);

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
        back.setColor(Tones.get(num));

        number.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("numbers/" + num + ".png")))));
    }

}
