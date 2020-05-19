/* 
 * Programmer: David Kopp
 * Project #: Project 3
 * File Name: DPMKSP.java
 * Date: 11-24-15
 * Class: Comp496ALG
 * Description: This class is the dynamic programming method to solving the knapsack
 * 				problem.
 */

package project3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class DPMKSP {

	// Data Structures
	private List<Integer> solutions;
	private int[][] OPT;
	private int[] values;
	private int[] weights;
	
	// Variables
	private int knapSackWeightCapacity;
	private int numberOfItems;
	private double runtime;

	// Main constructor for the Dynamic Programming Solution for the Knapsack Problem
	public DPMKSP(int[] values, int[] weights, int knapSackWeightCapacity, int numberOfItems) {
		
		runtime = 0;
		long startTime = System.nanoTime(); 
		solutions = new ArrayList<Integer>();
		OPT = new int[numberOfItems + 1][knapSackWeightCapacity + 1];
		this.values = values;
		this.weights = weights;
		this.knapSackWeightCapacity = knapSackWeightCapacity;
		this.numberOfItems = numberOfItems;
		generateOPTArray();
		solutions = findSolution(numberOfItems, knapSackWeightCapacity);
		long endTime = System.nanoTime();
		runtime = (endTime - startTime) / Math.pow(10, 6);
		generateStatistics();
	}

	// Generates the OPT table
	private void generateOPTArray() {
		
		for (int i = 1; i <= numberOfItems; i++) {
			for (int j = 1; j <= knapSackWeightCapacity; j++) {
				if (weights[i - 1] > j) {
					OPT[i][j] = OPT[i - 1][j];
				}
				else {
					OPT[i][j] = Math.max(OPT[i - 1][j], values[i - 1] + OPT[i - 1][j - weights[i - 1]]);
				}
			}		
		}
//		for (int i = 0; i <= numberOfItems; i++) { // Prints out the OPT Table for debugging
//			for (int k = 0; k <= knapSackWeightCapacity; k++) {
//				System.out.print(OPT[i][k] + " ");
//			}
//			System.out.println();
//		}		
	}

	// Recursive method to find the optimal set of items picked from the OPT table
	private List<Integer> findSolution(int k, int w) {
		
		List<Integer> temp = new ArrayList<Integer>();
		if (k == 0 || w == 0) { // If the bag is full or there is no more items to put into the bag
			return temp;
		}
		else if (weights[k - 1] > w) { // If you did not pick item
			return findSolution(k - 1, w);
		}
		else if (OPT[k - 1][w] >= values[k - 1] + OPT[k - 1][w - weights[k - 1]]) { // If you did not pick item
			return findSolution(k - 1, w);
		}
		else {	// Put item into solution set of items
			temp.add(k);
			return union(temp, findSolution(k - 1, w - weights[k - 1]));
		}
	}
	
	// Returns the sorted union of two lists
	public List<Integer> union(List<Integer> list1, List<Integer> list2) {
		
	    HashSet<Integer> set = new HashSet<Integer>();

	    set.addAll(list1);
	    set.addAll(list2);
	    List<Integer> unsorted = new ArrayList<Integer>(set);
	    Collections.sort(unsorted);
	    
	    return unsorted;
	}
	
	// Returns the value of the passed subset
	public int valueCalculation(List<Integer> arrayList) {
		
		int total = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			total += values[arrayList.get(i) - 1];
		}	
		return total;
	}

	// Returns the weight of the passed subset
	public int weightCalculation(List<Integer> arrayList) {
		
		int total = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			total += weights[arrayList.get(i) - 1];
		}
		return total;
	}

	// Prints all the statistics that is required for the project's Rubric
	public void generateStatistics() {
		
		System.out.println("    Dynamic Programming Optimal Solution (List one Solution):");
		System.out.print("\tSubset of Items = {");
		for (int i = 0; i < solutions.size(); i++) {
			if (i < solutions.size() - 1) {
			System.out.print(solutions.get(i) + ",");
			}
			else {
				System.out.print(solutions.get(i));
			}
		}
		System.out.print("}; Subset Weight = " + weightCalculation(solutions) + "; Subset Value = " 
							+ valueCalculation(solutions) + ";\n");
		System.out.println("\tRuntime (ms) = " + runtime);
		System.out.println();
	}
}