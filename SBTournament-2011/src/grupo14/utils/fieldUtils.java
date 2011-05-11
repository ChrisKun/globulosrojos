package grupo14.utils;

import teams.rolebased.WorldAPI;
import grupo14.aprendizaje.CBR.OctantsState;
import EDU.gatech.cc.is.util.Vec2;

/**
 * Proporciona metodos referentes a la situacion del terreno de juego, posicion de los jugadores
 * conversion de coordenadas globales a egocentricas y viceversa
 * @author markel
 *
 */
public class fieldUtils {

	/**
	 * Fills the octants with the number of players located in each one.
	 * @param teammates: Array with the position of each team mate (egocentric respect goal keeper)
	 * @param opponents: Array with the position of each opponent (egocentric respect goal keeper)
	 * @return OctantsState object with the filled octants
	 */
	public static OctantsState setOctantsState(Vec2[] teammates, Vec2[] opponents, WorldAPI worldAPI) {
		OctantsState state = new OctantsState();
		for(int i = 0; i < 8; i++)
			state.octants.add(new Integer(0));
		for(int i = 0; i < teammates.length; i++)
		{
			//Adds 1 to the octant where the player is located
			int octant = getLocationsOctant(teammates[i],worldAPI)-1;
			int playersInOctant = state.octants.get(octant).intValue();
			state.octants.set(octant, playersInOctant+1);
		}
		for(int i = 0; i < opponents.length; i++)
		{
			//Adds 1 to the octant where the player is located
			int octant = getLocationsOctant(opponents[i],worldAPI)-1;
			int playersInOctant = state.octants.get(octant).intValue()+1;
			state.octants.set(octant, playersInOctant);
		}
		
		//Falta por meter la posicion del portero, porque en teammates no viene su posicion
		Vec2 pos = worldAPI.getPosition();
		int octant = getLocationsOctant(pos, worldAPI)-1;
		int playersInOctant = state.octants.get(octant).intValue();
		state.octants.set(octant, playersInOctant+1);
		
		return state;
	}
	
	/**
	 * Returns the octant number of the received location
	 * @param vec2
	 * @return Integer value representing the octant number of the player (from 1 to 8) or -1 if an error occurs
	 */
	public static int getLocationsOctant(Vec2 playersPosition, WorldAPI worldAPI) {
		
		Vec2 globalPos = toGlobalCoordinates(playersPosition, worldAPI);
		if(globalPos.y >= 0)
		{
			if(globalPos.x < -0.685)
				return 1;
			else if(globalPos.x >= -0.685 && globalPos.x < 0)
				return 2;
			else if(globalPos.x >= 0 && globalPos.x < 0.685)
				return 3;
			else if(globalPos.x >= 0.685)
				return 4;
		}
		else
		{
			if(globalPos.x < -0.685)
				return 5;
			else if(globalPos.x >= -0.685 && globalPos.x < 0)
				return 6;
			else if(globalPos.x >= 0 && globalPos.x < 0.685)
				return 7;
			else if(globalPos.x >= 0.685)
				return 8;
		}
		return -1;
	}
	
	/**
	 * Convierte un vector con coordenadas globales a uno con coordenadas egocentricas
	 * @param sourceCoord: Vector con coordenadas globales que se quiere convertir
	 * @return Objeto Vec2 con las coordenadas egocentricas del vector recibido
	 */
	public static Vec2 toEgocentricCoordinates(Vec2 sourceCoord, WorldAPI worldAPI){
		Vec2 coordinates = (Vec2)sourceCoord.clone(); 
		coordinates.sub(worldAPI.getPosition());
		return coordinates;
	}
	
	/**
	 * Convierte un vector con coordenadas egocentricas a uno con coordenadas globales
	 * @param sourceCoord: Vector con coordenadas egocentricas que se quiere convertir
	 * @return Objeto Vec2 con las coordenadas globales del vector recibido
	 */
	public static Vec2 toGlobalCoordinates(Vec2 sourceCoord, WorldAPI worldAPI)
	{
		Vec2 coordinates = (Vec2)sourceCoord.clone(); 
		coordinates.add(worldAPI.getPosition());
		return coordinates;
	}
	
	/**
	 * Devuelve la coordenada Y del punto en el que se cruzan las rectas A y B, siendo A
	 * la recta que pasa desde la posicion del balon y el centro de la
	 * porteria y B, la recta x = keeperPosition
	 * @param ballPosition: Posicion del balon para poder calcular la recta que une el balon con la porteria
	 * @return Coordenada Y del punto de interseccion
	 */
	public static double calculateIntersection(WorldAPI worldAPI, double defenseLine){
		Vec2 goalPosition = worldAPI.getOurGoal();
		Vec2 ballPosition = worldAPI.getBall();
		double deltaY = (goalPosition.y - ballPosition.y);
		double deltaX = (goalPosition.x - ballPosition.x);
		return deltaY * (ballPosition.y/deltaY - ballPosition.x/deltaX + defenseLine / deltaX);
	}
	
	/**
	 * Devuelve la posicion de un item desde el centro del campo
	 * @param worldAPI
	 * @param position
	 * @return
	 */
    public static Vec2 getRelativePosition(WorldAPI worldAPI,Vec2 position)
    {
            return new Vec2(worldAPI.getPosition().x+position.x,worldAPI.getPosition().y+position.y);
    }
    
    public static boolean whoHasTheBall(WorldAPI worldAPI)
    {
            // si el balon esta en tierra de nadie, tambien lo toma como si fuera posesion contraria
            //0.06 = cankick
            double unidadDeCercania = 0.1;
            boolean nosotros = true;
            Vec2[] oponentes = worldAPI.getOpponents();
            Vec2[] companeros = worldAPI.getTeammates();
            Vec2 oponenteMasCercano = oponentes[0];
            Vec2 companeroMasCercano = companeros[0];
            Vec2 balon = getRelativePosition(worldAPI,worldAPI.getBall());
            int index = oponentes.length;
            for(int i=1; i<index;i++)
            {
                    if((Math.abs(getRelativePosition(worldAPI,oponentes[i]).x - balon.x) <= unidadDeCercania) && (Math.abs(getRelativePosition(worldAPI,oponentes[i]).y - balon.y) <= unidadDeCercania))
                    {
                            oponenteMasCercano = oponentes[i];
                    }
            }
            for(int i = 1; i<index-1; i++)
            {
                    if((Math.abs(getRelativePosition(worldAPI,companeros[i]).x - balon.x) <= unidadDeCercania) && (Math.abs(getRelativePosition(worldAPI,companeros[i]).y - balon.y) <= unidadDeCercania))
                    {
                            companeroMasCercano = companeros[i];
                    }
            }
            if(oponenteMasCercano.x > companeroMasCercano.x && oponenteMasCercano.y > companeroMasCercano.y  )
                    nosotros = true;
            else
                    nosotros = false;
            return nosotros;
    }

}
