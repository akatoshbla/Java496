/* 
 * Programmer: David Kopp
 * Project #: Project 2
 * File Name: ETSPGreedy.java
 * Date: 10-25-15
 * Class: Comp496ALG
 * Description: This class using the Greedy Solution to solve 
 * 				the Euclidean Traveling Salesman Problem.
 */

// Eclipse project package (comment out if not using eclipse project manager)
package project2;

// Import Statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Class for Greedy Solution
public class ETSPGreedy 
{
	// Declared Variables
	private int numberOfCities;
	private double distanceOfPath;

	// Input File Object
	File file;

	// Data Structures
	private ArrayList<Integer> pathTraveled;
	private boolean[] cityVisited;
	private double[] xloc;
	private double[] yloc;
	private double[][] distanceTable;
	private String[] cityNames; //cityName[k] is name of city k
	
	// Constructor for ETSPGreedy
	public ETSPGreedy(String inputFile, int startCity)
	{
		file = new File(inputFile);
		cityNames = new String[26];
		initArray(cityNames);
		readInputData();
		calculateDistances();
		//printDistanceTable();
		greedyTour(startCity);
		pathTraveled.add(pathTraveled.get(0));
		distanceOfPath = computeDistance(pathTraveled);
	}
	
	// Prints the distance table for troubleshooting
	public void printDistanceTable() 
	{
		for (int i = 0; i < numberOfCities; i++) 
		{
			for (int j = 0; j < numberOfCities; j++) 
			{
				System.out.print(distanceTable[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	// This method is the Greedy Algorithm for the ETSP problem
	private void greedyTour(int startCity)
	{
		pathTraveled.add(startCity);
		cityVisited[startCity] = true;
		
		int path = nextCity(startCity);
		
		while (!hasVisitedAll())
		{
			pathTraveled.add(path);
			cityVisited[path] = true;
			path = nextCity(path);
		}
	}
	
	// This method finds the next closest unvisited city
	public int nextCity(int fromCity)
	{
		int toCity = firstNotVisited();
		double temp1 = distanceTable[fromCity][toCity];
		double temp2 = temp1;
		for(int i = toCity; i < numberOfCities; i++)
		{
			if (!cityVisited[i])
			{
				temp1 = distanceTable[fromCity][i];
				
				if (temp1 < temp2 && temp1 != 0 && temp2 != 0)
				{
					toCity = i;
				}
			}
		}
		return toCity;
	}
	
	// This method finds out if all the cities have been visited
	public boolean hasVisitedAll()
	{
		boolean result = true;
		for (int i = 0; i < cityVisited.length; i++)
		{
			if (!cityVisited[i])
			{
				result = false;
				break;
			}
		}
		return result;
	}
	
	// This method finds the first unvisited city
	public int firstNotVisited()
	{
		int result = 0;
		
		for (int i = 0; i < cityVisited.length; i++)
		{
			if (!cityVisited[i])
			{
				result = i;
				break;
			}
		}
		return result;
	}
	
	// Distance formula for two points in a Euclidean plane
	public double distance(double x1, double x2, double y1, double y2)
	{
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	// This method computes the distance traveled in an ArrayList<Integer>
	public double computeDistance(ArrayList<Integer> traveledPath)
	{
		double totalDistance = 0;
		//printArr(traveledPath);

		for (int i = 0; i + 1 <= numberOfCities; i++)
		{
			totalDistance += distanceTable[traveledPath.get(i)][traveledPath.get(i + 1)];
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
			sb.append(pathTraveled.get(i) + " ");
		}

		return sb.toString();
	}

	public void printArr(int[] arr)
	{
		for (int i = 0; i < arr.length; i++)
		{
			System.out.print(+ arr[i] + " ");
		}
		System.out.println("\n");
	}

	public String printArr(String[] arr)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < pathTraveled.size(); i++)
		{
			sb.append(cityNames[pathTraveled.get(i)] + " ");
		}

		return sb.toString();
	}

	// Returns a String to print the information about the ETSPBruteForce object
	public String toString()
	{
		return "Best Greedy Tour: " + numberOfCities + " Cities\nMin Cost " + distanceOfPath 
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
			cityVisited = new boolean[numberOfCities];
			pathTraveled = new ArrayList<Integer>();
			xloc = new double[numberOfCities];
			yloc = new double[numberOfCities];
			distanceTable = new double[numberOfCities][numberOfCities];			

			initArray(cityVisited);

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
	
	// Initiates the cityVisited array
	private void initArray(boolean[] arr)
	{
		int length = arr.length;
		for (int i = 0; i < length; i++)
		{
			arr[i] = false;
		}
	}
	
	// Initiates the cityName array
	private void initArray(String[] arr)
	{
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();	
		for (int i = 0; i < arr.length; i++) 
		{
			arr[i] = Character.toString(chars[i]);
		}
	}
}