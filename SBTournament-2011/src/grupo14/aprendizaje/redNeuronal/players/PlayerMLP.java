package grupo14.aprendizaje.redNeuronal.players;

import grupo14.aprendizaje.redNeuronal.Perceptron;
import grupo14.aprendizaje.redNeuronal.log.LogEntry;

import java.io.File;
import java.util.HashMap;

public abstract class PlayerMLP {

	/** Lista de perceptrones de un solo jugador, uno por cada 
	 * estado posible de un jugador. */
	private HashMap<String, Perceptron> mlps;
	
	/** Lee todos los perceptrones de un directorio dado y los almacena
	 * en la lista de perceptrones del jugador.
	 * @param directory Directorio donde están los perceptrones. */
	public void readFromFile(String directory) {
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for (int iFile = 0; iFile < listOfFiles.length; iFile++)
			mlps.put(listOfFiles[iFile].getName(), Perceptron.readFromFile(listOfFiles[iFile].getAbsolutePath()));
	}
	
	/** Aprende un nuevo caso en base a la evolución del juego.
	 * @param oldState Estado del juego en el pasado.
	 * @param newState Estado del juego en el presente. */
	public abstract void train(LogEntry oldState, LogEntry newState);

}
