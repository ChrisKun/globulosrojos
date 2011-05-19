package grupo14.utils;

import teams.rolebased.WorldAPI;
import grupo14.players.MatchState;
import grupo14.states.Catenaccio;
import grupo14.states.Heroica;
import grupo14.states.JuegoBrusco;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.states.PosesionContrarioNuestroCampo;
import grupo14.states.PosesionNuestraEnNuestroCampo;
import grupo14.states.PosesionNuestraEnSuCampo;
import grupo14.states.UltimoHombreContrario;

/**
 * Class with methods to manage the Match State 
 * @author markel
 *
 */
public class MatchStateUtils {

	public MatchState matchState;
	
	public MatchStateUtils(){
	}
	
	/**
	 * Sets the Match State using a String received as parameter
	 * @param recoveredState
	 */
	public void setMatchStateUsingName(String recoveredState) {
		if(recoveredState.equals("Catenaccio"))
			this.matchState = new Catenaccio();
		else if(recoveredState.equals("Heroica"))
			this.matchState = new Heroica();
		else if(recoveredState.equals("JuegoBrusco"))
			this.matchState = new JuegoBrusco();
		else if(recoveredState.equals("PosesionContrarioEnSuCampo"))
			this.matchState = new PosesionContrarioEnSuCampo();
		else if(recoveredState.equals("PosesionContrarioNuestroCampo"))
			this.matchState = new PosesionContrarioNuestroCampo();
		else if(recoveredState.equals("PosesionNuestraEnNuestroCampo"))
			this.matchState = new PosesionNuestraEnNuestroCampo();
		else if(recoveredState.equals("PosesionNuestraEnSuCampo"))
			this.matchState = new PosesionNuestraEnSuCampo();
		else if(recoveredState.equals("UltimoHombreContrario"))
			this.matchState = new UltimoHombreContrario();
		else if(recoveredState.equals("null"))
			this.matchState = null;
	}

	public void setMatchState(MatchState state) {
		this.matchState = state;
	}
	
	public String getMatchState(WorldAPI worldAPI) {
		int lado = worldAPI.getFieldSide(); //-1 en la derecha, 1 en la izquierda
		//si el balon lo tiene el contrario, estado=2, estado=3... 
		if(worldAPI.getMyScore() > worldAPI.getOpponentScore())
		{
			
			matchState = new Catenaccio();
			return "catenaccio";
		}
		else if (worldAPI.getMyScore() < worldAPI.getOpponentScore() && worldAPI.getMatchRemainingTime() < 15000)
		{
			matchState = new Heroica();
			return "heroica";
		}
		else 
			//tienen ellos el balon y estan cerca de la porteria
			if(!fieldUtils.whoHasTheBall(worldAPI) && fieldUtils.getLocationsOctant(worldAPI.getPosition(), worldAPI) == 1 && fieldUtils.getLocationsOctant(worldAPI.getPosition(), worldAPI) == 5)
			{
				setMatchStateUsingName("UltimoHombreContrario");
				return "UltimoHombreContrario";
			}
			else{
				{
					if (fieldUtils.getRelativePosition(worldAPI, worldAPI.getBall()).x * lado < 0) {
						// el balon esta en tu campo
						if(fieldUtils.whoHasTheBall(worldAPI)){
							matchState = new PosesionNuestraEnNuestroCampo();
							return "PosesionNuestraEnNuestroCampo";
						}
						else
						{
							matchState = new PosesionContrarioNuestroCampo();
							return "PosesionContrarioNuestroCampo";
						}
				} else 
				{
					if(fieldUtils.whoHasTheBall(worldAPI))
					{
						matchState = new PosesionNuestraEnSuCampo();
						return "PosesionNuestraEnSuCampo";
					}
					else
					{
						matchState = new PosesionContrarioEnSuCampo();
						return "PosesionContrarioEnSuCampo";
					}
		}
		}
	}
	}
}
