package grupo14.states;

import grupo14.players.Acciones;
import grupo14.players.Acciones.Lado;
import grupo14.players.MatchState;
import grupo14.utils.fieldUtils;
import teams.rolebased.WorldAPI;

/**
 * El delantero contrario esta delante de la porteria.
 * Nuestro portero debe ir a por el balon
 * Nuestro mega defensa va a por el balon tambien
 * El defensa bloquea al contrario
 * Un delantero baja a bloquear a otro delantero contrario
 * El otro delantero se queda en medio campo
 * @author markel
 *
 */
public class UltimoHombreContrario implements MatchState{

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
			Acciones.bloquearContrario(worldAPI);
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
			Acciones.correrHaciaBalon(worldAPI);
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
				if(fieldUtils.getLocationsOctant(worldAPI.getPosition(), worldAPI) == 1 || 
						fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
						fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
						fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
				{
					Acciones.bloquearContrario(worldAPI);
				}
				else
				{
					Acciones.irALaFrontalPropia(worldAPI, Lado.centro);
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
				Acciones.correrHaciaBalon(worldAPI);
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
			Acciones.irALaMedular(worldAPI, Acciones.Lado.centro);
		}
		return 0;
	}
	
	@Override
	public String getStateName(){
		return "UltimoHombreContrario";
	}

}
