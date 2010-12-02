package RecomendadorJuegos;
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
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("src/RecomendadorJuegos/ratings"));
			String line = null;
			Perfil perfil = new Perfil();
			String nombre = "";
			String[] infoRatings;
			Float f;
			Integer i;
			HashMap<Integer, Float> lista = new HashMap<Integer, Float>();
			while ((line=reader.readLine())!=null)
			{
				
				infoRatings = line.split(";");
				while(infoRatings[1].equals("name"))
				{
					line=reader.readLine();
					infoRatings = line.split(";");
				}
				if(nombre.equals(infoRatings[0]))
				{
					f = Float.valueOf(infoRatings[2].trim()).floatValue();
					i = Integer.parseInt(infoRatings[1].trim());
					System.out.println("\n"+infoRatings[0]);
					System.out.println(" - ");
					System.out.println(infoRatings[1]);
					lista.put(i, f);
					
					
					while ((line=reader.readLine())!=null && nombre.equals(infoRatings[0]))
					{
						while(infoRatings[1].equals("name"))
						{
							line=reader.readLine();
							infoRatings = line.split(";");
						}
						f = Float.valueOf(infoRatings[2].trim()).floatValue();
						i = Integer.parseInt(infoRatings[1].trim());
						System.out.println("\n"+infoRatings[0]);
						System.out.println(" - ");
						System.out.println(infoRatings[1]);
						lista.put(i, f);
						infoRatings = line.split(";");
					}
					perfil.setListaValoraciones(lista);
					CBRCase _case = new CBRCase();
					_case.setDescription(perfil);
					cases.add(_case);
					perfil = new Perfil();
					lista = new HashMap<Integer, Float>();
					if(line != null)
					{
						infoRatings = line.split(";");
						while(infoRatings[1].equals("name"))
						{
							line=reader.readLine();
							infoRatings = line.split(";");
						}
					}
				}
				nombre = infoRatings[0];
				perfil.setNickName(nombre);
				f = Float.valueOf(infoRatings[2].trim()).floatValue();
				i = Integer.parseInt(infoRatings[1].trim());
				System.out.println("\n"+infoRatings[0]);
				System.out.println(" - ");
				System.out.println(infoRatings[1]);
				lista.put(i, f);
				
			}
			perfil.setListaValoraciones(lista);
			CBRCase _case = new CBRCase();
			_case.setDescription(perfil);
			cases.add(_case);
			perfil = new Perfil();
			lista = new HashMap<Integer, Float>();
			
			reader.close();

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
