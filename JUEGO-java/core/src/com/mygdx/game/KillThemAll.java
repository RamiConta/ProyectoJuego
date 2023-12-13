package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.elementos.Chico;
import com.mygdx.game.pantallas.PantallaJuego;
import com.mygdx.game.pantallas.PantallaMenu;
import com.mygdx.game.utiles.Render;

import red.UtilesRed;

public class KillThemAll extends Game {
	public SpriteBatch batch;
//SpriteBatch batch;

	@Override
	public void create () {
		Render.app = this;
		batch = new SpriteBatch();
		Render.batch = batch;
				
		this.setScreen(new PantallaMenu());
	//	this.setScreen(new PantallaJuego());
	
}
	

	@Override
	public void render () {
		super.render();
	
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		batch.begin();
		 
//		 batch.draw(personaje, 0, 0);
		
//		batch.end();

}

	@Override
	

	public void dispose () {	
		Render.batch.dispose();
//		super.dispose();
		UtilesRed.hc.fin();//Aca tengo que cerrar el hilo asi no me queda abierto en segundo plano
	
	}

}