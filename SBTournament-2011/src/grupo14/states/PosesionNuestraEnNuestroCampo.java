package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.MatchState;

/**
 * Tenemos el balon en nuestro campo
 * El portero esta en su posicion
 * El mega defensa si no tiene el balon se coloca un poco por detras, en caso contrario chuta hacia delante
 * El defensa si tiene el balon chuta hacia delante y si no que se ponga a la par del mega defensa
 * El delantero si tiene el balon chuta hacia delante y si no que se coloque en el centro del campo
 * El delantero tocapelotas si tiene el balon chuta hacia delante y sino que suba al ataque
 * @author batxes
 *
 */
public class PosesionNuestraEnNuestroCampo implements MatchState {

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
				//el defensa se pondra en la derecha, y el megadefensa en la izquierda
			Acciones.irALaFrontalPropia(worldAPI, Acciones.Lado.derecha);
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
			Acciones.irALaMedular(worldAPI, Acciones.Lado.centro);
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
				//el defensa se pondra en la derecha, y el megadefensa en la izquierda
				Acciones.irALaFrontalPropia(worldAPI, Acciones.Lado.izquierda);
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
				Acciones.correrAlAtaque(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}

}
