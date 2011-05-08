package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.MatchState;

/**
 * El contrario tiene el balon en su campo
 * Todos se colocan en su posicion menos el delantero tocapelotas que va hacia el balon todo el rato
 * @author markel
 *
 */
public class PosesionContrarioEnSuCampo implements MatchState{

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
				Acciones.irALaMedular(worldAPI, Acciones.Lado.centro);
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
			Acciones.irAMedioCentroOfensivo(worldAPI, Acciones.Lado.centro);
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
			Acciones.irALaFrontalPropia(worldAPI, Acciones.Lado.centro);
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
			Acciones.correrHaciaBalon(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}

}
