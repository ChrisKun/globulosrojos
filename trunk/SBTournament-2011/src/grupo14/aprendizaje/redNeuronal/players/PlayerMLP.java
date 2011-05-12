package grupo14.aprendizaje.redNeuronal.players;

import grupo14.aprendizaje.redNeuronal.Perceptron;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.LogParser;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import teams.rolebased.WorldAPI;

import EDU.gatech.cc.is.util.Vec2;

public abstract class PlayerMLP {
	
	public static void main(String args[]) {
		// Se inicializan todos los perceptrones
		UltraDefenderMLP ultraDef = new UltraDefenderMLP();
		ultraDef.readFromFile("training/MLP/UltraDefender");
		DefenderMLP normalDef = new DefenderMLP();
		normalDef.readFromFile("training/MLP/Defender");
		UltraStrikerMLP ultraStriker = new UltraStrikerMLP();
		ultraStriker.readFromFile("training/MLP/UltraStriker");
		StrikerMLP normalStriker = new StrikerMLP();
		normalStriker.readFromFile("training/MLP/Striker");
		
		// Por cada fichero de log de la carpeta logfiles se entrenan los perceptrones
		int casos = 0;
		LogEntry oldEntry, newEntry;
		File folder = new File("logfiles");
		File[] listOfFiles = folder.listFiles();
		for (int iFile = 0; iFile < listOfFiles.length; iFile++) {
			if (listOfFiles[iFile].getName().endsWith(".xml")) {
				LogParser parser = new LogParser(listOfFiles[iFile].getAbsolutePath());
				// Al menos hay una entrada...
				oldEntry = parser.getNextLogEntry();
				while((newEntry = parser.getNextLogEntry()) != null) {
					ultraDef.train(oldEntry, newEntry);
					normalDef.train(oldEntry, newEntry);
					ultraStriker.train(oldEntry, newEntry);
					normalStriker.train(oldEntry, newEntry);
					casos++;
					oldEntry = newEntry;
				}
			}
		}
		
		ultraDef.writeToFile("training/MLP/UltraDefender/");
		normalDef.writeToFile("training/MLP/Defender/");
		ultraStriker.writeToFile("training/MLP/UltraStriker/");
		normalStriker.writeToFile("training/MLP/Striker/");
		
		System.out.println("Casos: " + casos);
	}
	
	/** Lista de perceptrones de un solo jugador, uno por cada 
	 * estado posible de un jugador. */
	private HashMap<String, Perceptron> mlps;
	
	/** Constructora por defecto. */
	public PlayerMLP() {
		mlps = new HashMap<String, Perceptron>();
		for (int iAction = 0; iAction < Actions.ACTIONS.length; iAction++)
			mlps.put(Actions.ACTIONS[iAction], new Perceptron(24, 2));
	}
	
