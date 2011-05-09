package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.MatchState;

/**
 * Tenemos el balon en su campo
 * El portero se queda en su posicion
 * El mega defensa se queda al final de nuestro campo o un poco mas atras
 * El defensa pasa al ataque
 * El delantero que no tiene el balon bloquea al portero
 * El que tiene el balon que chite si puede o si no que vaya a por el balon 
 * @author markel
 *
 */
public class PosesionNuestraEnSuCampo implements MatchState {

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		if(role.equals("defensor"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			else
			Acciones.correrAlAtaque(worldAPI);
		}
		if(role.equals("portero"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			else
			Acciones.taparPorteria(worldAPI);
		}
		if(role.equals("delantero"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			else
			{
				//vemos con 0.1 si bloquea al portero
				if((Math.abs(worldAPI.getPosition().x - worldAPI.getGoalkeeper().x)<=0.1) && (Math.abs(worldAPI.getPosition().y - worldAPI.getGoalkeeper().y)<=0.1))
				{
					Acciones.bloquearContrario(worldAPI);
				}
				else
				{
					worldAPI.setSteerHeading(worldAPI.getGoalkeeper().t);
					worldAPI.setSpeed(1.0);
				}
			}
		}
		if(role.equals("megaDefensor"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			else
			Acciones.irALaMedular(worldAPI, Acciones.Lado.centro);
		}
		if(role.equals("delanteroTocapelotas"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			else
			Acciones.irALaFrontalContraria(worldAPI, Acciones.Lado.centro);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}

}
