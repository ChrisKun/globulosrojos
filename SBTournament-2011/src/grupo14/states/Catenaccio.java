package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.MatchState;
import grupo14.players.Acciones.Lado;
import grupo14.utils.fieldUtils;

/**
 * Situacion en la que se usa:
 * 	   Vamos ganando y falta poco
 * Acciones de los jugadores
 *     El portero esta en la porteria con el comportamiento habitual
 *     El mega defensa coge el centro de la defensa y el defensa uno de los lados
 *     El otro lado de la defensa la coge el delantero
 *     El delantero tocapelotas se queda mas adelantado para ser la primera linea de presion 
 * 
 * @author markel
 *
 */
public class Catenaccio implements MatchState {

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		
		int octanteUno = 1;
		int octanteDos = 2;
		int octanteTres = 3;
		int octanteCuatro = 4;
		int octanteCinco = 5;
		int octanteSeis = 6;
		int octanteSiete = 7;
		int octanteOcho = 8;
		
		if(worldAPI.getFieldSide() == -1)
		{
			octanteUno = 4;
			octanteDos = 3;
			octanteTres = 2;
			octanteCuatro = 1;
			octanteCinco = 8;
			octanteSeis = 7;
			octanteSiete = 6;
			octanteOcho = 5;
		}
		
		System.out.println(role.getClass().toString());
		if(role.equals("portero"))
		{
			//Ver si tengo el balon
			if(worldAPI.canKick())
			{
				//Posicionarme correctamente y chutar hacia delante
				Acciones.chutarAPuerta(worldAPI);
			}
//			//Si el balon entra en mi zona (todo nuestro campo)...
//			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 1 || 
//					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
//					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
//					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
//			{
//				Acciones.correrHaciaBalon(worldAPI);
//			}
//			else
			{
				//Ir al centro del campo
				Acciones.irANuestraPorteria(worldAPI);
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
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteUno ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteCinco)
			{
				Acciones.correrHaciaBalon(worldAPI);
			}
			else
			{
				//Ir a posicion de medio centro ofensivo por la izquierda
				Acciones.irALaFrontalPropia(worldAPI, Lado.centro);
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
//			//TODO Implementar que si esta cerca del balon aunque no sea su zona vaya a por el
//			//Y ponerselo a todos los jugadores
			//Si el balon entra en mi zona...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteUno)
			{
				Acciones.correrHaciaBalon(worldAPI);
			}
			//Si el balon entra en la zona del defensa del otro lado ...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteCinco)
			{//Ir al centro de la defensa
				Acciones.irALaFrontalPropia(worldAPI, Lado.centro);
			}
			else
			{
				//Ir a posicion de medio centro ofensivo por la derecha
				Acciones.irALaFrontalPropia(worldAPI, Lado.izquierda);
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
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteCinco)
			{
				//Ir a por el balon
				Acciones.correrHaciaBalon(worldAPI);
			}
			//Si el balon entra en la zona del defensa del otro lado ...
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteUno)
			{//Ir al centro de la defensa
				Acciones.irALaFrontalPropia(worldAPI, Lado.centro);
			}
			else {//En caso contrario...
				//Ir a nuestra frontal, ponerse en la aprte derecha
				Acciones.irALaFrontalPropia(worldAPI, Lado.derecha);
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
			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteUno ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteDos ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteCinco ||
					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == octanteSeis)
			{//Voy hacia el balon
				Acciones.correrHaciaBalon(worldAPI);
			}
//			//Si el balon entra en la zona del portero (todo nuestro campo)...
//			else if(fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 1 || 
//					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 2 ||
//					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 5 || 
//					fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI) == 6)
//			{//Coger la posicion de delantero centro
//				Acciones.irALaFrontalContraria(worldAPI, Lado.centro);
//			}
			else {
				//Ir a la linea de mecio centro defensivo
				Acciones.irAMedioCentroDefensivo(worldAPI, Lado.centro);
			}
			Acciones.evitarBloqueos(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		return "Catenaccio";
	}

}
