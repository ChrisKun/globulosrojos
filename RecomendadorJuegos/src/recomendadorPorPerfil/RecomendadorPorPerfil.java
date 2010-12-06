package recomendadorPorPerfil;

import java.util.ArrayList;
import java.util.Collection;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.selection.SelectCases;
import sistema.Game;
import sistema.Perfil;
import sistema.Sistema;

import java.util.Map.Entry;

import criticar.InterfazCriticar;

public class RecomendadorPorPerfil {

	public RecomendadorPorPerfil() {
		
	}
	
	public void recomendar() {
		ArrayList<Integer> juegosRecomendadosID = new ArrayList<Integer>();
		ArrayList<CBRCase> juegosRecomendados = new ArrayList<CBRCase>();
		
		Perfil perfil = Sistema.getPerfil();
		
		// TODO Queda el simConfig de usuarios (2 - null)
		// Creamos la query con la información del usuario logueado
		CBRQuery query = new CBRQuery();
		query.setDescription(perfil);
		
		// Obtenemos los 5 usuarios más parecidos
		Collection<RetrievalResult> usuariosParecidos = NNScoringMethod.evaluateSimilarity(Sistema.getCBusuariosInstance().getCases(), query, null);
		Collection<CBRCase> selectedCases = SelectCases.selectTopK(usuariosParecidos, 6);
		
		// Por cada lista de valoraciones de cada usuario obtenemos los 2 mejores juegos
		for (CBRCase cbrCase : selectedCases) {
			Perfil p = (Perfil) cbrCase.getDescription();
			// Si el usuario recogido de la CBR es el mismo que el logueado nos olvidamos de él
			if (p.getNickName().equals(perfil.getNickName())) continue;
			
			int gameID1 = 0;
			int gameID2 = 0;
			float valGameID1 = 0;
			float valGameID2 = 0;

			for (Entry<Integer, Float> valoracion : p.getListaValoraciones().entrySet()) {
				if (valoracion.getValue() > valGameID1) {
					gameID2 = gameID1;
					valGameID2 = valGameID1;
					gameID1 = valoracion.getKey();
					valGameID1 = valoracion.getValue();
				}
				else if (valoracion.getValue() > valGameID2) {
					gameID2 = valoracion.getKey();
					valGameID2 = valoracion.getValue();
				}
			}
			
			juegosRecomendadosID.add(gameID1);
			juegosRecomendadosID.add(gameID2);
		}
		
		// Extraemos la información de los juegos por sus identificadores
		for (CBRCase c : Sistema.getCBjuegosInstance().getCases()) {
			// Si el identificador coincide con alguno de los del array de juegos recomendados lo metemos
			if (juegosRecomendadosID.contains(((Game)c.getDescription()).getgameId()))
				juegosRecomendados.add(c);
		}
		
		InterfazCriticar ic = new InterfazCriticar(juegosRecomendados);
		ic.show();
	}
}


