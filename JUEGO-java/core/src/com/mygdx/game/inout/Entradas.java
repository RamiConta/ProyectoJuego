package com.mygdx.game.inout;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.pantallas.PantallaJuego;
import com.mygdx.game.pantallas.PantallaMenu;
import com.mygdx.game.utiles.Configuracion;

public class Entradas implements InputProcessor {

	private int mouseX, mouseY ;
	private boolean click = false;
	
	PantallaMenu app;
	
	
	 public Entradas() {
	}
	 
	public Entradas(PantallaMenu app) {
		this.app = app;
	}

	   
	 
	@Override
	public boolean keyDown(int keycode) {

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		click = true; 
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		click = false;
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		mouseX = screenX;
		mouseY = Configuracion.alto - screenY;
		
		return false;
	}

	public boolean scrolled(int amount) {
		
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {

		return false;
	}

	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public boolean isClick() {
		
		return click;
	}
}
