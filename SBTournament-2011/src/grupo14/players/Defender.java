package grupo14.players;

import grupo14.aprendizaje.redNeuronal.players.DefenderMLP;
import grupo14.aprendizaje.redNeuronal.players.UltraDefenderMLP;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.team.Ordenes;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Defender extends X {

	String role = "defensor";
	Ordenes ordenesDeEquipo;
	DefenderMLP mlp = null;

	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString("Defensor");
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		ordenesDeEquipo = Ordenes.getInstance();
		mlp = new DefenderMLP();
		mlp.readFromFile("training/MLP/Defender");
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		if (mlp != null)
			mlp.getNextMove(worldAPI);
		
		//Ver si hay nueva orden de la red neuronal
		if(this.ordenesDeEquipo.usarRN()) 
			//Codigo Para Sergio
			Acciones.realizaAccion(mlp.getLastMove().getAction(), worldAPI);
		//Ver si el entrenador ha ordenado un nuevo estado
		else if(this.ordenesDeEquipo.hayNuevoEstado())
			//Pasar al estado ordenado
			matchStateUtils.matchState = ordenesDeEquipo.pasarAEstado();
		else
		{
			matchStateUtils.getMatchState(this.worldAPI);
			matchStateUtils.matchState.accionARealizar(worldAPI, role);
		}

		return WorldAPI.ROBOT_OK;
	}
	
	public void setMLP(DefenderMLP mlp) {
		this.mlp = mlp;
	}
}
