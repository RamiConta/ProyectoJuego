package com.mygdx.game.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utiles.Configuracion;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

import red.UtilesRed;

public class Chico {

	public Sprite chicoSpr;	
	private Sprite balaSpr;  //no lo estoy usando
	public Vector2 posicion;
	private boolean puedeMoverse = true, estaChocando = false;
//	public Texture textura;
	
	private float veloPersonaje = 90;
	
    private TextureRegion[] frames;
    private int frameActual = 0;
    public int idCliente =3;
    
    
    
//valores para la velocidad de la animacion, esto es porque tengo pocos frames en la animacion y necesito que renderice mas lento
    private float tiempoFrame = 0.1f; 
    private float tiempoAcumulado = 0;

    private final int cantidadFrames = 4;
    private final int anchoFrame = 64;
    private final int altoFrame = 64;
	
 // variables para la bala   
    private float tiempoDeVida = 0;
	private float tiempoMaxDeVida = 50;
	
	
	public boolean puedeDisparar = true;
	
	public int rangoDeGolpe = 32;
	
// cramos la bala    
	public float anguloEnGrados;
    private Entidad bala;
    
//creamos la camara
	public OrthographicCamera camara;
    
// creamos la colision	
	private Rectangle colision;
	private String direccion = "";
	private String direccionChoque = "";
	
	public Chico (String rutaImagenChico, OrthographicCamera camara, int x, int y) {

		//bala = new Bala(Recursos.BALA_SPRITE); //creamos la bala
		
		
		posicion = new Vector2(x,y); //posicion establecida del chico
		chicoSpr = new Sprite(new Texture(rutaImagenChico));
		 this.camara = camara;

		 chicoSpr.setX(posicion.x);
		 chicoSpr.setY(posicion.y);
		 colision = new Rectangle(posicion.x, posicion.y, 32, 32);
		 
		 this.idCliente = UtilesRed.hc.idCliente;
		 
		cargarAnimacion(rutaImagenChico, cantidadFrames, anchoFrame, altoFrame);
		
		punteroCustomizado();

    }

	private void cargarAnimacion(String rutaChico, int cantidadFrames, int anchoFrame, int altoFrame) {

		Texture texturaChico = new Texture(Recursos.CHICO_SPRITE);

//		chicoSpr = new Sprite(texturaChico);
		chicoSpr.setSize(anchoFrame, altoFrame);
		
		TextureRegion[][] animacion = TextureRegion.split(texturaChico, anchoFrame, altoFrame);

		frames = new TextureRegion[cantidadFrames];

		int indice = 0;

		for (int i = 0; i < cantidadFrames; i++) {
			frames[i] = animacion[0][i];
		}
    }
	
	public void dibujar() {	
		chicoSpr.draw(Render.batch);
		
//		 spr.draw(Render.batch);  //ya no me funciona eso
	}
	
	
	public void update(float delta) {
		
//		float disminuirVelocidad = 1.5f; //esto funciona pero al final no lo necesito				
													//con esto conseguimos que cuando llegue al ultimo frame se divida por el total de frames
//		}											//es decir, por 4, y vuelva a empezar en el array de frames.	
		       movimientoInstruccion();
		       apuntar();
		       disparar();
	}
	
	
//teniendo la posicion del jugador y del mouse, calculas la tangente restando las posiciones de los mismos y con el angulo, utilizando radianes
// le seteas la rotacion al personaje y utilizo setOrigin para que gire sobre su propio eje.
	
	private void apuntar() {
	    float mouseX = Gdx.input.getX() - (Configuracion.ancho / 2);
	    float mouseY = Gdx.input.getY() - (Configuracion.alto / 2);

	    float anguloRadianes = MathUtils.atan2(mouseY, mouseX);
	    anguloEnGrados = MathUtils.radiansToDegrees * anguloRadianes;

	    // Ajuste para que el ángulo sea positivo y esté en el rango correcto (0 a 360 grados)
	    anguloEnGrados = (anguloEnGrados + 360) % 360;

	    chicoSpr.setOrigin(anchoFrame / 2, altoFrame / 2);
	    chicoSpr.setRotation(anguloEnGrados*-1);  //lo multiplique por -1 porque la rotacion estaba invertida y esto lo soluciono    
	    UtilesRed.hc.enviarMensaje("rotacion#"+idCliente+"#"+anguloEnGrados*-1);
	    
	}
	
	

