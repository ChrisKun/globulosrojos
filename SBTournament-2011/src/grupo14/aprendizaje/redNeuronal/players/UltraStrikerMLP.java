package grupo14.aprendizaje.redNeuronal.players;

import java.util.ArrayList;
import java.util.HashMap;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

public class UltraStrikerMLP extends PlayerMLP {

	public UltraStrikerMLP() {
		actionWeights = new HashMap<String, Double>();
		actionWeights.put(Actions.ACTIONS[Actions.CHUTAR_A_PUERTA], 1.3);
		actionWeights.put(Actions.ACTIONS[Actions.CORRER_A_DEFENSA], 0.5);
		actionWeights.put(Actions.ACTIONS[Actions.CORRER_AL_ATAQUE], 1.3);
		actionWeights.put(Actions.ACTIONS[Actions.CORRER_HACIA_EL_BALON], 1.0);
		actionWeights.put(Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_CONTRARIA_AB], 1.2);
		actionWeights.put(Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_CONTRARIA_ARR], 1.2);
		actionWeights.put(Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_PROPIA_AB], 0.5);
		actionWeights.put(Actions.ACTIONS[Actions.IR_A_LA_FRONTAL_PROPIA_ARR], 0.5);
		actionWeights.put(Actions.ACTIONS[Actions.IR_A_LA_MEDULAR_AB], 0.8);
		actionWeights.put(Actions.ACTIONS[Actions.IR_A_LA_MEDULAR_ARR], 0.8);
		actionWeights.put(Actions.ACTIONS[Actions.IR_AL_CENTRO_DEL_CAMPO], 0.8);
		actionWeights.put(Actions.ACTIONS[Actions.TAPAR_PORTERIA], 0.5);
	}
	
	@Override
	protected boolean isGoodMove(LogEntry oldState, LogEntry newState, int fieldSide) {
		/* Es una buena jugada si:
		 *   · Ha sido gol
		 *   · La pelota avanza una cierta distancia en su campo */
		Vec2 oldBallPosition = new Vec2(oldState.getBallInfo().getPositionX(), oldState.getBallInfo().getPositionY());
		Vec2 newBallPosition = new Vec2(newState.getBallInfo().getPositionX(), newState.getBallInfo().getPositionY());
		
		int myOldScore;
		int myNewScore;
		if (fieldSide == -1) {
			myOldScore = oldState.getBallInfo().getWestScore();
			myNewScore = newState.getBallInfo().getEastScore();
		}
		else {
			myOldScore = newState.getBallInfo().getEastScore();
			myNewScore = oldState.getBallInfo().getWestScore();
		}
		
		if ((myOldScore < myNewScore) ||
			(oldBallPosition.x * fieldSide < 0 && oldBallPosition.distance(newBallPosition) > 0.2))
			return true;
		else return false;
	}
	
	@Override
	protected int getReferencePlayer(LogEntry state, int fieldSide) {
		/* El jugador de referencia es el más adelantado del equipo */
		int id = -1;
		double xPos = Double.POSITIVE_INFINITY;
		
		ArrayList<PlayerInfo> team;
		if (fieldSide == -1)
			team = state.getWestTeamInfo();
		else team = state.getEastTeamInfo();
		
		for (PlayerInfo player : team)
			if (player.getPositionX() * fieldSide < xPos) {
				id = player.getRobotId();
				xPos = player.getPositionX() * fieldSide;
			}
		
		return id;
	}

}
