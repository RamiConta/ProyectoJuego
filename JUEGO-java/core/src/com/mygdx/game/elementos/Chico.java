package com.mygdx.game.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.inout.Entradas;
import com.mygdx.game.utiles.Render;

public class Chico {

	public Sprite chico;	
//	public Texture textura;
	private float veloPersonaje = 80;
    private TextureRegion[] frames;
    private int frameActual = 0;
    
//valores para la velocidad de la animacion, esto es porque tengo pocos frames en la animacion y necesito que renderice mas lento
    private float tiempoFrame = 0.1f; 
    private float tiempoAcumulado = 0;
//creamos la camara
    
    private OrthographicCamera camara;
	
    
	public Chico (String rutaImagenChico, int cantidadFrames, int anchoFrame, int altoFrame) {
		
		 chico = new Sprite();
		
		cargarAnimacion(rutaImagenChico, cantidadFrames, 64, 64);
		
		chico.setFlip(false, false);
    }
	
	public void establecerCamara(OrthographicCamera camara) {
		
        this.camara = camara;
    }

	private void cargarAnimacion(String rutaChico, int cantidadFrames, int anchoFrame, int altoFrame) {

		Texture texturaChico = new Texture("Animaciones/Chico_CORRER.png");

		chico = new Sprite(texturaChico);
		chico.setSize(anchoFrame, altoFrame);
		
		TextureRegion[][] animacion = TextureRegion.split(texturaChico, anchoFrame, altoFrame);

		frames = new TextureRegion[cantidadFrames];

		int indice = 0;

		for (int i = 0; i < cantidadFrames; i++) {
			frames[i] = animacion[0][i];
		}
    }
	
	public void dibujar() {
		update(Gdx.graphics.getDeltaTime());
		
		chico.draw(Render.batch);
//		 spr.draw(Render.batch);  //ya no me funciona eso
	}
	
	
	public void update(float delta) {
		
//		float disminuirVelocidad = 1.5f; //esto funciona pero al final no lo necesito
		
//		tiempoAcumulado += delta; // * disminuirVelocidad;
//
//		if (tiempoAcumulado >= tiempoFrame) { 
//			tiempoAcumulado -= tiempoFrame; 				
//			frameActual = (frameActual + 1) % frames.length;//con esto conseguimos que cuando llegue al ultimo frame se divida por el total de frames
//		}											//es decir, por 4, y vuelva a empezar en el array de frames.	
		       movPersonaje(delta);
		       if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)
		               && !Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
		           miraMouse();
		       }
	}
	
	public void setX(float x) {
		chico.setX(x);
	}
	
	public void setY(float y) {
		chico.setY(y);
	}

	public float getX() {
		
		return chico.getX();
	}
	
	public float getY() {
		
		return chico.getY();
	}
		//Ya funciona el metodo y se mueve
	
	
	private void movPersonaje(float deltaTime) {
		
	    float nuevaPosIzquierda = chico.getX() - veloPersonaje * deltaTime;
	    float nuevaPosDerecha = chico.getX() + veloPersonaje * deltaTime;
	    float nuevaPosArriba = chico.getY() + veloPersonaje * deltaTime;
	    float nuevaPosAbajo = chico.getY() - veloPersonaje * deltaTime;

	    boolean seMueve = false;

	    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
	        chico.setX(nuevaPosIzquierda);
	        seMueve = true;
	        
	    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
	        chico.setX(nuevaPosDerecha);
	        seMueve = true;
	    }

	    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
	        chico.setY(nuevaPosArriba);
	        seMueve = true;
	        
	    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
	        chico.setY(nuevaPosAbajo);
	        seMueve = true;
	    }

	    if (seMueve) {
	    	
	        tiempoAcumulado += deltaTime;

	        if (tiempoAcumulado >= tiempoFrame) {
	            tiempoAcumulado -= tiempoFrame;
	            frameActual = (frameActual + 1) % frames.length;
	            chico.setRegion(frames[frameActual]);
	        }
	        
	    } else {
	//        frameActual = 0;  // para que el personaje esté quieto
	    }
	    
	}

	private void miraMouse() {
	    // Obtener la posición del personaje
	    float personajeX = chico.getX() + chico.getWidth() / 2;
	    float personajeY = chico.getY() + chico.getHeight() / 2;

	    // Calcular la dirección del mouse con respecto al personaje
	    float direccionX = getMouseX() - personajeX;
	    float direccionY = getMouseY() - personajeY;

	    // Calcular el ángulo de rotación en radianes
	    float angulo = MathUtils.atan2(direccionY, direccionX);

	    // Convertir el ángulo a grados y establecer la rotación del sprite
	    chico.setRotation(MathUtils.radiansToDegrees * angulo);
	}



//	private void miraMouse() {
//	    float mouseX = Gdx.input.getX();
//	    float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//	    float personajeX = chico.getX() + chico.getWidth() / 2;
//	    float personajeY = chico.getY() + chico.getHeight() / 2;
//
//	    float direccionX = mouseX - personajeX;
//	    float direccionY = mouseY - personajeY;
//
//	    float distancia = Vector2.dst(personajeX, personajeY, mouseX, mouseY);
//	    float umbralDistancia = 50f;  // Ajusta este valor según sea necesario
//
//	    if (distancia > umbralDistancia) {
//	        float angulo = MathUtils.atan2(direccionY, direccionX);
//	        chico.setRotation(MathUtils.radiansToDegrees * angulo);
//	    }
//	}

	

}
