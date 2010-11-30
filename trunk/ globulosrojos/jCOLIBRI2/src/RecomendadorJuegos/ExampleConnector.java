package RecomendadorJuegos;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CaseBaseFilter;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.InitializingException;


public class ExampleConnector implements Connector {

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
			BufferedReader reader = new BufferedReader(new FileReader("games"));
			String line = null;
			while ((line=reader.readLine())!=null)
			{
				Game game = new Game();
				game.setUrl(line);
				game.setCode(reader.readLine());
				game.setCodeName(reader.readLine());
				game.setTitle(reader.readLine());
				game.setImage(reader.readLine());
				String artists = reader.readLine();
				game.setArtists(new ArrayList<String>(Arrays.asList(artists.split(",[")))); //Sin probar...
				
				//...
				
				if(!reader.readLine().equals("<GAME>"))
					throw new Exception("File format error");
				
				CBRCase _case = new CBRCase();
				_case.setDescription(game);
				cases.add(_case);
				
			}
			reader.close();

		}catch (Exception e)
		{
			//error...
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
