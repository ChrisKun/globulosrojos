package sistema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import principal.MejoresJuegos;

import sistema.Perfil.FormaDeSer;
import sistema.Perfil.Gender;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseBaseFilter;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.InitializingException;

public class ProfileConnector2 implements Connector {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initFromXMLfile(URL arg0) throws InitializingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<CBRCase> retrieveAllCases() {
		MejoresJuegos.init();
		ArrayList<CBRCase> cases = new ArrayList<CBRCase>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("perfil.dat"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				Perfil perfil = new Perfil();
				
				String [] splittedLine = line.split("-");
				// Si el usuario no se ha cargado correctamente leemos otra linea
				if (splittedLine.length < 8) continue;
				// Nickname
				perfil.setNickName(splittedLine[0]);
				// Age
				perfil.setAge(Integer.parseInt(splittedLine[1]));
				// Gender
				if (splittedLine[2].equals("Male")) perfil.setGender(Gender.Male);
				else perfil.setGender(Gender.Female);
				// Forma de ser
				if (splittedLine[3].equals("agresivo")) perfil.setFormaDeSer(FormaDeSer.agresivo);
				else if (splittedLine[3].equals("solitario")) perfil.setFormaDeSer(FormaDeSer.solitario);
				else if (splittedLine[3].equals("friki")) perfil.setFormaDeSer(FormaDeSer.friki);
				else if (splittedLine[3].equals("cotilla")) perfil.setFormaDeSer(FormaDeSer.cotilla);
				else if (splittedLine[3].equals("extrovetido")) perfil.setFormaDeSer(FormaDeSer.extrovetido);
				else if (splittedLine[3].equals("clasico")) perfil.setFormaDeSer(FormaDeSer.clasico);
				else if (splittedLine[3].equals("trabajador")) perfil.setFormaDeSer(FormaDeSer.trabajador);
				else if (splittedLine[3].equals("calculador")) perfil.setFormaDeSer(FormaDeSer.calculador);
				else if (splittedLine[3].equals("aventurero")) perfil.setFormaDeSer(FormaDeSer.aventurero);
				// Tiene memoria
				perfil.setTieneBuenaMemoria(splittedLine[4].equals("true"));
				// Tiene paciencia
				perfil.setTienePaciencia(splittedLine[5].equals("true"));
				// Lista de valoraciones
				HashMap<Integer, Float> listaValoraciones = new HashMap<Integer, Float>();
				String [] valoraciones = splittedLine[6].replace("{", "").replace("}", "").split(", ");
				for (String parValoracion : valoraciones) {
					String [] parValoracionSplitted = parValoracion.split("=");
					Integer gameID = Integer.parseInt(parValoracionSplitted[0]);
					Float valoracion = Float.parseFloat(parValoracionSplitted[1]);
					listaValoraciones.put(gameID, valoracion);	
					// Registramos cada valoración para hacer la media en los mejores juegos
					MejoresJuegos.addValoracion(gameID, valoracion);
				}
				perfil.setListaValoraciones(listaValoraciones);
				
				// ID del usuario
				perfil.setId(Integer.parseInt(splittedLine[7]));

				// Añadimos el nuevo perfil al CBR
				CBRCase _case = new CBRCase();
				_case.setDescription(perfil);
				cases.add(_case);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cases;
	}

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}

}
