/* 
 * Programmer: David Kopp
 * Project #: Project 3
 * File Name: BFSKSP.java
 * Date: 11-24-15
 * Class: Comp496ALG
 * Description: This class is the BFS for solving the knapsack problem. Subset permutations was found
 * 				at http://stackoverflow.com/questions/22280078
 * 				/how-to-write-iterative-algorithm-for-generate-all-subsets-of-a-set 
 * 				(Modified by David Kopp)
 */

package project3;

import java.util.ArrayList;

public class BFSKSP {

	// Data Structures
	private ArrayList<ArrayList<Integer>> solutions;
	private int[] values;
	private int[] weights;
	
	// Variables
	private int knapSackWeightCapacity;
	private int numberOfItems;
	private int currentMaxValue;
	private String testLabel;
	private double runtime;

	
	// Main constructor for the Brute Force Solution for the Knapsack Problem
	public BFSKSP(int[] values, int[] weights, int knapSackWeightCapacity, int numberOfItems, String testLabel) {
		
		runtime = 0;
		long startTime = System.nanoTime(); 
		solutions = new ArrayList<ArrayList<Integer>>();
		int[] arr = new int[numberOfItems];
		makeItemArray(arr);
		this.values = values;
		this.weights = weights;
		this.knapSackWeightCapacity = knapSackWeightCapacity;
		this.numberOfItems = numberOfItems;
		this.testLabel = testLabel;
		currentMaxValue = 0;
		allSubsets(arr);
		long endTime = System.nanoTime();
		runtime = (endTime - startTime) / Math.pow(10, 6); // Converts from ns to ms
		generateStatistics();
	}

	// Sets the Array to items 1.....k (There is no item 0)
	private void makeItemArray(int[] arr) {
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i + 1;
		}
	}
	
	// Generates all the Subsets found at:
	// http://stackoverflow.com/questions/22280078/
	// how-to-write-iterative-algorithm-for-generate-all-subsets-of-a-set (Modified by David Kopp)
	private void allSubsets(int[] arr) {
		
		byte[] counter = new byte[arr.length];

		while (true) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			// Print combination
			for (int i = 0; i < counter.length; i++) {
				if (counter[i] != 0) {
					//System.out.print(arr[i] + " ");
					temp.add(arr[i]);
				}
			}
			//System.out.println();
			int totalValue = valueCalculation(temp);
			// If you can put the subset into the knapsack without violating the constraints.
			if (validWeight(temp) && totalValue >= currentMaxValue) { 
				if (totalValue > currentMaxValue) {
					currentMaxValue = totalValue; // Set new max value
					solutions.clear(); // Remove all subsets with the old max value
					solutions.add(temp); // Add new subset with the new max value
				}
				else {
					solutions.add(temp); // Add subset to list with same max value
				}
			}
			temp = null;
			
			// Increment counter
			int i = 0;
			while (i < counter.length && counter[i] == 1)
				counter[i++] = 0;
			if (i == counter.length)
				break;
			counter[i] = 1;
		}
	}

	// Returns true or false depending on if the subset can fit in the bag or not
	public boolean validWeight(ArrayList<Integer> arrayList) {
		
		int totalWeight = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			totalWeight += weights[arrayList.get(i) - 1];
		}

		return totalWeight <= knapSackWeightCapacity;
	}

	// Returns the value of the passed subset
	public int valueCalculation(ArrayList<Integer> arrayList) {
		
		int total = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			total += values[arrayList.get(i) - 1];
		}	
		return total;
	}

	// Returns the weight of the passed subset
	public int weightCalculation(ArrayList<Integer> arrayList) {
		
		int total = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			total += weights[arrayList.get(i) - 1];
		}
		return total;
	}
	
	// Prints the passed subset
	public String printSolution(ArrayList<Integer> list) {
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < list.size(); i++) {
			if (i < list.size() - 1) {
				sb.append(list.get(i) + ",");
			}
			else {
				sb.append(list.get(i));
			}
		}
		return sb.toString();
	}

	// Prints all the statistics that is required for the project's Rubric 
	public void generateStatistics() {
		
		System.out.println(testLabel);
		System.out.println("    Number of Items = " + numberOfItems);
		System.out.println("    Weight Capacity of Knapsack: " + knapSackWeightCapacity);
		System.out.println("\n    Brute Force Optimal Solutions (List all solutions):");
		for (int i = 0; i < solutions.size(); i++) {
			System.out.println("\tSubset of Items = {" + printSolution(solutions.get(i)) + "}; Subset Weight = "
					+ weightCalculation(solutions.get(i)) + "; Subset Value = " + valueCalculation(solutions.get(i)) + ";");
		}
		System.out.println("\tRuntime (ms) = " + runtime);
		System.out.println();
	}
}