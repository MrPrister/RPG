package tools.random;

import java.util.ArrayList;

public class Random {

	public Random() {
		
	}
	
	/**
	 * return a random integer between the min and max (inclusive)
	 * @param min
	 * @param max
	 * @return
	 */
	public static int number(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	/**
	 * return a random element from an array
	 * @param object
	 * @return
	 */
	public static Object object(Object[] object) {
		int rnd = number(0, (object.length - 1));
		return object[rnd];
	}
	
	/**
	 * produce a random number that is influenced by luck
	 * @param min minimum value (further altered by luck)
	 * @param max max possible value
	 * @param luck value between 1 and 10, higher is more likely to return a higher result
	 * @param luckInfluence how much luck influences the number 1.0 is default
	 * @return
	 */
	public static int numberLuck(int min, int max, int luck, float luckInfluence) {
		int random = 0;
		
		java.util.Random rand = new java.util.Random();
		double num = rand.nextGaussian();
		double luckNum = rand.nextGaussian() + ((float) luck / 10); 
		
		random = (int) (((num + luckNum) * (max / 2)) + (max / (2 - luckNum)));
		
		if(random < min) {
			random = min;
		}
		if(random > max) {
			random = max;
		}
		
		return random;
	}
	
	public static int numberLuck(int min, int max, int luck) {
		return Random.numberLuck(min, max, luck, 1f);
	}
	
}
