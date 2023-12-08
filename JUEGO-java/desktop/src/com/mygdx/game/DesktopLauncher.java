package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.utiles.Configuracion;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setWindowedMode(Configuracion.ANCHO, Configuracion.ALTO);
		
		config.setTitle("Kill Them All");
//		config.height= Configuracion.ALTO;
//		config.width= Configuracion.ANCHO;
		config.setResizable(false);
		config.setForegroundFPS(60);
//		config.vSyncEnabled = true;
		
		new Lwjgl3Application(new KillThemAll(), config);
		
		
	}
}
