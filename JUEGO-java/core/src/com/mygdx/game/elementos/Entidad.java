package com.mygdx.game.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utiles.Configuracion;
import com.mygdx.game.utiles.Render;

public abstract class Entidad {
	
	private int vida = 100;
	private float velocidad = 30;
	private int daño = 20;
	public Vector2 posicion;
	private int ancho = 64, alto = 64;
	private int cantFrames = 4;
    private float tiempoAcumulado = 0;
    private float tiempoFrame = 0.1f;
	
	public Sprite spr;
	
	private TextureRegion[] frames;
	
	private int frameActual = 0;
	
	float movimientoX, movimientoY;
	
	
	public Entidad(String rutaImagenEntidad) {
		
		posicion = new Vector2(32*4,32*4);
		spr = new Sprite(new Texture(rutaImagenEntidad));
		
		spr.setPosition(posicion.x, posicion.y);
		
	}
	
	public void update (Vector2 posicionChico) {
		
		mirar(posicionChico);
		cargarAnimacion();
		spr.setPosition(posicion.x, posicion.y);
	}
	
	protected void mirar(Vector2 posicionChico) {
			float delta = Gdx.graphics.getDeltaTime();
		
		    float posChicoX = (posicionChico.x-32) - posicion.x; //le resto 32 ya que sino, la entidad mira al centro del sprite sheet en su totalidad (64*4 pixeles) cuando solo quiero que apunte a un solo frame
		    float posChicoY = (posicionChico.y-32) - posicion.y;

		    float anguloRadianes = MathUtils.atan2(posChicoY, posChicoX);
		    float anguloEnGrados = MathUtils.radiansToDegrees * anguloRadianes;

		    // Ajuste para que el ángulo sea positivo y esté en el rango correcto (0 a 360 grados)
		    anguloEnGrados = (anguloEnGrados + 360) % 360;

		    spr.setOrigin(ancho / 2, alto / 2);
		    spr.setRotation(anguloEnGrados);  //lo multiplique por -1 porque la rotacion estaba invertida y esto lo soluciono 
		    
		    if(posicion.x > posicionChico.x-32) {
		    	posicion.x-= velocidad *delta; 
		    }else if(posicion.x < posicionChico.x-32) {
		    	posicion.x+= velocidad *delta;
		    }
		    
		    if(posicion.y > posicionChico.y-32){
		    posicion.y-= velocidad *delta;
		    }else if(posicion.y < posicionChico.y-32) {
		    	posicion.y+= velocidad *delta;
		    }
		    
		
	}
	
	protected void mirar(Vector2 posicionChico, float angulo) {
		
		float delta = Gdx.graphics.getDeltaTime();
	
	    spr.setOrigin(ancho / 2, alto / 2);
	    spr.setRotation(angulo);  //lo multiplique por -1 porque la rotacion estaba invertida y esto lo soluciono 
	    spr.setPosition(posicionChico.x-32, posicionChico.y-32);
	    
		movimientoX = MathUtils.cosDeg(angulo);
		movimientoY = MathUtils.sinDeg(angulo);
		
		posicion.x = posicionChico.x - 32;
		posicion.y = posicionChico.y - 32;
}
	
	protected void moverBala(Vector2 posicionChico) {
		
		posicion.x += movimientoX*10;
		posicion.y += movimientoY*10;
		
		
		spr.setPosition(posicion.x, posicion.y);
	}
	
	private void cargarAnimacion() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		
		Texture texturaChico = new Texture("Animaciones/zombie1.png");

//		chicoSpr = new Sprite(texturaChico);
		spr.setSize(ancho, alto);
		
		TextureRegion[][] animacion = TextureRegion.split(texturaChico, ancho, alto);

		frames = new TextureRegion[cantFrames];

		int indice = 0;

		for (int i = 0; i < cantFrames; i++) {
			frames[i] = animacion[0][i];
		}
		
        tiempoAcumulado += deltaTime;

        if (tiempoAcumulado >= tiempoFrame) {
            tiempoAcumulado -= tiempoFrame;
            frameActual = (frameActual + 1) % frames.length;
            spr.setRegion(frames[frameActual]);
        }
    
    
    }
	
	
	public void dibujar() {
		spr.draw(Render.batch);
	}
	
	
	
}