	/** Lee todos los perceptrones de un directorio dado y los almacena
	 * en la lista de perceptrones del jugador.
	 * @param directory Directorio donde están los perceptrones. */
	public void readFromFile(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for (int iFile = 0; iFile < listOfFiles.length; iFile++) {
			boolean read = false;
			for (int iAction = 0; iAction < Actions.ACTIONS.length && !read; iAction++)
				if (listOfFiles[iFile].getName().contains(Actions.ACTIONS[iAction])) {
					mlps.put(Actions.ACTIONS[iAction], Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
					read = true;
				}
		}
	}
	
	/** Guarda todos los perceptrones en un directorio dado. 
	 * @param directory Directorio donde almacenar los perceptrones. */
	public void writeToFile(String directory) {
		for (Entry<String, Perceptron> perceptron : mlps.entrySet()) {
			boolean written = false;
			for (int iAction = 0; iAction < Actions.ACTIONS.length && !written; iAction++)
				if (perceptron.getKey().equals(Actions.ACTIONS[iAction])) {
					perceptron.getValue().writeToFile(directory + Actions.ACTIONS[iAction] + ".SpongeBob");
					written = true;
				}
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
				// Se aprende el caso sólo si se ha podido reconocer un movimiento claro del jugador
				if (action != null)
					learnMove(oldState, action, fieldSide);
			}
	}	
	
	/** Mediante los perceptrones almacenados y dado el estado del juego actual
	 * devuelve la acción que debe ejecutarse a continuación.
	 * @param worldAPI Estado del juego actual.
	 * @return Siguiente acción a realizar. */
	public String getNextMove(WorldAPI worldAPI) {
		String nextMove = null;
		double betterMLPOutput = Double.NEGATIVE_INFINITY;
		
		for (Entry<String, Perceptron> p : mlps.entrySet()) {
			double output = p.getValue().compute(getMLPInputValues(worldAPI));
			if (output > betterMLPOutput) {
				nextMove = p.getKey();
				betterMLPOutput = output;
			}
		}
		
		return nextMove + " : " + betterMLPOutput;
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
			return Actions.ACTIONS[Actions.CHUTAR_A_PUERTA];
		
		/* Correr hacia el balón: 
		 *   old: 
		 *   new: el jugador está más cerca del balón
		 *        el balón no se ha movido mucho */
		if (newPlayerBallDistance < oldPlayerBallDistance - 0.1 &&
			oldBallPosition.distance(newBallPosition) < 0.3)
			return Actions.ACTIONS[Actions.CORRER_HACIA_EL_BALON];
		
		/* Tapar la portería:
		 *   old: 
		 *   new: el jugador está entre la portería y el balón (con un radio de error) */
		Vec2 newMidBallGoalPoint = new Vec2((newBallPosition.x + fieldSide * 1.37) / 2.0,
											newBallPosition.y / 2.0);
		if (newMidBallGoalPoint.distance(newPlayerPosition) < 0.3)
			return Actions.ACTIONS[Actions.TAPAR_PORTERIA];
		
		/* Correr al ataque:
		 *   old: el jugador está en su propio campo 
		 *   new: el jugador está en el campo contrario
		 *        el jugador se ha movido más de una distancia de seguridad */
		if (oldPlayerPosition.x * fieldSide > 0 &&
			newPlayerPosition.x * fieldSide < 0 && 
			newPlayerPosition.distance(oldPlayerPosition) > 0.2)
			return Actions.ACTIONS[Actions.CORRER_AL_ATAQUE];
		
		/* Correr a defensa:
		 *   old: el jugador está en el campo contrario
		 *   new: el jugador está en su propio campo
		 *        el jugador se ha movido más de una distancia de seguridad */
		if (oldPlayerPosition.x * fieldSide < 0 &&
			newPlayerPosition.x * fieldSide > 0 && 
			newPlayerPosition.distance(oldPlayerPosition) > 0.2)
			return Actions.ACTIONS[Actions.CORRER_A_DEFENSA];
		
		/* Ir al centro del campo:
		 *   old: 
		 *   new: el jugador está a una distancia cercana del centro del campo */
		if (newPlayerPosition.distance(new Vec2(0, 0)) < 0.15)
			return Actions.ACTIONS[Actions.IR_AL_CENTRO_DEL_CAMPO];
		
		/* Ir a la medular (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la medular por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(0, 0.3)) < 0.15)
			return Actions.ACTIONS[Actions.IR_A_LA_MEDULAR_ARR];
		
		/* Ir a la medular (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la medular por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(0, -0.3)) < 0.15)
			return Actions.ACTIONS[Actions.IR_A_LA_MEDULAR_AB];
		
		/* Ir a la frontal contraria (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal contraria por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(1.0 * -fieldSide, 0.3)) < 0.2)
			return Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_CONTRARIA_ARR];
		
		/* Ir a la frontal contraria (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal contraria por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(1.0 * -fieldSide, -0.3)) < 0.2)
			return Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_CONTRARIA_AB];
		
		/* Ir a la frontal propia (Arr):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal propia por arriba (Arr) */
		if (newPlayerPosition.distance(new Vec2(1.0 * fieldSide, 0.3)) < 0.2)
			return Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_PROPIA_ARR];
		
		/* Ir a la frontal propia (Ab):
		 *   old:
		 *   new: el jugador está a una distancia cercana de la frontal propia por abajo (Ab) */
		if (newPlayerPosition.distance(new Vec2(1.0 * fieldSide, -0.3)) < 0.2)
			return Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_PROPIA_AB];
		
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
		// tiempo (1) + marcador (2) + (numJugadores (10) + pelota (1)) * 2 (x, y) = 25 entradas
		double[] inputValues = new double[25];
		
