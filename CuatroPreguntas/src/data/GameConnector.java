package data;

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

public class GameConnector implements Connector {

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
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/gameFiles/games"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				Game game = new Game();
				
				game.setUrl(line);//URL
				game.setgameId(Integer.parseInt(reader.readLine()));//GAMEID
				game.setCodeName(reader.readLine());//CODENAME
				game.setName(reader.readLine());//NAME
				game.setImage(reader.readLine());//IMAGE
				String string = reader.readLine();//ARTISTS
				string = deleteSubStrings(string,"[","]");
				game.setArtists(generateStringArrayList(string.split(", ")));
				string = reader.readLine();//DESIGNERS
				string = deleteSubStrings(string,"[","]");
				game.setDesigners(generateStringArrayList(string.split(", ")));
				string = reader.readLine();//PUBLISHERS
				string = deleteSubStrings(string,"[","]");
				game.setPublishers(generateStringArrayList(string.split(", ")));
				game.setYearPublished(Integer.parseInt(reader.readLine()));//YEARPUBLISHED
				game.setNumPlayers(generateIntegerArrayList(reader.readLine().split("&nbsp;&minus;&nbsp;")));//NUMPLAYERS
				string = reader.readLine();//BESTNUMPLAYERS
				string = deleteSubStrings(string, "Best with ", " players");
				string = string.replace("more than ", "");
				game.setBestNumPlayers(generateIntegerArrayList(string.split(", ")));
				string = reader.readLine();//RECNUMPLAYERS
				string = deleteSubStrings(string, "Recommended with ", " players");
				string = string.replace("more than ", "");
				game.setRecNumPlayers(generateIntegerArrayList(string.split(", ")));
				string = reader.readLine().split(" ")[0];//PLAYINGTIME
				if(string.equals("&nbsp;"))
					game.setPlayingTime(0);
				else
					game.setPlayingTime(Integer.parseInt(string));
				string = reader.readLine().split(" ")[0];
				if(string.equals("&nbsp;"))
					game.setAge(0);//AGE
				else
					game.setAge(Integer.parseInt(string));
				string = reader.readLine();//SUBDOMAINS
				string = deleteSubStrings(string, "[", "]");
				game.setSubdomains(generateStringArrayList(string.split(", ")));
				string = reader.readLine();//CATEGORIES
				string = deleteSubStrings(string, "[", "]");
				game.setCategories(generateStringArrayList(string.split(", ")));
				string = reader.readLine();//MECHANICS
				string = deleteSubStrings(string, "[", "]");
				game.setMechanics(generateStringArrayList(string.split(", ")));
				reader.readLine();
				System.out.println(game.getName());

				if (!reader.readLine().equals("<GAME>"))
					throw new Exception("File format error");

				CBRCase _case = new CBRCase();
				_case.setDescription(game);
				cases.add(_case);

			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cases;
	}

	private ArrayList<Integer> generateIntegerArrayList(String[] stringArray) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i< stringArray.length; i++)
			list.add(Integer.parseInt(stringArray[i].trim()));
		return list;
	}

	private ArrayList<String> generateStringArrayList(String[] stringArray) {
			
			ArrayList<String> list = new ArrayList<String>();
			for(int i = 0; i< stringArray.length; i++)
				list.add(stringArray[i]);
			return list;
		}
	
	private String deleteSubStrings(String str, String elementOne, String elementTwo)
	{
		str = str.replace(elementOne, "");
		str = str.replace(elementTwo, "");
		return str;
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
