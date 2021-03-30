package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bartish.twentyfortyeight.utils.Tones;

public class Block extends Group {
    private int num;
    public boolean connected = false;

    private Image image;
    //private Label text;

    public Block(int num) {
        super();
        this.num = num;

        image = new Image(new Texture(Gdx.files.internal("block.png")));
        image.setPosition(0, 0);
        addActor(image);

//        Label.LabelStyle style = new Label.LabelStyle();
//        style.font = new BitmapFont(Gdx.files.internal("font.otf"));
//        style.fontColor = Color.WHITE;
        //text = new Label("kek",);
        //addActor(text);

        setSize(image.getWidth(), image.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        image.setColor(Tones.get(num));
        //text.setText(Math.pow(2, (num + 1)) + "");
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void doubleTheNumber() {
         num++;
    }

}
