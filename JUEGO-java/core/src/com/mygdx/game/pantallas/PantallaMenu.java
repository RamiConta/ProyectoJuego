package com.mygdx.game.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.elementos.Imagen;
import com.mygdx.game.elementos.Texto;
import com.mygdx.game.inout.Entradas;
import com.mygdx.game.utiles.Configuracion;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class PantallaMenu implements Screen {

	private OrthographicCamera camara;
	private ScreenViewport vp;
	Imagen fondoMenu;
	SpriteBatch batch;

	Texto opciones[] = new Texto[4];
	String textos[] = {"Multijugador","Opciones","Creditos","Salir"};

	int opc = 1;
	boolean mouseOpc = false;
	
	Entradas entradas = new Entradas(this);
	
	
	ShapeRenderer shapeRenderer;
	
	@Override
	public void show() {

		fondoMenu = new Imagen(Recursos.FONDOMENU);
		fondoMenu.setSize(Configuracion.ancho, Configuracion.alto);
		
		batch= Render.batch;
//creamos la camara		
		
		vp = new ScreenViewport();
		camara = new OrthographicCamera();
		camara.setToOrtho(false, Configuracion.ancho, Configuracion.alto);
		
		shapeRenderer = new ShapeRenderer();
		
		Gdx.input.setInputProcessor(entradas);
		
		int avance = 30;
		
// Recorremos el array de las opciones		
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTEMENU, 55, Color.WHITE, true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosition((Configuracion.ancho / 2)-(opciones[i].getAncho() / 2), ((Configuracion.alto / 2) + (opciones[i].getAlto() / 2)) - ((opciones[i].getAlto() * i) + (avance * i)));
		}

	}

	@Override
	public void render(float delta) {
		
		Render.limpiarPantalla(0, 0 ,0);
		camara.update();
		
		batch.begin();
			fondoMenu.dibujar();
			
			for (int i = 0; i < opciones.length; i++) {
				opciones[i].dibujar();
			}

 // Chequeo que las coordenadas del mouse esten sobre el rectangulo de cada opcion y luego las pinto con el siguiente FOR			
			
			int contOpc = 0;
			
			for (int i = 0; i < opciones.length; i++) {
				if ((entradas.getMouseX() >= opciones[i].getX()) && (entradas.getMouseX() <= (opciones[i].getX() + opciones[i].getAncho()))) {
					if ((entradas.getMouseY() >= opciones[i].getY() - opciones[i].getAlto()) && (entradas.getMouseY() <= (opciones[i].getY()))) {					
						
						opc = i + 1;
						mouseOpc = true;
						contOpc ++;
					}
				}
			}
			
			if(contOpc > 0) {
				mouseOpc = true ;
			}else {
				mouseOpc = false;
			}
//creamos un for que recorre las opciones y pinta de color ROJO la opcion sobre la que esta encima del mouse.			
			
			for (int i = 0; i < opciones.length; i++) {
				if(i==(opc - 1)) {
					opciones[i].setColor(Color.RED);
				} else {
					opciones[i].setColor(Color.WHITE);
				}
			}
			
			if(entradas.isClick())
				if((opc == 1) && (entradas.isClick() && (mouseOpc))) {
					Render.app.setScreen(new PantallaJuego());
					
				} else if ((opc == 4) && (entradas.isClick() && (mouseOpc))) {
					Gdx.app.exit();
					
				}
			
//  utilizamos el ShapeRenderer para dibujar formas en la pantalla y le pasamos como parametro al begin que solo queremos dibujar el contorno del rectangulo		
//		shapeRenderer.begin(ShapeType.Line);
// Le asignamos un color al SR y le pedimos que recorra el vector de las opciones con el for para dibujarle el rectangulo a las mismas
//como los rectangulos se ven desfazados por encima de las opciones le restamos el alto de las opciones a "opciones[i].getY()" y asi quedaran bien ubicados.	
			
			batch.end();
			
//			batch.begin();
//			shapeRenderer.setAutoShapeType(true);
//			shapeRenderer.begin();
//			shapeRenderer.setColor(Color.RED);
//		
//			for (int i = 0; i < opciones.length; i++) {
//				shapeRenderer.rect(opciones[i].getX(), opciones[i].getY() - opciones[i].getAlto(), opciones[i].getAncho(), opciones[i].getAlto());
//				opciones[i].dibujar();
//			}
//		shapeRenderer.end();
//		
//		
//		batch.end();
		

		
	}

	@Override
	public void resize(int width, int height) {
		vp.update(width, height, true);
		Configuracion.alto = height;
		Configuracion.ancho = width;
		camara.viewportWidth = Configuracion.ancho;
		camara.viewportHeight = Configuracion.alto;
		camara.update();
		entradas.mouseMoved(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}

