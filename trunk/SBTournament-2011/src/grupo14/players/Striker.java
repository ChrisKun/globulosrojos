package grupo14.players;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.states.Catenaccio;
import grupo14.states.Heroica;
import grupo14.states.IrAlAtaque;
import grupo14.states.JuegoBrusco;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.states.PosesionContrarioNuestroCampo;
import grupo14.states.PosesionNuestraEnNuestroCampo;
import grupo14.states.PosesionNuestraEnSuCampo;
import grupo14.states.UltimoHombreContrario;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Striker extends Role {

	String role="delantero";
	public MatchState matchState;
	@Override
	public int configure() {
		worldAPI.setDisplayString("Delantero");	
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		MatchState state = new PosesionContrarioEnSuCampo();
		matchState = state;
		//getMatchState();
		matchState.accionARealizar(worldAPI,role);
		
		
		return WorldAPI.ROBOT_OK;
	}
	private void getMatchState() {
		int lado = worldAPI.getFieldSide(); //-1 en la derecha, 1 en la izquierda
		//si el balon lo tiene el contrario, estado=2, estado=3... 
		if(worldAPI.getMyScore()>worldAPI.getOpponentScore())
			matchState = new Catenaccio();
		else if (worldAPI.getMyScore()<worldAPI.getOpponentScore())
			matchState = new Heroica();
		else 
		{
			if (getRelativePosition(worldAPI.getBall()).x * lado < 0) {
			// el balon esta en tu campo
			if(whoHasTheBall())
				matchState = new PosesionNuestraEnNuestroCampo();
			else
				matchState = new PosesionContrarioNuestroCampo();
		} else 
		{
			if(whoHasTheBall())
				matchState = new PosesionNuestraEnSuCampo();
			else
				matchState = new PosesionContrarioEnSuCampo();
		}
	}
	}
	
	//devuelve la posicion de un item desde el centro del campo
	public Vec2 getRelativePosition(Vec2 position)
	{
		return new Vec2(worldAPI.getPosition().x+position.x,worldAPI.getPosition().y+position.y);
	}
	public boolean whoHasTheBall()
	{
		// si el balon esta en tierra de nadie, tambien lo toma como si fuera posesion contraria
		//0.06 = cankick
		double unidadDeCercania = 0.1;
		boolean nosotros = true;
		Vec2[] oponentes = worldAPI.getOpponents();
		Vec2[] companeros = worldAPI.getTeammates();
		Vec2 oponenteMasCercano = oponentes[0];
		Vec2 companeroMasCercano = companeros[0];
		Vec2 balon = getRelativePosition(worldAPI.getBall());
		int index = oponentes.length;
		for(int i=1; i<index;i++)
		{
			if((Math.abs(getRelativePosition(oponentes[i]).x - balon.x) <= unidadDeCercania) && (Math.abs(getRelativePosition(oponentes[i]).y - balon.y) <= unidadDeCercania))
			{
				oponenteMasCercano = oponentes[i];
			}
			if((Math.abs(getRelativePosition(companeros[i]).x - balon.x) <= unidadDeCercania) && (Math.abs(getRelativePosition(companeros[i]).y - balon.y) <= unidadDeCercania))
			{
				companeroMasCercano = companeros[i];
			}
		}
		if(oponenteMasCercano.x > companeroMasCercano.x && oponenteMasCercano.y > companeroMasCercano.y  )
			nosotros = true;
		else
			nosotros = false;
		return nosotros;
	}

}
