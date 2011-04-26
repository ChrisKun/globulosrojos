package grupo14.players;

import teams.rolebased.WorldAPI;

public interface MatchState {
	abstract int accionARealizar(WorldAPI worldAPI, String role);
	
	abstract String getStateName();
}
