package grupo14.players;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;

public class Acciones {
	
	public static void correrAlAtaque(WorldAPI worldAPI) {
		Vec2 miPosicion = worldAPI.getPosition();
		Vec2 porteriaContraria = worldAPI.getOpponentsGoal();
		
		worldAPI.setSpeed(1.0);
		
		// Avanza hacia adelante manteniendo la posicion Y
		if (miPosicion.x < porteriaContraria.x - 0.05 * 1.37 * worldAPI.getFieldSide()) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setDisplayString("CorrerAlAtaque (subiendo)");
		}
		
		// Si ha llegado a los l�mites del campo (en X) se queda rondando por la zona
		if (miPosicion.x > porteriaContraria.x - 0.1 * 1.37 * worldAPI.getFieldSide()) {
			giroAleatorio(worldAPI);
			worldAPI.setSpeed(1.0);
			worldAPI.setDisplayString("CorrerAlAtaque (arriba)");
		}
		
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
	
	private static void giroAleatorio(WorldAPI worldAPI) {
		
		worldAPI.setSteerHeading(worldAPI.getSteerHeading() + MyRandom.nextDouble(-0.1, 0.1));
	}
	
	private static void evitarBloqueos(WorldAPI worldAPI) {
		if (worldAPI.blocked()) {
			worldAPI.avoidCollisions();
			worldAPI.setSpeed(1.0);
			worldAPI.setDisplayString("Evitando bloqueo");
		}
	}
	
	private static void evitarBandas(WorldAPI worldAPI) {
		Vec2 miPosicion = worldAPI.getPosition();
		Vec2 miPorteria = worldAPI.getOurGoal();
		Vec2 porteriaEnemiga = worldAPI.getOpponentsGoal();
		
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
}
