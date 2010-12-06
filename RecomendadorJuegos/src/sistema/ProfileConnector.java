package sistema;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseBaseFilter;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.InitializingException;
import jcolibri.extensions.recommendation.navigationByAsking.InformationGain;


public class ProfileConnector implements Connector {

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCases(Collection<CBRCase> cases) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initFromXMLfile(URL file) throws InitializingException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<CBRCase> retrieveAllCases() {
		ArrayList<CBRCase> cases = new ArrayList<CBRCase>();
		RatingConnector ratingConnector = new RatingConnector();
		ArrayList<Perfil> ratingCases = ratingConnector.retrieveAllCases();
		int i = 0;
		try
		{
			while(ratingCases.size()> i)
			{
				Perfil perfil = new Perfil();
				perfil.setNickName(((Perfil)ratingCases.get(i)).getNickName());
				perfil.setListaValoraciones(((Perfil)ratingCases.get(i)).getListaValoraciones());
				
				perfil.GenerateRandomProfiles();
				
				CBRCase _case = new CBRCase();
				_case.setDescription(perfil);
				cases.add(_case);

				i++;
			}

		}catch (Exception e)
		{
			System.out.println("\n No se pudo cargar el archivo\n");
			e.printStackTrace();
		}
		return cases;
	}

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeCases(Collection<CBRCase> cases) {
		// TODO Auto-generated method stub

	}
}
