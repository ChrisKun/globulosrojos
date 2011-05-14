package grupo14.aprendizaje.redNeuronal.players;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.LogParser;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;
import grupo14.players.Acciones.Accion;

public class UltraDefenderMLP extends PlayerMLP {
	
	public static void main(String args[]) {
		UltraDefenderMLP ultraDefender = new UltraDefenderMLP();
		
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
					ultraDefender.train(oldEntry, newEntry);
					casos++;
					oldEntry = newEntry;
				}
			}
		}
		
		ultraDefender.writeToFile("training/MLP/UltraDefender/");
	} 
	
	public UltraDefenderMLP() {
		actionWeights = new HashMap<Accion, Double>();
		actionWeights.put(Accion.chutarAPuerta, 1.0);
		actionWeights.put(Accion.controlarLaPelota, 0.8);
		actionWeights.put(Accion.correrADefensa, 1.1);
		actionWeights.put(Accion.correrAlAtaque, 0.5);
		actionWeights.put(Accion.correrHaciaBalon, 1.0);
		actionWeights.put(Accion.irALaFrontalContrariaArr, 0.5);
		actionWeights.put(Accion.irALaFrontalContrariaAb, 0.5);
		actionWeights.put(Accion.irALaFrontalPropiaArr, 1.0);
		actionWeights.put(Accion.irALaFrontalPropiaAb, 1.0);
		actionWeights.put(Accion.irALaMedularArr, 0.8);
		actionWeights.put(Accion.irALaMedularAb, 0.8);
		actionWeights.put(Accion.irAlCentroDelCampo, 0.8);
		actionWeights.put(Accion.taparPorteria, 1.0);
	}
	
	@Override
	protected boolean isGoodMove(LogEntry oldState, LogEntry newState, int fieldSide) {
		/* Es una buena jugada si:
		 *   · La pelota pasa de nuestro campo al campo contrario.
		 *   · La pelota está en nuestro campo y avanza una determinada cantidad en X */
		Vec2 oldBallPosition = new Vec2(oldState.getBallInfo().getPositionX(), oldState.getBallInfo().getPositionY());
		Vec2 newBallPosition = new Vec2(newState.getBallInfo().getPositionX(), newState.getBallInfo().getPositionY());
		
		if ((oldBallPosition.x * fieldSide > 0 && newBallPosition.x * fieldSide < 0) ||
			(oldBallPosition.x * fieldSide > 0 && (newBallPosition.x - oldBallPosition.x) * fieldSide < 0.3)) 
			return true;
		else return false;	
	}
	
	@Override
	protected int getReferencePlayer(LogEntry state, int fieldSide) {
		/* El jugador de referencia es el que esté más retrasado en su campo
		 * (sin contar al portero, es decir, el segundo más retrasado) */
		int id1 = -1; // Id del jugador más retrasado (lolazo)
		int id2 = -1; // Id del segundo jugador más retrasado (lolapong)
		double xPos1 = Double.NEGATIVE_INFINITY; // Posición del jugador más retrasado (facepalm)
		double xPos2 = Double.NEGATIVE_INFINITY; // Posición del segundo jugador más retrasado (u serious?1)
		
		ArrayList<PlayerInfo> team;
		if (fieldSide == -1)
			team = state.getWestTeamInfo();
		else team = state.getEastTeamInfo();
		
		for (PlayerInfo player : team)
			if (player.getPositionX() * fieldSide > xPos1) {
				id2 = id1;
				xPos2 = xPos1;
				id1 = player.getRobotId();
				xPos1 = player.getPositionX() * fieldSide;
			}
			else if (player.getPositionX() * fieldSide > xPos2) {
				id2 = player.getRobotId();
				xPos2 = player.getPositionX() * fieldSide;
			}
		
		return id2;
	}

}
