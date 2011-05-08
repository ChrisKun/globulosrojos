package grupo14.aprendizaje.redNeuronal.players;

import grupo14.aprendizaje.redNeuronal.Perceptron;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import EDU.gatech.cc.is.util.Vec2;

public abstract class PlayerMLP {

	/** Lista de perceptrones de un solo jugador, uno por cada 
	 * estado posible de un jugador. */
	private HashMap<String, Perceptron> mlps;
	
	/** Lee todos los perceptrones de un directorio dado y los almacena
	 * en la lista de perceptrones del jugador.
	 * @param directory Directorio donde están los perceptrones. */
	public void readFromFile(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for (int iFile = 0; iFile < listOfFiles.length; iFile++)
			mlps.put(listOfFiles[iFile].getName(), Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
	}
	
	/** Aprende un nuevo caso en base a la evolución del juego.
	 * @param oldState Estado del juego en el pasado.
	 * @param newState Estado del juego en el presente. */
	public abstract void train(LogEntry oldState, LogEntry newState);
	
	/** Dado una evolución del juego y un jugador concreto extrae la acción que
	 * está realizando el jugador. 
	 * @param oldState Estado del juego en el pasado.
	 * @param newState Estado del juego en el presente.
	 * @param playerId Id del jugador que se quiere evaluar.
	 * @param fieldSide Lado del campo del jugador.
	 * @return Movimiento realizado por el jugador analizado. */
	protected String recognizeMovement(LogEntry oldState, LogEntry newState, int playerId, int fieldSide) {
		// Se extraen ciertos datos importantes para las comprobaciones posteriores
		PlayerInfo oldPlayerInfo = getPlayerInfo(oldState, playerId);
		PlayerInfo newPlayerInfo = getPlayerInfo(newState, playerId);
		Vec2 oldBallPosition = new Vec2(oldState.getBallInfo().getPositionX(), oldState.getBallInfo().getPositionY());
		Vec2 newBallPosition = new Vec2(newState.getBallInfo().getPositionX(), newState.getBallInfo().getPositionY());
		Vec2 oldPlayerPosition = new Vec2(oldPlayerInfo.getPositionX(), oldPlayerInfo.getPositionY());
		Vec2 newPlayerPosition = new Vec2(newPlayerInfo.getPositionX(), newPlayerInfo.getPositionY());
		double oldPlayerBallDistance = oldBallPosition.distance(oldPlayerPosition);
		double newPlayerBallDistance = newBallPosition.distance(newPlayerPosition);
		
		
		/* Chutar a portería:
		 *   old: el jugador está cerca del balón
		 *   new: el balón ha avanzado delta en el eje X */
		if (oldPlayerBallDistance < 0.3 && 
			newBallPosition.x - oldBallPosition.x > 0.3 * -fieldSide)
			return "ChutarAPorteria";
		
		/* Correr hacia el balón: 
		 *   old: 
		 *   new: el jugador está más cerca del balón
		 *        el balón no se ha movido mucho */
		if (newPlayerBallDistance < oldPlayerBallDistance - 0.1 &&
			oldBallPosition.distance(newBallPosition) < 0.3)
			return "CorrerHaciaElBalon";
		
		/* Tapar la portería:
		 *   old: 
		 *   new: el jugador está entre la portería y el balón (con un radio de error) */
		Vec2 newMidBallGoalPoint = new Vec2((newBallPosition.x + fieldSide * 1.37) / 2.0,
											newBallPosition.y / 2.0);
		if (newMidBallGoalPoint.distance(newPlayerPosition) < 0.3)
			return "TaparPorteria";
		
		/* Correr al ataque:
		 *   old: el jugador está en su propio campo 
		 *   new: el jugador está en el campo contrario
		 *        el jugador se ha movido más de una distancia de seguridad */
		if (oldPlayerPosition.x * fieldSide > 0 &&
			newPlayerPosition.x * fieldSide < 0 && 
			newPlayerPosition.distance(oldPlayerPosition) > 0.2)
			return "CorrerAlAtaque";
		
		/* Correr a defensa:
		 *   old: el jugador está en el campo contrario
		 *   new: el jugador está en su propio campo
		 *        el jugador se ha movido más de una distancia de seguridad */
		if (oldPlayerPosition.x * fieldSide < 0 &&
			newPlayerPosition.x * fieldSide > 0 && 
			newPlayerPosition.distance(oldPlayerPosition) > 0.2)
			return "CorrerADefensa";
		
		/* Ir al centro del campo:
		 *   old: 
		 *   new: el jugador está a una distancia cercana del centro del campo */
		if (newPlayerPosition.distance(new Vec2(0, 0)) < 0.15)
			return "IrAlCentroDelCampo";
		
		/* Ir a la medular (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la medular por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(0, 0.3)) < 0.15)
			return "IrALaMedularArr";
		
		/* Ir a la medular (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la medular por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(0, -0.3)) < 0.15)
			return "IrALaMedularAb";
		
		/* Ir a la frontal contraria (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal contraria por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(1.0 * -fieldSide, 0.3)) < 0.2)
			return "IrALaFrontalContrariaArr";
		
		/* Ir a la frontal contraria (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal contraria por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(1.0 * -fieldSide, -0.3)) < 0.2)
			return "IrALaFrontalContrariaAb";
		
		/* Ir a la frontal propia (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal propia por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(1.0 * fieldSide, 0.3)) < 0.2)
			return "IrALaFrontalPropiaArr";
		
		/* Ir a la frontal propia (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal propia por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(1.0 * fieldSide, -0.3)) < 0.2)
			return "IrALaFrontalPropiaAb";
		
		return null;
	}
	
	/** Devuelve la información del log correspondiente con el jugador del id dado.
	 * @param state Estado del que se quiere sacar la información. 
	 * @param playerId Id del jugador del que se quiere obtener la información. 
	 * @return Información del jugador en el estado dado. */
	protected static PlayerInfo getPlayerInfo(LogEntry state, int playerId) {
		PlayerInfo player = null;
		
		ArrayList<PlayerInfo> eastTeam = state.getEastTeamInfo();
		ArrayList<PlayerInfo> westTeam = state.getWestTeamInfo();
		
		boolean found = false;
		
		for (int iPlayer = 0; iPlayer < eastTeam.size() && !found; iPlayer++)
			if (eastTeam.get(iPlayer).getRobotId() == playerId) {
				player = eastTeam.get(iPlayer);
				found = true;
			}
		for (int iPlayer = 0; iPlayer < westTeam.size() && !found; iPlayer++)
			if (westTeam.get(iPlayer).getRobotId() == playerId) {
				player = westTeam.get(iPlayer);
				found = true;
			}
		
		return player;
	}

}
