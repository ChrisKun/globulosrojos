package grupo14.aprendizaje.redNeuronal.players;

import grupo14.aprendizaje.redNeuronal.Perceptron;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
		for (int iFile = 0; iFile < listOfFiles.length; iFile++) {
			if (listOfFiles[iFile].getName().contains(Actions.CHUTAR_A_PUERTA))
				mlps.put(Actions.CHUTAR_A_PUERTA, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.CORRER_A_DEFENSA))
				mlps.put(Actions.CORRER_A_DEFENSA, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.CORRER_AL_ATAQUE))
				mlps.put(Actions.CORRER_AL_ATAQUE, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.CORRER_HACIA_EL_BALON))
				mlps.put(Actions.CORRER_HACIA_EL_BALON, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_A_LA_FRONTAL_CONTRARIA_AB))
				mlps.put(Actions.IR_A_LA_FRONTAL_CONTRARIA_AB, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_A_LA_FRONTAL_CONTRARIA_ARR))
				mlps.put(Actions.IR_A_LA_FRONTAL_CONTRARIA_ARR, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_A_LA_FRONTAL_PROPIA_AB))
				mlps.put(Actions.IR_A_LA_FRONTAL_PROPIA_AB, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_A_LA_FRONTAL_PROPIA_ARR))
				mlps.put(Actions.IR_A_LA_FRONTAL_PROPIA_ARR, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_A_LA_MEDULAR_AB))
				mlps.put(Actions.IR_A_LA_MEDULAR_AB, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_A_LA_MEDULAR_ARR))
				mlps.put(Actions.IR_A_LA_MEDULAR_ARR, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.IR_AL_CENTRO_DEL_CAMPO))
				mlps.put(Actions.IR_AL_CENTRO_DEL_CAMPO, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
			else if (listOfFiles[iFile].getName().contains(Actions.TAPAR_PORTERIA))
				mlps.put(Actions.TAPAR_PORTERIA, Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
		}
	}
	
	/** Aprende un nuevo caso en base a la evolución del juego.
	 * @param oldState Estado del juego en el pasado.
	 * @param newState Estado del juego en el presente. */
	public void train(LogEntry oldState, LogEntry newState) {
		for (int fieldSide = -1; fieldSide < 2; fieldSide += 2)
			if (isGoodMove(oldState, newState, fieldSide)) {
				int playerId = getReferencePlayer(oldState, fieldSide);
				String action = recognizeAction(oldState, newState, playerId, fieldSide);
				learnMove(oldState, action, fieldSide);
			}
	}
	
	/** Determina si la jugada es buena para ser aprendida 
	 * @param oldState Estado del juego en el pasado.
	 * @param newState Estado del juego en el presente. 
	 * @param fieldSide Lado del campo del jugador.
	 * @return true si ha habido una mejora en el cambio de estado, false en otro caso. */
	protected abstract boolean isGoodMove(LogEntry oldState, LogEntry newState, int fieldSide);
	
	/** Determina el jugador que se quiere tomar como referencia para aprender la jugada. 
	 * @param oldState Estado del juego.
	 * @return id del jugador que se toma como referencia. */
	protected abstract int getReferencePlayer(LogEntry state, int fieldSide);
	
	/** Dado una evolución del juego y un jugador concreto extrae la acción que
	 * está realizando el jugador. 
	 * @param oldState Estado del juego en el pasado.
	 * @param newState Estado del juego en el presente.
	 * @param playerId Id del jugador que se quiere evaluar.
	 * @param fieldSide Lado del campo del jugador.
	 * @return Movimiento realizado por el jugador analizado. */
	protected String recognizeAction(LogEntry oldState, LogEntry newState, int playerId, int fieldSide) {
		// Se extraen ciertos datos importantes para las comprobaciones posteriores
		PlayerInfo oldPlayerInfo = getPlayerInfo(oldState, playerId);
		PlayerInfo newPlayerInfo = getPlayerInfo(newState, playerId);
		Vec2 oldBallPosition = new Vec2(oldState.getBallInfo().getPositionX(), oldState.getBallInfo().getPositionY());
		Vec2 newBallPosition = new Vec2(newState.getBallInfo().getPositionX(), newState.getBallInfo().getPositionY());
		Vec2 oldPlayerPosition = new Vec2(oldPlayerInfo.getPositionX(), oldPlayerInfo.getPositionY());
		Vec2 newPlayerPosition = new Vec2(newPlayerInfo.getPositionX(), newPlayerInfo.getPositionY());
		double oldPlayerBallDistance = oldBallPosition.distance(oldPlayerPosition);
		double newPlayerBallDistance = newBallPosition.distance(newPlayerPosition);
		
		
		/* Chutar a puerta:
		 *   old: el jugador está cerca del balón
		 *   new: el balón ha avanzado delta en el eje X */
		if (oldPlayerBallDistance < 0.3 && 
			newBallPosition.x - oldBallPosition.x > 0.3 * -fieldSide)
			return Actions.CHUTAR_A_PUERTA;
		
		/* Correr hacia el balón: 
		 *   old: 
		 *   new: el jugador está más cerca del balón
		 *        el balón no se ha movido mucho */
		if (newPlayerBallDistance < oldPlayerBallDistance - 0.1 &&
			oldBallPosition.distance(newBallPosition) < 0.3)
			return Actions.CORRER_HACIA_EL_BALON;
		
		/* Tapar la portería:
		 *   old: 
		 *   new: el jugador está entre la portería y el balón (con un radio de error) */
		Vec2 newMidBallGoalPoint = new Vec2((newBallPosition.x + fieldSide * 1.37) / 2.0,
											newBallPosition.y / 2.0);
		if (newMidBallGoalPoint.distance(newPlayerPosition) < 0.3)
			return Actions.TAPAR_PORTERIA;
		
		/* Correr al ataque:
		 *   old: el jugador está en su propio campo 
		 *   new: el jugador está en el campo contrario
		 *        el jugador se ha movido más de una distancia de seguridad */
		if (oldPlayerPosition.x * fieldSide > 0 &&
			newPlayerPosition.x * fieldSide < 0 && 
			newPlayerPosition.distance(oldPlayerPosition) > 0.2)
			return Actions.CORRER_AL_ATAQUE;
		
		/* Correr a defensa:
		 *   old: el jugador está en el campo contrario
		 *   new: el jugador está en su propio campo
		 *        el jugador se ha movido más de una distancia de seguridad */
		if (oldPlayerPosition.x * fieldSide < 0 &&
			newPlayerPosition.x * fieldSide > 0 && 
			newPlayerPosition.distance(oldPlayerPosition) > 0.2)
			return Actions.CORRER_A_DEFENSA;
		
		/* Ir al centro del campo:
		 *   old: 
		 *   new: el jugador está a una distancia cercana del centro del campo */
		if (newPlayerPosition.distance(new Vec2(0, 0)) < 0.15)
			return Actions.IR_AL_CENTRO_DEL_CAMPO;
		
		/* Ir a la medular (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la medular por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(0, 0.3)) < 0.15)
			return Actions.IR_A_LA_MEDULAR_ARR;
		
		/* Ir a la medular (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la medular por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(0, -0.3)) < 0.15)
			return Actions.IR_A_LA_MEDULAR_AB;
		
		/* Ir a la frontal contraria (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal contraria por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(1.0 * -fieldSide, 0.3)) < 0.2)
			return Actions.IR_A_LA_FRONTAL_CONTRARIA_ARR;
		
		/* Ir a la frontal contraria (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal contraria por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(1.0 * -fieldSide, -0.3)) < 0.2)
			return Actions.IR_A_LA_FRONTAL_CONTRARIA_AB;
		
		/* Ir a la frontal propia (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal propia por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(1.0 * fieldSide, 0.3)) < 0.2)
			return Actions.IR_A_LA_FRONTAL_PROPIA_ARR;
		
		/* Ir a la frontal propia (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal propia por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(1.0 * fieldSide, -0.3)) < 0.2)
			return Actions.IR_A_LA_FRONTAL_PROPIA_AB;
		
		return null;
	}
	
	/** Aprende un caso poniendo como valor positivo solo el perceptrón seleccionado.
	 * @param state Estado del juego.
	 * @param action Acción que se va a aprender.
	 * @param fieldSide Campo del jugador. */
	protected void learnMove(LogEntry state, String action, int fieldSide) {
		double[] inputValues = getMLPInputValues(state, fieldSide);
		
		Iterator<Entry<String, Perceptron>> itMLPS = mlps.entrySet().iterator();
		while (itMLPS.hasNext()) {
			Entry<String, Perceptron> entry = itMLPS.next();
			if(entry.getKey().equals(action))
				entry.getValue().train(inputValues, 1);
			else
				entry.getValue().train(inputValues, 0);
		}
	}
	
	/** Devuelve las entradas para el perceptrón en el formato adecuado. 
	 * @param state Estado del que sacar la información.
	 * @param fieldSide Lado del campo del jugador.
	 * @return Entradas para el perceptrón. */
	protected double[] getMLPInputValues(LogEntry state, int fieldSide) {
		// tiempo (1) + marcador (2) + (numJugadores (10) + pelota (1)) * 2 (x, y) = 19 entradas
		double[] inputValues = new double[24];
		// Tiempo TODO : Convertir a double dividiendo por el máx de tiempo
		inputValues[0] = state.getTime();
		// Marcador
		inputValues[1] = state.getBallInfo().getWestScore();
		inputValues[2] = state.getBallInfo().getEastScore();
		// Pelota
		inputValues[3] = state.getBallInfo().getPositionX();
		inputValues[4] = state.getBallInfo().getPositionY();
		// Jugadores
		ArrayList<PlayerInfo> myTeam;
		ArrayList<PlayerInfo> enemyTeam;
		if (fieldSide == -1) {
			myTeam = state.getWestTeamInfo();
			enemyTeam = state.getEastTeamInfo();
		}
		else {
			myTeam = state.getEastTeamInfo();
			enemyTeam = state.getWestTeamInfo();
		}
		
		for (int iPlayer = 0; iPlayer < myTeam.size(); iPlayer++) {
			inputValues[5 + 2 * iPlayer] = myTeam.get(iPlayer).getPositionX();
			inputValues[5 + 2 * iPlayer + 1] = myTeam.get(iPlayer).getPositionY();
		}
		for (int iPlayer = 0; iPlayer < enemyTeam.size(); iPlayer++) {
			inputValues[15 + 2 * iPlayer] = enemyTeam.get(iPlayer).getPositionX();
			inputValues[15 + 2 * iPlayer + 1] = enemyTeam.get(iPlayer).getPositionY();
		}

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
