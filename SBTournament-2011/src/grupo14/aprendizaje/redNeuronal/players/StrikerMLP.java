package grupo14.aprendizaje.redNeuronal.players;

import java.util.ArrayList;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

public class StrikerMLP extends PlayerMLP {
	@Override
	protected boolean isGoodMove(LogEntry oldState, LogEntry newState, int fieldSide) {
		/* Es una buena jugada si:
		 *   · La pelota cambia de campo
		 *   · La pelota avanza una cierta distancia en 3/4 de su campo */
		Vec2 oldBallPosition = new Vec2(oldState.getBallInfo().getPositionX(), oldState.getBallInfo().getPositionY());
		Vec2 newBallPosition = new Vec2(newState.getBallInfo().getPositionX(), newState.getBallInfo().getPositionY());
		
		if ((oldBallPosition.x * fieldSide > 0 && newBallPosition.x * fieldSide < 0) ||
			(oldBallPosition.x * fieldSide - 0.5 < 0 && oldBallPosition.distance(newBallPosition) > 0.3))
			return true;
		else return false;
	}
	
	@Override
	protected int getReferencePlayer(LogEntry state, int fieldSide) {
		/* El jugador de referencia es el que está más cercano a la bola y se encuentra 
		 * en 3/4 del campo contrario
		 * Si ese jugador no existe entonces cogemos el segundo jugador más adelantado
		 * de nuestro equipo */
		int id = -1;
		int idEx1 = -1;
		int idEx2 = -1;
		double distance = Double.POSITIVE_INFINITY;
		double xPos1 = Double.POSITIVE_INFINITY;
		double xPos2 = Double.POSITIVE_INFINITY;
		Vec2 ballPosition = new Vec2(state.getBallInfo().getPositionX(), state.getBallInfo().getPositionY());
		
		ArrayList<PlayerInfo> team;
		if (fieldSide == -1)
			team = state.getWestTeamInfo();
		else team = state.getEastTeamInfo();
		
		for (PlayerInfo player : team) {
			Vec2 playerPosition = new Vec2(player.getPositionX(), player.getPositionY());
			double distanceBallPlayer = ballPosition.distance(playerPosition);
			
			if ((playerPosition.x * fieldSide - 0.5 < 0) && 
				(distanceBallPlayer < distance)) {
				id = player.getRobotId();
				distance = distanceBallPlayer;
			}
			
			if (playerPosition.x * fieldSide < xPos1) {
				idEx2 = idEx1;
				xPos2 = xPos1;
				idEx1 = player.getRobotId();
				xPos1 = player.getPositionX() * fieldSide;
			}
			else if (player.getPositionX() * fieldSide < xPos2) {
				idEx2 = player.getRobotId();
				xPos2 = player.getPositionX() * fieldSide;
			}
		}
		
		return (id != -1)? id : idEx2;
	}
}