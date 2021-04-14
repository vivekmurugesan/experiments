package algo.part1.week2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Shuffle {

	public static void main(String[] args) {

		int[] input = {3,5,2,1,7,9,8,6,4,0};
		
		int[] result = shuffle(input);
		
		for(int x : result)
			System.out.printf("%d\t", x);
		
		System.out.println();
		
		int[] input1 = {3,5,2,1,7,9,8,6,4,0};
		
		shuffleLinear(input1);
		
		for(int x : input1)
			System.out.printf("%d\t", x);
		
	}
	
	public static void shuffleLinear(int[] input) {
		Arrays.parallelSort(input);
		
		int n = input.length;
		Random rand = new Random();
		
		for(int i=1;i<n;i++) {
			int r = rand.nextInt(i);
			swap(input, i, r);
		}
	}
	
	public static void swap(int[] input, int i, int j) {
		int t = input[i];
		input[i] = input[j];
		input[j] = t;
	}
	
	public static int[] shuffle(int[] input) {
		Arrays.parallelSort(input);
		
		int n = input.length;
		
		Map<Integer, Double> randKeys = new HashMap<>();
		Map<Double, Integer> randKeyIndex = new HashMap<>();
		
		double[] randoms = new double[n];
		
		Random rand = new Random();
		
		for(int i=0;i<n;i++) {
			randoms[i] = rand.nextDouble();
			randKeys.put(i, randoms[i]);
		}
		
		Arrays.parallelSort(randoms);
		
		for(int i=0;i<n;i++)
			randKeyIndex.put(randoms[i], i);
		
		int[] result = new int[n];
		
		for(int i=0;i<n;i++){
			double randKey = randKeys.get(i);
			int index = randKeyIndex.get(randKey);
			
			result[index] = input[i];
		}
		
		return result;
	}

}
