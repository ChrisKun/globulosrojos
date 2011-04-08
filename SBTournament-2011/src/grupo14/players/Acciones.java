package grupo14.players;

import java.util.Random;

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
		
		// Si ha llegado a los límites del campo (en X) se queda rondando por la zona
		if (miPosicion.x > porteriaContraria.x - 0.1 * 1.37 * worldAPI.getFieldSide()) {
			worldAPI.setSteerHeading(Vec2.PI / 2);
			worldAPI.setSpeed(0.5);
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
		
		// Si ha llegado a los límites del campo (en X) disminuye velocidad y se queda por la zona
		if (miPosicion.x < miPorteria.x + 0.05 * 1.37 * worldAPI.getFieldSide()) {
			worldAPI.setSteerHeading(0.0);
			worldAPI.setSpeed(0.5);
			worldAPI.setDisplayString("CorrerADefensa (abajo)");
		}
		
		evitarBandas(worldAPI);
			
		evitarBloqueos(worldAPI);
	}
	
	
	private static void giroAleatorio(WorldAPI worldAPI) {
		
		worldAPI.setSteerHeading(worldAPI.getSteerHeading());
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
		
		// Si estamos posicionados en la zona izquierda del campo vamos hacia la derecha
		if (miPosicion.y > 0.7625 * 0.01)
			worldAPI.setSteerHeading(-Vec2.PI / 2);
		// Si estamos posicionados en la zona derecha del campo vamos hacia la izquierda
		else if (miPosicion.y < -0.7625 * 0.01)
			worldAPI.setSteerHeading(Vec2.PI / 2);		
	}
}
