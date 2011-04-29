package states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.Defender;
import grupo14.players.Goalkeeper;
import grupo14.players.MatchState;
import grupo14.players.Striker;

/**
 * El delantero contrario esta delante de la porteria.
 * Nuestro portero debe ir a por el balon
 * Nuestro mega defensa va a por el balontambien
 * El defensa bloquea al contrario
 * Un delantero baja a bloquear a otro delantero contrario
 * El otro delantero se queda en medio campo
 * @author markel
 *
 */
public class UltimoHombreContrario implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		System.out.println(role.getClass().toString());
		if(role.equals("defensor"))
		{
//			{
//				if(worldAPI.isBlocking(worldAPI.getPosition()))
//				{
//					worldAPI.avoidCollisions();
//				}
//				else{
//				Vec2 vector = worldAPI.getBall();
//				worldAPI.setSteerHeading(vector.t);
//				worldAPI.setSpeed(0.2);
//				}
//			}
			Acciones.taparPorteria(worldAPI);
		}
		if(role.equals("portero"))
		{
			Vec2 vector = worldAPI.getBall();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
		}
		if(role.equals("delantero"))
		{
//			Vec2 vector = new Vec2(0,0);
//			worldAPI.setSteerHeading(vector.t);
//			worldAPI.setSpeed(1.0);
			Acciones.correrADefensa(worldAPI);
		}
		return 0;
	}
	
	@Override
	public String getStateName(){
		return "UltimoHombreContrario";
	}

}
