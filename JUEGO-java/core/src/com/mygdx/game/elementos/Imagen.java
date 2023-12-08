package com.mygdx.game.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.utiles.Configuracion;
import com.mygdx.game.utiles.Render;

public class Imagen {

	private Texture textura;
	private Sprite sprite;
	
	public Imagen(String rutaImagen){
		textura = new Texture(rutaImagen);
		sprite = new Sprite(textura);
	}
	
	public void dibujar(){
		sprite.draw(Render.batch);
	}
	
	public void setSize(float ancho, float alto) {
		sprite.setSize(Configuracion.ANCHO, Configuracion.ALTO);
		
	}
}