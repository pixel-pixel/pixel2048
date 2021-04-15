package com.bartish.twentyfortyeight;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bartish.twentyfortyeight.shields.GameShield;
import com.bartish.twentyfortyeight.shields.Shield;

/**
 * Extends <code>ApplicationAdapter</code> and just override methods for enter, pause, resume... the game.
 *
 * @author pixel-pixel
 * @version 1.0
 * @see ApplicationListener
 */
public class Main extends ApplicationAdapter {
    /**
     * For manipulation with game screens.
     *
     * @see Stage
     */
    private Shield shield;

    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        shield = new GameShield();
    }

    /**
     * Called when the {@link Application} should render itself.
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(250 / 255f, 248 / 255f, 240 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shield.act();
        shield.draw();
    }

    /**
     * Called when the {@link Application} is paused, usually when it's not active or visible on-screen. An Application is also
     * paused before it is destroyed.
     */
    @Override
    public void pause() {
        super.pause();
        shield.save();
    }

    /**
     * Called when the {@link Application} is resized. This can happen at any point during a non-paused state but will never happen
     * before a call to {@link #create()}.
     *
     * @param width  the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        shield.resize(width, height);
    }

    /**
     * Called when the {@link Application} is destroyed. Preceded by a call to {@link #pause()}.
     */
    @Override
    public void dispose() {
        shield.dispose();
    }
}
