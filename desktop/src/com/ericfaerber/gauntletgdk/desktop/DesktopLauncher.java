package com.ericfaerber.gauntletgdk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ericfaerber.gauntletgdk.Gauntlet;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//                config.height = 600;
//                config.width = 800;
//                config.fullscreen = true;
		config.resizable = false;
                config.foregroundFPS = 60;
                
                new LwjglApplication(new Gauntlet(), config);
	}
}
