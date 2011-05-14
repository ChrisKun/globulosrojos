package grupo14.players;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;

public class Acciones {
	
	public enum Accion {
		correrAlAtaque,
		irAlCentroDelCampo,
		irALaMedularArr,
		irALaMedularAb,
		irALaFrontalContrariaArr,
		irALaFrontalContrariaAb,
		irALaFrontalPropiaArr,
		irALaFrontalPropiaAb,
		correrADefensa,
		correrHaciaBalon,
		chutarAPuerta,
		taparPorteria,
		controlarLaPelota		
	}
	
	public enum Lado{
		derecha,
		centro,
		izquierda
	}
	
	public static void realizaAccion(Accion accion, WorldAPI worldAPI) {
		switch (accion) {
		case correrAlAtaque:
			correrAlAtaque(worldAPI);
			break;
		case irAlCentroDelCampo:
			irAlCentroDelCampo(worldAPI);
			break;
		case irALaMedularArr:
			irALaMedular(worldAPI, Lado.izquierda);
			break;
		case irALaMedularAb:
			irALaMedular(worldAPI, Lado.derecha);
			break;
		case irALaFrontalContrariaArr:
			irALaFrontalContraria(worldAPI, Lado.izquierda);
			break;
		case irALaFrontalContrariaAb:
			irALaFrontalContraria(worldAPI, Lado.derecha);
			break;
		case irALaFrontalPropiaArr:
			irALaFrontalPropia(worldAPI, Lado.izquierda);
			break;
		case irALaFrontalPropiaAb:
			irALaFrontalPropia(worldAPI, Lado.derecha);
			break;
		case correrADefensa:
			correrADefensa(worldAPI);
			break;
		case correrHaciaBalon:
			correrHaciaBalon(worldAPI);
			break;
		case chutarAPuerta:
			chutarAPuerta(worldAPI);
			break;
		case taparPorteria:
			taparPorteria(worldAPI);
			break;
		case controlarLaPelota:
			controlarLaPelota(worldAPI);
			break;
		}
	}
	
	public static Accion getAccionPorNombre(String nombreAccion) {
		String minusNombreAccion = nombreAccion.toLowerCase();
		if (minusNombreAccion.contains("correralataque"))
			return Accion.correrAlAtaque;
		else if (minusNombreAccion.contains("iralcentrodelcampo"))
			return Accion.irAlCentroDelCampo;
		else if (minusNombreAccion.contains("iralamedulararr"))
			return Accion.irALaMedularArr;
		else if (minusNombreAccion.contains("iralamedularab"))
			return Accion.irALaMedularAb;
		else if (minusNombreAccion.contains("iralafrontalcontrariaarr"))
			return Accion.irALaFrontalContrariaArr;
		else if (minusNombreAccion.contains("iralafrontalcontrariaab"))
			return Accion.irALaFrontalContrariaAb;
		else if (minusNombreAccion.contains("iralafrontalpropiaarr"))
			return Accion.irALaFrontalPropiaArr;
		else if (minusNombreAccion.contains("iralafrontalpropiaab"))
			return Accion.irALaFrontalPropiaAb;
		else if (minusNombreAccion.contains("correradefensa"))
			return Accion.correrADefensa;
		else if (minusNombreAccion.contains("correrhaciabalon"))
			return Accion.correrHaciaBalon;
		else if (minusNombreAccion.contains("chutarapuerta"))
			return Accion.chutarAPuerta;
		else if (minusNombreAccion.contains("taparporteria"))
			return Accion.taparPorteria;
		else if (minusNombreAccion.contains("controlarlapelota"))
			return Accion.controlarLaPelota;
		
		return Accion.chutarAPuerta;
	}
	
	public static void correrAlAtaque(WorldAPI worldAPI) {
		Vec2 miPosicion = worldAPI.getPosition();
		Vec2 porteriaContraria = worldAPI.getOpponentsGoal();
		
		worldAPI.setSpeed(1.0);
		
		// Avanza hacia adelante manteniendo la posicion Y
		if (miPosicion.x < porteriaContraria.x - 0.05 * 1.37 * worldAPI.getFieldSide()) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setDisplayString("CorrerAlAtaque (subiendo)");
		}
		