		// Tiempo
		inputValues[0] = state.getTime() / 30000.0;
		
		// Marcador
		if (fieldSide == -1) {
			inputValues[1] = state.getBallInfo().getWestScore();
			inputValues[2] = state.getBallInfo().getEastScore();
		}
		else {
			inputValues[1] = state.getBallInfo().getEastScore();
			inputValues[2] = state.getBallInfo().getWestScore();
		}
		
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
		
		// Se ordenan los jugadores por su posición en el eje X
		PlayerInfo[] myTeamArray = (PlayerInfo[]) myTeam.toArray(new PlayerInfo[myTeam.size()]);
		PlayerInfo[] enemyTeamArray = (PlayerInfo[]) enemyTeam.toArray(new PlayerInfo[myTeam.size()]);
		
		Arrays.sort(myTeamArray, new PlayerInfoComparator());
		Arrays.sort(enemyTeamArray, new PlayerInfoComparator());
		
		for (int iPlayer = 0; iPlayer < myTeam.size(); iPlayer++) {
			inputValues[5 + 2 * iPlayer] = myTeamArray[iPlayer].getPositionX() * fieldSide;
			inputValues[5 + 2 * iPlayer + 1] = myTeamArray[iPlayer].getPositionY();
		}
		for (int iPlayer = 0; iPlayer < enemyTeam.size(); iPlayer++) {
			inputValues[15 + 2 * iPlayer] = enemyTeamArray[iPlayer].getPositionX() * -fieldSide;
			inputValues[15 + 2 * iPlayer + 1] = enemyTeamArray[iPlayer].getPositionY();
		}

		return inputValues;
	}
	
	/** Devuelve las entradas para el perceptrón en el formato adecuado. 
	 * @param worldAPI Estado del que sacar la información.
	 * @return Entradas para el perceptrón. */
	protected double[] getMLPInputValues(WorldAPI worldAPI) {
		double[] inputValues = new double[25];
		
		Vec2 myPosition = worldAPI.getPosition();

		// Tiempo
		inputValues[0] = worldAPI.getTimeStamp() / 30000.0;
		
		// Marcador
		inputValues[1] = worldAPI.getMyScore();
		inputValues[2] = worldAPI.getOpponentScore();
		
		// Pelota
		Vec2 ballPosition = getGlobalPosition(myPosition, worldAPI.getBall());
		inputValues[3] = ballPosition.x;
		inputValues[4] = ballPosition.y;
		
		// Jugadores		
		Vec2 []teammates = worldAPI.getTeammates();
		Arrays.sort(teammates, new Vec2Comparator());
		for (int iPlayer = 0; iPlayer < teammates.length; iPlayer++) {
			Vec2 playerPosition = getGlobalPosition(myPosition, teammates[iPlayer]);
			inputValues[5 + 2 * iPlayer] = playerPosition.x * worldAPI.getFieldSide();
			inputValues[5 + 2 * iPlayer + 1] = playerPosition.y;
		}
		Vec2 []oponents = worldAPI.getOpponents();
		Arrays.sort(oponents, new Vec2Comparator());
		for (int iPlayer = 0; iPlayer < oponents.length; iPlayer++) {
			Vec2 playerPosition = getGlobalPosition(myPosition, oponents[iPlayer]);
			inputValues[15 + 2 * iPlayer] = playerPosition.x * -worldAPI.getFieldSide();
			inputValues[15 + 2 * iPlayer + 1] = playerPosition.y;
		}
		
		return inputValues;
	}
	
	/** Transforma un punto expresado en coordenadas del jugador en coordenadas de campo.
	 * @param  */
	private static Vec2 getGlobalPosition(Vec2 refPoint, Vec2 point) {
		Vec2 globalPoint = refPoint;
		globalPoint.add(point);
		return globalPoint;
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


class PlayerInfoComparator implements Comparator<PlayerInfo> {

	@Override
	public int compare(PlayerInfo p1, PlayerInfo p2) {
		if (p1.getPositionX() - p2.getPositionX() < 0)
			return -1;
		else return 1;
	}
}

class Vec2Comparator implements Comparator<Vec2> {

	@Override
	public int compare(Vec2 v1, Vec2 v2) {
		if (v1.x - v2.x < 0)
			return -1;
		else return 1;
	}

}