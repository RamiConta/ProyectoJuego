package com.mygdx.game.pantallas;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.elementos.Bala;
import com.mygdx.game.elementos.Chico;
import com.mygdx.game.elementos.Entidad;
import com.mygdx.game.elementos.Zombie;
import com.mygdx.game.utiles.Configuracion;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

import red.Cliente;
import red.UtilesRed;

public class PantallaJuego implements Screen{
	
	//El juego es unicamente multiplayer, por lo que hay que crear y dibujar 2 personajes y asignarle una camara a cada uno 
	
	private TiledMap mapaBosque;
	private OrthogonalTiledMapRenderer renderer;

	private Chico chico1, chico2; 
	private OrthographicCamera camaraJugador1 , camaraJugador2;
	public Entidad bala1;
	public int idJugador = 0;
	
//creo una entidad zombie. 
	
	private Entidad zombie;
	
	
	//creo el cliente 
	Cliente cliente;
	
	public boolean volverAlMenu = false;
	
	 @Override
	public void show() {
		 cliente = new Cliente(this);
		//creamos el mapa y lo cargamos a traves de un cargador de mapas
		TmxMapLoader cargador = new TmxMapLoader();
		mapaBosque = cargador.load("mapas/Bosque.tmx");
		
		bala1 = new Bala(Recursos.BALA_SPRITE); //creamos la bala

		renderer = new OrthogonalTiledMapRenderer(mapaBosque);
		
		//creamos la camara para que el mapa se ubique de tal forma que entre en nuestra ventana

		camaraJugador1 = new OrthographicCamera(Configuracion.ancho, Configuracion.alto);
		camaraJugador1.setToOrtho(false);
		camaraJugador1.zoom = .9f;
		
		camaraJugador2 = new OrthographicCamera(Configuracion.ancho, Configuracion.alto);
		camaraJugador2.setToOrtho(false);
		camaraJugador2.zoom = .9f;
		
		 //Creamos al personaje y lo instanciamos.
		 chico1 = new Chico(Recursos.CHICO_SPRITE, camaraJugador1, 0,0);
		 chico2 = new Chico(Recursos.CHICO_SPRITE, camaraJugador2, 32*5,32*5);
		

		 //creo e instancio el zombie
		 zombie = new Zombie(Recursos.ZOMBIE_SPRITE);
		 
		 
		 UtilesRed.hc.setGame(this); //porque hc necesita modificar el juego constantemente
	}

	@Override
	 public void render(float delta) {

		
		if(!volverAlMenu) {	

		    Render.limpiarPantalla(0, 0, 0);

	    	renderer.render();
	    	
	    	Render.batch.begin();//hay algo en el update que tira error y no se que es
	    	
		    if(idJugador==0) { //le asigno la camara a cada jugador y la actualizo porque el rednerer solo puede usar una camara
		    	
		    	renderer.setView(camaraJugador1);
			    Render.batch.setProjectionMatrix(camaraJugador1.combined);
		    	camaraJugador1.update();
		    	chico1.update(delta);
		    
		    }else if(idJugador == 1){ // lo mismo
		    	renderer.setView(camaraJugador2);
			    Render.batch.setProjectionMatrix(camaraJugador2.combined);
		    	camaraJugador2.update(); 
		    	chico2.update(delta);  //aca esta el update separado
		    }
		    
		    Render.batch.end();

		    Render.batch.begin();  
		    chico1.dibujar();
		    chico2.dibujar(); // me surgio un problema con el update y tuve que separarlo del metodo dibujar() el update mas arriba 
		    bala1.dibujar();
		    
			 chico1.detectarColision(chico2.getColision());
			 chico2.detectarColision(chico1.getColision());
		    
		    zombie.dibujar();
		    zombie.update(chico1.posicion); //para que siga al jugador 1
		    Render.batch.end();
		}else {
			//Render.app.setScreen(new PantallaMenu());
		}
		}

	@Override
    public void resize(int width, int height) { //mas de lo mismo
		
		camaraJugador1.viewportWidth = width;
		camaraJugador1.viewportHeight = height;
		camaraJugador1.update();
		
		camaraJugador2.viewportWidth = width;
		camaraJugador2.viewportHeight = height;
		camaraJugador2.update();

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		mapaBosque.dispose();
		renderer.dispose();
		
	}
	
	
	public Chico getChico1(){
		
		return chico1;
	}
	
	public Chico getChico2(){
		
		return chico2;
	}

}