		// Si ha llegado a los limites del campo (en X) se queda rondando por la zona
		if (miPosicion.x > porteriaContraria.x - 0.1 * 1.37 * worldAPI.getFieldSide()) {
			giroAleatorio(worldAPI);
			worldAPI.setSpeed(1.0);
			worldAPI.setDisplayString("CorrerAlAtaque (arriba)");
		}
		
		evitarBandas(worldAPI);
			
		evitarBloqueos(worldAPI);
	}
	
	public static void irAlCentroDelCampo(WorldAPI worldAPI) {
		Vec2 miPosicion = worldAPI.getPosition();
		int ladoDelCampo = worldAPI.getFieldSide();
		
		worldAPI.setSpeed(1.0);
		
		// Si ha llegado al centro del campo se queda parado a la espera
		if ( (miPosicion.x > -0.05 * ladoDelCampo || miPosicion.x <= 0) && 
				(miPosicion.y > -0.05 || miPosicion.y <= 0) ) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("A la espera en el centro del campo");
		}

		irAPosicionDeEspera(worldAPI, new Vec2(0,0));
		
		evitarBandas(worldAPI);
			
		evitarBloqueos(worldAPI);
	}
	
	/**
	 * Lleva al jugador a la linea del centro del campo pero especificando un lado
	 * @param worldAPI
	 */
	public static void irALaMedular(WorldAPI worldAPI, Lado lado) {
		Vec2 miPosicion = worldAPI.getPosition();
		int ladoDelCampo = worldAPI.getFieldSide();
		
		Vec2 posicionDeEspera;
		if(lado == Lado.derecha)
			posicionDeEspera = new Vec2(0, 0.5 * ladoDelCampo);
		else
			posicionDeEspera = new Vec2(0,-0.5 * ladoDelCampo);
		
		worldAPI.setSpeed(1.0);
		
		// Si ha llegado al centro del campo se queda parado a la espera
		if (miPosicion.x ==  posicionDeEspera.x && miPosicion.y == posicionDeEspera.y) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("A la espera en el centro del campo");
		}

		irAPosicionDeEspera(worldAPI, posicionDeEspera);
		evitarBandas(worldAPI);	
		evitarBloqueos(worldAPI);
	}
	
	/**
	 * Lleva al jugador a la linea del centro del campo pero especificando un lado
	 * @param worldAPI
	 */
	public static void irALaFrontalContraria(WorldAPI worldAPI, Lado lado) {
		Vec2 miPosicion = worldAPI.getPosition();
		int ladoDelCampo = worldAPI.getFieldSide();
		
		worldAPI.setSpeed(1.0);
		
		Vec2 posicionDeEspera;
		if(lado == Lado.derecha)
			posicionDeEspera = new Vec2(1.0305 * ladoDelCampo, -0.5 * ladoDelCampo);
		else if(lado == Lado.izquierda)
			posicionDeEspera = new Vec2(1.0305 * ladoDelCampo, 0.5 * ladoDelCampo);
		else
			posicionDeEspera = new Vec2(1.0305 * ladoDelCampo, 0);
		
		// Si ha llegado a la frontal del area contraria se para
		if ( miPosicion.x == posicionDeEspera.x && miPosicion.y == posicionDeEspera.y) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("Posicion de espera");
		}
		else
			irAPosicionDeEspera(worldAPI, posicionDeEspera);
		evitarBandas(worldAPI);
		evitarBloqueos(worldAPI);
	}
	
	/**
	 * Lleva al jugador a la linea defensiva por el lado especificado
	 * @param worldAPI
	 */
	public static void irALaFrontalPropia(WorldAPI worldAPI, Lado lado) {
		Vec2 miPosicion = worldAPI.getPosition();
		int ladoDelCampo = worldAPI.getFieldSide();
		
		worldAPI.setSpeed(1.0);
		
		Vec2 posicionDeEspera;
		if(lado == Lado.derecha)
			posicionDeEspera = new Vec2(-1.0305 * ladoDelCampo, -0.3 * ladoDelCampo);
		else if(lado == Lado.izquierda)
			posicionDeEspera = new Vec2(-1.0305 * ladoDelCampo, 0.3 * ladoDelCampo);
		else
			posicionDeEspera = new Vec2(-1.0305 * ladoDelCampo, 0);
		
		// Si ha llegado a la frontal del area contraria se para
		if ( miPosicion.x == posicionDeEspera.x && miPosicion.y == posicionDeEspera.y) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("Posicion de espera");
		}
		else
			irAPosicionDeEspera(worldAPI, posicionDeEspera);
		evitarBandas(worldAPI);
		evitarBloqueos(worldAPI);
	}
	
	/**
	 * Lleva al jugador a la posicion por delante del centro del campo
	 * @param worldAPI
	 */
	public static void irAMedioCentroOfensivo(WorldAPI worldAPI, Lado lado) {
		
		Vec2 miPosicion = worldAPI.getPosition();
		int ladoDelCampo = worldAPI.getFieldSide();	
		Vec2 posicionDeEspera;
		
		if(lado == Lado.derecha)
			posicionDeEspera = new Vec2(0.3425 * ladoDelCampo, -0.3 * ladoDelCampo);
		else
			posicionDeEspera = new Vec2(0.3425 * ladoDelCampo, 0.3 * ladoDelCampo);
		
		worldAPI.setSpeed(1.0);
		
		// Si ha llegado al centro del campo se queda parado a la espera
		if (miPosicion.x == posicionDeEspera.x && miPosicion.y == posicionDeEspera.y ) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("A la espera en el centro del campo");
		}
		
		irAPosicionDeEspera(worldAPI, posicionDeEspera);
		evitarBandas(worldAPI);
		evitarBloqueos(worldAPI);
	}
	
	public static void irAMedioCentroDefensivo(WorldAPI worldAPI, Lado lado) {

		Vec2 miPosicion = worldAPI.getPosition();
		int ladoDelCampo = worldAPI.getFieldSide();	
		Vec2 posicionDeEspera;
		
		if(lado == Lado.derecha)
			posicionDeEspera = new Vec2(-0.3425 * ladoDelCampo, -0.3 * ladoDelCampo);
		else if(lado == Lado.izquierda)
			posicionDeEspera = new Vec2(-0.3425 * ladoDelCampo, 0.3 * ladoDelCampo);
		else
			posicionDeEspera = new Vec2(-0.3425 * ladoDelCampo, 0);
		
		worldAPI.setSpeed(1.0);
		
		// Si ha llegado al centro del campo se queda parado a la espera
		if (miPosicion.x == posicionDeEspera.x && miPosicion.y == posicionDeEspera.y ) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("A la espera en el centro del campo");
		}
		
		irAPosicionDeEspera(worldAPI, posicionDeEspera);
		evitarBandas(worldAPI);
		evitarBloqueos(worldAPI);		
	}
	
	public static void irANuestraPorteria(WorldAPI worldAPI){
		
		int ladoDelCampo = worldAPI.getFieldSide();
		Vec2 miPosicion = worldAPI.getPosition();
		Vec2 posicionDeEspera = new Vec2(-1.37 * ladoDelCampo, 0);
		
		worldAPI.setSpeed(1.0);
		
		// Ir hacia atras manteniendo la posicion Y
		if (miPosicion.x > posicionDeEspera.x ) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("A la espera en nuestra porteria");
		}
		
		irAPosicionDeEspera(worldAPI, posicionDeEspera);
		evitarBandas(worldAPI);
		evitarBloqueos(worldAPI);
	}
	
	public static void correrADefensa(WorldAPI worldAPI) {
		Vec2 miPosicion = worldAPI.getPosition();
		Vec2 miPorteria = worldAPI.getOurGoal();
		
		worldAPI.setSpeed(1.0);
		
		// Ir hacia atras manteniendo la posicion Y
		if (miPosicion.x > miPorteria.x + 0.1 * 1.37 * worldAPI.getFieldSide()) {
			worldAPI.setSteerHeading(Vec2.PI);
			worldAPI.setDisplayString("CorrerADefensa (bajando)");
		}
		
		// Si ha llegado a los l�mites del campo (en X) se queda rondando por la zona
		if (miPosicion.x < miPorteria.x + 0.05 * 1.37 * worldAPI.getFieldSide()) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(1.0);
			worldAPI.setDisplayString("CorrerADefensa (abajo)");
		}
		
		evitarBandas(worldAPI);
			
		evitarBloqueos(worldAPI);
	}
	
	public static void correrHaciaBalon(WorldAPI worldAPI) {
		Vec2 porteriaContraria = worldAPI.getOpponentsGoal();
		worldAPI.setBehindBall(porteriaContraria);
		worldAPI.setDisplayString("CorrerHaciaBalon");
		
		evitarBloqueos(worldAPI);
	}
	
	public static void chutarAPuerta(WorldAPI worldAPI) {
		if (worldAPI.canKick() && worldAPI.alignedToBallandGoal()) {
			worldAPI.kick();
			worldAPI.setDisplayString("ChutarAPorteria (chutando)");
		}		
		else
			correrHaciaBalon(worldAPI);
		
		worldAPI.setDisplayString("ChutarAPorteria (intentando)");
	}
	
	// TODO : Bloquear al mas cercano al jugador? al mas cercano a nuestra porteraa?, al portero?
	public static void bloquearContrario(WorldAPI worldAPI) {
		worldAPI.blockClosest();
		worldAPI.setDisplayString("BloquearContrario (bloqueando)");
	}
	
	public static void desbloquearse(WorldAPI worldAPI) {
		evitarBandas(worldAPI);
		evitarBloqueos(worldAPI);
	}
	
	public static void irAPosicionDeEspera(WorldAPI worldAPI, Vec2 puntoEspera) {
		// Calculamos el vector que va desde el jugador hasta la pelota
		Vec2 vectorDireccion = (Vec2)puntoEspera.clone();
		vectorDireccion.sub(worldAPI.getPosition());
		
		worldAPI.setSteerHeading(vectorDireccion.t);
		worldAPI.setSpeed(1);
		
		if (isCloseToPoint(worldAPI, puntoEspera)) {
			worldAPI.setSpeed(0);
			worldAPI.setDisplayString("IrAPosicionEspera (esperando)");
		}
		else {
			worldAPI.setDisplayString("IrAPosicionEspera (yendo)");
			evitarBandas(worldAPI);
			evitarBloqueos(worldAPI);
		}
	}
	
	// TODO: What? c�mo se hace esto?
	public static void desmarcarse(WorldAPI worldAPI) {
		
	}
	
	// TODO: Con qu� m�todo? a una distancia porcentual de la porteri� (punto medio entre bal�n y porter�a, o 1/3, o...)?
	public static void taparPorteria(WorldAPI worldAPI) {
		Vec2 dif = (Vec2) worldAPI.getOurGoal().clone();
		dif.sub(worldAPI.getBall());
		dif.normalize(dif.r / 2);
		
		irAPosicionDeEspera(worldAPI, dif);
	}
	
	private static void giroAleatorio(WorldAPI worldAPI) {
		
		worldAPI.setSteerHeading(worldAPI.getSteerHeading() + MyRandom.nextDouble(-0.1, 0.1));
	}
	
	public static void evitarBloqueos(WorldAPI worldAPI) {
		if (worldAPI.blocked()) {
			worldAPI.avoidCollisions();
			//TODO Poner alguna direccion aleatoria porque si estamos contra la pared OWNED!
			worldAPI.setSpeed(1.0);
			worldAPI.setDisplayString("Evitando bloqueo");
		}
	}
	
	public static void controlarLaPelota(WorldAPI worldAPI) {
		Vec2 ballPosition = worldAPI.getBall();
		Vec2 porteriaContraria = worldAPI.getOpponentsGoal();
		double radioRobot = worldAPI.getPlayerRadius();

		if(detrasDePunto(ballPosition, porteriaContraria) && ballPosition.t < radioRobot * 4) {
			worldAPI.setSteerHeading(porteriaContraria.t);
			worldAPI.setSpeed(1.0);

			if( (Math.abs(worldAPI.getSteerHeading() - porteriaContraria.t) < Math.PI/8) &&
			    (porteriaContraria.r < radioRobot * 15))
				chutarAPuerta(worldAPI);
		}
		else {
			ponerseDetras(ballPosition, porteriaContraria, worldAPI);
			
			evitarBandas(worldAPI);
			evitarBloqueos(worldAPI);
		}
	}
	
	private static void ponerseDetras(Vec2 point, Vec2 orient, WorldAPI worldAPI) {
		Vec2 movimiento = new Vec2();
		Vec2 behind_point = new Vec2(0,0);
		double behind = 0;
		double point_side = 0;

		// find a vector from the point, away from the orientation
		// you want to be
		behind_point.sett(orient.t);
		behind_point.setr(orient.r);

		behind_point.sub(point);
		behind_point.setr(-worldAPI.getPlayerRadius() * 1.8);

		// determine if you are behind the object with respect
		// to the orientation
		behind = Math.cos(Math.abs(point.t - behind_point.t));

		// determine if you are on the left or right hand side
		// with respect to the orientation
		point_side = Math.sin(Math.abs(point.t - behind_point.t));

		// if you are in FRONT
		if( behind > 0) {
			// make the behind point more of a beside point
			// by rotating it depending on the side of the
			// orientation you are on
			if( point_side > 0)
				behind_point.sett(behind_point.t + Math.PI/2);
			else
				behind_point.sett(behind_point.t - Math.PI/2);
		}

		// move toward the behind point
		movimiento.sett(point.t);
		movimiento.setr(point.r);
		movimiento.add(behind_point);

		movimiento.setr(1.0);
		
		worldAPI.setSteerHeading(movimiento.t);
		worldAPI.setSpeed(movimiento.r);
	}
	
	private static boolean detrasDePunto(Vec2 point, Vec2 orient) {
		// you are behind an object relative to the orientation
		// if your position relative to the point and the orientation
		// are approximately the same
		if(Math.abs(point.t - orient.t) < Math.PI/10) 
			return true;
		else
			return false;
	}
	
	private static void evitarBandas(WorldAPI worldAPI) {
		Vec2 miPosicion = worldAPI.getPosition();
//		Vec2 miPorteria = worldAPI.getOurGoal();
//		Vec2 porteriaEnemiga = worldAPI.getOpponentsGoal();
		
		// Si estamos posicionados en la zona izquierda del campo vamos hacia la derecha
		if (miPosicion.y > 0.7625 * 0.9) {
			worldAPI.setSteerHeading(-Vec2.PI / 2);
			worldAPI.setDisplayString("Evitando la banda");
		}
		// Si estamos posicionados en la zona derecha del campo vamos hacia la izquierda
		else if (miPosicion.y < -0.7625 * 0.9) {
			worldAPI.setSteerHeading(Vec2.PI / 2);
			worldAPI.setDisplayString("Evitando la banda");
		}
		// Si estamos cerca de la porteria rival vamos hacia atras
		else if (miPosicion.x > 1.37 * 0.95) {
			worldAPI.setSteerHeading(Vec2.PI);
			worldAPI.setDisplayString("Evitando la linea de fondo");			
		}
		// Si estamos cerca de nuestra porteria vamos hacia adelante
		else if (miPosicion.x < -1.37 * 0.95) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setDisplayString("Evitando la linea de fondo");			
		}
	}
	
	private static boolean isCloseToPoint(WorldAPI worldAPI, Vec2 punto) {
		float delta = 0.01f;
		Vec2 vectorDiferencia = (Vec2)punto.clone();
		vectorDiferencia.sub(worldAPI.getPosition());
		
		return vectorDiferencia.r <= delta;
	}
}
