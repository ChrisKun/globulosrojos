package grupo14.players;

import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Striker extends Role {

	@Override
	public int configure() {
		// TODO Auto-generated method stub
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		// TODO Auto-generated method stub
		return WorldAPI.ROBOT_OK;
	}

}
