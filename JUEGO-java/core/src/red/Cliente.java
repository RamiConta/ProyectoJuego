package red;

import com.mygdx.game.pantallas.PantallaJuego;

public class Cliente {


	private boolean empiezaChat = false;

	public Cliente(PantallaJuego game) {
		UtilesRed.hc = new HiloCliente(game);//tengo que pasarle el juego al hc para tener las propiedades del juego
		UtilesRed.hc.start();			
		UtilesRed.hc.enviarMensaje("conectar#zombie");		
	}

	public void empezarChat() {
		this.empiezaChat = true;
	}

	public void cerrarCliente() {
		System.out.println("cerrando");
		UtilesRed.hc.fin();
		UtilesRed.hc.interrupt();
	}
}