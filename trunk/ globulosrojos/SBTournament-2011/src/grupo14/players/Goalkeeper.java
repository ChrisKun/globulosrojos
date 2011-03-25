package grupo14.players;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends Role {

	private final double KEEPER_DEFENSE_LINE = -110;
	private Vec2 goalPosition;
	
	@Override
	public int configure() {
		//Recoger posicion de nuestra porteria
		this.goalPosition = worldAPI.getOurGoal();
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		double keeperPosY = calculateIntersection(KEEPER_DEFENSE_LINE, worldAPI.getBall());
		Vec2 destPos = toEgocentricCoordinates(new Vec2(KEEPER_DEFENSE_LINE, keeperPosY));
		worldAPI.setSteerHeading(destPos.r);
		worldAPI.setSpeed(1);
		System.out.println(keeperPosY);
		return WorldAPI.ROBOT_OK;
	}

	private Vec2 calculatePosition(){
		//Recoger posicion del balon
		Vec2 ballPosition = worldAPI.getBall();
		
		return null;
	}
	
	/**
	 * Convierte un vector con coordenadas globales a uno con coordenadas egocentricas
	 * @param sourceCoord: Vector con coordenadas globales que se quiere convertir
	 * @return Objeto Vec2 con las coordenadas egocentricas del vector recibido
	 */
	private Vec2 toEgocentricCoordinates(Vec2 sourceCoord){
		Vec2 coordinates = (Vec2)sourceCoord.clone(); 
		coordinates.sub(worldAPI.getPosition());
		return coordinates;
	}
	
	/**
	 * Convierte un vector con coordenadas egocentricas a uno con coordenadas globales
	 * @param sourceCoord: Vector con coordenadas egocentricas que se quiere convertir
	 * @return Objeto Vec2 con las coordenadas globales del vector recibido
	 */
	private Vec2 toGlobalCoordinates(Vec2 sourceCoord){
//		Vec2 coordinates = (Vec2)destinationPosition.clone(); 
//		coordinates.sub(worldAPI.);
//		return coordinates;
		return null;
	}
	
	/**
	 * Devuelve la coordenada Y del punto en el que se cruzan las rectas A y B, siendo A
	 * la recta que pasa desde la posicion del balon y el centro de la
	 * porteria y B, la recta x = keeperPosition
	 * @return Coordenada Y del punto de interseccion
	 */
	private double calculateIntersection(double keeperPosition, Vec2 ballPosition){
		
		double deltaY = (goalPosition.y - ballPosition.y);
		double deltaX = (goalPosition.x - ballPosition.x);
		return deltaY * (ballPosition.y/deltaY - ballPosition.x/deltaX + keeperPosition / deltaX);
	}
}
