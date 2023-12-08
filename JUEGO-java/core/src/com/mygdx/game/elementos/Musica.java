package com.mygdx.game.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Musica {
	
	public Music musicaMenu;	

	public void reproducirMusicaMenu() {
		
		musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("the-batman.wav.wav"));
	    musicaMenu.setLooping(true);
	    musicaMenu.play();	      
	}
	
}
