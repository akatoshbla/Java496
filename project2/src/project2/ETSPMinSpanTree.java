/* 
 * Programmer: David Kopp
 * Project #: Project 2
 * File Name: ETSPMinSpanTree.java
 * Date: 10-25-15
 * Class: Comp496ALG
 * Description: This class using Prim's Algorithm and a depth first search 
 * 				solution to solve the Euclidean Traveling Salesman Problem.
 */

// Eclipse project package (comment out if not using eclipse project manager)
package project2;

// Import Statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

// Class for Prim's Algorithm Solution
public class ETSPMinSpanTree 
{
	
	// Declared Variables
	private int numberOfCities;
	private double distanceOfPath;

	// Input File Object
	File file;

	// Data Structures
	private ArrayList<Integer> pathTraveled;
	private double[] xloc;
	private double[] yloc;
	private double[][] distanceTable;
	private double[][] graphT;
	private boolean[] cityVisited;
	private int[] parent;
	private ArrayList<Integer> s = new ArrayList<Integer>();
	private double[] distance;
	private String[] cityNames; //cityName[k] is name of city k

	// Constructor for ETSPMinSpanTree
	public ETSPMinSpanTree(String inputFile, int startCity)
	{
		file = new File(inputFile);
		cityNames = new String[26];
		initArray(cityNames);
		readInputData();
		calculateDistances();
		//printTable(distanceTable);
		primsTour(startCity);
		//printEdges();
		createGraphT(startCity);
		pathTraveled.add(pathTraveled.get(0));
		distanceOfPath = computeDistance(pathTraveled);
	}
	
	// Prints the edges of prim's algorithm
	public void printEdges()
	{
		for (int i = 1; i < numberOfCities; i++) 
		{
			System.out.println("Edge " + i + ": (" + parent[s.get(i)] + ", " 
								+ s.get(i) + ") " + distance[s.get(i)]);
		}
	}
	
	// Prim's Algorithm generates a MST
	private void primsTour(int startCity)
	{
		
		int currentParent = startCity;
		
		distance[currentParent] = 0;
		cityVisited[currentParent] = true;
		parentOf(currentParent);
		s.add(currentParent);
		
		for (int i = 1; i < numberOfCities; i++)
		{
			int u = findSmallestU(i);
			s.add(u);
			for (int j = 0; j < numberOfCities; j++)
			{
				if (!s.contains(j) && distanceTable[u][j] != 0)
				{
				if (distanceTable[u][j] < distance[j])
				{
					distance[j] = distanceTable[u][j];
					parent[j] = u;
				}
				}
			}
			cityVisited[u] = true;
		}	
	}
	
	// Creates the graphT and does a dfs on it.
	private void createGraphT(int startCity)
	{
		for (int i = 1; i < numberOfCities; i++)
		{
			graphT[parent[s.get(i)]][s.get(i)] = distance[s.get(i)];
			graphT[s.get(i)][parent[s.get(i)]] = distance[s.get(i)];
		}
		//printTable(graphT);
		initArray(cityVisited);
		dfs(startCity);
		//Collections.reverse(pathTraveled);
		//System.out.println(pathTraveled.toString());
	}
	
	// Recursion method for DFS Traversal
	private void dfs(int start)
	{
	   Stack<Integer> stack = new Stack<Integer>();
	   boolean[] visited = new boolean[numberOfCities];
	   initArray(visited);
		   
	   stack.push(start);
	   visited[start] = true;
	   pathTraveled.add(start);
	   //System.out.print(start + " ");
		   
	   while (!stack.isEmpty())
	   {
		   int stackTop = stack.peek();
		   for (int i = start; i < numberOfCities; i++)
		   {
			  if (graphT[stackTop][i] != 0 && !visited[i])
			  {
				  stack.push(i);
				  visited[i] = true;
				  //System.out.print(i + " ");
				  stackTop = i;
				  pathTraveled.add(stackTop);
				  i = 1;
			  }
		   }		   
		   stack.pop();
	   }
		   
	} 
	
	// Finds the smallest distance for u
	public int findSmallestU(int u)
	{
		double smallestDistance = Double.POSITIVE_INFINITY;
		int result = 1;
		for (int i = 0; i < numberOfCities; i++) 
		{
			if (distance[i] < smallestDistance && !cityVisited[i])
			{
				smallestDistance = distance[i];
				result = i;
			}
		}
		return result;
	}
	
	// Inserts the parent city to all children cities
	private void parentOf(int parent)
	{
		for (int i = 0; i < numberOfCities; i++) 
		{
			if (distanceTable[parent][i] != 0 && !cityVisited[i])
			{
				distance[i] = distanceTable[parent][i];
				this.parent[i] = parent;
			}
		}
	}
	
	// Finds the next city to visit
	public int nextVisited(int city)
	{
		int result = -1;
		for (int i = 0; i < numberOfCities; i++)
		{
			if (cityVisited[i] && i != city)
			{
				result = i;
				break;
			}
		}
		return result;
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
				
			}
			
			if (temp1 < temp2 && temp1 != 0 && temp2 != 0)
			{
				toCity = i;
			}
			else if (temp1 == temp2)
			{
			temp2 = temp1;
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
	
	// Prints the distance table for troubleshooting
	public void printTable(double[][] arr) 
	{
		for (int i = 0; i < numberOfCities; i++) 
		{
			for (int j = 0; j < numberOfCities; j++) 
			{
				System.out.print(arr[i][j] + " ");
			}
			System.out.print("\n");
		}
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

		for (int i = 0; i + 1 < numberOfCities; i++)
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

		for (int i = 0; i < numberOfCities; i++)
		{
			sb.append(pathTraveled.get(i) + " ");
		}

		return sb.toString();
	}

	// Prints out an int[]
	public void printArr(int[] arr)
	{
		for (int i = 0; i < arr.length; i++)
		{
			System.out.print(+ arr[i] + " ");
		}
		System.out.println("\n");
	}

	// Labels for all the cities
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
		return "MST Tour: " + numberOfCities + " Cities\nMin Cost " + distanceOfPath 
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
			parent = new int[numberOfCities];			
			distance = new double[numberOfCities];
			xloc = new double[numberOfCities];
			yloc = new double[numberOfCities];
			distanceTable = new double[numberOfCities][numberOfCities];	
			graphT = new double[numberOfCities][numberOfCities];

			initArray(cityVisited);
			initArray(parent);
			initArray(distance);
			initArray(graphT);

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
	
	// Initiates the parent array
	private void initArray(int[] arr)
	{
		int length = arr.length;
		for (int i = 0; i < length; i++)
		{
			arr[i] = -1;
		}
	}
	
	// Initiates the distance array
	private void initArray(double[] arr)
	{
		int length = arr.length;
		for (int i = 0; i < length; i++)
		{
			arr[i] = Double.POSITIVE_INFINITY;
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
	
	// Initiates the graphT array[][]
	private void initArray(double[][] arr)
	{
		for (int i = 0; i < arr.length; i++) 
		{
			for (int j = 0; j < arr.length; j++) 
			{
				arr[i][j] = 0;
			}
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