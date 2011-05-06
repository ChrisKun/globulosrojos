package grupo14.states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.Acciones.Lado;
import grupo14.players.MatchState;
import grupo14.utils.fieldUtils;

/**
 * Situacion en la que se usa:
 * 	   Vamos perdiendo llevamos mas de la mitad del tiempo y tenemos la posesion
 * Acciones de los jugadores:
 *     El que tenga el balon chuta hacia delante
 *     Todos van hacia delante, quedando el portero en el centro del campo
 *     Los dos defensas un poco mas adelantados
 *     Y los delanteros arriba del todo
 * 
 *     Si el balon entra en nuestro propio campo el portero ira a por el para devolverlo al campo contrario
 *     En ese caso el Mega defensa coge la posicion del centro del campo (la que tenia el portero) y su posicion 
 *     la coge el delantero.
 *     El delantero toca pelotas coge en ese caso la posicion de unico delantero (arriba en el centro)
 * 
 *     Cada jugador ira a por el balon cuando este entre en su zona:
 * 	       Portero: nuestro campo
 *         El resto de jugadores: el octante donde se encuentre su posicion de espera
 * @author markel
 *
 */
public class Heroica implements MatchState {

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		System.out.println(role.getClass().toString());
		if(role.equals("portero"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			//Si el balon entra en mi zona (todo nuestro campo)...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 1 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
			{
				Acciones.correrHaciaBalon(worldAPI);
			}
			else
			{
				//Ir al centro del campo
				Acciones.irAlCentroDelCampo(worldAPI);
			}
			Acciones.evitarBloqueos(worldAPI);
		}
		else if(role.equals("megaDefensor"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			//Si el balon entra en mi zona...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 3)
			{
				Acciones.correrHaciaBalon(worldAPI);
			}
			//Si el balon entra en la zona del portero (todo nuestro campo)...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 1 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
			{//Coger la posicion del portero
				Acciones.irAlCentroDelCampo(worldAPI);
			}
			else
			{
				//Ir a posicion de medio centro ofensivo por la izquierda
				Acciones.irAMedioCentroOfensivo(worldAPI, Lado.izquierda);
			}
			Acciones.evitarBloqueos(worldAPI);
		}
		else if(role.equals("defensor"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			//TODO Implementar que si esta cerca del balon aunque no sea su zona vaya a por el
			//Y ponerselo a todos los jugadores
			//Si el balon entra en mi zona...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 7)
			{
				Acciones.correrHaciaBalon(worldAPI);
			}
			//Si el balon entra en la zona de los delanteros...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 4 ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 8)
			{//Subir al ataque
				Acciones.irALaFrontalContraria(worldAPI, Lado.centro);
			}
			else
			{
				//Ir a posicion de medio centro ofensivo por la derecha
				Acciones.irAMedioCentroOfensivo(worldAPI, Lado.derecha);
			}
			Acciones.evitarBloqueos(worldAPI);
		}
		else if(role.equals("delantero"))
		{
			//Si tengo el balon...
			if(worldAPI.canKick())
			{
				//Chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			//Si el balon entra en mi zona...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 8)
			{
				//Ir a por el balon
				Acciones.correrHaciaBalon(worldAPI);
			}
			//Si el balon entra en la zona del portero (todo nuestro campo)...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 1 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
			{//Coger la posicion del mega defensa
				Acciones.irAMedioCentroOfensivo(worldAPI,Lado.izquierda);
			}
			else {//En caso contrario...
				//Ir al ataque por la derecha
				Acciones.irALaFrontalContraria(worldAPI, Lado.izquierda);
			}
			Acciones.evitarBloqueos(worldAPI);
		}
		else if(role.equals("delanteroTocapelotas"))
		{
			//Si tengo el balon...
			if(worldAPI.canKick())
			{
				//Chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
			//Si el balon entra en mi zona...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 4)
			{
				Acciones.correrHaciaBalon(worldAPI);
			}
			//Si el balon entra en la zona del portero (todo nuestro campo)...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 1 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
			{//Coger la posicion de delantero centro
				Acciones.irALaFrontalContraria(worldAPI, Lado.centro);
			}
			else {
				//Ir al ataque por la derecha
				Acciones.irALaFrontalContraria(worldAPI, Lado.derecha);
			}
			Acciones.evitarBloqueos(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		return "Heroica";
	}

}
