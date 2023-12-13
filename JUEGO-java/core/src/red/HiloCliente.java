package red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.mygdx.game.pantallas.PantallaJuego;

public class HiloCliente extends Thread {
	
	private DatagramSocket socket;
	private boolean fin = false;
	private InetAddress ipServer;
	private int puerto = 36758;//El puerto del servidor siempre va a estar fijo
	private boolean conexionExitosa = false;
	private boolean servidorLleno = false;
	private boolean esperandoJugadores = true;
	public boolean comenzoJuego = false;
	private PantallaJuego game;
	public int idCliente;

	public HiloCliente(PantallaJuego game) {
		this.game = game;

		try {
			socket = new DatagramSocket();
			ipServer = InetAddress.getByName("255.255.255.255"); //este es el Broadcast
			
		} catch (SocketException | UnknownHostException e) {
			fin = true;
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		do {
			byte[] datos = new byte[1024];
			DatagramPacket dp = new DatagramPacket(datos, datos.length);
			try {
				socket.receive(dp);
				
				procesarMensaje(dp);
			} catch (IOException e) {
				//e.printStackTrace();

			}
		}while(!fin);
		socket.close(); // Cierra el socket al salir del bucle
	}

	private void procesarMensaje(DatagramPacket dp) {
		
		String msg = new String(dp.getData()).trim();//trim() lo que hace es sacar los espacios
		String[] mensajeCompuesto = msg.split("#");
		
		
		switch (mensajeCompuesto[0]) { //Revisa lo que le llega
		
		case "conectado":
			idCliente = Integer.parseInt(mensajeCompuesto[1]);//El servidor envia un mensaje compuesto cuando un jugador se conecta, el primero es "conectado" y el segundo es el id que el server le asigna a este cliente. Como el mensaje que manda el servidor esta en String, lo convierto a int y se lo asigno a la variable idCliente
			System.out.println("Conexion exitosa con el servidor");

			ipServer.equals(dp.getAddress());//Cuando le llega el primer mensaje, agarra la ip del servidor para deje de hacer Broadcast
			game.idJugador = idCliente;
			break;

		case "empezar": 
			
			comenzoJuego = true;
			break;
			
		case "ActualizarPosicion": 
			if(comenzoJuego) {
				
			
			if(Float.parseFloat(mensajeCompuesto[1]) == 0) { // esto es para que solamente se mueva la camara del jugador que envia el mensaje.
				
			game.getChico1().moverCamara();
			game.getChico1().actualizarPosEnRed(Float.parseFloat(mensajeCompuesto[2]),Float.parseFloat(mensajeCompuesto[3]));
			} else if(Float.parseFloat(mensajeCompuesto[1]) == 1){
				
			game.getChico2().moverCamara();
			game.getChico2().actualizarPosEnRed(Float.parseFloat(mensajeCompuesto[2]), Float.parseFloat(mensajeCompuesto[3]));
			}
			}
			
		break;
		
		case "actualizarRotacion":
			if(comenzoJuego) {
			if(mensajeCompuesto[1].equals("0") && game.getChico2() != null) {
				game.getChico2().rotarEnRed(Float.parseFloat(mensajeCompuesto[2]));
			}else if(mensajeCompuesto[1].equals("1") && game.getChico1() != null) {
				game.getChico1().rotarEnRed(Float.parseFloat(mensajeCompuesto[2]));
			}
			}
		break;
		
		case "asegurarseDePosicion":
			if(mensajeCompuesto[1].equals("jugador0")) {
				game.getChico1().posicion.x = Float.parseFloat(mensajeCompuesto[2]);
				game.getChico1().posicion.y = Float.parseFloat(mensajeCompuesto[3]);
				
			}else if(mensajeCompuesto[1].equals("jugador1")) {
				
				game.getChico2().posicion.x = Float.parseFloat(mensajeCompuesto[2]);
				game.getChico2().posicion.y = Float.parseFloat(mensajeCompuesto[3]);
			}
			
			break;

		case "balaDisparada":
			if(comenzoJuego) { // uso una bala para los dos jugadores porque caundo la bala sale de la 
  
					System.out.println("puum++++++++++++++++");
				game.bala1.posicion.x = Float.parseFloat(mensajeCompuesto[1]);
				game.bala1.posicion.y = Float.parseFloat(mensajeCompuesto[2]);
				game.bala1.spr.setPosition(Float.parseFloat(mensajeCompuesto[1]), Float.parseFloat(mensajeCompuesto[2]));
				game.bala1.spr.setRotation(Float.parseFloat(mensajeCompuesto[3]));
			
			}
			
			break;
			
			
		case "jugadorDesconectado":
			game.volverAlMenu = true;
			break;
		}
	}


	public void enviarMensaje(String msg) {
		byte[] mensaje = msg.getBytes();

		try {
			DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipServer, puerto);
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConexionExitosa() {
		return conexionExitosa;
	}

	public boolean isServidorLleno() {
		return servidorLleno;
	}

	public boolean isEsperandojugadores() {
		return esperandoJugadores;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void fin() {
		enviarMensaje("desconectar");
		fin = true;
		socket.close();
	}


	public void setGame(PantallaJuego game) {//Sirve para pasarle un Juego, porque en el constructor del static le estoy pasando uno nulo, entonces llamo a esta funcion desde la clase del juego y fue
		this.game = game;
	}


}
