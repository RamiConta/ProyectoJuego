package com.mygdx.game.pantallas;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.elementos.Chico;
import com.mygdx.game.utiles.Configuracion;
import com.mygdx.game.utiles.Render;

public class PantallaJuego implements Screen{
	
	private TiledMap mapaBosque;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camara;
	
	private Chico chico; 
	
	private static final int cantFrames = 4;

	 SpriteBatch batch;
	
	 @Override
	public void show() {
		//creamos el mapa y lo cargamos a traves de un cargador de mapas
		TmxMapLoader cargador = new TmxMapLoader();
		mapaBosque = cargador.load("mapas/Bosque.tmx");
		
		renderer = new OrthogonalTiledMapRenderer(mapaBosque);
				
		batch= Render.batch;	
		
		//creamos la camara para que el mapa se ubique de tal forma que entre en nuestra ventana
		camara = new OrthographicCamera();
		camara.setToOrtho(false, Configuracion.ANCHO, Configuracion.ALTO);
		
		//Creamos al personaje y lo instanciamos.
		 chico = new Chico("Animaciones/Chico_CORRER.png", cantFrames, 64, 64);

		
	}

	@Override
	 public void render(float delta) {
//		    Render.limpiarPantalla(0, 0, 0);
//		    
//		    // Actualiza la cámara antes de renderizar el mapa
//		    camara.update();
//		    
//		    renderer.setView(camara);
//		    renderer.render();
//		    
//		    renderer.getBatch().setProjectionMatrix(camara.combined);
//		    
//		    batch.begin();
//		    
//		    // Establece la cámara para el personaje antes de dibujarlo
//		    chico.establecerCamara(camara);
//		    chico.dibujar();
//		    batch.end();

		    Render.limpiarPantalla(0, 0, 0);

		    camara.update();
		    renderer.setView(camara);
		    renderer.render();

		    renderer.getBatch().setProjectionMatrix(camara.combined);

		   batch.begin();

		    chico.establecerCamara(camara);
		    chico.dibujar();

		   batch.end();
		}

	 

	@Override
    public void resize(int width, int height) {
        // Actualizar la cámara cuando la ventana cambia de tamaño
        camara.setToOrtho(false, width, height);
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
		
		batch.dispose();
		
//		tengo que poner personaje.dispose();
	}
	
	

}
