package grupo14.aprendizaje.redNeuronal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Perceptron implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** N�mero de entradas incluyendo el bias. */
	private int nInputs;
	/** N�mero de capas ocultas. */
	private int nHiddenLayers;
	/** Tasa de aprendizaje para las neuronas las capas ocultas. */
	private double LR_IH = 0.7;
	/** Tasa de aprendizaje para las neuronas de salida. */
	private double LR_HO = 0.07;

	/** Salida de las neuronas de las capas ocultas. */
	private double[] hiddenVal;
	/** Salida del perceptron. */
	private double output;

	/** Matriz de pesos de las capas ocultas. */
	private double[][] weightsIH;
	/** Matriz de pesos de la capa de salida. */
	private double[] weightsHO;

	/** Constructora del perceptr�n. 
	 * @param nInputs N�mero de entradas del perceptr�n. 
	 * @param nHiddenLayers N�mero de capas ocultas del perceptr�n. */
	public Perceptron(int nInputs, int nHiddenLayers) {
		this.nInputs = nInputs;
		this.nHiddenLayers = nHiddenLayers;
		this.hiddenVal = new double[nHiddenLayers];
		this.weightsIH = new double[nInputs][nHiddenLayers];
		this.weightsHO = new double[nHiddenLayers];
		initWeights();
	}
	
	/** Ejecuta la red neuronal para averiguar la salida que devuelve para un
	 * caso concreto.
	 * @param inputValues Entrada del perceptr�n.
	 * @return Valor de la salida tras la ejecuci�n. */
	public double compute(double[] inputValues) {
		// Se calculan las salidas de las capas ocultas mediante forward propagation
		for (int i = 0; i < nHiddenLayers; i++) {
			hiddenVal[i] = 0.0;

			for (int j = 0; j < nInputs; j++)
				hiddenVal[i] = hiddenVal[i]	+ (inputValues[j] * weightsIH[j][i]);

			hiddenVal[i] = tanh(hiddenVal[i]);
		}

		// Se calcula la salida del perceptr�n
		output = 0.0;

		for (int i = 0; i < nHiddenLayers; i++)
			output = output + hiddenVal[i] * weightsHO[i];
		
		return output;
	}
	
	/** Entrena el perceptr�n dada la entrada y la salida esperada.
	 * @param inputValues Entrada del perceptr�n.
	 * @param expectedValue Valor esperado de la salida. */
	public void train(double[] inputValues, double expectedValue) {
		// Se calculan las salidas de las capas ocultas mediante forward propagation
		for (int i = 0; i < nHiddenLayers; i++) {
			hiddenVal[i] = 0.0;

			for (int j = 0; j < nInputs; j++)
				hiddenVal[i] = hiddenVal[i]	+ (inputValues[j] * weightsIH[j][i]);

			hiddenVal[i] = tanh(hiddenVal[i]);
		}

		// Se calcula la salida del perceptr�n
		output = 0.0;

		for (int i = 0; i < nHiddenLayers; i++)
			output = output + hiddenVal[i] * weightsHO[i];

		// Aplica backpropagation para actualizar los pesos del perceptr�n
		weightChangesHO(output - expectedValue);
		weightChangesIH(output - expectedValue, inputValues);
	}

	/** Aplica backpropagation en la capa de salida. 
	 * @param error El error para la capa de salida. */
	private void weightChangesHO(double error) {
		for (int k = 0; k < nHiddenLayers; k++) {
			double weightChange = LR_HO * error * hiddenVal[k];
			weightsHO[k] = weightsHO[k] - weightChange;

			// Se impone un l�mite en el valor de los pesos
			if (weightsHO[k] < -5)
				weightsHO[k] = -5;
			else if (weightsHO[k] > 5)
				weightsHO[k] = 5;
		}
	}

	/** Aplica backpropagation para las capas ocultas. 
	 * @param error Error para las capas ocultas.
	 * @param inputValues Valores de entrada del perceptr�n. */
	private void weightChangesIH(double error, double []inputValues) {
		for (int i = 0; i < nHiddenLayers; i++) {
			for (int k = 0; k < nInputs; k++) {
				double x = 1 - (hiddenVal[i] * hiddenVal[i]);
				x = x * weightsHO[i] * error * LR_IH;
				x = x * inputValues[k];
				double weightChange = x;
				weightsIH[k][i] = weightsIH[k][i] - weightChange;
			}
		}
	}
	
	/** Inicializa las matrices de pesos. */
	private void initWeights() {
		for(int j = 0; j < nHiddenLayers; j++) {
			weightsHO[j] = (Math.random() - 0.5) / 2;
			for(int i = 0; i < nInputs; i++)
				weightsIH[i][j] = (Math.random() - 0.5) / 5;
		}
	}

	/** Funci�n de activaci�n tanh. 
	 * @param x Valor de entrada. 
	 * @return Valor de la funci�n. */
	private static double tanh(double x) {
		if (x > 20)
			return 1;
		else if (x < -20)
			return -1;
		else {
			double a = Math.exp(x);
			double b = Math.exp(-x);
			return (a - b) / (a + b);
		}
	}
	
	/** Devuelve la salida del perceptr�n. 
	 * @return La salida del perceptron. */
	public double getOutput() {return output;}
	
	/** Guarda el perceptron en un fichero.
	 * @param path Ruta al fichero donde se va a guardar el perceptr�n. */
	public void writeToFile(String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	/** Lee un perceptron desde un fichero.
	 * @param path Ruta al fichero donde est� almacenado el peceptr�n.
	 * @return Perceptron le�do. */
	public static Perceptron readFromFile(String path) {
		Perceptron p = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			p = (Perceptron) ois.readObject();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		return p;
	}
	
	public static void main(String args[]) {
		Perceptron mlp = readFromFile("perceptron.mlp");
		
		double[] input = new double[3];
		for (int i = 0; i < 500; i++) {
			input[0] = 0.0; input[1] = 0.0; input[2] = 1.0;
			mlp.train(input, 0.0);
			input[0] = 0.0; input[1] = 1.0; input[2] = 1.0;
			mlp.train(input, 1.0);
			input[0] = 1.0; input[1] = 0.0; input[2] = 1.0;
			mlp.train(input, 1.0);
			input[0] = 1.0; input[1] = 1.0; input[2] = 1.0;
			mlp.train(input, 0.0);
		}
		
		input[0] = 0.0; input[1] = 0.0; input[2] = 1.0;
		mlp.train(input, 0.0);
		System.out.println(mlp.getOutput());
		input[0] = 0.0; input[1] = 1.0; input[2] = 1.0;
		mlp.train(input, 1.0);
		System.out.println(mlp.getOutput());
		input[0] = 1.0; input[1] = 0.0; input[2] = 1.0;
		mlp.train(input, 1.0);
		System.out.println(mlp.getOutput());
		input[0] = 1.0; input[1] = 1.0; input[2] = 1.0;
		mlp.train(input, 0.0);
		System.out.println(mlp.getOutput());
		
		mlp.writeToFile("perceptron.mlp");
	}
}
