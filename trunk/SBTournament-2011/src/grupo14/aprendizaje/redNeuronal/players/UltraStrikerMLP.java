package grupo14.aprendizaje.redNeuronal.players;

import java.util.ArrayList;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

public class UltraStrikerMLP extends PlayerMLP {

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
