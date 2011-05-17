package grupo14.team;

import java.util.ArrayList;

import grupo14.aprendizaje.redNeuronal.players.MLPResult;
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
	
	private static Ordenes instance;
	
	private boolean usarRN;
	private ArrayList<MLPResult> accionesRN;
	
	
	private Ordenes() {
		stateUtils = new MatchStateUtils();
		accionesRN = new ArrayList<MLPResult>();
	}
	
	/**
	 * Singleton para obtener una instancia de Ordenes, que todo el equipo compartirá
	 * @return
	 */
	public static Ordenes getInstance(){
		if(instance == null)
			instance = new Ordenes();
		return instance;
	}
	
	/**
	 * Metodo que utilizara el entrenador para ordenar al equipo pasar a un estado
	 * @param nuevoEstado: Estado al que deberá pasar el equipo
	 */
	public void establecerEstado(String nuevoEstado)
	{
		this.usarRN = false;
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
	
	/**
	 * Especifica si el entrenador a ordenado pasar a un nuevo estado. Lo que se mira realmente es si el estado 
	 * es null o no. En caso afirmativo es que no hay nueva orden y en caso de que no se null, hay una nueva orden
	 * @return true si hay nuevo estado y false en otro caso
	 */
	public boolean hayNuevoEstado()
	{
		if(this.stateUtils.matchState == null)
			return false;
		else
			return true;
	}
	
	public void setAccionesMLP(ArrayList<MLPResult> acciones) {
		usarRN = true;
		accionesRN = acciones;
	}
	
	public boolean usarRN() {
		return usarRN;
	}
}
