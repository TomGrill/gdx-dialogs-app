package de.tomgrill.gdxdialogs.app.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.tomgrill.gdxdialogs.app.GdxDialogsGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 800;
		new LwjglApplication(new GdxDialogsGame(), config);
	}
}
