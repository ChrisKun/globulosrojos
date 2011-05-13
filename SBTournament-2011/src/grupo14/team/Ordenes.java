package grupo14.team;

import grupo14.players.MatchState;
import grupo14.utils.MatchStateUtils;

/**
 * Clase utilizada por el entrenador para ordenar cosas a los jugadores. Las opciones pueden ser:
 * 	- Pasar a un estado determinado
 *  - Una accion a realizar a cada jugador
 * Los jugadores accederan a esta clase en cada TakeStep para ver si hay ordenes nuevas
 * @author markel
 *
 */
public class Ordenes {

	/**
	 * Estado al que deberá pasar el equipo
	 */
	private MatchStateUtils stateUtils;
	
	/**
	 * Metodo que utilizara el entrenador para ordenar al equipo pasar a un estado
	 * @param nuevoEstado: Estado al que deberá pasar el equipo
	 */
	public void establecerEstado(String nuevoEstado)
	{
		this.stateUtils.setMatchStateUsingName(nuevoEstado);
	}
	
	/**
	 * Metodo que utilizaran los jugadores para recibir las ordenes referentes a los estados
	 * @return
	 */
	public MatchState pasarAEstado()
	{
		return this.stateUtils.matchState;
	}
}
