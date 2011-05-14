package grupo14.players;
import grupo14.aprendizaje.redNeuronal.players.UltraDefenderMLP;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.team.Ordenes;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.WorldAPI;

public class MegaDefender extends X{

	String role="megaDefensor";
	UltraDefenderMLP mlp;
	Ordenes ordenesDeEquipo;
	
	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString(role);
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		ordenesDeEquipo.getInstance();
		//Inicializacion de la red neuronal para el ultra defensor
		mlp = new UltraDefenderMLP();
		mlp.readFromFile("training/MLP/UltraDefender");
		
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		//Ver si hay nueva orden de la red neuronal
		//if( HAY NUEVA ORDEN DE RED NEURONAL  )
			//Codigo Para Sergio
		//Ver si el entrenador ha ordenado un nuevo estado
		/*else*/if(this.ordenesDeEquipo.hayNuevoEstado())
			//Pasar al estado ordenado
			matchStateUtils.matchState = ordenesDeEquipo.pasarAEstado();
		else
		{
			matchStateUtils.getMatchState(this.worldAPI);
			matchStateUtils.matchState.accionARealizar(worldAPI, role);
		}
		
		return WorldAPI.ROBOT_OK;
	}

}
