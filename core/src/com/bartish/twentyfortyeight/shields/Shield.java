package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bartish.twentyfortyeight.Main;

import static com.bartish.twentyfortyeight.utils.Constants.*;

/**
 * Add-on over the <code>Stage</code> class for easier work when resizing the screen and storing screen data
 *
 * @author pixel-pixel
 * @version 1.0
 * @see Stage
 * */
public abstract class Shield extends Stage {

    /**
     * Some viewport for easily working with resizing pixel game.
     */
    private static final ExtendViewport viewport = new ExtendViewport(GAME_WIDTH, GAME_HEIGHT);

    /**
     * Save worldWidth at this moment.
     *
     * @see ExtendViewport#getWorldWidth()
     */
    private float width = GAME_WIDTH;

    /**
     * Save worldHeight at this moment.
     *
     * @see ExtendViewport#getWorldHeight()
     */
    private float height = GAME_HEIGHT;

    /**
     * Constructor that pass <code>ExtendViewport</code> to super constructor for simplify code.
     *
     * @see Stage#Stage(Viewport)
     */
    public Shield() {
        super(viewport);
    }

    /**
     * Final method for easily work with resizing.
     *
     * @param width Window width.
     * @param height Window height.
     * @see #resize()
     */
    public final void resize(int width, int height){
        viewport.update(width, height, true);

        this.width = viewport.getWorldWidth();
        this.height = viewport.getWorldHeight();

        System.out.println(this.width);

        resize();
    }

    /**
     * For change some positions of <code>Actor</code> when window is resizing.
     */
    protected abstract void resize();

    /**
     * Simplify saving screen data.
     *
     * @return Is save is done.
     * @see Main#pause()
     */
    public boolean save() {
        return true;
    }

    /**
     * Simplify loading screen data.
     *
     * @return Is load is done.
     */
    public boolean load() {
        return true;
    }

    /**
     * Getter for WorldWidth at this moment.
     * @return {@link #width}
     */
    @Override
    public final float getWidth() {
        return width;
    }

    /**
     * Getter for WorldHeight at this moment.
     * @return {@link #height}
     */
    @Override
    public final float getHeight() {
        return height;
    }
}
