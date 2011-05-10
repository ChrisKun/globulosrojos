package grupo14.aprendizaje.redNeuronal.players;

import java.util.ArrayList;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;
import grupo14.aprendizaje.redNeuronal.log.PlayerInfo;

public class DefenderMLP extends PlayerMLP {

	@Override
	protected boolean isGoodMove(LogEntry oldState, LogEntry newState, int fieldSide) {
		/* Es una buena jugada si:
		 *   · La pelota está en 3/4 de nuestro campo y avanza una determinada cantidad en X */
		Vec2 oldBallPosition = new Vec2(oldState.getBallInfo().getPositionX(), oldState.getBallInfo().getPositionY());
		Vec2 newBallPosition = new Vec2(newState.getBallInfo().getPositionX(), newState.getBallInfo().getPositionY());
		
		if (oldBallPosition.x * fieldSide + 0.5 > 0 && (newBallPosition.x - oldBallPosition.x) * fieldSide < 0.4) 
			return true;
		else return false;
	}
	
	@Override
	protected int getReferencePlayer(LogEntry state, int fieldSide) {
		/* El jugador de referencia es aquel que esté más cercano a la pelota por
		 * la izquierda y esté en nuestro campo
		 * Si este jugador no existe se saca el segundo más adelantado del equipo */
		int id = -1;
		int idEx1 = -1; 
		int idEx2 = -1;
		double distance = Double.MAX_VALUE;
		double xPos1 = Double.MIN_VALUE;
		double xPos2 = Double.MIN_VALUE;
		Vec2 ballPosition = new Vec2(state.getBallInfo().getPositionX(), state.getBallInfo().getPositionY());
		
		ArrayList<PlayerInfo> team;
		if (fieldSide == -1)
			team = state.getWestTeamInfo();
		else team = state.getEastTeamInfo();
		
		for (PlayerInfo player : team) {
			Vec2 playerPosition = new Vec2(player.getPositionX(), player.getPositionY());
			double distanceBallPlayer = ballPosition.distance(playerPosition);
			if ((playerPosition.x * fieldSide > ballPosition.x) &&
				(distanceBallPlayer < distance)) {
				id = player.getRobotId();
				distance = distanceBallPlayer;
			}
			
			if (player.getPositionX() * fieldSide > xPos1) {
				idEx2 = idEx1;
				xPos2 = xPos1;
				idEx1 = player.getRobotId();
				xPos1 = player.getPositionX() * fieldSide;
			}
			else if (player.getPositionX() * fieldSide > xPos2) {
				idEx2 = player.getRobotId();
				xPos2 = player.getPositionX() * fieldSide;
			}
		}
		
		return (id != -1)? id : idEx2;
	}

}