	public void movimientoInstruccion() {
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
	    boolean seMueve = false;
	    	

	    if (Gdx.input.isKeyPressed(Input.Keys.A) && !direccionChoque.equals("izquierda")) {
	    	
	    	direccion = "izquierda"; 
	    	
	    	UtilesRed.hc.enviarMensaje("Movimiento#izquierda");
	    	
	        seMueve = true;
	        
	    } else if (Gdx.input.isKeyPressed(Input.Keys.D)&& !direccionChoque.equals("derecha")) {
	    	
	    	direccion = "derecha";
	    	
	    	UtilesRed.hc.enviarMensaje("Movimiento#derecha");
	        
	        seMueve = true;
	    }

	    if (Gdx.input.isKeyPressed(Input.Keys.W)&& !direccionChoque.equals("arriba")) {
	    	
	    	direccion = "arriba" ; 
	    	
	    	UtilesRed.hc.enviarMensaje("Movimiento#arriba");
	        seMueve = true;
	        
	    } else if (Gdx.input.isKeyPressed(Input.Keys.S)&& !direccionChoque.equals("abajo")) {
	    	
	    	direccion = "abajo";
	    	
	    	UtilesRed.hc.enviarMensaje("Movimiento#abajo");
	        seMueve = true;
	    }

	    if (seMueve) {
	    	
	        tiempoAcumulado += deltaTime;

	        if (tiempoAcumulado >= tiempoFrame) {
	            tiempoAcumulado -= tiempoFrame;
	            frameActual = (frameActual + 1) % frames.length;
	            chicoSpr.setRegion(frames[frameActual]);
	        }
	        
	    } else {
	        frameActual = 0;  // para que el personaje esté quieto
	    }
	    
	    chicoSpr.setX(posicion.x-(anchoFrame/2));
	    chicoSpr.setY(posicion.y-(altoFrame/2));
	    
	    //moverCamara();
    
	}
		
	public void actualizarPosEnRed(float x, float y ) {
		float delta = Gdx.graphics.getDeltaTime();
		
		posicion.x = x;
		posicion.y = y;
		
	    colision.setPosition(posicion);//actualiza posicion de colision
		
		chicoSpr.setPosition(posicion.x-(anchoFrame/2), posicion.y-(altoFrame/2));//aca tenia el mismo error de siempre con el spritesheet

        tiempoAcumulado += Gdx.graphics.getDeltaTime();

        if (tiempoAcumulado >= tiempoFrame) {
            tiempoAcumulado -= tiempoFrame;
            frameActual = (frameActual + 1) % frames.length;
            chicoSpr.setRegion(frames[frameActual]);
        }
	}
	
	
	public void rotarEnRed(float angulo) {
	    chicoSpr.setOrigin(anchoFrame / 2, altoFrame / 2);
	    chicoSpr.setRotation(angulo);//lo multiplique por -1 porque la rotacion estaba invertida y esto lo soluciono 
	}
	
	//Reemplaza el cursor por la imagen de la mira, es decir, convierte el cursor en una mira que yo quiera, (https://libgdx.com/wiki/input/cursor-visibility-and-catching)
	
	private void punteroCustomizado() {
		Pixmap pixmap = new Pixmap(Gdx.files.internal(Recursos.MIRA_SPRITE));
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		pixmap.dispose(); 
		Gdx.graphics.setCursor(cursor);

	}
// metodo para disparar el arma


	
	private void disparar() {
//creamos la bala y la instanciamos		
		if(Gdx.input.isTouched()) {
			//bala.mirar(posicion, anguloDeLaBala);
		
			UtilesRed.hc.enviarMensaje("bala#"+idCliente);
		}
		
	

		
		//System.out.println(tiempoDeVida);
	}

	public void moverCamara() {	
        camara.position.set(posicion.x, posicion.y, 0);
        camara.update();

	}
	
	public void detectarColision(Rectangle colision) {
		if(this.colision.overlaps(colision)) {
			if(!estaChocando) {
				direccionChoque = direccion; 
				estaChocando = true;
			}
		}else {
			estaChocando = false;
			direccionChoque = "";
		}
	}

	public Rectangle getColision() {
		return colision;
	}

}
