package com.bartish.twentyfortyeight.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bartish.twentyfortyeight.Main;
import com.bartish.twentyfortyeight.constants.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = Constants.WINDOW_WIDTH;
		config.height = Constants.WINDOW_HEIGHT;
		config.title = Constants.WINDOW_TITLE;

		new LwjglApplication(new Main(), config);
	}
}
