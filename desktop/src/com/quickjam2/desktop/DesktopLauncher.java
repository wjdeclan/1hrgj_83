package com.quickjam2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quickjam2.QuickJam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Selarule - Game";
		config.width = 768;
		config.height = 576;
		config.resizable = false;
		new LwjglApplication(new QuickJam(), config);
	}
}
