/* 
 * Programmer: David Kopp
 * Project #: Project 2
 * File Name: ETSPBruteForce.java
 * Date: 10-25-15
 * Class: Comp496ALG
 * Description: This class using brute force to solve the Euclidean Traveling Salesman Problem.
 */

// Eclipse project package (comment out if not using eclipse project manager)
package project2;

// Import Statements
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

// Class for ETSP Brute Force
public class ETSPBruteForce 
{
	
// Declared Variables
private int numberOfCities;
private double distanceOfPath;

// Input File Object
File file;

// Data Structures
private int[] pathTraveled;
private int[] permutationArray;
private double[] xloc;
private double[] yloc;
private double[][] distanceTable;
private String[] cityNames; //cityName[k] is name of city k

// Constructor for ETSPBruteForce
public ETSPBruteForce(String inputFile)
{
	file = new File(inputFile);
	cityNames = new String[26];
	initArray(cityNames);
	readInputData();
	calculateDistances();
	checkDistance(permutationArray);
	permutations(permutationArray);
}

// Distance formula for two points in a Euclidean plane
public double distance(double x1, double x2, double y1, double y2)
{
	return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
}

// This method computes the distance traveled in an int[]
public double computeDistance(int[] traveledPath)
{
	double totalDistance = 0;
	//printArr(traveledPath);

	for (int i = 0; i + 1 <= numberOfCities; i++)
	{
		totalDistance += distanceTable[traveledPath[i]][traveledPath[i + 1]];
		//System.out.println("subtotal[" +i +"]: " +distanceTable[traveledPath[i]][traveledPath[i+1]]);
	}
	//System.out.println("Total: " +totalDistance);
	return totalDistance;
}

// Returns a String of the pathTraveled array
public String printPath() 
{
	StringBuilder sb = new StringBuilder();

	for (int i = 0; i <= numberOfCities; i++)
	{
		sb.append(pathTraveled[i] + " ");
	}

	return sb.toString();
}

public void printArr(int[] arr)
{
	for(int i = 0; i < arr.length; i++)
	{
		System.out.print(+ arr[i] + " ");
	}
	System.out.println("\n");
}

public String printArr(String[] arr)
{
	StringBuilder sb = new StringBuilder();

	for (int i = 0; i < pathTraveled.length; i++)
	{
		sb.append(cityNames[pathTraveled[i]] + " ");
	}

	return sb.toString();
}

// Returns a String to print the information about the ETSPBruteForce object
public String toString()
{
	return "Optimal Tour: " + numberOfCities + " Cities\nMin Cost " + distanceOfPath 
			+ "\n" + printPath() + "\n" + printArr(cityNames) + "\n";
}

// Private Methods
// Reads the input of the text file and initializes variables and data structures
private void readInputData()
{
	try
	{
		Scanner input = new Scanner(file);
		numberOfCities = input.nextInt();
		pathTraveled = new int[numberOfCities + 1];
		permutationArray = new int[numberOfCities];
		xloc = new double[numberOfCities];
		yloc = new double[numberOfCities];
		distanceTable = new double[numberOfCities][numberOfCities];			

		initArray(pathTraveled);
		initPermutationArray(permutationArray);

		for (int i = 0; i < numberOfCities; i++)
		{
			xloc[i] = input.nextDouble();
		}

		for (int i = 0; i < numberOfCities; i++)
		{
			yloc[i] = input.nextDouble();
		}		
		input.close();
	} // End of Try
	catch (FileNotFoundException e)
	{
		System.out.println(e);
		System.exit(1);
	} // End of Catch
}

// Calculates the distance between every point to every point on the Euclidean plane
private void calculateDistances()
{
	for (int i = 0; i < numberOfCities; i++)
	{
		for (int j = 0; j < numberOfCities; j++)
		{
			distanceTable[i][j] = distance(xloc[i], xloc[j], yloc[i], yloc[j]);
		}
	}

}

/* Got this algorithm from 
 ** http://stackoverflow.com/questions/2799078/permutation-algorithm-without-recursion-java
 ** Posted by higuaro (Methods swap, reverse, and permutations)
 */ 
private int factorial(int n) {
	int fact = 1;
	for (int i = 1; i <= n; i++) {
		fact *= i;
	}
	return fact;
}

// Swaps elements in an array
private void swap(int[] elements, int i, int j) {
	int temp = elements[i];
	elements[i] = elements[j];
	elements[j] = temp;
}

// Reverses the elements of an array (in place) from the start index to the end index 
private void reverse(int[] array, int startIndex, int endIndex) {
	int size = endIndex + 1 - startIndex;
	int limit = startIndex + size / 2;
	for (int i = startIndex; i < limit; i++) {
		// swap(array, i, startIndex + (size - 1 - (i - startIndex)));
		swap(array, i, 2 * startIndex + size - 1 - i);
	}
}

/**
 * Implements the Knuth's L-Algorithm permutation algorithm 
 * modifying the collection in place
 */
private void permutations(int[] sequence) {
	final int N = sequence.length;
	// There are n! permutations, but the first permutation is the array without 
	// modifications, so the number of permutations is n! - 1
	int numPermutations = factorial(N) - 1;

	// For every possible permutation 
	for (int n = 0; n < numPermutations; n++) {

		// Iterate the array from right to left in search 
		// of the first couple of elements that are in ascending order
		for (int i = N - 1; i >= 1; i--) {
			// If the elements i and i - 1 are in ascending order
			if (sequence[i - 1] < sequence[i]) {
				// Then the index "i - 1" becomes our pivot index 
				int pivotIndex = i - 1;

				// Scan the elements at the right of the pivot (again, from right to left)
				// in search of the first element that is bigger
				// than the pivot and, if found, swap it
				for (int j = N - 1; j > pivotIndex; j--) {
					if (sequence[j] > sequence[pivotIndex]) {
						swap(sequence, j, pivotIndex);
						break;
					}
				}

				// Now reverse the elements from the right of the pivot index
				// (this nice touch to the algorithm avoids the recursion)
				reverse(sequence, pivotIndex + 1, N - 1);
				break;
			}
		}
		checkDistance(sequence);
	}
}

/* Checks the distance between the pathTraveled array and the passed next permutation.
 *  If the permutation is shorter in distance then it becomes pathTraveled. 
 *  Otherwise nothing happens.
 */
private void checkDistance(int[] pathGenerated)
{
	if (pathTraveled[0] == -1)
	{
		pathTraveled = copyArray(pathGenerated);
		distanceOfPath = computeDistance(pathTraveled);
	}
	else
	{
		int[] tempArr = new int[numberOfCities + 1];
		tempArr = copyArray(pathGenerated);
		double generatedPathDistance = computeDistance(tempArr);

		if (distanceOfPath > generatedPathDistance)
		{
			pathTraveled = tempArr;
			distanceOfPath = generatedPathDistance;
		}
	}
}

// Initiates an array to -1 
private void initArray(int[] inputArray)
{
	int length = inputArray.length;
	for (int i = 0; i < length; i++)
	{
		inputArray[i] = -1;
	}
}

private void initArray(String[] arr)
{
	char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();	
	for (int i = 0; i < arr.length; i++) 
	{
		arr[i] = Character.toString(chars[i]);
	}
}

// Initiates the permutationArray to 0 to n-1
private void initPermutationArray(int[] inputArray)
{
	int length = inputArray.length;
	for (int i = 0; i < length; i++)
	{
		inputArray[i] = i;
	}
}

// This method copies the array and adds another slot for the first city
private int[] copyArray(int[] arr1)
{
	int[] newArr = new int[numberOfCities + 1];
	
	for (int i = 0; i < arr1.length; i++)
	{
		newArr[i] = arr1[i];
	}
	
	newArr[numberOfCities] = arr1[0];
	
	return newArr;
}
}