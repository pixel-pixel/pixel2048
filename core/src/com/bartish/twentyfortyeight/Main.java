package com.bartish.twentyfortyeight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bartish.twentyfortyeight.shields.GameShield;
import com.bartish.twentyfortyeight.shields.Shield;

public class Main extends ApplicationAdapter {
	Shield shield;
	
	@Override
	public void create () {
		shield = new GameShield();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(250/255f, 248/255f, 240/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shield.act();
		shield.draw();
	}

	@Override
	public void resize(int width, int height) {
		shield.resize(width, height);
	}

	@Override
	public void dispose () {
		shield.dispose();
	}
}
