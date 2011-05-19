package grupo14.team;

import grupo14.manager.Mourinho;
import grupo14.players.Defender;
import grupo14.players.FuckingStriker;
import grupo14.players.Goalkeeper;
import grupo14.players.MegaDefender;
import grupo14.players.Striker;
import teams.rolebased.Role;
import teams.rolebased.RoleBasedTeam;
import teams.rolebased.TeamManager;

public class Team extends RoleBasedTeam{

	@Override
	protected Role[] getRoles() {
		Goalkeeper goalkeeper = new Goalkeeper();
		MegaDefender megaDefender = new MegaDefender();
		goalkeeper.setDefender(megaDefender);
		megaDefender.setGoalkeeper(goalkeeper);
		
		return new Role[] { goalkeeper,
							megaDefender,
							new Defender(),
							new Striker(),
							new FuckingStriker()};
	}

	@Override
	protected TeamManager getTeamManager() {
		return new Mourinho();
	}

}
