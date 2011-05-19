package grupo14.aprendizaje.redNeuronal;

import grupo14.aprendizaje.redNeuronal.players.DefenderMLP;
import grupo14.aprendizaje.redNeuronal.players.PlayerMLP;
import grupo14.aprendizaje.redNeuronal.players.StrikerMLP;
import grupo14.aprendizaje.redNeuronal.players.UltraDefenderMLP;
import grupo14.aprendizaje.redNeuronal.players.UltraStrikerMLP;

import java.util.ArrayList;

public class TeamMLP {
	
	/** Tabla de jugadores de un equipo. */
	private ArrayList<PlayerMLP> players;
	
	public TeamMLP() {
		UltraDefenderMLP ultraDefender = new UltraDefenderMLP();
		ultraDefender.readFromFile("robots/grupo14/aprendizaje/trainingfiles/MLP/UltraDefender");
		DefenderMLP defender = new DefenderMLP();
		defender.readFromFile("robots/grupo14/aprendizaje/trainingfiles/MLP/Defender");
		StrikerMLP striker = new StrikerMLP();
		striker.readFromFile("robots/grupo14/aprendizaje/trainingfiles/MLP/Striker");
		UltraStrikerMLP ultraStriker = new UltraStrikerMLP();
		ultraStriker.readFromFile("robots/grupo14/aprendizaje/trainingfiles/MLP/UltraStriker");
		
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
