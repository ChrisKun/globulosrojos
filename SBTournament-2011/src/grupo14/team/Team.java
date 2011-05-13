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
		return new Role[] { new Goalkeeper(),
							new MegaDefender(),
							new Defender(),
							new Striker(),
							new FuckingStriker()};
	}

	@Override
	protected TeamManager getTeamManager() {
		return new Mourinho();
	}

}
