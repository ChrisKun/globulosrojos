package grupo14.players;

import java.util.Random;

public class MyRandom {
	private static Random myRandom = null;
	
	private MyRandom(){}
	
	private static void initRandom() {
		if (myRandom == null)
			myRandom = new Random(System.nanoTime());
	}
	
	public static double nextDouble() {
		initRandom();
		return myRandom.nextDouble();
	}
	
	public static double nextDouble(double min, double max) {
		initRandom();
		return min + myRandom.nextDouble() * (max - min);
	}
}
