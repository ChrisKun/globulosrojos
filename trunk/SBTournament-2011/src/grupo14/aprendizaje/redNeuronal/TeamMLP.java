package grupo14.aprendizaje.redNeuronal;

import grupo14.aprendizaje.redNeuronal.players.DefenderMLP;
import grupo14.aprendizaje.redNeuronal.players.PlayerMLP;
import grupo14.aprendizaje.redNeuronal.players.StrikerMLP;
import grupo14.aprendizaje.redNeuronal.players.UltraDefenderMLP;
import grupo14.aprendizaje.redNeuronal.players.UltraStrikerMLP;

import java.io.File;
import java.util.ArrayList;

public class TeamMLP {
	
	/** Tabla de jugadores de un equipo. */
	private ArrayList<PlayerMLP> players;
	
	public TeamMLP() {
		UltraDefenderMLP ultraDefender = new UltraDefenderMLP();
		ultraDefender.readFromFile(getFolderPath("mlpTSUltraDefender"));
		DefenderMLP defender = new DefenderMLP();
		defender.readFromFile(getFolderPath("mlpTSDefender"));
		StrikerMLP striker = new StrikerMLP();
		striker.readFromFile(getFolderPath("mlpTSStriker"));
		UltraStrikerMLP ultraStriker = new UltraStrikerMLP();
		ultraStriker.readFromFile(getFolderPath("mlpTSUltraStriker"));
		
		players = new ArrayList<PlayerMLP>();
		players.add(ultraDefender);
		players.add(defender);
		players.add(striker);
		players.add(ultraStriker);
	}
	
	public double getAverageConfidence() {
		double totalConfidence;
		if (players.get(0).getLastMove() == null ||
			players.get(1).getLastMove() == null ||
			players.get(2).getLastMove() == null ||
			players.get(3).getLastMove() == null)
			totalConfidence = 0.0;
		else {
			totalConfidence = players.get(0).getLastMove().getConfidence() +
							  players.get(1).getLastMove().getConfidence() +
							  players.get(2).getLastMove().getConfidence() +
							  players.get(3).getLastMove().getConfidence();
		}
		
		return totalConfidence / 4.0;
		
	}
	
	public static String getFolderPath(String folderName) {
		String folderPath = "";
		boolean found = false;
		
		ArrayList<File> files = new ArrayList<File>();
		files.add(new File("."));
		while (!found && files.size() > 0) {
			File actualFile = files.get(0);
			if (actualFile.isDirectory()) {
				if (actualFile.getAbsolutePath().endsWith(File.separator + folderName)) {
					folderPath = actualFile.getAbsolutePath();
					found = true;
				}
				File[] listOfFiles = actualFile.listFiles();
				for (File f : listOfFiles)
					files.add(f);
			}
			else if (actualFile.isFile()) {
			}
			files.remove(0);
		}
		
		return folderPath + File.separator;
	}
	
	public UltraDefenderMLP getUltraDefender() {
		return (UltraDefenderMLP) players.get(0);
	}
	
	public DefenderMLP getDefender() {
		return (DefenderMLP) players.get(1);
	}
	
	public StrikerMLP getStriker() {
		return (StrikerMLP) players.get(2);
	}
	
	public UltraStrikerMLP getUltraStriker() {
		return (UltraStrikerMLP) players.get(3);
	}
	
}
