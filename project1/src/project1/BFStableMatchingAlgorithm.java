/* 
 * Programmer: David Kopp
 * Project #: Project 1
 * File Name: BFStableMatchingAlgorithm.java
 * Date: 9-15-15
 * Class: Comp496ALG
 * Description: This class finds the brute force solution for the Stable Matching
 * 				problem and then reports the stable matches. The permutation
 * 				algorithm was found at:  
 *  			http://stackoverflow.com/questions/2799078/permutation-algorithm-without-recursion-java
 * 				Posted by higuaro 
 */

// Defined package for eclipse project (comment out if not using eclipse)
package project1;

// Imported Statements
//import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// Class for Brute Force Algorithm
public class BFStableMatchingAlgorithm 
{
	
	// Variables
	private int numberOfPairs;
	
	// Input File
	private File file;
	
	// Data Structures
	private int[] marriagePairList;
	private int[][] malePreferences;
	private int[][] femalePreferences;
	private int[] permutationArray;
	//private ArrayList<ArrayList<Integer>> permutationList;
	
	// Public Methods
	// Constructor that starts the BF Algorithm
	public BFStableMatchingAlgorithm(String inputFileName) 
	{
		file = new File(inputFileName);
		readInputData();
	}
	
	// Prints the permutationArray
	public void printPairs()
	{
		System.out.print("[");
		for (int i = 0; i < numberOfPairs; i++)
		{
			System.out.print("(" + i + "," + permutationArray[i] + ")");
			if (i != numberOfPairs - 1)
			{
				System.out.print(",");
			}
			else
			{
				System.out.print("]\n");
			}
		}
	}
	
	/* Got this algorithm from 
	** http://stackoverflow.com/questions/2799078/permutation-algorithm-without-recursion-java
	** Posted by higuaro
	*/ 
	private int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
	
	/* Got this algorithm from 
	** http://stackoverflow.com/questions/2799078/permutation-algorithm-without-recursion-java
	** Posted by higuaro
	*/ 
    private void swap(int[] elements, int i, int j) {
        int temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

	/* Got this algorithm from 
	** http://stackoverflow.com/questions/2799078/permutation-algorithm-without-recursion-java
	** Posted by higuaro
	**
    ** Reverses the elements of an array (in place) from the start index to the end index 
    **/
    private void reverse(int[] array, int startIndex, int endIndex) {
        int size = endIndex + 1 - startIndex;
        int limit = startIndex + size / 2;
        for (int i = startIndex; i < limit; i++) {
            // swap(array, i, startIndex + (size - 1 - (i - startIndex)));
            swap(array, i, 2 * startIndex + size - 1 - i);
        }
    }

	/* Got this algorithm from 
	** http://stackoverflow.com/questions/2799078/permutation-algorithm-without-recursion-java
	** Posted by higuaro
	*/ 
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

            checkStability(sequence);
        }
    }
    
    // Calls for a check on the first permutation and then calls for the next one
    public void bruteForceSolution(int[] num)
    {
    	checkStability(num);
    	permutations(num);
    }
	
    // Checks to see if the permutation is stable
	public void checkStability(int[] pairsList)
	{
		//for (int i = 0; i < pairsList.size(); i++)
		//{
			//ArrayList<Integer> pairList = pairsList.get(i);
			if (isStable(pairsList))
			{
				//convertArrayListToArray(pairList);
				
				printPairs();
			}
			else
			{
				//System.out.println(pairList + " - Instable Match!");
			}
		//}
	}
	
	// Returns a boolean depending on if it is stable(true) or not(false).
	public boolean isStable(int[] pairing)
	{
		boolean isStable = true;
		int preferredMate = -1;
		
		for (int i = 0; i < numberOfPairs; i++ )
		{
			for (int j = 0; j < numberOfPairs; j++)
			{
				preferredMate = malePreferences[i][j];
				if (maleRanking(i, pairing[i]) > 
					maleRanking(i, malePreferences[i][j]))
				{
					if (femaleRanking(preferredMate, i) < 
							femaleRanking(preferredMate, 
								findPair(preferredMate, pairing)))
					{
						isStable = false;
					}
				}
			}
		}
		return isStable;
	}
	
	// Finds the male that is engaged to a specific female
	public int findPair(int female, int[] pairing)
	{
		int malePair = -1;
		for (int i = 0; i < numberOfPairs; i++)
		{
			if (pairing[i] == female)
			{
				malePair = i;
			}
		}
		return malePair;
	}
	
	// Returns what rank the female is on the male's preference list
	public int maleRanking(int male, int female)
	{
		int rank = -1;
		for (int i = 0; i < numberOfPairs; i++)
		{
			if (malePreferences[male][i] == female)
			{
				rank = i;
			}
		}
		return rank;
	}
	
	// Returns what rank the male is on the female's preference list
	public int femaleRanking(int female, int male)
	{
		int rank = -1;
		for (int i = 0; i < numberOfPairs; i++)
		{
			if (femalePreferences[female][i] == male)
			{
				rank = i;
			}
		}
		return rank;
	}
	
	// Private Methods
	// Reads the data from the input text file and also initiates the variables and data structures
	private void readInputData()
	{
		try
		{
			Scanner input = new Scanner(file);
			numberOfPairs = input.nextInt();
			marriagePairList = new int[numberOfPairs];
			permutationArray = new int[numberOfPairs];
			malePreferences = new int[numberOfPairs][numberOfPairs];
			femalePreferences = new int[numberOfPairs][numberOfPairs];			
			
			initArray(marriagePairList);
			initPermutationArray(permutationArray);
			
			for (int i = 0; i < numberOfPairs; i++)
			{
				for (int j = 0; j < numberOfPairs; j++)
				{
					malePreferences[i][j] = input.nextInt();
				}
			}
			
			for (int i = 0; i < numberOfPairs; i++)
			{
				for (int j = 0; j < numberOfPairs; j++)
				{
					femalePreferences[i][j] = input.nextInt();
				}
			}		
			input.close();
		} // End of Try
		catch (FileNotFoundException e)
		{
			System.out.println(e);
			System.exit(1);
		} // End of Catch
		bruteForceSolution(permutationArray);
		//checkStability(permutationList);
	}
	
	// Initiates an array to -1 
	private void initArray(int[] inputArray)
	{
		for (int i = 0; i < numberOfPairs; i++)
		{
			inputArray[i] = -1;
		}
	}
	
	// Initiates the permutationArray to 0 to n-1
	private void initPermutationArray(int[] inputArray)
	{
		for (int i = 0; i < numberOfPairs; i++)
		{
			inputArray[i] = i;
		}
	}
	
	// Currently not used - was for testing with a different permutation algorithm.
//	private void convertArrayListToArray(ArrayList<Integer> list)
//	{
//		for (int i = 0; i < list.size(); i++)
//		{
//			marriagePairList[i] = list.get(i);
//		}
//	}
}