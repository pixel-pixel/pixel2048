package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.bartish.twentyfortyeight.constants.Constants.*;

public abstract class Shield extends Stage {

    private static final ExtendViewport viewport = new ExtendViewport(GAME_WIDTH, GAME_HEIGHT);

    private float width = GAME_WIDTH;
    private float height = GAME_HEIGHT;

    public Shield() {
        super(viewport);
        //resize();
    }

    //protected abstract void restart();

    protected abstract void resize();

    protected abstract void remove();

    public final void resize(int width, int height){
        viewport.update(width, height, true);

        this.width = viewport.getWorldWidth();
        this.height = viewport.getWorldHeight();

        System.out.println(this.width);

        resize();
    }

    @Override
    public void dispose() {
        super.dispose();
        remove();
    }

    @Override
    public final float getWidth() {
        return width;
    }

    @Override
    public final float getHeight() {
        return height;
    }
}
